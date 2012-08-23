/**
 * Copyright (c) 2011-2012 University of Bolton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE 
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.xcri.presentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.core.Provider;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.XcriElement;
import org.xcri.util.lax.Lax;
import org.xcri.util.lax.SingleElementException;

public class Venue extends XcriElement{
	
	private Log log = LogFactory.getLog(Venue.class);

	
	private Provider provider;

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.XCRI_NAMESPACE_NS;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "venue";
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getProvider() != null) element.addContent(this.getProvider().toXml());
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
	
		if (!this.getValue().trim().isEmpty()){
			throw new InvalidElementException("venue: If a <venue> element contains any text content, Aggregators MUST treat the <venue> element as being in error.");
		}
		
		try {
			Lax.getChild(element, "provider", Namespaces.XCRI_NAMESPACE_NS);
		} catch (SingleElementException e) {
			throw new InvalidElementException("venue: If a <venue> element does not contain one and only one <provider> element, Aggregators MUST treat the <venue> element as being in error.");
		}
		
		Element providerElement = Lax.getChildQuietly(element, "provider", Namespaces.XCRI_NAMESPACE_NS, log);
		if (providerElement != null){
			this.provider = new Provider();
			provider.fromXml(providerElement);
		
			if (provider.getLocation() == null){
				log.warn("Location: When a provider element is used in a venue element, Producers SHOULD include a location element.");
			}
		} else {
			throw new InvalidElementException("venue: If a <venue> element does not contain one and only one <provider> element, Aggregators MUST treat the <venue> element as being in error.");
		}
		
		// TODO If a <venue> element contains any child elements other than <provider>, Aggregators MUST treat the <venue> element as being in error.
		
		// NOT TESTABLE: Provider: Producers SHOULD use the provider element to describe the organisation which acts as the provider of the venue. This MAY be a sub-organisation of the provider of the presentation (such as a department or school), or it MAY be a third party or external organisation.
		// NOT TESTABLE: Provider Properties: When a provider element is used in a venue element, Producers SHOULD include only basic information about an organisation, such as identifier, title, description, url, image and location.
		 

	}

	/**
	 * @return the provider
	 */
	public Provider getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	

}

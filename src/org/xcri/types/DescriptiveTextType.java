/**
 * Copyright (c) 2011 University of Bolton
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
package org.xcri.types;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xcri.Namespaces;
import org.xcri.ParserConfiguration;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.util.ContentSecurityFilter;

public class DescriptiveTextType extends XcriElement{

	private Log log = LogFactory.getLog(DescriptiveTextType.class);

	private String href;
	private boolean isXhtml = false;
	private Element xhtml = null;

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/* (non-Javadoc)
	 * @see org.xcri.Localized#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);


		//
		// Add XHTML content if present
		//
		if (element.getChild("div", Namespaces.XHTML_NAMESPACE_NS)!= null){
			XMLOutputter out = new XMLOutputter();
			StringWriter writer = new StringWriter();
			xhtml = (Element) element.getChild("div", Namespaces.XHTML_NAMESPACE_NS).detach();
			try {
				//
				// Check for potentially dangerous elements
				//
				Iterator i = xhtml.getDescendants(new ContentSecurityFilter());
				ArrayList<Element> toRemove = new ArrayList<Element>();
				while (i.hasNext()){
					Element dangerousElement = (Element) i.next();
					toRemove.add(dangerousElement);
					log.warn("description : content contains potentially dangerous XHTML :"+dangerousElement.getName());
				}
				
				//
				// If configured to do so, remove elements
				//
				if (ParserConfiguration.getInstance().sanitizeXHTML()){
					for (Element dangerousElement: toRemove){
						log.warn("description : removing XHTML element :"+dangerousElement.getName());
						dangerousElement.getParentElement().removeContent(dangerousElement);					
					}
				}
				
				out.output(xhtml, writer);
				this.setValue(writer.getBuffer().toString());
				isXhtml = true;


			} catch (IOException e) {
				throw new InvalidElementException("Error reading content of description element");
			}
		}

		//
		// Add HREF
		//
		this.setHref(element.getAttributeValue("href"));

		//
		// Cannot have both linked and inline content
		//
		if (this.getValue()!=null && this.getValue().length()>0){
			if(this.getHref()!= null && this.getHref().length()>0){
				throw new InvalidElementException("Description contains both text content and href attribute; only one or the other may be used");
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.xcri.Localized#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getHref() != null) element.setAttribute("href", this.getHref());
		if (isXhtml){
			element.addContent(xhtml);
		}
		return element;
	}



}

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

package org.xcri.course;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.XcriElement;

public class Credit extends XcriElement {
	private Log log = LogFactory.getLog(Credit.class);
	
	public static final String CREDIT_NAMESPACE = "http://purl.org/net/cm";
	private static final Namespace CREDIT_NAMESPACE_NS = Namespace.getNamespace(CREDIT_NAMESPACE);

	private String scheme;
	private String level;
	private String creditValue;
	
	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}
	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the value
	 */
	public String getCreditValue() {
		return creditValue;
	}
	/**
	 * @param value the value to set
	 */
	public void setCreditValue(String value) {
		this.creditValue = value;
	}
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.MLO_NAMESPACE_NS;
	}
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "credit";
	}
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getScheme() != null){
			Element schemeElement = new Element("scheme", CREDIT_NAMESPACE_NS);
			schemeElement.setText(getScheme());
			element.addContent(schemeElement);
		}
		if (this.getLevel() != null){
			Element levelElement = new Element("level", CREDIT_NAMESPACE_NS);
			levelElement.setText(getLevel());
			element.addContent(levelElement);
		}
		if (this.getCreditValue() != null){
			Element valueElement = new Element("value", CREDIT_NAMESPACE_NS);
			valueElement.setText(getCreditValue());
			element.addContent(valueElement);
		}
		return element;
	}
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		Element scheme = element.getChild("scheme", CREDIT_NAMESPACE_NS);
		Element level = element.getChild("level", CREDIT_NAMESPACE_NS);
		Element value = element.getChild("value", CREDIT_NAMESPACE_NS);
		
		if (scheme != null && scheme.getText() != null && scheme.getText().trim().length() > 0) this.setScheme(scheme.getText());
		if (level != null && level.getText() != null && level.getText().trim().length() > 0) this.setLevel(level.getText());
		if (value != null && value.getText() != null && value.getText().trim().length() > 0) this.setCreditValue(value.getText());
		
		/**
		 * multiple credit schemes: Producers SHOULD use a separate credit element to represent the credits for each scheme.
         */
		if (element.getChildren("value", CREDIT_NAMESPACE_NS).size() > 1){
			log.warn("credit : Multiple credit values found: Producers SHOULD use a separate credit element to represent the credits for each scheme");
		}
		
		/**
		 * scheme: While scheme is optional, the scheme SHOULD be stated unless a default has been agreed between the Producer and the Aggregator.
		 */
		if (this.getScheme() == null){
			log.warn("credit: While scheme is optional, the scheme SHOULD be stated unless a default has been agreed between the Producer and the Aggregator");
		}
	}

}

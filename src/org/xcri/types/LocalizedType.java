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

import org.jdom.Element;
import org.xcri.Namespaces;
import org.xcri.XcriObject;
import org.xcri.exceptions.InvalidElementException;

public abstract class LocalizedType extends XcriElement implements XcriObject {
	
	private String lang;
	private String content;

	/* (non-Javadoc)
	 * @see org.xcri.XcriObject#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		this.setContent(element.getText());
		this.setLang(element.getAttributeValue("lang", Namespaces.XML_NAMESPACE_NS));
		
	}

	/* (non-Javadoc)
	 * @see org.xcri.XcriObject#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getLang() != null) element.setAttribute("lang", this.getLang(), Namespaces.XML_NAMESPACE_NS);
		return element;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

}

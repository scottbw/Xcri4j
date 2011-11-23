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
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.XcriObject;
import org.xcri.exceptions.InvalidElementException;

public abstract class XcriElement implements XcriObject {

	private String name;
	private Namespace namespace;
	private String value;
	private String type;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the namespace
	 */
	public Namespace getNamespace() {
		return namespace;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	@Override
	public Element toXml() {
		Element element = new Element(this.getName(), this.getNamespace());
		if (this.getValue() != null) element.setText(getValue());
		if (this.getType() != null) element.setAttribute("type", getType(), Namespaces.XSI_NAMESPACE_NS);;
		return element;
 	}

	/* (non-Javadoc)
	 * @see org.xcri.XcriObject#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		this.setValue(element.getText());
		this.setType(element.getAttributeValue("type", Namespaces.XSI_NAMESPACE_NS));
	}
	
	
}

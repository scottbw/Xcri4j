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

package org.xcri.common;

import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.types.DescriptiveTextType;

public class Description extends DescriptiveTextType{

	/* (non-Javadoc)
	 * @see org.xcri.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.DC_NAMESPACE_NS;
	}

	/* (non-Javadoc)
	 * @see org.xcri.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "description";
	}

	/**
	 * Create a clone of the description, including a deep clone of the Xhtml Node
	 */
	public Description clone(){
		Description description = new Description();
		description.setHref(this.getHref());
		description.setLang(this.getLang());
		description.setType(this.getType());
		if (this.getXhtml() != null)
			description.setXhtml((Element)this.getXhtml().clone());
		description.setValue(this.getValue());
		description.setIsXhtml(this.isXhtml());
		return description;
	}
	
	

}

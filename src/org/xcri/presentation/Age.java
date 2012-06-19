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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.XcriElement;

public class Age extends XcriElement{

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
		return "age";
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		

		/* The value of this element MUST be one of:
		 * any
		 * not known
		 * x-y
		 * x+
		 * Where x and y MUST be non-negative integers.
		 * If the value of x is greater than the value of y, Aggregators MUST treat this element as in error and ignore this element.
		 * If the content of this element is not one of the values or patterns defined above, Aggregators MUST treat this element as in error and ignore this element.
		 */
		
		Pattern regex = Pattern.compile("(any|not known|^\\d+-\\d+|^\\d+\\+)$");
		Matcher matcher = regex.matcher(this.getValue());
		if (!matcher.find()) throw new InvalidElementException("value does not conform to a required pattern, which must be one of 'any', 'not known', x-y, or x+");

		Pattern range = Pattern.compile("(^\\d+)-(\\d+)$");
		matcher = range.matcher(this.getValue());
		if (matcher.find()){
			int start = Integer.parseInt(matcher.group(1));
			int second = Integer.parseInt(matcher.group(2));
			if (start > second) throw new InvalidElementException("start age greater than end age");
		}
	}
	
	
	

}

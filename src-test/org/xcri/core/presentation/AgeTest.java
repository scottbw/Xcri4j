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
package org.xcri.core.presentation;

import static org.junit.Assert.assertEquals;

import org.jdom.Element;
import org.junit.Test;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.presentation.Age;

public class AgeTest {
	
	/* The value of this element MUST be one of:
	 * any
	 * not known
	 * x-y
	 * x+
	 * Where x and y MUST be non-negative integers.
	 * If the value of x is greater than the value of y, Aggregators MUST treat this element as in error and ignore this element.
	 * If the content of this element is not one of the values or patterns defined above, Aggregators MUST treat this element as in error and ignore this element.
	 */
	
	@Test
	public void ranges() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("18-30");
		age.fromXml(element);
		element.setText("0-30");
		age.fromXml(element);
		element.setText("100-120");
		age.fromXml(element);
	}
	
	@Test
	public void lowerbounds() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("18+");
		age.fromXml(element);
		element.setText("0+");
		age.fromXml(element);
		element.setText("100+");
		age.fromXml(element);
	}

	@Test
	public void strings() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("any");
		age.fromXml(element);
		assertEquals("any", age.getValue());
		element.setText("not known");
		age.fromXml(element);
		assertEquals("not known", age.getValue());
	}
	
	@Test(expected=InvalidElementException.class)
	public void invalidStrings() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("other");
		age.fromXml(element);
	}
	
	@Test(expected=InvalidElementException.class)
	public void invalidRange() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("25-");
		age.fromXml(element);
	}
	
	
	@Test(expected=InvalidElementException.class)
	public void invalidRange2() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("-25");
		age.fromXml(element);
	}
	
	@Test(expected=InvalidElementException.class)
	public void invalidRange3() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("+5");
		age.fromXml(element);
	}
	
	@Test(expected=InvalidElementException.class)
	public void invalidRange4() throws InvalidElementException{
		Age age = new Age();
		Element element = new Element("age");
		element.setText("50-5");
		age.fromXml(element);
	}

}

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
package org.xcri;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.Test;
import org.xcri.common.Description;
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;

public class GeneralTest {
	
	@Test
	public void parseReallyBadDocument() throws IOException, JDOMException, InvalidElementException{
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		
		Document document = builder.build(new File("src-test/really_bad_xcri.xml"));
		
		catalog.fromXml(document);
		
		assertEquals("This is a really bad example provider", catalog.getProviders()[0].getDescriptions()[0].getValue());
		assertEquals("A provider", catalog.getProviders()[0].getTitles()[0].getValue());
		
		assertEquals("A Course", catalog.getProviders()[0].getCourses()[0].getTitles()[0].getValue());
		
		Description description = catalog.getProviders()[0].getCourses()[0].getDescriptions()[0];
		String output = new XMLOutputter().outputString(description.toXml());
		assertEquals("<dc:description xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><xhtml:div xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"><xhtml:p>This is a course, its great!</xhtml:p></xhtml:div></dc:description>", output);

		
		new XMLOutputter().output(catalog.toXml(), System.out);
	}
	

}

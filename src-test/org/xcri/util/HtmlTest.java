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
package org.xcri.util;

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

public class HtmlTest {
	
	@Test
	public void parseCdata() throws IOException, JDOMException, InvalidElementException{
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		
		Document document = builder.build(new File("src-test/cdata_test.xml"));
		
		catalog.fromXml(document);
		
		Description description = catalog.getProviders()[0].getCourses()[0].getDescriptions()[0];
		
		String output = new XMLOutputter().outputString(description.toXml());
		
		assertEquals("<dc:description xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><xhtml:div xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"><xhtml:p>This is a computing course, its great!</xhtml:p></xhtml:div></dc:description>", output);
	}
	
	@Test
	public void parsePlain() throws IOException, JDOMException, InvalidElementException{
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		
		Document document = builder.build(new File("src-test/cdata_plain_test.xml"));
		
		catalog.fromXml(document);
		
		Description description = catalog.getProviders()[0].getCourses()[0].getDescriptions()[0];
		
		String output = new XMLOutputter().outputString(description.toXml());
		
		assertEquals("<dc:description xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><xhtml:div xmlns:xhtml=\"http://www.w3.org/1999/xhtml\">This is a computing course, its great!</xhtml:div></dc:description>", output);
	}
	
	@Test
	public void parseXhtml() throws IOException, JDOMException, InvalidElementException{
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		
		Document document = builder.build(new File("src-test/xhtml_test.xml"));
		
		catalog.fromXml(document);
		
		Description description = catalog.getProviders()[0].getCourses()[0].getDescriptions()[0];
		
		String output = new XMLOutputter().outputString(description.toXml());
		
		assertEquals("<dc:description xmlns:dc=\"http://purl.org/dc/elements/1.1/\"><xhtml:div xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"><p xmlns=\"http://www.w3.org/1999/xhtml\">This is a computing course, its great!</p></xhtml:div></dc:description>", output);
	}

}

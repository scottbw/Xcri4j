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
package org.xcri.core;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xcri.exceptions.InvalidElementException;

public class ProviderTest {
	
	private static Catalog catalog;
	
	@BeforeClass
	public static void setup() throws JDOMException, IOException, InvalidElementException{
		catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new File("src-test/test.xml"));
		catalog.fromXml(document);
	}
	
	@Test
	public void testProviderProperties(){
		assertEquals(1, catalog.getProviders().length);
		Provider provider = catalog.getProviders()[0];
		assertEquals("This is an example provider to show what needs to be implemented for a valid XCRI feed", provider.getDescriptions()[0].getValue());
		assertEquals("http://es.craighawker.co.uk/", provider.getIdentifiers()[0].getValue());
		assertEquals("http://www.craighawker.co.uk/logo.gif", provider.getImages()[0].getSrc());
		assertEquals("Example provider", provider.getTitles()[0].getValue());
		assertEquals("http://www.craighawker.co.uk/", provider.getUrls()[0].getValue());
	}

}
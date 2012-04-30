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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;

public class CatalogTest {

	@Test
	public void parse() throws IOException, JDOMException, InvalidElementException{
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		
		Document document = builder.build(new File("src-test/test.xml"));
		
		catalog.fromXml(document);
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(catalog.toXml(), System.out);
	}
	/**
	 * "generated attribute is invalid: if the value of the @generated attribute 
	 * is not a valid date/time according to ISO 8601, then an Aggregator SHOULD 
	 * treat the XCRI document as being in error."
	 */
	@Test(expected = InvalidElementException.class)
	public void invalidGeneratedDate() throws IOException, JDOMException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog generated='invalid'/>"));
		catalog.fromXml(document);
	}
	
	/**
	 * generated attribute is missing: If a <catalog> does not contain an 
	 * @generated attribute, then an Aggregator MAY process the document treating 
	 * the document as if it was generated at the time the catalog was obtained 
	 * by the Aggregator.
	 */
	@Test
	public void noGeneratedDate() throws IOException, JDOMException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog/>"));
		catalog.fromXml(document);
		assertEquals(Calendar.getInstance().getTime(), catalog.getGenerated());
	}
	
	/**
	 * generated: Producers SHOULD use both date and time in the @generated 
	 * attribute. Example: '2010-08-02T06:14:37Z'.
	 */
    @Test
    public void incompleteGeneratedDate() throws InvalidElementException, JDOMException, IOException {
        Logger logger = Logger.getLogger(Catalog.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog generated='2011-11-11'/>"));
    		catalog.fromXml(document);
    		SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
    		assertEquals("2011", simpleDateformat.format(catalog.getGenerated()));

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: catalog: @generated contains date but not time"));

        } finally {
            logger.removeHandler(handler);
        }
    }
    
	/**
	 * Test that even where providers use a bad namespace, we allow it and log it
	 */
	@Test
	public void laxWithInvalidNamespace() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Catalog.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog><provider><title>Test</title></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("elements use incorrect namespace"));
            assertEquals(1,catalog.getProviders().length);

        } finally {
            logger.removeHandler(handler);
        }	
	}


}

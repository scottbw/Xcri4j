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
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xcri.Namespaces;
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

	/**
	 * TODO Identifiers: Producers SHOULD create Provider elements with 
	 * a default unique identifier formatted as a URL 
	 * (e.g. "http://www.bolton.ac.uk/"). Producers MAY include 
	 * additional identifiers in other formats, but these SHOULD be 
	 * qualified using xsi:type to a specific identifier namespace. 
	 * An example would include the UK provider reference number (UKPRN) 
	 * within the UKRLP:UKPRN namespace. See the identifier element 
	 * definition for more details.
	 */

	/**
	 * Title: Producers SHOULD include at least one title element for a 
	 * provider, which SHOULD be the trading name of the organisation
	 */
	@Test
	public void noTitle() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Provider.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("provider: provider has no title"));

        } finally {
            logger.removeHandler(handler);
        }	
	}
	
	/** 
	 * Type: Where a Producer uses the Type element for a provider, 
	 * this SHOULD use a sector-specific controlled list of terms 
	 * for this element. These SHOULD be qualified using xsi:type 
	 * to a specific vocabulary namespace.
	 */

	/**
	 * Url: Producers SHOULD include one Url element for each provider, 
	 * which SHOULD include a URL for its homepage, or its applications 
	 * microsite, in addition to its primary domain identifier (see above).
	 */
	@Test
	public void testNoUrl() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Provider.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("provider: provider has no URL"));

        } finally {
            logger.removeHandler(handler);
        }	
	}

	/**
	 * Course: In almost all cases Producers SHOULD include at least one 
	 * course for a provider. The capability for supporting zero courses 
	 * is offered for cases where XCRI CAP is used to format course search 
	 * results.
	 */
	@Test
	public void noCourses() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Provider.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><mlo:url>http://test.com</mlo:url><dc:subject>lots of stuff</dc:subject></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("provider: provider contains no courses"));

        } finally {
            logger.removeHandler(handler);
        }	
	}
	
	/**
	 * Location
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws InvalidElementException 
	 */
	@Test
	public void location() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><mlo:location><street>1 High Street</street><town>New Town</town></mlo:location></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("1 High Street", catalog.getProviders()[0].getLocation().getStreet());
		assertEquals("New Town", catalog.getProviders()[0].getLocation().getPostalTown());
	}
	
	/**
	 * TODO multiple location elements
	 */
	
	/**
	 * Location
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws InvalidElementException 
	 */
	@Test
	public void locations() throws JDOMException, IOException, InvalidElementException{

		Logger logger = Logger.getLogger(Provider.class.getName());
		Formatter formatter = new SimpleFormatter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Handler handler = new StreamHandler(out, formatter);
		logger.addHandler(handler);

		try {
			Catalog catalog = new Catalog();
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><mlo:location><street>1 High Street</street><town>New Town</town></mlo:location><mlo:location></mlo:location></provider></catalog>"));
			catalog.fromXml(document);

			handler.flush();
			String logMsg = out.toString();

			assertNotNull(logMsg);
			assertTrue(logMsg.contains("provider : multiple <location> elements found; skipping all but first occurrence"));

			assertEquals("1 High Street", catalog.getProviders()[0].getLocation().getStreet());
			assertEquals("New Town", catalog.getProviders()[0].getLocation().getPostalTown());
		} finally {
			logger.removeHandler(handler);
		}
	}
	
	
	/**
	 * Location
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws InvalidElementException 
	 */
	@Test
	public void locationsNoNs() throws JDOMException, IOException, InvalidElementException{

		Logger logger = Logger.getLogger(Provider.class.getName());
		Formatter formatter = new SimpleFormatter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Handler handler = new StreamHandler(out, formatter);
		logger.addHandler(handler);

		try {
			Catalog catalog = new Catalog();
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader("<catalog><provider><location><address>1 High Street</address></location><location><address>25 Test Street</address></location></provider></catalog>"));
			catalog.fromXml(document);

			handler.flush();
			String logMsg = out.toString();

			assertNotNull(logMsg);
			assertTrue(logMsg.contains("provider : multiple <location> elements found; skipping all but first occurrence"));			
			assertEquals("1 High Street", catalog.getProviders()[0].getLocation().getAddress()[0]);
		} finally {
			logger.removeHandler(handler);
		}
	}

}

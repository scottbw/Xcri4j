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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
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
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;

public class CourseTest {
	
	/**
	 * Identifier: A Course SHOULD have a unique identifier 
	 * formatted as a URI (e.g. "http://www.bolton.ac.uk/courses/1"). 
	 * Additional identifiers in other formats may be used, 
	 * but SHOULD be qualified using xsi:type to a specific identifier 
	 * namespace. See the guidance on the identifier element for more 
	 * details.
	 */
	@Test
	public void noIdentifier() throws JDOMException, IOException, InvalidElementException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\"><provider><course></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: course: course does not contain any identifiers"));

        } finally {
            logger.removeHandler(handler);
        }
	}	
	@Test
	public void noUriIdentifier() throws JDOMException, IOException, InvalidElementException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\"><provider><course><dc:title>Course Test</dc:title><dc:identifier>test-no-urls</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: course: course does not contain a URI identifier"));

        } finally {
            logger.removeHandler(handler);
        }
	}
	@Test
	public void noUriIdentifierNoType() throws JDOMException, IOException, InvalidElementException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\"><provider><course><dc:title>Course Test</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier>test-no-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: course: course contains a non-URI identifier with no type"));

        } finally {
            logger.removeHandler(handler);
        }
	}
	@Test
	public void goodIdentifiers() throws JDOMException, IOException, InvalidElementException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><dc:title>Course Test</dc:title><dc:subject>stuff</dc:subject><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.length() == 0);

        } finally {
            logger.removeHandler(handler);
        }
	}
	
	/**
	 * Title: Producers SHOULD include at least one title element for each course.
	 */
	@Test
	public void testTitle() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("course: course has no title"));

        } finally {
            logger.removeHandler(handler);
        }		
	}
	
	/**
	 * Subject: Producer SHOULD include at least one subject element for each course. 
	 * See the guidance on the subject element for more information.
	 */
	@Test
	public void testSubject() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("course: course does not contain a subject"));

        } finally {
            logger.removeHandler(handler);
        }		
	}
	@Test
	public void testSubjectInherited() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:subject>lots of stuff</dc:subject><course><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);
    		
    		assertEquals("lots of stuff", catalog.getProviders()[0].getSubjects()[0].getValue());
    		assertEquals("lots of stuff", catalog.getProviders()[0].getCourses()[0].getSubjects()[0].getValue());

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("course: course does not contain a subject"));

        } finally {
            logger.removeHandler(handler);
        }		
	}
	@Test
	public void testSubjectOK() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("lots of stuff", catalog.getProviders()[0].getSubjects()[0].getValue());
		assertEquals("stuff", catalog.getProviders()[0].getCourses()[0].getSubjects()[0].getValue());

	}
	
	/**
	 * Level: This is included for compatibility with [EN 15982]. 
	 * Producers SHOULD NOT use this element, and Aggregators SHOULD 
	 * make use of the Qualification and Credit elements to assist 
	 * users to discover courses by level.
	 * @throws InvalidElementException 
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	@Test
	public void testLevels() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Course.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
    		catalog.fromXml(document);


            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("course: level is not recommended"));

        } finally {
            logger.removeHandler(handler);
        }	
	}
	
	/**
	 * Credit: Producers SHOULD use separate credit elements for 
	 * describing the credits available under each scheme, for 
	 * example CATS or ECTS.
	 */
	//
	// TODO
	//
	
	/**
	 * Absence of Image: Where a course does not contain an image, 
	 * but its containing provider does, an Aggregator MAY use the 
	 * image of the provider when displaying the course.
	 */
	@Test
	public void testInheritImages() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("provider.png", catalog.getProviders()[0].getImages()[0].getSrc());
		assertEquals("provider.png", catalog.getProviders()[0].getCourses()[0].getImages()[0].getSrc());

	}
}

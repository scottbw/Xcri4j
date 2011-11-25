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
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.CommonType;

public class CommonTypeTest {
	
	/**
	 * date: Producers SHOULD NOT use the <date> element, but instead where 
	 * possible use the <start> element and the temporal elements defined 
	 * in this document: <end>, <applyFrom>, and <applyUntil>
	 */
	@Test
	public void date() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:date>Tuesday</dc:date><image src=\"provider.png\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("date: Producers SHOULD NOT use the <date> element, but instead where possible use the <start> element and the temporal elements defined in this document: <end>, <applyFrom>, and <applyUntil"));
        } finally {
            logger.removeHandler(handler);
        }	
	}
	
	/**
	 * hasPart/isPartOf: these elements are included for compatibility with 
	 * the [EN 15982] standard. Producers SHOULD NOT use these elements. For 
	 * more information on these elements see [EN 15982]
	 */
	@Test
	public void hasPartisPartOf() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:hasPart></dc:hasPart><dc:isPartOf></dc:isPartOf><image src=\"provider.png\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("hasPart/isPartOf: these elements are included for compatibility with the [EN 15982] standard. Producers SHOULD NOT use these elements"));
        } finally {
            logger.removeHandler(handler);
        }	
	}

	/**
	 * Contributor
	 *
	 * Contact Information Producers SHOULD NOT use this element for general contact 
	 * information; Producers SHOULD use the <location> element for this purpose.
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Contributor
	 * 
	 * Refinements Producers SHOULD use refinements of this element, for example 
	 * for "presenter" or "lecturer" or other contributor types relevant to 
	 * the type of course or presentation.
	 */
	@Test
	public void contributor() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:contributor>Me</dc:contributor><image src=\"provider.png\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("contributor : Producers SHOULD use refinements of this element, for example for \"presenter\" or \"lecturer\" or other contributor types relevant to  the type of course or presentation."));
        } finally {
            logger.removeHandler(handler);
        }	
	}
	
	/**
	 * Identifier
	 * 
	 * Resolvable URLs Producers SHOULD use URLs for identifiers that also resolve 
	 * to human-readable content.
	 * 
	 * Third-party identifiers: Producers SHOULD use identifier with an encoding 
	 * scheme to represent third-party identifiers; Producers SHOULD use dc:subject 
	 * to represent third-party codes that refer to multiple objects (e.g. subject 
	 * classification codes, provider type codes)
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Title
	 * 
	 * Localisation: Producers SHOULD use the xml:lang attribute to provide alternative 
	 * language versions of a title; there SHOULD NOT be more than one occurrence of 
	 * title per language tag.
	 */
	@Test
	public void titleLocalisation() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:title xml:lang='de'>Deutsche Name</dc:title><dc:title xml:lang='en'>Another English Name</dc:title><dc:title xml:lang='en'>English Name</dc:title><image src=\"provider.png\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("title : there SHOULD NOT be more than one occurrence of title per language tag."));
        } finally {
            logger.removeHandler(handler);
        }	
	}
	 
	/**
	 * Title
	 * 
	 * Qualfiications: In the qualification element, Producers SHOULD use title for the 
	 * name of the qualification, preferably as given by its Awarding Body.
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Subject
	 * 
	 * Identifier:: Where a subject has both a human-readable label and an identifier, 
	 * Producers SHOULD use an @identifier attribute of the subject element for the 
	 * identifier, and the text content of the subject element for the label.
	 * 
	 * Localisation: Producers SHOULD use an xml:lang attribute to provide alternative 
	 * language versions of a subject element.
	 * 
	 * Vocabularies: Producers SHOULD use vocabulary encoding schemes to include 
	 * classification terms using the subject element.
	 * 
	 * Inheritance: This element and any refinements of it is inheritable. See the 
	 * section on inheritance for more guidance.
	 */
	
	/**
	 * Type
	 * 
	 * Producers MAY use this element to classify providers, courses, qualifications 
	 * and presentations; additional guidance may be found under the sections on the 
	 * Provider, Course, Qualification and Presentation elements.
	 * 
	 * NOT TESTABLE
	 */

	/**
	 * Url
	 * 
	 * When to use Url versus Identifier: Producers SHOULD Use identifier with the 
	 * default encoding type in addition to the url element wherever possible. Also, 
	 * Producers SHOULD use this element when the resource cannot be given a 
	 * dereferencable URL as an identifier (for example, a course description generated 
	 * from within a CMS, or where the identifier is a URN or HDL) to indicate a place 
	 * on the provider's website where further information can be obtained, even if 
	 * it is just general information about the department offering the course.
	 * 
	 * NOT TESTABLE
	 */

	/**
	 * Image
	 * 
	 * Image size: An Aggregator MAY choose to re-scale images.
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Inheritance: This element and any refinements of it is inheritable. See the section 
	 * on inheritance for more guidance.
	 * 
	 * TESTED IN PresentationTest, CourseTest, ProviderTest
	 */
	
	/**
	 * Image
	 * 
	 * Image format: A Producer SHOULD offer images in standard formats, such as PNG and JPEG.
	 */
	@Test
	public void imageFormat() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.tiff\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("image : A Producer SHOULD offer images in standard formats, such as PNG and JPEG"));
        } finally {
            logger.removeHandler(handler);
        }	
	}	
	/**
	 * Image
	 * 
	 * Accessibility: While @alt is optional, following the structure of XHTML, a Producer 
	 * SHOULD provide meaningful alternative text.
	 */
	@Test
	public void imageNoAlt() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(CommonType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/></provider></catalog>"));
    		catalog.fromXml(document);
            handler.flush();
            String logMsg = out.toString();
            assertNotNull(logMsg);
            assertTrue(logMsg.contains("image: While @alt is optional, following the structure of XHTML, a Producer SHOULD provide meaningful alternative text"));
        } finally {
            logger.removeHandler(handler);
        }	
	}
}

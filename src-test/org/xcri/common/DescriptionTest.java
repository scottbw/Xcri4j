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
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.DescriptiveTextType;

public class DescriptionTest {
	
	/** 
	 * If a Descriptive Text Element has an @href attribute, the Producer 
	 * MUST NOT include any text content or child elements.
	 */ 
	@Test
	public void linkedDescription() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:description href=\"http://xcri.org/test\"></dc:description></provider></catalog>"));
		catalog.fromXml(document);
		
		assertEquals("http://xcri.org/test", catalog.getProviders()[0].getDescriptions()[0].getHref());
		assertEquals("", catalog.getProviders()[0].getDescriptions()[0].getValue());
		assertEquals("http://xcri.org/test", catalog.getProviders()[0].getDescriptions()[0].toXml().getAttributeValue("href"));	
		assertEquals("", catalog.getProviders()[0].getDescriptions()[0].toXml().getText());	
	}
	
	/**
	 * The content of a Descriptive Text Element MUST be one of either:
	 * Empty
	 * Plain unescaped text content
	 * Valid XHTML 1.0 content
	 */
	@Test
	public void xhtmlContent() throws JDOMException, IOException, InvalidElementException{
		String content = 
		"<div xmlns=\"http://www.w3.org/1999/xhtml\">" +
		"<p>This module shows how to take an existing presentation and modify it and how to create " +
		"your own presentations. No knowledge of PowerPoint is assumed.</p>" +
		"<p>The topics covered are:" +
		"<ul>" +
		"<li>Using and modifying an existing PowerPoint presentation</li> " +
		"<li>Creating a simple presentation</li> " +
		"<li>Making use of themes and schemes supplied with PowerPoint</li> " +
		"<li>The PowerPoint views</li> " +
		"<li>Printing and saving your presentation</li>" +
		"</ul>" +
		"</p> " +
		"</div>";
		
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\"><provider><dc:description>"+content+"</dc:description></provider></catalog>"));
		catalog.fromXml(document);
		
		assertEquals(content, catalog.getProviders()[0].getDescriptions()[0].getValue());
		assertNotNull(catalog.getProviders()[0].getDescriptions()[0].toXml().getChild("div", Namespaces.XHTML_NAMESPACE_NS));
	}
	
	/** 
	 * If a Descriptive Text Element has an @href attribute and the <description> 
	 * element contains either text content or child elements, an Aggregator MUST 
	 * treat the element as in error and ignore the element.
	 */
	@Test
	public void both() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:description href=\"http://xcri.org/test\">text content</dc:description></provider></catalog>"));
		catalog.fromXml(document);
		
		assertEquals(0, catalog.getProviders()[0].getDescriptions().length);
	}
	
	/**
	 * Encoding schemes: Use of vocabularies for types of Descriptive Text Elements is encouraged.
	 */ 
	
	/** 
	 * Use of images: Images SHOULD NOT be referenced from within the Descriptive Text Element 
	 * element by Producers as these are unlikely to be presented by Aggregators; potentially 
	 * an image tag can be used to execute cross-site scripting (XSS) attacks. Instead, any 
	 * image SHOULD be provided separately using the XCRI image element.
	 */
	
	/** 
	 * Length: Aggregators MAY choose to truncate long Descriptive Text Element content; a 
	 * suggested maximum length is 4000 characters.
	 */
	
	/** 
	 * Safe use of XHTML: See the section on Security Considerations for guidance.
	 * Aggregators SHOULD pay particular attention to the security of the IMG, SCRIPT, 
	 * EMBED, OBJECT, FRAME, FRAMESET, IFRAME, META, and LINK elements, but other 
	 * elements might also have negative security properties.
	 * @throws InvalidElementException 
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	@Test
	public void contentWarnings() throws JDOMException, IOException, InvalidElementException{
		
		assertTrue(hasContentWarning("<img/>"));
		assertTrue(hasContentWarning("<script>nasty code</script>"));
		assertTrue(hasContentWarning("<embed>nasty embed</embed>"));
		assertTrue(hasContentWarning("<object>nasty object</object>"));
		assertTrue(hasContentWarning("<frame>nasty thing</frame>"));
		assertTrue(hasContentWarning("<frameset>nasty thing</frameset>"));
		assertTrue(hasContentWarning("<meta>nasty thing</meta>"));
		assertTrue(hasContentWarning("<link>nasty thing</link>"));
		
	}
	private boolean hasContentWarning(String content) throws JDOMException, IOException, InvalidElementException{
        Logger logger = Logger.getLogger(DescriptiveTextType.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\"><dc:description><div xmlns=\"http://www.w3.org/1999/xhtml\">"+content+"</div></dc:description></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            if(logMsg.contains("description : content contains potentially dangerous XHTML")) return true;

        } finally {
            logger.removeHandler(handler);
        }
		
		return false;
	}
	@Test
	public void sanitize() throws JDOMException, IOException, InvalidElementException{
		String content = "<p>Hello World</p><script>nasty script</script><p>Goodbye</p>";
    	Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\"><dc:description><div xmlns=\"http://www.w3.org/1999/xhtml\">"+content+"</div></dc:description></catalog>"));
		catalog.fromXml(document);
		
		assertEquals("<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Hello World</p><p>Goodbye</p></div>", catalog.getDescriptions()[0].getValue());
	}
	
	/** 
	 * Inheritance: Descriptive Text Elements and any refinements are inheritable. See the 
	 * section on inheritance for more guidance.
	 * 
	 * TESTED IN OTHER CLASSES
	 */
	

}

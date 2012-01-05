package org.xcri.core.presentation;

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
import org.xcri.core.Presentation;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.presentation.Duration;

public class DurationTest {
	
	
	/**
	 * If the <duration> element has no text content, the <duration> element is in error and an Aggregator MUST ignore this element.
	 */
	@Test
	public void noText() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Presentation.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><mlo:duration interval=\"P3Y\"></mlo:duration></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("duration element has no text content"));

        } finally {
            logger.removeHandler(handler);
        }	
	}	
	
	/**
	 * If the <duration> element contains both text content and an @interval attribute, and the value of the @interval attribute is 
	 * NOT a duration-only time interval as specified by [ISO 8601], then an Aggregator MUST process the text content and ignore the interval attribute.
	 */
	@Test
	public void badInterval() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Duration.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><mlo:duration interval=\"banana\">banana</mlo:duration></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: duration : skipping invalid interval attribute"));

        } finally {
            logger.removeHandler(handler);
        }	
	}
	@Test
	public void intervalTest1() throws InvalidElementException, JDOMException, IOException{
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><mlo:duration interval=\"P3Y\">Three years</mlo:duration></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);
    		
    		assertEquals(3, catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getDuration().getInterval().getYears());
	}
	@Test
	public void intervalTest2() throws InvalidElementException, JDOMException, IOException{
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><mlo:duration interval=\"P3W\">Three weeks</mlo:duration></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);
    		
    		assertEquals(3, catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getDuration().getInterval().getWeeks());
	}
	
	/**
	 * Use of the interval attribute While the text content of the duration element is intended to contain a human-readable description of the duration 
	 * of the learning opportunity, the interval attribute can be used to provide a machine-readable equivalent. If there is no direct machine-equivalent 
	 * value (e.g., the duration is flexible), then Producers SHOULD NOT include the interval attribute.
	 */
	
}

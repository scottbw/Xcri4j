package org.xcri.core.presentation;

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

public class EndTest {
	
	@Test
	public void endNoText() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Presentation.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><end></end></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: presentation : skipping invalid end element: end: temporal element has no text content"));

        } finally {
            logger.removeHandler(handler);
        }	
	}
	@Test
	public void endInvalidDtf() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Presentation.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><presentation><end dtf=\"banana\">end</end></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("WARNING: presentation : skipping invalid end element: end: temporal element has @dtf attribute with invalid W3C-DTF date"));

        } finally {
            logger.removeHandler(handler);
        }	
	}

}

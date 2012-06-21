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
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.Test;
import org.xcri.core.Catalog;
import org.xcri.core.Provider;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.presentation.Venue;
import org.xcri.provider.Location;

public class VenueTest {
	
	@Test(expected=InvalidElementException.class)
	public void invalidContent() throws InvalidElementException{
		Venue venue = new Venue();
		Element element = new Element("venue");
		element.setText("no text allowed");
		venue.fromXml(element);
	}
	
	@Test(expected=InvalidElementException.class)
	public void noProvider() throws InvalidElementException{
		Venue venue = new Venue();
		Element element = new Element("venue");
		venue.fromXml(element);
	}
	
	@Test(expected=InvalidElementException.class)
	public void multipleProviders() throws InvalidElementException{
		Venue venue = new Venue();
		Element element = new Element("venue");
		Element provider = new Element("provider");
		element.addContent(provider);
		Element provider2 = new Element("provider");
		element.addContent(provider2);
		venue.fromXml(element);
	}
	
	@Test
	public void validContent() throws InvalidElementException{
		Venue venue = new Venue();
		Element element = new Element("venue");
		Element provider = new Element("provider");
		element.addContent(provider);
		venue.fromXml(element);
	}
	
	@Test
	public void validContentWithLocation() throws InvalidElementException{
		Venue venue = new Venue();
		Provider provider = new Provider();
		Location location = new Location();
		provider.setLocation(location);
		venue.setProvider(provider);
		Element element = venue.toXml();
		String out = new XMLOutputter().outputString(element);
		assertEquals("<xcri:venue xmlns:xcri=\"http://xcri.org/profiles/1.2/catalog\"><xcri:provider><mlo:location xmlns:mlo=\"http://purl.org/net/mlo\" /></xcri:provider></xcri:venue>", out);		
	}
	
	@Test
	public void noLocation() throws InvalidElementException, JDOMException, IOException{
        Logger logger = Logger.getLogger(Venue.class.getName());

        Formatter formatter = new SimpleFormatter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(out, formatter);
        logger.addHandler(handler);

        try {
        	Catalog catalog = new Catalog();
    		SAXBuilder builder = new SAXBuilder();
    		Document document = builder.build(new StringReader("<catalog><provider><course><presentation><venue><provider></provider></venue></presentation></course></provider></catalog>"));
    		catalog.fromXml(document);

            handler.flush();
            String logMsg = out.toString();

            assertNotNull(logMsg);
            assertTrue(logMsg.contains("Location: When a provider element is used in a venue element, Producers SHOULD include a location element."));

        } finally {
            logger.removeHandler(handler);
        }		
	}
	
	

}

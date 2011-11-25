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

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;

public class ContributorTest {
	
	@Test
	public void contributor() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:contributor>Bob</dc:contributor></provider></catalog>"));
		catalog.fromXml(document);
		assertEquals("Bob", catalog.getProviders()[0].getContributors()[0].getValue());
	}

	@Test
	public void contributorRefinement() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:contributor xsi:type=\"ext:lecturer\">Bob</dc:contributor></provider></catalog>"));
		catalog.fromXml(document);
		assertEquals("Bob", catalog.getProviders()[0].getContributors()[0].getValue());
		assertEquals("ext:lecturer", catalog.getProviders()[0].getContributors()[0].getType());
	}
	
	@Test
	public void contributorOutput() throws JDOMException, IOException, InvalidElementException{
		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><dc:contributor xsi:type=\"ext:lecturer\">Bob</dc:contributor></provider></catalog>"));
		catalog.fromXml(document);
		assertEquals("Bob", catalog.getProviders()[0].getContributors()[0].getValue());
		assertEquals("ext:lecturer", catalog.getProviders()[0].getContributors()[0].getType());	
		
		Element element = catalog.getProviders()[0].getContributors()[0].toXml();
		assertEquals("Bob", element.getText());
		assertEquals(Namespaces.DC_NAMESPACE_NS, element.getNamespace());
		
		element = catalog.getProviders()[0].toXml();
		assertEquals("Bob", element.getChild("contributor", Namespaces.DC_NAMESPACE_NS).getText());
	}
}

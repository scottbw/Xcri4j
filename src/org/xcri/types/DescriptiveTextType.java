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
package org.xcri.types;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.jdom.CDATA;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xcri.Namespaces;
import org.xcri.ParserConfiguration;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.util.ContentSecurityFilter;
import org.xcri.util.lax.Lax;

public class DescriptiveTextType extends XcriElement{

	private Log log = LogFactory.getLog(DescriptiveTextType.class);

	private String href;
	private boolean isXhtml = false;
	private Element xhtml = null;
	
	

	/**
	 * @return the xhtml
	 */
	public Element getXhtml() {
		return xhtml;
	}

	/**
	 * @param xhtml the xhtml to set
	 */
	public void setXhtml(Element xhtml) {
		this.xhtml = xhtml;
	}

	/**
	 * @return the isXhtml
	 */
	public boolean isXhtml() {
		return isXhtml;
	}

	/**
	 * @param isXhtml the isXhtml to set
	 */
	public void setIsXhtml(boolean isXhtml) {
		this.isXhtml = isXhtml;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/* (non-Javadoc)
	 * @see org.xcri.Localized#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		//
		// Add XHTML content if present
		//
		Element div = Lax.getChildQuietly(element, "div", Namespaces.XHTML_NAMESPACE_NS, log);
		if (div != null){
			div = (Element) div.detach();
			processXhtml(div);
		}
		
		//
		// How about some CDATA with HTML in it?
		//
		for (Object child: element.getContent()){
			if (child instanceof CDATA){
				if (ParserConfiguration.getInstance().fixCDATA()){
				log.warn("description: uses CDATA instead of XHTML. Attempting to convert inline HTML into XHTML.");
				processCDATA((CDATA)child);
				} else {
					throw new InvalidElementException("description: contains CDATA. To allow CDATA to be converted to XHTML, run with fixCDATA=true");
				}
			}
		}
		
		//
		// Add HREF
		//
		this.setHref(element.getAttributeValue("href"));

		//
		// Cannot have both linked and inline content
		//
		if ((this.getValue()!=null && this.getValue().length()>0) || this.xhtml != null){
			if(this.getHref()!= null && this.getHref().length()>0){
				throw new InvalidElementException("Description contains both text content and href attribute; only one or the other may be used");
			}
		}
	}
	
	/**
	 * Attempt to process a CDATA section as HTML, and convert to XHTML
	 * @param cdata
	 * @throws InvalidElementException
	 */
	private void processCDATA(CDATA cdata) throws InvalidElementException{
		
		//
		// Use HTMLCleaner to clean the content
		//
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(cdata.getValue());
		
		//
		// Output the HTML as XML, and read in using JDOM
		//
		PrettyXmlSerializer ser = new PrettyXmlSerializer(cleaner.getProperties());
		try {
			String out = ser.getAsString(node);
			Element element = new Element("div", Namespaces.XHTML_NAMESPACE_NS);
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader(out));
			
			//
			// Add all children of <body> to a new <div> element
			//
			@SuppressWarnings("unchecked")
			List<Content> li = document.getRootElement().getChild("body").getChildren();
			for(int i=0;i<li.size();i++){
				Element next = (Element)li.get(i);
				next.detach();
				next.setNamespace(Namespaces.XHTML_NAMESPACE_NS);
				element.addContent(next);
			}

			//
			// Finally, add any raw text
			//
			element.addContent(document.getRootElement().getChild("body").getTextTrim());	
			
			//
			// Post-process the XHTML
			//
			processXhtml(element);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void processXhtml(Element element) throws InvalidElementException{
		try {
			//
			// Check for potentially dangerous elements
			//
			@SuppressWarnings("rawtypes")
			Iterator i = element.getDescendants(new ContentSecurityFilter());
			ArrayList<Element> toRemove = new ArrayList<Element>();
			while (i.hasNext()){
				Element dangerousElement = (Element) i.next();
				toRemove.add(dangerousElement);
				log.warn("description : content contains potentially dangerous XHTML :"+dangerousElement.getName());
			}
			
			//
			// If configured to do so, remove elements
			//
			if (ParserConfiguration.getInstance().sanitizeXHTML()){
				for (Element dangerousElement: toRemove){
					log.warn("description : removing XHTML element :"+dangerousElement.getName());
					dangerousElement.getParentElement().removeContent(dangerousElement);					
				}
			}
			
			xhtml = element;
			isXhtml = true;
			
			//
			// Set value to text output of XML
			//
			this.setValue(new XMLOutputter().outputString(xhtml));

		} catch (Exception e) {
			throw new InvalidElementException("Error reading content of description element");
		}
	}

	/* (non-Javadoc)
	 * @see org.xcri.Localized#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getHref() != null) element.setAttribute("href", this.getHref());
		if (isXhtml){
			//
			// Remove plain content
			//
			element.removeContent();
			//
			// Add XHTML
			//
			element.addContent(xhtml.detach());
		}
		return element;
	}



}

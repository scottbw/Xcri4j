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
package org.xcri.util.lax;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.jdom.Element;
import org.jdom.Namespace;

public class Lax {
	
	/**
	 * Fetch child elements and log any warnings to the specified Log instance
	 * @param parentElement
	 * @param childElementName
	 * @param preferredNamespace
	 * @param log
	 * @return
	 */
	public static List<Element> getChildrenQuietly(Element parentElement, String childElementName, Namespace preferredNamespace, Log log){
		List<Element> elements;
		try {
			elements = Lax.getChildren(parentElement, childElementName, preferredNamespace);
			return elements;
		} catch (LaxException e) {
			elements = e.getElements();
			if (e.isMisspelled()) log.warn("elements uses incorrect name:"+childElementName);
			if (e.isIncorrectNamespace()) log.warn("elements use incorrect namespace:"+elements.get(0).getNamespaceURI());
			return elements;
		}
	}
	
	public static Element getChildQuietly(Element parentElement, String childElementName, Namespace preferredNamespace, Log log){
		try {
			return Lax.getChild(parentElement, childElementName, preferredNamespace);
		} catch (SingleElementException e) {
			log.warn("multiple '"+childElementName+"' child elements returned instead of a single element; ignoring all but the first child element found");
			return e.getElements().get(0);
		}
	}
	
	public static List<Element> getChildren(Element parentElement, String childElementName, Namespace preferredNamespace) throws LaxException{
		return getAllChildren(parentElement, childElementName, preferredNamespace);
	}

	public static Element getChild(Element parentElement, String childElementName, Namespace preferredNamespace) throws SingleElementException{
		List<Element> children;
		try {
			children = getChildren(parentElement, childElementName,preferredNamespace);
		} catch (LaxException e) {
			children = e.getElements();
		}
		
		if (children.size() > 1){
			throw new SingleElementException(children);
		}
		if (children.size() == 1){
			return children.get(0);
		}
		return null;
	}
	
	/**
	 * Get specified child elements as generously as possible
	 * 
	 * @param parentElement
	 * @param childElementName
	 * @param preferredNamespace
	 * @return
	 * @throws WrongNamespaceException
	 * @throws ElementNameFormattingException
	 */
	@SuppressWarnings("unchecked")
	private static List<Element> getAllChildren(Element parentElement, String childElementName, Namespace preferredNamespace) throws LaxException {
		
		boolean misspelled = false;
		boolean wrongNamespace = false;
		
		LinkedList<Element> list = new LinkedList<Element>();
		List<Element> allChildren = parentElement.getChildren();
		Iterator<Element> iter = allChildren.iterator();
		while(iter.hasNext()){
			org.jdom.Element nextElement = (org.jdom.Element)iter.next();
			if(nextElement.getName().equals(childElementName)){
				list.add(nextElement);
				if (!nextElement.getNamespace().equals(preferredNamespace)){
					wrongNamespace = true;
				}
			} else if(nextElement.getName().compareToIgnoreCase(childElementName) == 0){
				list.add(nextElement);
				misspelled = true;
			}
		}
		if(misspelled || wrongNamespace){
			LaxException ex = new LaxException(list);
			ex.setMisspelled(misspelled);
			ex.setIncorrectNamespace(wrongNamespace);
			throw ex;
		}

		return list;
	}

}

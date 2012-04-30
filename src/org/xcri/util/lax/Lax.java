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
		} catch (WrongNamespaceException e) {
			elements = e.getElements();
			log.warn("catalog: provider elements use incorrect namespace");
			return elements;
		}
	}
	
	public static Element getChildQuietly(Element parentElement, String childElementName, Namespace preferredNamespace, Log log){
		try {
			return Lax.getChild(parentElement, childElementName, preferredNamespace);
		} catch (WrongNamespaceException e) {
			return e.getElements().get(0);
		} catch (SingleElementException e) {
			return e.getElements().get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Element> getChildren(Element parentElement, String childElementName, Namespace preferredNamespace) throws WrongNamespaceException{
		List<Element> children = parentElement.getChildren(childElementName,preferredNamespace);
		
		if (children == null || children.size() == 0) {
			children = parentElement.getChildren(childElementName);
			
			if (children.size() > 0){
				throw new WrongNamespaceException(children);
			}
			
		}
		return children;
	}

	@SuppressWarnings("unchecked")
	public static Element getChild(Element parentElement, String childElementName, Namespace preferredNamespace) throws WrongNamespaceException, SingleElementException{
		List<Element> children;
		try {
			children = getChildren(parentElement, childElementName,preferredNamespace);
		} catch (WrongNamespaceException e) {
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
}

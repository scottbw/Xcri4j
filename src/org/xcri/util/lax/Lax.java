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

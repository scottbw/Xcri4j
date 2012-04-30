/**
 * 
 */
package org.xcri.util.lax;

import java.util.List;

import org.jdom.Element;

/**
 * An exception when processing an element, where we capture
 * the returned elements irrespective of the error, for example
 * where the child elements use an incorrect namespace or have
 * the wrong multiplicity.
 */
public class LaxException extends Exception {
	
	private static final long serialVersionUID = 79468947079793688L;
	
	private List<Element> elements;

	public LaxException(List<Element> elements) {
		this.elements = elements;
	}
	
	public List<Element> getElements(){
		return elements;
	}


}

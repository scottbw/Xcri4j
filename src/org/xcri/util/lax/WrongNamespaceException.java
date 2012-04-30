/**
 * 
 */
package org.xcri.util.lax;

import java.util.List;

import org.jdom.Element;

/**
 * @author scottw
 *
 */
public class WrongNamespaceException extends LaxException {

	public WrongNamespaceException(List<Element> elements) {
		super(elements);
	}


}

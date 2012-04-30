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
public class SingleElementException extends LaxException {

	public SingleElementException(List<Element> elements) {
		super(elements);
	}

}

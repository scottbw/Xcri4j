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
	
	private boolean misspelled;
	private boolean incorrectNamespace;

	public LaxException(List<Element> elements) {
		this.elements = elements;
	}
	
	public List<Element> getElements(){
		return elements;
	}

	/**
	 * @return the misspelled
	 */
	public boolean isMisspelled() {
		return misspelled;
	}

	/**
	 * @param misspelled the misspelled to set
	 */
	public void setMisspelled(boolean misspelled) {
		this.misspelled = misspelled;
	}

	/**
	 * @return the incorrectNamespace
	 */
	public boolean isIncorrectNamespace() {
		return incorrectNamespace;
	}

	/**
	 * @param incorrectNamespace the incorrectNamespace to set
	 */
	public void setIncorrectNamespace(boolean incorrectNamespace) {
		this.incorrectNamespace = incorrectNamespace;
	}
	
	


}

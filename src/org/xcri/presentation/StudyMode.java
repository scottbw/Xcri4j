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
package org.xcri.presentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.XcriElement;

public class StudyMode extends XcriElement {
	
	private Log log = LogFactory.getLog(StudyMode.class);
	
	public enum StudyModeType {
		NK,
		FL,
		PT,
		PF,
		FT
	}
	
	public StudyModeType getStudyModeType(){
		try {
			return StudyModeType.valueOf(getIdentifier());
		} catch (Exception e) {
			return null;
		}
	}
	
	private String identifier;


	@Override
	public Namespace getNamespace() {
		return Namespaces.XCRI_NAMESPACE_NS;
	}

	@Override
	public String getName() {
		return "studyMode";
	}

	@Override
	public Element toXml() {
		return super.toXml();
	}

	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		/**
		 * Recommended Values: Producers SHOULD use the following values for this element, with the two-letter code used in the @identifier attribute, and the label in the element content:
		 * NK Not known [DEFAULT] The provider has not supplied the information.
		 * FL Flexible Could be full time or part time dependent on the learner.
		 * FT Full time The learning opportunity is the learner's main activity.
		 * PF Part of a full time programme The learning opportunity is a component of a set of learning opportunities that form the learner's main activity.
		 * PT Part time The learning opportunity is not the learner's main activity
		 * Note: These are mutually exclusive terms, so 'Full time' does not include 'Part of a full time programme'.
		 */
		String identifier = element.getAttributeValue("identifier");
		if (identifier != null){
			this.setIdentifier(identifier);
			if (this.getStudyModeType() == null){
				log.warn("StudyMode : identifier (\""+identifier+"\") is not a member of the recommended vocabulary");
			}
		}
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	

}

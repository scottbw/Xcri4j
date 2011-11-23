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

package org.xcri.course;

import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.CommonType;

public class Qualification extends CommonType {

	private String abbr;
	private String awardedBy;
	private String accreditedBy;
	private String educationLevel;
	
	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if(this.getAbbr()!=null){
			Element abbr = new Element("abbr", Namespaces.XCRI_NAMESPACE_NS);
			abbr.setText(this.getAbbr());
			element.addContent(abbr);
		}
		if(this.getEducationLevel()!=null){
			Element educationLevel = new Element("educationLevel", Namespaces.DCTerms_NAMESPACE_NS);
			educationLevel.setText(this.getEducationLevel());
			element.addContent(educationLevel);
		}
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		if (element.getChild("abbr", Namespaces.XCRI_NAMESPACE_NS) != null) this.setAbbr(element.getChild("abbr", Namespaces.XCRI_NAMESPACE_NS).getText());
		if (element.getChild("awardedBy", Namespaces.XCRI_NAMESPACE_NS) != null) this.setAwardedBy(element.getChild("awardedBy", Namespaces.XCRI_NAMESPACE_NS).getText());
		if (element.getChild("accreditedBy", Namespaces.XCRI_NAMESPACE_NS) != null) this.setAccreditedBy(element.getChild("accreditedBy", Namespaces.XCRI_NAMESPACE_NS).getText());
		if (element.getChild("educationLevel", Namespaces.DCTerms_NAMESPACE_NS) != null) this.setEducationLevel(element.getChild("educationLevel", Namespaces.DCTerms_NAMESPACE_NS).getText());
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.MLO_NAMESPACE_NS;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "qualification";
	}

	/**
	 * @return the abbr
	 */
	public String getAbbr() {
		return abbr;
	}

	/**
	 * @param abbr the abbr to set
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/**
	 * @return the awardedBy
	 */
	public String getAwardedBy() {
		return awardedBy;
	}

	/**
	 * @param awardedBy the awardedBy to set
	 */
	public void setAwardedBy(String awardedBy) {
		this.awardedBy = awardedBy;
	}

	/**
	 * @return the accreditedBy
	 */
	public String getAccreditedBy() {
		return accreditedBy;
	}

	/**
	 * @param accreditedBy the accreditedBy to set
	 */
	public void setAccreditedBy(String accreditedBy) {
		this.accreditedBy = accreditedBy;
	}

	/**
	 * @return the educationLevel
	 */
	public String getEducationLevel() {
		return educationLevel;
	}

	/**
	 * @param educationLevel the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	
	

}

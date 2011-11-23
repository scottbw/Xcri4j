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
package org.xcri.core;

import java.util.ArrayList;

import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.course.Qualification;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.CommonDescriptiveType;

public class Course extends CommonDescriptiveType{
	
	private Presentation[] presentations;
	private Qualification[] qualifications;

	/**
	 * @return the presentations
	 */
	public Presentation[] getPresentations() {
		return presentations;
	}

	/**
	 * @param presentations the presentations to set
	 */
	public void setPresentations(Presentation[] presentations) {
		this.presentations = presentations;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getPresentations()!= null) for (Presentation presentation:this.getPresentations()) element.addContent(presentation.toXml());
		if (this.getQualifications()!= null) for (Qualification qualification:this.getQualifications()) element.addContent(qualification.toXml());
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		//
		// Add children
		//
		ArrayList<Presentation> presentations = new ArrayList<Presentation>();
		for (Object obj : element.getChildren("presentation", Namespaces.XCRI_NAMESPACE_NS)){
			Presentation presentation = new Presentation();
			try {
				presentation.fromXml((Element)obj);
				presentations.add(presentation);
			} catch (InvalidElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setPresentations(presentations.toArray(new Presentation[presentations.size()]));
		
		ArrayList<Qualification> qualifications = new ArrayList<Qualification>();
		for (Object obj : element.getChildren("qualification", Namespaces.MLO_NAMESPACE_NS)){
			Qualification qualification = new Qualification();
			try {
				qualification.fromXml((Element)obj);
				qualifications.add(qualification);
			} catch (InvalidElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setQualifications(qualifications.toArray(new Qualification[qualifications.size()]));
		
		

		
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.XCRI_NAMESPACE_NS;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "course";
	}

	/**
	 * @return the qualifications
	 */
	public Qualification[] getQualifications() {
		return qualifications;
	}

	/**
	 * @param qualifications the qualifications to set
	 */
	public void setQualifications(Qualification[] qualifications) {
		this.qualifications = qualifications;
	}
	
	

}

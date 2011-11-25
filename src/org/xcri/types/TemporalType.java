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
package org.xcri.types;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.jdom.Element;
import org.xcri.exceptions.InvalidElementException;

public class TemporalType  extends XcriElement {
	
	private Date dtf;

	/**
	 * @return the dtf
	 */
	public Date getDtf() {
		return dtf;
	}

	/**
	 * @param dtf the dtf to set
	 */
	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		Calendar dtf = Calendar.getInstance();
		dtf.setTime(this.getDtf());
		if (this.getDtf() != null) element.setAttribute("dtf", DatatypeConverter.printDateTime(dtf));
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		/*
		 * If a Temporal Element does not contain text content, an Aggregator MUST treat the element as in error and ignore the element.
		 */
		if (this.getValue() == null || this.getValue().trim().length() == 0) throw new InvalidElementException(this.getName()+": temporal element has no text content");
		
		if (element.getAttribute("dtf") != null){
			try {
				Date date = new Date();
				date = DatatypeConverter.parseDateTime(element.getAttributeValue("dtf")).getTime();
				this.setDtf(date);
			} catch (Exception e) {
				/* 
				 * If a Temporal Element has a @dtf attribute, and the value of the attribute does not contain a valid date or time according 
				 * to [W3C-DTF], an Aggregator MUST treat the element as in error and ignore the element.
				 */
				throw new InvalidElementException(this.getName()+": temporal element has @dtf attribute with invalid W3C-DTF date");
			}
		}
	}
	
	

}

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
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.XcriElement;

public class Duration extends XcriElement{
	
	private Log log = LogFactory.getLog(Duration.class);
	
	private Period interval;
	
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
		return "duration";
	}

	/**
	 * @return the interval
	 */
	public Period getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(Period interval) {
		this.interval = interval;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getInterval() != null) element.setAttribute("interval", this.getInterval().toString(ISOPeriodFormat.standard()));
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		/**
		 * If the <duration> element has no text content, the <duration> element is in error and an Aggregator MUST ignore this element.
		 */
		if (this.getValue() == null || this.getValue().trim().length()==0){
			throw new InvalidElementException("duration element has no text content");
		}
		
		/**
		 * If the <duration> element contains both text content and an @interval attribute, and the value of the @interval attribute is 
		 * NOT a duration-only time interval as specified by [ISO 8601], then an Aggregator MUST process the text content and ignore the interval attribute.
		 */
		String intervalString = element.getAttributeValue("interval");
		if (intervalString != null){
			PeriodFormatter fmt = ISOPeriodFormat.standard();
			Period interval;
			try {
				interval = fmt.parsePeriod(intervalString);
				this.setInterval(interval);
			} catch (Exception e) {
				log.warn("duration : skipping invalid interval attribute");
			}
		}
		
		
	}
	
	

}

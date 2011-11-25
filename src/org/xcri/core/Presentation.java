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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.common.Title;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.presentation.End;
import org.xcri.presentation.Start;
import org.xcri.types.CommonDescriptiveType;
import org.xcri.types.CommonType;

public class Presentation extends CommonDescriptiveType {
	
	private Log log = LogFactory.getLog(Presentation.class);
	
	private Start start;
	private End end;

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getStart() != null) element.addContent(this.getStart().toXml());
		if (this.getEnd() != null) element.addContent(this.getEnd().toXml());
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		if (element.getChild("start", Namespaces.MLO_NAMESPACE_NS)!=null){
			Start start = new Start();
			try {
				start.fromXml(element.getChild("start", Namespaces.MLO_NAMESPACE_NS));
				this.setStart(start);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid start element: "+e.getMessage());
			}
		}
		if (element.getChild("end", Namespaces.XCRI_NAMESPACE_NS)!=null){
			End end = new End();
			try {
				end.fromXml(element.getChild("end", Namespaces.XCRI_NAMESPACE_NS));
				this.setEnd(end);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid end element: "+e.getMessage());
			}
		}
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
		return "presentation";
	}

	/**
	 * @return the start
	 */
	public Start getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Start start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public End getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(End end) {
		this.end = end;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#getTitles()
	 */
	@Override
	public Title[] getTitles() {
		if (titles == null || titles.length == 0){
			if (this.getParent() instanceof CommonType){
				return ((CommonType)this.getParent()).getTitles();
			}
		} 
		return titles;
	}
	
	

}

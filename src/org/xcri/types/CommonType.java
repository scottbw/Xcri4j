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

import java.util.ArrayList;

import org.jdom.Element;
import org.xcri.Namespaces;
import org.xcri.common.*;

public abstract class CommonType extends XcriElement {
	
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#toXml()
	 */
	@Override
	public Element toXml() {
		
		Element element = super.toXml();
		if (this.getContributors() != null) for (Contributor c: this.getContributors()) element.addContent(c.toXml());
		if (this.getDescriptions() != null) for (Description d: this.getDescriptions()) element.addContent(d.toXml());
		if (this.getIdentifiers() != null) for (Identifier i: this.getIdentifiers()) element.addContent(i.toXml());
		if (this.getTitles() != null) for (Title t: this.getTitles()) element.addContent(t.toXml());
		if (this.getSubjects() != null) for (Subject s: this.getSubjects()) element.addContent(s.toXml());
		if (this.getImages() != null) for (Image i: this.getImages()) element.addContent(i.toXml());
		if (this.getTypes() != null) for (Type t: this.getTypes()) element.addContent(t.toXml());
		if (this.getUrls() != null) for (Url u: this.getUrls()) element.addContent(u.toXml());
		return element;
	}
	
	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) {
		super.fromXml(element);
		
		// Process child elements
		
		ArrayList<Contributor> contributors = new ArrayList<Contributor>();
		for (Object obj: element.getChildren("contributor", Namespaces.DC_NAMESPACE_NS)){
			Contributor contributor = new Contributor();
			contributor.fromXml((Element)obj);
			contributors.add(contributor);
		}
		this.setContributors(contributors.toArray(new Contributor[contributors.size()]));
		
		ArrayList<Description> descriptions = new ArrayList<Description>();
		for (Object obj: element.getChildren("description", Namespaces.DC_NAMESPACE_NS)){
			Description description = new Description();
			description.fromXml((Element)obj);
			descriptions.add(description);
		}
		this.setDescriptions(descriptions.toArray(new Description[descriptions.size()]));
		
		ArrayList<Identifier> identifiers = new ArrayList<Identifier>();
		for (Object obj: element.getChildren("identifier", Namespaces.DC_NAMESPACE_NS)){
			Identifier identifier = new Identifier();
			identifier.fromXml((Element)obj);
			identifiers.add(identifier);
		}
		this.setIdentifiers(identifiers.toArray(new Identifier[identifiers.size()]));
		
		ArrayList<Title> titles = new ArrayList<Title>();
		for (Object obj: element.getChildren("title", Namespaces.DC_NAMESPACE_NS)){
			Title title = new Title();
			title.fromXml((Element)obj);
			titles.add(title);
		}
		this.setTitles(titles.toArray(new Title[titles.size()]));
		
	}
	
	
	private Contributor[] contributors;
	private Description[] descriptions;
	private Identifier[] identifiers;
	private Title[] titles;
	private Subject[] subjects;
	private Image[] images;
	private Type[] types;
	private Url[] urls;
	
	/**
	 * @return the contributors
	 */
	public Contributor[] getContributors() {
		return contributors;
	}
	/**
	 * @param contributors the contributors to set
	 */
	public void setContributors(Contributor[] contributors) {
		this.contributors = contributors;
	}
	/**
	 * @return the descriptions
	 */
	public Description[] getDescriptions() {
		return descriptions;
	}
	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(Description[] descriptions) {
		this.descriptions = descriptions;
	}
	/**
	 * @return the identifiers
	 */
	public Identifier[] getIdentifiers() {
		return identifiers;
	}
	/**
	 * @param identifiers the identifiers to set
	 */
	public void setIdentifiers(Identifier[] identifiers) {
		this.identifiers = identifiers;
	}
	/**
	 * @return the titles
	 */
	public Title[] getTitles() {
		return titles;
	}
	/**
	 * @param titles the titles to set
	 */
	public void setTitles(Title[] titles) {
		this.titles = titles;
	}
	/**
	 * @return the subjects
	 */
	public Subject[] getSubjects() {
		return subjects;
	}
	/**
	 * @param subjects the subjects to set
	 */
	public void setSubjects(Subject[] subjects) {
		this.subjects = subjects;
	}
	/**
	 * @return the images
	 */
	public Image[] getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(Image[] images) {
		this.images = images;
	}
	/**
	 * @return the types
	 */
	public Type[] getTypes() {
		return types;
	}
	/**
	 * @param types the types to set
	 */
	public void setTypes(Type[] types) {
		this.types = types;
	}
	/**
	 * @return the urls 
	 */
	public Url[] getUrls() {
		return urls;
	}
	/**
	 * @param urls the urls to set
	 */
	public void setUrls(Url[] urls) {
		this.urls = urls;
	}
	
	
}

/**
 * Copyright (c) 2011-2012 University of Bolton
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.common.Title;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.presentation.Age;
import org.xcri.presentation.ApplyFrom;
import org.xcri.presentation.ApplyTo;
import org.xcri.presentation.ApplyUntil;
import org.xcri.presentation.AttendanceMode;
import org.xcri.presentation.AttendancePattern;
import org.xcri.presentation.Cost;
import org.xcri.presentation.Duration;
import org.xcri.presentation.End;
import org.xcri.presentation.Engagement;
import org.xcri.presentation.LanguageOfAssessment;
import org.xcri.presentation.LanguageOfInstruction;
import org.xcri.presentation.Places;
import org.xcri.presentation.Start;
import org.xcri.presentation.StudyMode;
import org.xcri.presentation.Venue;
import org.xcri.types.CommonDescriptiveType;
import org.xcri.types.CommonType;
import org.xcri.util.lax.Lax;

public class Presentation extends CommonDescriptiveType {
	
	private Log log = LogFactory.getLog(Presentation.class);
	
	private Start start;
	private End end;
	private Duration duration;
	private ApplyFrom applyFrom;
	private ApplyUntil applyUntil;
	private ApplyTo applyTo;
	private Engagement[] engagements;
	private StudyMode studyMode;
	private AttendanceMode attendanceMode;
	private AttendancePattern attendancePattern;
	private LanguageOfInstruction[] languageOfInstruction;
	private LanguageOfAssessment[] languageOfAssessment;
	private Places places;
	private Cost cost;
	private Age age;
	private Venue[] venues;

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		if (this.getStart() != null) element.addContent(this.getStart().toXml());
		if (this.getEnd() != null) element.addContent(this.getEnd().toXml());
		if (this.getDuration() != null) element.addContent(this.getDuration().toXml());
		if (this.getApplyFrom() != null) element.addContent(this.getApplyFrom().toXml());
		if (this.getApplyUntil() != null) element.addContent(this.getApplyUntil().toXml());
		if (this.getApplyTo() != null) element.addContent(this.getApplyTo().toXml());
		// TODO Engagement
		if (this.getStudyMode() != null) element.addContent(this.getStudyMode().toXml());
		if (this.getAttendanceMode() != null) element.addContent(this.getAttendanceMode().toXml());
		if (this.getAttendancePattern() != null) element.addContent(this.getAttendancePattern().toXml());
		if (this.getLanguageOfInstruction() != null){
			for (LanguageOfInstruction lang: this.getLanguageOfInstruction()){
				element.addContent(lang.toXml());
			}
		}
		if (this.getLanguageOfAssessment() != null){
			for (LanguageOfAssessment lang: this.getLanguageOfAssessment()){
				element.addContent(lang.toXml());
			}
		}
		if (this.getPlaces() != null) element.addContent(this.getPlaces().toXml());
		if (this.getCost() != null) element.addContent(this.getCost().toXml());
		if (this.getAge() != null) element.addContent(this.getAge().toXml());
		
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		if (Lax.getChildQuietly(element, "start", Namespaces.MLO_NAMESPACE_NS, log) !=null){
			Start start = new Start();
			try {
				start.fromXml(Lax.getChildQuietly(element, "start", Namespaces.MLO_NAMESPACE_NS, log));
				this.setStart(start);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid start element: "+e.getMessage());
			}
		}
		if (Lax.getChildQuietly(element, "end", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			End end = new End();
			try {
				end.fromXml(Lax.getChildQuietly(element, "end", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setEnd(end);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid end element: "+e.getMessage());
			}
		}
		if (Lax.getChildQuietly(element, "duration", Namespaces.MLO_NAMESPACE_NS, log)!=null){
			Duration duration = new Duration();
			try {
				duration.fromXml(Lax.getChildQuietly(element, "duration", Namespaces.MLO_NAMESPACE_NS, log));
				this.setDuration(duration);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid duration element: "+e.getMessage());
			}
		}
		/**
		 * Start dates : A Producer SHOULD include a start element even if there is no specific 
		 * start date as this can still be used to describe the start details- see the section on 
		 * Temporal Elements.
		 */
		if (this.getStart() == null){
			log.warn("presentation : A Producer SHOULD include a start element");
		}
		
		/**
		 *  Duration: A Producer SHOULD include a duration element or start and end dates, or both.
		 */
		if (this.getDuration() == null && this.getStart() == null && this.getEnd() == null){
			log.warn("presentation : A Producer SHOULD include a duration element or start and end dates, or both.");
		}
		
		if (Lax.getChildQuietly(element, "applyFrom", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			ApplyFrom applyFrom = new ApplyFrom();
			try {
				applyFrom.fromXml(Lax.getChildQuietly(element, "applyFrom", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setApplyFrom(applyFrom);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid applyFrom element: "+e.getMessage());
			}
		}
		
		if (Lax.getChildQuietly(element, "applyUntil", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			ApplyUntil applyUntil = new ApplyUntil();
			try {
				applyFrom.fromXml(Lax.getChildQuietly(element, "applyUntil", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setApplyUntil(applyUntil);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid applyUntil element: "+e.getMessage());
			}
		}
		
		
		if (Lax.getChildQuietly(element, "applyTo", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			ApplyTo applyTo = new ApplyTo();
			try {
				applyFrom.fromXml(Lax.getChildQuietly(element, "applyTo", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setApplyTo(applyTo);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid applyTo element: "+e.getMessage());
			}
		}
		
		// TODO engagement
		
		if (Lax.getChildQuietly(element, "studyMode", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			StudyMode studyMode = new StudyMode();
			try {
				studyMode.fromXml(Lax.getChildQuietly(element, "studyMode", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setStudyMode(studyMode);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid studyMode element: "+e.getMessage());
			}
		}
		if (Lax.getChildQuietly(element, "attendanceMode", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			AttendanceMode attendanceMode = new AttendanceMode();
			try {
				attendanceMode.fromXml(Lax.getChildQuietly(element, "attendanceMode", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setAttendanceMode(attendanceMode);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid attendanceMode element: "+e.getMessage());
			}
		}
		if (Lax.getChildQuietly(element, "attendancePattern", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			AttendancePattern attendancePattern = new AttendancePattern();
			try {
				attendancePattern.fromXml(Lax.getChildQuietly(element, "attendancePattern", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setAttendancePattern(attendancePattern);
			} catch (InvalidElementException e) {
				log.warn("presentation : skipping invalid attendancePattern element: "+e.getMessage());
			}
		}
		if (Lax.getChildrenQuietly(element, "languageOfInstruction", Namespaces.MLO_NAMESPACE_NS, log)!=null){
			ArrayList<LanguageOfInstruction> languages = new ArrayList<LanguageOfInstruction>();
			for (Element langElement: Lax.getChildrenQuietly(element, "languageOfInstruction", Namespaces.MLO_NAMESPACE_NS, log)){
				LanguageOfInstruction lang = new LanguageOfInstruction();
				lang.fromXml(langElement);
				languages.add(lang);
			}
			this.setLanguageOfInstruction(languages.toArray(new LanguageOfInstruction[languages.size()]));
		}
		if (Lax.getChildrenQuietly(element, "languageOfAssessment", Namespaces.MLO_NAMESPACE_NS, log)!=null){
			ArrayList<LanguageOfAssessment> languages = new ArrayList<LanguageOfAssessment>();
			for (Element langElement: Lax.getChildrenQuietly(element, "languageOfAssessment", Namespaces.XCRI_NAMESPACE_NS, log)){
				LanguageOfAssessment lang = new LanguageOfAssessment();
				lang.fromXml(langElement);
				languages.add(lang);
			}
			this.setLanguageOfAssessment(languages.toArray(new LanguageOfAssessment[languages.size()]));
		}
		
		if (Lax.getChildQuietly(element, "places", Namespaces.MLO_NAMESPACE_NS, log)!=null){
			Places places = new Places();
			try {
				places.fromXml(Lax.getChildQuietly(element, "places", Namespaces.MLO_NAMESPACE_NS, log));
				this.setPlaces(places);
			} catch (Exception e) {
				log.warn("presentation: skipping invalid places element: "+e.getMessage());
			}
		}
		if (Lax.getChildQuietly(element, "cost", Namespaces.MLO_NAMESPACE_NS, log)!=null){
			Cost cost = new Cost();
			try {
				cost.fromXml(Lax.getChildQuietly(element, "cost", Namespaces.MLO_NAMESPACE_NS, log));
				this.setCost(cost);
			} catch (Exception e) {
				log.warn("presentation: skipping invalid cost element: "+e.getMessage());
			}
		}
		
		if (Lax.getChildQuietly(element, "age", Namespaces.XCRI_NAMESPACE_NS, log)!=null){
			Age age = new Age();
			try {
				age.fromXml(Lax.getChildQuietly(element, "age", Namespaces.XCRI_NAMESPACE_NS, log));
				this.setAge(age);
			} catch (Exception e) {
				log.warn("presentation: skipping invalid age element: "+e.getMessage());
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

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * @return the applyFrom
	 */
	public ApplyFrom getApplyFrom() {
		return applyFrom;
	}

	/**
	 * @param applyFrom the applyFrom to set
	 */
	public void setApplyFrom(ApplyFrom applyFrom) {
		this.applyFrom = applyFrom;
	}

	/**
	 * @return the applyUntil
	 */
	public ApplyUntil getApplyUntil() {
		return applyUntil;
	}

	/**
	 * @param applyUntil the applyUntil to set
	 */
	public void setApplyUntil(ApplyUntil applyUntil) {
		this.applyUntil = applyUntil;
	}

	/**
	 * @return the applyTo
	 */
	public ApplyTo getApplyTo() {
		return applyTo;
	}

	/**
	 * @param applyTo the applyTo to set
	 */
	public void setApplyTo(ApplyTo applyTo) {
		this.applyTo = applyTo;
	}

	/**
	 * @return the engagements
	 */
	public Engagement[] getEngagements() {
		return engagements;
	}

	/**
	 * @param engagements the engagements to set
	 */
	public void setEngagements(Engagement[] engagements) {
		this.engagements = engagements;
	}

	/**
	 * @return the studyMode
	 */
	public StudyMode getStudyMode() {
		return studyMode;
	}

	/**
	 * @param studyMode the studyMode to set
	 */
	public void setStudyMode(StudyMode studyMode) {
		this.studyMode = studyMode;
	}

	/**
	 * @return the attendanceMode
	 */
	public AttendanceMode getAttendanceMode() {
		return attendanceMode;
	}

	/**
	 * @param attendanceMode the attendanceMode to set
	 */
	public void setAttendanceMode(AttendanceMode attendanceMode) {
		this.attendanceMode = attendanceMode;
	}

	/**
	 * @return the attendancePattern
	 */
	public AttendancePattern getAttendancePattern() {
		return attendancePattern;
	}

	/**
	 * @param attendancePattern the attendancePattern to set
	 */
	public void setAttendancePattern(AttendancePattern attendancePattern) {
		this.attendancePattern = attendancePattern;
	}

	/**
	 * @return the languageOfInstruction
	 */
	public LanguageOfInstruction[] getLanguageOfInstruction() {
		return languageOfInstruction;
	}

	/**
	 * @param languageOfInstruction the languageOfInstruction to set
	 */
	public void setLanguageOfInstruction(LanguageOfInstruction[] languageOfInstruction) {
		this.languageOfInstruction = languageOfInstruction;
	}

	/**
	 * @return the languageOfAssessment
	 */
	public LanguageOfAssessment[] getLanguageOfAssessment() {
		return languageOfAssessment;
	}

	/**
	 * @param languageOfAssessment the languageOfAssessment to set
	 */
	public void setLanguageOfAssessment(LanguageOfAssessment[] languageOfAssessment) {
		this.languageOfAssessment = languageOfAssessment;
	}

	/**
	 * @return the places
	 */
	public Places getPlaces() {
		return places;
	}

	/**
	 * @param places the places to set
	 */
	public void setPlaces(Places places) {
		this.places = places;
	}

	/**
	 * @return the cost
	 */
	public Cost getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Cost cost) {
		this.cost = cost;
	}

	/**
	 * @return the age
	 */
	public Age getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Age age) {
		this.age = age;
	}

	/**
	 * @return the venues
	 */
	public Venue[] getVenues() {
		return venues;
	}

	/**
	 * @param venues the venues to set
	 */
	public void setVenues(Venue[] venues) {
		this.venues = venues;
	}
	
	

}

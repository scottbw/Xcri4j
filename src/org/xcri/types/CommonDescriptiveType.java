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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.xcri.Namespaces;
import org.xcri.common.descriptive.*;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.util.lax.Lax;

public class CommonDescriptiveType extends CommonType {
	
	private Log log = LogFactory.getLog(CommonDescriptiveType.class);
	
	private Abstract[] abstracts;
	private ApplicationProcedure[] applicationProcedures;
	private Assessment[] assessments;
	private LearningOutcome[] learningOutcomes;
	private Objective[] objectives;
	private Prerequisite[] prerequisites;
	private Regulations[] regulations;

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		Element element = super.toXml();
		
		//
		// Descriptions...
		//
		
		if (this.getAbstracts() != null) for (Abstract a: this.getAbstracts()) element.addContent(a.toXml());
		if (this.getApplicationProcedures() != null) for (ApplicationProcedure a: this.getApplicationProcedures()) element.addContent(a.toXml());
		if (this.getAssessments() != null) for (Assessment a: this.getAssessments()) element.addContent(a.toXml());
		if (this.getLearningOutcomes() != null) for (LearningOutcome a: this.getLearningOutcomes()) element.addContent(a.toXml());
		if (this.getObjectives() != null) for (Objective a: this.getObjectives()) element.addContent(a.toXml());
		if (this.getPrerequisites() != null) for (Prerequisite a: this.getPrerequisites()) element.addContent(a.toXml());
		if (this.getRegulations() != null) for (Regulations a: this.getRegulations()) element.addContent(a.toXml());
		
		return element;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) throws InvalidElementException {
		super.fromXml(element);
		
		//
		// Descriptions...
		//
		ArrayList<Abstract> abstracts = new ArrayList<Abstract>();
		for (Object obj: Lax.getChildrenQuietly(element, "abstract", Namespaces.XCRI_NAMESPACE_NS, log)){
			System.out.println("abstract");

			Abstract anabstract = new Abstract();
			try {
				anabstract.fromXml((Element)obj);
				System.out.println("abstract="+anabstract.getValue().length());

				abstracts.add(anabstract);				
				// Producers MUST NOT create a value of this element that exceeds 140 characters.
				if (anabstract.getValue().length() > 140){
					log.warn(this.getName()+ ": Abstract: Producers MUST NOT create a value of this element that exceeds 140 characters.");
				}
				
			} catch (InvalidElementException e) {
				log.warn(this.getName()+" : skipping invalid abstract element: "+e.getMessage());
			}
		}
		this.setAbstracts(abstracts.toArray(new Abstract[abstracts.size()]));
		
		ArrayList<ApplicationProcedure> applicationProcedures = new ArrayList<ApplicationProcedure>();
		for (Object obj: Lax.getChildrenQuietly(element, "applicationProcedure", Namespaces.XCRI_NAMESPACE_NS, log)){
			ApplicationProcedure applicationProcedure = new ApplicationProcedure();
			try {
				applicationProcedure.fromXml((Element)obj);
				applicationProcedures.add(applicationProcedure);
			} catch (InvalidElementException e) {
				log.warn(this.getName()+" : skipping invalid applicationProcedure element: "+e.getMessage());
			}
		}
		this.setApplicationProcedures(applicationProcedures.toArray(new ApplicationProcedure[applicationProcedures.size()]));
		
		
		ArrayList<Assessment> assessments = new ArrayList<Assessment>();
		for (Object obj: Lax.getChildrenQuietly(element, "assessment", Namespaces.MLO_NAMESPACE_NS, log)){
			Assessment assessment = new Assessment();
			try {
				assessment.fromXml((Element)obj);
				assessments.add(assessment);
			} catch (InvalidElementException e) {
				log.warn(this.getName()+" : skipping invalid assessment element: "+e.getMessage());
			}
		}
		this.setAssessments(assessments.toArray(new Assessment[assessments.size()]));
		
		// TODO learningoutcome
		// TODO objective
		// TODO prerequisite
		// TODO regulations

	}

	/**
	 * @return the abstracts
	 */
	public Abstract[] getAbstracts() {
		return abstracts;
	}

	/**
	 * @param abstracts the abstracts to set
	 */
	public void setAbstracts(Abstract[] abstracts) {
		this.abstracts = abstracts;
	}

	/**
	 * @return the applicationProcedures
	 */
	public ApplicationProcedure[] getApplicationProcedures() {
		return applicationProcedures;
	}

	/**
	 * @param applicationProcedures the applicationProcedures to set
	 */
	public void setApplicationProcedures(
			ApplicationProcedure[] applicationProcedures) {
		this.applicationProcedures = applicationProcedures;
	}

	/**
	 * @return the assessments
	 */
	public Assessment[] getAssessments() {
		return assessments;
	}

	/**
	 * @param assessments the assessments to set
	 */
	public void setAssessments(Assessment[] assessments) {
		this.assessments = assessments;
	}

	/**
	 * @return the learningOutcomes
	 */
	public LearningOutcome[] getLearningOutcomes() {
		return learningOutcomes;
	}

	/**
	 * @param learningOutcomes the learningOutcomes to set
	 */
	public void setLearningOutcomes(LearningOutcome[] learningOutcomes) {
		this.learningOutcomes = learningOutcomes;
	}

	/**
	 * @return the objectives
	 */
	public Objective[] getObjectives() {
		return objectives;
	}

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(Objective[] objectives) {
		this.objectives = objectives;
	}

	/**
	 * @return the prerequisites
	 */
	public Prerequisite[] getPrerequisites() {
		return prerequisites;
	}

	/**
	 * @param prerequisites the prerequisites to set
	 */
	public void setPrerequisites(Prerequisite[] prerequisites) {
		this.prerequisites = prerequisites;
	}

	/**
	 * @return the regulations
	 */
	public Regulations[] getRegulations() {
		return regulations;
	}

	/**
	 * @param regulations the regulations to set
	 */
	public void setRegulations(Regulations[] regulations) {
		this.regulations = regulations;
	}
	
	

}

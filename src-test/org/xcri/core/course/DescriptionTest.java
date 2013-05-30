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
package org.xcri.core.course;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;

public class DescriptionTest {
	
	@Test
	public void testMultipleDescriptions() throws InvalidElementException, JDOMException, IOException{
		
		
		String content = "<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\">"+
				"<provider><course>" +
				"<description>a course</description>" +
				"<abstract>a course abstract</abstract>" +
				"<applicationProcedure>apply online</applicationProcedure>"+
				"<assessment>by exam</assessment>"+
				"<learningOutcome>a diploma!</learningOutcome>"+
				"<objective>learn stuff</objective>"+
				"<prerequisite>have a brain</prerequisite>"+
				"<regulations>be excellent to one another</regulations>"+
				"</course></provider></catalog>";
		
    	Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader(content));
		catalog.fromXml(document);
		
		assertEquals("a course", catalog.getProviders()[0].getCourses()[0].getDescriptions()[0].getValue());
		
		assertEquals("a course abstract",catalog.getProviders()[0].getCourses()[0].getAbstracts()[0].getValue());
		assertEquals("abstract",catalog.getProviders()[0].getCourses()[0].getAbstracts()[0].getName());
		assertEquals(Namespaces.XCRI_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getAbstracts()[0].getNamespace());		
		
		assertEquals("apply online",catalog.getProviders()[0].getCourses()[0].getApplicationProcedures()[0].getValue());	
		assertEquals("applicationProcedure",catalog.getProviders()[0].getCourses()[0].getApplicationProcedures()[0].getName());
		assertEquals(Namespaces.XCRI_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getApplicationProcedures()[0].getNamespace());
		
		assertEquals("by exam",catalog.getProviders()[0].getCourses()[0].getAssessments()[0].getValue());	
		assertEquals("assessment",catalog.getProviders()[0].getCourses()[0].getAssessments()[0].getName());
		assertEquals(Namespaces.MLO_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getAssessments()[0].getNamespace());		
		
		assertEquals("a diploma!",catalog.getProviders()[0].getCourses()[0].getLearningOutcomes()[0].getValue());
		assertEquals("learningOutcome",catalog.getProviders()[0].getCourses()[0].getLearningOutcomes()[0].getName());
		assertEquals(Namespaces.XCRI_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getLearningOutcomes()[0].getNamespace());			
		
		assertEquals("learn stuff",catalog.getProviders()[0].getCourses()[0].getObjectives()[0].getValue());
		assertEquals("objective",catalog.getProviders()[0].getCourses()[0].getObjectives()[0].getName());
		assertEquals(Namespaces.MLO_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getObjectives()[0].getNamespace());		

		assertEquals("have a brain",catalog.getProviders()[0].getCourses()[0].getPrerequisites()[0].getValue());
		assertEquals("prerequisite",catalog.getProviders()[0].getCourses()[0].getPrerequisites()[0].getName());
		assertEquals(Namespaces.MLO_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getPrerequisites()[0].getNamespace());		
		
		assertEquals("be excellent to one another",catalog.getProviders()[0].getCourses()[0].getRegulations()[0].getValue());
		assertEquals("regulations",catalog.getProviders()[0].getCourses()[0].getRegulations()[0].getName());
		assertEquals(Namespaces.XCRI_NAMESPACE_NS, catalog.getProviders()[0].getCourses()[0].getRegulations()[0].getNamespace());	
	}

}

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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.exceptions.InvalidElementException;

public class PresentationTest {
	
	/**
	 * TODO Absence of Venue: Where a venue is not specified, Aggregators SHOULD interpret this 
	 * as meaning that the provider address is the venue, and use its contact information 
	 * for this purpose.
	 */
	
	/**
	 * TODO Determining uniqueness: Where a presentation does not contain an identifier, Aggregators 
	 * may need to construct presentation identifiers. It is recommended that presentations 
	 * use a URL-formatted identifier where possible, following the scheme for the provider 
	 * and course. E.g. "http://www.bolton.ac.uk/courses/1/2008-1"
	 */
	
	/**
	 * TODO Absence of study mode and attendance: Aggregators SHOULD assume that the default value 
	 * for studyMode is "Full Time", the default for attendanceMode is "Campus", and the default 
	 * for attendancePattern is "Daytime"
	 */
	
	/**
	 * TODO Start dates : A Producer SHOULD include a start element even if there is no specific 
	 * start date as this can still be used to describe the start details- see the section on 
	 * Temporal Elements.
	 */
	
	/**
	 * TODO Duration: A Producer SHOULD include a duration element or start and end dates, or both.
	 */
	
	/**
	 * Absence of Image: Where a presentation does not contain an image, but its containing 
	 * course does, an Aggregator MAY use the image of the course when displaying the presentation.
	 */
	@Test
	public void testInheritImagesFromCourse() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><image src=\"course.png\"/><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier><presentation></presentation></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("provider.png", catalog.getProviders()[0].getImages()[0].getSrc());
		assertEquals("course.png", catalog.getProviders()[0].getCourses()[0].getImages()[0].getSrc());
		assertEquals("course.png", catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getImages()[0].getSrc());
	}
	
	/**
	 * Absence of Image: Where a course does not contain an image, 
	 * but its containing provider does, an Aggregator MAY use the 
	 * image of the provider when displaying the course.
	 */
	@Test
	public void testInheritImages() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Test Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier><presentation></presentation></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("provider.png", catalog.getProviders()[0].getImages()[0].getSrc());
		assertEquals("provider.png", catalog.getProviders()[0].getCourses()[0].getImages()[0].getSrc());
		assertEquals("provider.png", catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getImages()[0].getSrc());
	}
	
	/**
	 * Absence of Title: Where a presentation does not contain a title, but its containing course 
	 * does, an Aggregator MAY use the title of the course when displaying the presentation.
	 */
	@Test
	public void testInheritTitle() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Course Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier><presentation></presentation></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("Course Title", catalog.getProviders()[0].getCourses()[0].getTitles()[0].getValue());
		assertEquals("Course Title", catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getTitles()[0].getValue());
	}
	@Test
	public void testDontInheritTitle() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Course Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier><presentation><dc:title>Presentation Title</dc:title></presentation></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("Course Title", catalog.getProviders()[0].getCourses()[0].getTitles()[0].getValue());
		assertEquals("Presentation Title", catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getTitles()[0].getValue());
	}
	
	/**
	 * Absence of Description: Where a presentation does not contain a description, but its 
	 * containing course does, an Aggregator MAY use the description of the course when displaying 
	 * the presentation.
	 */
	@Test
	public void testInheritDescription() throws InvalidElementException, JDOMException, IOException{

		Catalog catalog = new Catalog();
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><image src=\"provider.png\"/><dc:subject>lots of stuff</dc:subject><course><dc:description>Course Description</dc:description><dc:subject>stuff</dc:subject><mlo:level>3</mlo:level><dc:title>Course Title</dc:title><dc:identifier>http://test.org/testcourse/1</dc:identifier><dc:identifier xsi:type='test'>test-contains-type</dc:identifier><presentation></presentation></course></provider></catalog>"));
		catalog.fromXml(document);

		assertEquals("Course Description", catalog.getProviders()[0].getCourses()[0].getDescriptions()[0].getValue());
		assertEquals("Course Description", catalog.getProviders()[0].getCourses()[0].getPresentations()[0].getDescriptions()[0].getValue());
	}
	
	/**
	 * Use of the interval attribute While the text content of the duration element is 
	 * intended to contain a human-readable description of the duration of the learning opportunity, 
	 * the interval attribute can be used to provide a machine-readable equivalent. If there is no 
	 * direct machine-equivalent value (e.g., the duration is flexible), then Producers SHOULD NOT 
	 * include the interval attribute.
	 */
	
	/**
	 * Engagement
	 * 
	 * TODO Refinements Producers SHOULD use the refinements defined for this element with their predefined 
	 * vocabularies in preference to this element.
	 */
	
	/**
	 * TODO Study Mode
	 * 
	 * Recommended Values: Producers SHOULD use the following values for this element, with the 
	 * two-letter code used in the @identifier attribute, and the label in the element content:
	 * 
	 * NK Not known [DEFAULT] The provider has not supplied the information.
	 * FL Flexible  Could be full time or part time dependent on the learner.
	 * FT Full time The learning opportunity is the learner's main activity.
	 * PF Part of a full time programme The learning opportunity is a component of a set of learning opportunities that form the learner's main activity.
	 * PT Part time The learning opportunity is not the learner's main activity
	 * 
	 * Note: These are mutually exclusive terms, so 'Full time' does not include 'Part of a full time programme'.
	 */
	
	/**
	 * TODO Attendance Mode
	 * 
	 * Recommended Values: Producers SHOULD use the following values for this element, with the 
	 * two-letter code used in the @identifier attribute, and the label in the element content:
	 * 
	 * The value of this element MUST be one of:
	 * CM Campus
	 * DA Distance with attendance
	 * DS Distance without attendance
	 * NC Face-to-face non-campus
	 * MM Mixed mode
	 * ON Online (no attendance)
	 * WB Work-based
	 */
	
	/**
	 * TODO Attendance Pattern
	 * 
	 * Recommended Values: Producers SHOULD use the following values for this element, with 
	 * the two-letter code used in the @identifier attribute, and the label in the element content:
	 * 
	 * DT Daytime
	 * EV Evening
	 * TW Twilight
	 * DR Day/Block release
	 * WE Weekend
	 * CS Customised
	 */
	
	/**
	 * TODO Places
	 * 
	 * The default, unqualified value of this property is a simple textual description. Producers 
	 * MAY use specific encoding schemes that refine the use of this property.
	 */
	
	/**
	 * TODO Cost
	 * 
	 * The default, unqualified value of this property is a simple textual description. Producers 
	 * MAY use specific encoding schemes that refine the use of this property.
	 */

	/**
	 * TODO Age
	 * 
	 * If the value of y is greater than or equal to 99, Aggregator MAY treat the value of this 
	 * element as being equivalent to x+.
	 * 
	 * If the value of x or y is greater than 120, Aggregators MAY either truncate the value from 
	 * the right to a more realistic number, for example changing "124" to "12", or they MAY treat 
	 * this element as in error and ignore this element.
	 */
	
	/**
	 * TODO Venue
	 * 
	 * Provider: Producers SHOULD use the provider element to describe the organisation which acts 
	 * as the provider of the venue. This MAY be a sub-organisation of the provider of the presentation 
	 * (such as a department or school), or it MAY be a third party or external organisation.
	 * 
	 * Provider Properties: When a provider element is used in a venue element, Producers SHOULD 
	 * include only basic information about an organisation, such as identifier, title, description, 
	 * url, image and location.
	 * 
	 * Location: When a provider element is used in a venue element, Producers SHOULD include a location element.
	 */
}

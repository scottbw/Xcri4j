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
package org.xcri.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.xcri.Namespaces;
import org.xcri.core.Catalog;
import org.xcri.exceptions.InvalidElementException;
import org.xcri.types.CommonDescriptiveType;
import org.xcri.types.CommonType;

public class CommonDescriptiveTypeTest {
	
	/**
	 * Abstract
	 * 
	 * Length: A Aggregator MAY choose to truncate the value of this element 
	 * if it exceeds 140 characters.
	 */
	@Test
	public void abstractTest() throws InvalidElementException, JDOMException, IOException{
		Logger logger = Logger.getLogger(CommonDescriptiveType.class.getName());

		Formatter formatter = new SimpleFormatter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Handler handler = new StreamHandler(out, formatter);
		logger.addHandler(handler);
		try {
			Catalog catalog = new Catalog();
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new StringReader("<catalog xmlns=\""+Namespaces.XCRI_NAMESPACE+"\" xmlns:mlo=\""+Namespaces.MLO_NAMESPACE+"\" xmlns:dc=\""+Namespaces.DC_NAMESPACE+"\" xmlns:xsi=\""+Namespaces.XSI_NAMESPACE+"\"><provider><course><abstract>123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890</abstract></course></provider></catalog>"));
			catalog.fromXml(document);
			handler.flush();
			String logMsg = out.toString();
			assertNotNull(logMsg);
			System.out.println(logMsg);
			assertTrue(logMsg.contains("Abstract: Producers MUST NOT create a value of this element that exceeds 140 characters."));
		} finally {
			logger.removeHandler(handler);
		}	
	}
	
	/** 
	 * Learning Outcome
	 * 
	 * Learning Outcome: This SHOULD be used for specific, individual, measurable 
	 * learning outcomes. For more general course aims, use the "objective" element.
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Objective
	 * 
	 * Objective: This SHOULD be used for the general aims of the course or 
	 * presentation, and give a general overview of the purpose of the course.
	 * 
	 * NOT TESTABLE
	 */
	
	/**
	 * Prerequisite: 
	 * 
	 * Use this for general entry requirements for the course or presentation, 
	 * e.g. details of formal and informal requirements for entry to the course offering
	 * 
	 * NOT TESTABLE
	 */
	
}

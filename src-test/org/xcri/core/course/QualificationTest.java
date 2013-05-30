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

public class QualificationTest {
	
	/**
	 * TODO identifier: It is possible that Awarding Bodies will 
	 * have permanent URLs for the qualifications they award; 
	 * these would be preferable identifiers, but it is accepted 
	 * that they may be difficult to collect, so internal 
	 * identifiers are a practical alternative. Producers SHOULD 
	 * also include an identifier that refines this property where 
	 * possible to refer to an entry in a specific qualification 
	 * framework, e.g. QAN.
	 */
	
	/** 
	 * TODO educationLevel: Producers SHOULD where possible use a URI 
	 * to refer to a level within a relevant framework, e.g. 
	 * "http://purl.org/net/cm/terms/EQF#4" would refer to EQF 
	 * level 4. Attention is drawn to [1]
	 */
	
	/** 
	 * awardedBy and accreditedBy: A Producer SHOULD use the common 
	 * name of the organisation for the content of these elements.
	 * 
	 * NOT TESTABLE
	 */
	
	/** 
	 * absence of awardedBy or accreditedBy: Where a qualification 
	 * does not contain an awardedBy and/or accreditedBy property, 
	 * an Aggregator SHOULD interpret this as meaning the capability 
	 * is provided by the Provider.
	 * 
	 * NOT TESTABLE
	 */

}

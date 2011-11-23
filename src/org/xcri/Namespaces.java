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

package org.xcri;

import org.jdom.Namespace;

public class Namespaces {

	public static final String XCRI_NAMESPACE = "http://xcri.org/profiles/1.2/catalog";
	public static final String MLO_NAMESPACE = "http://purl.org/net/mlo";
	public static final String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";
	public static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	
	
	public static final Namespace XSI_NAMESPACE_NS = Namespace.getNamespace("xsi", XSI_NAMESPACE);
	public static final Namespace DC_NAMESPACE_NS = Namespace.getNamespace("dc",DC_NAMESPACE);
	public static final Namespace XML_NAMESPACE_NS = Namespace.XML_NAMESPACE;
	public static final Namespace MLO_NAMESPACE_NS = Namespace.getNamespace(MLO_NAMESPACE);
	public static final Namespace XCRI_NAMESPACE_NS = Namespace.getNamespace(XCRI_NAMESPACE);
}

# Xcri4j

A Java library for parsing and generating XCRI documents

http://www.xcri.org/wiki/index.php/XCRI_CAP_1.2

## Features

* Handles incorrect namespaces
* Handles element names in the WRONG CaSe
* Handles HTML in CDATA sections in descriptions, tries to convert to valid XHTML
* Logs errors and tries to fix them rather than fail

## Basic Usage

To parse an XML document and create a new Catalog object:

    Catalog catalog = new Catalog();
    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(new File("src-test/test.xml"));
    catalog.fromXml(document);
 
You can also output XCRI using JDOM:

	new XMLOutputter().output(catalog.toXml(), out);
	
(For example, you can use XCRI4J as a preprocessor to attempt to clean up a feed)
    
## NOTE

Note that this ISN'T FINISHED! Please fork it and add missing parts :-)

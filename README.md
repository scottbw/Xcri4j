# Xcri4j

A Java library for parsing XCRI documents

http://www.xcri.org/wiki/index.php/XCRI_CAP_1.2

Note that this ISN'T FINISHED! Please fork it and add missing parts :-)

## Basic Usage

To parse an XML document and create a new Catalog object:

    Catalog catalog = new Catalog();
    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(new File("src-test/test.xml"));
    catalog.fromXml(document);
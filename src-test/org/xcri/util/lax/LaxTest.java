package org.xcri.util.lax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.jdom.Element;
import org.junit.Test;
import org.xcri.Namespaces;

public class LaxTest {
	
	@Test
	public void getChildrenNoNamespace(){
		
	}
	
	@Test
	public void getChildrenWrongNamespace(){
		
	}
	
	@Test
	public void getChildrenMisspelling(){
		Element root = new Element("root");
		Element elem = new Element("testElement");
		root.addContent(elem);
		try {
			List<Element> results = Lax.getChildren(root, "TestElement", Namespaces.DC_NAMESPACE_NS);
			fail();
		} catch (LaxException e) {
			assertEquals("TestElement", e.getElements().get(0).getName());
		}
		
	}
	
	@Test
	public void getSingleChildNoNamespace(){
		Element root = new Element("root");
		Element elem = new Element("TestElement");
		root.addContent(elem);
		try {
			Element result = Lax.getChild(root, "TestElement", Namespaces.DC_NAMESPACE_NS);
			assertEquals("TestElement", result.getName());
			assertEquals(Namespaces.DC_NAMESPACE_NS, result.getNamespace());
		} catch (SingleElementException e) {
			fail();
		}
	}
	
	@Test
	public void getSingleChildWrongNamespace(){
		Element root = new Element("root");
		Element elem = new Element("TestElement", Namespaces.MLO_NAMESPACE);
		root.addContent(elem);
		try {
			Element result = Lax.getChild(root, "TestElement", Namespaces.DC_NAMESPACE_NS);
			assertEquals("TestElement", result.getName());
			assertEquals(Namespaces.DC_NAMESPACE_NS, result.getNamespace());
		} catch (SingleElementException e) {
			fail();
		}		
	}
	
	@Test
	public void getSingleChildMisspelling(){
		Element root = new Element("root");
		Element elem = new Element("testElement");
		root.addContent(elem);
		try {
			Element result = Lax.getChild(root, "TestElement", Namespaces.DC_NAMESPACE_NS);
			assertEquals("TestElement", result.getName());
		} catch (SingleElementException e) {
			fail();
		}
	}
	
	@Test
	public void getChildrenMixed(){
		
	}

}

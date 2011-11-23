package org.xcri.core;

import org.jdom.Element;
import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.types.CommonDescriptiveType;

public class Presentation extends CommonDescriptiveType {

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#toXml()
	 */
	@Override
	public Element toXml() {
		// TODO Auto-generated method stub
		return super.toXml();
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.CommonType#fromXml(org.jdom.Element)
	 */
	@Override
	public void fromXml(Element element) {
		// TODO Auto-generated method stub
		super.fromXml(element);
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
	
	

}

package org.xcri.presentation;

import org.jdom.Namespace;
import org.xcri.Namespaces;
import org.xcri.types.TemporalType;

public class Start extends TemporalType {

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getNamespace()
	 */
	@Override
	public Namespace getNamespace() {
		return Namespaces.MLO_NAMESPACE_NS;
	}

	/* (non-Javadoc)
	 * @see org.xcri.types.XcriElement#getName()
	 */
	@Override
	public String getName() {
		return "start";
	}
	
	

}

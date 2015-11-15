package org.aries.tx.service.ejb;

import org.aries.tx.service.AbstractListener;


public abstract class AbstractEJBListener extends AbstractListener implements EJBListener {

	@Override
	public void initialize() throws Exception {
	}

	@Override
	public void reset() throws Exception {
	}

	@Override
	public void close() throws Exception {
	}
	
}

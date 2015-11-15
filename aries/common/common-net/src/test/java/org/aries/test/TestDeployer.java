package org.aries.test;

import java.net.URL;


public interface TestDeployer {

	/** 
	 * Deploys specified archive.
	 */
	void deploy(URL archive) throws Exception;

	/** 
	 * Undeploys specified archive.
	 */
	void undeploy(URL archive) throws Exception;

}

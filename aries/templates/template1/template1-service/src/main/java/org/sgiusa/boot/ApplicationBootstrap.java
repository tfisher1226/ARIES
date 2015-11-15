package org.sgiusa.boot;

import javax.ejb.Local;


@Local
public interface ApplicationBootstrap {
	String NAME = "applicationBootstrap";

	void init() throws Exception;

}
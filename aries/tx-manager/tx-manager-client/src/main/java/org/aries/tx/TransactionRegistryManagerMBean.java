package org.aries.tx;

import javax.management.MXBean;


@MXBean
public interface TransactionRegistryManagerMBean {
	
	public static final String MBEAN_NAME = "org.aries.transactionRegistryManager:name=TransactionRegistryManagerMBean";
	
	public void assertActive(String correlationId) throws Exception;
	
	public void assertCommitted(String correlationId) throws Exception;
	
	public void assertRolledBack(String correlationId) throws Exception;

}

package org.aries.cache;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup;
import net.sf.ehcache.transaction.manager.TransactionManagerLookup;
import net.sf.ehcache.transaction.xa.EhcacheXAResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.arjuna.ats.jta.common.jtaPropertyManager;


public class TransactionManagerLookupClass extends DefaultTransactionManagerLookup implements TransactionManagerLookup {

	private static Log log = LogFactory.getLog(TransactionManagerLookupClass.class);
	

	@Override
	public void setProperties(Properties properties) {
		System.out.println();
	}

	@Override
	public TransactionManager getTransactionManager() {
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			
			if (transactionManager == null)
				//This will be null when called from outside the container
				transactionManager = jtaPropertyManager.getJTAEnvironmentBean().getTransactionManager();
			
			//TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
			return transactionManager;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	//@Override
	public void register(EhcacheXAResource resource) {
		try {
			TransactionManager transactionManager = getTransactionManager();
			Transaction transaction = transactionManager.getTransaction();
//			if (transaction == null)
//				transactionManager.begin();
//			transaction = transactionManager.getTransaction();
			if (transaction != null && transaction.getStatus() == 0)
				transaction.enlistResource(resource);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}

	//@Override
	public void unregister(EhcacheXAResource resource) {
		try {
			TransactionManager transactionManager = getTransactionManager();
			Transaction transaction = transactionManager.getTransaction();
			if (transaction != null)
				transaction.delistResource(resource, 0);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}

}

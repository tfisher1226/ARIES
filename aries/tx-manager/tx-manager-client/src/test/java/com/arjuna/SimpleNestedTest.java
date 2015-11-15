package com.arjuna;

import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import org.junit.Test;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.jta.TransactionManager;
import com.arjuna.ats.jta.common.jtaPropertyManager;


public class SimpleNestedTest {

	@Test
	public void testEnabled () throws Exception {
		ThreadActionData.purgeActions();
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);

		javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();

		transactionManager.begin();
		transactionManager.begin();

		Transaction currentTrans = transactionManager.getTransaction();
		TestXAResource res1, res2;
		currentTrans.enlistResource(res1 = new TestXAResource());
		currentTrans.enlistResource(res2 = new TestXAResource());

		currentTrans.delistResource(res2, XAResource.TMSUCCESS);
		currentTrans.delistResource(res1, XAResource.TMSUCCESS);

		transactionManager.commit();
		transactionManager.commit();
	}
}

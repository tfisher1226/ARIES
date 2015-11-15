package com.arjuna;

import static org.junit.Assert.fail;

import javax.transaction.NotSupportedException;

import org.junit.Ignore;
import org.junit.Test;

import com.arjuna.ats.jta.TransactionManager;
import com.arjuna.ats.jta.common.jtaPropertyManager;


public class SimpleNestedDisabledTest {

	@Test
	@Ignore
	public void testDisabled () throws Exception {
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(false);

		javax.transaction.TransactionManager transactionManager = TransactionManager.transactionManager();

		transactionManager.begin();

		try {
			transactionManager.begin();
			fail();
		} catch (final NotSupportedException e) {
		}

		transactionManager.commit();
	}

}

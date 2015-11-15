package com.arjuna.jca;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.arjuna.ats.arjuna.ObjectType;
import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.internal.jta.transaction.arjunacore.subordinate.jca.SubordinateAtomicAction;
import com.arjuna.ats.internal.jta.transaction.arjunacore.subordinate.jca.TransactionImple;


public class SubordinateJcaTxUnitTest {

	@Test
	public void testTransactionImple () throws Exception {
		TransactionImple tx = new TransactionImple(new Uid());
		TransactionImple dummy = new TransactionImple(new Uid());

		tx.recordTransaction();

		assertFalse(tx.equals(dummy));

		assertTrue(tx.toString() != null);

		tx.recover();
	}

	@Test
	public void testAtomicAction () throws Exception {
		SubordinateAtomicAction saa1 = new SubordinateAtomicAction();
		SubordinateAtomicAction saa2 = new SubordinateAtomicAction(new Uid());
		OutputObjectState os = new OutputObjectState();

		assertTrue(saa1.save_state(os, ObjectType.ANDPERSISTENT));

		InputObjectState is = new InputObjectState(os);

		assertTrue(saa2.restore_state(is, ObjectType.ANDPERSISTENT));
	}

}

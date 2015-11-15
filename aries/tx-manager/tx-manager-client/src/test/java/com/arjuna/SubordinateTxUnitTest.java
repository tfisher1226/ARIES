package com.arjuna;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.arjuna.ats.arjuna.coordinator.TwoPhaseOutcome;
import com.arjuna.ats.internal.jta.transaction.arjunacore.subordinate.TransactionImple;
import com.arjuna.ats.jta.exceptions.InvalidTerminationStateException;


public class SubordinateTxUnitTest {

	@Test
	public void testTransactionImple () throws Exception {
		TransactionImple tx = new TransactionImple(0);
		TransactionImple dummy = new TransactionImple(0);

		assertFalse(tx.equals(dummy));

		try
		{
			tx.commit();

			fail();
		}
		catch (final IllegalStateException ex)
		{
		}

		try
		{
			tx.rollback();

			fail();
		}
		catch (InvalidTerminationStateException ex)
		{
		}

		assertEquals(tx.doPrepare(), TwoPhaseOutcome.PREPARE_READONLY);

		tx.doCommit();

		dummy.doRollback();

		tx = new TransactionImple(10);

		tx.doOnePhaseCommit();
		tx.doForget();

		tx.doBeforeCompletion();

		assertTrue(tx.toString() != null);
		assertTrue(tx.activated());
	}

}

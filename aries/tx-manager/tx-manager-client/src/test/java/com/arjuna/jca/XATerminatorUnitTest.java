package com.arjuna.jca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

import org.junit.Before;
import org.junit.Test;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.internal.jta.transaction.arjunacore.jca.SubordinateTransaction;
import com.arjuna.ats.internal.jta.transaction.arjunacore.jca.SubordinationManager;
import com.arjuna.ats.internal.jta.transaction.arjunacore.jca.XATerminatorImple;
import com.arjuna.ats.jta.common.jtaPropertyManager;
import com.arjuna.ats.jta.exceptions.UnexpectedConditionException;
import com.arjuna.ats.jta.xa.XidImple;
import com.arjuna.jca.FailureXAResource.FailLocation;
import com.arjuna.jca.FailureXAResource.FailType;


public class XATerminatorUnitTest {

	@Before
	public void setUp() throws Exception {
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
	}
	
	@Test
	public void test () throws Exception
	{
		XATerminatorImple term = new XATerminatorImple();
		XidImple xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);

		assertTrue(term.beforeCompletion(xid));
		assertEquals(term.prepare(xid), XAResource.XA_RDONLY);

		SubordinationManager.getTransactionImporter().removeImportedTransaction(xid);
		SubordinationManager.getTransactionImporter().importTransaction(xid);

		term.commit(xid, true);

		SubordinationManager.getTransactionImporter().removeImportedTransaction(xid);
		SubordinationManager.getTransactionImporter().importTransaction(xid);

		term.rollback(xid);

		SubordinationManager.getTransactionImporter().removeImportedTransaction(xid);
		SubordinationManager.getTransactionImporter().importTransaction(xid);

		term.recover(XAResource.TMSTARTRSCAN);

		try
		{
			term.recover(XAResource.TMSTARTRSCAN);

			fail();
		}
		catch (final XAException ex)
		{
		}

		term.recover(XAResource.TMENDRSCAN);

		term.forget(xid);
	}

	@Test
	public void testFail () throws Exception
	{
		XATerminatorImple term = new XATerminatorImple();
		XidImple xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);

		SubordinateTransaction tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.commit, FailType.rollback));

		try
		{
			term.commit(xid, false);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.commit, FailType.heurcom));

		try
		{
			term.commit(xid, false);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.commit, FailType.heurcom));

		term.prepare(xid);

		try
		{
			term.commit(xid, false);
		}
		catch (final XAException ex)
		{
			fail();
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.commit, FailType.normal));

		try
		{
			term.commit(xid, false);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.rollback, FailType.rollback));

		try
		{
			term.rollback(xid);
		}
		catch (final XAException ex)
		{
			fail();
		}

		/*
		 * TODO FIXME 
		 * DOES NOT WORK WHEN TEST IS RUN BY MAVEN
		 * 
		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.rollback, FailType.heurcom));

		term.prepare(xid);

		try
		{
			term.rollback(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.rollback, FailType.normal));

		term.prepare(xid);

		try
		{
			term.rollback(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}


		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.prepare_and_rollback, FailType.normal));

		try
		{
			term.prepare(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.prepare_and_rollback, FailType.heurcom));

		try
		{
			term.prepare(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}

		xid = new XidImple(new Uid());
		SubordinationManager.getTransactionImporter().importTransaction(xid);
		tx = SubordinationManager.getTransactionImporter().getImportedTransaction(xid);

		tx.enlistResource(new FailureXAResource(FailLocation.prepare_and_rollback, FailType.rollback));

		try
		{
			term.prepare(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}
		 */
	}

	@Test
	public void testUnknownTransaction () throws Exception
	{
		XATerminatorImple term = new XATerminatorImple();
		XidImple xid = new XidImple(new Uid());

		try
		{
			term.beforeCompletion(xid);

			fail();
		}
		catch (final UnexpectedConditionException ex)
		{
		}

		try
		{
			term.prepare(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}

		try
		{
			term.commit(xid, false);

			fail();
		}
		catch (final XAException ex)
		{
		}

		try
		{
			term.rollback(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}

		try
		{
			term.forget(xid);

			fail();
		}
		catch (final XAException ex)
		{
		}
	}

	@Test
	public void testInvalid () throws Exception
	{
		XATerminatorImple term = new XATerminatorImple();
		XidImple xid = new XidImple(new Uid());

		try
		{
			SubordinationManager.getTransactionImporter().importTransaction(null);

			fail();
		}
		catch (final IllegalArgumentException ex)
		{
		}

		try
		{
			SubordinationManager.getTransactionImporter().recoverTransaction(null);

			fail();
		}
		catch (final IllegalArgumentException ex)
		{
		}

		try
		{
			SubordinationManager.getTransactionImporter().getImportedTransaction(null);

			fail();
		}
		catch (final IllegalArgumentException ex)
		{
		}

		try
		{
			SubordinationManager.getTransactionImporter().removeImportedTransaction(null);

			fail();
		}
		catch (final IllegalArgumentException ex)
		{
		}

		Uid uid = new Uid();

		try
		{
			Object obj = SubordinationManager.getTransactionImporter().recoverTransaction(uid);

			fail();
		}
		catch (IllegalArgumentException ex)
		{
		}
	}

}

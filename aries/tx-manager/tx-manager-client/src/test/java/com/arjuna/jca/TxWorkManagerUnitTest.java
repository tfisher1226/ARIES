package com.arjuna.jca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.resource.spi.work.Work;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;

import org.junit.Test;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.arjuna.ats.internal.jta.transaction.arjunacore.jca.TxWorkManager;
import com.arjuna.ats.internal.jta.transaction.arjunacore.jca.WorkSynchronization;


public class TxWorkManagerUnitTest {

	@Test
	public void testWorkManager () throws Exception
	{
		DummyWork work = new DummyWork();
		Transaction tx = new TransactionImple(0);

		TxWorkManager.addWork(work, tx);

		try
		{
			TxWorkManager.addWork(new DummyWork(), tx);

			fail();
		}
		catch (final Throwable ex)
		{
		}

		assertTrue(TxWorkManager.hasWork(tx));

		assertEquals(work, TxWorkManager.getWork(tx));       

		TxWorkManager.removeWork(work, tx);

		assertEquals(TxWorkManager.getWork(tx), null);
	}

	@Test
	public void testWorkSynchronization () throws Exception
	{
		Transaction tx = new TransactionImple(0);
		Synchronization ws = new WorkSynchronization(tx);
		DummyWork work = new DummyWork();

		TxWorkManager.addWork(work, tx);

		try
		{
			ws.beforeCompletion();

			fail();
		}
		catch (final IllegalStateException ex)
		{
		}

		ws.afterCompletion(Status.STATUS_COMMITTED);
	}

	class DummyWork implements Work
	{       
		public DummyWork ()
		{
		}

		public void release ()
		{
		}

		public void run ()
		{
		}
	}

}

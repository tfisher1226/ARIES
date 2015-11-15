package org.aries.validate.util;

import java.net.ProtocolException;
import java.util.Iterator;
import java.util.List;

import org.aries.Assert;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.validate.Checkpoint;
import org.aries.validate.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CheckpointManagerTest {

	@Before
	public void setUp() throws Exception {
		String domain = "test";
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(domain);
		CheckpointManager.setJAXBSessionCache(jaxbSessionCache);
		//CheckpointManager.readConfigurations();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testReadConfiguration() throws Exception {
		List<Checkpoint> checkpoints = CheckpointManager.getCheckpoints();
		assertCheckpoints(checkpoints);
	}

	private void assertCheckpoints(List<Checkpoint> checkpoints) {
		Assert.notNull(checkpoints, "Checkpoints null");
		Assert.isTrue(checkpoints.size() == 4, "Incorrect size");
		Iterator<Checkpoint> iterator = checkpoints.iterator();
		while (iterator.hasNext()) {
			Checkpoint checkpoint = iterator.next();
			assertCheckpoint(checkpoint);
		}
	}

	private void assertCheckpoint(Checkpoint checkpoint) {
		Assert.notEmpty(checkpoint.getName(), "Checkpoint name empty");
		List<Condition> conditions = checkpoint.getConditions();
		Assert.notNull(conditions, "Conditions null");
		Assert.isTrue(conditions.size() > 0, "Invalid size");
		Iterator<Condition> iterator = conditions.iterator();
		while (iterator.hasNext()) {
			Condition condition = iterator.next();
			assertCondition(condition);
		}
	}

	private void assertCondition(Condition condition) {
		//Assert.notEmpty(condition.getName(), "Condition name empty");
		//TODO validate logic
	}

	@Ignore
	@Test(expected=RuntimeException.class)
	public void testCheck_NullObject() throws Exception {
		validate("checkpoint1", null, "Request not specified");
	}

	@Ignore
	@Test(expected=RuntimeException.class)
	public void testCheck_NullStringField() throws Exception {
		Request request = new Request();
		request.stringField = null;
		validate("checkpoint2", request, "Request input not specified");
	}

	@Ignore
	@Test(expected=RuntimeException.class)
	public void testCheck_EmptyStringField() throws Exception {
		Request request = new Request();
		request.stringField = "";
		validate("checkpoint2", request, "Request input empty");
	}

	@Ignore
	@Test(expected=IllegalArgumentException.class)
	public void testCheck_FalseBooleanField() throws Exception {
		Request request = new Request();
		request.booleanField = false;
		validate("checkpoint3", request, "Boolean field false");
	}

	@Ignore
	@Test(expected=ProtocolException.class)
	public void testCheck_Nested_BooleanField() throws Exception {
		Message message = new Message();
		Header header = new Header();
		message.header = header;
		header.expired = true;
		validate("checkpoint4", message, "Message expired");
	}

	protected void validate(String checkpointName, Object object) throws Exception {
		validate(checkpointName, object, null);
	}
	
	protected void validate(String checkpointName, Object object, String detail) throws Exception {
		validate(checkpointName, object, null, null);
	}
	
	protected void validate(String checkpointName, Object object, String detail, Class<?> exceptionClass) throws Exception {
		try {
			Check.isValid(checkpointName, object);
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			if (cause == null)
				cause = e;
			
			if (exceptionClass != null && !exceptionClass.equals(cause.getClass()))
				throw new Exception("Unexpected exception class: "+exceptionClass+", expected: "+cause.getClass());
			
			if (detail != null && !detail.equals(cause.getMessage()))
				throw new Exception("Unexpected exception message: "+detail+", expected: "+cause.getMessage());
			
			if (cause instanceof Exception)
				throw (Exception) cause;
		}
	}

	class Request {
		private String stringField;
		private boolean booleanField;

		public void request() {
		}

		public String getStringField() {
			return stringField;
		}

		public void setStringField(String stringField) {
			this.stringField = stringField;
		}

		public boolean getBooleanField() {
			return booleanField;
		}

		public void setBooleanField(boolean booleanField) {
			this.booleanField = booleanField;
		}
	}

	class Message {
		private Header header;
		
		public Header getHeader() {
			return header;
		}
	}

	class Header {
		private String messageId;
		private boolean expired;

		public String getMessageId() {
			return messageId;
		}

		public boolean getExpired() {
			return expired;
		}
	}

}

package org.aries.tx;

import java.lang.reflect.Constructor;

import org.aries.Assert;
import org.aries.AssertionFailure;


public abstract class AbstractUnitTest {
	
	protected boolean isRequestExpected;

	protected boolean isAbortExpected;

	protected Exception expectedException;
	
	protected String expectedExceptionMessage;
	
	protected Class<?> expectedExceptionClass;
	
	protected Class<?> expectedExceptionCause;


	public void setUp() throws Exception {
		isRequestExpected = true;
		isAbortExpected = false;
	}
	
	public void tearDown() throws Exception {
		expectedExceptionMessage = null;
		expectedExceptionClass = null;
		expectedExceptionCause = null;
	}
	
	protected void setupForExpectedAssertionFailure(String exceptionMessage) throws Exception {
		addExpectedException(AssertionFailure.class, null, exceptionMessage);
	}
	
	protected void setupForExpectedAssertionFailure(Class<? extends Throwable> exceptionCause, String exceptionMessage) throws Exception {
		addExpectedException(AssertionFailure.class, exceptionCause, exceptionMessage);
	}
	
	protected void setupForExpectedIllegalArgument(String exceptionMessage) throws Exception {
		addExpectedException(IllegalArgumentException.class, null, exceptionMessage);
	}

	protected void setupForExpectedIllegalArgument(Class<? extends Throwable> exceptionCause, String exceptionMessage) throws Exception {
		addExpectedException(IllegalArgumentException.class, exceptionCause, exceptionMessage);
	}
	
	protected void setupForExpectedException(Class<? extends Throwable> exceptionClass, String exceptionMessage) throws Exception {
		addExpectedException(exceptionClass, null, exceptionMessage);
	}
	
	protected void addExpectedNullPointerException() throws Exception {
		expectedException = new NullPointerException(null);
		expectedExceptionClass = NullPointerException.class;
		expectedExceptionMessage = null;
	}

	protected void addExpectedServiceAbortedException(String exceptionMessage) throws Exception {
		expectedException = new AssertionFailure(exceptionMessage);
		expectedExceptionClass = AssertionFailure.class;
		expectedExceptionMessage = exceptionMessage;
	}

	protected void addExpectedServiceAbortedException(Class<?> exceptionClass, String exceptionMessage) throws Exception {
		expectedException = (Exception) createException(exceptionClass, exceptionMessage);
		expectedExceptionClass = exceptionClass;
		expectedExceptionMessage = exceptionMessage;
	}
	
	protected void addExpectedException(Class<? extends Throwable> exceptionClass, Class<? extends Throwable> exceptionCause, String exceptionMessage) throws Exception {
		if (exceptionCause != null) {
			Throwable instanceofCause = createException(exceptionCause, "message");
			expectedException = (Exception) createException(exceptionClass, exceptionMessage, instanceofCause);
		} else expectedException = (Exception) createException(exceptionClass, exceptionMessage);
		expectedExceptionClass = exceptionClass;
		expectedExceptionCause = exceptionCause;
		expectedExceptionMessage = exceptionMessage;
	}

	protected <T> T createException(Class<T> classObject, Object exceptionMessage) throws Exception {
		return createException(classObject, exceptionMessage, null);
	}
	
	protected <T> T createException(Class<T> classObject, Object exceptionMessage, Throwable exceptionCause) throws Exception {
		Class<?>[] parameterTypes = new Class<?>[2];
		parameterTypes[0] = String.class;
		parameterTypes[1] = Throwable.class;
		Constructor<?> constructor = classObject.getConstructor(parameterTypes);
		Assert.notNull(constructor, "Constructor not found for: "+classObject.getName());
		@SuppressWarnings("unchecked") T instance = (T) constructor.newInstance(exceptionMessage, exceptionCause);
		return instance;
	}
	
	protected void validateAfterException(Throwable e) {
		validateExceptionType(e);
	}
	
	protected void validateExceptionType(Throwable e) {
		Assert.equals(e.getClass(), expectedExceptionClass);
		Throwable exception = getExceptionForThrowable(e);
		if (exception == null)
			exception = e;
		//Assert.equals(exception.getClass(), expectedExceptionClass);
		if (expectedExceptionMessage != null) {
			Assert.notNull(exception.getMessage(), "Exception message null, expecting: "+expectedExceptionMessage);
			Assert.startsWith(exception.getMessage(), expectedExceptionMessage);
		}
		if (expectedExceptionCause != null) {
			Assert.notNull(exception.getCause(), "Exception cause null, expecting: "+expectedExceptionCause.getName());
			Assert.equals(exception.getCause().getClass(), expectedExceptionCause);
		}
	}

	//gets first non-RuntimeException in cause chain
	protected Throwable getExceptionForThrowable(Throwable e) {
		if (e instanceof RuntimeException == false)
			return e;
		Throwable cause = e.getCause();
		if (cause instanceof Error)
			return cause;
		if (cause instanceof RuntimeException)
			return getExceptionForThrowable(cause);
		return cause;
	}
	
}

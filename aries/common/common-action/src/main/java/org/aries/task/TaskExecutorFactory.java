package org.aries.task;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.ObjectUtil;


public class TaskExecutorFactory {

	private static final String DEFAULT_TASK_EXECUTOR_CLASS = TaskExecutorImpl.class.getName();
	
	private String taskExecutorClassName = DEFAULT_TASK_EXECUTOR_CLASS;
	
	
	public void setTaskExecutorClassName(String className) {
		taskExecutorClassName = className;
	}
	
	public TaskExecutor createExecutor(Object object) {
		Assert.notNull(taskExecutorClassName, "TaskExecutor class not specified");
		TaskExecutor taskExecutor = newInstance(taskExecutorClassName, object);
		return taskExecutor;
	}

	protected <T> T newInstance(String className, Object parameter) {
		Class<?>[] parameterTypes = new Class<?>[] { Object.class };
		Object[] parameterValues = new Object[] { parameter };
		try {
			Class<?> classInstance = ClassUtil.loadClass(className);
			T object = ObjectUtil.loadObject(classInstance, parameterTypes, parameterValues);
			return object;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}

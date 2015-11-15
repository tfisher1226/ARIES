package org.aries.task;

import org.aries.Assert;
import org.aries.util.ClassUtil;
import org.aries.util.ObjectUtil;


public class TaskInvokerFactory {

	private static final String DEFAULT_TASK_EXECUTOR_CLASS = TaskInvokerImpl.class.getName();
	
	private String taskInvokerClassName = DEFAULT_TASK_EXECUTOR_CLASS;
	
	
	public void setTaskInvokerClassName(String className) {
		taskInvokerClassName = className;
	}
	
	public TaskInvoker createInvoker(Object object) {
		Assert.notNull(taskInvokerClassName, "TaskInvoker class not specified");
		TaskInvoker taskInvoker = newInstance(taskInvokerClassName, object);
		return taskInvoker;
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

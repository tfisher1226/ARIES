package org.aries.tx;

import org.jboss.byteman.contrib.bmunit.BMUnit;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;


public class BytemanHelper extends BMUnitRunner implements MethodRule {

	private static Class<?> testClass;

	public static BytemanHelper create(Class<?> testClass) {
		BytemanHelper.testClass = testClass;
		try {
			return new BytemanHelper(testClass);
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
	}

	private BytemanHelper(Class testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	public Statement apply(final Statement statement, final FrameworkMethod method, final Object target) {
		Statement result = addMethodMultiRuleLoader(statement, method);
		if (result == statement) {
			result = addMethodSingleRuleLoader(statement, method);
		}
		return result;
	}

	protected Statement addMethodMultiRuleLoader(final Statement statement, FrameworkMethod method) {
		BytemanRules annotation = method.getAnnotation(BytemanRules.class);
		if (annotation == null) {
			return statement;
		} else {
			final String name = method.getName();
			final String script = BytemanUtil.constructScriptText(annotation.rules());
			return new Statement() {
				public void evaluate() throws Throwable {
					BMUnit.loadScriptText(testClass, name, script);
					try {
						statement.evaluate();
					} finally {
						BMUnit.unloadScriptText(testClass, name);
					}
				}
			};
		}
	}

	protected Statement addMethodSingleRuleLoader(final Statement statement, FrameworkMethod method) {
		BytemanRule annotation = method.getAnnotation(BytemanRule.class);
		if (annotation == null) {
			return statement;
		} else {
			final String name = method.getName();
			final String script = BytemanUtil.constructScriptText(new BytemanRule[]{annotation});
			return new Statement() {
				public void evaluate() throws Throwable {
					BMUnit.loadScriptText(testClass, name, script);
					try {
						statement.evaluate();
					} finally {
						BMUnit.unloadScriptText(testClass, name);
					}
				}
			};
		}
	}

}

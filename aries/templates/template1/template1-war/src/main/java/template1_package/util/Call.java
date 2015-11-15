package template1_package.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


/**
 * This class lets you call static methods from EL
 * 
 * Step 1) Create an instance of this class and bind it to the "call" variable
 * in your EL. For example, in a JSP do the following:
 * request.setAttribute("call", new Call());
 * 
 * Step 2) Call any static method as follows:
 * 
 * ${call["some.package.SomeClass.methodName"]["arg1"][arg2]}
 * 
 * The first argument is the fully qualified class and method name. The
 * remaining arguments are arguments to the method that you are calling.
 * 
 * Note: method overloading not supported
 * 
 * Note: method must be static
 * 
 * @author Vineet Manohar
 */

@Name("call")  
@Scope(ScopeType.APPLICATION)
public class Call extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	@Override
	public Object get(Object key) {
		String fullyQualifiedMethodName = (String) key;

		// format of key is package.Class.method
		Pattern pattern = Pattern.compile("(.+)\\.([^\\.]+)");
		Matcher m = pattern.matcher(fullyQualifiedMethodName);
		if (m.matches()) {
			String fqClassName = m.group(1);
			String methodName = m.group(2);
			Class<Object> clazz;
			try {
				clazz = (Class<Object>) Class.forName(fqClassName);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Invalid method name: "
						+ key, e);
			}
			Method[] methods = clazz.getMethods();
			for (final Method method : methods) {
				if ((method.getModifiers() & Modifier.STATIC) == 0) {
					continue;
				}

				if (method.getName().equals(methodName)) {
					// return the first method found
					int numParameters = method.getParameterTypes().length;

					if (numParameters == 0) {
						return invokeMethod(method);
					}

					return new ELMethod(numParameters) {
						@Override
						public Object result(Object[] args) {
							return invokeMethod(method, args);
						}
					};
				}
			}
		}

		throw new IllegalArgumentException("Invalid method name: " + key
				+ ". Must be a fully qualified class and method name");
	}

	private Object invokeMethod(final Method method, Object... args) {
		try {
			return method.invoke(null, args);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Exception while executing method", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Exception while executing method", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Exception while executing method", e);
		}
	}

}

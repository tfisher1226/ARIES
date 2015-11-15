package template1_package.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Extend this class to implement a server side method that takes arguments that
 * you would like to call via expression language.
 * 
 * @author Vineet Manohar
 */
public abstract class ELMethod extends HashMap<Object, Object> {
	private static final long serialVersionUID = 1L;

	private final int numArgs;

	/**
	 * @param numArgs
	 *            number of arguments this method takes
	 */
	protected ELMethod(int numArgs) {
		this.numArgs = numArgs;
	}

	@Override
	public Object get(Object key) {
		// if exactly one argument, call the result() method
		if (numArgs == 1) {
			return result(new Object[] {key});
		}

		// if more tha one argument
		return new Arg(this, key);
	}

	public int getNumArgs() {
		return numArgs;
	}

	/**
	 * 1) Implement this method in the child class. This method becomes
	 * accesible via expression language.
	 * 
	 * 2) Call this method using map syntax, by treating the instance of the
	 * child class as a map of map of maps...
	 * 
	 * For example, you could extends this class and create a class called
	 * FormatDate. In that class, the result method would expect 2 arguments,
	 * format string and date object.
	 * 
	 * ${FormatDate["MMM dd"][user.creationDate]}, where dateFormat is an
	 * instance of the child class.
	 * 
	 * @param args
	 * @return
	 */
	public abstract Object result(Object[] args);

	public static class Arg extends HashMap<Object, Object> {
		private static final long serialVersionUID = 1L;
		private List<Object> args = new ArrayList<Object>();
		private ELMethod parent;

		public Arg(ELMethod eLMethod, Object key) {
			this.parent = eLMethod;
			this.args.add(key);
		}

		@Override
		public Object get(Object key) {
			this.args.add(key);

			// if all the arguments have been received, invoke the result method
			if (args.size() == parent.getNumArgs()) {
				Object retVal = parent.result(args.toArray());

				return retVal;
			}

			return this;
		}
	}
}

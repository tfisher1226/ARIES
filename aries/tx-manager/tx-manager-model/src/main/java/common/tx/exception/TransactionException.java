package common.tx.exception;

/**
 * This is the base class from which all WSAS related exceptions
 * inherit. It provides several kinds of additional information:
 *
 * (i) a string describing the error.
 * (ii) an integer code for the error.
 * 
 */
public class TransactionException extends Exception {

	/**
	 * Constructs a WSASException object; reason defaults to null and
	 * errorcode defaults to 0.
	 */

	public TransactionException ()
	{
		super();

		_errorCode = 0;
		_data = null;
	}

	/**
	 * Constructs a WSASException object with the specified reason.
	 * errorcode defaults to 0.
	 */

	public TransactionException (String reason)
	{
		super(reason);

		_errorCode = 0;
		_data = null;
	}

	/**
	 * Constructs a WSASException object with the specified reason and
	 * errorcode.
	 */

	public TransactionException (String reason, int errorcode)
	{
		super(reason);

		_errorCode = errorcode;
		_data = null;
	}

	/**
	 * Constructs a WSASException object with the specified reason and
	 * object.
	 */

	public TransactionException (String reason, Object obj)
	{
		super(reason);

		_errorCode = 0;
		_data = obj;
	}

	/**
	 * Constructs a WSASException object with the specified object.
	 */

	public TransactionException (Object obj)
	{
		super();

		_errorCode = 0;
		_data = obj;
	}

	/**
	 * @return the errorcode associated with this exception.
	 */

	public final int getErrorCode ()
	{
		return _errorCode;
	}

	/**
	 * @return the data object associated with this exception.
	 */

	public final Object getData ()
	{
		return _data;
	}

	private int    _errorCode;
	private Object _data;

}



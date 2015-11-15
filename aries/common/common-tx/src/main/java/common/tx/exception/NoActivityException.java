package common.tx.exception;

/**
 * Thrown if there is no activity associated with the invoking
 * thread and one needs to be in order to execute the operation.
 * 
 * @author Mark Little (mark.little@arjuna.com)
 * @version $Id: NoActivityException.java,v 1.1 2002/11/25 10:51:43 nmcl Exp $
 * @since 1.0.
 */

public class NoActivityException extends Exception
{

    public NoActivityException ()
    {
	super();
    }

    public NoActivityException (String s)
    {
	super(s);
    }

//    public NoActivityException (String s, int errorcode)
//    {
//	super(s, errorcode);
//    }
    
}



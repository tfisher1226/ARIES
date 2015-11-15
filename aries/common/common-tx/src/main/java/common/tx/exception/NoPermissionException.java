package common.tx.exception;


public class NoPermissionException extends Exception {

    public NoPermissionException ()
    {
	super();
    }

    public NoPermissionException (String s)
    {
	super(s);
    }

//    public NoPermissionException (String s, int errorcode)
//    {
//	super(s, errorcode);
//    }
    
}



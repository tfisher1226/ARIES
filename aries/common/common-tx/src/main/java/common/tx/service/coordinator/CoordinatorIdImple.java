package common.tx.service.coordinator;

import com.arjuna.ats.arjuna.common.Uid;

/**
 * This implementation of CoordinatorId uses the ArjunaCore Uid class.
 *
 * @author Mark Little (mark.little@arjuna.com)
 * @version $Id: CoordinatorIdImple.java,v 1.3 2004/03/15 13:25:13 nmcl Exp $
 * @since 1.0.
 */
public class CoordinatorIdImple extends Uid implements CoordinatorId {

    public CoordinatorIdImple ()
    {
	super();

	_value = stringForm().getBytes();
    }
    
    public CoordinatorIdImple (String id)
    {
	super(id);

	_value = stringForm().getBytes();
    }

    public CoordinatorIdImple (Uid id)
    {
	super(id);

	_value = stringForm().getBytes();
    }
    
    public byte[] value ()
    {
	return _value;
    }
    
    private byte[] _value;
    
}

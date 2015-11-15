package common.tx.util;

import java.rmi.dgc.VMID;


public class MessageId {
	
    private MessageId() {
    }

    public static String getMessageId() {
        return "urn:" + new VMID().toString() ;
    }

}

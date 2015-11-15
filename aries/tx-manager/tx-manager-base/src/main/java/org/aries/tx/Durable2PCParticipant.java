package org.aries.tx;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;


public interface Durable2PCParticipant extends Participant {

    /**
     * Save the state of the particpant to the specified input object stream.
     * @param oos The output output stream.
     * @return true if persisted, false otherwise.
     */
    public boolean saveState(OutputObjectState objectState);
    
    /**
     * Restore the state of the particpant from the specified input object stream.
     * @param ios The Input object stream.
     * @return true if restored, false otherwise.
     */
    public boolean restoreState(InputObjectState objectState);
    
}

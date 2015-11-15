package common.tx.model.participant;

import javax.transaction.SystemException;

import common.tx.vote.Vote;


public interface Participant {

	public String getId();
	
    public Vote prepare() throws SystemException;

    public void commit() throws SystemException;

    public void rollback() throws SystemException;

    /**
     * During recovery the participant can enquire as to the status of the
     * transaction it was registered with. If that transaction is no longer
     * available (has rolled back) then this operation will be invoked by the
     * coordination service.
     */
    public void unknown() throws SystemException;

    /**
     * During recovery the participant can enquire as to the status of the
     * transaction it was registered with. If an error occurs (e.g., the
     * transaction service is unavailable) then this operation will be invoked.
     */
    void error() throws SystemException;
    
}

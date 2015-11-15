package org.aries.tx.service.subordinate;

import org.aries.tx.Volatile2PCParticipant;

import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.vote.Aborted;
import common.tx.vote.Prepared;
import common.tx.vote.Vote;


/**
 * A volatile participant registered on behalf of an interposed WS-AT coordinator in order to ensure that
 * volatile participants in the subtransaction are prepared at the right time.
 */
public class SubordinateVolatile2PCStub implements Volatile2PCParticipant {
	
    private SubordinateATCoordinator coordinator;

	
    public SubordinateVolatile2PCStub(SubordinateATCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public String getParticipantId() {
    	throw new RuntimeException("NotSupported");
    }
    
    public String getCoordinatorId() {
    	throw new RuntimeException("NotSupported");
    }
    
    /**
     * This will be called when the parent coordinator is preparing its volatile participants and should ensure
     * that the interposed cooordinator does the same.
     *
     * @return the Vote returned by the subordinate coordinator.
     * @throws WrongStateException if the subordinate coordinator does the same
     * @throws SystemException if the subordinate coordinator does the same
     */
    public Vote prepare() throws WrongStateException, SystemException {
        if (coordinator.prepareVolatile()) {
            return new Prepared();
        } else {
            return new Aborted();
        }
    }

    /**
     * this is called as part of the after completion processing and should ensure that the interposed
     * coordinator performs its afterCompletion processing
     * @throws WrongStateException
     * @throws SystemException
     */

    public void commit() throws WrongStateException, SystemException {
        coordinator.commitVolatile();
    }

    /**
     * this is called as part of the after completion processing and should ensure that the interposed
     * coordinator performs its afterCompletion processing
     * @throws WrongStateException
     * @throws SystemException
     */
    public void rollback() throws WrongStateException, SystemException {
        coordinator.rollbackVolatile();
    }

    /**
     * This should never get called.
     * @throws SystemException
     */
    public void unknown() throws SystemException {
    	throw new SystemException("Not supported");
    }

    /**
     * This should never get called.
     * @throws SystemException
     */
    public void error() throws SystemException {
    	throw new SystemException("Not supported");
    }

}

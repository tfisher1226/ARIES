package org.aries.tx;

import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.vote.Vote;


public interface Participant {

	public String getParticipantId();

	public String getCoordinatorId();

	public Vote prepare() throws WrongStateException, SystemException;

	public void commit() throws WrongStateException, SystemException;

	public void rollback() throws WrongStateException, SystemException;

	public void unknown() throws SystemException;

	public void error() throws SystemException;

	//public void invalidate();

}

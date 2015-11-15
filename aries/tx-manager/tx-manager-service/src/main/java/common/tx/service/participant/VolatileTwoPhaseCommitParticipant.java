package common.tx.service.participant;

import org.aries.tx.Synchronization;
import org.aries.tx.Volatile2PCParticipant;

import common.tx.exception.HeuristicConfirmException;
import common.tx.exception.HeuristicHazardException;
import common.tx.exception.HeuristicMixedException;
import common.tx.exception.InvalidParticipantException;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.service.coordinator.CoordinationResult;
import common.tx.vote.Prepared;
import common.tx.vote.ReadOnly;
import common.tx.vote.Vote;


public class VolatileTwoPhaseCommitParticipant implements Synchronization {

	private Volatile2PCParticipant participant;

	private boolean readonly;


	public VolatileTwoPhaseCommitParticipant(Volatile2PCParticipant participant) {
		this.participant = participant;
	}

	/**
	 * The transaction that the instance is enrolled with is about to commit.
	 */
	public void beforeCompletion () throws SystemException {
		try {
			if (participant != null) {
				Vote vote = participant.prepare();

				if (vote instanceof ReadOnly) {
					readonly = true;
					
				} else {
					if (vote instanceof Prepared) {
						// do nothing
					} else
						throw new SystemException();
				}
			} else
				throw new SystemException();
			
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * The transaction that the instance is enrolled with has completed and the
	 * state in which is completed is passed as a parameter.
	 */
	public void afterCompletion (int status) throws SystemException {
		if (!readonly) {
			try {
				switch (status) {
				case CoordinationResult.CONFIRMED:
					confirm();
					break;
				default:
					cancel();
					break;
				}
			} catch (SystemException e) {
				throw e;
			} catch (Exception e) {
				throw new SystemException(e);
			}
		}
	}

	private final void confirm () throws SystemException {
		if (participant != null) {
			try {
				participant.commit();
			} catch (WrongStateException e) {
				throw new SystemException(e);
			}
			
			/*
			 * catch (com.arjuna.mw.wst.exceptions.HeuristicHazardException ex) {
			 * throw new HeuristicHazardException(ex.toString()); } catch
			 * (com.arjuna.mw.wst.exceptions.HeuristicMixedException ex) { throw
			 * new HeuristicMixedException(ex.toString()); } catch
			 * (com.arjuna.mw.wst.exceptions.HeuristicRollbackException ex) {
			 * throw new HeuristicCancelException(ex.toString()); }
			 */
		}
		else
			throw new SystemException("Invalid participant");
	}

	private final void cancel() throws InvalidParticipantException, WrongStateException, HeuristicHazardException, HeuristicMixedException, HeuristicConfirmException, SystemException {
		if (participant != null) {
			try {
				participant.rollback();
			} catch (WrongStateException e) {
				throw new SystemException(e);
			}
			
			/*
			 * catch (com.arjuna.mw.wst.exceptions.HeuristicHazardException ex) {
			 * throw new HeuristicHazardException(ex.toString()); } catch
			 * (com.arjuna.mw.wst.exceptions.HeuristicMixedException ex) { throw
			 * new HeuristicMixedException(ex.toString()); } catch
			 * (com.arjuna.mw.wst.exceptions.HeuristicCommitException ex) {
			 * throw new HeuristicConfirmException(ex.toString()); }
			 */
		} else
			throw new SystemException("Invalid participant");
	}

}

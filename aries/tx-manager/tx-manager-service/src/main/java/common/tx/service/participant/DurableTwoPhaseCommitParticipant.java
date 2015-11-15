package common.tx.service.participant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.Durable2PCParticipant;
import org.aries.tx.ParticipantInternal;

import common.tx.exception.HeuristicCancelException;
import common.tx.exception.HeuristicHazardException;
import common.tx.exception.HeuristicMixedException;
import common.tx.exception.InvalidParticipantException;
import common.tx.exception.SystemCommunicationException;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.vote.Prepared;
import common.tx.vote.ReadOnly;
import common.tx.vote.Vote;
import common.tx.vote.VoteCancel;
import common.tx.vote.VoteConfirm;
import common.tx.vote.VoteReadOnly;


public class DurableTwoPhaseCommitParticipant implements ParticipantInternal {

	private static Log log = LogFactory.getLog(DurableTwoPhaseCommitParticipant.class);

	private Durable2PCParticipant participant;

	private String coordinatorId;

	private boolean readonly;

	private boolean rolledback;


	// default ctor for crash recovery
	public DurableTwoPhaseCommitParticipant() {
		//nothing for now
	}

	public DurableTwoPhaseCommitParticipant(Durable2PCParticipant participant, String coordinatorId) {
		this.participant = participant;
		this.coordinatorId = coordinatorId;
	}

	public String id() throws SystemException {
		return coordinatorId;
	}

	public Vote prepare() throws InvalidParticipantException, WrongStateException, HeuristicHazardException, HeuristicMixedException, SystemException {
		try {
			if (participant != null) {
				Vote vt = participant.prepare();

				if (vt instanceof ReadOnly) {
					readonly = true;
					return new VoteReadOnly();

				} else {
					if (vt instanceof Prepared) {
						return new VoteConfirm();
					} else {
						rolledback = true;
						return new VoteCancel();
					}
				}
			} else
				return new VoteCancel();

		} catch (WrongStateException e) {
			throw new SystemException(e);
		}

		/*
		 * catch (com.arjuna.mw.wst.exceptions.HeuristicHazardException ex {
		 * throw new HeuristicHazardException(ex.toString()); } catch
		 * (com.arjuna.mw.wst.exceptions.HeuristicMixedException ex) { throw new
		 * HeuristicMixedException(ex.toString()); }
		 */
		catch (SystemException e) {
			if (e instanceof SystemCommunicationException) {
				// log an error here or else the participant may be left hanging
				// waiting for a prepare
				String message = "Timeout attempting to prepare transaction participant: "+coordinatorId;
				log.error(message);
				throw new SystemCommunicationException(message);
			} else {
				throw new SystemException(e);
			}
		}
	}

	/**
	 * attempt to commit the participant
	 *
	 */
	public void confirm() throws SystemException {
		if (participant != null) {
			try {
				if (!readonly) {
					participant.commit();
				}

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
			catch (SystemException e) {
				if (e instanceof SystemCommunicationException) {
					// log an error here -- we will end up writing a heuristic transaction record too
					log.error("Timeout attempting to commit transaction participant: "+coordinatorId);
					throw new SystemCommunicationException(e.toString());
				}
				throw new SystemException(e);
			}
		} else
			throw new SystemException("Invalid participant");
	}

	public void cancel() throws SystemException {
		if (participant != null) {
			try {
				if (!rolledback)
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
			catch (SystemException e) {
				if (e instanceof SystemCommunicationException) {
					// log an error here -- if the participant is dead it will retry anyway
					log.error("Timeout attempting to cancel transaction participant: "+coordinatorId);
					throw new SystemCommunicationException(e.toString());
				} else {
					throw new SystemException(e);
				}
			}
		}
		else
			throw new SystemException("Invalid participant");
	}

	public void confirmOnePhase() throws SystemException {
		if (participant != null) {
			Vote v = null;

			try {
				v = prepare();
				
			} catch (Exception e) {
				// either the prepare timed out or the participant was invalid or in an
				// invalid state
				log.error("Error", e);
				v = new VoteCancel();
			}

			if (v instanceof VoteReadOnly) {
				readonly = true;
				
			} else if (v instanceof VoteCancel) {
				rolledback = false;

				// TODO only do this if we didn't return VoteCancel

				try {
					cancel();
				} catch (SystemCommunicationException e) {
					// if the rollback times out as well as the prepare we
					// return an exception which indicates a failed transaction
				}
				throw new SystemException("Participant cancelled");
				
			} else {
				if (v instanceof VoteConfirm) {
					try {
						confirm();
					} catch (HeuristicHazardException e) {
						throw e;
					} catch (HeuristicMixedException e) {
						throw e;
					} catch (HeuristicCancelException e) {
						throw e;
					} catch (Exception e) {
						throw new HeuristicHazardException();
					}
					
				} else {
					cancel(); // TODO error
					throw new HeuristicHazardException();
				}
			}
		} else
			throw new SystemException("Invalid participant");
	}

	public void forget() throws SystemException {
	}

	public void unknown() throws SystemException {
		/*
		 * If the transaction is unknown, then we assume it rolled back.
		 */
		try {
			cancel();
		} catch (Exception e) {
			// TODO
		}
	}

	//	public boolean save_state (OutputObjectState os) {
	//        return PersistableParticipantHelper.save_state(os, _resource) ;
	//	}
	//
	//	public boolean restore_state (InputObjectState os) {
	//        _resource = (Durable2PCParticipant) PersistableParticipantHelper.restore_state(os) ;
	//        return true ;
	//	}

}

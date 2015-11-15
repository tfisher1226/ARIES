package org.aries.tx;

import java.io.PrintWriter;

import org.aries.tx.coordinator.CoordinatorIdImple;

import com.arjuna.ats.arjuna.ObjectType;
import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.coordinator.AbstractRecord;
import com.arjuna.ats.arjuna.coordinator.RecordType;
import com.arjuna.ats.arjuna.coordinator.TwoPhaseOutcome;
import common.tx.exception.HeuristicCancelException;
import common.tx.exception.HeuristicConfirmException;
import common.tx.exception.HeuristicHazardException;
import common.tx.exception.HeuristicMixedException;
import common.tx.exception.InvalidParticipantException;
import common.tx.exception.NotSupportedException;
import common.tx.exception.ParticipantCancelledException;
import common.tx.exception.SystemCommunicationException;
import common.tx.exception.SystemException;
import common.tx.exception.WrongStateException;
import common.tx.vote.Vote;
import common.tx.vote.VoteConfirm;
import common.tx.vote.VoteReadOnly;

/**
 * Arjuna abstract record to handle two-phase participants.
 *
 * @author Mark Little (mark.little@arjuna.com)
 * @version $Id: ParticipantRecord.java,v 1.11 2005/06/09 09:41:27 nmcl Exp $
 */
public class ParticipantRecord extends AbstractRecord {

	private ParticipantInternal _resourceHandle;

	private CoordinatorIdImple _coordId;

	private boolean _rolledback = false;

	private boolean _readonly = false;

	private long _timeout;


	/*
	 * Constructor used by crash recovery.
	 */
	public ParticipantRecord() {
		_resourceHandle = null;
		_timeout = 0;
		_coordId = null;
	}

	public ParticipantRecord (ParticipantInternal theResource, Uid id) {
		super(id, null, ObjectType.ANDPERSISTENT);
		_resourceHandle = theResource;
		_timeout = 0;
		_coordId = new CoordinatorIdImple(id);
        //if (theResource == null)
            //wscfLogger.i18NLogger.warn_model_twophase_arjunacore_ParticipantRecord_1(order());
	}

	public String type() {
		return "/StateManager/AbstractRecord/WSCF/ArjunaCore/ParticipantRecord";
	}

	public int typeIs() {
		// TODO add specific record type.
		return RecordType.XTS_WSAT_RECORD;
	}

	public static AbstractRecord create() {
		return new ParticipantRecord();
	}

	public void remove(AbstractRecord toDelete) {
		toDelete = null;
	}

	public void print(PrintWriter writer) {
		super.print(writer);
		writer.print("ParticipantRecord");
		writer.print(_resourceHandle);
	}

	public boolean propagateOnCommit() {
		return true;
	}

	/**
	 * The internal value.
	 */
	public Object value() {
		return _resourceHandle;
	}

	/**
	 * Set the internal value. Not allowed for this class.
	 */
	public void setValue(Object object) {
		throw new NotSupportedException();
	}

	/**
	 * The record is being driven through nested rollback.
	 */
	public int nestedAbort() {
		try {
			if (_resourceHandle != null)
				return TwoPhaseOutcome.FINISH_ERROR;
			return TwoPhaseOutcome.FINISH_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.FINISH_ERROR;
        }
	}

	/**
	 * The record is being driven through nested commit.
	 */
	public int nestedCommit() {
		try {
			if (_resourceHandle != null)
				return TwoPhaseOutcome.FINISH_ERROR;
			return TwoPhaseOutcome.FINISH_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.FINISH_ERROR;
        }
	}

	/**
	 * The record is being driven through nested prepare.
	 */
	public int nestedPrepare() {
		try {
			if (_resourceHandle != null)
				return TwoPhaseOutcome.FINISH_ERROR;
			return TwoPhaseOutcome.PREPARE_NOTOK;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.HEURISTIC_HAZARD;
        }
	}

	/**
	 * The record is being driven through top-level rollback.
	 */
	public int topLevelAbort() {
		try {
			if (_resourceHandle != null) {
				try {
					if (!_rolledback)
						_resourceHandle.cancel();
				} catch (InvalidParticipantException e) {
					return TwoPhaseOutcome.FINISH_ERROR;
				} catch (WrongStateException e) {
					return TwoPhaseOutcome.FINISH_ERROR;
				} catch (HeuristicHazardException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				} catch (HeuristicMixedException e) {
					return TwoPhaseOutcome.HEURISTIC_MIXED;
				} catch (HeuristicConfirmException e) {
					return TwoPhaseOutcome.HEURISTIC_COMMIT;
				} catch (SystemException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				}

				return TwoPhaseOutcome.FINISH_OK;
			} else
				return TwoPhaseOutcome.FINISH_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.FINISH_ERROR;
        }
	}

	/**
	 * The record is being driven through top-level commit.
	 */
	public int topLevelCommit() {
		try {
			if (_resourceHandle != null) {
				try {
					if (!_rolledback && !_readonly)
						_resourceHandle.confirm();
					if (_rolledback)
						throw new HeuristicHazardException();
				} catch (InvalidParticipantException e) {
					return TwoPhaseOutcome.FINISH_ERROR;
				} catch (WrongStateException e) {
					return TwoPhaseOutcome.NOT_PREPARED; // should be HEURISTIC_HAZARD?
				} catch (HeuristicHazardException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				} catch (HeuristicMixedException e) {
					return TwoPhaseOutcome.HEURISTIC_MIXED;
				} catch (HeuristicCancelException e) {
					return TwoPhaseOutcome.HEURISTIC_ROLLBACK;
				} catch (SystemCommunicationException e) {
                    return TwoPhaseOutcome.FINISH_ERROR;
                } catch (SystemException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				}

				return TwoPhaseOutcome.FINISH_OK;
			} else
				return TwoPhaseOutcome.FINISH_ERROR;
			
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.FINISH_ERROR;
        }
	}

	/**
	 * The record is being driven through top-level prepare.
	 */
	public int topLevelPrepare() {
		try {
			if (_resourceHandle != null) {
				if (_rolledback)
					return TwoPhaseOutcome.PREPARE_NOTOK;

				if (_readonly)
					return TwoPhaseOutcome.PREPARE_READONLY;

				try {
					Vote res = _resourceHandle.prepare();
					if (res instanceof VoteConfirm) {
						return TwoPhaseOutcome.PREPARE_OK;
					} else {
						if (res instanceof VoteReadOnly) {
							_readonly = true;
							return TwoPhaseOutcome.PREPARE_READONLY;
						} else {
							_rolledback = true;
							return TwoPhaseOutcome.PREPARE_NOTOK;
						}
					}
				} catch (InvalidParticipantException e) {
					return TwoPhaseOutcome.FINISH_ERROR;
				} catch (WrongStateException e) {
					return TwoPhaseOutcome.FINISH_ERROR;
				} catch (HeuristicHazardException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				} catch (HeuristicMixedException e) {
					return TwoPhaseOutcome.HEURISTIC_MIXED;
				} catch (SystemCommunicationException e) {
                    // if prepare timed out then we return error so it goes back on the
                    // prepare list and is rolled back
                    return TwoPhaseOutcome.FINISH_ERROR;
                } catch (SystemException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				}
			} else
				return TwoPhaseOutcome.PREPARE_NOTOK;
			
		} catch (Exception e) {
			e.printStackTrace();
			return TwoPhaseOutcome.PREPARE_NOTOK;
        }
	}

	/**
	 * The record is being driven through nested commit and is the only
	 * resource.
	 */
	public int nestedOnePhaseCommit() {
		try {
			if (_resourceHandle != null)
				return TwoPhaseOutcome.FINISH_ERROR;
			return TwoPhaseOutcome.FINISH_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.FINISH_ERROR;
        }
	}

	/**
	 * The record is being driven through top-level commit and is the only
	 * resource.
	 */
	public int topLevelOnePhaseCommit() {
		try {
			if (_resourceHandle != null) {
				if (_rolledback)
					return TwoPhaseOutcome.ONE_PHASE_ERROR;

				if (_readonly)
					return TwoPhaseOutcome.FINISH_OK;

				try {
					_resourceHandle.confirmOnePhase();
				} catch (InvalidParticipantException e) {
					return TwoPhaseOutcome.ONE_PHASE_ERROR;
				} catch (WrongStateException e) {
					return TwoPhaseOutcome.ONE_PHASE_ERROR;
				} catch (HeuristicHazardException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				} catch (HeuristicMixedException e) {
					return TwoPhaseOutcome.HEURISTIC_MIXED;
				} catch (HeuristicCancelException e){
					return TwoPhaseOutcome.HEURISTIC_ROLLBACK;
				} catch (ParticipantCancelledException e) {
					return TwoPhaseOutcome.ONE_PHASE_ERROR;
				} catch (SystemException e) {
					return TwoPhaseOutcome.HEURISTIC_HAZARD;
				}

				return TwoPhaseOutcome.FINISH_OK;
				
			} else
				return TwoPhaseOutcome.ONE_PHASE_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
            return TwoPhaseOutcome.ONE_PHASE_ERROR;
        }
	}

	/**
	 * The record generated a heuristic and can now forget about it.
	 */
	public boolean forgetHeuristic() {
		try {
			if (_resourceHandle != null) {
				try {
					_resourceHandle.forget();
				} catch (InvalidParticipantException e) {
					return false;
				} catch (WrongStateException e) {
					return false;
				} catch (SystemException e) {
					return false;
				}

				return true;
			} else {
                //log
            }
		} catch (Exception e) {
			e.printStackTrace();
        }

		return false;
	}


//	public boolean restore_state (InputObjectState os, int t)
//	{
//		boolean result = super.restore_state(os, t);
//
//		if (result)
//		{
//			try
//			{
//                String resourcehandleImplClassName = os.unpackString();
//                Class clazz = ClassUtil.loadClass(resourcehandleImplClassName);
//                //Class clazz = ClassLoaderHelper.forName(ParticipantRecord.class, resourcehandleImplClassName);
//                _resourceHandle = (ParticipantInternal)clazz.newInstance();
//
//                result = _resourceHandle.restore_state(os);
//
//				if (result)
//					_timeout = os.unpackLong();
//
//				/*
//				 * TODO: unpack qualifiers and coord id.
//				 */
//			}
//			catch (Exception ex) {
//                //wscfLogger.i18NLogger.warn_model_twophase_arjunacore_ParticipantRecord_13(ex);
//
//                result = false;
//            }
//		}
//
//		return result;
//	}


//	public boolean save_state (OutputObjectState os, int t)
//	{
//		boolean result = super.save_state(os, t);
//
//		if (result)
//		{
//			try
//			{
//                os.packString(_resourceHandle.getClass().getName()); // TODO: a shorter value whould be more efficient.
//                result = _resourceHandle.save_state(os);
//
//				if (result)
//					os.packLong(_timeout);
//
//				/*
//				 * TODO: pack qualifiers and coord id.
//				 */
//			}
//			catch (Exception ex) {
//                //wscfLogger.i18NLogger.warn_model_twophase_arjunacore_ParticipantRecord_14(ex);
//
//                result = false;
//            }
//		}
//
//		return result;
//	}

	public boolean doSave() {
		return true;
	}

	public void merge(AbstractRecord record) {
	}

	public void alter(AbstractRecord record) {
	}

	public boolean shouldAdd(AbstractRecord record) {
		return false;
	}

	public boolean shouldAlter(AbstractRecord record) {
		return false;
	}

	public boolean shouldMerge (AbstractRecord record) {
		return false;
	}

	public boolean shouldReplace (AbstractRecord record) {
		return false;
	}

	public final void rolledback() {
		_rolledback = true;
	}

	public final void readonly() {
		_readonly = true;
	}

}

package common.tx.service.coordinator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.arjuna.ats.arjuna.common.Uid;
import com.arjuna.ats.arjuna.coordinator.ActionStatus;


/**
 * An implementation of the ArjunaCore synchronization interface.
 * Synchronizations take part in the pre- and post- two-phase protocol and
 * are not persistent.
 */
public class SynchronizationRecord implements com.arjuna.ats.arjuna.coordinator.SynchronizationRecord {

	private static Log log = LogFactory.getLog(SynchronizationRecord.class);

	private Synchronization _resourceHandle;

	private CoordinatorIdImple _id;


	/**
	 * Constructor.
	 *
	 * @param theResource is the proxy that allows us to call out to the
	 * object.
	 */
	public SynchronizationRecord(Synchronization theResource, Uid id) {
		_resourceHandle = theResource;
		_id = new CoordinatorIdImple(id);
		if (_resourceHandle == null)
			log.warn("Participant not specified: "+_id);
	}

	public boolean beforeCompletion() {
		if (_resourceHandle != null) {
			try {
				_resourceHandle.beforeCompletion();
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public boolean afterCompletion (int status) {
		if (_resourceHandle != null) {
			try {
				_resourceHandle.afterCompletion(convertStatus(status));
			} catch (Exception ex) {
			}

			return true;
		}

		return false;
	}

	public Uid get_uid() {
		return _id;
	}

	public int compareTo(Object object) {
		SynchronizationRecord sr = (SynchronizationRecord) object;
		if (!_id.equals(sr.get_uid()))
			return _id.lessThan(sr.get_uid()) ? -1 : 1;
		return 0;
	}

	private int convertStatus(int result) {
		switch (result) {
		case ActionStatus.COMMITTED:
		case ActionStatus.COMMITTING:
			return CoordinationResult.CONFIRMED;
		case ActionStatus.ABORTED:
		case ActionStatus.ABORTING:
		default:
			return CoordinationResult.CANCELLED;
		}
	}

}

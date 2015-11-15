package common.tx.service.coordinator;

import org.aries.tx.Synchronization;

import common.tx.exception.SystemException;
import common.tx.service.registration.RegistrarImple;


public class CleanupSynchronization implements Synchronization {

	private String coordinatorId;

	private RegistrarImple registrar;


	public CleanupSynchronization(String coordinatorId, RegistrarImple registrar) {
		this.coordinatorId = coordinatorId;
		this.registrar = registrar;
	}

	public void beforeCompletion() throws SystemException {
		// do nothing
	}

	public void afterCompletion(int status) throws SystemException {
		try {
			registrar.disassociate(coordinatorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SystemException(ex.toString());
		}
	}

}

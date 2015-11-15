package org.aries.tx.service.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIListener extends Remote {

	public <T extends Serializable> T invoke(Serializable payload, String correlationId, long timeout) throws RemoteException;

}

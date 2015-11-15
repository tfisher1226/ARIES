package common.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIClientRemote extends Remote {

	public void deliver(String correlationId, Serializable response) throws RemoteException;

	public void deliver(String correlationId, Exception exception) throws RemoteException;

}

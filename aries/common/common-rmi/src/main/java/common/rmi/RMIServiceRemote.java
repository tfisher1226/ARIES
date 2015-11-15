package common.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServiceRemote extends Remote {

	public void send(Serializable message, String correlationId) throws RemoteException;

	public void invoke(Serializable message, String correlationId) throws RemoteException;

	public void addSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException;

	public void removeSubscriber(SubscriberDescripter subscriberDescripter) throws RemoteException;

	public void reset() throws RemoteException;

}

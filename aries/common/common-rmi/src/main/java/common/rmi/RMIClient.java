package common.rmi;

import java.io.Serializable;
import java.util.concurrent.Future;

import org.aries.Handler;
import org.aries.client.Client;


public interface RMIClient extends Client {

	public void initialize() throws Exception;
	
	public void close() throws Exception;

	public void send(Serializable message);

	public void send(Serializable message, String correlationId);

	public <T extends Serializable> T receive(long timeout);

	public <T extends Serializable> T receive(String correlationId, long timeout);

	public <T extends Serializable> Future<T> receive(Handler<T> handler);

	public <T extends Serializable> Future<T> receive(String correlationId, Handler<T> handler);

	public <T extends Serializable> T invoke(Serializable request, String correlationId, long timeout);

	public <T extends Serializable> Future<T> invoke(Serializable request, String correlationId, Handler<T> handler);

}

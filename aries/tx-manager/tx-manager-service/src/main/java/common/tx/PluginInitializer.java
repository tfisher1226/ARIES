package common.tx;


public interface PluginInitializer {

	 public void startup(String host, int port);
	 
	 public void shutdown();
	 
}

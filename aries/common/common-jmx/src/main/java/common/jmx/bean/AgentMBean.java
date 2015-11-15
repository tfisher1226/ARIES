package common.jmx.bean;


public interface AgentMBean {

	public String getName();
	
	public boolean isRunning();
	
	public void startService(String serviceName);
	
	public void stopService(String serviceName);
	
}

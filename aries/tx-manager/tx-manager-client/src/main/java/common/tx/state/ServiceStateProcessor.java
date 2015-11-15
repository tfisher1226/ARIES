package common.tx.state;


public interface ServiceStateProcessor<T extends ServiceState>  {

	public boolean validateState(T state);

	public void updateState(T state);
	
	public void resetState(T state);

}

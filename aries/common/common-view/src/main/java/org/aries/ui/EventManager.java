package org.aries.ui;


public interface EventManager<T> {

	public void fireAddEvent();

	public void fireAddEvent(Object object);
	
	public void fireAddedEvent();

	public void fireAddedEvent(Object object);
	
	public void fireSaveEvent();

	public void fireSaveEvent(Object object);
	
	public void fireSavedEvent();

	public void fireSavedEvent(Object object);
	
	public void fireRemoveEvent();

	public void fireRemoveEvent(Object object);
	
	public void fireRemovedEvent();
	
	public void fireRemovedEvent(Object object);
	
	public void fireRefreshEvent();

	public void fireRefreshEvent(Object object);
	
	public void fireRefreshedEvent();
	
	public void fireRefreshedEvent(Object object);
	
	public void fireCancelEvent();

	public void fireCancelEvent(Object object);
	
	public void fireCancelledEvent();

	public void fireCancelledEvent(Object object);
	
	public void fireSelectEvent();

	public void fireSelectEvent(Object object);
	
	public void fireSelectedEvent();

	public void fireSelectedEvent(Object object);
	
	public void fireClearSelectionEvent();

	public void fireClearSelectionEvent(String source);

}

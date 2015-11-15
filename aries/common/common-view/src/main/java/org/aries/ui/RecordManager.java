package org.aries.ui;


public interface RecordManager<T> {

	public void initialize();
	
	public String viewRecord();
	
	public String newRecord();
	
	public String editRecord();

}

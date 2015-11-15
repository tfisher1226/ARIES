package org.aries.ui;

import java.util.Collection;
import java.util.List;


public abstract class AbstractListManagerOLD<T> extends AbstractRecordManager<T> {

	public abstract List<T> getList();
	
	public abstract T getSelection();

	//public abstract boolean hasSelection();

	protected abstract void refreshModel();

	public abstract boolean validate(T record);
	
	private long rowCount;

	private int pageSize = 20;
	
	private int pageIndex;

	private String searchString;
	

	@Override
	public boolean isEmpty() {
		return isEmpty(getList());
	}

	public boolean isEmpty(T record) {
		return record == null;
	}

	public boolean isEmpty(Collection<T> recordList) {
		return recordList == null || recordList.isEmpty();
	}
	
	public String toString (T record) {
		return null;
	}
	
	public T createRecord() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<T> getRecordClass() {
		T record = getRecord();
		if (record != null)
			return (Class<T>) record.getClass();
		return null;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public boolean isNextPageAvailable() {
		return isPopulated() && rowCount > ((pageIndex+1) * pageSize);
	}

	public boolean isPreviousPageAvailable() {
		return isPopulated() && pageIndex > 0;
	}

	public boolean isPopulated() {
		List<?> model = getList();
		boolean status = model != null && model.size() > 0;
		//log.info("-------- isPopulated: "+status);
		return status;
	}
	
	public String getPageStatus() {
		if (isPopulated()) {
			long pageCount = (rowCount / pageSize) + 1;
			//log.info("-------- getPageStatus: "+rowCount+", " + (int) (rowCount/pageSize));
			return "Page " + (pageIndex+1) + " of " + pageCount;
		}
		return "";
	}

	
	public void find() {
		pageIndex = 0;
		refreshModel();
	}

	/**
	 * Carries out fresh retrieval of records (model) for display.
	 * Executes on demand from user.
	 * @return The path to next page or null.
	 */
	//@BypassInterceptors
	//TODO change return type to String
	public String refresh() {
		pageIndex = 0;
		refreshModel();
		return null;
	}

	/**
	 * Carries out fresh retrieval of next page of records. 
	 * This is done in preparation before re-rendering of the display.
	 * Executes on demand from user.
	 * @return The path to next page or null.
	 */
	//@BypassInterceptors
	//TODO change return type to String
	public void nextPage() {
		if (((pageIndex+1) * pageSize) < rowCount)
			pageIndex++;
		refreshModel();
	}

	/**
	 * Carries out fresh retrieval of previous page of records. 
	 * This is done in preparation before re-rendering of the display.
	 * Executes on demand from user.
	 * @return The path to next page or null.
	 */
	//@BypassInterceptors
	//TODO change return type to String
	public void previousPage() {
		if (pageIndex > 0)
			pageIndex--;
		refreshModel();
	}

}

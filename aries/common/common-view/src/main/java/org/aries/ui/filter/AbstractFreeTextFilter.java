package org.aries.ui.filter;



public abstract class AbstractFreeTextFilter<T> implements SortFilter<T> {

	protected String filterValue;

	protected SortMode sortMode;
	
	
	protected AbstractFreeTextFilter() {
		filterValue = "";
		sortMode = SortMode.Unsorted;
	}
	
	@Override
	public SortMode getSortMode() {
		return sortMode;
	}
	
	@Override
	public void setSortMode(SortMode sortMode) {
		this.sortMode = sortMode;
	}
	
	public Object getFilterValue() {
		return filterValue;
	}
	
	public void setFilterValue(Object filterValue) {
		if (!(filterValue instanceof String)) {
			this.filterValue = "";
			return;
		}
		
		this.filterValue = (String) filterValue;
	}
}

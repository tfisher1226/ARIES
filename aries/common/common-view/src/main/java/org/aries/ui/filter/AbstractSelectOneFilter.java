package org.aries.ui.filter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;



public abstract class AbstractSelectOneFilter<T> implements SortFilter<T> {

	protected static final SelectItem ALL_SELECT_ITEM = new SelectItem("All", "All");
	
	protected List<SelectItem> selectItems;

	protected SortMode sortMode;
	
	protected String filterValue;
	
	
	protected AbstractSelectOneFilter() {
		sortMode = SortMode.Unsorted;
		filterValue = ALL_SELECT_ITEM.getLabel();
	}
	
	public List<SelectItem> getSelectItems() {
		// Return a defensive copy in case Seam or JSF performs some
		// unpredictable behavior.
		return new ArrayList<SelectItem>(selectItems);
	}

	@Override
	public SortMode getSortMode() {
		return sortMode;
	}
	
	@Override
	public void setSortMode(SortMode sortMode) {
		this.sortMode = sortMode;
	}
	
	@Override
	public Object getFilterValue() {
		return filterValue;
	}
	
	@Override
	public void setFilterValue(Object filterValue) {
		if (!(filterValue instanceof String)) {
			this.filterValue = ALL_SELECT_ITEM.getLabel();
			return;
		}
		
		this.filterValue = (String) filterValue;
	}
	
}

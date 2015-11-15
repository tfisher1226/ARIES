package org.aries.ui.filter;

import java.util.Date;

import org.aries.util.compare.Comparisons;



public abstract class AbstractDateFilter<T> implements SortFilter<T> {

	protected Date filterValue;
	
	protected SortMode sortMode;
	
	protected AbstractDateFilter() {
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

    @Override
    public int compareRows(T row1, T row2) {
        Object temp1 = getColumnValue(row1);
        Object temp2 = getColumnValue(row2);

        Date date1 = temp1 instanceof Date ? (Date) temp1 : null;
        Date date2 = temp2 instanceof Date ? (Date) temp2 : null;

        return Comparisons.compareDates(date1, date2);
    }

	public Object getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(Object filterDate) {
		if (!(filterDate instanceof Date)) {
			this.filterValue = null;
			return;
		}
		
		this.filterValue = (Date) filterDate;
	}

}

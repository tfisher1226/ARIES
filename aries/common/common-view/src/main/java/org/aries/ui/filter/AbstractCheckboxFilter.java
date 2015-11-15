package org.aries.ui.filter;



public abstract class AbstractCheckboxFilter<T> implements SortFilter<T> {

	private Boolean filterValue;

    private SortMode sortMode;

    
    protected AbstractCheckboxFilter() {
        filterValue = false;
        sortMode = SortMode.Unsorted;
    }

    @Override
    public SortMode getSortMode() {
        return sortMode;
    }

    @Override
    public void setSortMode(SortMode sortMode) {
        if (sortMode == null) {
            this.sortMode = SortMode.Unsorted;
            return;
        }

        this.sortMode = sortMode;
    }

    @Override
    public Object getFilterValue() {
        return filterValue;
    }

    @Override
    public void setFilterValue(Object filterValue) {
        if (!(filterValue instanceof Boolean)) {
            this.filterValue = false;
            return;
        }

        this.filterValue = (Boolean) filterValue;
    }
}

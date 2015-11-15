package org.aries.ui.filter;


/**
 * Extracts the desired column value for either sorting or filtering from a table row.
 */
public interface SortFilter<T> {

	enum SortMode {
		Ascending(1, "resource://images/icons/button/sort-asc.png"),
		Descending(-1, "resource://images/icons/button/sort-desc.png"),
		Unsorted(0, "resource://images/icons/button/sort-unsort.png");

		public final String icon;

        /**
         * SortFilters assume the compare methods are always in ascending order. The sort multiplier helps
         * change the sort order by multiplying the value to the value returned by the compare method.
         */
        public final int sortMultiplier;

		SortMode(final int sortMultiplier, final String icon) {
            this.sortMultiplier = sortMultiplier;
			this.icon = icon;
		}
	}

	public String getId();

    /**
     * Determines whether a table row should be displayed based on a user's filter value.
     */
	public boolean filter(T row);

    /**
     * Compares two table rows. Since SortFilterStrategy objects should look at column values, the compare method
     * should get the column value from each table row and compare them.
     */
	public int compareRows(T row1, T row2);

	public SortMode getSortMode();

	public void setSortMode(SortMode sortMode);

	public Object getColumnValue(T row);

	public Object getFilterValue();

	public void setFilterValue(Object filterValue);
	
}

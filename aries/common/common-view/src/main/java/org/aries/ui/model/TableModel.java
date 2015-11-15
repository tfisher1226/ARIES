package org.aries.ui.model;

import java.util.List;

import javax.faces.event.ValueChangeListener;

import org.aries.ui.filter.SortFilter;


public interface TableModel<T> extends ValueChangeListener {

	/**
     * Determines whether the row should be included in the filter result.
     */
	public boolean filter(Object row);

    /**
     * Uses a sort strategy to sort the table model.
     */
    public void sort(SortFilter<T> sortStrategy);

    /**
     * Gets the sort icon for the specified column. This icon is a classpath resource represented as a String.
     */
	public String getSortIconForColumn(String columnId);

    /**
     * The filtered results.
     */
	public List<T> getFilteredModel();

	public void clearFilters();

    /**
     * Adds a row to the table model.
     */
	public void addRow(T row);

    /**
     * Removes the row from the table model.
     */
	public void removeRow(T row);

}

package org.aries.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.filter.SortFilter;
import org.aries.ui.filter.SortFilter.SortMode;


@SuppressWarnings("serial")
public class ListTableModel<T> extends DataModel implements TableModel<T>, Serializable {

	private List<T> dataModel;
	
	private int rowIndex;
	
	private SortFilter<T> filterStrategy;
	
	private SortFilter<T> prevFilterStrategy;
	
	private SortFilter<T> prevSortStrategy;
	
	private Set<T> filteredDataModel;
	
	
	public ListTableModel() {
		initialize(null);
	}
	
	public ListTableModel(List<? extends T> dataModel) {
        initialize(dataModel);
	}

	private void initialize(List<? extends T> dataModel) {
		if (dataModel != null) {
			this.dataModel = new ArrayList<T>(dataModel);
			this.filteredDataModel = new TreeSet<T>(dataModel);
		} else {
			this.dataModel = new ArrayList<T>();
			this.filteredDataModel = new TreeSet<T>();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean filter(Object row) {
		T testRow = (T) row;
		if (filterStrategy == null) {
			return true;
		}
		
		boolean include = filterStrategy.filter(testRow);
		
		// Update the filtered items based on the include result.
		if (include) {
			filteredDataModel.add(testRow);
			
		} else {
			filteredDataModel.remove(testRow);
		}
		
		// Reset the previous filter
		if (prevFilterStrategy != null && !prevFilterStrategy.equals(filterStrategy)) {
			prevFilterStrategy.setFilterValue(null);
		}
		
		prevFilterStrategy = filterStrategy;
		
		return include;
		
	}
	
	public void filter(SortFilter<T> sortFilterStrategy) {
		if (sortFilterStrategy == null) {
			return;
		}
		
		for (T row : dataModel) {
			filter(row);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		String strategyId = event.getComponent().getId();
		SortFilter<T> strategy = (SortFilter<T>) BeanContext.get(strategyId);
		if (strategy == null) {
			//log.warn(String.format("No SortFilterStrategy found for id: '%s'", strategyId));
			return;
		}
		
		filterStrategy = strategy;
		
	}
	
	@Override
	public void sort(final SortFilter<T> sortStrategy) {
		if (sortStrategy == null) {
			throw new IllegalArgumentException("Sort must be called with a SortFilterStrategy.");
		}

		switch(sortStrategy.getSortMode()) {
			case Unsorted:
				sortStrategy.setSortMode(SortMode.Ascending);
				break;
			case Ascending:
				sortStrategy.setSortMode(SortMode.Descending);
				break;
			case Descending:
				sortStrategy.setSortMode(SortMode.Ascending);
		}

        Collections.sort(dataModel, new Comparator<T>() {
            @Override
            public int compare(T row1, T row2) {
                int multiplier = sortStrategy.getSortMode().sortMultiplier;
                return sortStrategy.compareRows(row1, row2) * multiplier;
            }
        });
		
		if (prevSortStrategy != null && !prevSortStrategy.equals(sortStrategy)) {
			prevSortStrategy.setSortMode(SortMode.Unsorted);
		}
		
		prevSortStrategy = sortStrategy;
	}
	
	@Override
	public List<T> getFilteredModel() {
		return new ArrayList<T>(filteredDataModel);
	}
	
	@Override
	public String getSortIconForColumn(String columnId) {
		SortFilter<T> sortFilterStrategy = BeanContext.get(columnId);
		return sortFilterStrategy.getSortMode().icon;
	}
	
	@Override
	public void clearFilters() {
		if (prevFilterStrategy == null) {
			return;
		}
		
		prevFilterStrategy.setFilterValue(null);
		prevFilterStrategy = null;
	}
	
	@Override
	public int getRowCount() {
		return dataModel.size();
	}

	@Override
	public Object getRowData() {
		return dataModel.get(rowIndex);
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public Object getWrappedData() {
		return dataModel;
	}

	@Override
	public boolean isRowAvailable() {
		return isIndexValid(rowIndex);
	}

	@Override
	public void setRowIndex(int index) {
		rowIndex = index;

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setWrappedData(Object data) {
		if (!(data instanceof List)) {
			throw new IllegalArgumentException("Invalid data type. Received " + data.getClass() + " but expected " + List.class);
		}
		
		initialize((List<? extends T>) data);

	}

    @Override
    public void addRow(T row) {
        if (row == null) {
            throw new IllegalArgumentException("Cannot add null row object.");
        }

        dataModel.add(row);
    }

    @Override
    public void removeRow(T row) {
        if (row == null) {
            throw new IllegalArgumentException("Cannot remove a null row object.");
        }

        dataModel.remove(row);
    }
	
	private boolean isIndexValid(int index) {
		return index >= 0 && index < dataModel.size();
	}
	
	
}

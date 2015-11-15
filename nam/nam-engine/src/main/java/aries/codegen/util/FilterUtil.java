package aries.codegen.util;

import java.io.File;

import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;


public class FilterUtil {
	
	public static FilterSetCollection createFilterSetCollection() {
		FilterSetCollection filterSetCollection = new FilterSetCollection();
		return filterSetCollection;
	}

	public static FilterSetCollection createFilterSetCollection(FilterSet filterSet) {
		FilterSetCollection filterSetCollection = new FilterSetCollection();
		filterSetCollection.addFilterSet(filterSet);
		return filterSetCollection;
	}

	public static FilterSetCollection createFilterSetCollection(String filterSetLocation) {
		FilterSetCollection filterSetCollection = createFilterSetCollection();
		FilterSet filterSet = createFilterSet(filterSetLocation);
		if (filterSet.hasFilters())
			filterSetCollection.addFilterSet(filterSet);
		return filterSetCollection;
	}

	public static FilterSet createFilterSet(String filterSetLocation) {
		FilterSet filterSet = new MyFilterSet();
		File directory = new File(filterSetLocation);
		File[] files = directory.listFiles();
		if (files != null) {
			for (int i=0; i < files.length; i++) {
				File file = files[i];
				String path = file.getPath();
				if (path.endsWith(".properties")) {
					filterSet.readFiltersFromFile(file);
				}
			}
		}
		return filterSet;
	}
	
}

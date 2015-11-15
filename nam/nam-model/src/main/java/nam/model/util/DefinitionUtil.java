package nam.model.util;

import java.util.List;

import nam.model.Definition;


public class DefinitionUtil {

	public static String getValueAsString(Definition definition) {
		return getLastSelector(definition);
	}

	public static String getLastSelector(Definition definition) {
		List<String> selectors = definition.getSelectors();
		return getLastSelector(selectors);
	}
	
	public static String getLastSelector(List<String> selectors) {
		if (selectors != null && selectors.size() > 0) {
			String lastSelector = selectors.get(selectors.size() - 1);
			return lastSelector;
		}
		return null;
	}
	
}

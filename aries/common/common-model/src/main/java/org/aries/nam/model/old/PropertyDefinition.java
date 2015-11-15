package org.aries.nam.model.old;

import java.util.ArrayList;
import java.util.List;


public class PropertyDefinition extends ValueDefinition implements PropertyDescripter {

	private static final long serialVersionUID = 1L;
	
	
    @SuppressWarnings("unchecked")
	public List<Object> getValues() {
        if (getValue() == null)
            setValue(new ArrayList<Object>());
        return (List<Object>) getValue();
    }
    
    public void addValue(Object value) {
        getValues().add(value);
    }
    
}

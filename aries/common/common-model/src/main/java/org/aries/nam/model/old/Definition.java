package org.aries.nam.model.old;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Definition implements Descriptor, Serializable {

	private String name;
    
    private String type;
    
    private String description;
    
    private Map<String, PropertyDescripter> properties = new HashMap<String, PropertyDescripter>();


    public String getName() {
        return name;
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
    	this.type = type;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	this.description = description;
    }
   
    public Map<String, PropertyDescripter> getProperties() {
        return properties;
    }

    public void addPropertyDescriptor(PropertyDescripter property) {
        getProperties().put(property.getName(), property);
    }
    
}

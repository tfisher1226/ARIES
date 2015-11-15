package aries.generation.model;

import org.apache.commons.lang.StringUtils;



/**
 * Class modeling an attribute of an instance. An attribute is a
 * member variable of an instance that may or may not be used to
 * uniquely identify the instance depending on whether is is marked 
 * as a key attribute or not.
 */
public class ModelAttribute extends ModelField {

	public ModelAttribute() {
		//nothing for now
	}
	
	public String getRepresentation() {
		String representation = "attribute";
		if (!StringUtils.isEmpty(getStructure()))
			representation += " "+getStructure();
		return representation;
	}
	
}
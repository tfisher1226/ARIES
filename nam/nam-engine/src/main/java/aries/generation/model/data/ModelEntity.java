package aries.generation.model.data;

import java.util.List;

import aries.generation.model.ModelClass;
import aries.generation.model.ModelField;


/**
 * Class modeling an entity bean.
 * 
 */
public class ModelEntity extends ModelClass {
	
	private List<ModelField> idKey;

	public List<ModelField> getIdKey() {
		return idKey;
	}

	public void setIdKey(List<ModelField> idKey) {
		this.idKey = idKey;
	} 

}

package nam.model.model;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Model;
import nam.model.util.ModelUtil;


public class ModelListObject extends AbstractListObject<Model> implements Comparable<ModelListObject>, Serializable {
	
	private Model model;
	
	
	public ModelListObject(Model model) {
		this.model = model;
	}
	
	
	public Model getModel() {
		return model;
	}
	
	@Override
	public Object getKey() {
		return getKey(model);
	}
	
	public Object getKey(Model model) {
		return ModelUtil.getKey(model);
	}
	
	@Override
	public String getLabel() {
		return getLabel(model);
	}
	
	public String getLabel(Model model) {
		return ModelUtil.getLabel(model);
	}
	
	@Override
	public String toString() {
		return toString(model);
	}
	
	@Override
	public String toString(Model model) {
		return ModelUtil.toString(model);
	}
	
	@Override
	public int compareTo(ModelListObject other) {
		Object thisKey = getKey(this.model);
		Object otherKey = getKey(other.model);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ModelListObject other = (ModelListObject) object;
		Object thisKey = ModelUtil.getKey(this.model);
		Object otherKey = ModelUtil.getKey(other.model);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}

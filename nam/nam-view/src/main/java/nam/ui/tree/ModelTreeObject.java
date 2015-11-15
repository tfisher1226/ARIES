package nam.ui.tree;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ModelTreeObject<T> implements Serializable {

	private String id;

	private String type;

	private String label;

	private T object;

	
//	public ModelTreeObject(String label) {
//		this(null, "TEMP", label); 
//	}
	
//	public ModelTreeObject(T object, String label) {
//		this(object, label, object.getClass().getSimpleName()); 
//	}

	public ModelTreeObject(T object, String type, String label) {
		//this.object = JSONUtil.toString(object);
		this.object = object;
		this.type = type;
		this.label = label;
		this.id = type + ":" + label;
	}

	public ModelTreeObject(T object, String id, String type, String label) {
		this(object, type, label);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type= type;
	}

	public String getLabel() {
		if (label != null)
			return label;
		return getType();
	}

	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ModelTreeObject<T> other = (ModelTreeObject) object;
		if (!other.type.equals(this.type))
			return false;
		if (other.object == null && this.object == null)
			return true;
		if (other.object.equals(this.object))
			return true;
		return false;
	}

}

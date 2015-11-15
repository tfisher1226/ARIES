package aries.generation.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import aries.codegen.util.Buf;


public class ModelAnnotation {

	private String label;
	
	private Map<Object, Object> parts;

	private boolean formatted;

	
	public ModelAnnotation() {
		parts = new LinkedHashMap<Object, Object>();
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String source) {
		this.label = source;
	}

	public boolean isFormatted() {
		return formatted;
	}

	public void setFormatted(boolean formatted) {
		this.formatted = formatted;
	}

	public Map<Object, Object> getParts() {
		return parts;
	}

	public String getPartsAsString() {
		Buf buf = new Buf();
		Set<Object> keySet = parts.keySet();
		Iterator<Object> iterator = keySet.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Object key = iterator.next();
			Object value = parts.get(key);
			buf.put(key+" = "+value+"");
			if (i < keySet.size()-1)
				buf.put(", ");	
		}
		return buf.toString();
	}

	public void setParts(Map<Object, Object> parts) {
		this.parts = parts;
	}
	
	public void addPart(Object key, Object value) {
		parts.put(key, value);
	}

}

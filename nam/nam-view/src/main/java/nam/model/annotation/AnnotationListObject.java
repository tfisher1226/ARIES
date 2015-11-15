package nam.model.annotation;

import java.io.Serializable;
import java.util.List;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.common.MapEntry;
import org.aries.ui.AbstractListObject;


public class AnnotationListObject extends AbstractListObject<Annotation> implements Comparable<AnnotationListObject>, Serializable {
	
	private Annotation annotation;

	
	public AnnotationListObject(Annotation annotation) {
		this.annotation = annotation;
	}

	
	public Annotation getAnnotation() {
		return annotation;
	}

	@Override
	public Object getKey() {
		return getKey(annotation);
	}
	
	public Object getKey(Annotation annotation) {
		return AnnotationUtil.getKey(annotation);
	}
	
	@Override
	public String getLabel() {
		return getLabel(annotation);
	}
	
	public String getLabel(Annotation annotation) {
		return AnnotationUtil.getLabel(annotation);
	}
	
	@Override
	public String toString() {
		return toString(annotation);
	}
	
	@Override
	public String toString(Annotation annotation) {
		return AnnotationUtil.toString(annotation);
	}
	
	@Override
	public int compareTo(AnnotationListObject other) {
		Object thisKey = getKey(this.annotation);
		Object otherKey = getKey(other.annotation);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}

//	@Override
//	public int compareTo(AnnotationListObject other) {
//		String thisName = record.getSource();
//		String otherName = other.record.getSource();
//		org.aries.common.Map thisParts = record.getDetails();
//		org.aries.common.Map otherParts = other.record.getDetails();
//		if (!thisName.equals(otherName))
//			return thisName.compareTo(otherName);
//		if (!thisParts.equals(otherParts))
//			return thisParts.toString().compareTo(otherParts.toString());
//		return 0;
//	}

	@Override
	public boolean equals(Object object) {
		AnnotationListObject other = (AnnotationListObject) object;
		Object thisKey = AnnotationUtil.getKey(this.annotation);
		Object otherKey = AnnotationUtil.getKey(other.annotation);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
	
	public String getAnnotationText(Annotation annotation) {
		StringBuffer buf = new StringBuffer();
		buf.append("@");
		buf.append(annotation.getSource());
		//List<Property> parts = annotation.getParts();
		org.aries.common.Map parts = annotation.getDetails();
		List<MapEntry> entries = parts.getEntries();
		if (entries.size() > 0) {
			buf.append("(");
			for (int i=0; i < entries.size(); i++) {
				MapEntry entry = entries.get(i);
				if (i > 0)
					buf.append(", ");
				Object key = entry.getKey();
				if (key instanceof String) {
					buf.append(key.toString());
					Object value = entry.getValue();
					if (value != null && value instanceof String) {
						String valueString = (String) value;
						if (!StringUtils.isEmpty(valueString)) {
							buf.append(" = ");
							buf.append("\"");
							buf.append(valueString);
							buf.append("\"");
						}
					} else if (value != null) {
						buf.append(" = ");
						buf.append(value.toString());
					}
				} else {
					buf.append(key.toString());
				}
			}
			buf.append(")");
		}
		return buf.toString();
	}
	
//	@Override
//	public int compareTo(AnnotationListObject other) {
//		String thisName = record.getLabel();
//		String otherName = other.record.getLabel();
//		List<Property> thisProperties = record.getProperties();
//		List<Property> otherProperties = other.record.getProperties();
//		if (!thisName.equals(otherName))
//			return thisName.compareTo(otherName);
//		if (!thisProperties.equals(otherProperties))
//			return thisProperties.toString().compareTo(otherProperties.toString());
//		return 0;
//	}
	
}

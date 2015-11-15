package aries.generation.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * Class modeling a reference to an instance. A reference is a
 * member variable of an element in a non-containment relationship 
 * i.e. the field value being "referenced" is not wholly-contained 
 * by the element, but instead exists (created, managed, destroyed, ...) 
 * external to the element.
 */
public class ModelReference extends ModelField {

	private Boolean contained;

	private Boolean inverse;

	private List<String> acceptedTypes;
	
	
	public ModelReference() {
		//nothing for now
		acceptedTypes = new ArrayList<String>();
	}

	public Boolean isContained() {
		return contained != null && contained;
	}

	public void setContained(boolean contained) {
		this.contained = contained;
	}
	
	public Boolean isInverse() {
		return inverse != null && inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}
	
	public List<String> getAcceptedTypes() {
		return acceptedTypes;
	}

	public void addAcceptedType(String acceptedType) {
		this.acceptedTypes.add(acceptedType);
	}

	public void addAcceptedTypes(List<String> acceptedTypes) {
		this.acceptedTypes.addAll(acceptedTypes);
	}

	public void setAcceptedTypes(List<String> acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public String getRepresentation() {
		String representation = "";
		if (isContained())
			representation += "containment reference";
		else representation = "reference";
		if (!StringUtils.isEmpty(getStructure()))
			representation += " "+getStructure();
		return representation;
	}
	

}

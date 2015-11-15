package aries.generation.model;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Class modeling an enumerated type.
 * 
 */
public class ModelEnum extends ModelClass {
	
	private Set<ModelLiteral> literals;


	/**
	 * Constructs an uninitialized @{link ModelEnum}.
	 */
	public ModelEnum() {
		literals = new LinkedHashSet<ModelLiteral>();
	}

	public Set<ModelLiteral> getLiterals() {
		return literals;
	}

	public void addLiteral(ModelLiteral operation) {
		literals.add(operation);
	}

	public boolean removeLiteral(ModelLiteral operation) {
		return literals.remove(operation);
	}

}

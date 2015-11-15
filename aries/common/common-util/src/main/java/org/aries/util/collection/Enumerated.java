package org.aries.util.collection;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public abstract class Enumerated implements Serializable {

	private final Object value;

	/**
	 * Construct the enumerated value.
	 * @param value The string value of the enumeration.
	 */
	protected Enumerated(Object value) {
		this.value = value;
	}

	/**
	 * Compare the specified object to this one.
	 * @param rhs The other object to compare.
	 */
	public boolean equals(Object rhs) {
		if (rhs == this)
			return true;
		if (rhs != null && rhs.getClass() == getClass()) {
			Object rhsValue = ((Enumerated) rhs).value;
			return (value == null ? rhsValue == null : value.equals(rhsValue));
		}
		return false;
	}

	/**
	 * Get the key representation of this enumeration.
	 * @return the key representation.
	 */
	public Object getKey() {
		return value;
	}

	/**
	 * Resolve the value of an enumeration.
	 * @param value The value of the enumeration.
	 * @return The enumeration.
	 * @throws InvalidEnumerationException if the enumeration value is invalid. 
	 */
	protected abstract Enumerated resolveEnum(final Object value) throws Exception;

	/**
	 * Replace any serialised version of this class with the same instance. 
	 * @return The instance of the enumeration.
	 * @throws ObjectStreamException
	 */
	protected Object readResolve() throws ObjectStreamException {
		try {
			return resolveEnum(value);
		} catch (Exception e) {
			throw new InvalidObjectException(e.getMessage());
		}
	}

	/**
	 * Generate the map of enumeration values to enumerations.
	 * @param enumerations The enumerations to configure.
	 * @return The enumeration map.
	 */
	protected static Map generateMap(final Enumerated[] enumerations) {
		int numEnumerations = enumerations == null ? 0 : enumerations.length;
		Map result;
		if (numEnumerations == 0) {
			result = Collections.EMPTY_MAP;
		} else {
			result = new HashMap();
			for (int count = 0; count < numEnumerations; count++) {
				Enumerated enumeration = enumerations[count];
				result.put(enumeration.getKey(), enumeration);
			}
		}
		return result;
	}

	/**
	 * Return a hash code for this enumerated entry.
	 * @return the enumeration hash code.
	 */
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	/**
	 * Get the string representation of this enumeration.
	 * @return the string representation.
	 */
	public String toString() {
		return value == null ? "<null>" : value.toString();
	}

}

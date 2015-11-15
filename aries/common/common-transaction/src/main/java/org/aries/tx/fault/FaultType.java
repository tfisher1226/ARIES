package org.aries.tx.fault;

import java.util.Map;

import org.aries.util.collection.Enumerated;


/**
 * Class representing the fault type.
 */
public class FaultType extends Enumerated {

	/**
	 * Serial version UID for serialisation.
	 */
	private static final long serialVersionUID = 6597369531649776751L;

	/**
	 * The version mismatch type.
	 */
	public static final FaultType FAULT_VERSION_MISMATCH = new FaultType("VersionMismatch");

	/**
	 * The must understand type.
	 */
	public static final FaultType FAULT_MUST_UNDERSTAND = new FaultType("MustUnderstand");

	/**
	 * The data encoding unknown type.
	 */
	public static final FaultType FAULT_DATA_ENCODING_UNKNOWN = new FaultType("DataEncodingUnknown");

	/**
	 * The sender type.
	 */
	public static final FaultType FAULT_SENDER = new FaultType("Sender");

	/**
	 * The receiver type.
	 */
	public static final FaultType FAULT_RECEIVER = new FaultType("Receiver");

	/**
	 * The map of enumerations.
	 */
	private static final Map ENUM_MAP = generateMap(new Enumerated[] {
			FAULT_VERSION_MISMATCH, 
			FAULT_MUST_UNDERSTAND, 
			FAULT_DATA_ENCODING_UNKNOWN,
			FAULT_SENDER, 
			FAULT_RECEIVER
	});

	/**
	 * Construct the state enumeration with the specified value.
	 * @param localName The localName of the state enumeration.
	 */
	private FaultType(String value) {
		super(value);
	}

	/**
	 * Get the value of this enumeration.
	 * @return the value.
	 */
	public String getValue() {
		return (String) getKey();
	}

	/**
	 * Resolve the enumeration for the specified value.
	 * @param value The value.
	 * @return The enumeration.
	 * @throws InvalidEnumerationException if the value is not valid.
	 */
	protected Enumerated resolveEnum(Object value) throws Exception {
		return toState((String)value);
	}

	/**
	 * Return the enumeration for the specified value.
	 * @param value The value.
	 * @return The enumeration.
	 * @throws InvalidEnumerationException if the value is not valid.
	 */
	public static FaultType toState(String value) throws Exception {
		Object state = ENUM_MAP.get(value);
		if (state == null) {
			throw new Exception("Invalid fault type enumeration: "+value);
		}
		return (FaultType) state;
	}
}

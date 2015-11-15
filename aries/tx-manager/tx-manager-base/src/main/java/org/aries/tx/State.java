package org.aries.tx;

import java.util.Map;

import org.aries.tx.util.Enumerated;


public class State extends Enumerated {

	public static final State STATE_ACTIVE = new State("Active") ;
	public static final State STATE_PREPARING = new State("Preparing") ;
	public static final State STATE_PREPARED = new State("Prepared") ;
	public static final State STATE_PREPARED_SUCCESS = new State("PreparedSuccess") ;
	public static final State STATE_COMMITTING = new State("Committing") ;
	public static final State STATE_ABORTING = new State("Aborting") ;

	private static final Map ENUM_MAP = generateMap(new Enumerated[] {
			STATE_ACTIVE, 
			STATE_PREPARING, 
			STATE_PREPARED, 
			STATE_PREPARED_SUCCESS,
			STATE_COMMITTING, 
			STATE_ABORTING
	}) ;

	private State(String value) {
		super(value);
	}

	public String getValue() {
		return (String) getKey();
	}

	protected Enumerated resolveEnum(Object value) {
		return toState11((String) value) ;
	}

	public static State toState11(String value) {
		final Object state = ENUM_MAP.get(value);
		if (state == null)
			throw new RuntimeException("Unexpected value: "+value) ;
		return (State) state;
	}

}

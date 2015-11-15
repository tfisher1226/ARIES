package org.aries.tx.state;

import java.util.Map;

import org.aries.util.collection.Enumerated;


/**
 * Class representing AT state enumerations.
 */
@SuppressWarnings("serial")
public class State extends Enumerated {
	
    /**
     * The Active state.
     */
    public static final State STATE_ACTIVE = new State("Active");
    
    /**
     * The Preparing state.
     */
    public static final State STATE_PREPARING = new State("Preparing");
    
    /**
     * The Prepared state.
     */
    public static final State STATE_PREPARED = new State("Prepared");
    
    /**
     * The PreparedSuccess state.
     */
    public static final State STATE_PREPARED_SUCCESS = new State("PreparedSuccess");
    
    /**
     * The Committing state.
     */
    public static final State STATE_COMMITTING = new State("Committing");
    
    /**
     * The Aborting state.
     */
    public static final State STATE_ABORTING = new State("Aborting");

    /**
     * The map of enumerations.
     */
    private static final Map ENUM_MAP = generateMap(new Enumerated[] {
        STATE_ACTIVE, 
        STATE_PREPARING, 
        STATE_PREPARED, 
        STATE_PREPARED_SUCCESS,
        STATE_COMMITTING, 
        STATE_ABORTING
    });

    /**
     * Construct the state enumeration with the specified value.
     * @param value The localName of the state enumeration.
     */
    private State(String value) {
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
     * @throws RuntimeException if the value is not valid.
     */
    protected State resolveEnum(final Object value) throws Exception {
        Object state = ENUM_MAP.get(value);
        if (state == null)
            throw new RuntimeException("Invalid state: "+value);
        return (State) state;
    }

}
package org.aries;

import java.util.HashMap;
import java.util.Map;


public enum Transport {

	/** RMI */
	RMI (0, "RMI"),
	
	/** EJB */
	EJB (1, "EJB"),
	
	/** CORBA */
	CORBA (0, "CORBA"),
	
	/** JMS */
	JMS (1, "JMS"),

	/** WS */
	WS (1, "WS");

	
	private static Map<String, Transport> labelMap = new HashMap<String, Transport>();
	
	static {
		Transport[] list = Transport.values();
		for (Transport item: list) {
			labelMap.put(item.getLabel().toUpperCase(), item);
		}
	}
	
	public static Transport getTransport(String label) {
		Transport transport = labelMap.get(label.toUpperCase());
		return transport;
	}

	
	private final int id;

	private final String label;
	
	
	private Transport(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
}

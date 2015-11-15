package org.aries.transport;

import java.util.HashMap;
import java.util.Map;


public enum TransferMode {

	/** Indicates content should be transferred as text-based */
	TEXT(0, "TEXT"),
	
	/** Indicates content should be transferred in binary */
	BINARY(1, "BINARY");

	private static Map<String, TransferMode> labelMap = new HashMap<String, TransferMode>();
	
	static {
		TransferMode[] list = TransferMode.values();
		for (TransferMode item: list) {
			labelMap.put(item.getLabel(), item);
		}
	}
	
	public static TransferMode getTransferMode(String label) {
		TransferMode transport = labelMap.get(label.toUpperCase());
		return transport;
	}

	
	private final int id;

	private final String label;
	
	
	private TransferMode(int id, String label) {
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

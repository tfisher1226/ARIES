package org.aries.validate.util;


public class Check {

//	public static void notNull(String checkpointName, Object object) {
//		CheckpointManager.notNull(checkpointName, object, null);
//	}
//	
//	public static void notNull(String checkpointName, Object object, String message) {
//		CheckpointManager.notNull(checkpointName, object, message);
//	}

	public static void isValid(String checkpointName, Object object) {
		CheckpointManager.isValid(checkpointName, object);
	}
	
	public static void isValid(String checkpointName, Object object, String message) {
		CheckpointManager.isValid(checkpointName, object, message);
	}

}

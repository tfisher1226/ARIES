package common.tx.service.coordinator;


/**
 * The final coordination result.
 */
public class CoordinationResult {

	public static final int CONFIRMED = 0;

	public static final int CANCELLED = 1;


	/**
	 * @return a human-readable version of the outcome.
	 */
	public static String stringForm (int res) {
		switch (res) 		{
		case CANCELLED:
			return "CoordinationResult.CANCELLED";
		case CONFIRMED:
			return "CoordinationResult.CONFIRMED";
		default:
			return "Unknown - "+res;
		}
	}

}

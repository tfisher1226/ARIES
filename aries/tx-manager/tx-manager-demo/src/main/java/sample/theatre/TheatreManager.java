package sample.theatre;

import common.tx.state.AbstractServiceManager;


public class TheatreManager extends AbstractServiceManager<TheatreState> {

	public static TheatreManager INSTANCE = new TheatreManager();

	public synchronized static TheatreManager getInstance() {
		return INSTANCE;
	}


	public int getNFreeSeats(int area) {
		return currentState.getFreeSeats(area);
	}

	public int getNTotalSeats(int area) {
		return currentState.getTotalSeats(area);
	}

	public int getNBookedSeats(int area) {
		return currentState.getBookedSeats(area);
	}

	public synchronized int getNPreparedSeats(int area) {
		if (isLocked()) {
			TheatreState childState = getPreparedState();
			return childState.getBookedSeats(area) - currentState.getBookedSeats(area);
		}
		return 0;
	}


	@Override
	protected TheatreState createState() {
		return new TheatreState();
	}
    
	@Override
	protected TheatreState resetState() {
		TheatreState state = new TheatreState();
		return state;
	}

	/**
	 * identify the name of file used to store the current service state
	 * @return the name of the file used to store the current service state
	 */
	@Override
	public String getLatestStateFilename() {
		return TheatreState.STATE_FILENAME;
	}

	/**
	 * identify the name of file used to store the shadow service state
	 * @return the name of the file used to store the shadow service state
	 */
	@Override
	public String getShadowStateFilename() {
		return TheatreState.SHADOW_STATE_FILENAME;
	}


	@Override
	public boolean saveState(TheatreState state) {
		// TODO Auto-generated method stub
		return false;
	}

}

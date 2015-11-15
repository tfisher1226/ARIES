package sample.restaurant;

import common.tx.state.AbstractServiceManager;


public class RestaurantManager extends AbstractServiceManager<RestaurantState> {

	public static RestaurantManager INSTANCE = new RestaurantManager();

	public synchronized static RestaurantManager getInstance() {
		return INSTANCE;
	}


	public int getNFreeSeats() {
		return currentState.getFreeSeats();
	}

	public int getNTotalSeats() {
		return currentState.getTotalSeats();
	}

	public int getNBookedSeats() {
		return currentState.getBookedSeats();
	}

	public synchronized int getNPreparedSeats() {
		if (isLocked()) {
			RestaurantState childState = getPreparedState();
			return childState.getBookedSeats() - currentState.getBookedSeats();
		}
		return 0;
	}


	@Override
	protected RestaurantState createState() {
		return new RestaurantState();
	}
    
	@Override
	protected RestaurantState resetState() {
		RestaurantState state = new RestaurantState();
		return state;
	}

//	public boolean confirmPrepare() {
//		return true;
//	}
	
	/**
	 * identify the name of file used to store the current service state
	 * @return the name of the file used to store the current service state
	 */
	@Override
	public String getLatestStateFilename() {
		return RestaurantState.STATE_FILENAME;
	}

	/**
	 * identify the name of file used to store the shadow service state
	 * @return the name of the file used to store the shadow service state
	 */
	@Override
	public String getShadowStateFilename() {
		return RestaurantState.SHADOW_STATE_FILENAME;
	}


	@Override
	public boolean saveState(RestaurantState state) {
		// TODO Auto-generated method stub
		return false;
	}

}

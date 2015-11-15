package sample.hotel;

import org.aries.tx.Transactional;

import common.tx.state.AbstractStateManager;


public class HotelManager extends AbstractStateManager<HotelState> implements Transactional {

	public static HotelManager INSTANCE = new HotelManager();

	public synchronized static HotelManager getInstance() {
		return INSTANCE;
	}

	
	@Override
	public String getName() {
		return "HotelManager";
	}
	
	public int getNFreeRooms() {
		return currentState.getFreeRooms();
	}

	public int getNTotalRooms() {
		return currentState.getTotalRooms();
	}

	public int getNBookedRooms() {
		return currentState.getBookedRooms();
	}

	public synchronized int getNPreparedRooms() {
		if (isLocked()) {
			HotelState childState = getPreparedState();
			return childState.getBookedRooms() - currentState.getBookedRooms();
		}
		return 0;
	}


	@Override
	protected HotelState createState() {
		return new HotelState();
	}
    
	@Override
	protected HotelState resetState() {
		HotelState state = new HotelState();
		return state;
	}

	/**
	 * identify the name of file used to store the current service state
	 * @return the name of the file used to store the current service state
	 */
	@Override
	public String getLatestStateFilename() {
		return HotelState.STATE_FILENAME;
	}

	/**
	 * identify the name of file used to store the shadow service state
	 * @return the name of the file used to store the shadow service state
	 */
	@Override
	public String getShadowStateFilename() {
		return HotelState.SHADOW_STATE_FILENAME;
	}


	@Override
	public boolean saveState(HotelState state) {
		// TODO Auto-generated method stub
		return false;
	}

}

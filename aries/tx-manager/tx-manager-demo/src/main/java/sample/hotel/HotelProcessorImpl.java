package sample.hotel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.state.AbstractProcessor;
import common.tx.state.ParticipantManager;



public class HotelProcessorImpl extends AbstractProcessor<HotelState> implements HotelProcessor {

	private static Log log = LogFactory.getLog(HotelProcessor.class);
	
	private int numberOfPeople;
	
	
	@Override
	public String getActionName() {
		return "HotelService";
	}
	
	@Override
	protected ParticipantManager getParticipantManager() {
		return HotelManager.getInstance();
	}
	
	@Override
	public void bookRoom(String transactionId, int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
        ensureParticipantEnrollment(transactionId);
		HotelManager.getInstance().execute(this, transactionId);
	}

	@Override
	public boolean validateState(HotelState state) {
		if (state.freeRooms < numberOfPeople || state.bookedRooms + numberOfPeople > state.totalRooms) {
			log.warn("requested number of seats (" + numberOfPeople + ") not available");
			return false;
		}
		return true;
	}
	
	@Override
	public void updateState(HotelState state) {
		state.freeRooms -= numberOfPeople;
		state.bookedRooms += numberOfPeople;
	}

	@Override
	public void resetState(HotelState state) {
		state.totalRooms = HotelState.DEFAULT_ROOM_CAPACITY;
		state.freeRooms = state.totalRooms;
		state.bookedRooms = 0;
	}
	
}

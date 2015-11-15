package sample.theatre;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionParticipantManager;

import sample.restaurant.RestaurantManager;

import common.tx.state.AbstractProcessor;
import common.tx.state.ParticipantManager;



public class TheatreProcessorImpl extends AbstractProcessor<TheatreState> implements TheatreProcessor {

	private static Log log = LogFactory.getLog(TheatreProcessor.class);
	
	private int howMany;

	private int whichArea;

	
	@Override
	public String getActionName() {
		return "TheatreService";
	}
	
	@Override
	protected ParticipantManager getParticipantManager() {
		return TheatreManager.getInstance();
	}
	
	/**
	 * Book a number of seats in the Theatre
	 * Enrolls a Participant if necessary, then passes
	 * the call through to the business logic.
	 * @param howMany The number of seats to book.
	 * @param whichArea The area of the theatre to book seats in.
	 */
	@Override
	public void bookSeats(String transactionId, int howMany, int whichArea) {
		this.howMany = howMany;
		this.whichArea = whichArea;
		TransactionParticipantManager.getInstance().enrollTransaction("bookSeats", transactionId, TheatreManager.INSTANCE);
        //ensureParticipantEnrollment(transactionId);
		//TheatreManager.getInstance().execute(this, transactionId);
	}

	@Override
	public boolean validateState(TheatreState state) {
		if (state.freeSeats[whichArea] < howMany || state.bookedSeats[whichArea] + howMany > state.totalSeats[whichArea]) {
			log.warn("requested number of seats (" + howMany + ") not available");
			return false;
		}
		return true;
	}
	
	@Override
	public void updateState(TheatreState state) {
        state.freeSeats[whichArea] -= howMany;
		state.bookedSeats[whichArea] += howMany;
	}

	@Override
	public void resetState(TheatreState state) {
        for (int area = 0; area < TheatreState.NUM_SEAT_AREAS; area++) {
        	state.totalSeats[area] = TheatreState.DEFAULT_SEATING_CAPACITY;
        	state.freeSeats[area] = TheatreState.DEFAULT_SEATING_CAPACITY;
        	state.bookedSeats[area] = 0;
        }
	}
	
}

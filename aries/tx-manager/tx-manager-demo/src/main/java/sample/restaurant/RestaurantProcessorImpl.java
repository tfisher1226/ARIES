package sample.restaurant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.state.AbstractProcessor;
import common.tx.state.ParticipantManager;



public class RestaurantProcessorImpl extends AbstractProcessor<RestaurantState> implements RestaurantProcessor {

	private static Log log = LogFactory.getLog(RestaurantProcessor.class);
	
	private int howMany;
	
	
	public RestaurantProcessorImpl() {
		this.transactionContextReentrant = true;
	}

	@Override
	public String getActionName() {
		return "RestaurantService";
	}
	
	@Override
	protected ParticipantManager getParticipantManager() {
		return RestaurantManager.getInstance();
	}
	
	/**
	 * Book a number of seats in the Theatre
	 * Enrolls a Participant if necessary, then passes
	 * the call through to the business logic.
	 * @param howMany The number of seats to book.
	 */
	@Override
	public void bookSeats(String transactionId, int howMany) {
		this.howMany = howMany;
        ensureParticipantEnrollment(transactionId);
		RestaurantManager.getInstance().execute(this, transactionId);
	}

	@Override
	public boolean validateState(RestaurantState state) {
		if (state.freeSeats < howMany || state.bookedSeats + howMany > state.totalSeats) {
			log.warn("requested number of seats (" + howMany + ") not available");
			return false;
		}
		return true;
	}
	
	@Override
	public void updateState(RestaurantState state) {
		state.freeSeats -= howMany;
		state.bookedSeats += howMany;
	}

	@Override
	public void resetState(RestaurantState state) {
		state.totalSeats = RestaurantState.DEFAULT_SEATING_CAPACITY;
		state.freeSeats = state.totalSeats;
		state.bookedSeats = 0;
	}
	
}

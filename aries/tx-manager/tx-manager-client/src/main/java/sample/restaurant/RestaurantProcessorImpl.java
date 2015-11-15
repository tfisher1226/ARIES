package sample.restaurant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.TransactionParticipantManager;

import common.tx.state.ParticipantHelper;


public class RestaurantProcessorImpl implements RestaurantProcessor {

	private static Log log = LogFactory.getLog(RestaurantProcessor.class);
	
	private ParticipantHelper<RestaurantState> participantHelper;
	
	private int howMany;
	
	
	public RestaurantProcessorImpl() {
		participantHelper = new ParticipantHelper<RestaurantState>();
		//participantHelper.setServiceManager(RestaurantManager.getInstance());
		participantHelper.setActionName("RestaurantService");
	}

	@Override
	public void resetState(RestaurantState state) {
		state.totalSeats = RestaurantState.DEFAULT_SEATING_CAPACITY;
		state.freeSeats = state.totalSeats;
		state.bookedSeats = 0;
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

	/**
	 * Book a number of seats in the Theatre
	 * Enrolls a Participant if necessary, then passes
	 * the call through to the business logic.
	 * @param howMany The number of seats to book.
	 */
	@Override
	public void bookSeats(String transactionId, int howMany) {
		this.howMany = howMany;
		TransactionParticipantManager.getInstance().enrollTransaction("bookSeats", transactionId, RestaurantManager.INSTANCE);
		//TODO participantHelper.ensureParticipantEnrollment(transactionId);
		//RestaurantManager.getInstance().execute(transactionId);
	}
	
}

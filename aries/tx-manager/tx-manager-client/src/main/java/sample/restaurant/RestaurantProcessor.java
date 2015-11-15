package sample.restaurant;

import common.tx.state.ServiceStateProcessor;


public interface RestaurantProcessor extends ServiceStateProcessor<RestaurantState> {

	public void bookSeats(String transactionId, int seatCount);
	
}

package sample.restaurant;

import common.tx.state.ServiceState;


/**
 * An object which models the state of a restaurant identifying the number of free and
 * booked seats and the total available
 */
public class RestaurantState extends ServiceState {

	private static final long serialVersionUID = 1L;
	
    /**
     * The default initial capacity of each seating area.
     */
    public static final int DEFAULT_SEATING_CAPACITY = 100;

    /**
     * the name of the file used to persist the current restaurant state
     */
    public final static String STATE_FILENAME = "restaurantManagerState";

    /**
     * the name of the file used to persist the shadow restaurant state
     */
    public final static String SHADOW_STATE_FILENAME = "restaurantManagerShadowState";
    
	
	int totalSeats;
    
	int bookedSeats;
    
	int freeSeats;

	
    /**
     * create a new initial restaurant state
     */
	public RestaurantState() {
        this.totalSeats = DEFAULT_SEATING_CAPACITY;
        this.freeSeats = DEFAULT_SEATING_CAPACITY;
        this.bookedSeats = 0;
    }

    /**
     * Creates a restaurant state derived from a parent state.
     * @param parent the parent state whose data should be copied into this state
     * and whose version should be incremented by 1 and then installed in this state.
     */
    private RestaurantState(RestaurantState parent) {
        super(parent);
        this.totalSeats = parent.totalSeats;
        this.bookedSeats = parent.bookedSeats;
        this.freeSeats = totalSeats - bookedSeats;
    }

    public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}

//	/**
//     * create a new initial restaurant state
//     * @return an initial restaurant state containing no booked seats
//     */
//    public RestaurantState getInitialState() {
//        return new RestaurantState();
//    }

    /**
     * derive a child restaurant state from this state
     * @return a derived restaurant state containing the same data as this state
     * but having a version id one greater
     */
    @SuppressWarnings("unchecked")
	public RestaurantState getDerivedState() {
        return new RestaurantState(this);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RestaurantState{version=");
        builder.append(version);
        builder.append(", totalSeats=");
        builder.append(totalSeats);
        builder.append(", bookedSeats=");
        builder.append(bookedSeats);
        builder.append(", freeSeats=");
        builder.append(freeSeats);
        builder.append("}");
        return builder.toString();
    }

}

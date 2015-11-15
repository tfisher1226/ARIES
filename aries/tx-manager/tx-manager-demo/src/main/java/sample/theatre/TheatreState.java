package sample.theatre;

import java.util.Arrays;

import common.tx.state.ServiceState;



/**
 * An object which models the state of a restaurant identifying the number of free and
 * booked seats and the total available
 */
public class TheatreState extends ServiceState {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constant (array index) used for the seating area CIRCLE.
	 */
	public static final int CIRCLE = 0;

	/**
	 * Constant (array index) used for the seating area STALLS.
	 */
	public static final int STALLS = 1;

	/**
	 * Constant (array index) used for the seating area BALCONY.
	 */
	public static final int BALCONY = 2;

	/**
	 * The total number (array size) of seating areas.
	 */
	public static final int NUM_SEAT_AREAS = 3;

	/**
	 * The default initial capacity of each seating area.
	 */
	public static final int DEFAULT_SEATING_CAPACITY = 100;

	/**
	 * the name of the file used to store the current theatre manager state
	 */
	public static final String STATE_FILENAME = "theatreManagerState";

	/**
	 * the name of the file used to store the shadow theatre manager state
	 */
	public static final String SHADOW_STATE_FILENAME = "theatreManagerShadowState";


	int[] totalSeats;

	int[] bookedSeats;

	int[] freeSeats;


	/**
	 * create a new initial restaurant state
	 */
	TheatreState() {
		this.totalSeats = new int[NUM_SEAT_AREAS];
		this.bookedSeats = new int[NUM_SEAT_AREAS];
		this.freeSeats = new int[NUM_SEAT_AREAS];
		for (int i = 0; i < NUM_SEAT_AREAS; i++) {
			totalSeats[i] = DEFAULT_SEATING_CAPACITY;
			bookedSeats[i] = 0;
			freeSeats[i] = DEFAULT_SEATING_CAPACITY;
		}
	}

	/**
	 * create a theatre state derived from a parent state
	 *
	 * @param parent the parent state whose data should be copied into this state
	 * and whose version should be incremented by 1 and then installed in this state.
	 */
	private TheatreState(TheatreState parent) {
		super(parent);
		this.totalSeats =  Arrays.copyOf(parent.totalSeats, NUM_SEAT_AREAS);
		this.bookedSeats =  Arrays.copyOf(parent.bookedSeats, NUM_SEAT_AREAS);
		this.freeSeats =  Arrays.copyOf(parent.freeSeats, NUM_SEAT_AREAS);
	}

    public int getTotalSeats(int area) {
		return totalSeats[area];
	}

	public void setTotalSeats(int totalSeats, int area) {
		this.totalSeats[area] = totalSeats;
	}

	public int getBookedSeats(int area) {
		return bookedSeats[area];
	}

	public void setBookedSeats(int bookedSeats, int area) {
		this.bookedSeats[area] = bookedSeats;
	}

	public int getFreeSeats(int area) {
		return freeSeats[area];
	}

	public void setFreeSeats(int freeSeats, int area) {
		this.freeSeats[area] = freeSeats;
	}
	
	/**
	 * create a new initial theatre state
	 * @return an initial theatre state containing no booked seats
	 */
	public TheatreState getInitialState() {
		return new TheatreState();
	}

	/**
	 * derive a child theatre state from this state
	 * @return a derived theatre state containing the same data as this state
	 * but having a version id one greater 
	 */
	@SuppressWarnings("unchecked")
	public TheatreState getDerivedState() {
		return new TheatreState(this);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TheatreState{version=");
		builder.append(version);
		builder.append(", totalSeats=[circle=");
		builder.append(totalSeats[CIRCLE]);
		builder.append(", STALLS=");
		builder.append(totalSeats[STALLS]);
		builder.append(", BALCONY=");
		builder.append(totalSeats[BALCONY]);
		builder.append("], bookedSeats=[circle=");
		builder.append(bookedSeats[CIRCLE]);
		builder.append(", STALLS=");
		builder.append(bookedSeats[STALLS]);
		builder.append(", BALCONY=");
		builder.append(bookedSeats[BALCONY]);
		builder.append("], freeSeats=[circle=");
		builder.append(freeSeats[CIRCLE]);
		builder.append(", STALLS=");
		builder.append(freeSeats[STALLS]);
		builder.append(", BALCONY=");
		builder.append(freeSeats[BALCONY]);
		builder.append("]}");
		return builder.toString();
	}

}

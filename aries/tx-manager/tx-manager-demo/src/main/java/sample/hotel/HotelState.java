package sample.hotel;

import common.tx.state.ServiceState;


public class HotelState extends ServiceState {

	private static final long serialVersionUID = 1L;
	
    /**
     * The default initial capacity of each seating area.
     */
    public static final int DEFAULT_ROOM_CAPACITY = 100;

    /**
     * the name of the file used to persist the current restaurant state
     */
    public final static String STATE_FILENAME = "hotelManagerState";

    /**
     * the name of the file used to persist the shadow restaurant state
     */
    public final static String SHADOW_STATE_FILENAME = "hotelManagerShadowState";
    
	
	int totalRooms;
    
	int bookedRooms;
    
	int freeRooms;

	
	public HotelState() {
        this.totalRooms = DEFAULT_ROOM_CAPACITY;
        this.freeRooms = DEFAULT_ROOM_CAPACITY;
        this.bookedRooms = 0;
    }

    /**
     * Creates a HotelState derived from a parent state.
     * @param parent the parent state whose data should be copied into this state
     * and whose version should be incremented by 1 and then installed in this state.
     */
    private HotelState(HotelState parent) {
        super(parent);
        this.totalRooms = parent.totalRooms;
        this.bookedRooms = parent.bookedRooms;
        this.freeRooms = totalRooms - bookedRooms;
    }

    public int getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}

	public int getBookedRooms() {
		return bookedRooms;
	}

	public void setBookedRooms(int bookedRooms) {
		this.bookedRooms = bookedRooms;
	}

	public int getFreeRooms() {
		return freeRooms;
	}

	public void setFreeRooms(int freeRooms) {
		this.freeRooms = freeRooms;
	}

//	/**
//     * create a new initial restaurant state
//     * @return an initial restaurant state containing no booked seats
//     */
//    public HotelState getInitialState() {
//        return new HotelState();
//    }

    /**
     * derive a child restaurant state from this state
     * @return a derived restaurant state containing the same data as this state
     * but having a version id one greater
     */
    @SuppressWarnings("unchecked")
	public HotelState getDerivedState() {
        return new HotelState(this);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HotelState{version=");
        builder.append(version);
        builder.append(", totalRooms=");
        builder.append(totalRooms);
        builder.append(", bookedRooms=");
        builder.append(bookedRooms);
        builder.append(", freeRooms=");
        builder.append(freeRooms);
        builder.append("}");
        return builder.toString();
    }

}

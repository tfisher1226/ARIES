package sample.hotel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "hotelRoom")
public class HotelRoomEntity {

	private long id;
	
	private int numberOfPeople;
	
	private boolean nonSmoking;
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public boolean isNonSmoking() {
		return nonSmoking;
	}

	public void setNonSmoking(boolean nonSmoking) {
		this.nonSmoking = nonSmoking;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		HotelRoomEntity other = (HotelRoomEntity) object;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Room ["+"roomSize="+numberOfPeople+", nonSmoking="+nonSmoking+"]";
	}
	
}

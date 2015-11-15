package bookshop2;

import java.io.Serializable;

import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class ReservationAbortedException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String reason;
	
	
	public ReservationAbortedException() {
		//nothing for now
	}
	
	public ReservationAbortedException(String message) {
		super(message);
	}
	
	public ReservationAbortedException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	public ReservationAbortedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}

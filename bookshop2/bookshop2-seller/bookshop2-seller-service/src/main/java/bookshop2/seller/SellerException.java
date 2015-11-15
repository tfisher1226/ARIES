package bookshop2.seller;

import java.io.Serializable;

import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class SellerException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String reason;
	
	
	public SellerException() {
		//nothing for now
	}
	
	public SellerException(String message) {
		super(message);
	}
	
	public SellerException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	public SellerException(String message, Throwable cause) {
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
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		SellerException other = (SellerException) object;
		if (this.getId() == null || other.getId() == null || 
			this.getReason() == null || other.getReason() == null)
			return this == other;
		if (this.getId().equals(other.getId()) && 
			this.getReason().equals(other.getReason()))
			return true;
		return this == object;
	}
	
	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return super.hashCode();
	}
	
}

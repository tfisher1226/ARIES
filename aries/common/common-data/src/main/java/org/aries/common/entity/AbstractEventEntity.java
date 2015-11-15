package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;


@MappedSuperclass
public class AbstractEventEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@Column(name = "correlation_id", nullable = false)
	private String correlationId;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Cache(usage = READ_WRITE)
	private AbstractUserEntity user;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getCorrelationId() {
		return correlationId;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public AbstractUserEntity getUser() {
		return user;
	}
	
	public void setUser(AbstractUserEntity user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}

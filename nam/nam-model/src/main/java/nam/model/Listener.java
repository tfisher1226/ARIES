package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Listener", namespace = "http://nam/model", propOrder = {
    "receiveChannel",
    "invalidChannel",
    "expiredChannel",
	"noLocalReceipt"
})
@XmlRootElement(name = "listener", namespace = "http://nam/model")
public class Listener extends Interactor implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "receiveChannel", namespace = "http://nam/model")
	private Channel receiveChannel;

	@XmlElement(name = "invalidChannel", namespace = "http://nam/model")
	private Channel invalidChannel;

	@XmlElement(name = "expiredChannel", namespace = "http://nam/model")
	private Channel expiredChannel;

	@XmlElement(name = "noLocalReceipt", namespace = "http://nam/model", type = String.class, defaultValue = "true")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
	private Boolean noLocalReceipt = true;


	public Listener() {
		//nothing for now
    }

	
    public Channel getReceiveChannel() {
        return receiveChannel;
    }

	public void setReceiveChannel(Channel receiveChannel) {
		this.receiveChannel = receiveChannel;
	}
	
    public Channel getInvalidChannel() {
        return invalidChannel;
    }

	public void setInvalidChannel(Channel invalidChannel) {
		this.invalidChannel = invalidChannel;
	}
	
    public Channel getExpiredChannel() {
        return expiredChannel;
    }

	public void setExpiredChannel(Channel expiredChannel) {
		this.expiredChannel = expiredChannel;
    }

	public Boolean isNoLocalReceipt() {
		return noLocalReceipt != null && noLocalReceipt;
	}
	
	public Boolean getNoLocalReceipt() {
		return noLocalReceipt != null && noLocalReceipt;
	}
	
	public void setNoLocalReceipt(Boolean noLocalReceipt) {
		this.noLocalReceipt = noLocalReceipt;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			//do nothing
		}
		int status = super.compareTo(object);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Listener other = (Listener) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Listener: noLocalReceipt="+noLocalReceipt;
	}

}

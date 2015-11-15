package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Callback", namespace = "http://nam/model", propOrder = {
    "direction",
    "sendingService",
    "receivingService"
})
@XmlRootElement(name = "callback", namespace = "http://nam/model")
public class Callback extends Service implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "direction", namespace = "http://nam/model")
	private Direction direction;
	
	@XmlElement(name = "sendingService", namespace = "http://nam/model")
	private Service sendingService;
	
	@XmlElement(name = "receivingService", namespace = "http://nam/model")
	private Service receivingService;
	
	
	public Callback() {
		//nothing for now
	}
	
	
    public Direction getDirection() {
        return direction;
    }

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
    public Service getSendingService() {
        return sendingService;
    }

	public void setSendingService(Service sendingService) {
		this.sendingService = sendingService;
	}
	
    public Service getReceivingService() {
        return receivingService;
    }

	public void setReceivingService(Service receivingService) {
		this.receivingService = receivingService;
	}
	
	@Override
	public String toString() {
		return "Callback: direction="+direction;
    }

}

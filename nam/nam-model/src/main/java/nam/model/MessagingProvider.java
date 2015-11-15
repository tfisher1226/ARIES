package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessagingProvider", namespace = "http://nam/model")
@XmlRootElement(name = "messagingProvider", namespace = "http://nam/model")
public class MessagingProvider extends Provider implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	public MessagingProvider() {
		//nothing for now
	}
	
}

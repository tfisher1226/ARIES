package nam.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CacheProvider", namespace = "http://nam/model")
@XmlRootElement(name = "cacheProvider", namespace = "http://nam/model")
public class CacheProvider extends Provider implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	public CacheProvider() {
		//nothing for now
	}
	
}

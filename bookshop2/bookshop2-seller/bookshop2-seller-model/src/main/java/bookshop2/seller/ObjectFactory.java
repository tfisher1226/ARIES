package bookshop2.seller;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public static final long serialVersionUID = 1L;


	public ObjectFactory() {
		//nothing for now
	}


	public Advertisement createAdvertisement() {
		return new Advertisement();
	}

}
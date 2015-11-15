package nam.ui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nam.model.Component;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Control", namespace = "http://nam/ui")
@XmlRootElement(name = "control", namespace = "http://nam/ui")
public class Control extends Component implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	public Control() {
		//nothing for now
	}
	
}

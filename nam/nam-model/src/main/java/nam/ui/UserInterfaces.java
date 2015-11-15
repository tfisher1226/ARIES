package nam.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInterfaces", namespace = "http://nam/ui", propOrder = {
	"userInterfaces"
})
@XmlRootElement(name = "userInterfaces", namespace = "http://nam/ui")
public class UserInterfaces implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "userInterfaces", namespace = "http://nam/ui")
	private List<UserInterface> userInterfaces;
	
	
	public UserInterfaces() {
		userInterfaces = new ArrayList<UserInterface>();
	}
	
	
	public List<UserInterface> getUserInterfaces() {
		synchronized (userInterfaces) {
			return userInterfaces;
		}
	}
	
	public void setUserInterfaces(Collection<UserInterface> userInterfaces) {
		if (userInterfaces == null) {
			this.userInterfaces = null;
		} else {
		synchronized (this.userInterfaces) {
				this.userInterfaces = new ArrayList<UserInterface>();
				addToUserInterfaces(userInterfaces);
			}
		}
	}

	public void addToUserInterfaces(UserInterface userInterface) {
		if (userInterface != null ) {
			synchronized (this.userInterfaces) {
				this.userInterfaces.add(userInterface);
			}
		}
	}

	public void addToUserInterfaces(Collection<UserInterface> userInterfaceCollection) {
		if (userInterfaceCollection != null && !userInterfaceCollection.isEmpty()) {
			synchronized (this.userInterfaces) {
				this.userInterfaces.addAll(userInterfaceCollection);
			}
		}
	}

	public void removeFromUserInterfaces(UserInterface userInterface) {
		if (userInterface != null ) {
			synchronized (this.userInterfaces) {
				this.userInterfaces.remove(userInterface);
			}
		}
	}

	public void removeFromUserInterfaces(Collection<UserInterface> userInterfaceCollection) {
		if (userInterfaceCollection != null ) {
			synchronized (this.userInterfaces) {
				this.userInterfaces.removeAll(userInterfaceCollection);
			}
		}
	}

	public void clearUserInterfaces() {
		synchronized (userInterfaces) {
			userInterfaces.clear();
		}
	}
}

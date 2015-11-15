package admin;

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
@XmlType(name = "Users", namespace = "http://admin", propOrder = {
	"records"
})
@XmlRootElement(name = "users", namespace = "http://admin")
public class Users implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "records", namespace = "http://admin")
	private List<User> records;
	
	
	public Users() {
		records = new ArrayList<User>();
	}
	
	
	public List<User> getRecords() {
		synchronized (records) {
			return records;
		}
	}
	
	public void setRecords(Collection<User> records) {
		if (records == null) {
			this.records = null;
		} else {
		synchronized (this.records) {
			this.records = new ArrayList<User>();
			addToRecords(records);
		}
	}
	}

	public void addToRecords(User user) {
		if (user != null ) {
			synchronized (this.records) {
				this.records.add(user);
			}
		}
	}

	public void addToRecords(Collection<User> userCollection) {
		if (userCollection != null && !userCollection.isEmpty()) {
			synchronized (this.records) {
				this.records.addAll(userCollection);
			}
		}
	}

	public void removeFromRecords(User user) {
		if (user != null ) {
			synchronized (this.records) {
				this.records.remove(user);
			}
		}
	}

	public void removeFromRecords(Collection<User> userCollection) {
		if (userCollection != null ) {
			synchronized (this.records) {
				this.records.removeAll(userCollection);
			}
		}
	}

	public void clearRecords() {
		synchronized (records) {
			records.clear();
		}
	}
}

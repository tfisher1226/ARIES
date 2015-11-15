package admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Team", namespace = "http://admin", propOrder = {
	"id",
	"name",
	"organization",
	"description",
	"members",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "team", namespace = "http://admin")
public class Team implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "name", namespace = "http://admin", required = true)
	private String name;
	
	@XmlElement(name = "organization", namespace = "http://admin")
	private String organization;
	
	@XmlElement(name = "description", namespace = "http://admin")
	private String description;
	
	@XmlElement(name = "members", namespace = "http://admin")
	private List<User> members;
	
	@XmlElement(name = "creationDate", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	
	public Team() {
		members = new ArrayList<User>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<User> getMembers() {
		synchronized (members) {
			return members;
		}
	}
	
	public void setMembers(Collection<User> members) {
		if (members == null) {
			this.members = null;
		} else {
		synchronized (this.members) {
				this.members = new ArrayList<User>();
				addToMembers(members);
			}
		}
	}

	public void addToMembers(User user) {
		if (user != null ) {
			synchronized (this.members) {
				this.members.add(user);
			}
		}
	}

	public void addToMembers(Collection<User> userCollection) {
		if (userCollection != null && !userCollection.isEmpty()) {
			synchronized (this.members) {
				this.members.addAll(userCollection);
			}
		}
	}

	public void removeFromMembers(User user) {
		if (user != null ) {
			synchronized (this.members) {
				this.members.remove(user);
			}
		}
	}

	public void removeFromMembers(Collection<User> userCollection) {
		if (userCollection != null ) {
			synchronized (this.members) {
				this.members.removeAll(userCollection);
			}
		}
	}

	public void clearMembers() {
		synchronized (members) {
			members.clear();
		}
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Team other = (Team) object;
			int status = compare(name, other.name);
			if (status != 0)
				return status;
			status = compare(organization, other.organization);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Team other = (Team) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (organization != null)
			hashCode += organization.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Team: name="+name+", organization="+organization;
	}
	
}

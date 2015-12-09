package admin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.MapAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Account", namespace = "http://admin", propOrder = {
	"id",
	"user",
	"models",
	"creditCard",
	"enabled"
})
@XmlRootElement(name = "account", namespace = "http://admin")
public class Account implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "user", namespace = "http://admin", required = true)
	private User user;
	
	@XmlElement(name = "models", namespace = "http://admin")
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, String> models;
	
	@XmlElement(name = "creditCard", namespace = "http://admin", required = true)
	private String creditCard;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Account() {
		models = new HashMap<String, String>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Map<String, String> getModels() {
		synchronized (models) {
			return models;
		}
	}
	
	public void setModels(Map<String, String> models) {
		if (models == null) {
			this.models = null;
		} else {
		synchronized (this.models) {
				this.models = new HashMap<String, String>();
				addToModels(models);
			}
		}
	}

	public void addToModels(String modelsKey, String modelsValue) {
		if (modelsKey != null) {
			synchronized (this.models) {
				this.models.put(modelsKey, modelsValue);
			}
		}
	}

	public void addToModels(Map<String, String> modelsMap) {
		if (modelsMap != null && !modelsMap.isEmpty()) {
			synchronized (this.models) {
				this.models.putAll(modelsMap);
			}
		}
	}

	public void removeFromModels(String modelsKey) {
		if (models != null ) {
			synchronized (this.models) {
				this.models.remove(modelsKey);
			}
		}
	}

	public void removeFromModels(Map<String, String> modelsCollection) {
		if (modelsCollection != null && !modelsCollection.isEmpty()) {
			synchronized (this.models) {
				Set<String> keySet = modelsCollection.keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					this.models.remove(key);
				}
			}
		}
	}

	public void clearModels() {
		synchronized (models) {
			models.clear();
		}
	}
	
	public String getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
	public Boolean isEnabled() {
		return enabled != null && enabled;
	}
	
	public Boolean getEnabled() {
		return enabled != null && enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Account other = (Account) object;
			int status = compareObject(user, other.user);
			if (status != 0)
				return status;
			status = compare(creditCard, other.creditCard);
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
	
	protected <T extends Comparable<Object>> int compareObject(T value1, T value2) {
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
		Account other = (Account) object;
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
		if (creditCard != null)
			hashCode += creditCard.hashCode();
		if (user != null)
			hashCode += user.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Account: user="+user+", creditCard="+creditCard;
	}
	
}

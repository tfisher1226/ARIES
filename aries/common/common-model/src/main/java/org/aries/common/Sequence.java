package org.aries.common;

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
@XmlType(name = "Sequence", namespace = "http://www.aries.org/common", propOrder = {
	"items"
})
@XmlRootElement(name = "sequence", namespace = "http://www.aries.org/common")
public class Sequence implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "items", namespace = "http://www.aries.org/common")
	private List<Object> items;
	
	
	public Sequence() {
		items = new ArrayList<Object>();
	}
	
	
	public List<Object> getItems() {
		synchronized (items) {
			return items;
		}
	}
	
	public void setItems(Collection<Object> itemsList) {
		if (itemsList == null) {
			this.items = null;
		} else {
		synchronized (this.items) {
			this.items = new ArrayList<Object>();
				addToItems(itemsList);
			}
		}
	}

	public void addToItems(Object items) {
		if (items != null ) {
			synchronized (this.items) {
				this.items.add(items);
			}
		}
	}

	public void addToItems(Collection<Object> itemsCollection) {
		if (itemsCollection != null && !itemsCollection.isEmpty()) {
			synchronized (this.items) {
				this.items.addAll(itemsCollection);
			}
		}
	}

	public void removeFromItems(Object items) {
		if (items != null ) {
			synchronized (this.items) {
				this.items.remove(items);
			}
		}
	}

	public void removeFromItems(Collection<Object> itemsCollection) {
		if (itemsCollection != null ) {
			synchronized (this.items) {
				this.items.removeAll(itemsCollection);
			}
		}
	}

	public void clearItems() {
		synchronized (items) {
			items.clear();
		}
	}
}

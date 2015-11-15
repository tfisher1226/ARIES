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
@XmlType(name = "Map", namespace = "http://www.aries.org/common", propOrder = {
	"entries"
})
@XmlRootElement(name = "map", namespace = "http://www.aries.org/common")
public class Map implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "entries", namespace = "http://www.aries.org/common")
	private List<MapEntry> entries;
	
	
	public Map() {
		entries = new ArrayList<MapEntry>();
	}
	
	
	public List<MapEntry> getEntries() {
		synchronized (entries) {
			return entries;
		}
	}
	
	public void setEntries(Collection<MapEntry> mapEntryList) {
		if (mapEntryList == null) {
			this.entries = null;
		} else {
		synchronized (this.entries) {
			this.entries = new ArrayList<MapEntry>();
				addToEntries(mapEntryList);
			}
		}
	}

	public void addToEntries(MapEntry mapEntry) {
		if (mapEntry != null ) {
			synchronized (this.entries) {
				this.entries.add(mapEntry);
			}
		}
	}

	public void addToEntries(Collection<MapEntry> mapEntryCollection) {
		if (mapEntryCollection != null && !mapEntryCollection.isEmpty()) {
			synchronized (this.entries) {
				this.entries.addAll(mapEntryCollection);
			}
		}
	}

	public void removeFromEntries(MapEntry mapEntry) {
		if (mapEntry != null ) {
			synchronized (this.entries) {
				this.entries.remove(mapEntry);
			}
		}
	}

	public void removeFromEntries(Collection<MapEntry> mapEntryCollection) {
		if (mapEntryCollection != null ) {
			synchronized (this.entries) {
				this.entries.removeAll(mapEntryCollection);
			}
		}
	}

	public void clearEntries() {
		synchronized (entries) {
			entries.clear();
		}
	}
}

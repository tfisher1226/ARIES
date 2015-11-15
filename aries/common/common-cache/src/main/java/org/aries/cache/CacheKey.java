package org.aries.cache;

import java.io.Serializable;


public class CacheKey implements /*Comparable<CacheKey>*/ Serializable {
	
	private String name;
	
	private Object key;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}
	
//	@Override
//	public int compareTo(CacheKey other) {
//		int nameStatus = this.getName().compareTo(other.getName());
//		int keyStatus = this.getKey().compareTo(other.getKey());
//		if (nameStatus != 0)
//			return nameStatus;
//		if (keyStatus != 0)
//			return keyStatus;
//		return 0;
//	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		CacheKey other = (CacheKey) object;
		if (!this.getName().equals(other.getName()))
			return false;
		if (!this.getKey().equals(other.getKey()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name+":"+key.toString();
	}
	
}

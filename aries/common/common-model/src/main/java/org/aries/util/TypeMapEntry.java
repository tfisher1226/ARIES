// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst nonlb space 
// Source File Name:   TypeMapEntry.java

package org.aries.util;


public class TypeMapEntry {

	private String name;
	private String namespace;
	private Class classObject;

	public TypeMapEntry() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Class getClassObject() {
		return classObject;
	}

	public void setClassObject(Class classObject) {
		this.classObject = classObject;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TypeMapEntry)) {
			return false;
		} else {
			TypeMapEntry other = (TypeMapEntry)object;
			return getName().equals(other.getName()) && getNamespace().equals(other.getNamespace());
		}
	}
}

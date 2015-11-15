package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.MapAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractCriteria", namespace = "http://www.aries.org/common", propOrder = {
	"idList",
	"keyList",
	"fieldMap"
})
@XmlRootElement(name = "abstractCriteria", namespace = "http://www.aries.org/common")
public class AbstractCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "idList", namespace = "http://www.aries.org/common", nillable = true)
	private List<Long> idList;
	
	@XmlElement(name = "keyList", namespace = "http://www.aries.org/common", nillable = true)
	private List<Object> keyList;

	@XmlElement(name = "fieldMap", namespace = "http://www.aries.org/common", nillable = true)
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, Object> fieldMap;
	
	
	public List<Long> getIdList() {
		if (idList == null)
			idList = new ArrayList<Long>();
		return idList;
	}
	
	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	
	public void addToIdList(Long id) {
		this.idList.add(id);
	}
	
	public void addToIdList(Collection<Long> idList) {
		this.idList.addAll(idList);
	}
	
	public Collection<Object> getKeyList() {
		if (keyList == null)
			keyList = new ArrayList<Object>();
		return keyList;
	}
	
	public void setKeyList(List<Object> keyList) {
		this.keyList = keyList;
	}
	
	public void addToKeyList(Object key) {
		this.keyList.add(key);
	}
	
	public void addToKeyList(Collection<Object> keyList) {
		this.keyList.addAll(keyList);
	}

	public Map<String, Object> getFieldMap() {
		if (fieldMap == null)
			fieldMap = new HashMap<String, Object>();
		return fieldMap;
	}

	public void setFieldMap(Map<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public void addToFieldMap(String name, Object value) {
		getFieldMap().put(name,  value);
	}
	
	public Object getFieldValue(String name) {
		return getFieldMap().get(name);
	}
	
}

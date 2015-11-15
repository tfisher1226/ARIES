package org.aries.runtime;

import java.util.HashMap;
import java.util.Map;


public class BeanMap {

	private Map<String, Object> beans;
	
	
	public BeanMap() {
		//TODO add this later when debugging is over
		//beans = new ConcurrentHashMap<String, Object>();		
		beans = new HashMap<String, Object>();		
	}

	public void clear() {
		beans.clear();
	}

	public Map<String, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<String, Object> beans) {
		this.beans = beans;
	}

	@SuppressWarnings("unchecked") 
	public <T> T getBean(String beanName) {
		return (T) beans.get(beanName);
	}

	/*
	 * If bean already exists for the specified name,
	 * then we overwrite that bean with the new bean. 
	 */
	public void addBean(String beanName, Object bean) {
		beans.put(beanName, bean);
	}
	
	public void addBeans(BeanMap beans) {
		this.beans.putAll(beans.getBeans());
	}

	public void removeBean(String beanName) {
		beans.remove(beanName);
	}

}

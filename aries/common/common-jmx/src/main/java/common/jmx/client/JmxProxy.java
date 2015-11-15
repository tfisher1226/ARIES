package common.jmx.client;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.jmx.client.JmxManager;


public class JmxProxy {

	private static final Log log = LogFactory.getLog(JmxProxy.class);
	
	public JmxManager jmxManager;
	

	
	public void setJmxManager(JmxManager jmxManager) {
		this.jmxManager = jmxManager;
	}
	
	public void call(String mBeanName, String methodName) {
		try {
			jmxManager.invoke(mBeanName, methodName);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}
	
	public void call(String mBeanName, String methodName, String[] signature, Object[] parameters) {
		try {
			jmxManager.invoke(mBeanName, methodName, parameters, signature);
		} catch (Exception e) {
			log.error("Error", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String mBeanName, String methodName) {
		try {
			return (T) jmxManager.invoke(mBeanName, methodName);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String mBeanName, String methodName, String[] signature, Object[] parameters) {
		try {
			return (T) jmxManager.invoke(mBeanName, methodName, parameters, signature);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String mBeanName, String methodName) {
		try {
			return (List<T>) jmxManager.invoke(mBeanName, methodName);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String mBeanName,String methodName, String[] signature, Object[] parameters) {
		try {
			return (List<T>) jmxManager.invoke(mBeanName, methodName, parameters, signature);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> Map<K, T> getMap(String mBeanName,String methodName) {
		try {
			return (Map<K, T>) jmxManager.invoke(mBeanName, methodName);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> Map<K, T> getMap(String mBeanName,String methodName, String[] signature, Object[] parameters) {
		try {
			return (Map<K, T>) jmxManager.invoke(mBeanName, methodName, parameters, signature);
		} catch (Exception e) {
			log.error("Error", e);
			return null;
		}
	}
	
}

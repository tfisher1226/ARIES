/*
 * MBeanUtil.java
 * Created on Jun 2, 2005
 * 
 * 
 */
package org.aries.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MBeanUtil {

    private static Log log = LogFactory.getLog(MBeanUtil.class);
    
    public static MBeanServer INSTANCE = MBeanServerFactory.createMBeanServer();

    
    public static MBeanServer getMBeanServer() {
        return INSTANCE;
    }
    
    public static void setMBeanServer(MBeanServer mbeanServer) {
        INSTANCE = mbeanServer;
    }
    
    public static ObjectName makeObjectName(String mbeanName) {
        try {
            ObjectName objectName = new ObjectName(mbeanName);
            return objectName;
        } catch (MalformedObjectNameException e) {
            log.warn("Could not resolve name: "+mbeanName);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Unexpected error resolving name: "+mbeanName);
            throw new RuntimeException(e);
        }
    }
    
    public static void registerMBean(Object object, String mbeanName) {
        ObjectName objectName = makeObjectName(mbeanName);
        registerMBean(object, objectName);
    }
    
    public static void registerMBean(Object object, ObjectName objectName) {
        try {
            getMBeanServer().registerMBean(object, objectName);
            log.debug("registered MBean: "+objectName);
        } catch (InstanceAlreadyExistsException e) {
            log.warn("Instance already exists: "+objectName);
            throw new RuntimeException(e);
        } catch (NotCompliantMBeanException e) {
            log.error("Not a compliant MBean: "+objectName);
            throw new RuntimeException(e);
        } catch (MBeanRegistrationException e) {
            log.error("Unexpected error registering MBean: "+objectName);
            throw new RuntimeException(e);
        }
    }

    public static void unregisterMBean(String mbeanName) {
        ObjectName objectName = makeObjectName(mbeanName);
        unregisterMBean(objectName);
    }
    
    public static void unregisterMBean(ObjectName objectName) {
        try {
			getMBeanServer().unregisterMBean(objectName);
	        log.debug("unregistered MBean: "+objectName);
		} catch (InstanceNotFoundException e) {
            log.error("Instance not found: "+objectName);
            throw new RuntimeException(e);
		} catch (MBeanRegistrationException e) {
            log.error("Unexpected error unregistering MBean: "+objectName);
            throw new RuntimeException(e);
		}
    }
    
    public static Set<ObjectName> getObjectNames(MBeanServerConnection connection, String objectMask) {
        try {
            ObjectName criteria = MBeanUtil.makeObjectName(objectMask);
            Set<ObjectName> objectNames = connection.queryNames(criteria, null);
            return objectNames;
        } catch (IOException e) {
            log.warn("Query failed for: "+objectMask, e);
            return new HashSet<ObjectName>();
        }
    }
    
}

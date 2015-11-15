package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Callback;
import nam.model.Channels;
import nam.model.Domain;
import nam.model.Import;
import nam.model.Service;
import nam.model.Services;


public class ServicesUtil {

	public static Services newServices() {
		Services services = new Services();
		return services;
	}

	public static Domain getDomain(Services services, String domainName) {
		return getObjectByName(services, Domain.class, domainName);
	}

	public static List<Domain> getDomains(Services services) {
		return getObjectList(services, Domain.class);
	}

	public static void addDomains(Services services, List<Domain> domainList) {
		Iterator<Domain> iterator = domainList.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			addDomain(services, domain);
		}
	}
	
	public static void addDomain(Services services, Domain domain) {
		if (!services.getImportsAndDomainsAndServices().contains(domain))
			services.getImportsAndDomainsAndServices().add(domain);
	}

	public static boolean removeDomain(Services services, Domain domain) {
		return services.getImportsAndDomainsAndServices().remove(domain);
	}

	public static List<Service> getImmediateServices(Services services) {
		return getObjectList(services, Service.class);
	}

	public static List<Service> getServices(Services services) {
		List<Service> serviceList = new ArrayList<Service>();
		if (services != null) {
			serviceList.addAll(ServicesUtil.getImmediateServices(services));
			List<Domain> domains = ServicesUtil.getDomains(services);
			Iterator<Domain> iterator = domains.iterator();
			while (iterator.hasNext()) {
				Domain domain = iterator.next();
				serviceList.addAll(DomainUtil.getServices(domain));
			}
		}
		return serviceList;
	}

	public static void addServices(Services services, List<Service> serviceList) {
		Iterator<Service> iterator = serviceList.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			addService(services, service);
		}
	}
	
	public static void addService(Services services, Service service) {
		if (!services.getImportsAndDomainsAndServices().contains(service))
			services.getImportsAndDomainsAndServices().add(service);
	}

	public static boolean removeService(Services services, Service service) {
		boolean removed = services.getImportsAndDomainsAndServices().remove(service);
		if (!removed) {
			List<Domain> domains = getDomains(services);
			Iterator<Domain> iterator = domains.iterator();
			while (iterator.hasNext()) {
				Domain domain = iterator.next();
				removed = removeService(domain, service);
				if (removed)
					return true;
			}
		}
		return false;
	}

	public static boolean removeService(Domain domain, Service service) {
		return DomainUtil.removeService(domain, service);
	}

	public static List<Callback> getCallbacks(Services services) {
		return DomainUtil.getCallbacks(getDomains(services));
	}
	
	public static List<Import> getImports(Services services) {
		return getObjectList(services, Import.class);
	}

	public static <T> T getObjectById(Services services, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(services, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getId", name);
	}
	
	public static <T> T getObjectByName(Services services, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(services, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Services services, Class<?> objectClass) {
		List<Serializable> objects = services.getImportsAndDomainsAndServices();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Services services, Class<?> objectClass) {
		List<Serializable> objects = services.getImportsAndDomainsAndServices();
		return ListUtil.getObject(objects, objectClass);
	}
	
}

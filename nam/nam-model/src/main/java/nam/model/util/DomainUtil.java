package nam.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nam.model.Application;
import nam.model.Callback;
import nam.model.Domain;
import nam.model.Information;
import nam.model.Listener;
import nam.model.Module;
import nam.model.Network;
import nam.model.Persistence;
import nam.model.Project;
import nam.model.Service;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class DomainUtil extends BaseUtil {

	public static Object getKey(Domain domain) {
		return domain.getNamespace();
	}

	public static String getLabel(Domain domain) {
		return domain.getName();
	}

	public static boolean getLabel(Collection<Domain> domainList) {
		if (domainList == null  || domainList.size() == 0)
			return true;
		Iterator<Domain> iterator = domainList.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			if (!isEmpty(domain))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Domain domain) {
		if (domain == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Domain> domainList) {
		if (domainList == null  || domainList.size() == 0)
			return true;
		Iterator<Domain> iterator = domainList.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			if (!isEmpty(domain))
				return false;
		}
		return true;
	}
	
	public static String toString(Domain domain) {
		if (isEmpty(domain))
			return "Domain: [uninitialized] "+domain.toString();
		String text = domain.toString();
		return text;
	}
	
	public static String toString(Collection<Domain> domainList) {
		if (isEmpty(domainList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Domain> iterator = domainList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Domain domain = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(domain);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Domain create() {
		Domain domain = new Domain();
		initialize(domain);
		return domain;
	}
	
	public static void initialize(Domain domain) {
		//nothing for now
	}
	
	public static boolean validate(Domain domain) {
		if (domain == null)
			return false;
		Validator validator = Validator.getValidator();
		//TODO SerializableUtil.validate(domain.getMembers());
		NamespaceUtil.validate(domain.getNamespace());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Domain> domainList) {
		Validator validator = Validator.getValidator();
		Iterator<Domain> iterator = domainList.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			//TODO break or accumulate?
			validate(domain);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Domain> domainList) {
		Collections.sort(domainList, createDomainComparator());
	}
	
	public static Collection<Domain> sortRecords(Collection<Domain> domainCollection) {
		List<Domain> list = new ArrayList<Domain>(domainCollection);
		Collections.sort(list, createDomainComparator());
		return list;
	}
	
	public static Comparator<Domain> createDomainComparator() {
		return new Comparator<Domain>() {
			public int compare(Domain domain1, Domain domain2) {
				Object key1 = getKey(domain1);
				Object key2 = getKey(domain2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Domain clone(Domain domain) {
		if (domain == null)
			return null;
		Domain clone = create();
		clone.setName(ObjectUtil.clone(domain.getName()));
		clone.setLabel(ObjectUtil.clone(domain.getLabel()));
		clone.setVersion(ObjectUtil.clone(domain.getVersion()));
		clone.setNamespace(NamespaceUtil.clone(domain.getNamespace()));
		clone.setDescription(ObjectUtil.clone(domain.getDescription()));
		List<Serializable> objectList = domain.getMembers();
		Iterator<Serializable> iterator = objectList.iterator();
		while (iterator.hasNext()) {
			Serializable object = iterator.next();
			if (object instanceof Application) {
				clone.addToMembers(ApplicationUtil.clone((Application) object));
			} else if (object instanceof Module) {
				clone.addToMembers(ModuleUtil.clone((Module) object));
			} else if (object instanceof Information) {
				clone.addToMembers(InformationUtil.clone((Information) object));
			} else if (object instanceof Persistence) {
				clone.addToMembers(PersistenceUtil.clone((Persistence) object));
			} else if (object instanceof Service) {
				clone.addToMembers(ServiceUtil.clone((Service) object));
			} else if (object instanceof Listener) {
				clone.addToMembers(ListenerUtil.clone((Listener) object));
			}
		}
		return clone;
	}

	
	public static void addObject(Domain domain, Serializable object) {
		if (!domain.getMembers().contains(object)) {
			domain.getMembers().add(object);
		}
	}

	public static boolean removeObject(Domain domain, Serializable object) {
		return domain.getMembers().remove(object);
	}
	

	public static boolean containsService(Domain domain, String serviceId) {
		List<Service> services = getServices(domain);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String serviceId1 = ServiceUtil.getServiceId(service);
			if (serviceId1.equals(serviceId)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addServices(Domain domain, List<Service> services) {
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			addService(domain, service);
		}
	}
	
	public static void addService(Domain domain, Service service) {
		if (!domain.getMembers().contains(service)) {
			domain.getMembers().add(service);
			service.setDomain(domain.getName());
		}
	}

	public static boolean removeService(Domain domain, Service service) {
		return domain.getMembers().remove(service);
	}
	
	public static List<Service> getServices(List<Domain> domains) {
		List<Service> services = new ArrayList<Service>();
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			services.addAll(getServices(domain));
		}
		return services;
	}

	public static List<Service> getServices(Domain domain) {
		return getObjectList(domain, Service.class);
	}

	public static List<Callback> getCallbacks(List<Domain> domains) {
		return getCallbacksFromServices(getServices(domains));
	}
	
	public static List<Callback> getCallbacks(Domain domain) {
		return getCallbacksFromServices(getServices(domain));
	}

	public static List<Callback> getCallbacksFromServices(List<Service> services) {
		List<Callback> callbacks = new ArrayList<Callback>();
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			callbacks.addAll(ServiceUtil.getCallbacks(service));
		}
		return callbacks;
	}
	
	public static Map<String, Service> getServicesForTypes(Domain domain) {
		Map<String, Service> servicesForTypes = new HashMap<String, Service>();
		List<Service> services = getServices(domain);
		Iterator<Service> iterator = services.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			String typeName = service.getElement();
			if (typeName != null) {
				if (!TypeUtil.isValidType(typeName))
					typeName = TypeUtil.getTypeFromNamespaceAndLocalPart(domain.getNamespace(), typeName);
				servicesForTypes.put(typeName, service);
			}
		}
		return servicesForTypes;
	}
	
	public static List<Persistence> getPersistenceBlocks(Domain domain) {
		return getObjectList(domain, Persistence.class);
	}
	
	public static <T> T getObjectByName(Domain domain, Class<?> objectClass, String name) {
		List<Object> objectList = getObjectList(domain, objectClass);
		return ListUtil.getObjectByValue(objectList, objectClass, "getName", name);
	}
	
	public static <T> List<T> getObjectList(Domain domain, Class<?> objectClass) {
		List<Serializable> objects = domain.getMembers();
		return ListUtil.getObjectList(objects, objectClass);
	}
	
	public static <T> T getObject(Domain domain, Class<?> objectClass) {
		List<Serializable> objects = domain.getMembers();
		return ListUtil.getObject(objects, objectClass);
	}

	public static void addApplication(Domain owner, Application application) {
	}

	public static boolean removeApplication(Domain owner, Application application) {
		return false;
	}

	
	public static Collection<Network> getNetworks(Domain domain) {
		return getObjectList(domain, Network.class);
	}

	public static void addNetwork(Domain domain, Network network) {
		if (!domain.getMembers().contains(network)) {
			domain.getMembers().add(network);
			network.setDomain(domain.getName());
		}
	}

	public static boolean removeNetwork(Domain domain, Network network) {
		return domain.getMembers().remove(network);
	}

	public static Collection<Application> getApplications(Collection<Project> projectList, Collection<Domain> domains) {
		Collection<Application> list = new HashSet<Application>();
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			list.addAll(getApplications(projectList, domain));
		}
		return list;
	}

	public static Collection<Application> getApplications(Collection<Project> projectList, Domain domain) {
		Collection<Application> applicationList = ProjectUtil.getApplicationsForGroupId(projectList, domain.getName());
		return applicationList;
	}

	public static Collection<Service> getServices(Collection<Domain> domains) {
		Collection<Service> list = new HashSet<Service>();
		Iterator<Domain> iterator = domains.iterator();
		while (iterator.hasNext()) {
			Domain domain = iterator.next();
			list.addAll(getServices(domain));
		}
		return list;
	}

}

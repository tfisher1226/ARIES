package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.EjbTransport;
import nam.model.HttpTransport;
import nam.model.JmsTransport;
import nam.model.Project;
import nam.model.RmiTransport;
import nam.model.Role2;
import nam.model.Transport;
import nam.model.TransportType;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class TransportUtil extends BaseUtil {
	
	public static Object getKey(Transport transport) {
		return transport.getName();
	}
	
	public static String getLabel(Transport transport) {
		return transport.getName();
	}
	
	public static boolean isEmpty(Transport transport) {
		if (transport == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(transport.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Transport> transportList) {
		if (transportList == null  || transportList.size() == 0)
			return true;
		Iterator<Transport> iterator = transportList.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			if (!isEmpty(transport))
				return false;
		}
		return true;
	}
	
	public static String toString(Transport transport) {
		if (isEmpty(transport))
			return "Transport: [uninitialized] "+transport.toString();
		String text = transport.toString();
		return text;
	}
	
	public static String toString(Collection<Transport> transportList) {
		if (isEmpty(transportList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Transport> iterator = transportList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Transport transport = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(transport);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Transport create() {
		Transport transport = new Transport();
		initialize(transport);
		return transport;
	}
	
	public static void initialize(Transport transport) {
		if (transport.getTransacted() == null)
			transport.setTransacted(false);
	}
	
	public static boolean validate(Transport transport) {
		if (transport == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(transport.getName(), "\"Name\" must be specified");
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Transport> transportList) {
		Validator validator = Validator.getValidator();
		Iterator<Transport> iterator = transportList.iterator();
		while (iterator.hasNext()) {
			Transport transport = iterator.next();
			//TODO break or accumulate?
			validate(transport);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Transport> transportList) {
		Collections.sort(transportList, createTransportComparator());
	}
	
	public static Collection<Transport> sortRecords(Collection<Transport> transportCollection) {
		List<Transport> list = new ArrayList<Transport>(transportCollection);
		Collections.sort(list, createTransportComparator());
		return list;
	}
	
	public static Comparator<Transport> createTransportComparator() {
		return new Comparator<Transport>() {
			public int compare(Transport transport1, Transport transport2) {
				Object key1 = getKey(transport1);
				Object key2 = getKey(transport2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Transport clone(Transport transport) {
		if (transport == null)
			return null;
		Transport clone = create();
		clone.setName(ObjectUtil.clone(transport.getName()));
		clone.setType(transport.getType());
		clone.setHost(ObjectUtil.clone(transport.getHost()));
		clone.setPort(ObjectUtil.clone(transport.getPort()));
		clone.setScope(ObjectUtil.clone(transport.getScope()));
		clone.setTransferMode(transport.getTransferMode());
		clone.setTransacted(ObjectUtil.clone(transport.getTransacted()));
		clone.setProvider(ObjectUtil.clone(transport.getProvider()));
		return clone;
	}


//	public static org.aries.TransportType convertTransportType(TransportType transportType) {
//		switch (transportType) {
//		case RMI: return org.aries.TransportType.RMI;
//		case EJB: return org.aries.TransportType.EJB;
//		case HTTP: return org.aries.TransportType.HTTP;
//		case JMS: return org.aries.TransportType.JMS;
//		default: return null;
//		}
//	}

	public static TransportType getTransportType(Transport transport) {
		//TransportType transportType = null;
		if (transport instanceof RmiTransport)
			return TransportType.RMI; 
		if (transport instanceof EjbTransport)
			return TransportType.EJB; 
		if (transport instanceof HttpTransport)
			return TransportType.HTTP; 
		if (transport instanceof JmsTransport)
			return TransportType.JMS; 
		return null;
	}

	public static RmiTransport getRMITransport(Project project, Role2 role) {
		return (RmiTransport) getTransport(project, role.getTransports(), TransportType.RMI);
	}

	public static EjbTransport getEJBTransport(Project project, Role2 role) {
		return (EjbTransport) getTransport(project, role.getTransports(), TransportType.EJB);
	}

	public static HttpTransport getHTTPTransport(Project project, Role2 role) {
		return (HttpTransport) getTransport(project, role.getTransports(), TransportType.HTTP);
	}

	public static JmsTransport getJMSTransport(Project project, Role2 role) {
		return (JmsTransport) getTransport(project, role.getTransports(), TransportType.JMS);
	}

	public static Transport getTransport(Project project, Role2 role, TransportType transportType) {
		return getTransport(project, role.getTransports(), transportType);
	}

	public static Transport getTransport(Project project, List<Transport> transports, TransportType transportType) {
		Iterator<Transport> transportIterator = transports.iterator();
		while (transportIterator.hasNext()) {
			Transport transport = transportIterator.next();
			String transportRef = transport.getRef();
			if (transportRef != null)
				transport = MessagingUtil.getTransportByName(project, transportRef);
			Assert.notNull(transport, "Transport not found: "+transportRef);
			if (transportType == TransportUtil.getTransportType(transport)) {
				return transport; 
			}
		}
		return null;
	}

}

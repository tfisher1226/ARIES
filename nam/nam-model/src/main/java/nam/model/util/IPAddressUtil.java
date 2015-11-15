package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import nam.model.IPAddress;


public class IPAddressUtil extends BaseUtil {
	
	public static Object getKey(IPAddress iPAddress) {
		return null;
	}
	
	public static boolean getLabel(Collection<IPAddress> iPAddressList) {
		if (iPAddressList == null  || iPAddressList.size() == 0)
			return true;
		Iterator<IPAddress> iterator = iPAddressList.iterator();
		while (iterator.hasNext()) {
			IPAddress iPAddress = iterator.next();
			if (!isEmpty(iPAddress))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(IPAddress iPAddress) {
		if (iPAddress == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<IPAddress> iPAddressList) {
		if (iPAddressList == null  || iPAddressList.size() == 0)
			return true;
		Iterator<IPAddress> iterator = iPAddressList.iterator();
		while (iterator.hasNext()) {
			IPAddress iPAddress = iterator.next();
			if (!isEmpty(iPAddress))
				return false;
		}
		return true;
	}
	
	public static String toString(IPAddress iPAddress) {
		if (isEmpty(iPAddress))
			return "IPAddress: [uninitialized] "+iPAddress.toString();
		String text = iPAddress.toString();
		return text;
	}
	
	public static String toString(Collection<IPAddress> iPAddressList) {
		if (isEmpty(iPAddressList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<IPAddress> iterator = iPAddressList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			IPAddress iPAddress = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(iPAddress);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static IPAddress create() {
		IPAddress iPAddress = new IPAddress();
		initialize(iPAddress);
		return iPAddress;
	}
	
	public static void initialize(IPAddress iPAddress) {
		//nothing for now
	}
	
	public static boolean validate(IPAddress iPAddress) {
		if (iPAddress == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<IPAddress> iPAddressList) {
		Validator validator = Validator.getValidator();
		Iterator<IPAddress> iterator = iPAddressList.iterator();
		while (iterator.hasNext()) {
			IPAddress iPAddress = iterator.next();
			//TODO break or accumulate?
			validate(iPAddress);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<IPAddress> iPAddressList) {
		Collections.sort(iPAddressList, createIPAddressComparator());
	}
	
	public static Collection<IPAddress> sortRecords(Collection<IPAddress> iPAddressCollection) {
		List<IPAddress> list = new ArrayList<IPAddress>(iPAddressCollection);
		Collections.sort(list, createIPAddressComparator());
		return list;
	}
	
	public static Comparator<IPAddress> createIPAddressComparator() {
		return new Comparator<IPAddress>() {
			public int compare(IPAddress iPAddress1, IPAddress iPAddress2) {
				Object key1 = getKey(iPAddress1);
				Object key2 = getKey(iPAddress2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static IPAddress clone(IPAddress iPAddress) {
		if (iPAddress == null)
			return null;
		IPAddress clone = create();
		clone.setDnsName(ObjectUtil.clone(iPAddress.getDnsName()));
		clone.setAddress(ObjectUtil.clone(iPAddress.getAddress()));
		return clone;
	}
	
}

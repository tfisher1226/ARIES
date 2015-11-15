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

import nam.model.Master;


public class MasterUtil extends BaseUtil {
	
	public static Object getKey(Master master) {
		return null;
	}
	
	public static String getLabel(Master master) {
		return null;
	}

	public static boolean getLabel(Collection<Master> masterList) {
		if (masterList == null  || masterList.size() == 0)
			return true;
		Iterator<Master> iterator = masterList.iterator();
		while (iterator.hasNext()) {
			Master master = iterator.next();
			if (!isEmpty(master))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Master master) {
		if (master == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Master> masterList) {
		if (masterList == null  || masterList.size() == 0)
			return true;
		Iterator<Master> iterator = masterList.iterator();
		while (iterator.hasNext()) {
			Master master = iterator.next();
			if (!isEmpty(master))
				return false;
		}
		return true;
	}
	
	public static String toString(Master master) {
		if (isEmpty(master))
			return "Master: [uninitialized] "+master.toString();
		String text = master.toString();
		return text;
	}
	
	public static String toString(Collection<Master> masterList) {
		if (isEmpty(masterList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Master> iterator = masterList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Master master = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(master);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Master create() {
		Master master = new Master();
		initialize(master);
		return master;
	}
	
	public static void initialize(Master master) {
		//nothing for now
	}
	
	public static boolean validate(Master master) {
		if (master == null)
			return false;
		Validator validator = Validator.getValidator();
		IPAddressUtil.validate(master.getBindAddress());
		IPAddressUtil.validate(master.getDnsIP());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Master> masterList) {
		Validator validator = Validator.getValidator();
		Iterator<Master> iterator = masterList.iterator();
		while (iterator.hasNext()) {
			Master master = iterator.next();
			//TODO break or accumulate?
			validate(master);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Master> masterList) {
		Collections.sort(masterList, createMasterComparator());
	}
	
	public static Collection<Master> sortRecords(Collection<Master> masterCollection) {
		List<Master> list = new ArrayList<Master>(masterCollection);
		Collections.sort(list, createMasterComparator());
		return list;
	}
	
	public static Comparator<Master> createMasterComparator() {
		return new Comparator<Master>() {
			public int compare(Master master1, Master master2) {
				Object key1 = getKey(master1);
				Object key2 = getKey(master2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Master clone(Master master) {
		if (master == null)
			return null;
		Master clone = create();
		clone.setVersion(ObjectUtil.clone(master.getVersion()));
		clone.setDnsDomain(ObjectUtil.clone(master.getDnsDomain()));
		clone.setDnsIP(IPAddressUtil.clone(master.getDnsIP()));
		clone.setBindAddress(IPAddressUtil.clone(master.getBindAddress()));
		return clone;
	}
	
}

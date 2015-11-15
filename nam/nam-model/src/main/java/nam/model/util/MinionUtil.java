package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Minion;
import nam.model.Pod;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class MinionUtil extends BaseUtil {
	
	public static Object getKey(Minion minion) {
		return minion.getName();
	}
	
	public static String getLabel(Minion minion) {
		return minion.getName();
	}

	public static boolean getLabel(Collection<Minion> minionList) {
		if (minionList == null  || minionList.size() == 0)
			return true;
		Iterator<Minion> iterator = minionList.iterator();
		while (iterator.hasNext()) {
			Minion minion = iterator.next();
			if (!isEmpty(minion))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Minion minion) {
		if (minion == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Minion> minionList) {
		if (minionList == null  || minionList.size() == 0)
			return true;
		Iterator<Minion> iterator = minionList.iterator();
		while (iterator.hasNext()) {
			Minion minion = iterator.next();
			if (!isEmpty(minion))
				return false;
		}
		return true;
	}
	
	public static String toString(Minion minion) {
		if (isEmpty(minion))
			return "Minion: [uninitialized] "+minion.toString();
		String text = minion.toString();
		return text;
	}
	
	public static String toString(Collection<Minion> minionList) {
		if (isEmpty(minionList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Minion> iterator = minionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Minion minion = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(minion);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Minion create() {
		Minion minion = new Minion();
		initialize(minion);
		return minion;
	}
	
	public static void initialize(Minion minion) {
		//nothing for now
	}
	
	public static boolean validate(Minion minion) {
		if (minion == null)
			return false;
		Validator validator = Validator.getValidator();
		IPAddressUtil.validate(minion.getBindAddress());
		IPAddressUtil.validate(minion.getDnsIP());
		PodUtil.validate(minion.getPods());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Minion> minionList) {
		Validator validator = Validator.getValidator();
		Iterator<Minion> iterator = minionList.iterator();
		while (iterator.hasNext()) {
			Minion minion = iterator.next();
			//TODO break or accumulate?
			validate(minion);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Minion> minionList) {
		Collections.sort(minionList, createMinionComparator());
	}
	
	public static Collection<Minion> sortRecords(Collection<Minion> minionCollection) {
		List<Minion> list = new ArrayList<Minion>(minionCollection);
		Collections.sort(list, createMinionComparator());
		return list;
	}
	
	public static Comparator<Minion> createMinionComparator() {
		return new Comparator<Minion>() {
			public int compare(Minion minion1, Minion minion2) {
				Object key1 = getKey(minion1);
				Object key2 = getKey(minion2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Minion clone(Minion minion) {
		if (minion == null)
			return null;
		Minion clone = create();
		clone.setVersion(ObjectUtil.clone(minion.getVersion()));
		clone.setDnsDomain(ObjectUtil.clone(minion.getDnsDomain()));
		clone.setDnsIP(IPAddressUtil.clone(minion.getDnsIP()));
		clone.setBindAddress(IPAddressUtil.clone(minion.getBindAddress()));
		clone.setVolumeDirectory(ObjectUtil.clone(minion.getVolumeDirectory()));
		clone.setPods(PodUtil.clone(minion.getPods()));
		return clone;
	}

	public static Pod getPod(Minion minion) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

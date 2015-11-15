package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Container;
import nam.model.Pod;
import nam.model.Service;
import nam.model.Volume;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class PodUtil extends BaseUtil {

	public static Object getKey(Pod pod) {
		return pod.getName();
	}
	
	public static String getLabel(Pod pod) {
		return pod.getName();
	}
	
	public static boolean getLabel(Collection<Pod> podList) {
		if (podList == null  || podList.size() == 0)
			return true;
		Iterator<Pod> iterator = podList.iterator();
		while (iterator.hasNext()) {
			Pod pod = iterator.next();
			if (!isEmpty(pod))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Pod pod) {
		if (pod == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Pod> podList) {
		if (podList == null  || podList.size() == 0)
			return true;
		Iterator<Pod> iterator = podList.iterator();
		while (iterator.hasNext()) {
			Pod pod = iterator.next();
			if (!isEmpty(pod))
				return false;
		}
		return true;
	}
	
	public static String toString(Pod pod) {
		if (isEmpty(pod))
			return "Pod: [uninitialized] "+pod.toString();
		String text = pod.toString();
		return text;
	}
	
	public static String toString(Collection<Pod> podList) {
		if (isEmpty(podList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Pod> iterator = podList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Pod pod = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(pod);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Pod create() {
		Pod pod = new Pod();
		initialize(pod);
		return pod;
	}
	
	public static void initialize(Pod pod) {
		//nothing for now
	}
	
	public static boolean validate(Pod pod) {
		if (pod == null)
			return false;
		Validator validator = Validator.getValidator();
		ContainerUtil.validate(pod.getContainers());
		IPAddressUtil.validate(pod.getIpAddress());
		VolumeUtil.validate(pod.getVolumes());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Pod> podList) {
		Validator validator = Validator.getValidator();
		Iterator<Pod> iterator = podList.iterator();
		while (iterator.hasNext()) {
			Pod pod = iterator.next();
			//TODO break or accumulate?
			validate(pod);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Pod> podList) {
		Collections.sort(podList, createPodComparator());
	}
	
	public static Collection<Pod> sortRecords(Collection<Pod> podCollection) {
		List<Pod> list = new ArrayList<Pod>(podCollection);
		Collections.sort(list, createPodComparator());
		return list;
	}
	
	public static Comparator<Pod> createPodComparator() {
		return new Comparator<Pod>() {
			public int compare(Pod pod1, Pod pod2) {
				Object key1 = getKey(pod1);
				Object key2 = getKey(pod2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Pod clone(Pod pod) {
		if (pod == null)
			return null;
		Pod clone = create();
		clone.setName(ObjectUtil.clone(pod.getName()));
		clone.setLabel(ObjectUtil.clone(pod.getLabel()));
		clone.setIpAddress(IPAddressUtil.clone(pod.getIpAddress()));
		clone.setContainers(ContainerUtil.clone(pod.getContainers()));
		clone.setVolumes(VolumeUtil.clone(pod.getVolumes()));
		return clone;
	}
	
	public static List<Pod> clone(List<Pod> podList) {
		if (podList == null)
			return null;
		List<Pod> newList = new ArrayList<Pod>();
		Iterator<Pod> iterator = podList.iterator();
		while (iterator.hasNext()) {
			Pod pod = iterator.next();
			Pod clone = clone(pod);
			newList.add(clone);
		}
		return newList;
	}

	public static Collection<Service> getServices(Pod pod) {
		return null;
	}

	public static Collection<Container> getContainers(Pod pod) {
		return null;
	}

	public static Collection<Volume> getVolumes(Pod pod) {
		return null;
	}
	
}

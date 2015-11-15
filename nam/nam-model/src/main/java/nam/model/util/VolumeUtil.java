package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Volume;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class VolumeUtil extends BaseUtil {

	public static Object getKey(Volume volume) {
		return volume.getName();
	}
	
	public static String getLabel(Volume volume) {
		return volume.getName();
	}
	
	public static boolean getLabel(Collection<Volume> volumeList) {
		if (volumeList == null  || volumeList.size() == 0)
			return true;
		Iterator<Volume> iterator = volumeList.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			if (!isEmpty(volume))
				return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Volume volume) {
		if (volume == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Volume> volumeList) {
		if (volumeList == null  || volumeList.size() == 0)
			return true;
		Iterator<Volume> iterator = volumeList.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			if (!isEmpty(volume))
				return false;
		}
		return true;
	}
	
	public static String toString(Volume volume) {
		if (isEmpty(volume))
			return "Volume: [uninitialized] "+volume.toString();
		String text = volume.toString();
		return text;
	}
	
	public static String toString(Collection<Volume> volumeList) {
		if (isEmpty(volumeList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Volume> iterator = volumeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Volume volume = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(volume);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Volume create() {
		Volume volume = new Volume();
		initialize(volume);
		return volume;
	}
	
	public static void initialize(Volume volume) {
		//nothing for now
	}
	
	public static boolean validate(Volume volume) {
		if (volume == null)
			return false;
		Validator validator = Validator.getValidator();
		PropertiesUtil.validate(volume.getProperties());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Volume> volumeList) {
		Validator validator = Validator.getValidator();
		Iterator<Volume> iterator = volumeList.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			//TODO break or accumulate?
			validate(volume);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Volume> volumeList) {
		Collections.sort(volumeList, createVolumeComparator());
	}
	
	public static Collection<Volume> sortRecords(Collection<Volume> volumeCollection) {
		List<Volume> list = new ArrayList<Volume>(volumeCollection);
		Collections.sort(list, createVolumeComparator());
		return list;
	}
	
	public static Comparator<Volume> createVolumeComparator() {
		return new Comparator<Volume>() {
			public int compare(Volume volume1, Volume volume2) {
				Object key1 = getKey(volume1);
				Object key2 = getKey(volume2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Volume clone(Volume volume) {
		if (volume == null)
			return null;
		Volume clone = create();
		clone.setType(ObjectUtil.clone(volume.getType()));
		clone.setName(ObjectUtil.clone(volume.getName()));
		clone.setStore(ObjectUtil.clone(volume.getStore()));
		clone.setProperties(PropertiesUtil.clone(volume.getProperties()));
		return clone;
	}
	
	public static List<Volume> clone(List<Volume> volumeList) {
		if (volumeList == null)
			return null;
		List<Volume> newList = new ArrayList<Volume>();
		Iterator<Volume> iterator = volumeList.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			Volume clone = clone(volume);
			newList.add(clone);
		}
		return newList;
	}
	
}

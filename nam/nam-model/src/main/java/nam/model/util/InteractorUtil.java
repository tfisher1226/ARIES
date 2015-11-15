package nam.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.DeliveryMode;
import nam.model.Interactor;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class InteractorUtil extends BaseUtil {
	
	public static Object getKey(Interactor interactor) {
		return interactor.getName() + ":" + interactor.getChannel();
	}
	
	public static String getLabel(Interactor interactor) {
		return interactor.getName();
	}
	
	public static boolean isEmpty(Interactor interactor) {
		if (interactor == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<Interactor> interactorList) {
		if (interactorList == null  || interactorList.size() == 0)
			return true;
		Iterator<Interactor> iterator = interactorList.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			if (!isEmpty(interactor))
				return false;
		}
		return true;
	}
	
	public static String toString(Interactor interactor) {
		if (isEmpty(interactor))
			return "Interactor: [uninitialized] "+interactor.toString();
		String text = interactor.toString();
		return text;
	}
	
	public static String toString(Collection<Interactor> interactorList) {
		if (isEmpty(interactorList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Interactor> iterator = interactorList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Interactor interactor = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(interactor);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Interactor create() {
		Interactor interactor = new Interactor();
		initialize(interactor);
		return interactor;
	}
	
	public static void initialize(Interactor interactor) {
		if (interactor.getDeliveryMode() == null)
			interactor.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	public static boolean validate(Interactor interactor) {
		if (interactor == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Interactor> interactorList) {
		Validator validator = Validator.getValidator();
		Iterator<Interactor> iterator = interactorList.iterator();
		while (iterator.hasNext()) {
			Interactor interactor = iterator.next();
			//TODO break or accumulate?
			validate(interactor);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Interactor> interactorList) {
		Collections.sort(interactorList, createInteractorComparator());
	}
	
	public static Collection<Interactor> sortRecords(Collection<Interactor> interactorCollection) {
		List<Interactor> list = new ArrayList<Interactor>(interactorCollection);
		Collections.sort(list, createInteractorComparator());
		return list;
	}
	
	public static Comparator<Interactor> createInteractorComparator() {
		return new Comparator<Interactor>() {
			public int compare(Interactor interactor1, Interactor interactor2) {
				Object key1 = getKey(interactor1);
				Object key2 = getKey(interactor2);
				String text1 = key1.toString();
				String text2 = key2.toString();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
	public static Interactor clone(Interactor interactor) {
		if (interactor == null)
			return null;
		Interactor clone = create();
		clone.setRef(ObjectUtil.clone(interactor.getRef()));
		clone.setName(ObjectUtil.clone(interactor.getName()));
		clone.setRole(ObjectUtil.clone(interactor.getRole()));
		clone.setService(ObjectUtil.clone(interactor.getService()));
		clone.setChannel(ObjectUtil.clone(interactor.getChannel()));
		clone.setInteraction(interactor.getInteraction());
		clone.setDeliveryMode(interactor.getDeliveryMode());
		clone.setTimeToLive(ObjectUtil.clone(interactor.getTimeToLive()));
		clone.setAsynchronous(ObjectUtil.clone(interactor.getAsynchronous()));
		clone.setTransacted(ObjectUtil.clone(interactor.getTransacted()));
		clone.setDurable(ObjectUtil.clone(interactor.getDurable()));
		clone.setPriority(ObjectUtil.clone(interactor.getPriority()));
		clone.setTimeout(ObjectUtil.clone(interactor.getTimeout()));
		return clone;
	}
	
}

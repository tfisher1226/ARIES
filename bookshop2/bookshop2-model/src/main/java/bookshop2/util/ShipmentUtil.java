package bookshop2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.util.BaseUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;

import bookshop2.Shipment;


public class ShipmentUtil extends BaseUtil {
	
	public static boolean isEmpty(Shipment shipment) {
		if (shipment == null)
			return true;
		boolean status = false;
		status |= OrderUtil.isEmpty(shipment.getOrder());
		status |= PersonNameUtil.isEmpty(shipment.getName());
		status |= StreetAddressUtil.isEmpty(shipment.getAddress());
		return status;
	}
	
	public static boolean isEmpty(Collection<Shipment> shipmentList) {
		if (shipmentList == null  || shipmentList.size() == 0)
			return true;
		Iterator<Shipment> iterator = shipmentList.iterator();
		while (iterator.hasNext()) {
			Shipment shipment = iterator.next();
			if (!isEmpty(shipment))
				return false;
		}
		return true;
	}
	
	public static String toString(Shipment shipment) {
		if (isEmpty(shipment))
			return "Shipment: [uninitialized] "+shipment.toString();
		String text = shipment.toString();
		return text;
	}
	
	public static String toString(Collection<Shipment> shipmentList) {
		if (isEmpty(shipmentList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Shipment> iterator = shipmentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Shipment shipment = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(shipment);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static Shipment create() {
		Shipment shipment = new Shipment();
		initialize(shipment);
		return shipment;
	}
	
	public static void initialize(Shipment shipment) {
		//nothing for now
	}
	
	public static boolean validate(Shipment shipment) {
		if (shipment == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(StreetAddressUtil.isEmpty(shipment.getAddress()), "\"Address\" must be specified");
		validator.notNull(shipment.getDate(), "\"Date\" must be specified");
		validator.isFalse(PersonNameUtil.isEmpty(shipment.getName()), "\"Name\" must be specified");
		validator.isFalse(OrderUtil.isEmpty(shipment.getOrder()), "\"Order\" must be specified");
		validator.notNull(shipment.getTime(), "\"Time\" must be specified");
		StreetAddressUtil.validate(shipment.getAddress());
		PersonNameUtil.validate(shipment.getName());
		OrderUtil.validate(shipment.getOrder());
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Shipment> shipmentList) {
		Validator validator = Validator.getValidator();
		Iterator<Shipment> iterator = shipmentList.iterator();
		while (iterator.hasNext()) {
			Shipment shipment = iterator.next();
			//TODO break or accumulate?
			validate(shipment);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Shipment> shipmentList) {
		Collections.sort(shipmentList, createShipmentComparator());
	}
	
	public static Collection<Shipment> sortRecords(Collection<Shipment> shipmentCollection) {
		List<Shipment> list = new ArrayList<Shipment>(shipmentCollection);
		Collections.sort(list, createShipmentComparator());
		return list;
	}
	
	public static Comparator<Shipment> createShipmentComparator() {
		return new Comparator<Shipment>() {
			public int compare(Shipment shipment1, Shipment shipment2) {
				int status = shipment1.compareTo(shipment2);
				return status;
			}
		};
	}
	
	public static Shipment clone(Shipment shipment) {
		if (shipment == null)
			return null;
		Shipment clone = create();
		clone.setId(ObjectUtil.clone(shipment.getId()));
		clone.setOrder(OrderUtil.clone(shipment.getOrder()));
		clone.setDate(ObjectUtil.clone(shipment.getDate()));
		clone.setTime(ObjectUtil.clone(shipment.getTime()));
		clone.setName(PersonNameUtil.clone(shipment.getName()));
		clone.setAddress(StreetAddressUtil.clone(shipment.getAddress()));
		return clone;
	}
	
}

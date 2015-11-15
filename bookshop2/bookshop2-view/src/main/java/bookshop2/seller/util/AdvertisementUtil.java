package bookshop2.seller.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PhoneNumberUtil;
import org.aries.util.Validator;
import org.aries.util.ObjectUtil;

import bookshop2.seller.Advertisement;


public class AdvertisementUtil {
	
	public static boolean isEmpty(Advertisement advertisement) {
		if (advertisement == null)
			return true;
		boolean status = false;
		status |= EmailAddressUtil.isEmpty(advertisement.getEmailAddress());
		status |= StringUtils.isEmpty(advertisement.getFirstName());
		status |= StringUtils.isEmpty(advertisement.getLastName());
		status |= StringUtils.isEmpty(advertisement.getProductId());
		return status;
	}
	
	public static boolean isEmpty(Collection<Advertisement> advertisementList) {
		if (advertisementList == null  || advertisementList.size() == 0)
			return true;
		Iterator<Advertisement> iterator = advertisementList.iterator();
		while (iterator.hasNext()) {
			Advertisement advertisement = iterator.next();
			if (!isEmpty(advertisement))
				return false;
		}
		return true;
	}
	
	public static String toString(Advertisement advertisement) {
		if (isEmpty(advertisement))
			return "";
		String text = advertisement.toString();
		return text;
	}
	
	public static String toString(Collection<Advertisement> advertisementList) {
		if (isEmpty(advertisementList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Advertisement> iterator = advertisementList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Advertisement advertisement = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(advertisement);
			buf.append(text);
		}
		return buf.toString();
	}
	
	public static Advertisement create() {
		Advertisement advertisement = new Advertisement();
		initialize(advertisement);
		return advertisement;
	}
	
	public static void initialize(Advertisement advertisement) {
		if (advertisement.getEnabled() == null)
			advertisement.setEnabled(true);
		if (advertisement.getOnSale() == null)
			advertisement.setOnSale(true);
	}
	
	public static boolean validate(Advertisement advertisement) {
		if (advertisement == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.isFalse(EmailAddressUtil.isEmpty(advertisement.getEmailAddress()), "\"EmailAddress\" must be specified");
		validator.notEmpty(advertisement.getFirstName(), "\"FirstName\" must be specified");
		validator.notEmpty(advertisement.getLastName(), "\"LastName\" must be specified");
		validator.notEmpty(advertisement.getProductId(), "\"ProductId\" must be specified");
		validator.notNull(advertisement.getSalesPrice(), "\"SalesPrice\" must be specified");
		
		PhoneNumberUtil.validate(advertisement.getCellPhone());
		EmailAddressUtil.validate(advertisement.getEmailAddress());
		PhoneNumberUtil.validate(advertisement.getHomePhone());
		
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Advertisement> advertisementList) {
		Validator validator = Validator.getValidator();
		Iterator<Advertisement> iterator = advertisementList.iterator();
		while (iterator.hasNext()) {
			Advertisement advertisement = iterator.next();
			//TODO break or accumulate?
			validate(advertisement);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Advertisement> advertisementList) {
		Collections.sort(advertisementList, new Comparator<Advertisement>() {
			public int compare(Advertisement advertisement1, Advertisement advertisement2) {
				String text1 = AdvertisementUtil.toString(advertisement1);
				String text2 = AdvertisementUtil.toString(advertisement2);
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Advertisement clone(Advertisement advertisement) {
		if (advertisement == null)
			return null;
		Advertisement clone = create();
		clone.setId(ObjectUtil.clone(advertisement.getId()));
		clone.setEnabled(ObjectUtil.clone(advertisement.getEnabled()));
		clone.setOnSale(ObjectUtil.clone(advertisement.getOnSale()));
		clone.setProductId(ObjectUtil.clone(advertisement.getProductId()));
		clone.setSalesPrice(ObjectUtil.clone(advertisement.getSalesPrice()));
		clone.setSaleExpiration(ObjectUtil.clone(advertisement.getSaleExpiration()));
		clone.setFirstName(ObjectUtil.clone(advertisement.getFirstName()));
		clone.setLastName(ObjectUtil.clone(advertisement.getLastName()));
		clone.setEmailAddress(EmailAddressUtil.clone(advertisement.getEmailAddress()));
		clone.setHomePhone(PhoneNumberUtil.clone(advertisement.getHomePhone()));
		clone.setCellPhone(PhoneNumberUtil.clone(advertisement.getCellPhone()));
		clone.setCreationDate(ObjectUtil.clone(advertisement.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(advertisement.getLastUpdate()));
		return clone;
	}
	
}

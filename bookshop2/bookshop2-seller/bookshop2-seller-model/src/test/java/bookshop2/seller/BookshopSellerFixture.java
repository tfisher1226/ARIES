package bookshop2.seller;

import bookshop2.seller.Advertisement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.aries.common.util.CommonFixture;


public class BookshopSellerFixture {

	public static Advertisement createEmptyAdvertisement() {
		Advertisement advertisement = new Advertisement();
		advertisement.setId(1379409787956L);
		return advertisement;
	}

	public static Advertisement createAdvertisement() {
		Advertisement advertisement = createAdvertisement(0);
		return advertisement;
	}

	public static Advertisement createAdvertisement(int index) {
		Advertisement advertisement = createEmptyAdvertisement();
		advertisement.setId(1379409787957L);
		advertisement.setEnabled(false);
		advertisement.setOnSale(false);
		advertisement.setProductId("dummyProductId" + index);
		advertisement.setSalesPrice(1D + index);
		advertisement.setSaleExpiration(new Date());
		advertisement.setFirstName("dummyFirstName" + index);
		advertisement.setLastName("dummyLastName" + index);
		advertisement.setCreationDate(new Date());
		advertisement.setLastUpdate(new Date());
		advertisement.setEmailAddress(CommonFixture.create_EmailAddress(index));
		advertisement.setHomePhone(CommonFixture.create_PhoneNumber(index));
		advertisement.setCellPhone(CommonFixture.create_PhoneNumber(index));
		return advertisement;
	}

	public static List<Advertisement> createEmptyAdvertisement_List() {
		return new ArrayList<Advertisement>();
	}

	public static List<Advertisement> createAdvertisement_List() {
		return createAdvertisement_List(2);
	}

	public static List<Advertisement> createAdvertisement_List(int size) {
		List<Advertisement> advertisementList = createEmptyAdvertisement_List();
		for (int index=0; index < size; index++)
			advertisementList.add(createAdvertisement(index));
		return advertisementList;
	}

}
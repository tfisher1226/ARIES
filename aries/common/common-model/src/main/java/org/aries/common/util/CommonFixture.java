package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aries.Assert;
import org.aries.common.Attachment;
import org.aries.common.Country;
import org.aries.common.EmailAccount;
import org.aries.common.EmailAddress;
import org.aries.common.EmailAddressList;
import org.aries.common.EmailBox;
import org.aries.common.EmailMessage;
import org.aries.common.Person;
import org.aries.common.PersonName;
import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.common.Property;
import org.aries.common.State;
import org.aries.common.StreetAddress;
import org.aries.common.ZipCode;
import org.aries.util.BaseFixture;


public class CommonFixture extends BaseFixture {

	public static Property createEmpty_Property() {
		Property property = new Property();
		return property;
	}
	
	public static Property create_Property() {
		Property property = create_Property(1);
		return property;
	}
	
	public static Property create_Property(long index) {
		Property property = createEmpty_Property();
		property.setName("dummyName" + index);
		property.setValue("dummyValue" + index);
		property.setId(10L * index);
		return property;
	}
	
	public static List<Property> createEmptyList_Property() {
		return new ArrayList<Property>();
	}
	
	public static List<Property> createList_Property() {
		return createList_Property(2);
	}
	
	public static List<Property> createList_Property(int size) {
		return createList_Property(1, size);
	}
	
	public static List<Property> createList_Property(long index, int size) {
		List<Property> propertyList = createEmptyList_Property();
		long limit = index + size;
		for (; index < limit; index++)
			propertyList.add(create_Property(index));
		return propertyList;
	}
	
	public static Set<Property> createEmptySet_Property() {
		return new HashSet<Property>();
	}
	
	public static Set<Property> createSet_Property() {
		return createSet_Property(2);
	}
	
	public static Set<Property> createSet_Property(int size) {
		return createSet_Property(1, size);
	}
	
	public static Set<Property> createSet_Property(long index, int size) {
		Set<Property> propertySet = createEmptySet_Property();
		long limit = index + size;
		for (; index < limit; index++)
			propertySet.add(create_Property(index));
		return propertySet;
	}
	
	public static void assertPropertyExists(Collection<Property> propertyList, Property property) {
		Assert.notNull(propertyList, "Property list must be specified");
		Assert.notNull(property, "Property record must be specified");
		Iterator<Property> iterator = propertyList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Property property1 = iterator.next();
			if (property1.equals(property)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Property should exist: "+property);
	}
	
	public static void assertPropertyCorrect(Property property) {
		long index = property.getId() / 10L;
		assertObjectCorrect("Name", property.getName(), index);
		assertObjectCorrect("Value", property.getValue(), index);
	}
	
	public static void assertPropertyCorrect(Collection<Property> propertyList) {
		Assert.isTrue(propertyList.size() == 2, "Property count not correct");
		Iterator<Property> iterator = propertyList.iterator();
		while (iterator.hasNext()) {
			Property property = iterator.next();
			assertPropertyCorrect(property);
		}
	}
	
	public static void assertSameProperty(Property property1, Property property2) {
		assertSameProperty(property1, property2, false, "");
	}
	
	public static void assertSameProperty(Property property1, Property property2, String message) {
		assertSameProperty(property1, property2, false, message);
	}
	
	public static void assertSameProperty(Property property1, Property property2, boolean checkIds) {
		assertSameProperty(property1, property2, checkIds, "");
	}
	
	public static void assertSameProperty(Property property1, Property property2, boolean checkIds, String message) {
		assertObjectExists("Property1", property1);
		assertObjectExists("Property2", property2);
		if (checkIds)
			assertObjectEquals("Id", property1.getId(), property2.getId(), message);
		assertObjectEquals("Name", property1.getName(), property2.getName(), message);
		assertObjectEquals("Value", property1.getValue(), property2.getValue(), message);
	}
	
	public static void assertSameProperty(Collection<Property> propertyList1, Collection<Property> propertyList2) {
		assertSameProperty(propertyList1, propertyList2, false, "");
	}
	
	public static void assertSameProperty(Collection<Property> propertyList1, Collection<Property> propertyList2, String message) {
		assertSameProperty(propertyList1, propertyList2, false, message);
	}
	
	public static void assertSameProperty(Collection<Property> propertyList1, Collection<Property> propertyList2, boolean checkIds) {
		assertSameProperty(propertyList1, propertyList2, checkIds, "");
	}
	
	public static void assertSameProperty(Collection<Property> propertyList1, Collection<Property> propertyList2, boolean checkIds, String message) {
		Assert.notNull(propertyList1, "Property list1 must be specified");
		Assert.notNull(propertyList2, "Property list2 must be specified");
		Assert.equals(propertyList1.size(), propertyList2.size(), "Property count not equal");
		Collection<Property> sortedRecords1 = PropertyUtil.sortRecords(propertyList1);
		Collection<Property> sortedRecords2 = PropertyUtil.sortRecords(propertyList2);
		Iterator<Property> list1Iterator = sortedRecords1.iterator();
		Iterator<Property> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Property property1 = list1Iterator.next();
			Property property2 = list2Iterator.next();
			assertSameProperty(property1, property2, checkIds, message);
		}
	}
	
	public static Attachment createEmpty_Attachment() {
		Attachment attachment = new Attachment();
		return attachment;
	}
	
	public static Attachment create_Attachment() {
		Attachment attachment = create_Attachment(1);
		return attachment;
	}
	
	public static Attachment create_Attachment(long index) {
		Attachment attachment = createEmpty_Attachment();
		attachment.setName("dummyName" + index);
		attachment.setSize(1L + (long) index);
		attachment.setFileName("dummyFileName" + index);
		attachment.setFileData(new String("dummyFileData" + index).getBytes());
		attachment.setContentType("dummyContentType" + index);
		attachment.setId(10L * index);
		return attachment;
	}
	
	public static List<Attachment> createEmptyList_Attachment() {
		return new ArrayList<Attachment>();
	}
	
	public static List<Attachment> createList_Attachment() {
		return createList_Attachment(2);
	}
	
	public static List<Attachment> createList_Attachment(int size) {
		return createList_Attachment(1, size);
	}
	
	public static List<Attachment> createList_Attachment(long index, int size) {
		List<Attachment> attachmentList = createEmptyList_Attachment();
		long limit = index + size;
		for (; index < limit; index++)
			attachmentList.add(create_Attachment(index));
		return attachmentList;
	}
	
	public static Set<Attachment> createEmptySet_Attachment() {
		return new HashSet<Attachment>();
	}
	
	public static Set<Attachment> createSet_Attachment() {
		return createSet_Attachment(2);
	}
	
	public static Set<Attachment> createSet_Attachment(int size) {
		return createSet_Attachment(1, size);
	}
	
	public static Set<Attachment> createSet_Attachment(long index, int size) {
		Set<Attachment> attachmentSet = createEmptySet_Attachment();
		long limit = index + size;
		for (; index < limit; index++)
			attachmentSet.add(create_Attachment(index));
		return attachmentSet;
	}
	
	public static void assertAttachmentExists(Collection<Attachment> attachmentList, Attachment attachment) {
		Assert.notNull(attachmentList, "Attachment list must be specified");
		Assert.notNull(attachment, "Attachment record must be specified");
		Iterator<Attachment> iterator = attachmentList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Attachment attachment1 = iterator.next();
			if (attachment1.equals(attachment)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Attachment should exist: "+attachment);
	}
	
	public static void assertAttachmentCorrect(Attachment attachment) {
		long index = attachment.getId() / 10L;
		assertObjectCorrect("Name", attachment.getName(), index);
		assertObjectCorrect("Size", attachment.getSize(), index);
		assertObjectCorrect("FileName", attachment.getFileName(), index);
		assertObjectCorrect("FileData", attachment.getFileData(), index);
		assertObjectCorrect("ContentType", attachment.getContentType(), index);
	}
	
	public static void assertAttachmentCorrect(Collection<Attachment> attachmentList) {
		Assert.isTrue(attachmentList.size() == 2, "Attachment count not correct");
		Iterator<Attachment> iterator = attachmentList.iterator();
		while (iterator.hasNext()) {
			Attachment attachment = iterator.next();
			assertAttachmentCorrect(attachment);
		}
	}
	
	public static void assertSameAttachment(Attachment attachment1, Attachment attachment2) {
		assertSameAttachment(attachment1, attachment2, false, "");
	}
	
	public static void assertSameAttachment(Attachment attachment1, Attachment attachment2, String message) {
		assertSameAttachment(attachment1, attachment2, false, message);
	}
	
	public static void assertSameAttachment(Attachment attachment1, Attachment attachment2, boolean checkIds) {
		assertSameAttachment(attachment1, attachment2, checkIds, "");
	}
	
	public static void assertSameAttachment(Attachment attachment1, Attachment attachment2, boolean checkIds, String message) {
		assertObjectExists("Attachment1", attachment1);
		assertObjectExists("Attachment2", attachment2);
		if (checkIds)
			assertObjectEquals("Id", attachment1.getId(), attachment2.getId(), message);
		assertObjectEquals("Name", attachment1.getName(), attachment2.getName(), message);
		assertObjectEquals("Size", attachment1.getSize(), attachment2.getSize(), message);
		assertObjectEquals("FileName", attachment1.getFileName(), attachment2.getFileName(), message);
		assertObjectEquals("FileData", attachment1.getFileData(), attachment2.getFileData(), message);
		assertObjectEquals("ContentType", attachment1.getContentType(), attachment2.getContentType(), message);
	}
	
	public static void assertSameAttachment(Collection<Attachment> attachmentList1, Collection<Attachment> attachmentList2) {
		assertSameAttachment(attachmentList1, attachmentList2, false, "");
	}
	
	public static void assertSameAttachment(Collection<Attachment> attachmentList1, Collection<Attachment> attachmentList2, String message) {
		assertSameAttachment(attachmentList1, attachmentList2, false, message);
	}
	
	public static void assertSameAttachment(Collection<Attachment> attachmentList1, Collection<Attachment> attachmentList2, boolean checkIds) {
		assertSameAttachment(attachmentList1, attachmentList2, checkIds, "");
	}
	
	public static void assertSameAttachment(Collection<Attachment> attachmentList1, Collection<Attachment> attachmentList2, boolean checkIds, String message) {
		Assert.notNull(attachmentList1, "Attachment list1 must be specified");
		Assert.notNull(attachmentList2, "Attachment list2 must be specified");
		Assert.equals(attachmentList1.size(), attachmentList2.size(), "Attachment count not equal");
		Collection<Attachment> sortedRecords1 = AttachmentUtil.sortRecords(attachmentList1);
		Collection<Attachment> sortedRecords2 = AttachmentUtil.sortRecords(attachmentList2);
		Iterator<Attachment> list1Iterator = sortedRecords1.iterator();
		Iterator<Attachment> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Attachment attachment1 = list1Iterator.next();
			Attachment attachment2 = list2Iterator.next();
			assertSameAttachment(attachment1, attachment2, checkIds, message);
		}
	}
	
	public static Person createEmpty_Person() {
		Person person = new Person();
		return person;
	}

	public static Person create_Person() {
		Person person = create_Person(1);
		return person;
	}

	public static Person create_Person(long index) {
		Person person = createEmpty_Person();
		person.setUserId("dummyUserId" + index);
		person.setName(create_PersonName(index));
		person.setPhoneNumber(create_PhoneNumber(index));
		person.setEmailAddress(create_EmailAddress(index));
		person.setStreetAddress(create_StreetAddress(index));
		person.setId(10L * index);
		return person;
	}

	public static List<Person> createEmptyList_Person() {
		return new ArrayList<Person>();
	}

	public static List<Person> createList_Person() {
		return createList_Person(2);
	}

	public static List<Person> createList_Person(int size) {
		return createList_Person(1, size);
	}
	
	public static List<Person> createList_Person(long index, int size) {
		List<Person> personList = createEmptyList_Person();
		long limit = index + size;
		for (; index < limit; index++)
			personList.add(create_Person(index));
		return personList;
	}

	public static Set<Person> createEmptySet_Person() {
		return new HashSet<Person>();
	}
	
	public static Set<Person> createSet_Person() {
		return createSet_Person(2);
	}
	
	public static Set<Person> createSet_Person(int size) {
		return createSet_Person(1, size);
	}
	
	public static Set<Person> createSet_Person(long index, int size) {
		Set<Person> personSet = createEmptySet_Person();
		long limit = index + size;
		for (; index < limit; index++)
			personSet.add(create_Person(index));
		return personSet;
	}
	
	public static void assertPersonExists(Collection<Person> personList, Person person) {
		Assert.notNull(personList, "Person list must be specified");
		Assert.notNull(person, "Person record must be specified");
		Iterator<Person> iterator = personList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Person person1 = iterator.next();
			if (person1.equals(person)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - Person should exist: "+person);
	}
	
	public static void assertPersonCorrect(Person person) {
		long index = person.getId() / 10L;
		assertObjectCorrect("UserId", person.getUserId(), index);
		assertPersonNameCorrect(person.getName());
		assertPhoneNumberCorrect(person.getPhoneNumber());
		assertEmailAddressCorrect(person.getEmailAddress());
		assertStreetAddressCorrect(person.getStreetAddress());
	}
	
	public static void assertPersonCorrect(Collection<Person> personList) {
		Assert.isTrue(personList.size() == 2, "Person count not correct");
		Iterator<Person> iterator = personList.iterator();
		while (iterator.hasNext()) {
			Person person = iterator.next();
			assertPersonCorrect(person);
		}
	}
	
	public static void assertSamePerson(Person person1, Person person2) {
		assertSamePerson(person1, person2, false, "");
	}
	
	public static void assertSamePerson(Person person1, Person person2, String message) {
		assertSamePerson(person1, person2, false, message);
	}
	
	public static void assertSamePerson(Person person1, Person person2, boolean checkIds) {
		assertSamePerson(person1, person2, checkIds, "");
	}
	
	public static void assertSamePerson(Person person1, Person person2, boolean checkIds, String message) {
		assertObjectExists("Person1", person1);
		assertObjectExists("Person2", person2);
		if (checkIds)
			assertObjectEquals("Id", person1.getId(), person2.getId(), message);
		assertObjectEquals("UserId", person1.getUserId(), person2.getUserId(), message);
		assertSamePersonName(person1.getName(), person2.getName(), message);
		assertSamePhoneNumber(person1.getPhoneNumber(), person2.getPhoneNumber(), message);
		assertSameEmailAddress(person1.getEmailAddress(), person2.getEmailAddress(), message);
		assertSameStreetAddress(person1.getStreetAddress(), person2.getStreetAddress(), message);
	}
	
	public static void assertSamePerson(Collection<Person> personList1, Collection<Person> personList2) {
		assertSamePerson(personList1, personList2, false, "");
	}
	
	public static void assertSamePerson(Collection<Person> personList1, Collection<Person> personList2, String message) {
		assertSamePerson(personList1, personList2, false, message);
	}
	
	public static void assertSamePerson(Collection<Person> personList1, Collection<Person> personList2, boolean checkIds) {
		assertSamePerson(personList1, personList2, checkIds, "");
	}
	
	public static void assertSamePerson(Collection<Person> personList1, Collection<Person> personList2, boolean checkIds, String message) {
		Assert.notNull(personList1, "Person list1 must be specified");
		Assert.notNull(personList2, "Person list2 must be specified");
		Assert.equals(personList1.size(), personList2.size(), "Person count not equal");
		Collection<Person> sortedRecords1 = PersonUtil.sortRecords(personList1);
		Collection<Person> sortedRecords2 = PersonUtil.sortRecords(personList2);
		Iterator<Person> list1Iterator = sortedRecords1.iterator();
		Iterator<Person> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			Person person1 = list1Iterator.next();
			Person person2 = list2Iterator.next();
			assertSamePerson(person1, person2, checkIds, message);
		}
	}
	
	public static PersonName createEmpty_PersonName() {
		PersonName personName = new PersonName();
		return personName;
	}

	public static PersonName create_PersonName() {
		PersonName personName = create_PersonName(1);
		return personName;
	}

	public static PersonName create_PersonName(long index) {
		PersonName personName = createEmpty_PersonName();
		personName.setLastName("dummyLastName" + index);
		personName.setFirstName("dummyFirstName" + index);
		personName.setMiddleInitial("dummyMiddleInitial" + index);
		personName.setId(10L * index);
		return personName;
	}

	public static List<PersonName> createEmptyList_PersonName() {
		return new ArrayList<PersonName>();
	}

	public static List<PersonName> createList_PersonName() {
		return createList_PersonName(2);
	}

	public static List<PersonName> createList_PersonName(int size) {
		return createList_PersonName(1, size);
	}
	
	public static List<PersonName> createList_PersonName(long index, int size) {
		List<PersonName> personNameList = createEmptyList_PersonName();
		long limit = index + size;
		for (; index < limit; index++)
			personNameList.add(create_PersonName(index));
		return personNameList;
	}
	
	public static Set<PersonName> createEmptySet_PersonName() {
		return new HashSet<PersonName>();
	}
	
	public static Set<PersonName> createSet_PersonName() {
		return createSet_PersonName(2);
	}
	
	public static Set<PersonName> createSet_PersonName(int size) {
		return createSet_PersonName(1, size);
	}
	
	public static Set<PersonName> createSet_PersonName(long index, int size) {
		Set<PersonName> personNameSet = createEmptySet_PersonName();
		long limit = index + size;
		for (; index < limit; index++)
			personNameSet.add(create_PersonName(index));
		return personNameSet;
	}
	
	public static void assertPersonNameExists(Collection<PersonName> personNameList, PersonName personName) {
		Assert.notNull(personNameList, "PersonName list must be specified");
		Assert.notNull(personName, "PersonName record must be specified");
		Iterator<PersonName> iterator = personNameList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersonName personName1 = iterator.next();
			if (personName1.equals(personName)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PersonName should exist: "+personName);
	}
	
	public static void assertPersonNameCorrect(PersonName personName) {
		long index = personName.getId() / 10L;
		assertObjectCorrect("LastName", personName.getLastName(), index);
		assertObjectCorrect("FirstName", personName.getFirstName(), index);
		assertObjectCorrect("MiddleInitial", personName.getMiddleInitial(), index);
	}
	
	public static void assertPersonNameCorrect(Collection<PersonName> personNameList) {
		Assert.isTrue(personNameList.size() == 2, "PersonName count not correct");
		Iterator<PersonName> iterator = personNameList.iterator();
		while (iterator.hasNext()) {
			PersonName personName = iterator.next();
			assertPersonNameCorrect(personName);
		}
	}
	
	public static void assertSamePersonName(PersonName personName1, PersonName personName2) {
		assertSamePersonName(personName1, personName2, false, "");
	}
	
	public static void assertSamePersonName(PersonName personName1, PersonName personName2, String message) {
		assertSamePersonName(personName1, personName2, false, message);
	}
	
	public static void assertSamePersonName(PersonName personName1, PersonName personName2, boolean checkIds) {
		assertSamePersonName(personName1, personName2, checkIds, "");
	}
	
	public static void assertSamePersonName(PersonName personName1, PersonName personName2, boolean checkIds, String message) {
		assertObjectExists("PersonName1", personName1);
		assertObjectExists("PersonName2", personName2);
		if (checkIds)
			assertObjectEquals("Id", personName1.getId(), personName2.getId(), message);
		assertObjectEquals("LastName", personName1.getLastName(), personName2.getLastName(), message);
		assertObjectEquals("FirstName", personName1.getFirstName(), personName2.getFirstName(), message);
		assertObjectEquals("MiddleInitial", personName1.getMiddleInitial(), personName2.getMiddleInitial(), message);
	}
	
	public static void assertSamePersonName(Collection<PersonName> personNameList1, Collection<PersonName> personNameList2) {
		assertSamePersonName(personNameList1, personNameList2, false, "");
	}

	public static void assertSamePersonName(Collection<PersonName> personNameList1, Collection<PersonName> personNameList2, String message) {
		assertSamePersonName(personNameList1, personNameList2, false, message);
	}
	
	public static void assertSamePersonName(Collection<PersonName> personNameList1, Collection<PersonName> personNameList2, boolean checkIds) {
		assertSamePersonName(personNameList1, personNameList2, checkIds, "");
	}
	
	public static void assertSamePersonName(Collection<PersonName> personNameList1, Collection<PersonName> personNameList2, boolean checkIds, String message) {
		Assert.notNull(personNameList1, "PersonName list1 must be specified");
		Assert.notNull(personNameList2, "PersonName list2 must be specified");
		Assert.equals(personNameList1.size(), personNameList2.size(), "PersonName count not equal");
		Collection<PersonName> sortedRecords1 = PersonNameUtil.sortRecords(personNameList1);
		Collection<PersonName> sortedRecords2 = PersonNameUtil.sortRecords(personNameList2);
		Iterator<PersonName> list1Iterator = sortedRecords1.iterator();
		Iterator<PersonName> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PersonName personName1 = list1Iterator.next();
			PersonName personName2 = list2Iterator.next();
			assertSamePersonName(personName1, personName2, checkIds, message);
		}
	}

	public static StreetAddress createEmpty_StreetAddress() {
		StreetAddress streetAddress = new StreetAddress();
		return streetAddress;
	}

	public static StreetAddress create_StreetAddress() {
		StreetAddress streetAddress = create_StreetAddress(1);
		return streetAddress;
	}

	public static StreetAddress create_StreetAddress(long index) {
		StreetAddress streetAddress = createEmpty_StreetAddress();
		streetAddress.setStreet("dummyStreet" + index);
		streetAddress.setCity("dummyCity" + index);
		streetAddress.setState(State.CA);
		streetAddress.setCountry(Country.USA);
		streetAddress.setLatitude(1.0D + (double) index);
		streetAddress.setLongitude(1.0D + (double) index);
		streetAddress.setZipCode(create_ZipCode(index));
		streetAddress.setId(10L * index);
		return streetAddress;
	}

	public static List<StreetAddress> createEmptyList_StreetAddress() {
		return new ArrayList<StreetAddress>();
	}

	public static List<StreetAddress> createList_StreetAddress() {
		return createList_StreetAddress(2);
	}

	public static List<StreetAddress> createList_StreetAddress(int size) {
		return createList_StreetAddress(1, size);
	}
	
	public static List<StreetAddress> createList_StreetAddress(long index, int size) {
		List<StreetAddress> streetAddressList = createEmptyList_StreetAddress();
		long limit = index + size;
		for (; index < limit; index++)
			streetAddressList.add(create_StreetAddress(index));
		return streetAddressList;
	}

	public static Set<StreetAddress> createEmptySet_StreetAddress() {
		return new HashSet<StreetAddress>();
	}
	
	public static Set<StreetAddress> createSet_StreetAddress() {
		return createSet_StreetAddress(2);
	}
	
	public static Set<StreetAddress> createSet_StreetAddress(int size) {
		return createSet_StreetAddress(1, size);
	}
	
	public static Set<StreetAddress> createSet_StreetAddress(long index, int size) {
		Set<StreetAddress> streetAddressSet = createEmptySet_StreetAddress();
		long limit = index + size;
		for (; index < limit; index++)
			streetAddressSet.add(create_StreetAddress(index));
		return streetAddressSet;
	}
	
	public static void assertStreetAddressExists(Collection<StreetAddress> streetAddressList, StreetAddress streetAddress) {
		Assert.notNull(streetAddressList, "StreetAddress list must be specified");
		Assert.notNull(streetAddress, "StreetAddress record must be specified");
		Iterator<StreetAddress> iterator = streetAddressList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			StreetAddress streetAddress1 = iterator.next();
			if (streetAddress1.equals(streetAddress)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - StreetAddress should exist: "+streetAddress);
	}
	
	public static void assertStreetAddressCorrect(StreetAddress streetAddress) {
		long index = streetAddress.getId() / 10L;
		assertObjectCorrect("Street", streetAddress.getStreet(), index);
		assertObjectCorrect("City", streetAddress.getCity(), index);
		assertObjectCorrect("State", streetAddress.getState(), index);
		assertObjectCorrect("Country", streetAddress.getCountry(), index);
		assertObjectCorrect("Latitude", streetAddress.getLatitude(), index);
		assertObjectCorrect("Longitude", streetAddress.getLongitude(), index);
		assertZipCodeCorrect(streetAddress.getZipCode());
	}
	
	public static void assertStreetAddressCorrect(Collection<StreetAddress> streetAddressList) {
		Assert.isTrue(streetAddressList.size() == 2, "StreetAddress count not correct");
		Iterator<StreetAddress> iterator = streetAddressList.iterator();
		while (iterator.hasNext()) {
			StreetAddress streetAddress = iterator.next();
			assertStreetAddressCorrect(streetAddress);
		}
	}
	
	public static void assertSameStreetAddress(StreetAddress streetAddress1, StreetAddress streetAddress2) {
		assertSameStreetAddress(streetAddress1, streetAddress2, false, "");
	}
	
	public static void assertSameStreetAddress(StreetAddress streetAddress1, StreetAddress streetAddress2, String message) {
		assertSameStreetAddress(streetAddress1, streetAddress2, false, message);
	}
	
	public static void assertSameStreetAddress(StreetAddress streetAddress1, StreetAddress streetAddress2, boolean checkIds) {
		assertSameStreetAddress(streetAddress1, streetAddress2, checkIds, "");
	}
	
	public static void assertSameStreetAddress(StreetAddress streetAddress1, StreetAddress streetAddress2, boolean checkIds, String message) {
		assertObjectExists("StreetAddress1", streetAddress1);
		assertObjectExists("StreetAddress2", streetAddress2);
		if (checkIds)
			assertObjectEquals("Id", streetAddress1.getId(), streetAddress2.getId(), message);
		assertObjectEquals("Street", streetAddress1.getStreet(), streetAddress2.getStreet(), message);
		assertObjectEquals("City", streetAddress1.getCity(), streetAddress2.getCity(), message);
		assertObjectEquals("State", streetAddress1.getState(), streetAddress2.getState(), message);
		assertObjectEquals("Country", streetAddress1.getCountry(), streetAddress2.getCountry(), message);
		assertObjectEquals("Latitude", streetAddress1.getLatitude(), streetAddress2.getLatitude(), message);
		assertObjectEquals("Longitude", streetAddress1.getLongitude(), streetAddress2.getLongitude(), message);
		assertSameZipCode(streetAddress1.getZipCode(), streetAddress2.getZipCode(), message);
	}
	
	public static void assertSameStreetAddress(Collection<StreetAddress> streetAddressList1, Collection<StreetAddress> streetAddressList2) {
		assertSameStreetAddress(streetAddressList1, streetAddressList2, false, "");
	}
	
	public static void assertSameStreetAddress(Collection<StreetAddress> streetAddressList1, Collection<StreetAddress> streetAddressList2, String message) {
		assertSameStreetAddress(streetAddressList1, streetAddressList2, false, message);
	}
	
	public static void assertSameStreetAddress(Collection<StreetAddress> streetAddressList1, Collection<StreetAddress> streetAddressList2, boolean checkIds) {
		assertSameStreetAddress(streetAddressList1, streetAddressList2, checkIds, "");
	}
	
	public static void assertSameStreetAddress(Collection<StreetAddress> streetAddressList1, Collection<StreetAddress> streetAddressList2, boolean checkIds, String message) {
		Assert.notNull(streetAddressList1, "StreetAddress list1 must be specified");
		Assert.notNull(streetAddressList2, "StreetAddress list2 must be specified");
		Assert.equals(streetAddressList1.size(), streetAddressList2.size(), "StreetAddress count not equal");
		Collection<StreetAddress> sortedRecords1 = StreetAddressUtil.sortRecords(streetAddressList1);
		Collection<StreetAddress> sortedRecords2 = StreetAddressUtil.sortRecords(streetAddressList2);
		Iterator<StreetAddress> list1Iterator = sortedRecords1.iterator();
		Iterator<StreetAddress> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			StreetAddress streetAddress1 = list1Iterator.next();
			StreetAddress streetAddress2 = list2Iterator.next();
			assertSameStreetAddress(streetAddress1, streetAddress2, checkIds, message);
		}
	}
	
	public static ZipCode createEmpty_ZipCode() {
		ZipCode zipCode = new ZipCode();
		return zipCode;
	}

	public static ZipCode create_ZipCode() {
		ZipCode zipCode = create_ZipCode(1);
		return zipCode;
	}

	public static ZipCode create_ZipCode(long index) {
		ZipCode zipCode = createEmpty_ZipCode();
		zipCode.setNumber("dummyNumber" + index);
		zipCode.setExtension("dummyExtension" + index);
		zipCode.setCountry(Country.USA);
		zipCode.setId(10L * index);
		return zipCode;
	}

	public static List<ZipCode> createEmptyList_ZipCode() {
		return new ArrayList<ZipCode>();
	}

	public static List<ZipCode> createList_ZipCode() {
		return createList_ZipCode(2);
	}

	public static List<ZipCode> createList_ZipCode(int size) {
		return createList_ZipCode(1, size);
	}
	
	public static List<ZipCode> createList_ZipCode(long index, int size) {
		List<ZipCode> zipCodeList = createEmptyList_ZipCode();
		long limit = index + size;
		for (; index < limit; index++)
			zipCodeList.add(create_ZipCode(index));
		return zipCodeList;
	}

	public static Set<ZipCode> createEmptySet_ZipCode() {
		return new HashSet<ZipCode>();
	}
	
	public static Set<ZipCode> createSet_ZipCode() {
		return createSet_ZipCode(2);
	}
	
	public static Set<ZipCode> createSet_ZipCode(int size) {
		return createSet_ZipCode(1, size);
	}
	
	public static Set<ZipCode> createSet_ZipCode(long index, int size) {
		Set<ZipCode> zipCodeSet = createEmptySet_ZipCode();
		long limit = index + size;
		for (; index < limit; index++)
			zipCodeSet.add(create_ZipCode(index));
		return zipCodeSet;
	}
	
	public static void assertZipCodeExists(Collection<ZipCode> zipCodeList, ZipCode zipCode) {
		Assert.notNull(zipCodeList, "ZipCode list must be specified");
		Assert.notNull(zipCode, "ZipCode record must be specified");
		Iterator<ZipCode> iterator = zipCodeList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ZipCode zipCode1 = iterator.next();
			if (zipCode1.equals(zipCode)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ZipCode should exist: "+zipCode);
	}
	
	public static void assertZipCodeCorrect(ZipCode zipCode) {
		long index = zipCode.getId() / 10L;
		assertObjectCorrect("Number", zipCode.getNumber(), index);
		assertObjectCorrect("Extension", zipCode.getExtension(), index);
		assertObjectCorrect("Country", zipCode.getCountry(), index);
	}
	
	public static void assertZipCodeCorrect(Collection<ZipCode> zipCodeList) {
		Assert.isTrue(zipCodeList.size() == 2, "ZipCode count not correct");
		Iterator<ZipCode> iterator = zipCodeList.iterator();
		while (iterator.hasNext()) {
			ZipCode zipCode = iterator.next();
			assertZipCodeCorrect(zipCode);
		}
	}
	
	public static void assertSameZipCode(ZipCode zipCode1, ZipCode zipCode2) {
		assertSameZipCode(zipCode1, zipCode2, false, "");
	}
	
	public static void assertSameZipCode(ZipCode zipCode1, ZipCode zipCode2, String message) {
		assertSameZipCode(zipCode1, zipCode2, false, message);
	}
	
	public static void assertSameZipCode(ZipCode zipCode1, ZipCode zipCode2, boolean checkIds) {
		assertSameZipCode(zipCode1, zipCode2, checkIds, "");
	}
	
	public static void assertSameZipCode(ZipCode zipCode1, ZipCode zipCode2, boolean checkIds, String message) {
		assertObjectExists("ZipCode1", zipCode1);
		assertObjectExists("ZipCode2", zipCode2);
		if (checkIds)
			assertObjectEquals("Id", zipCode1.getId(), zipCode2.getId(), message);
		assertObjectEquals("Number", zipCode1.getNumber(), zipCode2.getNumber(), message);
		assertObjectEquals("Extension", zipCode1.getExtension(), zipCode2.getExtension(), message);
		assertObjectEquals("Country", zipCode1.getCountry(), zipCode2.getCountry(), message);
	}
	
	public static void assertSameZipCode(Collection<ZipCode> zipCodeList1, Collection<ZipCode> zipCodeList2) {
		assertSameZipCode(zipCodeList1, zipCodeList2, false, "");
	}
	
	public static void assertSameZipCode(Collection<ZipCode> zipCodeList1, Collection<ZipCode> zipCodeList2, String message) {
		assertSameZipCode(zipCodeList1, zipCodeList2, false, message);
	}
	
	public static void assertSameZipCode(Collection<ZipCode> zipCodeList1, Collection<ZipCode> zipCodeList2, boolean checkIds) {
		assertSameZipCode(zipCodeList1, zipCodeList2, checkIds, "");
	}
	
	public static void assertSameZipCode(Collection<ZipCode> zipCodeList1, Collection<ZipCode> zipCodeList2, boolean checkIds, String message) {
		Assert.notNull(zipCodeList1, "ZipCode list1 must be specified");
		Assert.notNull(zipCodeList2, "ZipCode list2 must be specified");
		Assert.equals(zipCodeList1.size(), zipCodeList2.size(), "ZipCode count not equal");
		Collection<ZipCode> sortedRecords1 = ZipCodeUtil.sortRecords(zipCodeList1);
		Collection<ZipCode> sortedRecords2 = ZipCodeUtil.sortRecords(zipCodeList2);
		Iterator<ZipCode> list1Iterator = sortedRecords1.iterator();
		Iterator<ZipCode> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			ZipCode zipCode1 = list1Iterator.next();
			ZipCode zipCode2 = list2Iterator.next();
			assertSameZipCode(zipCode1, zipCode2, checkIds, message);
		}
	}
	
	public static PhoneNumber createEmpty_PhoneNumber() {
		PhoneNumber phoneNumber = new PhoneNumber();
		return phoneNumber;
	}

	public static PhoneNumber create_PhoneNumber() {
		PhoneNumber phoneNumber = create_PhoneNumber(1);
		return phoneNumber;
	}

	public static PhoneNumber create_PhoneNumber(long index) {
		PhoneNumber phoneNumber = createEmpty_PhoneNumber();
		phoneNumber.setArea("dummyArea" + index);
		phoneNumber.setNumber("dummyNumber" + index);
		phoneNumber.setExtension("dummyExtension" + index);
		phoneNumber.setCountry(Country.USA);
		phoneNumber.setType(PhoneLocation.HOME);
		phoneNumber.setValue("dummyValue" + index);
		phoneNumber.setId(10L * index);
		return phoneNumber;
	}

	public static List<PhoneNumber> createEmptyList_PhoneNumber() {
		return new ArrayList<PhoneNumber>();
	}

	public static List<PhoneNumber> createList_PhoneNumber() {
		return createList_PhoneNumber(2);
	}

	public static List<PhoneNumber> createList_PhoneNumber(int size) {
		return createList_PhoneNumber(1, size);
	}
	
	public static List<PhoneNumber> createList_PhoneNumber(long index, int size) {
		List<PhoneNumber> phoneNumberList = createEmptyList_PhoneNumber();
		long limit = index + size;
		for (; index < limit; index++)
			phoneNumberList.add(create_PhoneNumber(index));
		return phoneNumberList;
	}

	public static Set<PhoneNumber> createEmptySet_PhoneNumber() {
		return new HashSet<PhoneNumber>();
	}
	
	public static Set<PhoneNumber> createSet_PhoneNumber() {
		return createSet_PhoneNumber(2);
	}
	
	public static Set<PhoneNumber> createSet_PhoneNumber(int size) {
		return createSet_PhoneNumber(1, size);
	}
	
	public static Set<PhoneNumber> createSet_PhoneNumber(long index, int size) {
		Set<PhoneNumber> phoneNumberSet = createEmptySet_PhoneNumber();
		long limit = index + size;
		for (; index < limit; index++)
			phoneNumberSet.add(create_PhoneNumber(index));
		return phoneNumberSet;
	}
	
	public static void assertPhoneNumberExists(Collection<PhoneNumber> phoneNumberList, PhoneNumber phoneNumber) {
		Assert.notNull(phoneNumberList, "PhoneNumber list must be specified");
		Assert.notNull(phoneNumber, "PhoneNumber record must be specified");
		Iterator<PhoneNumber> iterator = phoneNumberList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PhoneNumber phoneNumber1 = iterator.next();
			if (phoneNumber1.equals(phoneNumber)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PhoneNumber should exist: "+phoneNumber);
	}
	
	public static void assertPhoneNumberCorrect(PhoneNumber phoneNumber) {
		long index = phoneNumber.getId() / 10L;
		assertObjectCorrect("Area", phoneNumber.getArea(), index);
		assertObjectCorrect("Number", phoneNumber.getNumber(), index);
		assertObjectCorrect("Extension", phoneNumber.getExtension(), index);
		assertObjectCorrect("Country", phoneNumber.getCountry(), index);
		assertObjectCorrect("Type", phoneNumber.getType(), index);
		assertObjectCorrect("Value", phoneNumber.getValue(), index);
	}
	
	public static void assertPhoneNumberCorrect(Collection<PhoneNumber> phoneNumberList) {
		Assert.isTrue(phoneNumberList.size() == 2, "PhoneNumber count not correct");
		Iterator<PhoneNumber> iterator = phoneNumberList.iterator();
		while (iterator.hasNext()) {
			PhoneNumber phoneNumber = iterator.next();
			assertPhoneNumberCorrect(phoneNumber);
		}
	}
	
	public static void assertSamePhoneNumber(PhoneNumber phoneNumber1, PhoneNumber phoneNumber2) {
		assertSamePhoneNumber(phoneNumber1, phoneNumber2, false, "");
	}
	
	public static void assertSamePhoneNumber(PhoneNumber phoneNumber1, PhoneNumber phoneNumber2, String message) {
		assertSamePhoneNumber(phoneNumber1, phoneNumber2, false, message);
	}
	
	public static void assertSamePhoneNumber(PhoneNumber phoneNumber1, PhoneNumber phoneNumber2, boolean checkIds) {
		assertSamePhoneNumber(phoneNumber1, phoneNumber2, checkIds, "");
	}
	
	public static void assertSamePhoneNumber(PhoneNumber phoneNumber1, PhoneNumber phoneNumber2, boolean checkIds, String message) {
		assertObjectExists("PhoneNumber1", phoneNumber1);
		assertObjectExists("PhoneNumber2", phoneNumber2);
		if (checkIds)
			assertObjectEquals("Id", phoneNumber1.getId(), phoneNumber2.getId(), message);
		assertObjectEquals("Area", phoneNumber1.getArea(), phoneNumber2.getArea(), message);
		assertObjectEquals("Number", phoneNumber1.getNumber(), phoneNumber2.getNumber(), message);
		assertObjectEquals("Extension", phoneNumber1.getExtension(), phoneNumber2.getExtension(), message);
		assertObjectEquals("Country", phoneNumber1.getCountry(), phoneNumber2.getCountry(), message);
		assertObjectEquals("Type", phoneNumber1.getType(), phoneNumber2.getType(), message);
		assertObjectEquals("Value", phoneNumber1.getValue(), phoneNumber2.getValue(), message);
	}
	
	public static void assertSamePhoneNumber(Collection<PhoneNumber> phoneNumberList1, Collection<PhoneNumber> phoneNumberList2) {
		assertSamePhoneNumber(phoneNumberList1, phoneNumberList2, false, "");
	}
	
	public static void assertSamePhoneNumber(Collection<PhoneNumber> phoneNumberList1, Collection<PhoneNumber> phoneNumberList2, String message) {
		assertSamePhoneNumber(phoneNumberList1, phoneNumberList2, false, message);
	}
	
	public static void assertSamePhoneNumber(Collection<PhoneNumber> phoneNumberList1, Collection<PhoneNumber> phoneNumberList2, boolean checkIds) {
		assertSamePhoneNumber(phoneNumberList1, phoneNumberList2, checkIds, "");
	}
	
	public static void assertSamePhoneNumber(Collection<PhoneNumber> phoneNumberList1, Collection<PhoneNumber> phoneNumberList2, boolean checkIds, String message) {
		Assert.notNull(phoneNumberList1, "PhoneNumber list1 must be specified");
		Assert.notNull(phoneNumberList2, "PhoneNumber list2 must be specified");
		Assert.equals(phoneNumberList1.size(), phoneNumberList2.size(), "PhoneNumber count not equal");
		Collection<PhoneNumber> sortedRecords1 = PhoneNumberUtil.sortRecords(phoneNumberList1);
		Collection<PhoneNumber> sortedRecords2 = PhoneNumberUtil.sortRecords(phoneNumberList2);
		Iterator<PhoneNumber> list1Iterator = sortedRecords1.iterator();
		Iterator<PhoneNumber> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			PhoneNumber phoneNumber1 = list1Iterator.next();
			PhoneNumber phoneNumber2 = list2Iterator.next();
			assertSamePhoneNumber(phoneNumber1, phoneNumber2, checkIds, message);
		}
	}
	
	public static EmailAddress createEmpty_EmailAddress() {
		EmailAddress emailAddress = new EmailAddress();
		return emailAddress;
	}

	public static EmailAddress create_EmailAddress() {
		EmailAddress emailAddress = create_EmailAddress(1);
		return emailAddress;
	}

	public static EmailAddress create_EmailAddress(long index) {
		EmailAddress emailAddress = createEmpty_EmailAddress();
		emailAddress.setUrl("dummyUrl" + index);
		emailAddress.setUserId("dummyUserId" + index);
		emailAddress.setFirstName("dummyFirstName" + index);
		emailAddress.setLastName("dummyLastName" + index);
		emailAddress.setOrganization("dummyOrganization" + index);
		emailAddress.setCreationDate(new Date(1000L + index));
		emailAddress.setLastUpdate(new Date(1000L + index));
		emailAddress.setEnabled(true);
		emailAddress.setPhoneNumber(create_PhoneNumber(index));
		emailAddress.setId(10L * index);
		return emailAddress;
	}

	public static List<EmailAddress> createEmptyList_EmailAddress() {
		return new ArrayList<EmailAddress>();
	}

	public static List<EmailAddress> createList_EmailAddress() {
		return createList_EmailAddress(2);
	}

	public static List<EmailAddress> createList_EmailAddress(int size) {
		return createList_EmailAddress(1, size);
	}
	
	public static List<EmailAddress> createList_EmailAddress(long index, int size) {
		List<EmailAddress> emailAddressList = createEmptyList_EmailAddress();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressList.add(create_EmailAddress(index));
		return emailAddressList;
	}

	public static Set<EmailAddress> createEmptySet_EmailAddress() {
		return new HashSet<EmailAddress>();
	}
	
	public static Set<EmailAddress> createSet_EmailAddress() {
		return createSet_EmailAddress(2);
	}
	
	public static Set<EmailAddress> createSet_EmailAddress(int size) {
		return createSet_EmailAddress(1, size);
	}
	
	public static Set<EmailAddress> createSet_EmailAddress(long index, int size) {
		Set<EmailAddress> emailAddressSet = createEmptySet_EmailAddress();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressSet.add(create_EmailAddress(index));
		return emailAddressSet;
	}
	
	public static void assertEmailAddressExists(Collection<EmailAddress> emailAddressList, EmailAddress emailAddress) {
		Assert.notNull(emailAddressList, "EmailAddress list must be specified");
		Assert.notNull(emailAddress, "EmailAddress record must be specified");
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddress emailAddress1 = iterator.next();
			if (emailAddress1.equals(emailAddress)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAddress should exist: "+emailAddress);
	}
	
	public static void assertEmailAddressCorrect(EmailAddress emailAddress) {
		long index = emailAddress.getId() / 10L;
		assertObjectCorrect("Url", emailAddress.getUrl(), index);
		assertObjectCorrect("UserId", emailAddress.getUserId(), index);
		assertObjectCorrect("FirstName", emailAddress.getFirstName(), index);
		assertObjectCorrect("LastName", emailAddress.getLastName(), index);
		assertObjectCorrect("Organization", emailAddress.getOrganization(), index);
		assertObjectCorrect("CreationDate", emailAddress.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", emailAddress.getLastUpdate(), index);
		assertObjectCorrect("Enabled", emailAddress.getEnabled(), index);
		assertPhoneNumberCorrect(emailAddress.getPhoneNumber());
	}
	
	public static void assertEmailAddressCorrect(Collection<EmailAddress> emailAddressList) {
		Assert.isTrue(emailAddressList.size() == 2, "EmailAddress count not correct");
		Iterator<EmailAddress> iterator = emailAddressList.iterator();
		while (iterator.hasNext()) {
			EmailAddress emailAddress = iterator.next();
			assertEmailAddressCorrect(emailAddress);
		}
	}
	
	public static void assertSameEmailAddress(EmailAddress emailAddress1, EmailAddress emailAddress2) {
		assertSameEmailAddress(emailAddress1, emailAddress2, false, "");
	}
	
	public static void assertSameEmailAddress(EmailAddress emailAddress1, EmailAddress emailAddress2, String message) {
		assertSameEmailAddress(emailAddress1, emailAddress2, false, message);
	}
	
	public static void assertSameEmailAddress(EmailAddress emailAddress1, EmailAddress emailAddress2, boolean checkIds) {
		assertSameEmailAddress(emailAddress1, emailAddress2, checkIds, "");
	}
	
	public static void assertSameEmailAddress(EmailAddress emailAddress1, EmailAddress emailAddress2, boolean checkIds, String message) {
		assertObjectExists("EmailAddress1", emailAddress1);
		assertObjectExists("EmailAddress2", emailAddress2);
		if (checkIds)
			assertObjectEquals("Id", emailAddress1.getId(), emailAddress2.getId(), message);
		assertObjectEquals("Url", emailAddress1.getUrl(), emailAddress2.getUrl(), message);
		assertObjectEquals("UserId", emailAddress1.getUserId(), emailAddress2.getUserId(), message);
		assertObjectEquals("FirstName", emailAddress1.getFirstName(), emailAddress2.getFirstName(), message);
		assertObjectEquals("LastName", emailAddress1.getLastName(), emailAddress2.getLastName(), message);
		assertObjectEquals("Organization", emailAddress1.getOrganization(), emailAddress2.getOrganization(), message);
		assertObjectEquals("CreationDate", emailAddress1.getCreationDate(), emailAddress2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", emailAddress1.getLastUpdate(), emailAddress2.getLastUpdate(), message);
		assertObjectEquals("Enabled", emailAddress1.getEnabled(), emailAddress2.getEnabled(), message);
		assertSamePhoneNumber(emailAddress1.getPhoneNumber(), emailAddress2.getPhoneNumber(), message);
	}
	
	public static void assertSameEmailAddress(Collection<EmailAddress> emailAddressList1, Collection<EmailAddress> emailAddressList2) {
		assertSameEmailAddress(emailAddressList1, emailAddressList2, false, "");
	}
	
	public static void assertSameEmailAddress(Collection<EmailAddress> emailAddressList1, Collection<EmailAddress> emailAddressList2, String message) {
		assertSameEmailAddress(emailAddressList1, emailAddressList2, false, message);
	}
	
	public static void assertSameEmailAddress(Collection<EmailAddress> emailAddressList1, Collection<EmailAddress> emailAddressList2, boolean checkIds) {
		assertSameEmailAddress(emailAddressList1, emailAddressList2, checkIds, "");
	}
	
	public static void assertSameEmailAddress(Collection<EmailAddress> emailAddressList1, Collection<EmailAddress> emailAddressList2, boolean checkIds, String message) {
		Assert.notNull(emailAddressList1, "EmailAddress list1 must be specified");
		Assert.notNull(emailAddressList2, "EmailAddress list2 must be specified");
		Assert.equals(emailAddressList1.size(), emailAddressList2.size(), "EmailAddress count not equal");
		Collection<EmailAddress> sortedRecords1 = EmailAddressUtil.sortRecords(emailAddressList1);
		Collection<EmailAddress> sortedRecords2 = EmailAddressUtil.sortRecords(emailAddressList2);
		Iterator<EmailAddress> list1Iterator = sortedRecords1.iterator();
		Iterator<EmailAddress> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			EmailAddress emailAddress1 = list1Iterator.next();
			EmailAddress emailAddress2 = list2Iterator.next();
			assertSameEmailAddress(emailAddress1, emailAddress2, checkIds, message);
		}
	}
	
	public static EmailAddressList createEmpty_EmailAddressList() {
		EmailAddressList emailAddressList = new EmailAddressList();
		return emailAddressList;
	}

	public static EmailAddressList create_EmailAddressList() {
		EmailAddressList emailAddressList = create_EmailAddressList(1);
		return emailAddressList;
	}

	public static EmailAddressList create_EmailAddressList(long index) {
		EmailAddressList emailAddressList = createEmpty_EmailAddressList();
		emailAddressList.setName("dummyName" + index);
		emailAddressList.getAddresses().addAll(createList_EmailAddress(2));
		emailAddressList.setId(10L * index);
		return emailAddressList;
	}

	public static List<EmailAddressList> createEmptyList_EmailAddressList() {
		return new ArrayList<EmailAddressList>();
	}

	public static List<EmailAddressList> createList_EmailAddressList() {
		return createList_EmailAddressList(2);
	}

	public static List<EmailAddressList> createList_EmailAddressList(int size) {
		return createList_EmailAddressList(1, size);
	}
	
	public static List<EmailAddressList> createList_EmailAddressList(long index, int size) {
		List<EmailAddressList> emailAddressListList = createEmptyList_EmailAddressList();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressListList.add(create_EmailAddressList(index));
		return emailAddressListList;
	}

	public static Set<EmailAddressList> createEmptySet_EmailAddressList() {
		return new HashSet<EmailAddressList>();
	}
	
	public static Set<EmailAddressList> createSet_EmailAddressList() {
		return createSet_EmailAddressList(2);
	}
	
	public static Set<EmailAddressList> createSet_EmailAddressList(int size) {
		return createSet_EmailAddressList(1, size);
	}
	
	public static Set<EmailAddressList> createSet_EmailAddressList(long index, int size) {
		Set<EmailAddressList> emailAddressListSet = createEmptySet_EmailAddressList();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressListSet.add(create_EmailAddressList(index));
		return emailAddressListSet;
	}
	
	public static void assertEmailAddressListExists(Collection<EmailAddressList> emailAddressListList, EmailAddressList emailAddressList) {
		Assert.notNull(emailAddressListList, "EmailAddressList list must be specified");
		Assert.notNull(emailAddressList, "EmailAddressList record must be specified");
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddressList emailAddressList1 = iterator.next();
			if (emailAddressList1.equals(emailAddressList)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAddressList should exist: "+emailAddressList);
	}
	
	public static void assertEmailAddressListCorrect(EmailAddressList emailAddressList) {
		long index = emailAddressList.getId() / 10L;
		assertObjectCorrect("Name", emailAddressList.getName(), index);
		assertEmailAddressCorrect(emailAddressList.getAddresses());
	}
	
	public static void assertEmailAddressListCorrect(Collection<EmailAddressList> emailAddressListList) {
		Assert.isTrue(emailAddressListList.size() == 2, "EmailAddressList count not correct");
		Iterator<EmailAddressList> iterator = emailAddressListList.iterator();
		while (iterator.hasNext()) {
			EmailAddressList emailAddressList = iterator.next();
			assertEmailAddressListCorrect(emailAddressList);
		}
	}
	
	public static void assertSameEmailAddressList(EmailAddressList emailAddressList1, EmailAddressList emailAddressList2) {
		assertSameEmailAddressList(emailAddressList1, emailAddressList2, false, "");
	}
	
	public static void assertSameEmailAddressList(EmailAddressList emailAddressList1, EmailAddressList emailAddressList2, String message) {
		assertSameEmailAddressList(emailAddressList1, emailAddressList2, false, message);
	}
	
	public static void assertSameEmailAddressList(EmailAddressList emailAddressList1, EmailAddressList emailAddressList2, boolean checkIds) {
		assertSameEmailAddressList(emailAddressList1, emailAddressList2, checkIds, "");
	}
	
	public static void assertSameEmailAddressList(EmailAddressList emailAddressList1, EmailAddressList emailAddressList2, boolean checkIds, String message) {
		assertObjectExists("EmailAddressList1", emailAddressList1);
		assertObjectExists("EmailAddressList2", emailAddressList2);
		if (checkIds)
			assertObjectEquals("Id", emailAddressList1.getId(), emailAddressList2.getId(), message);
		assertObjectEquals("Name", emailAddressList1.getName(), emailAddressList2.getName(), message);
		assertSameEmailAddress(emailAddressList1.getAddresses(), emailAddressList2.getAddresses(), message);
	}
	
	public static void assertSameEmailAddressList(Collection<EmailAddressList> emailAddressListList1, Collection<EmailAddressList> emailAddressListList2) {
		assertSameEmailAddressList(emailAddressListList1, emailAddressListList2, false, "");
	}
	
	public static void assertSameEmailAddressList(Collection<EmailAddressList> emailAddressListList1, Collection<EmailAddressList> emailAddressListList2, String message) {
		assertSameEmailAddressList(emailAddressListList1, emailAddressListList2, false, message);
	}
	
	public static void assertSameEmailAddressList(Collection<EmailAddressList> emailAddressListList1, Collection<EmailAddressList> emailAddressListList2, boolean checkIds) {
		assertSameEmailAddressList(emailAddressListList1, emailAddressListList2, checkIds, "");
	}
	
	public static void assertSameEmailAddressList(Collection<EmailAddressList> emailAddressListList1, Collection<EmailAddressList> emailAddressListList2, boolean checkIds, String message) {
		Assert.notNull(emailAddressListList1, "EmailAddressList list1 must be specified");
		Assert.notNull(emailAddressListList2, "EmailAddressList list2 must be specified");
		Assert.equals(emailAddressListList1.size(), emailAddressListList2.size(), "EmailAddressList count not equal");
		Collection<EmailAddressList> sortedRecords1 = EmailAddressListUtil.sortRecords(emailAddressListList1);
		Collection<EmailAddressList> sortedRecords2 = EmailAddressListUtil.sortRecords(emailAddressListList2);
		Iterator<EmailAddressList> list1Iterator = sortedRecords1.iterator();
		Iterator<EmailAddressList> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			EmailAddressList emailAddressList1 = list1Iterator.next();
			EmailAddressList emailAddressList2 = list2Iterator.next();
			assertSameEmailAddressList(emailAddressList1, emailAddressList2, checkIds, message);
		}
	}
	
	public static EmailMessage createEmpty_EmailMessage() {
		EmailMessage emailMessage = new EmailMessage();
		return emailMessage;
	}

	public static EmailMessage create_EmailMessage() {
		EmailMessage emailMessage = create_EmailMessage(1);
		return emailMessage;
	}

	public static EmailMessage create_EmailMessage(long index) {
		EmailMessage emailMessage = createEmpty_EmailMessage();
		emailMessage.setContent("dummyContent" + index);
		emailMessage.setSubject("dummySubject" + index);
		emailMessage.setTimestamp(new Date(1000L + index));
		emailMessage.setSourceId("dummySourceId" + index);
		emailMessage.setSmtpHost("dummySmtpHost" + index);
		emailMessage.setSmtpPort("dummySmtpPort" + index);
		emailMessage.setSendAsHtml(false);
		emailMessage.setFromAddress(create_EmailAddress(index));
		emailMessage.getToAddresses().addAll(createList_EmailAddressList(2));
		emailMessage.getBccAddresses().addAll(createList_EmailAddressList(2));
		emailMessage.getCcAddresses().addAll(createList_EmailAddressList(2));
		emailMessage.getReplytoAddresses().addAll(createList_EmailAddressList(2));
		emailMessage.getAdminAddresses().addAll(createList_EmailAddressList(2));
		emailMessage.getAttachments().addAll(createList_Attachment(2));
		emailMessage.setId(10L * index);
		return emailMessage;
	}

	public static List<EmailMessage> createEmptyList_EmailMessage() {
		return new ArrayList<EmailMessage>();
	}

	public static List<EmailMessage> createList_EmailMessage() {
		return createList_EmailMessage(2);
	}

	public static List<EmailMessage> createList_EmailMessage(int size) {
		return createList_EmailMessage(1, size);
	}
	
	public static List<EmailMessage> createList_EmailMessage(long index, int size) {
		List<EmailMessage> emailMessageList = createEmptyList_EmailMessage();
		long limit = index + size;
		for (; index < limit; index++)
			emailMessageList.add(create_EmailMessage(index));
		return emailMessageList;
	}

	public static Set<EmailMessage> createEmptySet_EmailMessage() {
		return new HashSet<EmailMessage>();
	}
	
	public static Set<EmailMessage> createSet_EmailMessage() {
		return createSet_EmailMessage(2);
	}
	
	public static Set<EmailMessage> createSet_EmailMessage(int size) {
		return createSet_EmailMessage(1, size);
	}
	
	public static Set<EmailMessage> createSet_EmailMessage(long index, int size) {
		Set<EmailMessage> emailMessageSet = createEmptySet_EmailMessage();
		long limit = index + size;
		for (; index < limit; index++)
			emailMessageSet.add(create_EmailMessage(index));
		return emailMessageSet;
	}
	
	public static void assertEmailMessageExists(Collection<EmailMessage> emailMessageList, EmailMessage emailMessage) {
		Assert.notNull(emailMessageList, "EmailMessage list must be specified");
		Assert.notNull(emailMessage, "EmailMessage record must be specified");
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailMessage emailMessage1 = iterator.next();
			if (emailMessage1.equals(emailMessage)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailMessage should exist: "+emailMessage);
	}
	
	public static void assertEmailMessageCorrect(EmailMessage emailMessage) {
		long index = emailMessage.getId() / 10L;
		assertObjectCorrect("Content", emailMessage.getContent(), index);
		assertObjectCorrect("Subject", emailMessage.getSubject(), index);
		assertObjectCorrect("Timestamp", emailMessage.getTimestamp(), index);
		assertObjectCorrect("SourceId", emailMessage.getSourceId(), index);
		assertObjectCorrect("SmtpHost", emailMessage.getSmtpHost(), index);
		assertObjectCorrect("SmtpPort", emailMessage.getSmtpPort(), index);
		assertObjectCorrect("SendAsHtml", emailMessage.getSendAsHtml(), index);
		assertEmailAddressCorrect(emailMessage.getFromAddress());
		assertEmailAddressListCorrect(emailMessage.getToAddresses());
		assertEmailAddressListCorrect(emailMessage.getBccAddresses());
		assertEmailAddressListCorrect(emailMessage.getCcAddresses());
		assertEmailAddressListCorrect(emailMessage.getReplytoAddresses());
		assertEmailAddressListCorrect(emailMessage.getAdminAddresses());
		assertAttachmentCorrect(emailMessage.getAttachments());
	}
	
	public static void assertEmailMessageCorrect(Collection<EmailMessage> emailMessageList) {
		Assert.isTrue(emailMessageList.size() == 2, "EmailMessage count not correct");
		Iterator<EmailMessage> iterator = emailMessageList.iterator();
		while (iterator.hasNext()) {
			EmailMessage emailMessage = iterator.next();
			assertEmailMessageCorrect(emailMessage);
		}
	}
	
	public static void assertSameEmailMessage(EmailMessage emailMessage1, EmailMessage emailMessage2) {
		assertSameEmailMessage(emailMessage1, emailMessage2, false, "");
	}
	
	public static void assertSameEmailMessage(EmailMessage emailMessage1, EmailMessage emailMessage2, String message) {
		assertSameEmailMessage(emailMessage1, emailMessage2, false, message);
	}
	
	public static void assertSameEmailMessage(EmailMessage emailMessage1, EmailMessage emailMessage2, boolean checkIds) {
		assertSameEmailMessage(emailMessage1, emailMessage2, checkIds, "");
	}
	
	public static void assertSameEmailMessage(EmailMessage emailMessage1, EmailMessage emailMessage2, boolean checkIds, String message) {
		assertObjectExists("EmailMessage1", emailMessage1);
		assertObjectExists("EmailMessage2", emailMessage2);
		if (checkIds)
			assertObjectEquals("Id", emailMessage1.getId(), emailMessage2.getId(), message);
		assertObjectEquals("Content", emailMessage1.getContent(), emailMessage2.getContent(), message);
		assertObjectEquals("Subject", emailMessage1.getSubject(), emailMessage2.getSubject(), message);
		assertObjectEquals("Timestamp", emailMessage1.getTimestamp(), emailMessage2.getTimestamp(), message);
		assertObjectEquals("SourceId", emailMessage1.getSourceId(), emailMessage2.getSourceId(), message);
		assertObjectEquals("SmtpHost", emailMessage1.getSmtpHost(), emailMessage2.getSmtpHost(), message);
		assertObjectEquals("SmtpPort", emailMessage1.getSmtpPort(), emailMessage2.getSmtpPort(), message);
		assertObjectEquals("SendAsHtml", emailMessage1.getSendAsHtml(), emailMessage2.getSendAsHtml(), message);
		assertSameEmailAddress(emailMessage1.getFromAddress(), emailMessage2.getFromAddress(), message);
		assertSameEmailAddressList(emailMessage1.getToAddresses(), emailMessage2.getToAddresses(), message);
		assertSameEmailAddressList(emailMessage1.getBccAddresses(), emailMessage2.getBccAddresses(), message);
		assertSameEmailAddressList(emailMessage1.getCcAddresses(), emailMessage2.getCcAddresses(), message);
		assertSameEmailAddressList(emailMessage1.getReplytoAddresses(), emailMessage2.getReplytoAddresses(), message);
		assertSameEmailAddressList(emailMessage1.getAdminAddresses(), emailMessage2.getAdminAddresses(), message);
		assertSameAttachment(emailMessage1.getAttachments(), emailMessage2.getAttachments(), message);
	}
	
	public static void assertSameEmailMessage(Collection<EmailMessage> emailMessageList1, Collection<EmailMessage> emailMessageList2) {
		assertSameEmailMessage(emailMessageList1, emailMessageList2, false, "");
	}
	
	public static void assertSameEmailMessage(Collection<EmailMessage> emailMessageList1, Collection<EmailMessage> emailMessageList2, String message) {
		assertSameEmailMessage(emailMessageList1, emailMessageList2, false, message);
	}
	
	public static void assertSameEmailMessage(Collection<EmailMessage> emailMessageList1, Collection<EmailMessage> emailMessageList2, boolean checkIds) {
		assertSameEmailMessage(emailMessageList1, emailMessageList2, checkIds, "");
	}
	
	public static void assertSameEmailMessage(Collection<EmailMessage> emailMessageList1, Collection<EmailMessage> emailMessageList2, boolean checkIds, String message) {
		Assert.notNull(emailMessageList1, "EmailMessage list1 must be specified");
		Assert.notNull(emailMessageList2, "EmailMessage list2 must be specified");
		Assert.equals(emailMessageList1.size(), emailMessageList2.size(), "EmailMessage count not equal");
		Collection<EmailMessage> sortedRecords1 = EmailMessageUtil.sortRecords(emailMessageList1);
		Collection<EmailMessage> sortedRecords2 = EmailMessageUtil.sortRecords(emailMessageList2);
		Iterator<EmailMessage> list1Iterator = sortedRecords1.iterator();
		Iterator<EmailMessage> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			EmailMessage emailMessage1 = list1Iterator.next();
			EmailMessage emailMessage2 = list2Iterator.next();
			assertSameEmailMessage(emailMessage1, emailMessage2, checkIds, message);
		}
	}
	
	public static EmailAccount createEmpty_EmailAccount() {
		EmailAccount emailAccount = new EmailAccount();
		return emailAccount;
	}

	public static EmailAccount create_EmailAccount() {
		EmailAccount emailAccount = create_EmailAccount(1);
		return emailAccount;
	}

	public static EmailAccount create_EmailAccount(long index) {
		EmailAccount emailAccount = createEmpty_EmailAccount();
		emailAccount.setUserId("dummyUserId" + index);
		emailAccount.setPasswordHash("dummyPasswordHash" + index);
		emailAccount.setPasswordSalt("dummyPasswordSalt" + index);
		emailAccount.setFirstName("dummyFirstName" + index);
		emailAccount.setLastName("dummyLastName" + index);
		emailAccount.setEnabled(false);
		emailAccount.getEmailBoxes().addAll(createList_EmailBox(emailAccount, 2));
		emailAccount.setId(10L * index);
		return emailAccount;
	}

	public static List<EmailAccount> createEmptyList_EmailAccount() {
		return new ArrayList<EmailAccount>();
	}

	public static List<EmailAccount> createList_EmailAccount() {
		return createList_EmailAccount(2);
	}

	public static List<EmailAccount> createList_EmailAccount(int size) {
		return createList_EmailAccount(1, size);
	}
	
	public static List<EmailAccount> createList_EmailAccount(long index, int size) {
		List<EmailAccount> emailAccountList = createEmptyList_EmailAccount();
		long limit = index + size;
		for (; index < limit; index++)
			emailAccountList.add(create_EmailAccount(index));
		return emailAccountList;
	}

	public static Set<EmailAccount> createEmptySet_EmailAccount() {
		return new HashSet<EmailAccount>();
	}
	
	public static Set<EmailAccount> createSet_EmailAccount() {
		return createSet_EmailAccount(2);
	}
	
	public static Set<EmailAccount> createSet_EmailAccount(int size) {
		return createSet_EmailAccount(1, size);
	}
	
	public static Set<EmailAccount> createSet_EmailAccount(long index, int size) {
		Set<EmailAccount> emailAccountSet = createEmptySet_EmailAccount();
		long limit = index + size;
		for (; index < limit; index++)
			emailAccountSet.add(create_EmailAccount(index));
		return emailAccountSet;
	}
	
	public static void assertEmailAccountExists(Collection<EmailAccount> emailAccountList, EmailAccount emailAccount) {
		Assert.notNull(emailAccountList, "EmailAccount list must be specified");
		Assert.notNull(emailAccount, "EmailAccount record must be specified");
		Iterator<EmailAccount> iterator = emailAccountList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAccount emailAccount1 = iterator.next();
			if (emailAccount1.equals(emailAccount)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAccount should exist: "+emailAccount);
	}
	
	public static void assertEmailAccountCorrect(EmailAccount emailAccount) {
		long index = emailAccount.getId() / 10L;
		assertObjectCorrect("UserId", emailAccount.getUserId(), index);
		assertObjectCorrect("PasswordHash", emailAccount.getPasswordHash(), index);
		assertObjectCorrect("PasswordSalt", emailAccount.getPasswordSalt(), index);
		assertObjectCorrect("FirstName", emailAccount.getFirstName(), index);
		assertObjectCorrect("LastName", emailAccount.getLastName(), index);
		assertObjectCorrect("Enabled", emailAccount.getEnabled(), index);
		assertEmailBoxCorrect(emailAccount.getEmailBoxes());
	}
	
	public static void assertEmailAccountCorrect(Collection<EmailAccount> emailAccountList) {
		Assert.isTrue(emailAccountList.size() == 2, "EmailAccount count not correct");
		Iterator<EmailAccount> iterator = emailAccountList.iterator();
		while (iterator.hasNext()) {
			EmailAccount emailAccount = iterator.next();
			assertEmailAccountCorrect(emailAccount);
		}
	}
	
	public static void assertSameEmailAccount(EmailAccount emailAccount1, EmailAccount emailAccount2) {
		assertSameEmailAccount(emailAccount1, emailAccount2, false, "");
	}
	
	public static void assertSameEmailAccount(EmailAccount emailAccount1, EmailAccount emailAccount2, String message) {
		assertSameEmailAccount(emailAccount1, emailAccount2, false, message);
	}
	
	public static void assertSameEmailAccount(EmailAccount emailAccount1, EmailAccount emailAccount2, boolean checkIds) {
		assertSameEmailAccount(emailAccount1, emailAccount2, checkIds, "");
	}
	
	public static void assertSameEmailAccount(EmailAccount emailAccount1, EmailAccount emailAccount2, boolean checkIds, String message) {
		assertObjectExists("EmailAccount1", emailAccount1);
		assertObjectExists("EmailAccount2", emailAccount2);
		if (checkIds)
			assertObjectEquals("Id", emailAccount1.getId(), emailAccount2.getId(), message);
		assertObjectEquals("UserId", emailAccount1.getUserId(), emailAccount2.getUserId(), message);
		assertObjectEquals("PasswordHash", emailAccount1.getPasswordHash(), emailAccount2.getPasswordHash(), message);
		assertObjectEquals("PasswordSalt", emailAccount1.getPasswordSalt(), emailAccount2.getPasswordSalt(), message);
		assertObjectEquals("FirstName", emailAccount1.getFirstName(), emailAccount2.getFirstName(), message);
		assertObjectEquals("LastName", emailAccount1.getLastName(), emailAccount2.getLastName(), message);
		assertObjectEquals("Enabled", emailAccount1.getEnabled(), emailAccount2.getEnabled(), message);
		assertSameEmailBox(emailAccount1.getEmailBoxes(), emailAccount2.getEmailBoxes(), message);
	}
	
	public static void assertSameEmailAccount(Collection<EmailAccount> emailAccountList1, Collection<EmailAccount> emailAccountList2) {
		assertSameEmailAccount(emailAccountList1, emailAccountList2, false, "");
	}
	
	public static void assertSameEmailAccount(Collection<EmailAccount> emailAccountList1, Collection<EmailAccount> emailAccountList2, String message) {
		assertSameEmailAccount(emailAccountList1, emailAccountList2, false, message);
	}
	
	public static void assertSameEmailAccount(Collection<EmailAccount> emailAccountList1, Collection<EmailAccount> emailAccountList2, boolean checkIds) {
		assertSameEmailAccount(emailAccountList1, emailAccountList2, checkIds, "");
	}
	
	public static void assertSameEmailAccount(Collection<EmailAccount> emailAccountList1, Collection<EmailAccount> emailAccountList2, boolean checkIds, String message) {
		Assert.notNull(emailAccountList1, "EmailAccount list1 must be specified");
		Assert.notNull(emailAccountList2, "EmailAccount list2 must be specified");
		Assert.equals(emailAccountList1.size(), emailAccountList2.size(), "EmailAccount count not equal");
		Collection<EmailAccount> sortedRecords1 = EmailAccountUtil.sortRecords(emailAccountList1);
		Collection<EmailAccount> sortedRecords2 = EmailAccountUtil.sortRecords(emailAccountList2);
		Iterator<EmailAccount> list1Iterator = sortedRecords1.iterator();
		Iterator<EmailAccount> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			EmailAccount emailAccount1 = list1Iterator.next();
			EmailAccount emailAccount2 = list2Iterator.next();
			assertSameEmailAccount(emailAccount1, emailAccount2, checkIds, message);
		}
	}
	
	public static EmailBox createEmpty_EmailBox() {
		EmailBox emailBox = new EmailBox();
		return emailBox;
	}

	public static EmailBox create_EmailBox(EmailAccount emailAccount) {
		EmailBox emailBox = create_EmailBox(emailAccount, 1);
		return emailBox;
	}

	public static EmailBox create_EmailBox(EmailAccount emailAccount, long index) {
		EmailBox emailBox = createEmpty_EmailBox();
		emailBox.setType("dummyType" + index);
		emailBox.setName("dummyName" + index);
		emailBox.setCreationDate(new Date(1000L + index));
		emailBox.setLastUpdate(new Date(1000L + index));
		emailBox.setParentBox(createEmpty_EmailBox());
		emailBox.getMessages().addAll(createList_EmailMessage(2));
		emailBox.setId(10L * index);
		return emailBox;
	}

	public static List<EmailBox> createEmptyList_EmailBox() {
		return new ArrayList<EmailBox>();
	}

	public static List<EmailBox> createList_EmailBox(EmailAccount emailAccount) {
		return createList_EmailBox(emailAccount, 2);
	}

	public static List<EmailBox> createList_EmailBox(EmailAccount emailAccount, int size) {
		return createList_EmailBox(emailAccount, 1, size);
	}
	
	public static List<EmailBox> createList_EmailBox(EmailAccount emailAccount, long index, int size) {
		List<EmailBox> emailBoxList = createEmptyList_EmailBox();
		long limit = index + size;
		for (; index < limit; index++)
			emailBoxList.add(create_EmailBox(emailAccount, index));
		return emailBoxList;
	}
	
	public static Set<EmailBox> createEmptySet_EmailBox() {
		return new HashSet<EmailBox>();
	}
	
	public static Set<EmailBox> createSet_EmailBox(EmailAccount emailAccount) {
		return createSet_EmailBox(emailAccount, 2);
	}
	
	public static Set<EmailBox> createSet_EmailBox(EmailAccount emailAccount, int size) {
		return createSet_EmailBox(emailAccount, 1, size);
	}
	
	public static Set<EmailBox> createSet_EmailBox(EmailAccount emailAccount, long index, int size) {
		Set<EmailBox> emailBoxSet = createEmptySet_EmailBox();
		long limit = index + size;
		for (; index < limit; index++)
			emailBoxSet.add(create_EmailBox(emailAccount, index));
		return emailBoxSet;
	}
	
	public static void assertEmailBoxExists(Collection<EmailBox> emailBoxList, EmailBox emailBox) {
		Assert.notNull(emailBoxList, "EmailBox list must be specified");
		Assert.notNull(emailBox, "EmailBox record must be specified");
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailBox emailBox1 = iterator.next();
			if (emailBox1.equals(emailBox)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailBox should exist: "+emailBox);
	}
	
	public static void assertEmailBoxCorrect(EmailBox emailBox) {
		long index = emailBox.getId() / 10L;
		assertObjectCorrect("Type", emailBox.getType(), index);
		assertObjectCorrect("Name", emailBox.getName(), index);
		assertObjectCorrect("CreationDate", emailBox.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", emailBox.getLastUpdate(), index);
		assertEmailAccountCorrect(emailBox.getEmailAccount());
		assertEmailBoxCorrect(emailBox.getParentBox());
		assertEmailMessageCorrect(emailBox.getMessages());
	}
	
	public static void assertEmailBoxCorrect(Collection<EmailBox> emailBoxList) {
		Assert.isTrue(emailBoxList.size() == 2, "EmailBox count not correct");
		Iterator<EmailBox> iterator = emailBoxList.iterator();
		while (iterator.hasNext()) {
			EmailBox emailBox = iterator.next();
			assertEmailBoxCorrect(emailBox);
		}
	}
	
	public static void assertSameEmailBox(EmailBox emailBox1, EmailBox emailBox2) {
		assertSameEmailBox(emailBox1, emailBox2, false, "");
	}
	
	public static void assertSameEmailBox(EmailBox emailBox1, EmailBox emailBox2, String message) {
		assertSameEmailBox(emailBox1, emailBox2, false, message);
	}
	
	public static void assertSameEmailBox(EmailBox emailBox1, EmailBox emailBox2, boolean checkIds) {
		assertSameEmailBox(emailBox1, emailBox2, checkIds, "");
	}
	
	public static void assertSameEmailBox(EmailBox emailBox1, EmailBox emailBox2, boolean checkIds, String message) {
		assertObjectExists("EmailBox1", emailBox1);
		assertObjectExists("EmailBox2", emailBox2);
		if (checkIds)
			assertObjectEquals("Id", emailBox1.getId(), emailBox2.getId(), message);
		assertObjectEquals("Type", emailBox1.getType(), emailBox2.getType(), message);
		assertObjectEquals("Name", emailBox1.getName(), emailBox2.getName(), message);
		assertObjectEquals("CreationDate", emailBox1.getCreationDate(), emailBox2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", emailBox1.getLastUpdate(), emailBox2.getLastUpdate(), message);
		assertSameEmailAccount(emailBox1.getEmailAccount(), emailBox2.getEmailAccount(), message);
		assertSameEmailBox(emailBox1.getParentBox(), emailBox2.getParentBox(), message);
		assertSameEmailMessage(emailBox1.getMessages(), emailBox2.getMessages(), message);
	}
	
	public static void assertSameEmailBox(Collection<EmailBox> emailBoxList1, Collection<EmailBox> emailBoxList2) {
		assertSameEmailBox(emailBoxList1, emailBoxList2, false, "");
	}
	
	public static void assertSameEmailBox(Collection<EmailBox> emailBoxList1, Collection<EmailBox> emailBoxList2, String message) {
		assertSameEmailBox(emailBoxList1, emailBoxList2, false, message);
	}
	
	public static void assertSameEmailBox(Collection<EmailBox> emailBoxList1, Collection<EmailBox> emailBoxList2, boolean checkIds) {
		assertSameEmailBox(emailBoxList1, emailBoxList2, checkIds, "");
	}
	
	public static void assertSameEmailBox(Collection<EmailBox> emailBoxList1, Collection<EmailBox> emailBoxList2, boolean checkIds, String message) {
		Assert.notNull(emailBoxList1, "EmailBox list1 must be specified");
		Assert.notNull(emailBoxList2, "EmailBox list2 must be specified");
		Assert.equals(emailBoxList1.size(), emailBoxList2.size(), "EmailBox count not equal");
		Collection<EmailBox> sortedRecords1 = EmailBoxUtil.sortRecords(emailBoxList1);
		Collection<EmailBox> sortedRecords2 = EmailBoxUtil.sortRecords(emailBoxList2);
		Iterator<EmailBox> list1Iterator = sortedRecords1.iterator();
		Iterator<EmailBox> list2Iterator = sortedRecords2.iterator();
		while (list1Iterator.hasNext() && list2Iterator.hasNext()) {
			EmailBox emailBox1 = list1Iterator.next();
			EmailBox emailBox2 = list2Iterator.next();
			assertSameEmailBox(emailBox1, emailBox2, checkIds, message);
		}
	}
	
}

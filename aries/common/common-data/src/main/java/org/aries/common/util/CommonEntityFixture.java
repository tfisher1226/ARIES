package org.aries.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aries.Assert;
import org.aries.common.Country;
import org.aries.common.PhoneLocation;
import org.aries.common.State;
import org.aries.common.entity.AttachmentEntity;
import org.aries.common.entity.EmailAccountEntity;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.EmailAddressListEntity;
import org.aries.common.entity.EmailBoxEntity;
import org.aries.common.entity.EmailMessageEntity;
import org.aries.common.entity.PersonEntity;
import org.aries.common.entity.PersonNameEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.common.entity.PropertyEntity;
import org.aries.common.entity.StreetAddressEntity;
import org.aries.common.entity.ZipCodeEntity;
import org.aries.util.BaseFixture;


public class CommonEntityFixture extends BaseFixture {

	private static int counter = 0;

	
	public static PropertyEntity createEmpty_PropertyEntity() {
		PropertyEntity propertyEntity = new PropertyEntity();
		return propertyEntity;
	}
	
	public static PropertyEntity create_PropertyEntity() {
		PropertyEntity propertyEntity = create_PropertyEntity(1);
		return propertyEntity;
	}
	
	public static PropertyEntity create_PropertyEntity(long index) {
		PropertyEntity propertyEntity = createEmpty_PropertyEntity();
		propertyEntity.setName("dummyName" + index);
		propertyEntity.setValue("dummyValue" + index);
		propertyEntity.setId(10L * index);
		return propertyEntity;
	}
	
	public static List<PropertyEntity> createEmptyList_PropertyEntity() {
		return new ArrayList<PropertyEntity>();
	}
	
	public static List<PropertyEntity> createList_PropertyEntity() {
		return createList_PropertyEntity(2);
	}
	
	public static List<PropertyEntity> createList_PropertyEntity(int size) {
		return createList_PropertyEntity(1, size);
	}
	
	public static List<PropertyEntity> createList_PropertyEntity(long index, int size) {
		List<PropertyEntity> propertyEntityList = createEmptyList_PropertyEntity();
		long limit = index + size;
		for (; index < limit; index++)
			propertyEntityList.add(create_PropertyEntity(index));
		return propertyEntityList;
	}
	
	public static Set<PropertyEntity> createEmptySet_PropertyEntity() {
		return new HashSet<PropertyEntity>();
	}
	
	public static Set<PropertyEntity> createSet_PropertyEntity() {
		return createSet_PropertyEntity(2);
	}
	
	public static Set<PropertyEntity> createSet_PropertyEntity(int size) {
		return createSet_PropertyEntity(1, size);
	}
	
	public static Set<PropertyEntity> createSet_PropertyEntity(long index, int size) {
		Set<PropertyEntity> propertyEntitySet = createEmptySet_PropertyEntity();
		long limit = index + size;
		for (; index < limit; index++)
			propertyEntitySet.add(create_PropertyEntity(index));
		return propertyEntitySet;
	}
	
	public static void assertPropertyEntityExists(Collection<PropertyEntity> propertyEntityList, PropertyEntity propertyEntity) {
		Assert.notNull(propertyEntityList, "PropertyEntity list must be specified");
		Assert.notNull(propertyEntity, "PropertyEntity record must be specified");
		Iterator<PropertyEntity> iterator = propertyEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PropertyEntity propertyEntity1 = iterator.next();
			if (propertyEntity1.equals(propertyEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PropertyEntity should exist: "+propertyEntity);
	}
	
	public static void assertPropertyEntityCorrect(PropertyEntity propertyEntity) {
		long index = propertyEntity.getId() / 10L;
		assertObjectCorrect("Name", propertyEntity.getName(), index);
		assertObjectCorrect("Value", propertyEntity.getValue(), index);
	}
	
	public static void assertPropertyEntityCorrect(Collection<PropertyEntity> propertyEntityList) {
		Assert.isTrue(propertyEntityList.size() == 2, "PropertyEntity count not correct");
		Iterator<PropertyEntity> iterator = propertyEntityList.iterator();
		while (iterator.hasNext()) {
			PropertyEntity propertyEntity = iterator.next();
			assertPropertyEntityCorrect(propertyEntity);
		}
	}
	
	public static void assertSamePropertyEntity(PropertyEntity propertyEntity1, PropertyEntity propertyEntity2) {
		assertSamePropertyEntity(propertyEntity1, propertyEntity2, false, "");
	}
	
	public static void assertSamePropertyEntity(PropertyEntity propertyEntity1, PropertyEntity propertyEntity2, String message) {
		assertSamePropertyEntity(propertyEntity1, propertyEntity2, false, message);
	}
	
	public static void assertSamePropertyEntity(PropertyEntity propertyEntity1, PropertyEntity propertyEntity2, boolean checkIds) {
		assertSamePropertyEntity(propertyEntity1, propertyEntity2, checkIds, "");
	}
	
	public static void assertSamePropertyEntity(PropertyEntity propertyEntity1, PropertyEntity propertyEntity2, boolean checkIds, String message) {
		assertObjectExists("PropertyEntity1", propertyEntity1);
		assertObjectExists("PropertyEntity2", propertyEntity2);
		if (checkIds)
			assertObjectEquals("Id", propertyEntity1.getId(), propertyEntity2.getId(), message);
		assertObjectEquals("Name", propertyEntity1.getName(), propertyEntity2.getName(), message);
		assertObjectEquals("Value", propertyEntity1.getValue(), propertyEntity2.getValue(), message);
	}
	
	public static void assertSamePropertyEntity(Collection<PropertyEntity> propertyEntityList1, Collection<PropertyEntity> propertyEntityList2) {
		assertSamePropertyEntity(propertyEntityList1, propertyEntityList2, false, "");
	}
	
	public static void assertSamePropertyEntity(Collection<PropertyEntity> propertyEntityList1, Collection<PropertyEntity> propertyEntityList2, String message) {
		assertSamePropertyEntity(propertyEntityList1, propertyEntityList2, "");
	}
	
	public static void assertSamePropertyEntity(Collection<PropertyEntity> propertyEntityList1, Collection<PropertyEntity> propertyEntityList2, boolean checkIds) {
		assertSamePropertyEntity(propertyEntityList1, propertyEntityList2, checkIds, "");
	}
	
	public static void assertSamePropertyEntity(Collection<PropertyEntity> propertyEntityList1, Collection<PropertyEntity> propertyEntityList2, boolean checkIds, String message) {
		Assert.notNull(propertyEntityList1, "PropertyEntity list1 must be specified");
		Assert.notNull(propertyEntityList2, "PropertyEntity list2 must be specified");
		Assert.equals(propertyEntityList1.size(), propertyEntityList2.size(), "PropertyEntity count not equal");
		Iterator<PropertyEntity> iterator1 = propertyEntityList1.iterator();
		while (iterator1.hasNext()) {
			PropertyEntity propertyEntity1 = iterator1.next();
			Iterator<PropertyEntity> iterator2 = propertyEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				PropertyEntity propertyEntity2 = iterator2.next();
				if (propertyEntity1.getId().equals(propertyEntity2.getId())) {
					assertSamePropertyEntity(propertyEntity1, propertyEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Property record not found: "+propertyEntity1);
		}
	}
	
	public static AttachmentEntity createEmpty_AttachmentEntity() {
		AttachmentEntity attachmentEntity = new AttachmentEntity();
		return attachmentEntity;
	}
	
	public static AttachmentEntity create_AttachmentEntity() {
		AttachmentEntity attachmentEntity = create_AttachmentEntity(1);
		return attachmentEntity;
	}
	
	public static AttachmentEntity create_AttachmentEntity(long index) {
		AttachmentEntity attachmentEntity = createEmpty_AttachmentEntity();
		attachmentEntity.setName("dummyName" + index);
		attachmentEntity.setSize(1L + (long) index);
		attachmentEntity.setFileName("dummyFileName" + index);
		attachmentEntity.setFileData(new String("dummyFileData" + index).getBytes());
		attachmentEntity.setContentType("dummyContentType" + index);
		attachmentEntity.setId(10L * index);
		return attachmentEntity;
	}
	
	public static List<AttachmentEntity> createEmptyList_AttachmentEntity() {
		return new ArrayList<AttachmentEntity>();
	}
	
	public static List<AttachmentEntity> createList_AttachmentEntity() {
		return createList_AttachmentEntity(2);
	}
	
	public static List<AttachmentEntity> createList_AttachmentEntity(int size) {
		return createList_AttachmentEntity(1, size);
	}
	
	public static List<AttachmentEntity> createList_AttachmentEntity(long index, int size) {
		List<AttachmentEntity> attachmentEntityList = createEmptyList_AttachmentEntity();
		long limit = index + size;
		for (; index < limit; index++)
			attachmentEntityList.add(create_AttachmentEntity(index));
		return attachmentEntityList;
	}
	
	public static Set<AttachmentEntity> createEmptySet_AttachmentEntity() {
		return new HashSet<AttachmentEntity>();
	}
	
	public static Set<AttachmentEntity> createSet_AttachmentEntity() {
		return createSet_AttachmentEntity(2);
	}
	
	public static Set<AttachmentEntity> createSet_AttachmentEntity(int size) {
		return createSet_AttachmentEntity(1, size);
	}
	
	public static Set<AttachmentEntity> createSet_AttachmentEntity(long index, int size) {
		Set<AttachmentEntity> attachmentEntitySet = createEmptySet_AttachmentEntity();
		long limit = index + size;
		for (; index < limit; index++)
			attachmentEntitySet.add(create_AttachmentEntity(index));
		return attachmentEntitySet;
	}
	
	public static void assertAttachmentEntityExists(Collection<AttachmentEntity> attachmentEntityList, AttachmentEntity attachmentEntity) {
		Assert.notNull(attachmentEntityList, "AttachmentEntity list must be specified");
		Assert.notNull(attachmentEntity, "AttachmentEntity record must be specified");
		Iterator<AttachmentEntity> iterator = attachmentEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			AttachmentEntity attachmentEntity1 = iterator.next();
			if (attachmentEntity1.equals(attachmentEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - AttachmentEntity should exist: "+attachmentEntity);
	}
	
	public static void assertAttachmentEntityCorrect(AttachmentEntity attachmentEntity) {
		long index = attachmentEntity.getId() / 10L;
		assertObjectCorrect("Name", attachmentEntity.getName(), index);
		assertObjectCorrect("Size", attachmentEntity.getSize(), index);
		assertObjectCorrect("FileName", attachmentEntity.getFileName(), index);
		assertObjectCorrect("FileData", attachmentEntity.getFileData(), index);
		assertObjectCorrect("ContentType", attachmentEntity.getContentType(), index);
	}
	
	public static void assertAttachmentEntityCorrect(Collection<AttachmentEntity> attachmentEntityList) {
		Assert.isTrue(attachmentEntityList.size() == 2, "AttachmentEntity count not correct");
		Iterator<AttachmentEntity> iterator = attachmentEntityList.iterator();
		while (iterator.hasNext()) {
			AttachmentEntity attachmentEntity = iterator.next();
			assertAttachmentEntityCorrect(attachmentEntity);
		}
	}
	
	public static void assertSameAttachmentEntity(AttachmentEntity attachmentEntity1, AttachmentEntity attachmentEntity2) {
		assertSameAttachmentEntity(attachmentEntity1, attachmentEntity2, false, "");
	}
	
	public static void assertSameAttachmentEntity(AttachmentEntity attachmentEntity1, AttachmentEntity attachmentEntity2, String message) {
		assertSameAttachmentEntity(attachmentEntity1, attachmentEntity2, false, message);
	}
	
	public static void assertSameAttachmentEntity(AttachmentEntity attachmentEntity1, AttachmentEntity attachmentEntity2, boolean checkIds) {
		assertSameAttachmentEntity(attachmentEntity1, attachmentEntity2, checkIds, "");
	}
	
	public static void assertSameAttachmentEntity(AttachmentEntity attachmentEntity1, AttachmentEntity attachmentEntity2, boolean checkIds, String message) {
		assertObjectExists("AttachmentEntity1", attachmentEntity1);
		assertObjectExists("AttachmentEntity2", attachmentEntity2);
		if (checkIds)
			assertObjectEquals("Id", attachmentEntity1.getId(), attachmentEntity2.getId(), message);
		assertObjectEquals("Name", attachmentEntity1.getName(), attachmentEntity2.getName(), message);
		assertObjectEquals("Size", attachmentEntity1.getSize(), attachmentEntity2.getSize(), message);
		assertObjectEquals("FileName", attachmentEntity1.getFileName(), attachmentEntity2.getFileName(), message);
		assertObjectEquals("FileData", attachmentEntity1.getFileData(), attachmentEntity2.getFileData(), message);
		assertObjectEquals("ContentType", attachmentEntity1.getContentType(), attachmentEntity2.getContentType(), message);
	}
	
	public static void assertSameAttachmentEntity(Collection<AttachmentEntity> attachmentEntityList1, Collection<AttachmentEntity> attachmentEntityList2) {
		assertSameAttachmentEntity(attachmentEntityList1, attachmentEntityList2, false, "");
	}
	
	public static void assertSameAttachmentEntity(Collection<AttachmentEntity> attachmentEntityList1, Collection<AttachmentEntity> attachmentEntityList2, String message) {
		assertSameAttachmentEntity(attachmentEntityList1, attachmentEntityList2, "");
	}
	
	public static void assertSameAttachmentEntity(Collection<AttachmentEntity> attachmentEntityList1, Collection<AttachmentEntity> attachmentEntityList2, boolean checkIds) {
		assertSameAttachmentEntity(attachmentEntityList1, attachmentEntityList2, checkIds, "");
	}
	
	public static void assertSameAttachmentEntity(Collection<AttachmentEntity> attachmentEntityList1, Collection<AttachmentEntity> attachmentEntityList2, boolean checkIds, String message) {
		Assert.notNull(attachmentEntityList1, "AttachmentEntity list1 must be specified");
		Assert.notNull(attachmentEntityList2, "AttachmentEntity list2 must be specified");
		Assert.equals(attachmentEntityList1.size(), attachmentEntityList2.size(), "AttachmentEntity count not equal");
		Iterator<AttachmentEntity> iterator1 = attachmentEntityList1.iterator();
		while (iterator1.hasNext()) {
			AttachmentEntity attachmentEntity1 = iterator1.next();
			Iterator<AttachmentEntity> iterator2 = attachmentEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				AttachmentEntity attachmentEntity2 = iterator2.next();
				if (attachmentEntity1.getId().equals(attachmentEntity2.getId())) {
					assertSameAttachmentEntity(attachmentEntity1, attachmentEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Attachment record not found: "+attachmentEntity1);
		}
	}
	
	public static PersonEntity createEmpty_PersonEntity() {
		PersonEntity personEntity = new PersonEntity();
		return personEntity;
	}

	public static PersonEntity create_PersonEntity() {
		PersonEntity personEntity = create_PersonEntity(1);
		return personEntity;
	}

	public static PersonEntity create_PersonEntity(long index) {
		PersonEntity personEntity = createEmpty_PersonEntity();
		personEntity.setUserId("dummyUserId" + index);
		personEntity.setName(CommonEntityFixture.create_PersonNameEntity(index));
		personEntity.setPhoneNumber(CommonEntityFixture.create_PhoneNumberEntity(index));
		personEntity.setEmailAddress(CommonEntityFixture.create_EmailAddressEntity(index));
		personEntity.setStreetAddress(CommonEntityFixture.create_StreetAddressEntity(index));
		personEntity.setId(10L * index);
		return personEntity;
	}

	public static List<PersonEntity> createEmptyList_PersonEntity() {
		return new ArrayList<PersonEntity>();
	}

	public static List<PersonEntity> createList_PersonEntity() {
		return createList_PersonEntity(2);
	}

	public static List<PersonEntity> createList_PersonEntity(int size) {
		return createList_PersonEntity(1, size);
	}
	
	public static List<PersonEntity> createList_PersonEntity(long index, int size) {
		List<PersonEntity> personEntityList = createEmptyList_PersonEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personEntityList.add(create_PersonEntity(index));
		return personEntityList;
	}

	public static Set<PersonEntity> createEmptySet_PersonEntity() {
		return new HashSet<PersonEntity>();
	}
	
	public static Set<PersonEntity> createSet_PersonEntity() {
		return createSet_PersonEntity(2);
	}
	
	public static Set<PersonEntity> createSet_PersonEntity(int size) {
		return createSet_PersonEntity(1, size);
	}
	
	public static Set<PersonEntity> createSet_PersonEntity(long index, int size) {
		Set<PersonEntity> personEntitySet = createEmptySet_PersonEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personEntitySet.add(create_PersonEntity(index));
		return personEntitySet;
	}
	
	public static void assertPersonEntityExists(Collection<PersonEntity> personEntityList, PersonEntity personEntity) {
		Assert.notNull(personEntityList, "PersonEntity list must be specified");
		Assert.notNull(personEntity, "PersonEntity record must be specified");
		Iterator<PersonEntity> iterator = personEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersonEntity personEntity1 = iterator.next();
			if (personEntity1.equals(personEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PersonEntity should exist: "+personEntity);
	}
	
	public static void assertPersonEntityCorrect(PersonEntity personEntity) {
		long index = personEntity.getId() / 10L;
		assertObjectCorrect("UserId", personEntity.getUserId(), index);
		assertPersonNameEntityCorrect(personEntity.getName());
		assertPhoneNumberEntityCorrect(personEntity.getPhoneNumber());
		assertEmailAddressEntityCorrect(personEntity.getEmailAddress());
		assertStreetAddressEntityCorrect(personEntity.getStreetAddress());
	}
	
	public static void assertPersonEntityCorrect(Collection<PersonEntity> personEntityList) {
		Assert.isTrue(personEntityList.size() == 2, "PersonEntity count not correct");
		Iterator<PersonEntity> iterator = personEntityList.iterator();
		while (iterator.hasNext()) {
			PersonEntity personEntity = iterator.next();
			assertPersonEntityCorrect(personEntity);
		}
	}
	
	public static void assertSamePersonEntity(PersonEntity personEntity1, PersonEntity personEntity2) {
		assertSamePersonEntity(personEntity1, personEntity2, false, "");
	}
	
	public static void assertSamePersonEntity(PersonEntity personEntity1, PersonEntity personEntity2, String message) {
		assertSamePersonEntity(personEntity1, personEntity2, false, message);
	}
	
	public static void assertSamePersonEntity(PersonEntity personEntity1, PersonEntity personEntity2, boolean checkIds) {
		assertSamePersonEntity(personEntity1, personEntity2, checkIds, "");
	}
	
	public static void assertSamePersonEntity(PersonEntity personEntity1, PersonEntity personEntity2, boolean checkIds, String message) {
		assertObjectExists("PersonEntity1", personEntity1);
		assertObjectExists("PersonEntity2", personEntity2);
		if (checkIds)
			assertObjectEquals("Id", personEntity1.getId(), personEntity2.getId(), message);
		assertObjectEquals("UserId", personEntity1.getUserId(), personEntity2.getUserId(), message);
		assertSamePersonNameEntity(personEntity1.getName(), personEntity2.getName(), checkIds, message);
		assertSamePhoneNumberEntity(personEntity1.getPhoneNumber(), personEntity2.getPhoneNumber(), checkIds, message);
		assertSameEmailAddressEntity(personEntity1.getEmailAddress(), personEntity2.getEmailAddress(), checkIds, message);
		assertSameStreetAddressEntity(personEntity1.getStreetAddress(), personEntity2.getStreetAddress(), checkIds, message);
	}
	
	public static void assertSamePersonEntity(Collection<PersonEntity> personEntityList1, Collection<PersonEntity> personEntityList2) {
		assertSamePersonEntity(personEntityList1, personEntityList2, false, "");
	}
	
	public static void assertSamePersonEntity(Collection<PersonEntity> personEntityList1, Collection<PersonEntity> personEntityList2, String message) {
		assertSamePersonEntity(personEntityList1, personEntityList2, "");
	}
	
	public static void assertSamePersonEntity(Collection<PersonEntity> personEntityList1, Collection<PersonEntity> personEntityList2, boolean checkIds) {
		assertSamePersonEntity(personEntityList1, personEntityList2, checkIds, "");
	}
	
	public static void assertSamePersonEntity(Collection<PersonEntity> personEntityList1, Collection<PersonEntity> personEntityList2, boolean checkIds, String message) {
		Assert.notNull(personEntityList1, "PersonEntity list1 must be specified");
		Assert.notNull(personEntityList2, "PersonEntity list2 must be specified");
		Assert.equals(personEntityList1.size(), personEntityList2.size(), "PersonEntity count not equal");
		Iterator<PersonEntity> iterator1 = personEntityList1.iterator();
		while (iterator1.hasNext()) {
			PersonEntity personEntity1 = iterator1.next();
			Iterator<PersonEntity> iterator2 = personEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				PersonEntity personEntity2 = iterator2.next();
				if (personEntity1.getId().equals(personEntity2.getId())) {
					assertSamePersonEntity(personEntity1, personEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "Person record not found: "+personEntity1);
		}
	}
	
	public static PersonNameEntity createEmpty_PersonNameEntity() {
		PersonNameEntity personNameEntity = new PersonNameEntity();
		return personNameEntity;
	}

	public static PersonNameEntity create_PersonNameEntity() {
		PersonNameEntity personNameEntity = create_PersonNameEntity(1);
		return personNameEntity;
	}

	public static PersonNameEntity create_PersonNameEntity(long index) {
		PersonNameEntity personNameEntity = createEmpty_PersonNameEntity();
		personNameEntity.setLastName("dummyLastName" + index);
		personNameEntity.setFirstName("dummyFirstName" + index);
		personNameEntity.setMiddleInitial("dummyMiddleInitial" + index);
		personNameEntity.setId(10L * index);
		return personNameEntity;
	}

	public static List<PersonNameEntity> createEmptyList_PersonNameEntity() {
		return new ArrayList<PersonNameEntity>();
	}

	public static List<PersonNameEntity> createList_PersonNameEntity() {
		return createList_PersonNameEntity(2);
	}

	public static List<PersonNameEntity> createList_PersonNameEntity(int size) {
		return createList_PersonNameEntity(1, size);
	}
	
	public static List<PersonNameEntity> createList_PersonNameEntity(long index, int size) {
		List<PersonNameEntity> personNameEntityList = createEmptyList_PersonNameEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personNameEntityList.add(create_PersonNameEntity(index));
		return personNameEntityList;
	}
	
	public static Set<PersonNameEntity> createEmptySet_PersonNameEntity() {
		return new HashSet<PersonNameEntity>();
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity() {
		return createSet_PersonNameEntity(2);
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity(int size) {
		return createSet_PersonNameEntity(1, size);
	}
	
	public static Set<PersonNameEntity> createSet_PersonNameEntity(long index, int size) {
		Set<PersonNameEntity> personNameEntitySet = createEmptySet_PersonNameEntity();
		long limit = index + size;
		for (; index < limit; index++)
			personNameEntitySet.add(create_PersonNameEntity(index));
		return personNameEntitySet;
	}
	
	public static void assertPersonNameEntityExists(Collection<PersonNameEntity> personNameEntityList, PersonNameEntity personNameEntity) {
		Assert.notNull(personNameEntityList, "PersonNameEntity list must be specified");
		Assert.notNull(personNameEntity, "PersonNameEntity record must be specified");
		Iterator<PersonNameEntity> iterator = personNameEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PersonNameEntity personNameEntity1 = iterator.next();
			if (personNameEntity1.equals(personNameEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PersonNameEntity should exist: "+personNameEntity);
	}
	
	public static void assertPersonNameEntityCorrect(PersonNameEntity personNameEntity) {
		long index = personNameEntity.getId() / 10L;
		assertObjectCorrect("LastName", personNameEntity.getLastName(), index);
		assertObjectCorrect("FirstName", personNameEntity.getFirstName(), index);
		assertObjectCorrect("MiddleInitial", personNameEntity.getMiddleInitial(), index);
	}
	
	public static void assertPersonNameEntityCorrect(Collection<PersonNameEntity> personNameEntityList) {
		Assert.isTrue(personNameEntityList.size() == 2, "PersonNameEntity count not correct");
		Iterator<PersonNameEntity> iterator = personNameEntityList.iterator();
		while (iterator.hasNext()) {
			PersonNameEntity personNameEntity = iterator.next();
			assertPersonNameEntityCorrect(personNameEntity);
		}
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, false, "");
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, String message) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, false, message);
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, boolean checkIds) {
		assertSamePersonNameEntity(personNameEntity1, personNameEntity2, checkIds, "");
	}
	
	public static void assertSamePersonNameEntity(PersonNameEntity personNameEntity1, PersonNameEntity personNameEntity2, boolean checkIds, String message) {
		assertObjectExists("PersonNameEntity1", personNameEntity1);
		assertObjectExists("PersonNameEntity2", personNameEntity2);
		if (checkIds)
			assertObjectEquals("Id", personNameEntity1.getId(), personNameEntity2.getId(), message);
		assertObjectEquals("LastName", personNameEntity1.getLastName(), personNameEntity2.getLastName(), message);
		assertObjectEquals("FirstName", personNameEntity1.getFirstName(), personNameEntity2.getFirstName(), message);
		assertObjectEquals("MiddleInitial", personNameEntity1.getMiddleInitial(), personNameEntity2.getMiddleInitial(), message);
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, false, "");
	}

	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, String message) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, "");
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, boolean checkIds) {
		assertSamePersonNameEntity(personNameEntityList1, personNameEntityList2, checkIds, "");
	}
	
	public static void assertSamePersonNameEntity(Collection<PersonNameEntity> personNameEntityList1, Collection<PersonNameEntity> personNameEntityList2, boolean checkIds, String message) {
		Assert.notNull(personNameEntityList1, "PersonNameEntity list1 must be specified");
		Assert.notNull(personNameEntityList2, "PersonNameEntity list2 must be specified");
		Assert.equals(personNameEntityList1.size(), personNameEntityList2.size(), "PersonNameEntity count not equal");
		Iterator<PersonNameEntity> iterator1 = personNameEntityList1.iterator();
		while (iterator1.hasNext()) {
			PersonNameEntity personNameEntity1 = iterator1.next();
			Iterator<PersonNameEntity> iterator2 = personNameEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				PersonNameEntity personNameEntity2 = iterator2.next();
				if (personNameEntity1.getId().equals(personNameEntity2.getId())) {
					assertSamePersonNameEntity(personNameEntity1, personNameEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "PersonName record not found: "+personNameEntity1);
		}
	}
	
	public static StreetAddressEntity createEmpty_StreetAddressEntity() {
		StreetAddressEntity streetAddressEntity = new StreetAddressEntity();
		return streetAddressEntity;
	}

	public static StreetAddressEntity create_StreetAddressEntity() {
		StreetAddressEntity streetAddressEntity = create_StreetAddressEntity(1);
		return streetAddressEntity;
	}

	public static StreetAddressEntity create_StreetAddressEntity(long index) {
		StreetAddressEntity streetAddressEntity = createEmpty_StreetAddressEntity();
		streetAddressEntity.setStreet("dummyStreet" + index);
		streetAddressEntity.setCity("dummyCity" + index);
		streetAddressEntity.setState(State.CA);
		streetAddressEntity.setCountry(Country.USA);
		streetAddressEntity.setLatitude(1.0D + (double) index);
		streetAddressEntity.setLongitude(1.0D + (double) index);
		streetAddressEntity.setZipCode(CommonEntityFixture.create_ZipCodeEntity(index));
		streetAddressEntity.setId(10L * index);
		return streetAddressEntity;
	}

	public static List<StreetAddressEntity> createEmptyList_StreetAddressEntity() {
		return new ArrayList<StreetAddressEntity>();
	}

	public static List<StreetAddressEntity> createList_StreetAddressEntity() {
		return createList_StreetAddressEntity(2);
	}

	public static List<StreetAddressEntity> createList_StreetAddressEntity(int size) {
		return createList_StreetAddressEntity(1, size);
	}
	
	public static List<StreetAddressEntity> createList_StreetAddressEntity(long index, int size) {
		List<StreetAddressEntity> streetAddressEntityList = createEmptyList_StreetAddressEntity();
		long limit = index + size;
		for (; index < limit; index++)
			streetAddressEntityList.add(create_StreetAddressEntity(index));
		return streetAddressEntityList;
	}

	public static Set<StreetAddressEntity> createEmptySet_StreetAddressEntity() {
		return new HashSet<StreetAddressEntity>();
	}
	
	public static Set<StreetAddressEntity> createSet_StreetAddressEntity() {
		return createSet_StreetAddressEntity(2);
	}
	
	public static Set<StreetAddressEntity> createSet_StreetAddressEntity(int size) {
		return createSet_StreetAddressEntity(1, size);
	}
	
	public static Set<StreetAddressEntity> createSet_StreetAddressEntity(long index, int size) {
		Set<StreetAddressEntity> streetAddressEntitySet = createEmptySet_StreetAddressEntity();
		long limit = index + size;
		for (; index < limit; index++)
			streetAddressEntitySet.add(create_StreetAddressEntity(index));
		return streetAddressEntitySet;
	}
	
	public static void assertStreetAddressEntityExists(Collection<StreetAddressEntity> streetAddressEntityList, StreetAddressEntity streetAddressEntity) {
		Assert.notNull(streetAddressEntityList, "StreetAddressEntity list must be specified");
		Assert.notNull(streetAddressEntity, "StreetAddressEntity record must be specified");
		Iterator<StreetAddressEntity> iterator = streetAddressEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			StreetAddressEntity streetAddressEntity1 = iterator.next();
			if (streetAddressEntity1.equals(streetAddressEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - StreetAddressEntity should exist: "+streetAddressEntity);
	}
	
	public static void assertStreetAddressEntityCorrect(StreetAddressEntity streetAddressEntity) {
		long index = streetAddressEntity.getId() / 10L;
		assertObjectCorrect("Street", streetAddressEntity.getStreet(), index);
		assertObjectCorrect("City", streetAddressEntity.getCity(), index);
		assertObjectCorrect("State", streetAddressEntity.getState(), index);
		assertObjectCorrect("Country", streetAddressEntity.getCountry(), index);
		assertObjectCorrect("Latitude", streetAddressEntity.getLatitude(), index);
		assertObjectCorrect("Longitude", streetAddressEntity.getLongitude(), index);
		assertZipCodeEntityCorrect(streetAddressEntity.getZipCode());
	}
	
	public static void assertStreetAddressEntityCorrect(Collection<StreetAddressEntity> streetAddressEntityList) {
		Assert.isTrue(streetAddressEntityList.size() == 2, "StreetAddressEntity count not correct");
		Iterator<StreetAddressEntity> iterator = streetAddressEntityList.iterator();
		while (iterator.hasNext()) {
			StreetAddressEntity streetAddressEntity = iterator.next();
			assertStreetAddressEntityCorrect(streetAddressEntity);
		}
	}
	
	public static void assertSameStreetAddressEntity(StreetAddressEntity streetAddressEntity1, StreetAddressEntity streetAddressEntity2) {
		assertSameStreetAddressEntity(streetAddressEntity1, streetAddressEntity2, false, "");
	}
	
	public static void assertSameStreetAddressEntity(StreetAddressEntity streetAddressEntity1, StreetAddressEntity streetAddressEntity2, String message) {
		assertSameStreetAddressEntity(streetAddressEntity1, streetAddressEntity2, false, message);
	}
	
	public static void assertSameStreetAddressEntity(StreetAddressEntity streetAddressEntity1, StreetAddressEntity streetAddressEntity2, boolean checkIds) {
		assertSameStreetAddressEntity(streetAddressEntity1, streetAddressEntity2, checkIds, "");
	}
	
	public static void assertSameStreetAddressEntity(StreetAddressEntity streetAddressEntity1, StreetAddressEntity streetAddressEntity2, boolean checkIds, String message) {
		assertObjectExists("StreetAddressEntity1", streetAddressEntity1);
		assertObjectExists("StreetAddressEntity2", streetAddressEntity2);
		if (checkIds)
			assertObjectEquals("Id", streetAddressEntity1.getId(), streetAddressEntity2.getId(), message);
		assertObjectEquals("Street", streetAddressEntity1.getStreet(), streetAddressEntity2.getStreet(), message);
		assertObjectEquals("City", streetAddressEntity1.getCity(), streetAddressEntity2.getCity(), message);
		assertObjectEquals("State", streetAddressEntity1.getState(), streetAddressEntity2.getState(), message);
		assertObjectEquals("Country", streetAddressEntity1.getCountry(), streetAddressEntity2.getCountry(), message);
		assertObjectEquals("Latitude", streetAddressEntity1.getLatitude(), streetAddressEntity2.getLatitude(), message);
		assertObjectEquals("Longitude", streetAddressEntity1.getLongitude(), streetAddressEntity2.getLongitude(), message);
		assertSameZipCodeEntity(streetAddressEntity1.getZipCode(), streetAddressEntity2.getZipCode(), checkIds, message);
	}
	
	public static void assertSameStreetAddressEntity(Collection<StreetAddressEntity> streetAddressEntityList1, Collection<StreetAddressEntity> streetAddressEntityList2) {
		assertSameStreetAddressEntity(streetAddressEntityList1, streetAddressEntityList2, false, "");
	}
	
	public static void assertSameStreetAddressEntity(Collection<StreetAddressEntity> streetAddressEntityList1, Collection<StreetAddressEntity> streetAddressEntityList2, String message) {
		assertSameStreetAddressEntity(streetAddressEntityList1, streetAddressEntityList2, "");
	}
	
	public static void assertSameStreetAddressEntity(Collection<StreetAddressEntity> streetAddressEntityList1, Collection<StreetAddressEntity> streetAddressEntityList2, boolean checkIds) {
		assertSameStreetAddressEntity(streetAddressEntityList1, streetAddressEntityList2, checkIds, "");
	}
	
	public static void assertSameStreetAddressEntity(Collection<StreetAddressEntity> streetAddressEntityList1, Collection<StreetAddressEntity> streetAddressEntityList2, boolean checkIds, String message) {
		Assert.notNull(streetAddressEntityList1, "StreetAddressEntity list1 must be specified");
		Assert.notNull(streetAddressEntityList2, "StreetAddressEntity list2 must be specified");
		Assert.equals(streetAddressEntityList1.size(), streetAddressEntityList2.size(), "StreetAddressEntity count not equal");
		Iterator<StreetAddressEntity> iterator1 = streetAddressEntityList1.iterator();
		while (iterator1.hasNext()) {
			StreetAddressEntity streetAddressEntity1 = iterator1.next();
			Iterator<StreetAddressEntity> iterator2 = streetAddressEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				StreetAddressEntity streetAddressEntity2 = iterator2.next();
				if (streetAddressEntity1.getId().equals(streetAddressEntity2.getId())) {
					assertSameStreetAddressEntity(streetAddressEntity1, streetAddressEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "StreetAddress record not found: "+streetAddressEntity1);
		}
	}
	
	public static ZipCodeEntity createEmpty_ZipCodeEntity() {
		ZipCodeEntity zipCodeEntity = new ZipCodeEntity();
		return zipCodeEntity;
	}

	public static ZipCodeEntity create_ZipCodeEntity() {
		ZipCodeEntity zipCodeEntity = create_ZipCodeEntity(1);
		return zipCodeEntity;
	}

	public static ZipCodeEntity create_ZipCodeEntity(long index) {
		ZipCodeEntity zipCodeEntity = createEmpty_ZipCodeEntity();
		zipCodeEntity.setNumber("dummyNumber" + index);
		zipCodeEntity.setExtension("dummyExtension" + index);
		zipCodeEntity.setCountry(Country.USA);
		zipCodeEntity.setId(10L * index);
		return zipCodeEntity;
	}

	public static List<ZipCodeEntity> createEmptyList_ZipCodeEntity() {
		return new ArrayList<ZipCodeEntity>();
	}

	public static List<ZipCodeEntity> createList_ZipCodeEntity() {
		return createList_ZipCodeEntity(2);
	}

	public static List<ZipCodeEntity> createList_ZipCodeEntity(int size) {
		return createList_ZipCodeEntity(1, size);
	}
	
	public static List<ZipCodeEntity> createList_ZipCodeEntity(long index, int size) {
		List<ZipCodeEntity> zipCodeEntityList = createEmptyList_ZipCodeEntity();
		long limit = index + size;
		for (; index < limit; index++)
			zipCodeEntityList.add(create_ZipCodeEntity(index));
		return zipCodeEntityList;
	}

	public static Set<ZipCodeEntity> createEmptySet_ZipCodeEntity() {
		return new HashSet<ZipCodeEntity>();
	}
	
	public static Set<ZipCodeEntity> createSet_ZipCodeEntity() {
		return createSet_ZipCodeEntity(2);
	}
	
	public static Set<ZipCodeEntity> createSet_ZipCodeEntity(int size) {
		return createSet_ZipCodeEntity(1, size);
	}
	
	public static Set<ZipCodeEntity> createSet_ZipCodeEntity(long index, int size) {
		Set<ZipCodeEntity> zipCodeEntitySet = createEmptySet_ZipCodeEntity();
		long limit = index + size;
		for (; index < limit; index++)
			zipCodeEntitySet.add(create_ZipCodeEntity(index));
		return zipCodeEntitySet;
	}
	
	public static void assertZipCodeEntityExists(Collection<ZipCodeEntity> zipCodeEntityList, ZipCodeEntity zipCodeEntity) {
		Assert.notNull(zipCodeEntityList, "ZipCodeEntity list must be specified");
		Assert.notNull(zipCodeEntity, "ZipCodeEntity record must be specified");
		Iterator<ZipCodeEntity> iterator = zipCodeEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ZipCodeEntity zipCodeEntity1 = iterator.next();
			if (zipCodeEntity1.equals(zipCodeEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - ZipCodeEntity should exist: "+zipCodeEntity);
	}
	
	public static void assertZipCodeEntityCorrect(ZipCodeEntity zipCodeEntity) {
		long index = zipCodeEntity.getId() / 10L;
		assertObjectCorrect("Number", zipCodeEntity.getNumber(), index);
		assertObjectCorrect("Extension", zipCodeEntity.getExtension(), index);
		assertObjectCorrect("Country", zipCodeEntity.getCountry(), index);
	}
	
	public static void assertZipCodeEntityCorrect(Collection<ZipCodeEntity> zipCodeEntityList) {
		Assert.isTrue(zipCodeEntityList.size() == 2, "ZipCodeEntity count not correct");
		Iterator<ZipCodeEntity> iterator = zipCodeEntityList.iterator();
		while (iterator.hasNext()) {
			ZipCodeEntity zipCodeEntity = iterator.next();
			assertZipCodeEntityCorrect(zipCodeEntity);
		}
	}
	
	public static void assertSameZipCodeEntity(ZipCodeEntity zipCodeEntity1, ZipCodeEntity zipCodeEntity2) {
		assertSameZipCodeEntity(zipCodeEntity1, zipCodeEntity2, false, "");
	}
	
	public static void assertSameZipCodeEntity(ZipCodeEntity zipCodeEntity1, ZipCodeEntity zipCodeEntity2, String message) {
		assertSameZipCodeEntity(zipCodeEntity1, zipCodeEntity2, false, message);
	}
	
	public static void assertSameZipCodeEntity(ZipCodeEntity zipCodeEntity1, ZipCodeEntity zipCodeEntity2, boolean checkIds) {
		assertSameZipCodeEntity(zipCodeEntity1, zipCodeEntity2, checkIds, "");
	}
	
	public static void assertSameZipCodeEntity(ZipCodeEntity zipCodeEntity1, ZipCodeEntity zipCodeEntity2, boolean checkIds, String message) {
		assertObjectExists("ZipCodeEntity1", zipCodeEntity1);
		assertObjectExists("ZipCodeEntity2", zipCodeEntity2);
		if (checkIds)
			assertObjectEquals("Id", zipCodeEntity1.getId(), zipCodeEntity2.getId(), message);
		assertObjectEquals("Number", zipCodeEntity1.getNumber(), zipCodeEntity2.getNumber(), message);
		assertObjectEquals("Extension", zipCodeEntity1.getExtension(), zipCodeEntity2.getExtension(), message);
		assertObjectEquals("Country", zipCodeEntity1.getCountry(), zipCodeEntity2.getCountry(), message);
	}
	
	public static void assertSameZipCodeEntity(Collection<ZipCodeEntity> zipCodeEntityList1, Collection<ZipCodeEntity> zipCodeEntityList2) {
		assertSameZipCodeEntity(zipCodeEntityList1, zipCodeEntityList2, false, "");
	}
	
	public static void assertSameZipCodeEntity(Collection<ZipCodeEntity> zipCodeEntityList1, Collection<ZipCodeEntity> zipCodeEntityList2, String message) {
		assertSameZipCodeEntity(zipCodeEntityList1, zipCodeEntityList2, "");
	}
	
	public static void assertSameZipCodeEntity(Collection<ZipCodeEntity> zipCodeEntityList1, Collection<ZipCodeEntity> zipCodeEntityList2, boolean checkIds) {
		assertSameZipCodeEntity(zipCodeEntityList1, zipCodeEntityList2, checkIds, "");
	}
	
	public static void assertSameZipCodeEntity(Collection<ZipCodeEntity> zipCodeEntityList1, Collection<ZipCodeEntity> zipCodeEntityList2, boolean checkIds, String message) {
		Assert.notNull(zipCodeEntityList1, "ZipCodeEntity list1 must be specified");
		Assert.notNull(zipCodeEntityList2, "ZipCodeEntity list2 must be specified");
		Assert.equals(zipCodeEntityList1.size(), zipCodeEntityList2.size(), "ZipCodeEntity count not equal");
		Iterator<ZipCodeEntity> iterator1 = zipCodeEntityList1.iterator();
		while (iterator1.hasNext()) {
			ZipCodeEntity zipCodeEntity1 = iterator1.next();
			Iterator<ZipCodeEntity> iterator2 = zipCodeEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				ZipCodeEntity zipCodeEntity2 = iterator2.next();
				if (zipCodeEntity1.getId().equals(zipCodeEntity2.getId())) {
					assertSameZipCodeEntity(zipCodeEntity1, zipCodeEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "ZipCode record not found: "+zipCodeEntity1);
		}
	}
	
	public static PhoneNumberEntity createEmpty_PhoneNumberEntity() {
		PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
		return phoneNumberEntity;
	}

	public static PhoneNumberEntity create_PhoneNumberEntity() {
		PhoneNumberEntity phoneNumberEntity = create_PhoneNumberEntity(1);
		return phoneNumberEntity;
	}

	public static PhoneNumberEntity create_PhoneNumberEntity(long index) {
		PhoneNumberEntity phoneNumberEntity = createEmpty_PhoneNumberEntity();
		phoneNumberEntity.setArea("dummyArea" + index);
		phoneNumberEntity.setNumber("dummyNumber" + index);
		phoneNumberEntity.setExtension("dummyExtension" + index);
		phoneNumberEntity.setCountry(Country.USA);
		phoneNumberEntity.setType(PhoneLocation.HOME);
		phoneNumberEntity.setValue("dummyValue" + index);
		phoneNumberEntity.setId(10L * index);
		return phoneNumberEntity;
	}

	public static List<PhoneNumberEntity> createEmptyList_PhoneNumberEntity() {
		return new ArrayList<PhoneNumberEntity>();
	}

	public static List<PhoneNumberEntity> createList_PhoneNumberEntity() {
		return createList_PhoneNumberEntity(2);
	}

	public static List<PhoneNumberEntity> createList_PhoneNumberEntity(int size) {
		return createList_PhoneNumberEntity(1, size);
	}
	
	public static List<PhoneNumberEntity> createList_PhoneNumberEntity(long index, int size) {
		List<PhoneNumberEntity> phoneNumberEntityList = createEmptyList_PhoneNumberEntity();
		long limit = index + size;
		for (; index < limit; index++)
			phoneNumberEntityList.add(create_PhoneNumberEntity(index));
		return phoneNumberEntityList;
	}

	public static Set<PhoneNumberEntity> createEmptySet_PhoneNumberEntity() {
		return new HashSet<PhoneNumberEntity>();
	}
	
	public static Set<PhoneNumberEntity> createSet_PhoneNumberEntity() {
		return createSet_PhoneNumberEntity(2);
	}
	
	public static Set<PhoneNumberEntity> createSet_PhoneNumberEntity(int size) {
		return createSet_PhoneNumberEntity(1, size);
	}
	
	public static Set<PhoneNumberEntity> createSet_PhoneNumberEntity(long index, int size) {
		Set<PhoneNumberEntity> phoneNumberEntitySet = createEmptySet_PhoneNumberEntity();
		long limit = index + size;
		for (; index < limit; index++)
			phoneNumberEntitySet.add(create_PhoneNumberEntity(index));
		return phoneNumberEntitySet;
	}
	
	public static void assertPhoneNumberEntityExists(Collection<PhoneNumberEntity> phoneNumberEntityList, PhoneNumberEntity phoneNumberEntity) {
		Assert.notNull(phoneNumberEntityList, "PhoneNumberEntity list must be specified");
		Assert.notNull(phoneNumberEntity, "PhoneNumberEntity record must be specified");
		Iterator<PhoneNumberEntity> iterator = phoneNumberEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			PhoneNumberEntity phoneNumberEntity1 = iterator.next();
			if (phoneNumberEntity1.equals(phoneNumberEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - PhoneNumberEntity should exist: "+phoneNumberEntity);
	}
	
	public static void assertPhoneNumberEntityCorrect(PhoneNumberEntity phoneNumberEntity) {
		long index = phoneNumberEntity.getId() / 10L;
		assertObjectCorrect("Area", phoneNumberEntity.getArea(), index);
		assertObjectCorrect("Number", phoneNumberEntity.getNumber(), index);
		assertObjectCorrect("Extension", phoneNumberEntity.getExtension(), index);
		assertObjectCorrect("Country", phoneNumberEntity.getCountry(), index);
		assertObjectCorrect("Type", phoneNumberEntity.getType(), index);
		assertObjectCorrect("Value", phoneNumberEntity.getValue(), index);
	}
	
	public static void assertPhoneNumberEntityCorrect(Collection<PhoneNumberEntity> phoneNumberEntityList) {
		Assert.isTrue(phoneNumberEntityList.size() == 2, "PhoneNumberEntity count not correct");
		Iterator<PhoneNumberEntity> iterator = phoneNumberEntityList.iterator();
		while (iterator.hasNext()) {
			PhoneNumberEntity phoneNumberEntity = iterator.next();
			assertPhoneNumberEntityCorrect(phoneNumberEntity);
		}
	}
	
	public static void assertSamePhoneNumberEntity(PhoneNumberEntity phoneNumberEntity1, PhoneNumberEntity phoneNumberEntity2) {
		assertSamePhoneNumberEntity(phoneNumberEntity1, phoneNumberEntity2, false, "");
	}
	
	public static void assertSamePhoneNumberEntity(PhoneNumberEntity phoneNumberEntity1, PhoneNumberEntity phoneNumberEntity2, String message) {
		assertSamePhoneNumberEntity(phoneNumberEntity1, phoneNumberEntity2, false, message);
	}
	
	public static void assertSamePhoneNumberEntity(PhoneNumberEntity phoneNumberEntity1, PhoneNumberEntity phoneNumberEntity2, boolean checkIds) {
		assertSamePhoneNumberEntity(phoneNumberEntity1, phoneNumberEntity2, checkIds, "");
	}
	
	public static void assertSamePhoneNumberEntity(PhoneNumberEntity phoneNumberEntity1, PhoneNumberEntity phoneNumberEntity2, boolean checkIds, String message) {
		assertObjectExists("PhoneNumberEntity1", phoneNumberEntity1);
		assertObjectExists("PhoneNumberEntity2", phoneNumberEntity2);
		if (checkIds)
			assertObjectEquals("Id", phoneNumberEntity1.getId(), phoneNumberEntity2.getId(), message);
		assertObjectEquals("Area", phoneNumberEntity1.getArea(), phoneNumberEntity2.getArea(), message);
		assertObjectEquals("Number", phoneNumberEntity1.getNumber(), phoneNumberEntity2.getNumber(), message);
		assertObjectEquals("Extension", phoneNumberEntity1.getExtension(), phoneNumberEntity2.getExtension(), message);
		assertObjectEquals("Country", phoneNumberEntity1.getCountry(), phoneNumberEntity2.getCountry(), message);
		assertObjectEquals("Type", phoneNumberEntity1.getType(), phoneNumberEntity2.getType(), message);
		assertObjectEquals("Value", phoneNumberEntity1.getValue(), phoneNumberEntity2.getValue(), message);
	}
	
	public static void assertSamePhoneNumberEntity(Collection<PhoneNumberEntity> phoneNumberEntityList1, Collection<PhoneNumberEntity> phoneNumberEntityList2) {
		assertSamePhoneNumberEntity(phoneNumberEntityList1, phoneNumberEntityList2, false, "");
	}
	
	public static void assertSamePhoneNumberEntity(Collection<PhoneNumberEntity> phoneNumberEntityList1, Collection<PhoneNumberEntity> phoneNumberEntityList2, String message) {
		assertSamePhoneNumberEntity(phoneNumberEntityList1, phoneNumberEntityList2, "");
	}
	
	public static void assertSamePhoneNumberEntity(Collection<PhoneNumberEntity> phoneNumberEntityList1, Collection<PhoneNumberEntity> phoneNumberEntityList2, boolean checkIds) {
		assertSamePhoneNumberEntity(phoneNumberEntityList1, phoneNumberEntityList2, checkIds, "");
	}
	
	public static void assertSamePhoneNumberEntity(Collection<PhoneNumberEntity> phoneNumberEntityList1, Collection<PhoneNumberEntity> phoneNumberEntityList2, boolean checkIds, String message) {
		Assert.notNull(phoneNumberEntityList1, "PhoneNumberEntity list1 must be specified");
		Assert.notNull(phoneNumberEntityList2, "PhoneNumberEntity list2 must be specified");
		Assert.equals(phoneNumberEntityList1.size(), phoneNumberEntityList2.size(), "PhoneNumberEntity count not equal");
		Iterator<PhoneNumberEntity> iterator1 = phoneNumberEntityList1.iterator();
		while (iterator1.hasNext()) {
			PhoneNumberEntity phoneNumberEntity1 = iterator1.next();
			Iterator<PhoneNumberEntity> iterator2 = phoneNumberEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				PhoneNumberEntity phoneNumberEntity2 = iterator2.next();
				if (phoneNumberEntity1.getId().equals(phoneNumberEntity2.getId())) {
					assertSamePhoneNumberEntity(phoneNumberEntity1, phoneNumberEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "PhoneNumber record not found: "+phoneNumberEntity1);
		}
	}
	
	public static EmailAddressEntity createEmpty_EmailAddressEntity() {
		EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
		return emailAddressEntity;
	}

	public static EmailAddressEntity create_EmailAddressEntity() {
		EmailAddressEntity emailAddressEntity = create_EmailAddressEntity(1);
		return emailAddressEntity;
	}

	public static EmailAddressEntity create_EmailAddressEntity(long index) {
		EmailAddressEntity emailAddressEntity = createEmpty_EmailAddressEntity();
		emailAddressEntity.setUrl("dummyUrl" + index + counter++);
		emailAddressEntity.setUserId("dummyUserId" + index);
		emailAddressEntity.setFirstName("dummyFirstName" + index);
		emailAddressEntity.setLastName("dummyLastName" + index);
		emailAddressEntity.setOrganization("dummyOrganization" + index);
		emailAddressEntity.setCreationDate(new Date(1000L + index));
		emailAddressEntity.setLastUpdate(new Date(1000L + index));
		emailAddressEntity.setEnabled(true);
		emailAddressEntity.setPhoneNumber(CommonEntityFixture.create_PhoneNumberEntity(index));
		emailAddressEntity.setId(10L * index);
		return emailAddressEntity;
	}

	public static List<EmailAddressEntity> createEmptyList_EmailAddressEntity() {
		return new ArrayList<EmailAddressEntity>();
	}

	public static List<EmailAddressEntity> createList_EmailAddressEntity() {
		return createList_EmailAddressEntity(2);
	}

	public static List<EmailAddressEntity> createList_EmailAddressEntity(int size) {
		return createList_EmailAddressEntity(1, size);
	}
	
	public static List<EmailAddressEntity> createList_EmailAddressEntity(long index, int size) {
		List<EmailAddressEntity> emailAddressEntityList = createEmptyList_EmailAddressEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressEntityList.add(create_EmailAddressEntity(index));
		return emailAddressEntityList;
	}

	public static Set<EmailAddressEntity> createEmptySet_EmailAddressEntity() {
		return new HashSet<EmailAddressEntity>();
	}
	
	public static Set<EmailAddressEntity> createSet_EmailAddressEntity() {
		return createSet_EmailAddressEntity(2);
	}
	
	public static Set<EmailAddressEntity> createSet_EmailAddressEntity(int size) {
		return createSet_EmailAddressEntity(1, size);
	}
	
	public static Set<EmailAddressEntity> createSet_EmailAddressEntity(long index, int size) {
		Set<EmailAddressEntity> emailAddressEntitySet = createEmptySet_EmailAddressEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressEntitySet.add(create_EmailAddressEntity(index));
		return emailAddressEntitySet;
	}
	
	public static void assertEmailAddressEntityExists(Collection<EmailAddressEntity> emailAddressEntityList, EmailAddressEntity emailAddressEntity) {
		Assert.notNull(emailAddressEntityList, "EmailAddressEntity list must be specified");
		Assert.notNull(emailAddressEntity, "EmailAddressEntity record must be specified");
		Iterator<EmailAddressEntity> iterator = emailAddressEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddressEntity emailAddressEntity1 = iterator.next();
			if (emailAddressEntity1.equals(emailAddressEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAddressEntity should exist: "+emailAddressEntity);
	}
	
	public static void assertEmailAddressEntityCorrect(EmailAddressEntity emailAddressEntity) {
		long index = emailAddressEntity.getId() / 10L;
		assertObjectCorrect("Url", emailAddressEntity.getUrl(), index);
		assertObjectCorrect("UserId", emailAddressEntity.getUserId(), index);
		assertObjectCorrect("FirstName", emailAddressEntity.getFirstName(), index);
		assertObjectCorrect("LastName", emailAddressEntity.getLastName(), index);
		assertObjectCorrect("Organization", emailAddressEntity.getOrganization(), index);
		assertObjectCorrect("CreationDate", emailAddressEntity.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", emailAddressEntity.getLastUpdate(), index);
		assertObjectCorrect("Enabled", emailAddressEntity.getEnabled(), index);
		assertPhoneNumberEntityCorrect(emailAddressEntity.getPhoneNumber());
	}
	
	public static void assertEmailAddressEntityCorrect(Collection<EmailAddressEntity> emailAddressEntityList) {
		Assert.isTrue(emailAddressEntityList.size() == 2, "EmailAddressEntity count not correct");
		Iterator<EmailAddressEntity> iterator = emailAddressEntityList.iterator();
		while (iterator.hasNext()) {
			EmailAddressEntity emailAddressEntity = iterator.next();
			assertEmailAddressEntityCorrect(emailAddressEntity);
		}
	}
	
	public static void assertSameEmailAddressEntity(EmailAddressEntity emailAddressEntity1, EmailAddressEntity emailAddressEntity2) {
		assertSameEmailAddressEntity(emailAddressEntity1, emailAddressEntity2, false, "");
	}
	
	public static void assertSameEmailAddressEntity(EmailAddressEntity emailAddressEntity1, EmailAddressEntity emailAddressEntity2, String message) {
		assertSameEmailAddressEntity(emailAddressEntity1, emailAddressEntity2, false, message);
	}
	
	public static void assertSameEmailAddressEntity(EmailAddressEntity emailAddressEntity1, EmailAddressEntity emailAddressEntity2, boolean checkIds) {
		assertSameEmailAddressEntity(emailAddressEntity1, emailAddressEntity2, checkIds, "");
	}
	
	public static void assertSameEmailAddressEntity(EmailAddressEntity emailAddressEntity1, EmailAddressEntity emailAddressEntity2, boolean checkIds, String message) {
		assertObjectExists("EmailAddressEntity1", emailAddressEntity1);
		assertObjectExists("EmailAddressEntity2", emailAddressEntity2);
		if (checkIds)
			assertObjectEquals("Id", emailAddressEntity1.getId(), emailAddressEntity2.getId(), message);
		assertObjectEquals("Url", emailAddressEntity1.getUrl(), emailAddressEntity2.getUrl(), message);
		assertObjectEquals("UserId", emailAddressEntity1.getUserId(), emailAddressEntity2.getUserId(), message);
		assertObjectEquals("FirstName", emailAddressEntity1.getFirstName(), emailAddressEntity2.getFirstName(), message);
		assertObjectEquals("LastName", emailAddressEntity1.getLastName(), emailAddressEntity2.getLastName(), message);
		assertObjectEquals("Organization", emailAddressEntity1.getOrganization(), emailAddressEntity2.getOrganization(), message);
		assertObjectEquals("CreationDate", emailAddressEntity1.getCreationDate(), emailAddressEntity2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", emailAddressEntity1.getLastUpdate(), emailAddressEntity2.getLastUpdate(), message);
		assertObjectEquals("Enabled", emailAddressEntity1.getEnabled(), emailAddressEntity2.getEnabled(), message);
		assertSamePhoneNumberEntity(emailAddressEntity1.getPhoneNumber(), emailAddressEntity2.getPhoneNumber(), checkIds, message);
	}
	
	public static void assertSameEmailAddressEntity(Collection<EmailAddressEntity> emailAddressEntityList1, Collection<EmailAddressEntity> emailAddressEntityList2) {
		assertSameEmailAddressEntity(emailAddressEntityList1, emailAddressEntityList2, false, "");
	}
	
	public static void assertSameEmailAddressEntity(Collection<EmailAddressEntity> emailAddressEntityList1, Collection<EmailAddressEntity> emailAddressEntityList2, String message) {
		assertSameEmailAddressEntity(emailAddressEntityList1, emailAddressEntityList2, "");
	}
	
	public static void assertSameEmailAddressEntity(Collection<EmailAddressEntity> emailAddressEntityList1, Collection<EmailAddressEntity> emailAddressEntityList2, boolean checkIds) {
		assertSameEmailAddressEntity(emailAddressEntityList1, emailAddressEntityList2, checkIds, "");
	}
	
	public static void assertSameEmailAddressEntity(Collection<EmailAddressEntity> emailAddressEntityList1, Collection<EmailAddressEntity> emailAddressEntityList2, boolean checkIds, String message) {
		Assert.notNull(emailAddressEntityList1, "EmailAddressEntity list1 must be specified");
		Assert.notNull(emailAddressEntityList2, "EmailAddressEntity list2 must be specified");
		Assert.equals(emailAddressEntityList1.size(), emailAddressEntityList2.size(), "EmailAddressEntity count not equal");
		Iterator<EmailAddressEntity> iterator1 = emailAddressEntityList1.iterator();
		while (iterator1.hasNext()) {
			EmailAddressEntity emailAddressEntity1 = iterator1.next();
			Iterator<EmailAddressEntity> iterator2 = emailAddressEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				EmailAddressEntity emailAddressEntity2 = iterator2.next();
				if (emailAddressEntity1.getId().equals(emailAddressEntity2.getId())) {
					assertSameEmailAddressEntity(emailAddressEntity1, emailAddressEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "EmailAddress record not found: "+emailAddressEntity1);
		}
	}
	
	public static EmailAddressListEntity createEmpty_EmailAddressListEntity() {
		EmailAddressListEntity emailAddressListEntity = new EmailAddressListEntity();
		return emailAddressListEntity;
	}

	public static EmailAddressListEntity create_EmailAddressListEntity() {
		EmailAddressListEntity emailAddressListEntity = create_EmailAddressListEntity(1);
		return emailAddressListEntity;
	}

	public static EmailAddressListEntity create_EmailAddressListEntity(long index) {
		EmailAddressListEntity emailAddressListEntity = createEmpty_EmailAddressListEntity();
		emailAddressListEntity.setName("dummyName" + index);
		emailAddressListEntity.getAddresses().addAll(CommonEntityFixture.createList_EmailAddressEntity(2));
		emailAddressListEntity.setId(10L * index);
		return emailAddressListEntity;
	}

	public static List<EmailAddressListEntity> createEmptyList_EmailAddressListEntity() {
		return new ArrayList<EmailAddressListEntity>();
	}

	public static List<EmailAddressListEntity> createList_EmailAddressListEntity() {
		return createList_EmailAddressListEntity(2);
	}

	public static List<EmailAddressListEntity> createList_EmailAddressListEntity(int size) {
		return createList_EmailAddressListEntity(1, size);
	}
	
	public static List<EmailAddressListEntity> createList_EmailAddressListEntity(long index, int size) {
		List<EmailAddressListEntity> emailAddressListEntityList = createEmptyList_EmailAddressListEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressListEntityList.add(create_EmailAddressListEntity(index));
		return emailAddressListEntityList;
	}

	public static Set<EmailAddressListEntity> createEmptySet_EmailAddressListEntity() {
		return new HashSet<EmailAddressListEntity>();
	}
	
	public static Set<EmailAddressListEntity> createSet_EmailAddressListEntity() {
		return createSet_EmailAddressListEntity(2);
	}
	
	public static Set<EmailAddressListEntity> createSet_EmailAddressListEntity(int size) {
		return createSet_EmailAddressListEntity(1, size);
	}
	
	public static Set<EmailAddressListEntity> createSet_EmailAddressListEntity(long index, int size) {
		Set<EmailAddressListEntity> emailAddressListEntitySet = createEmptySet_EmailAddressListEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAddressListEntitySet.add(create_EmailAddressListEntity(index));
		return emailAddressListEntitySet;
	}
	
	public static void assertEmailAddressListEntityExists(Collection<EmailAddressListEntity> emailAddressListEntityList, EmailAddressListEntity emailAddressListEntity) {
		Assert.notNull(emailAddressListEntityList, "EmailAddressListEntity list must be specified");
		Assert.notNull(emailAddressListEntity, "EmailAddressListEntity record must be specified");
		Iterator<EmailAddressListEntity> iterator = emailAddressListEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAddressListEntity emailAddressListEntity1 = iterator.next();
			if (emailAddressListEntity1.equals(emailAddressListEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAddressListEntity should exist: "+emailAddressListEntity);
	}
	
	public static void assertEmailAddressListEntityCorrect(EmailAddressListEntity emailAddressListEntity) {
		long index = emailAddressListEntity.getId() / 10L;
		assertObjectCorrect("Name", emailAddressListEntity.getName(), index);
		assertEmailAddressEntityCorrect(emailAddressListEntity.getAddresses());
	}
	
	public static void assertEmailAddressListEntityCorrect(Collection<EmailAddressListEntity> emailAddressListEntityList) {
		Assert.isTrue(emailAddressListEntityList.size() == 2, "EmailAddressListEntity count not correct");
		Iterator<EmailAddressListEntity> iterator = emailAddressListEntityList.iterator();
		while (iterator.hasNext()) {
			EmailAddressListEntity emailAddressListEntity = iterator.next();
			assertEmailAddressListEntityCorrect(emailAddressListEntity);
		}
	}
	
	public static void assertSameEmailAddressListEntity(EmailAddressListEntity emailAddressListEntity1, EmailAddressListEntity emailAddressListEntity2) {
		assertSameEmailAddressListEntity(emailAddressListEntity1, emailAddressListEntity2, false, "");
	}
	
	public static void assertSameEmailAddressListEntity(EmailAddressListEntity emailAddressListEntity1, EmailAddressListEntity emailAddressListEntity2, String message) {
		assertSameEmailAddressListEntity(emailAddressListEntity1, emailAddressListEntity2, false, message);
	}
	
	public static void assertSameEmailAddressListEntity(EmailAddressListEntity emailAddressListEntity1, EmailAddressListEntity emailAddressListEntity2, boolean checkIds) {
		assertSameEmailAddressListEntity(emailAddressListEntity1, emailAddressListEntity2, checkIds, "");
	}
	
	public static void assertSameEmailAddressListEntity(EmailAddressListEntity emailAddressListEntity1, EmailAddressListEntity emailAddressListEntity2, boolean checkIds, String message) {
		assertObjectExists("EmailAddressListEntity1", emailAddressListEntity1);
		assertObjectExists("EmailAddressListEntity2", emailAddressListEntity2);
		if (checkIds)
			assertObjectEquals("Id", emailAddressListEntity1.getId(), emailAddressListEntity2.getId(), message);
		assertObjectEquals("Name", emailAddressListEntity1.getName(), emailAddressListEntity2.getName(), message);
		assertSameEmailAddressEntity(emailAddressListEntity1.getAddresses(), emailAddressListEntity2.getAddresses(), checkIds, message);
	}
	
	public static void assertSameEmailAddressListEntity(Collection<EmailAddressListEntity> emailAddressListEntityList1, Collection<EmailAddressListEntity> emailAddressListEntityList2) {
		assertSameEmailAddressListEntity(emailAddressListEntityList1, emailAddressListEntityList2, false, "");
	}
	
	public static void assertSameEmailAddressListEntity(Collection<EmailAddressListEntity> emailAddressListEntityList1, Collection<EmailAddressListEntity> emailAddressListEntityList2, String message) {
		assertSameEmailAddressListEntity(emailAddressListEntityList1, emailAddressListEntityList2, "");
	}
	
	public static void assertSameEmailAddressListEntity(Collection<EmailAddressListEntity> emailAddressListEntityList1, Collection<EmailAddressListEntity> emailAddressListEntityList2, boolean checkIds) {
		assertSameEmailAddressListEntity(emailAddressListEntityList1, emailAddressListEntityList2, checkIds, "");
	}
	
	public static void assertSameEmailAddressListEntity(Collection<EmailAddressListEntity> emailAddressListEntityList1, Collection<EmailAddressListEntity> emailAddressListEntityList2, boolean checkIds, String message) {
		Assert.notNull(emailAddressListEntityList1, "EmailAddressListEntity list1 must be specified");
		Assert.notNull(emailAddressListEntityList2, "EmailAddressListEntity list2 must be specified");
		Assert.equals(emailAddressListEntityList1.size(), emailAddressListEntityList2.size(), "EmailAddressListEntity count not equal");
		Iterator<EmailAddressListEntity> iterator1 = emailAddressListEntityList1.iterator();
		while (iterator1.hasNext()) {
			EmailAddressListEntity emailAddressListEntity1 = iterator1.next();
			Iterator<EmailAddressListEntity> iterator2 = emailAddressListEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				EmailAddressListEntity emailAddressListEntity2 = iterator2.next();
				if (emailAddressListEntity1.getId().equals(emailAddressListEntity2.getId())) {
					assertSameEmailAddressListEntity(emailAddressListEntity1, emailAddressListEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "EmailAddressList record not found: "+emailAddressListEntity1);
		}
	}
	
	public static EmailMessageEntity createEmpty_EmailMessageEntity() {
		EmailMessageEntity emailMessageEntity = new EmailMessageEntity();
		return emailMessageEntity;
	}

	public static EmailMessageEntity create_EmailMessageEntity() {
		EmailMessageEntity emailMessageEntity = create_EmailMessageEntity(1);
		return emailMessageEntity;
	}

	public static EmailMessageEntity create_EmailMessageEntity(long index) {
		EmailMessageEntity emailMessageEntity = createEmpty_EmailMessageEntity();
		emailMessageEntity.setContent("dummyContent" + index);
		emailMessageEntity.setSubject("dummySubject" + index);
		emailMessageEntity.setTimestamp(new Date(1000L + index));
		emailMessageEntity.setSourceId("dummySourceId" + index);
		emailMessageEntity.setSmtpHost("dummySmtpHost" + index);
		emailMessageEntity.setSmtpPort("dummySmtpPort" + index);
		emailMessageEntity.setSendAsHtml(false);
		emailMessageEntity.setFromAddress(CommonEntityFixture.create_EmailAddressEntity(index));
		emailMessageEntity.getToAddresses().addAll(CommonEntityFixture.createList_EmailAddressListEntity(2));
		emailMessageEntity.getBccAddresses().addAll(CommonEntityFixture.createList_EmailAddressListEntity(2));
		emailMessageEntity.getCcAddresses().addAll(CommonEntityFixture.createList_EmailAddressListEntity(2));
		emailMessageEntity.getReplytoAddresses().addAll(CommonEntityFixture.createList_EmailAddressListEntity(2));
		emailMessageEntity.getAdminAddresses().addAll(CommonEntityFixture.createList_EmailAddressListEntity(2));
		emailMessageEntity.getAttachments().addAll(CommonEntityFixture.createList_AttachmentEntity(2));
		emailMessageEntity.setId(10L * index);
		return emailMessageEntity;
	}

	public static List<EmailMessageEntity> createEmptyList_EmailMessageEntity() {
		return new ArrayList<EmailMessageEntity>();
	}

	public static List<EmailMessageEntity> createList_EmailMessageEntity() {
		return createList_EmailMessageEntity(2);
	}

	public static List<EmailMessageEntity> createList_EmailMessageEntity(int size) {
		return createList_EmailMessageEntity(1, size);
	}
	
	public static List<EmailMessageEntity> createList_EmailMessageEntity(long index, int size) {
		List<EmailMessageEntity> emailMessageEntityList = createEmptyList_EmailMessageEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailMessageEntityList.add(create_EmailMessageEntity(index));
		return emailMessageEntityList;
	}

	public static Set<EmailMessageEntity> createEmptySet_EmailMessageEntity() {
		return new HashSet<EmailMessageEntity>();
	}
	
	public static Set<EmailMessageEntity> createSet_EmailMessageEntity() {
		return createSet_EmailMessageEntity(2);
	}
	
	public static Set<EmailMessageEntity> createSet_EmailMessageEntity(int size) {
		return createSet_EmailMessageEntity(1, size);
	}
	
	public static Set<EmailMessageEntity> createSet_EmailMessageEntity(long index, int size) {
		Set<EmailMessageEntity> emailMessageEntitySet = createEmptySet_EmailMessageEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailMessageEntitySet.add(create_EmailMessageEntity(index));
		return emailMessageEntitySet;
	}
	
	public static void assertEmailMessageEntityExists(Collection<EmailMessageEntity> emailMessageEntityList, EmailMessageEntity emailMessageEntity) {
		Assert.notNull(emailMessageEntityList, "EmailMessageEntity list must be specified");
		Assert.notNull(emailMessageEntity, "EmailMessageEntity record must be specified");
		Iterator<EmailMessageEntity> iterator = emailMessageEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailMessageEntity emailMessageEntity1 = iterator.next();
			if (emailMessageEntity1.equals(emailMessageEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailMessageEntity should exist: "+emailMessageEntity);
	}
	
	public static void assertEmailMessageEntityCorrect(EmailMessageEntity emailMessageEntity) {
		long index = emailMessageEntity.getId() / 10L;
		assertObjectCorrect("Content", emailMessageEntity.getContent(), index);
		assertObjectCorrect("Subject", emailMessageEntity.getSubject(), index);
		assertObjectCorrect("Timestamp", emailMessageEntity.getTimestamp(), index);
		assertObjectCorrect("SourceId", emailMessageEntity.getSourceId(), index);
		assertObjectCorrect("SmtpHost", emailMessageEntity.getSmtpHost(), index);
		assertObjectCorrect("SmtpPort", emailMessageEntity.getSmtpPort(), index);
		assertObjectCorrect("SendAsHtml", emailMessageEntity.getSendAsHtml(), index);
		assertEmailAddressEntityCorrect(emailMessageEntity.getFromAddress());
		assertEmailAddressListEntityCorrect(emailMessageEntity.getToAddresses());
		assertEmailAddressListEntityCorrect(emailMessageEntity.getBccAddresses());
		assertEmailAddressListEntityCorrect(emailMessageEntity.getCcAddresses());
		assertEmailAddressListEntityCorrect(emailMessageEntity.getReplytoAddresses());
		assertEmailAddressListEntityCorrect(emailMessageEntity.getAdminAddresses());
		assertAttachmentEntityCorrect(emailMessageEntity.getAttachments());
	}
	
	public static void assertEmailMessageEntityCorrect(Collection<EmailMessageEntity> emailMessageEntityList) {
		Assert.isTrue(emailMessageEntityList.size() == 2, "EmailMessageEntity count not correct");
		Iterator<EmailMessageEntity> iterator = emailMessageEntityList.iterator();
		while (iterator.hasNext()) {
			EmailMessageEntity emailMessageEntity = iterator.next();
			assertEmailMessageEntityCorrect(emailMessageEntity);
		}
	}
	
	public static void assertSameEmailMessageEntity(EmailMessageEntity emailMessageEntity1, EmailMessageEntity emailMessageEntity2) {
		assertSameEmailMessageEntity(emailMessageEntity1, emailMessageEntity2, false, "");
	}
	
	public static void assertSameEmailMessageEntity(EmailMessageEntity emailMessageEntity1, EmailMessageEntity emailMessageEntity2, String message) {
		assertSameEmailMessageEntity(emailMessageEntity1, emailMessageEntity2, false, message);
	}
	
	public static void assertSameEmailMessageEntity(EmailMessageEntity emailMessageEntity1, EmailMessageEntity emailMessageEntity2, boolean checkIds) {
		assertSameEmailMessageEntity(emailMessageEntity1, emailMessageEntity2, checkIds, "");
	}
	
	public static void assertSameEmailMessageEntity(EmailMessageEntity emailMessageEntity1, EmailMessageEntity emailMessageEntity2, boolean checkIds, String message) {
		assertObjectExists("EmailMessageEntity1", emailMessageEntity1);
		assertObjectExists("EmailMessageEntity2", emailMessageEntity2);
		if (checkIds)
			assertObjectEquals("Id", emailMessageEntity1.getId(), emailMessageEntity2.getId(), message);
		assertObjectEquals("Content", emailMessageEntity1.getContent(), emailMessageEntity2.getContent(), message);
		assertObjectEquals("Subject", emailMessageEntity1.getSubject(), emailMessageEntity2.getSubject(), message);
		assertObjectEquals("Timestamp", emailMessageEntity1.getTimestamp(), emailMessageEntity2.getTimestamp(), message);
		assertObjectEquals("SourceId", emailMessageEntity1.getSourceId(), emailMessageEntity2.getSourceId(), message);
		assertObjectEquals("SmtpHost", emailMessageEntity1.getSmtpHost(), emailMessageEntity2.getSmtpHost(), message);
		assertObjectEquals("SmtpPort", emailMessageEntity1.getSmtpPort(), emailMessageEntity2.getSmtpPort(), message);
		assertObjectEquals("SendAsHtml", emailMessageEntity1.getSendAsHtml(), emailMessageEntity2.getSendAsHtml(), message);
		assertSameEmailAddressEntity(emailMessageEntity1.getFromAddress(), emailMessageEntity2.getFromAddress(), checkIds, message);
		assertSameEmailAddressListEntity(emailMessageEntity1.getToAddresses(), emailMessageEntity2.getToAddresses(), checkIds, message);
		assertSameEmailAddressListEntity(emailMessageEntity1.getBccAddresses(), emailMessageEntity2.getBccAddresses(), checkIds, message);
		assertSameEmailAddressListEntity(emailMessageEntity1.getCcAddresses(), emailMessageEntity2.getCcAddresses(), checkIds, message);
		assertSameEmailAddressListEntity(emailMessageEntity1.getReplytoAddresses(), emailMessageEntity2.getReplytoAddresses(), checkIds, message);
		assertSameEmailAddressListEntity(emailMessageEntity1.getAdminAddresses(), emailMessageEntity2.getAdminAddresses(), checkIds, message);
		assertSameAttachmentEntity(emailMessageEntity1.getAttachments(), emailMessageEntity2.getAttachments(), checkIds, message);
	}
	
	public static void assertSameEmailMessageEntity(Collection<EmailMessageEntity> emailMessageEntityList1, Collection<EmailMessageEntity> emailMessageEntityList2) {
		assertSameEmailMessageEntity(emailMessageEntityList1, emailMessageEntityList2, false, "");
	}
	
	public static void assertSameEmailMessageEntity(Collection<EmailMessageEntity> emailMessageEntityList1, Collection<EmailMessageEntity> emailMessageEntityList2, String message) {
		assertSameEmailMessageEntity(emailMessageEntityList1, emailMessageEntityList2, "");
	}
	
	public static void assertSameEmailMessageEntity(Collection<EmailMessageEntity> emailMessageEntityList1, Collection<EmailMessageEntity> emailMessageEntityList2, boolean checkIds) {
		assertSameEmailMessageEntity(emailMessageEntityList1, emailMessageEntityList2, checkIds, "");
	}
	
	public static void assertSameEmailMessageEntity(Collection<EmailMessageEntity> emailMessageEntityList1, Collection<EmailMessageEntity> emailMessageEntityList2, boolean checkIds, String message) {
		Assert.notNull(emailMessageEntityList1, "EmailMessageEntity list1 must be specified");
		Assert.notNull(emailMessageEntityList2, "EmailMessageEntity list2 must be specified");
		Assert.equals(emailMessageEntityList1.size(), emailMessageEntityList2.size(), "EmailMessageEntity count not equal");
		Iterator<EmailMessageEntity> iterator1 = emailMessageEntityList1.iterator();
		while (iterator1.hasNext()) {
			EmailMessageEntity emailMessageEntity1 = iterator1.next();
			Iterator<EmailMessageEntity> iterator2 = emailMessageEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				EmailMessageEntity emailMessageEntity2 = iterator2.next();
				if (emailMessageEntity1.getId().equals(emailMessageEntity2.getId())) {
					assertSameEmailMessageEntity(emailMessageEntity1, emailMessageEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "EmailMessage record not found: "+emailMessageEntity1);
		}
	}
	
	public static EmailAccountEntity createEmpty_EmailAccountEntity() {
		EmailAccountEntity emailAccountEntity = new EmailAccountEntity();
		return emailAccountEntity;
	}

	public static EmailAccountEntity create_EmailAccountEntity() {
		EmailAccountEntity emailAccountEntity = create_EmailAccountEntity(1);
		return emailAccountEntity;
	}

	public static EmailAccountEntity create_EmailAccountEntity(long index) {
		EmailAccountEntity emailAccountEntity = createEmpty_EmailAccountEntity();
		emailAccountEntity.setUserId("dummyUserId" + index + counter++);
		emailAccountEntity.setPasswordHash("dummyPasswordHash" + index);
		emailAccountEntity.setPasswordSalt("dummyPasswordSalt" + index);
		emailAccountEntity.setFirstName("dummyFirstName" + index);
		emailAccountEntity.setLastName("dummyLastName" + index);
		emailAccountEntity.setEnabled(false);
		emailAccountEntity.getEmailBoxes().addAll(CommonEntityFixture.createList_EmailBoxEntity(emailAccountEntity, 2));
		emailAccountEntity.setId(10L * index);
		return emailAccountEntity;
	}

	public static List<EmailAccountEntity> createEmptyList_EmailAccountEntity() {
		return new ArrayList<EmailAccountEntity>();
	}

	public static List<EmailAccountEntity> createList_EmailAccountEntity() {
		return createList_EmailAccountEntity(2);
	}

	public static List<EmailAccountEntity> createList_EmailAccountEntity(int size) {
		return createList_EmailAccountEntity(1, size);
	}
	
	public static List<EmailAccountEntity> createList_EmailAccountEntity(long index, int size) {
		List<EmailAccountEntity> emailAccountEntityList = createEmptyList_EmailAccountEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAccountEntityList.add(create_EmailAccountEntity(index));
		return emailAccountEntityList;
	}

	public static Set<EmailAccountEntity> createEmptySet_EmailAccountEntity() {
		return new HashSet<EmailAccountEntity>();
	}
	
	public static Set<EmailAccountEntity> createSet_EmailAccountEntity() {
		return createSet_EmailAccountEntity(2);
	}
	
	public static Set<EmailAccountEntity> createSet_EmailAccountEntity(int size) {
		return createSet_EmailAccountEntity(1, size);
	}
	
	public static Set<EmailAccountEntity> createSet_EmailAccountEntity(long index, int size) {
		Set<EmailAccountEntity> emailAccountEntitySet = createEmptySet_EmailAccountEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailAccountEntitySet.add(create_EmailAccountEntity(index));
		return emailAccountEntitySet;
	}
	
	public static void assertEmailAccountEntityExists(Collection<EmailAccountEntity> emailAccountEntityList, EmailAccountEntity emailAccountEntity) {
		Assert.notNull(emailAccountEntityList, "EmailAccountEntity list must be specified");
		Assert.notNull(emailAccountEntity, "EmailAccountEntity record must be specified");
		Iterator<EmailAccountEntity> iterator = emailAccountEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailAccountEntity emailAccountEntity1 = iterator.next();
			if (emailAccountEntity1.equals(emailAccountEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailAccountEntity should exist: "+emailAccountEntity);
	}
	
	public static void assertEmailAccountEntityCorrect(EmailAccountEntity emailAccountEntity) {
		long index = emailAccountEntity.getId() / 10L;
		assertObjectCorrect("UserId", emailAccountEntity.getUserId(), index);
		assertObjectCorrect("PasswordHash", emailAccountEntity.getPasswordHash(), index);
		assertObjectCorrect("PasswordSalt", emailAccountEntity.getPasswordSalt(), index);
		assertObjectCorrect("FirstName", emailAccountEntity.getFirstName(), index);
		assertObjectCorrect("LastName", emailAccountEntity.getLastName(), index);
		assertObjectCorrect("Enabled", emailAccountEntity.getEnabled(), index);
		assertEmailBoxEntityCorrect(emailAccountEntity.getEmailBoxes());
	}
	
	public static void assertEmailAccountEntityCorrect(Collection<EmailAccountEntity> emailAccountEntityList) {
		Assert.isTrue(emailAccountEntityList.size() == 2, "EmailAccountEntity count not correct");
		Iterator<EmailAccountEntity> iterator = emailAccountEntityList.iterator();
		while (iterator.hasNext()) {
			EmailAccountEntity emailAccountEntity = iterator.next();
			assertEmailAccountEntityCorrect(emailAccountEntity);
		}
	}
	
	public static void assertSameEmailAccountEntity(EmailAccountEntity emailAccountEntity1, EmailAccountEntity emailAccountEntity2) {
		assertSameEmailAccountEntity(emailAccountEntity1, emailAccountEntity2, false, "");
	}
	
	public static void assertSameEmailAccountEntity(EmailAccountEntity emailAccountEntity1, EmailAccountEntity emailAccountEntity2, String message) {
		assertSameEmailAccountEntity(emailAccountEntity1, emailAccountEntity2, false, message);
	}
	
	public static void assertSameEmailAccountEntity(EmailAccountEntity emailAccountEntity1, EmailAccountEntity emailAccountEntity2, boolean checkIds) {
		assertSameEmailAccountEntity(emailAccountEntity1, emailAccountEntity2, checkIds, "");
	}
	
	public static void assertSameEmailAccountEntity(EmailAccountEntity emailAccountEntity1, EmailAccountEntity emailAccountEntity2, boolean checkIds, String message) {
		assertObjectExists("EmailAccountEntity1", emailAccountEntity1);
		assertObjectExists("EmailAccountEntity2", emailAccountEntity2);
		if (checkIds)
			assertObjectEquals("Id", emailAccountEntity1.getId(), emailAccountEntity2.getId(), message);
		assertObjectEquals("UserId", emailAccountEntity1.getUserId(), emailAccountEntity2.getUserId(), message);
		assertObjectEquals("PasswordHash", emailAccountEntity1.getPasswordHash(), emailAccountEntity2.getPasswordHash(), message);
		assertObjectEquals("PasswordSalt", emailAccountEntity1.getPasswordSalt(), emailAccountEntity2.getPasswordSalt(), message);
		assertObjectEquals("FirstName", emailAccountEntity1.getFirstName(), emailAccountEntity2.getFirstName(), message);
		assertObjectEquals("LastName", emailAccountEntity1.getLastName(), emailAccountEntity2.getLastName(), message);
		assertObjectEquals("Enabled", emailAccountEntity1.getEnabled(), emailAccountEntity2.getEnabled(), message);
		assertSameEmailBoxEntity(emailAccountEntity1.getEmailBoxes(), emailAccountEntity2.getEmailBoxes(), checkIds, message);
	}
	
	public static void assertSameEmailAccountEntity(Collection<EmailAccountEntity> emailAccountEntityList1, Collection<EmailAccountEntity> emailAccountEntityList2) {
		assertSameEmailAccountEntity(emailAccountEntityList1, emailAccountEntityList2, false, "");
	}
	
	public static void assertSameEmailAccountEntity(Collection<EmailAccountEntity> emailAccountEntityList1, Collection<EmailAccountEntity> emailAccountEntityList2, String message) {
		assertSameEmailAccountEntity(emailAccountEntityList1, emailAccountEntityList2, "");
	}
	
	public static void assertSameEmailAccountEntity(Collection<EmailAccountEntity> emailAccountEntityList1, Collection<EmailAccountEntity> emailAccountEntityList2, boolean checkIds) {
		assertSameEmailAccountEntity(emailAccountEntityList1, emailAccountEntityList2, checkIds, "");
	}
	
	public static void assertSameEmailAccountEntity(Collection<EmailAccountEntity> emailAccountEntityList1, Collection<EmailAccountEntity> emailAccountEntityList2, boolean checkIds, String message) {
		Assert.notNull(emailAccountEntityList1, "EmailAccountEntity list1 must be specified");
		Assert.notNull(emailAccountEntityList2, "EmailAccountEntity list2 must be specified");
		Assert.equals(emailAccountEntityList1.size(), emailAccountEntityList2.size(), "EmailAccountEntity count not equal");
		Iterator<EmailAccountEntity> iterator1 = emailAccountEntityList1.iterator();
		while (iterator1.hasNext()) {
			EmailAccountEntity emailAccountEntity1 = iterator1.next();
			Iterator<EmailAccountEntity> iterator2 = emailAccountEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				EmailAccountEntity emailAccountEntity2 = iterator2.next();
				if (emailAccountEntity1.getId().equals(emailAccountEntity2.getId())) {
					assertSameEmailAccountEntity(emailAccountEntity1, emailAccountEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "EmailAccount record not found: "+emailAccountEntity1);
		}
	}
	
	public static EmailBoxEntity createEmpty_EmailBoxEntity() {
		EmailBoxEntity emailBoxEntity = new EmailBoxEntity();
		return emailBoxEntity;
	}

	public static EmailBoxEntity create_EmailBoxEntity(EmailAccountEntity emailAccountEntity) {
		EmailBoxEntity emailBoxEntity = create_EmailBoxEntity(emailAccountEntity, 1);
		return emailBoxEntity;
	}

	public static EmailBoxEntity create_EmailBoxEntity(EmailAccountEntity emailAccountEntity, long index) {
		EmailBoxEntity emailBoxEntity = createEmpty_EmailBoxEntity();
		emailBoxEntity.setEmailAccount(emailAccountEntity);
		emailBoxEntity.setType("dummyType" + index);
		emailBoxEntity.setName("dummyName" + index);
		emailBoxEntity.setCreationDate(new Date(1000L + index));
		emailBoxEntity.setLastUpdate(new Date(1000L + index));
		emailBoxEntity.setParentBox(CommonEntityFixture.createEmpty_EmailBoxEntity());
		emailBoxEntity.getMessages().addAll(CommonEntityFixture.createList_EmailMessageEntity(2));
		emailBoxEntity.setId(10L * index);
		return emailBoxEntity;
	}

	public static List<EmailBoxEntity> createEmptyList_EmailBoxEntity() {
		return new ArrayList<EmailBoxEntity>();
	}

	public static List<EmailBoxEntity> createList_EmailBoxEntity(EmailAccountEntity emailAccountEntity) {
		return createList_EmailBoxEntity(emailAccountEntity, 2);
	}

	public static List<EmailBoxEntity> createList_EmailBoxEntity(EmailAccountEntity emailAccountEntity, int size) {
		return createList_EmailBoxEntity(emailAccountEntity, 1, size);
	}
	
	public static List<EmailBoxEntity> createList_EmailBoxEntity(EmailAccountEntity emailAccountEntity, long index, int size) {
		List<EmailBoxEntity> emailBoxEntityList = createEmptyList_EmailBoxEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailBoxEntityList.add(create_EmailBoxEntity(emailAccountEntity, index));
		return emailBoxEntityList;
	}
	
	public static Set<EmailBoxEntity> createEmptySet_EmailBoxEntity() {
		return new HashSet<EmailBoxEntity>();
	}
	
	public static Set<EmailBoxEntity> createSet_EmailBoxEntity(EmailAccountEntity emailAccountEntity) {
		return createSet_EmailBoxEntity(emailAccountEntity, 2);
	}
	
	public static Set<EmailBoxEntity> createSet_EmailBoxEntity(EmailAccountEntity emailAccountEntity, int size) {
		return createSet_EmailBoxEntity(emailAccountEntity, 1, size);
	}
	
	public static Set<EmailBoxEntity> createSet_EmailBoxEntity(EmailAccountEntity emailAccountEntity, long index, int size) {
		Set<EmailBoxEntity> emailBoxEntitySet = createEmptySet_EmailBoxEntity();
		long limit = index + size;
		for (; index < limit; index++)
			emailBoxEntitySet.add(create_EmailBoxEntity(emailAccountEntity, index));
		return emailBoxEntitySet;
	}
	
	public static void assertEmailBoxEntityExists(Collection<EmailBoxEntity> emailBoxEntityList, EmailBoxEntity emailBoxEntity) {
		Assert.notNull(emailBoxEntityList, "EmailBoxEntity list must be specified");
		Assert.notNull(emailBoxEntity, "EmailBoxEntity record must be specified");
		Iterator<EmailBoxEntity> iterator = emailBoxEntityList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			EmailBoxEntity emailBoxEntity1 = iterator.next();
			if (emailBoxEntity1.equals(emailBoxEntity)) {
				return;
			}
		}
		throw new AssertionError("[Assertion failed] - EmailBoxEntity should exist: "+emailBoxEntity);
	}
	
	public static void assertEmailBoxEntityCorrect(EmailBoxEntity emailBoxEntity) {
		long index = emailBoxEntity.getId() / 10L;
		assertObjectCorrect("Type", emailBoxEntity.getType(), index);
		assertObjectCorrect("Name", emailBoxEntity.getName(), index);
		assertObjectCorrect("CreationDate", emailBoxEntity.getCreationDate(), index);
		assertObjectCorrect("LastUpdate", emailBoxEntity.getLastUpdate(), index);
		assertEmailBoxEntityCorrect(emailBoxEntity.getParentBox());
		assertEmailMessageEntityCorrect(emailBoxEntity.getMessages());
	}
	
	public static void assertEmailBoxEntityCorrect(Collection<EmailBoxEntity> emailBoxEntityList) {
		Assert.isTrue(emailBoxEntityList.size() == 2, "EmailBoxEntity count not correct");
		Iterator<EmailBoxEntity> iterator = emailBoxEntityList.iterator();
		while (iterator.hasNext()) {
			EmailBoxEntity emailBoxEntity = iterator.next();
			assertEmailBoxEntityCorrect(emailBoxEntity);
		}
	}
	
	public static void assertSameEmailBoxEntity(EmailBoxEntity emailBoxEntity1, EmailBoxEntity emailBoxEntity2) {
		assertSameEmailBoxEntity(emailBoxEntity1, emailBoxEntity2, false, "");
	}
	
	public static void assertSameEmailBoxEntity(EmailBoxEntity emailBoxEntity1, EmailBoxEntity emailBoxEntity2, String message) {
		assertSameEmailBoxEntity(emailBoxEntity1, emailBoxEntity2, false, message);
	}
	
	public static void assertSameEmailBoxEntity(EmailBoxEntity emailBoxEntity1, EmailBoxEntity emailBoxEntity2, boolean checkIds) {
		assertSameEmailBoxEntity(emailBoxEntity1, emailBoxEntity2, checkIds, "");
	}
	
	public static void assertSameEmailBoxEntity(EmailBoxEntity emailBoxEntity1, EmailBoxEntity emailBoxEntity2, boolean checkIds, String message) {
		assertObjectExists("EmailBoxEntity1", emailBoxEntity1);
		assertObjectExists("EmailBoxEntity2", emailBoxEntity2);
		if (checkIds)
			assertObjectEquals("Id", emailBoxEntity1.getId(), emailBoxEntity2.getId(), message);
		assertObjectEquals("Type", emailBoxEntity1.getType(), emailBoxEntity2.getType(), message);
		assertObjectEquals("Name", emailBoxEntity1.getName(), emailBoxEntity2.getName(), message);
		assertObjectEquals("CreationDate", emailBoxEntity1.getCreationDate(), emailBoxEntity2.getCreationDate(), message);
		assertObjectEquals("LastUpdate", emailBoxEntity1.getLastUpdate(), emailBoxEntity2.getLastUpdate(), message);
		assertSameEmailAccountEntity(emailBoxEntity1.getEmailAccount(), emailBoxEntity2.getEmailAccount(), checkIds, message);
		assertSameEmailBoxEntity(emailBoxEntity1.getParentBox(), emailBoxEntity2.getParentBox(), checkIds, message);
		assertSameEmailMessageEntity(emailBoxEntity1.getMessages(), emailBoxEntity2.getMessages(), checkIds, message);
	}
	
	public static void assertSameEmailBoxEntity(Collection<EmailBoxEntity> emailBoxEntityList1, Collection<EmailBoxEntity> emailBoxEntityList2) {
		assertSameEmailBoxEntity(emailBoxEntityList1, emailBoxEntityList2, false, "");
	}
	
	public static void assertSameEmailBoxEntity(Collection<EmailBoxEntity> emailBoxEntityList1, Collection<EmailBoxEntity> emailBoxEntityList2, String message) {
		assertSameEmailBoxEntity(emailBoxEntityList1, emailBoxEntityList2, "");
	}
	
	public static void assertSameEmailBoxEntity(Collection<EmailBoxEntity> emailBoxEntityList1, Collection<EmailBoxEntity> emailBoxEntityList2, boolean checkIds) {
		assertSameEmailBoxEntity(emailBoxEntityList1, emailBoxEntityList2, checkIds, "");
	}
	
	public static void assertSameEmailBoxEntity(Collection<EmailBoxEntity> emailBoxEntityList1, Collection<EmailBoxEntity> emailBoxEntityList2, boolean checkIds, String message) {
		Assert.notNull(emailBoxEntityList1, "EmailBoxEntity list1 must be specified");
		Assert.notNull(emailBoxEntityList2, "EmailBoxEntity list2 must be specified");
		Assert.equals(emailBoxEntityList1.size(), emailBoxEntityList2.size(), "EmailBoxEntity count not equal");
		Iterator<EmailBoxEntity> iterator1 = emailBoxEntityList1.iterator();
		while (iterator1.hasNext()) {
			EmailBoxEntity emailBoxEntity1 = iterator1.next();
			Iterator<EmailBoxEntity> iterator2 = emailBoxEntityList2.iterator();
			boolean isFound = false;
			while (iterator2.hasNext()) {
				EmailBoxEntity emailBoxEntity2 = iterator2.next();
				if (emailBoxEntity1.getId().equals(emailBoxEntity2.getId())) {
					assertSameEmailBoxEntity(emailBoxEntity1, emailBoxEntity2, checkIds, message);
					isFound = true;
					break;
				}
			}
			
			//if (!isFound)
			//	System.out.println();
			Assert.isTrue(isFound, "EmailBox record not found: "+emailBoxEntity1);
		}
	}
	
}

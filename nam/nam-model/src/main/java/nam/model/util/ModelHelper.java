package nam.model.util;

import java.util.Collection;
import java.util.Iterator;

import nam.model.Skin;
import nam.model.User;

import org.aries.Assert;


public class ModelHelper {

	private static nam.model.ObjectFactory objectFactory = new nam.model.ObjectFactory();


	/*
	 * Skin related
	 */

	public static void assertSkinRecordsEqual(Skin skin1, Skin skin2) {
		Assert.notNull(skin1, "Skin1 must be specified");
		Assert.notNull(skin2, "Skin2 must be specified");
		Assert.equals(skin1.getName(), skin2.getName(), "Skin name field not equal: author1="+skin1.getName()+", author2="+skin2.getName());
	}

	public static void assertSkinRecordListEquals(Collection<Skin> expectedSkins, Collection<Skin> actualSkins) {
		Assert.equals(expectedSkins.size(), actualSkins.size(), "Specified lists have unequal size");
		Iterator<Skin> expectedSkinsIterator = expectedSkins.iterator();
		Iterator<Skin> actualSkinsIterator = actualSkins.iterator();
		while (expectedSkinsIterator.hasNext() && actualSkinsIterator.hasNext()) {
			Skin expectedSkin = expectedSkinsIterator.next();
			Skin actualSkin = actualSkinsIterator.next();
			assertSkinRecordsEqual(expectedSkin, actualSkin);
		}
	}

	public static boolean isSkinRecordsEqual(Skin skin1, Skin skin2) {
		Assert.notNull(skin1, "Skin1 must be specified");
		Assert.notNull(skin2, "Skin2 must be specified");
		if (!skin1.getName().equals(skin2.getName()))
			return false;
		return true;
	}

	/*
	 * User related
	 */

	public static void assertUserRecordsEqual(User user1, User user2) {
		Assert.notNull(user1, "User1 must be specified");
		Assert.notNull(user2, "User2 must be specified");
		Assert.equals(user1.getId(), user2.getId(), "User id field not equal: id1="+user1.getId()+", id2="+user2.getId());
		Assert.equals(user1.getUserName(), user2.getUserName(), "User userName field not equal: userName1="+user1.getUserName()+", userName2="+user2.getUserName());
		//Assert.equals(user1.getFirstName(), user2.getFirstName(), "User firstName field not equal: firstName1="+user1.getFirstName()+", firstName2="+user2.getFirstName());
		//Assert.equals(user1.getLastName(), user2.getLastName(), "User lastName field not equal: lastName1="+user1.getLastName()+", lastName2="+user2.getLastName());
	}

	public static void assertUserRecordListEquals(Collection<User> expectedUsers, Collection<User> actualUsers) {
		Assert.equals(expectedUsers.size(), actualUsers.size(), "Specified lists have unequal size");
		Iterator<User> expectedUsersIterator = expectedUsers.iterator();
		Iterator<User> actualUsersIterator = actualUsers.iterator();
		while (expectedUsersIterator.hasNext() && actualUsersIterator.hasNext()) {
			User expectedUser = expectedUsersIterator.next();
			User actualUser = actualUsersIterator.next();
			assertUserRecordsEqual(expectedUser, actualUser);
		}
	}

	public static boolean isUserRecordsEqual(User user1, User user2) {
		Assert.notNull(user1, "User1 must be specified");
		Assert.notNull(user2, "User2 must be specified");
		if (!user1.getId().equals(user2.getId()))
			return false;
		if (!user1.getUserName().equals(user2.getUserName()))
			return false;
		if (!user1.getPersonName().equals(user2.getPersonName()))
			return false;
		return true;
	}
	

}

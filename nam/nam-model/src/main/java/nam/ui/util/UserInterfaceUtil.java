package nam.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import nam.model.Application;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.UserInterface;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;
import org.aries.util.NameUtil;
import org.aries.util.ObjectUtil;
import org.aries.util.Validator;


public class UserInterfaceUtil extends BaseUtil {
	
	public static String getKey(UserInterface userInterface) {
		return userInterface.getNamespace() + "/" + userInterface.getName();
	}
	
	public static String getLabel(UserInterface userInterface) {
		return NameUtil.capName(userInterface.getName());
	}
	
	public static boolean isEmpty(UserInterface userInterface) {
		if (userInterface == null)
			return true;
		boolean status = false;
		return status;
	}
	
	public static boolean isEmpty(Collection<UserInterface> userInterfaceList) {
		if (userInterfaceList == null  || userInterfaceList.size() == 0)
			return true;
		Iterator<UserInterface> iterator = userInterfaceList.iterator();
		while (iterator.hasNext()) {
			UserInterface userInterface = iterator.next();
			if (!isEmpty(userInterface))
				return false;
		}
		return true;
	}
	
	public static String toString(UserInterface userInterface) {
		if (isEmpty(userInterface))
			return "UserInterface: [uninitialized] "+userInterface.toString();
		String text = userInterface.toString();
		return text;
	}
	
	public static String toString(Collection<UserInterface> userInterfaceList) {
		if (isEmpty(userInterfaceList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<UserInterface> iterator = userInterfaceList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			UserInterface userInterface = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(userInterface);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static UserInterface create() {
		UserInterface userInterface = new UserInterface();
		initialize(userInterface);
		return userInterface;
	}
	
	public static void initialize(UserInterface userInterface) {
		//nothing for now
	}
	
	public static boolean validate(UserInterface userInterface) {
		if (userInterface == null)
			return false;
		Validator validator = Validator.getValidator();
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<UserInterface> userInterfaceList) {
		Validator validator = Validator.getValidator();
		Iterator<UserInterface> iterator = userInterfaceList.iterator();
		while (iterator.hasNext()) {
			UserInterface userInterface = iterator.next();
			//TODO break or accumulate?
			validate(userInterface);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<UserInterface> userInterfaceList) {
		Collections.sort(userInterfaceList, createUserInterfaceComparator());
	}
	
	public static Collection<UserInterface> sortRecords(Collection<UserInterface> userInterfaceCollection) {
		List<UserInterface> list = new ArrayList<UserInterface>(userInterfaceCollection);
		Collections.sort(list, createUserInterfaceComparator());
		return list;
	}
	
	public static Comparator<UserInterface> createUserInterfaceComparator() {
		return new Comparator<UserInterface>() {
			public int compare(UserInterface userInterface1, UserInterface userInterface2) {
				int status = userInterface1.compareTo(userInterface2);
				return status;
			}
		};
	}
	
	public static UserInterface clone(UserInterface userInterface) {
		if (userInterface == null)
			return null;
		UserInterface clone = create();
		clone.setType(userInterface.getType());
		clone.setName(ObjectUtil.clone(userInterface.getName()));
		clone.setLabel(ObjectUtil.clone(userInterface.getLabel()));
		clone.setGroupId(ObjectUtil.clone(userInterface.getGroupId()));
		clone.setArtifactId(ObjectUtil.clone(userInterface.getArtifactId()));
		clone.setVersion(ObjectUtil.clone(userInterface.getVersion()));
		clone.setNamespace(ObjectUtil.clone(userInterface.getNamespace()));
		clone.setDescription(ObjectUtil.clone(userInterface.getDescription()));
		clone.setCreator(ObjectUtil.clone(userInterface.getCreator()));
		clone.setCreationDate(ObjectUtil.clone(userInterface.getCreationDate()));
		clone.setLastUpdate(ObjectUtil.clone(userInterface.getLastUpdate()));
		return clone;
	}
	
	public static List<UserInterface> clone(List<UserInterface> userInterfaceList) {
		List<UserInterface> cloneList = new ArrayList<UserInterface>();
		Iterator<UserInterface> iterator = userInterfaceList.iterator();
		while (iterator.hasNext()) {
			UserInterface userInterface = iterator.next();
			UserInterface clone = clone(userInterface);
			cloneList.add(clone);
		}
		return cloneList;
	}

	public static Application getApplication(Collection<Project> projectList, UserInterface userInterface) {
		Collection<Application> applications = ProjectUtil.getApplications(projectList);
		Iterator<Application> iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Collection<UserInterface> userInterfaces = ApplicationUtil.getUserInterfaces(application);
			Iterator<UserInterface> iterator2 = userInterfaces.iterator();
			while (iterator2.hasNext()) {
				UserInterface userInterface2 = iterator2.next();
				if (userInterface.equals(userInterface2)) {
					return application;
				}
			}
		}
		return null;
	}

}

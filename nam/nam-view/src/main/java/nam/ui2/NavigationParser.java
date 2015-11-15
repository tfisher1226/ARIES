package nam.ui2;

import java.net.URL;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@ManagedBean
@ApplicationScoped
@Named("navigationParser")
public class NavigationParser {
	
	private List<GroupDescriptor> groupList;

	
	@XmlRootElement(name = "root")
	private static final class GroupsHolder {
		private List<GroupDescriptor> groups;

		@XmlElement(name = "group")
		public List<GroupDescriptor> getGroups() {
			return groups;
		}

		@SuppressWarnings("unused")
		public void setGroups(List<GroupDescriptor> groups) {
			this.groups = groups;
		}
	}

	public synchronized List<GroupDescriptor> getGroupList() {
		if (groupList == null) {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			URL resource = ccl.getResource("navigation.xml");
			try {
				JAXBContext context = JAXBContext.newInstance(GroupsHolder.class);
				GroupsHolder groupsHolder = (GroupsHolder) context.createUnmarshaller().unmarshal(resource);
				groupList = groupsHolder.getGroups();
			} catch (JAXBException e) {
				throw new FacesException(e.getMessage(), e);
			}
		}

		return groupList;
	}

}

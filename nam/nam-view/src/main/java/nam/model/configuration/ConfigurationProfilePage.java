package nam.model.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.aries.ui.AbstractWizardPage;

import nam.model.Configuration;
import nam.model.Tier;


@SuppressWarnings("serial")
public class ConfigurationProfilePage extends AbstractWizardPage<Configuration> {

	private Configuration configuration;
	
	private List<SelectItem> selectedProfiles;
	
	private Tier selectedTier = Tier.ALL;
	

	public ConfigurationProfilePage() {
		//setTitle("Select desired functionlity.");
		selectedProfiles = new ArrayList<SelectItem>();
		//setOwner(owner);
		reset();
	}

	protected void reset() {
		refreshSelectedProfiles();
		selectedProfiles = new ArrayList<SelectItem>();
	}

	public void initialize(Configuration configuration) {
		//fieldListManager = BeanContext.get("fieldListManager");
		//fieldListManager.refresh(element);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setConfiguration(configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Tier getSelectedTier() {
		return selectedTier;
	}

	public void setSelectedTier(Tier selectedTier) {
		this.selectedTier = selectedTier;
	}

	public List<SelectItem> getSelectedProfiles() {
		return selectedProfiles;
	}

	public void setSelectedProfiles(List<SelectItem> selectedProfiles) {
		this.selectedProfiles.addAll(selectedProfiles);
	}
	
//	public SelectItem[] getSelectedProfiles() {
//		SelectItem[] array = createSelectItem[selectedProfiles.size()];
//		return selectedProfiles.toArray(array);
//	}

//	public List<SelectItem> getSelectedProfiles() {
//		return selectedProfiles;
//	}

//	public void setSelectedProfiles(SelectItem[] selectedProfiles) {
//		this.selectedProfiles.addAll(Arrays.asList(selectedProfiles));
//	}
//
//	public void setSelectedProfiles(List<SelectItem> selectedProfiles) {
//		this.selectedProfiles.addAll(selectedProfiles);
//	}

//	public void setSelectedProfiles(List<SelectItem> selectedProfiles) {
//		this.selectedProfiles.addAll(selectedProfiles);
//	}

	public List<SelectItem> getAvailableProfiles() {
		if (selectedTier == null)
			selectedTier = Tier.ALL;
		switch (selectedTier) {
		case ALL: return getAllProfiles();
		case UI: return getUITierProfiles();
		case CLIENT: return getClientTierProfiles();
		case SERVICE: return getServiceTierProfiles();
		case MESSAGING: return getMessagingTierProfiles();
		case PERSISTENCE: return getPersistenceTierProfiles();
		default: return null;
		}
	}

	protected List<SelectItem> getAllProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("ActiveMQ"));
		list.add(createSelectItem("EhCache"));
		list.add(createSelectItem("Hibernate"));
		list.add(createSelectItem("HornetQ"));
		list.add(createSelectItem("HSQL"));
		list.add(createSelectItem("JAXB"));
		list.add(createSelectItem("JAXWS"));
		list.add(createSelectItem("JMS"));
		list.add(createSelectItem("JPA"));
		list.add(createSelectItem("JSF"));
		list.add(createSelectItem("MySQL"));
		list.add(createSelectItem("Richfaces"));
		list.add(createSelectItem("UDDI"));
		return list; 
	}

	protected List<SelectItem> getUITierProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("JSF"));
		list.add(createSelectItem("Seam"));
		list.add(createSelectItem("Richfaces"));
//		list.addAll(selectedProfiles);
		return list; 
	}

	protected List<SelectItem> getClientTierProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("UDDI"));
		list.add(createSelectItem("JMS"));
		list.add(createSelectItem("JAXWS"));
//		list.addAll(selectedProfiles);
		return list; 
	}

	protected List<SelectItem> getServiceTierProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("EhCache"));
		list.add(createSelectItem("JAXB"));
		list.add(createSelectItem("JAXWS"));
		list.add(createSelectItem("Seam"));
//		list.addAll(selectedProfiles);
		return list; 
	}

	protected List<SelectItem> getMessagingTierProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("ActiveMQ"));
		list.add(createSelectItem("HornetQ"));
//		list.addAll(selectedProfiles);
		return list; 
	}

	protected List<SelectItem> getPersistenceTierProfiles() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(createSelectItem("EhCache"));
		list.add(createSelectItem("Hibernate"));
		list.add(createSelectItem("HSQL"));
		list.add(createSelectItem("MySQL"));
//		list.addAll(selectedProfiles);
		return list; 
	}

	private SelectItem createSelectItem(String name) {
		SelectItem selectItem = new SelectItem(name);
		return selectItem;
	}

	protected void refreshSelectedProfiles() {
		// TODO Auto-generated method stub
		
	}

}

package nam.model.configuration;

import nam.model.Configuration;
import nam.ui.design.AbstractDomainElementManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;


@Startup
@AutoCreate
@Name("configurationManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class ConfigurationManager extends AbstractDomainElementManager {

	//private static Log log = LogFactory.getLog(ConfigurationManager.class);

	@Out(required=false, value="nam.configuration")
	private Configuration transientConfiguration;

	@Out(required = false, value="nam.selectedConfiguration")
	private Configuration selectedConfiguration = new Configuration();

	@Out(required=true, value="nam.configurationSetupPanel")
	private ConfigurationSetupPage configurationSetupPanel;

	@Out(required=true, value="nam.configurationProfilePanel")
	private ConfigurationProfilePage configurationProfilePanel;

	
	public ConfigurationManager() {
		configurationSetupPanel = new ConfigurationSetupPage();
		configurationProfilePanel = new ConfigurationProfilePage();
	}

	public void initialize(Configuration configuration) {
		configurationSetupPanel.initialize(configuration);
		configurationProfilePanel.initialize(configuration);
		configurationSetupPanel.setVisible(true);
		configurationProfilePanel.setVisible(true);
		this.transientConfiguration = configuration;
	}

	protected void refresh() {
		//nothing for now
	}

	public Configuration getConfiguration() {
		return transientConfiguration;
	}

	public Configuration getSelectedConfiguration() {
		return selectedConfiguration;
	}

	public void setConfiguration(Configuration configuration) {
		configurationSetupPanel.setConfiguration(configuration);
		configurationProfilePanel.setConfiguration(configuration);
		this.selectedConfiguration = configuration;
	}

	public void selectConfiguration(Configuration configuration) {
		this.selectedConfiguration = configuration;
		initialize(configuration);
	}
	
	@Override
	public String getTitle() {
		if (transientConfiguration != null)
			return "Configuration: "+transientConfiguration.getName();
		return null;
	}

	@Override
	protected String getRefreshEvent() {
		return "nam.configurationsChanged";
	}

//	public void setSelection(String name) {
//		List<Configuration> configurations = ProjectUtil.getConfigurations(project);
//		Iterator<Configuration> iterator = configurations.iterator();
//		while (iterator.hasNext()) {
//			Configuration configuration = iterator.next();
//			if (configuration.getArtifactId().equals(name)) {
//				setConfiguration(configuration);
//				break;
//			}
//		}
//	}

	@Begin(join=true)
	public void editConfiguration() {
		editConfiguration(transientConfiguration);
	}
	
	@Begin(join=true)
	public void editConfiguration(Configuration configuration) {
		setTitle("Configuration: "+configuration.getName()+"");
		initialize(configuration);
	}

	public void saveConfiguration() {
		if (configurationSetupPanel.isValid() &&
			configurationProfilePanel.isValid()) {
			saveProject();
		}
	}

	@Override
	public void submit() {
		super.submit();
		saveConfiguration();
		refresh();
	}
	
}

package nam.model.configuration;

import nam.model.Configuration;
import nam.ui.design.AbstractDomainElementWizard;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import admin.User;


@AutoCreate
@Name("configurationWizard")
@Scope(ScopeType.CONVERSATION)
@SuppressWarnings("serial")
public class ConfigurationWizard extends AbstractDomainElementWizard<Configuration> {

	//private static Log log = LogFactory.getLog(ConfigurationWizard.class);

	@In(required = false)
	private User user;

	@Out(required=false, value="nam.configuration")
	private Configuration configuration;

	@Out(required=true, value="nam.configurationSetupPage")
	private ConfigurationSetupPage configurationSetupPage;

	@Out(required=true, value="nam.configurationProfilePage")
	private ConfigurationProfilePage configurationProfilePage;

	
	public ConfigurationWizard() {
		configurationSetupPage = new ConfigurationSetupPage();
		configurationProfilePage = new ConfigurationProfilePage();
		addPage(configurationSetupPage);
		addPage(configurationProfilePage);
		setTitle("New Configuration");
		reset();
	}

	protected String getUrlContext() {
		return "/nam/design/configuration/newConfiguration.xhtml";
	}
	
	public void initialize(Configuration configuration) {
		setOrigin(getSelectionContext().getUrl());
		configurationSetupPage.initialize(configuration);
		configurationProfilePage.initialize(configuration);
		configurationSetupPage.setVisible(true);
		configurationProfilePage.setVisible(true);
		this.configuration = configuration;
		configuration.setOwner(user.getUserName());
	}
	
	@Override
	public void reset() {
		super.reset();
		configurationProfilePage.reset();
	}

	@Override
	public String refresh() {
		return super.refresh();
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public String getRefreshEvent() {
		return "nam.configurationsChanged";
	}
	
	@Begin(join=true)
	public void newConfiguration() {
		setTitle("New Configuration");
		Messages messages = BeanContext.get("messages");
		messages.info("annotationWizard", "Enter information for new Configuration.");
		initialize(new Configuration());
	}

	@Begin(join=true)
	public void editConfiguration() {
		editConfiguration(configuration);
	}
	
	@Begin(join=true)
	public void editConfiguration(Configuration configuration) {
		setTitle("Configuration: "+configuration.getName()+"");
		initialize(configuration);
	}
	
	public void saveConfiguration() {
//		List<Configuration> configurations = ProjectUtil.getConfigurations(project);
//		if (!configurations.contains(configuration))
//			configurations.add(configuration);
//		saveProject();
	}
	
	@Override
	public String finish() {
		super.finish();
		saveConfiguration();
		return super.finish();
	}
	
}

package nam.model.configuration;

import nam.model.Configuration;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;


@SuppressWarnings("serial")
public class ConfigurationSetupPage extends AbstractWizardPage<Configuration> {

	//private static Log log = LogFactory.getLog(ConfigurationSetupPage.class);

	private Configuration configuration;

	
	public ConfigurationSetupPage() {
		setTitle("Specify configuration information.");
		setNextEnabled(true);
		setFinishEnabled(false);
		//setOwner(owner);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void initialize(Configuration configuration) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setConfiguration(configuration);
	}

	public void validate() {
		if (configuration == null) {
			validator.missing("Configuration");
		} else {
			if (StringUtils.isEmpty(configuration.getGroupId()))
				validator.missing("Group ID");
			if (StringUtils.isEmpty(configuration.getArtifactId()))
				validator.missing("Artifact ID");
			if (StringUtils.isEmpty(configuration.getVersion()))
				validator.missing("Version");
		}
	}
	
}

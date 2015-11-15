package nam.model.repository;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Repository;
import nam.model.util.RepositoryUtil;


@SessionScoped
@Named("repositoryConfigurationSection")
public class RepositoryRecord_ConfigurationSection extends AbstractWizardPage<Repository> implements Serializable {
	
	private Repository repository;
	
	
	public RepositoryRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Repository getRepository() {
		return repository;
	}
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	
	@Override
	public void initialize(Repository repository) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setRepository(repository);
	}
	
	@Override
	public void validate() {
		if (repository == null) {
			validator.missing("Repository");
		} else {
		}
	}
	
}

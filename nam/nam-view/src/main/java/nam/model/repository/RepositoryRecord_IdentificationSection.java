package nam.model.repository;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Repository;
import nam.model.util.RepositoryUtil;


@SessionScoped
@Named("repositoryIdentificationSection")
public class RepositoryRecord_IdentificationSection extends AbstractWizardPage<Repository> implements Serializable {
	
	private Repository repository;
	
	
	public RepositoryRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
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

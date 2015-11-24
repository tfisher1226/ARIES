package nam.model.persistence;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Persistence;
import nam.model.util.PersistenceUtil;


@SessionScoped
@Named("persistenceEntitysSection")
public class PersistenceRecord_EntitiesSection extends AbstractWizardPage<Persistence> implements Serializable {
	
	private Persistence persistence;
	
	
	public PersistenceRecord_EntitiesSection() {
		setName("Entitys");
		setUrl("entitys");
	}
	
	
	public Persistence getPersistence() {
		return persistence;
	}
	
	public void setPersistence(Persistence persistence) {
		this.persistence = persistence;
	}
	
	@Override
	public void initialize(Persistence persistence) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPersistence(persistence);
	}
	
	@Override
	public void validate() {
		if (persistence == null) {
			validator.missing("Persistence");
		} else {
		}
	}
	
}
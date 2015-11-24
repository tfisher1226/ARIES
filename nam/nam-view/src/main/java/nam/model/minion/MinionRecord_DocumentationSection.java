package nam.model.minion;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Minion;
import nam.model.util.MinionUtil;


@SessionScoped
@Named("minionDocumentationSection")
public class MinionRecord_DocumentationSection extends AbstractWizardPage<Minion> implements Serializable {
	
	private Minion minion;
	
	
	public MinionRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Minion getMinion() {
		return minion;
	}
	
	public void setMinion(Minion minion) {
		this.minion = minion;
	}
	
	@Override
	public void initialize(Minion minion) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setMinion(minion);
	}
	
	@Override
	public void validate() {
		if (minion == null) {
			validator.missing("Minion");
		} else {
		}
	}
	
}
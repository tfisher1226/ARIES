package nam.model._import;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Import;
import nam.model.util.ImportUtil;


@SessionScoped
@Named("importOverviewSection")
public class ImportRecord_OverviewSection extends AbstractWizardPage<Import> implements Serializable {
	
	private Import _import;
	
	
	public ImportRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Import getImport() {
		return _import;
	}
	
	public void setImport(Import _import) {
		this._import = _import;
	}
	
	@Override
	public void initialize(Import _import) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setImport(_import);
	}
	
	@Override
	public void validate() {
		if (_import == null) {
			validator.missing("Import");
		} else {
		}
	}
	
}

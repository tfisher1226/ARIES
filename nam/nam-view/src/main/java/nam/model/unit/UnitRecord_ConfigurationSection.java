package nam.model.unit;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Unit;
import nam.model.util.UnitUtil;


@SessionScoped
@Named("unitConfigurationSection")
public class UnitRecord_ConfigurationSection extends AbstractWizardPage<Unit> implements Serializable {
	
	private Unit unit;
	
	
	public UnitRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Override
	public void initialize(Unit unit) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUnit(unit);
	}
	
	@Override
	public void validate() {
		if (unit == null) {
			validator.missing("Unit");
		} else {
		}
	}
	
}

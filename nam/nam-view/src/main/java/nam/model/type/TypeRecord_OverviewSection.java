package nam.model.type;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Type;
import nam.model.util.TypeUtil;


@SessionScoped
@Named("typeOverviewSection")
public class TypeRecord_OverviewSection extends AbstractWizardPage<Type> implements Serializable {
	
	private Type type;
	
	
	public TypeRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public void initialize(Type type) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setType(type);
	}
	
	@Override
	public void validate() {
		if (type == null) {
			validator.missing("Type");
		} else {
		}
	}
	
}

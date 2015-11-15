package nam.model.adapter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@SessionScoped
@Named("adapterConfigurationSection")
public class AdapterRecord_ConfigurationSection extends AbstractWizardPage<Adapter> implements Serializable {
	
	private Adapter adapter;
	
	
	public AdapterRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Adapter getAdapter() {
		return adapter;
	}
	
	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}
	
	@Override
	public void initialize(Adapter adapter) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setAdapter(adapter);
	}
	
	@Override
	public void validate() {
		if (adapter == null) {
			validator.missing("Adapter");
		} else {
		}
	}
	
}

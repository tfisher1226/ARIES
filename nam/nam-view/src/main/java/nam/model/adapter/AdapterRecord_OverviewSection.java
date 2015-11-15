package nam.model.adapter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@SessionScoped
@Named("adapterOverviewSection")
public class AdapterRecord_OverviewSection extends AbstractWizardPage<Adapter> implements Serializable {
	
	private Adapter adapter;
	
	
	public AdapterRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
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

package nam.model.adapter;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Adapter;
import nam.model.util.AdapterUtil;


@SessionScoped
@Named("adapterIdentificationSection")
public class AdapterRecord_IdentificationSection extends AbstractWizardPage<Adapter> implements Serializable {
	
	private Adapter adapter;
	
	
	public AdapterRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
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

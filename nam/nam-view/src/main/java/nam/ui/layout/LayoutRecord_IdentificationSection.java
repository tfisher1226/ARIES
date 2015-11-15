package nam.ui.layout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutIdentificationSection")
public class LayoutRecord_IdentificationSection extends AbstractWizardPage<Layout> implements Serializable {
	
	private Layout layout;
	
	
	public LayoutRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Layout getLayout() {
		return layout;
	}
	
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	
	@Override
	public void initialize(Layout layout) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setLayout(layout);
	}
	
	@Override
	public void validate() {
		if (layout == null) {
			validator.missing("Layout");
		} else {
		}
	}
	
}

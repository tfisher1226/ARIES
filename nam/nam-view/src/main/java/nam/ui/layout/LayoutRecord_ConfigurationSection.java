package nam.ui.layout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutConfigurationSection")
public class LayoutRecord_ConfigurationSection extends AbstractWizardPage<Layout> implements Serializable {
	
	private Layout layout;
	
	
	public LayoutRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
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
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
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

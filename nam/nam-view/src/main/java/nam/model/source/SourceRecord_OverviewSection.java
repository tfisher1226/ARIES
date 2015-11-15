package nam.model.source;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Source;
import nam.model.util.SourceUtil;


@SessionScoped
@Named("sourceOverviewSection")
public class SourceRecord_OverviewSection extends AbstractWizardPage<Source> implements Serializable {
	
	private Source source;
	
	
	public SourceRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Source getSource() {
		return source;
	}
	
	public void setSource(Source source) {
		this.source = source;
	}
	
	@Override
	public void initialize(Source source) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setSource(source);
	}
	
	@Override
	public void validate() {
		if (source == null) {
			validator.missing("Source");
		} else {
		}
	}
	
}

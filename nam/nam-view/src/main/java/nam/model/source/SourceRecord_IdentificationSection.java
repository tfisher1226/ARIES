package nam.model.source;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Source;
import nam.model.util.SourceUtil;


@SessionScoped
@Named("sourceIdentificationSection")
public class SourceRecord_IdentificationSection extends AbstractWizardPage<Source> implements Serializable {
	
	private Source source;
	
	
	public SourceRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
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
		setPopulateVisible(true);
		setPopulateEnabled(true);
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

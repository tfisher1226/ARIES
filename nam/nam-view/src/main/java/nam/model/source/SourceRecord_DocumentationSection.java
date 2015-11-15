package nam.model.source;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Source;
import nam.model.util.SourceUtil;


@SessionScoped
@Named("sourceDocumentationSection")
public class SourceRecord_DocumentationSection extends AbstractWizardPage<Source> implements Serializable {
	
	private Source source;
	
	
	public SourceRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
		setBackEnabled(true);
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

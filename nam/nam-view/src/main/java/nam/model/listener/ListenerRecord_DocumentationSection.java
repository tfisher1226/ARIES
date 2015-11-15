package nam.model.listener;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Listener;
import nam.model.util.ListenerUtil;


@SessionScoped
@Named("listenerDocumentationSection")
public class ListenerRecord_DocumentationSection extends AbstractWizardPage<Listener> implements Serializable {
	
	private Listener listener;
	
	
	public ListenerRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Listener getListener() {
		return listener;
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void initialize(Listener listener) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setListener(listener);
	}
	
	@Override
	public void validate() {
		if (listener == null) {
			validator.missing("Listener");
		} else {
		}
	}
	
}

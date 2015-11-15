package nam.ui.invocation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.ui.Invocation;
import nam.ui.util.InvocationUtil;


@SessionScoped
@Named("invocationDocumentationSection")
public class InvocationRecord_DocumentationSection extends AbstractWizardPage<Invocation> implements Serializable {
	
	private Invocation invocation;
	
	
	public InvocationRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Invocation getInvocation() {
		return invocation;
	}
	
	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}
	
	@Override
	public void initialize(Invocation invocation) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setInvocation(invocation);
	}
	
	@Override
	public void validate() {
		if (invocation == null) {
			validator.missing("Invocation");
		} else {
		}
	}
	
}

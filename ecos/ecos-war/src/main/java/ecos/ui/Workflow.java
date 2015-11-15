package ecos.ui;

import org.aries.runtime.BeanContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;


public class Workflow {

	@Factory(scope = ScopeType.CONVERSATION)
	public Workflow createWorkflow() {
		Workflow workflow = new Workflow();
		return workflow;
	}
	
	public Workflow getWorkflow() {
		Workflow workflow = BeanContext.getFromEvent("workflow");
		return workflow;
	}

}

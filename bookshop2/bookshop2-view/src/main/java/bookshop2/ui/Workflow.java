package bookshop2.ui;

import org.aries.runtime.BeanContext;


public class Workflow {

	//SEAM @Factory(scope = ScopeType.CONVERSATION)
	public Workflow createWorkflow() {
		Workflow workflow = new Workflow();
		return workflow;
	}
	
	public Workflow getWorkflow() {
		Workflow workflow = BeanContext.getFromEvent("workflow");
		return workflow;
	}

}

package org.aries.workflow.action;

//import org.jbpm.graph.def.ActionHandler;
//import org.jbpm.graph.def.Node;
//import org.jbpm.graph.exe.ExecutionContext;
//import org.jbpm.graph.exe.Token;


public class ExceptionHandler /*implements ActionHandler*/ {

	private static final long serialVersionUID = 1L;

	private String destination;

//	@Override
//	public void execute(ExecutionContext context) throws Exception {
//		Token token = context.getToken();
//		Node sourceNode = token.getNode();
//		Throwable throwable = context.getException();
//		System.out.println("Caught Exception " + throwable.getMessage() + " in node " + sourceNode.getName());
//		Node targetNode = context.getProcessDefinition().getNode(destination);
//		token.setNode(targetNode);
//		token.signal();
//	}

}

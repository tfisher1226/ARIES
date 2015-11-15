package org.aries.workflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
//import org.jbpm.graph.def.ActionHandler;
//import org.jbpm.graph.def.Node;
//import org.jbpm.graph.def.Transition;
//import org.jbpm.graph.exe.ExecutionContext;
//import org.jbpm.graph.exe.Token;


/**  * specifies configurable parallel execution behaviour.  *   */
public class ParallelStart /*implements ActionHandler*/ {

	private static final long serialVersionUID = 1L;

	// The name of the variable holding loop count
	private String loopCount;
	// The name of the variable current count (stored in the token variable scope)
	private String currentCount;
	// The list of the array variables. Each array has to be of the of the loop count  	// size. An appropriate element is stored in the token context scope
	private Map<String, String> variables;


//	public void execute(ExecutionContext context) {
//
//		// Load loop counter
//		int counter;
//		try {
//			counter = (Integer)context.getContextInstance().getVariable(loopCount);
//		}
//		catch(Exception e){
//			System.out.println("Can't load loop counter ");
//			e.printStackTrace();
//			return;
//		}
//
//		// Build splitter map
//		Map<String, Object[]> splitMap = null;
//		if((variables != null) && (variables.size() > 0)){
//			splitMap = new HashMap<String, Object[]>();
//			Set <String> keys = variables.keySet();
//			for(String v : keys){
//				try{
//					Object[] values = (Object[])context.getContextInstance().getVariable(v);
//					splitMap.put(v, values);
//				}
//				catch(Exception e){
//					System.out.println("Can't load values for variable " + v);
//					e.printStackTrace();
//					continue;
//				}
//			}
//		}
//
//		// Get default transition
//		Token token = context.getToken();
//		Node currentNode = token.getNode();
//		Transition tr = currentNode.getDefaultLeavingTransition();
//
//		// execute default transition loop times
//		for(int i = 0; i < counter; i++){
//			Token childToken = new Token(token, tr.getName()+ i);
//			ExecutionContext childExecutionContext = new ExecutionContext(childToken);
//			childExecutionContext.getContextInstance().setVariable(FONT COLOR="#0000FF">currentCount, i, childToken);
//			if(splitMap != null){
//
//				// Populate variables
//				Set<String> keys = splitMap.keySet();
//				for(String v : keys){
//					try{
//						Object value = splitMap.get(v)[i];
//						childExecutionContext.getContextInstance().setVariable(variables.get(v), value, childToken);
//					}
//					catch(Exception e){
//						System.out.println("Can't load " + i + " element for variable " + v);
//						e.printStackTrace();
//						continue;
//					}
//				}
//			}
//
//			// Start token execution
//			currentNode.leave(childExecutionContext);
//		}
//	}
}

package org.aries.workflow;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
//import org.hibernate.LockMode;
//import org.hibernate.Session;
//import org.jbpm.JbpmContext;
//import org.jbpm.graph.def.ActionHandler;
//import org.jbpm.graph.exe.ExecutionContext;
//import org.jbpm.graph.exe.Token;


public class ParallelEnd /*implements ActionHandler*/ {

	private static final long serialVersionUID = 1L;

	/** specifies wether what type of hibernate lock should be acquired.  null value defaults to LockMode.force */
	String parentLockMode;
	// The name of the variable holding loop count
	private String loopCount;
	// The name of the variable current count (stored in the token variable scope)
	private String currentCount;
	// The list of the array variables. Each array has to be of the of the loop count
	// size. An appropriate element is stored in the token context scope
	private Map<String, String> variables;

//	public void execute(ExecutionContext context) {
//
//		// Get current token
//		Token token = context.getToken();
//		boolean isAbleToReactivateParent = token.isAbleToReactivateParent();
//
//		if (!token.hasEnded()) {
//			token.end(false);
//		}
//
//		// if this token is not able to reactivate the parent,  		// we don't need to check anything
//		if ( isAbleToReactivateParent ) {
//
//			// the token arrived in the join and can only reactivate  			// the parent once
//			token.setAbleToReactivateParent(false);
//			Token parentToken = token.getParent();
//			// Build replies array
//			if((variables != null) && (variables.size() > 0)){
//				// current counter
//				int current = (integer)context.getContextInstance().getVariable(currentCount,token);
//				// for every variable
//				Set<String> keys = variables.keySet();
//				for (String v : keys) {
//					Object value = context.getContextInstance().getVariable(v, token);
//					String res = variables.get(v);
//					Object[] values = (Object[]) context.getContextInstance().getVariable(res);
//					if (values == null) {
//						// Get array size
//						int loopSize = (integer) context.getContextInstance().getVariable(loopCount);
//						// Get variable type
//						Class t = value.getClass();
//						// Create an array of a variable type
//						values = (Object[])Array.newInstance(t, loopSize);
//					}
//					values[current] = value;
//					context.getContextInstance().setVariable(res, values);
//				}
//			}
//
//			if ( parentToken != null ) {
//
//				// Get context and lock session
//				JbpmContext jbpmContext = context.getJbpmContext();
//				Session session = (jbpmContext != null ? jbpmContext.getSession() : null);
//				if (session != null) {
//					LockMode lockMode = LockMode.forCE;
//					if (parentLockMode!= null) {
//						lockMode = LockMode.parse(parentLockMode);
//					}
//					session.lock(parentToken, lockMode);
//				}
//
//				// Check if we can reactivate parent
//				boolean reactivateParent = mustParentBeReactivated(parentToken, parentToken.getChildren().keySet().iterator() );
//
//				// if the parent token needs to be reactivated from this join node
//				if (reactivateParent) {
//
//					// write to all child tokens that the parent is already reactivated
//					Iterator iter = parentToken.getChildren().values().iterator();
//					while ( iter.hasNext() ) {
//						((Token)iter.next()).setAbleToReactivateParent( false );
//					}
//
//					// write to all child tokens that the parent is already reactivated
//					ExecutionContext parentContext = new ExecutionContext(parentToken);
//					token.getNode().leave(parentContext);
//				}
//			}
//		}
//	}
//
//	public boolean mustParentBeReactivated(Token parentToken, Iterator childTokenNameIterator) {
//
//		boolean reactivateParent = true;
//		while ( (childTokenNameIterator.hasNext())
//				&& (reactivateParent) ){
//			String concurrentTokenName = (String) childTokenNameIterator.next();
//
//			Token concurrentToken = parentToken.getChild( concurrentTokenName );
//
//			if (concurrentToken.isAbleToReactivateParent()) {
//				reactivateParent = false;
//			}
//		}
//		return reactivateParent;
//	}

}

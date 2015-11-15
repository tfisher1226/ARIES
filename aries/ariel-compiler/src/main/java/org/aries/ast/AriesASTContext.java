package org.aries.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nam.model.Operation;
import nam.model.Result;

import org.aries.ast.node.ImportNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.MethodNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.ParticipantNode;
import org.aries.ast.node.ReceiveNode;


public class AriesASTContext {

	public static List<ImportNode> importNodes;

	public static List<NetworkNode> networkNodes;

	public static NetworkNode networkNode;

	public static ParticipantNode participantNode;

	public static ReceiveNode receiveNode;

	public static MethodNode methodNode;

	public static MessageNode messageNode;
	
	public static Operation operation;
	
	private static Map<String, Result> activeVariablesInScope;

	
	public static void clearActiveVariablesInScope() {
		activeVariablesInScope = new HashMap<String, Result>();
	}

	public static Result getActiveVariableInScope(String name) {
		return activeVariablesInScope.get(name);
	}

	public static void addActiveVariableInScope(Result result) {
		activeVariablesInScope.put(result.getName(), result);
	}
	
}

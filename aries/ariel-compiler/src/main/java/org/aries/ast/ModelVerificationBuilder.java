package org.aries.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Callback;
import nam.model.Element;
import nam.model.Field;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ElementUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.ast.node.CommandNode;
import org.aries.ast.node.InvokeNode;
import org.aries.ast.node.MessageNode;
import org.aries.ast.node.MethodNode;
import org.aries.ast.node.NetworkNode;
import org.aries.ast.node.ParameterNode;
import org.aries.ast.node.ParticipantNode;
import org.aries.ast.node.ReceiveNode;
import org.aries.util.NameUtil;
import org.aries.validate.Checkpoint;
import org.aries.validate.Checkpoints;
import org.aries.validate.Condition;

import aries.generation.engine.GenerationContext;
import aries.generation.engine.GeneratorEngine;


public class ModelVerificationBuilder {

	private GenerationContext context;

	private GeneratorEngine engine;

	private ParticipantNode participantNode;

	private ParameterNode parameterNode;

	private MessageNode messageNode;

	private Set<String> visitedTargets;
	

	public ModelVerificationBuilder(GeneratorEngine engine) {
		this.context = engine.getContext(); 
		this.engine = engine;
	}

	public Map<String, Checkpoints> buildRuntimeChecksForProjects(List<Project> projects) {
		Map<String, Checkpoints> map = new HashMap<String, Checkpoints>();
		Iterator<Project> iterator = projects.iterator();
		while (iterator.hasNext()) {
			Project project = iterator.next();
			map.putAll(buildRuntimeChecksForProject(project));
		}
		return map;
	}
	
	public Map<String, Checkpoints> buildRuntimeChecksForProject(Project project) {
		Map<String, Checkpoints> map = new HashMap<String, Checkpoints>();
		
		Set<Module> serviceModules = ProjectUtil.getServiceModules(project);
		Iterator<Module> iterator = serviceModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			visitedTargets = new HashSet<String>();
			Checkpoints checkpoints = buildRuntimeChecksForModule(module);
			String key = module.getName();
			map.put(key, checkpoints);
		}
		return map;
	}

	protected Checkpoints buildRuntimeChecksForModule(Module module) {
		Checkpoints checkpoints = new Checkpoints();
		List<Service> services = ModuleUtil.getServices(module);
		List<Callback> callbacks = ModuleUtil.getCallbacks(module);
		checkpoints.getCheckpoints().addAll(buildRuntimeChecksForServices(services));
		checkpoints.getCheckpoints().addAll(buildRuntimeChecksForServices(callbacks));
		return checkpoints;
	}

	protected <T extends Service> List<Checkpoint> buildRuntimeChecksForServices(List<T> services) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		Iterator<T> iterator = services.iterator();
		while (iterator.hasNext()) {
			T service = iterator.next();
			checkpoints.addAll(buildRuntimeChecksForService(service));
			checkpoints.addAll(buildRuntimeChecksForCallbacks(ServiceUtil.getCallbacks(service)));
		}
		return checkpoints;
	}
	
	protected List<Checkpoint> buildRuntimeChecksForService(Service service) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		List<Operation> operations = ServiceUtil.getOperations(service);
		Iterator<Operation> iterator = operations.iterator();
		while (iterator.hasNext()) {
			Operation operation = iterator.next();
			checkpoints.addAll(buildRuntimeChecksForOperation(operation));
		}
		return checkpoints;
	}
	
	protected List<Checkpoint> buildRuntimeChecksForOperation(Operation operation) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			Checkpoint checkpoint = buildRuntimeCheckForParameter(parameter);
			if (checkpoint != null)
				checkpoints.add(checkpoint);
		}
		return checkpoints;
	}
	
	protected Checkpoint buildRuntimeCheckForParameter(Parameter parameter) {
		return buildRuntimeCheck(parameter.getName());
	}
	
	protected Checkpoint buildRuntimeCheckForParameter(String name) {
		if (visitedTargets.contains(name))
			return null;
		visitedTargets.add(name);
		Checkpoint checkpoint = new Checkpoint();
		checkpoint.setEnabled(true);
		checkpoint.setForced(false);
		checkpoint.setName(NameUtil.uncapName(name));
		checkpoint.getConditions().add(buildNotNullCondition(name));
		checkpoint.getConditions().addAll(buildNotEmptyConditions(name));
		return checkpoint;
	}
	
	protected List<Checkpoint> buildRuntimeChecksForCallbacks(List<Callback> callbacks) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		Iterator<Callback> iterator = callbacks.iterator();
		while (iterator.hasNext()) {
			Callback callback = iterator.next();
			checkpoints.addAll(buildRuntimeChecksForService(callback));
		}
		return checkpoints;
	}

	
	
	/*
	 * AST specific
	 */
	
	public Map<String, Checkpoints> buildRuntimeChecksForProtocolNode(NetworkNode protocolNode) {
		Map<String, Checkpoints> map = new HashMap<String, Checkpoints>();
		List<ParticipantNode> participantNodes = protocolNode.getParticipantNodes();
		Iterator<ParticipantNode> iterator = participantNodes.iterator();
		while (iterator.hasNext()) {
			participantNode = iterator.next();
			String key = NameUtil.capName(participantNode.getName()).toLowerCase();
			Checkpoints checkpoints = buildRuntimeChecksForParticipantNode(participantNode);
			map.put(key, checkpoints);
		}
		return map;
	}

	protected Checkpoints buildRuntimeChecksForParticipantNode(ParticipantNode participantNode) {
		Checkpoints checkpoints = new Checkpoints();
		checkpoints.getCheckpoints().addAll(buildRuntimeChecksForCommandNodes(participantNode.getCommandNodes()));
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForCommandNodes(List<CommandNode> commandNodes) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		Iterator<CommandNode> iterator = commandNodes.iterator();
		while (iterator.hasNext()) {
			CommandNode commandNode = iterator.next();
			if (commandNode instanceof InvokeNode) {
				checkpoints.addAll(buildRuntimeChecksForInvokeNode((InvokeNode) commandNode));
			}
			if (commandNode instanceof ReceiveNode) {
				checkpoints.addAll(buildRuntimeChecksForReceiveNode((ReceiveNode) commandNode));
			}
			if (commandNode instanceof MethodNode) {
				checkpoints.addAll(buildRuntimeChecksForMethodNode((MethodNode) commandNode));
			}
		}
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForInvokeNode(InvokeNode invokeNode) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		checkpoints.addAll(buildRuntimeChecksForParameterNodes(invokeNode.getParameterNodes()));
		checkpoints.addAll(buildRuntimeChecksForMessageNodes(invokeNode.getMessageNodes()));
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForReceiveNode(ReceiveNode receiveNode) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		checkpoints.addAll(buildRuntimeChecksForParameterNodes(receiveNode.getParameterNodes()));
		checkpoints.addAll(buildRuntimeChecksForCommandNodes(receiveNode.getCommandNodes()));
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForMethodNode(MethodNode methodNode) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		checkpoints.addAll(buildRuntimeChecksForCommandNodes(methodNode.getCommandNodes()));
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForParameterNodes(List<ParameterNode> parameterNodes) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		Iterator<ParameterNode> iterator = parameterNodes.iterator();
		while (iterator.hasNext()) {
			parameterNode = iterator.next();
			checkpoints.add(buildRuntimeChecksForParameterNode(parameterNode));
		}
		parameterNode = null;
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForMessageNodes(List<MessageNode> messageNodes) {
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		Iterator<MessageNode> iterator = messageNodes.iterator();
		while (iterator.hasNext()) {
			messageNode = iterator.next();
			checkpoints.addAll(buildRuntimeChecksForMessageNode(messageNode));
		}
		messageNode = null;
		return checkpoints;
	}

	protected List<Checkpoint> buildRuntimeChecksForMessageNode(MessageNode messageNode) {
//		if (messageNode.getName().equalsIgnoreCase("bookAvailable"))
//			System.out.println();
		List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		checkpoints.add(buildRuntimeCheck(messageNode.getName()));
		return checkpoints;
	}

	protected Checkpoint buildRuntimeChecksForParameterNode(ParameterNode parameterNode) {
		return buildRuntimeCheck(parameterNode.getName());
	}
	
	protected Checkpoint buildRuntimeCheck(String name) {
		if (visitedTargets.contains(name))
			return null;
		visitedTargets.add(name);
		Checkpoint checkpoint = new Checkpoint();
		checkpoint.setEnabled(true);
		checkpoint.setForced(false);
		checkpoint.setName(NameUtil.uncapName(name));
		checkpoint.getConditions().add(buildNotNullCondition(name));
		checkpoint.getConditions().addAll(buildNotEmptyConditions(name));
		return checkpoint;
	}

	protected Condition buildNotNullCondition(String name) {
		String nameCapped = NameUtil.capName(name);
		Condition condition = new Condition();
		condition.setEnabled(true);
		condition.setType("notNull");
		condition.setMessage(nameCapped+" must be specified");
		condition.setException("org.aries.AssertionFailure");
		return condition;
	}

	protected <T extends Field> Condition buildNotNullCondition(String name, T field) {
		return buildNotNullCondition(name + "/" + field.getName());
	}
	
	protected List<Condition> buildNotEmptyConditions(String name) {
		List<Condition> conditions = new ArrayList<Condition>();
		Element element = getElementByName(name);
		if (element != null) {
			conditions.addAll(buildNotEmptyConditionsFromFields(name, ElementUtil.getAttributes(element), "notEmpty"));
			conditions.addAll(buildNotEmptyConditionsFromFields(name, ElementUtil.getReferences(element), "notEmpty"));
		}
//		if (parameterNode.getText().equals("item")) {
//		} else if (parameterNode.getText().equals("list")) {
//		} else {
//		}
		return conditions;
	}

	protected <T extends Field> List<Condition> buildNotEmptyConditions(String name, T field) {
		List<Condition> conditions = new ArrayList<Condition>();
		String fieldName = field.getName();
		Element element = getElementByType(field.getType());
		if (element != null) {
			//conditions.add(buildNotEmptyCondition(name, field));
			conditions.addAll(buildNotEmptyConditionsFromFields(name+"/"+fieldName, ElementUtil.getAttributes(element), "notEmpty"));
			conditions.addAll(buildNotEmptyConditionsFromFields(name+"/"+fieldName, ElementUtil.getReferences(element), "notEmpty"));
		}
//		if (parameterNode.getText().equals("item")) {
//		} else if (parameterNode.getText().equals("list")) {
//		} else {
//		}
		return conditions;
	}
	
	protected <T extends Field> List<Condition> buildNotEmptyConditionsFromFields(String name, List<T> fields, String type) {
		List<Condition> conditions = new ArrayList<Condition>();
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		
		Iterator<T> iterator = fields.iterator();
		while (iterator.hasNext()) {
			T field = iterator.next();
			
			boolean isRequired = field.getMinOccurs() > 0;
			boolean isCollection = field.getMaxOccurs() == -1 || field.getMaxOccurs() > 1;
			if (isRequired) {
				String fieldName = field.getName();
				String fieldType = field.getType();
				Element element = getElementByType(fieldType);
				if (element == null || isCollection) {
					Condition condition = new Condition();
					condition.setEnabled(true);
					condition.setType(type);
					if (isCollection) {
						String className = TypeUtil.getClassName(fieldType);
						condition.setMessage(nameCapped+" must include one or more "+className+"(s)");
					} else condition.setMessage(nameCapped+"/"+fieldName+" must be specified");
					condition.setException("org.aries.AssertionFailure");
					condition.setExpression("$"+nameUncapped+"/"+fieldName);
					conditions.add(condition);
				} else {
					conditions.add(buildNotNullCondition(name, field));
					conditions.addAll(buildNotEmptyConditions(name, field));
				}
			}
		}
		return conditions;
	}
	
	protected Element getElementByName(String name) {
		String nameUncapped = NameUtil.uncapName(name);
		Element element = context.findElementByName(nameUncapped, false);
		if (element == null)
			element = context.findElementByName(nameUncapped+"Message", false);
		return element;
	}

	protected Element getElementByType(String type) {
		//TypeUtil.get
		//String nameUncapped = NameUtil.uncapName(name);
		Element element = context.findElementByType(type, false);
		if (element == null)
			element = context.findElementByType(type+"Message", false);
		return element;
	}

}

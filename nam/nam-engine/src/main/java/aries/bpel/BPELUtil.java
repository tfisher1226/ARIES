package aries.bpel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.PortType;

import nam.model.util.TypeUtil;

import org.aries.Assert;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;


public class BPELUtil {

	public static Map<String, Variable> createVariablesMap(Variables variables) {
		Map<String, Variable> map = new HashMap<String, Variable>();
		Iterator<Variable> iterator = variables.getChildren().iterator();
		while (iterator.hasNext()) {
			Variable variable = (Variable) iterator.next();
			String variableName = variable.getName();
			//String variableName = getVariableName(variable);
			map.put(variableName, variable);
		}
		return map;
	}

	//TODO for now assuming only one part per message variable
	public static String getVariableName(Variable variable) {
		Message message = variable.getMessageType();
		if (message != null) {
			@SuppressWarnings("unchecked") Collection<Part> parts = message.getParts().values();
			Iterator<Part> partIterator = parts.iterator();
			while (partIterator.hasNext()) {
				Part part = (Part) partIterator.next();
				return variable.getName()+"_"+part.getName();
			}
		}

		return variable.getName();
	}

	public static Process getBPELProcess(nam.model.Process modelProcess) {
		Process process = (Process) modelProcess.getObject();
		return process; 
	}
	
	public static Process createBPELProcess() {
		Process process = null;

		try { 
			//create resource 
			URI uri = URI.createPlatformResourceURI ("/myProject/myBPEL.bpel", false); 
			ResourceSet rSet = new ResourceSetImpl(); 
			BPELResource bpelResource = (BPELResource) rSet.createResource(uri);

			process = BPELFactory.eINSTANCE.createProcess(); 
			process.setName("myBPEL"); 
			Sequence mySeq = BPELFactory.eINSTANCE.createSequence(); 
			mySeq.setName("mainSequence"); 
			process.setActivity(mySeq);
			bpelResource.getContents().add(process); 
			bpelResource.save(null); 
		
		} catch (Exception e) { 
			e.printStackTrace();
		}

		return process; 
	} 

	public static String getResultClassName(Sequence sequence) {
		Reply replyActivity = BPELUtil.getInitialReplyActivity(sequence);
		Variable variable = replyActivity.getVariable();
		String typeFromVariable = TypeUtil.getTypeFromVariable(variable);
		String resultClassName = TypeUtil.getClassName(typeFromVariable);
		return resultClassName;
	}
	
	public static Receive getInitialReceiveActivity(Process process) {
		Activity activityBlock = process.getActivity();
		List<Activity> activities = null;
		if (activityBlock instanceof Sequence) {
			Sequence sequence = (Sequence) activityBlock;
			activities = sequence.getActivities();
		} else if (activityBlock instanceof Flow) {
			Flow flow = (Flow) activityBlock;
			activities = flow.getActivities();
		}

		Receive receiveActivity = getInitialReceiveActivity(activities);
		//if (receiveActivity)
		return receiveActivity;
	}

	public static Receive getInitialReceiveActivity(Sequence sequence) {
		return getInitialReceiveActivity(sequence.getActivities());
	}
	
	public static Receive getInitialReceiveActivity(List<Activity> activities) {
		Iterator<Activity> iterator = activities.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			Receive receive = getInitialReceiveActivity(activity);
			if (receive != null)
				return receive;
		}
		return null;
	}

	public static Receive getInitialReceiveActivity(Activity activity) {
		if (activity instanceof Receive) {
			return (Receive) activity;

		} else if (activity instanceof Flow) {
			Flow flow = (Flow) activity;
			return getInitialReceiveActivity(flow.getActivities());

		} else if (activity instanceof Scope) {
			Scope scope = (Scope) activity;
			return getInitialReceiveActivity(scope.getActivity());

		} else if (activity instanceof Sequence) {
			Sequence sequence = (Sequence) activity;
			return getInitialReceiveActivity(sequence);
		}
		return null;
	}
	
	public static Reply getInitialReplyActivity(Sequence sequence) {
		return getInitialReplyActivity(sequence.getActivities());
	}
	
	public static Reply getInitialReplyActivity(List<Activity> activities) {
		Iterator<Activity> iterator = activities.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity instanceof Reply)
				return (Reply) activity;
		}
		return null;
	}
	
	public static Activity getInitialActivityFrom(Process process) {
		EList<Activity> activities = getInitialActivitiesFrom(process);
		Assert.notNull(activities, "Initial Activity list must exist");
		Assert.isTrue(activities.size() >= 1, "Initial Activity list must exist");
		Activity firstActivity = activities.get(0);
		return firstActivity;
	}
	
	public static EList<Activity> getInitialActivitiesFrom(Process process) {
		Activity activity = process.getActivity();
		if (activity instanceof Sequence) {
			Sequence sequence = (Sequence) process.getActivity();
			return sequence.getActivities();
		} else if (activity instanceof Flow) {
			Flow flow = (Flow) activity;
			return flow.getActivities();
		}
		return null;
	}

	public static PortType getIncomingPortType(Process process) {
		Receive receiveActivity = getInitialReceiveActivity(process);
		Assert.notNull(receiveActivity, "Initial Receive activity not found");
		org.eclipse.wst.wsdl.PortType portType = receiveActivity.getPortType();
		Assert.notNull(portType, "Incoming PortType not found");
		return portType;
	}

	public static PartnerLink getIncomingPartnerLink(Process process) {
		PartnerLinks partnerLinks = process.getPartnerLinks();
		Sequence sequence = (Sequence) process.getActivity();
		EList<Activity> activities = sequence.getActivities();
		Activity firstActivity = activities.get(0);
		Assert.notNull(firstActivity, "Initial Activity not found");
		Assert.isInstanceOf(Receive.class, firstActivity, "Initial Activity not a ReceiveActivity");
		Receive receiveActivity = (Receive) firstActivity;
		PartnerLink partnerLink = receiveActivity.getPartnerLink();
		//org.eclipse.wst.wsdl.PortType portType = receiveActivity.getPortType();
		
		boolean foundIt = false;
		Iterator<PartnerLink> iterator = partnerLinks.getChildren().iterator();
		while (iterator.hasNext()) {
			PartnerLink partnerLink2 = (PartnerLink) iterator.next();
			if (partnerLink2.equals(partnerLink))
				foundIt = true;
		}
		Assert.isTrue(foundIt, "Incoming PartnerLink not found");
		return partnerLink;
	}
	
}

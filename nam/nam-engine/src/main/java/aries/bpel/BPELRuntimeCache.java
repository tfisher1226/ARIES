package aries.bpel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nam.model.Service;
import nam.service.ServiceLayerHelper;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.wst.wsdl.PortType;


public class BPELRuntimeCache {

	public static BPELRuntimeCache INSTANCE = new BPELRuntimeCache();
	
	private HashMap<String, Process> processByServiceCache = new HashMap<String, Process>();

	private HashMap<String, Process> processByNameCache = new HashMap<String, Process>();


	/*
	 * Process cache related 
	 * ---------------------
	 */

	public void clearProcesses() {
		processByNameCache = new HashMap<String, Process>();
		processByServiceCache = new HashMap<String, Process>();
	}

	public List<Process> getProcesses() {
		return new ArrayList<Process>(processByNameCache.values());
	}

	public boolean hasProcess(Service service) {
		return getProcess(service) != null;
	}

	public Process getProcess(Service service) {
		String processName = ServiceLayerHelper.getProcessName(service.getProcess());
		Process process = BPELRuntimeCache.INSTANCE.getProcessByName(processName);
		return process;
	}

//	public Process getProcess() {
//		Service service = getService();
//		if (service != null)
//			return getProcessByService(service.getPortType());
//		return null;
//	}

	public Process getProcessByName(String processName) {
		return processByNameCache.get(processName);
	}

	public Process getProcessByService(String serviceName) {
		return processByServiceCache.get(serviceName);
	}

	public void addProcess(Process process) {
		Receive initialReceiveActivity = BPELUtil.getInitialReceiveActivity(process);
		//if (initialReceiveActivity == null)
		//	System.out.println();
		PortType portType = initialReceiveActivity.getPortType();
		//addProcess(portType.getQName().toString(), process);
		String serviceName = portType.getQName().toString();
		processByServiceCache.put(serviceName, process);
		processByNameCache.put(process.getName(), process);
	}

//	public void addProcess(String processKey, Process process) {
//	}

//	public void addProcessOLD(Process process) {
//		EList<PartnerLink> partnerLinks = process.getPartnerLinks().getChildren();
//		Iterator<PartnerLink> iterator = partnerLinks.iterator();
//		while (iterator.hasNext()) {
//			PartnerLink partnerLink = iterator.next();
//			System.out.println(">>>" + partnerLink.getMyRole().getName());
//			if (partnerLink.getMyRole() != null) {
//				PortType portType = getPortType(partnerLink, partnerLink.getMyRole().getName());
//				addProcess(portType.getQName().toString(), process);
//			}
//		}
//	}
}

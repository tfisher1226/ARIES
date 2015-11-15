package nam.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Container;
import nam.model.Master;
import nam.model.Minion;
import nam.model.Network;
import nam.model.Pod;
import nam.model.Project;
import nam.model.Service;
import nam.model.Volume;
import nam.model.util.ContainerUtil;
import nam.model.util.MasterUtil;
import nam.model.util.MinionUtil;
import nam.model.util.NetworkUtil;
import nam.model.util.PodUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.VolumeUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


public class NetworkTreeBuilder extends AbstractTreeBuilder implements Serializable {
	
	public void createTree(ModelTreeNode rootNode, Project project) {
		ModelTreeNode projectNode = treeNodeFactory.createProjectNode(project, correlationId);
		treeNodeFactory.addNode(rootNode, projectNode);
		create_NetworkDomains_node(projectNode, ProjectUtil.getNetworks(project));
	}
	
	protected void create_Containers_node(ModelTreeNode parentNode, Collection<Container> containerCollection) {
		if (containerCollection != null && containerCollection.size() > 0) {
			ModelTreeNode containersNode = treeNodeFactory.createFolderNode("Containers");
			treeNodeFactory.addNode(parentNode, containersNode);
			create_Container_nodes(containersNode, containerCollection);
		}
	}
	
	protected void create_Services_node(ModelTreeNode parentNode, Collection<Service> serviceCollection) {
		if (serviceCollection != null && serviceCollection.size() > 0) {
			ModelTreeNode servicesNode = treeNodeFactory.createFolderNode("Services");
			treeNodeFactory.addNode(parentNode, servicesNode);
			create_Service_nodes(servicesNode, serviceCollection);
		}
	}
	
	protected void create_Masters_node(ModelTreeNode parentNode, Collection<Master> masterCollection) {
		if (masterCollection != null && masterCollection.size() > 0) {
			ModelTreeNode controlNodesNode = treeNodeFactory.createFolderNode("Control Nodes");
			treeNodeFactory.addNode(parentNode, controlNodesNode);
			create_Master_nodes(controlNodesNode, masterCollection);
		}
	}
	
	protected void create_NetworkDomains_node(ModelTreeNode parentNode, Collection<Network> networkCollection) {
		if (networkCollection != null && networkCollection.size() > 0) {
			ModelTreeNode networksNode = treeNodeFactory.createFolderNode("Networks");
			treeNodeFactory.addNode(parentNode, networksNode);
			create_NetworkDomain_nodes(networksNode, networkCollection);
			networksNode.setExpanded(true);
		}
	}
	
	protected void create_Minions_node(ModelTreeNode parentNode, Collection<Minion> minionCollection) {
		if (minionCollection != null && minionCollection.size() > 0) {
			ModelTreeNode workerNodesNode = treeNodeFactory.createFolderNode("Worker Nodes");
			treeNodeFactory.addNode(parentNode, workerNodesNode);
			create_Minion_nodes(workerNodesNode, minionCollection);
		}
	}
	
	protected void create_Volumes_node(ModelTreeNode parentNode, Collection<Volume> volumeCollection) {
		if (volumeCollection != null && volumeCollection.size() > 0) {
			ModelTreeNode volumesNode = treeNodeFactory.createFolderNode("Volumes");
			treeNodeFactory.addNode(parentNode, volumesNode);
			create_Volume_nodes(volumesNode, volumeCollection);
		}
	}
	
	protected void create_NetworkDomain_nodes(ModelTreeNode parentNode, Collection<Network> networkCollection) {
		Map<String, List<Network>> map = create_NetworkByGroupId_map(networkCollection);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String groupId = iterator.next();
			ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);
			treeNodeFactory.addNode(parentNode, domainNode);
			List<Network> list = map.get(groupId);
			create_Network_nodes(domainNode, list);
		}
	}
	
	protected Map<String, List<Network>> create_NetworkByGroupId_map(Collection<Network> networkCollection) {
		Map<String, List<Network>> map = new HashMap<String, List<Network>>();
		Iterator<Network> iterator = networkCollection.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			String domain = network.getDomain();
			List<Network> list = map.get(domain);
			if (list == null) {
				list = new ArrayList<Network>();
				map.put(domain, list);
			}
			list.add(network);
		}
		return map;
	}
	
	protected void create_Pod_nodes(ModelTreeNode parentNode, Collection<Pod> podCollection) {
		Iterator<Pod> iterator = podCollection.iterator();
		while (iterator.hasNext()) {
			Pod pod = iterator.next();
			create_Pod_node(parentNode, pod);
		}
	}
	
	protected void create_Pod_node(ModelTreeNode parentNode, Pod pod) {
		ModelTreeNode podNode = treeNodeFactory.createPodNode(parentNode, pod, correlationId);
		create_Services_node(podNode, PodUtil.getServices(pod));
		create_Containers_node(podNode, PodUtil.getContainers(pod));
		create_Volumes_node(podNode, PodUtil.getVolumes(pod));
	}
	
	protected void create_Service_nodes(ModelTreeNode parentNode, Collection<Service> serviceCollection) {
		Iterator<Service> iterator = serviceCollection.iterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			create_Service_node(parentNode, service);
		}
	}
	
	protected void create_Service_node(ModelTreeNode parentNode, Service service) {
		ModelTreeNode serviceNode = treeNodeFactory.createServiceNode(parentNode, service, correlationId);
	}
	
	protected void create_Minion_nodes(ModelTreeNode parentNode, Collection<Minion> minionCollection) {
		Iterator<Minion> iterator = minionCollection.iterator();
		while (iterator.hasNext()) {
			Minion minion = iterator.next();
			create_Minion_node(parentNode, minion);
		}
	}
	
	protected void create_Minion_node(ModelTreeNode parentNode, Minion minion) {
		ModelTreeNode minionNode = treeNodeFactory.createMinionNode(parentNode, minion, correlationId);
		create_Pod_node(minionNode, MinionUtil.getPod(minion));
	}
	
	protected void create_Network_nodes(ModelTreeNode parentNode, Collection<Network> networkCollection) {
		Iterator<Network> iterator = networkCollection.iterator();
		while (iterator.hasNext()) {
			Network network = iterator.next();
			create_Network_node(parentNode, network);
		}
	}
	
	protected void create_Network_node(ModelTreeNode parentNode, Network network) {
		ModelTreeNode networkNode = treeNodeFactory.createNetworkNode(parentNode, network, correlationId);
		create_Masters_node(networkNode, NetworkUtil.getMasters(network));
		create_Minions_node(networkNode, NetworkUtil.getMinions(network));
	}
	
	protected void create_Volume_nodes(ModelTreeNode parentNode, Collection<Volume> volumeCollection) {
		Iterator<Volume> iterator = volumeCollection.iterator();
		while (iterator.hasNext()) {
			Volume volume = iterator.next();
			create_Volume_node(parentNode, volume);
		}
	}
	
	protected void create_Volume_node(ModelTreeNode parentNode, Volume volume) {
		ModelTreeNode volumeNode = treeNodeFactory.createVolumeNode(parentNode, volume, correlationId);
	}
	
	protected void create_Master_nodes(ModelTreeNode parentNode, Collection<Master> masterCollection) {
		Iterator<Master> iterator = masterCollection.iterator();
		while (iterator.hasNext()) {
			Master master = iterator.next();
			create_Master_node(parentNode, master);
		}
	}
	
	protected void create_Master_node(ModelTreeNode parentNode, Master master) {
		ModelTreeNode masterNode = treeNodeFactory.createMasterNode(parentNode, master, correlationId);
	}
	
	protected void create_Container_nodes(ModelTreeNode parentNode, Collection<Container> containerCollection) {
		Iterator<Container> iterator = containerCollection.iterator();
		while (iterator.hasNext()) {
			Container container = iterator.next();
			create_Container_node(parentNode, container);
		}
	}
	
	protected void create_Container_node(ModelTreeNode parentNode, Container container) {
		ModelTreeNode containerNode = treeNodeFactory.createContainerNode(parentNode, container, correlationId);
	}
	
}

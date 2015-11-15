package nam.model.messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Adapter;
import nam.model.Channel;
import nam.model.Element;
import nam.model.Interactor;
import nam.model.Listener;
import nam.model.Messaging;
import nam.model.Project;
import nam.model.Provider;
import nam.model.Router;
import nam.model.util.MessagingUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;


@SuppressWarnings("serial")
public class MessagingOutlineTreeBuilder extends AbstractTreeBuilder implements Serializable {

	protected void populateMap(Element value) {
		correlationId++;
	}

	protected Collection<ModelTreeNode> createTree(Project project) {
		List<ModelTreeNode> nodes = new ArrayList<ModelTreeNode>();
		ModelTreeNode node = treeNodeFactory.createROOTNode();
		createMessagingTree(node, project);
		nodes.add(node);
		return nodes;
	}
	
	protected ModelTreeNode createMessagingTree(ModelTreeNode parentNode, Project project) {
		List<Messaging> messagingBlocks = ProjectUtil.getMessagingBlocks(project);
		Messaging messaging = messagingBlocks.get(0);
		createProviderNodes(parentNode, messaging);
		createAdapterNodes(parentNode, messaging);
		createChannelNodes(parentNode, messaging);
		createListenerNodes(parentNode, messaging);
		createRouterNodes(parentNode, messaging);
		createEndpointNodes(parentNode, messaging);
		return parentNode;
	}

	protected void createProviderNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Provider> providers = MessagingUtil.getProviders(messaging);
		Iterator<Provider> iterator = providers.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Provider provider = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Providers");
			createProviderNode(folderNode, provider);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}
	
	protected ModelTreeNode createProviderNode(ModelTreeNode parentNode, Provider provider) {
		ModelTreeNode treeNode = treeNodeFactory.createMessagingProviderNode(parentNode, provider, correlationId);
		return treeNode;
	}

	protected void createAdapterNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Adapter> adapters = MessagingUtil.getAdapters(messaging);
		Iterator<Adapter> iterator = adapters.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Adapter adapter = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Adapters");
			createAdapterNode(folderNode, adapter);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createAdapterNode(ModelTreeNode parentNode, Adapter adapter) {
		ModelTreeNode treeNode = treeNodeFactory.createMessagingAdapterNode(parentNode, adapter, correlationId);
		return treeNode;
	}

	protected void createChannelNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Channel> channels = MessagingUtil.getChannels(messaging);
		Iterator<Channel> iterator = channels.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Channels");
			createChannelNode(folderNode, channel);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createChannelNode(ModelTreeNode parentNode, Channel channel) {
		ModelTreeNode treeNode = treeNodeFactory.createChannelNode(parentNode, channel, correlationId);
		return treeNode;
	}

	protected void createListenerNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Listener> listeners = MessagingUtil.getListeners(messaging);
		Iterator<Listener> iterator = listeners.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Listeners");
			createListenerNode(folderNode, listener);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createListenerNode(ModelTreeNode parentNode, Listener listener) {
		ModelTreeNode treeNode = treeNodeFactory.createListenerNode(parentNode, listener, correlationId);
		return treeNode;
	}

	protected void createRouterNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Router> routers = MessagingUtil.getRouters(messaging);
		Iterator<Router> iterator = routers.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Router router = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Routers");
			createRouterNode(folderNode, router);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createRouterNode(ModelTreeNode parentNode, Router router) {
		ModelTreeNode treeNode = treeNodeFactory.createRouterNode(parentNode, router, correlationId);
		return treeNode;
	}

	protected void createEndpointNodes(ModelTreeNode parentNode, Messaging messaging) {
		List<Interactor> endpoints = MessagingUtil.getInteractors(messaging);
		Iterator<Interactor> iterator = endpoints.iterator();
		ModelTreeNode folderNode = null;
		while (iterator.hasNext()) {
			Interactor endpoint = iterator.next();
			if (folderNode == null)
				folderNode = treeNodeFactory.createFOLDERNode("Endpoints");
			createEndpointNode(folderNode, endpoint);
		}
		if (folderNode != null)
			parentNode.addNode(folderNode);
	}

	protected ModelTreeNode createEndpointNode(ModelTreeNode parentNode, Interactor endpoint) {
		ModelTreeNode treeNode = treeNodeFactory.createEndpointNode(parentNode, endpoint, correlationId);
		return treeNode;
	}

}

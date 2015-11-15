package nam.ui.workspace;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import nam.model.Element;
import nam.model.Import;
import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractTreeBuilder;
import nam.ui.tree.ModelTreeNode;
import nam.ui.tree.ModelTreeObject;

import org.aries.Assert;
import org.aries.RuntimeContext;
import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class ModelFileTreeBuilder extends AbstractTreeBuilder {

	protected String getGroupKey(Import importedFile) {
		ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
		String contextId = (String) servletContext.getAttribute("contextId");
		GenerationContext context = BeanContext.getFromSession(contextId + ".context");

		String modelLocation = NameUtil.normalizePath(context.getModelLocation());
		String cacheLocation = NameUtil.normalizePath(context.getCacheLocation());
		String path = importedFile.getDir() + "/" + importedFile.getFile();
		String filePath = NameUtil.normalizePath(path);
		if (filePath.startsWith(modelLocation))
			return null;
		filePath = filePath.replace(cacheLocation, "");
		String fileSeparator = System.getProperty("file.separator");
		if (filePath.contains(fileSeparator+fileSeparator))
			filePath = filePath.replace(fileSeparator+fileSeparator, fileSeparator);
		filePath = NameUtil.getBaseName(filePath, fileSeparator);
		filePath = filePath.replace(fileSeparator, "/");
		return filePath;
	}
	
	public static String normalizePath(String filePath) {
		String newPath = NameUtil.normalizePath(filePath);
		String fileSeparator = System.getProperty("file.separator");
		StringTokenizer stringTokenizer = new StringTokenizer(newPath, fileSeparator);
		String normalizePath = "";
		for (int i=0; stringTokenizer.hasMoreElements(); i++) {
			String token = stringTokenizer.nextToken();
			if (token == null || token.equals(""))
				System.out.println();
			if (i+1 == stringTokenizer.countTokens())
				normalizePath += token;
			else normalizePath += token + fileSeparator;
		}
		return normalizePath;
	}

	protected void populateMap(Element value) {
		correlationId++;
	}
	
	public ModelTreeNode createTree(List<Project> projects) {
		initializeTreeBuilder();
		ModelTreeNode rootNode = treeNodeFactory.createROOTNode();
		ModelTreeNode modelsNode = treeNodeFactory.createFOLDERNode("Models");
		treeNodeFactory.addNode(rootNode, modelsNode);
		modelsNode.setExpanded(true);
		createGroupNodesFromModelFiles(modelsNode, projects);
		buildTree(projects);
		populateTreeNodeMap(rootNode);
		return rootNode;
	}
	
	protected void buildTree(List<Project> projects) {
		if (projects != null) {
			Iterator<Project> iterator = projects.iterator();
			while (iterator.hasNext()) {
				Project project = iterator.next();
				buildTree(project);
			}
		}
	}
	
	protected void buildTree(Project project) {
		if (project != null) {
			List<Import> imports = ProjectUtil.getImports(project);
			Iterator<Import> iterator = imports.iterator();
			while (iterator.hasNext()) {
				Import importedFile = iterator.next();
				buildTree(importedFile);
			}
		}
	}
	
	public void buildTree(Import importedFile) {
		String groupKey = getGroupKey(importedFile);
		if (groupKey != null) {
			ModelTreeNode groupNode = groupNodes.get(groupKey);
			Assert.notNull(groupNode, "GroupNode not found: "+groupKey);
			ModelTreeNode node = treeNodeFactory.createModelFileNode(importedFile);
			String nodeId = treeNodeFactory.getNodeId(groupNode, node);
			if (treeNodes.get(nodeId) == null) {
				treeNodes.put(nodeId, node);
				groupNode.addNode(node);
			}
		}
	}
	
	protected void createGroupNodesFromModelFiles(ModelTreeNode parentNode, List<Project> projects) {
		if (projects != null) {
			Iterator<Project> iterator = projects.iterator();
			while (iterator.hasNext()) {
				Project project = iterator.next();
				if (project != null)
					assureGroupNodesExist(parentNode, project);
			}
		}
	}

	protected void assureGroupNodesExist(ModelTreeNode parentNode, Project project) {
		List<Import> imports = ProjectUtil.getImports(project);
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import importedFile = iterator.next();
			assureGroupNodesExist(parentNode, importedFile);
		}
	}

	protected void assureGroupNodesExist(ModelTreeNode parentNode, Import importedFile) {
		String groupKey = getGroupKey(importedFile);
		if (groupKey != null) {
			ModelTreeNode groupNode = groupNodes.get(groupKey);
			if (groupNode == null) {
				groupNode = treeNodeFactory.createGROUPNode(groupKey);
				@SuppressWarnings("unchecked") ModelTreeObject<Import> data = (ModelTreeObject<Import>) groupNode.getData();
				data.setObject(importedFile);
				treeNodeFactory.addNode(parentNode, groupNode);
				groupNodes.put(groupKey, groupNode);
				groupNode.setExpanded(true);
			}
		}
	}
	
}

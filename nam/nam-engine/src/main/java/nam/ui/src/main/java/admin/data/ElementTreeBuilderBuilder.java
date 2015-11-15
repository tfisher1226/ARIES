package nam.ui.src.main.java.admin.data;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nam.model.Application;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.SubNode;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TreeUtil;
import nam.model.util.ViewUtil;
import nam.ui.Tree;
import nam.ui.TreeNode;
import nam.ui.View;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


/**
 * Builds an Element Record Tree Builder Implementation {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementTreeBuilderBuilder extends AbstractElementManagerBuilder {

	private Tree tree;

	private Map<String, TreeNode> folderTreeNodes;

	private Map<String, TreeNode> domainTreeNodes;

	private Map<String, TreeNode> elementTreeNodes;

	
	public ElementTreeBuilderBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
	}

	protected TreeNode getNode(String nodeKey) {
		TreeNode treeNode = elementTreeNodes.get(nodeKey);
		if (treeNode != null)
			return treeNode;
		treeNode = domainTreeNodes.get(nodeKey);
		if (treeNode != null)
			return treeNode;
		treeNode = folderTreeNodes.get(nodeKey);
		if (treeNode != null)
			return treeNode;
		return null;
	}

	public ModelClass buildClass(Type type) throws Exception {
		if (!ElementUtil.isHierarchical(type))
			return null;
		if (type instanceof Element) {
			Element element = (Element) type;
			return buildClass(element);
		}
		return null;
	}
	
	public ModelClass buildClass(Element element) throws Exception {
		String namespace = context.getModule().getNamespace();
		String elementName = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		//String namespacePackageName = ProjectLevelHelper.getPackageName(namespace);
		//String packageName = elementPackageName + ".ui." + elementNameUncapped;
		String packageName = elementPackageName + "." + elementNameUncapped;
		String className = elementName + "TreeBuilder";
		
		View view = context.getModule().getView();
		String treeName = elementNameUncapped + "Tree";
		Tree tree = ViewUtil.getTreeByName(view, treeName);
		if (tree == null)
			return null;

		this.tree = tree;
		buildTreeNodeMaps();

		ModelClass modelClass = createModelClass(namespace, packageName, className);
		modelClass.addImplementedInterface("Serializable");
		modelClass.setParentClassName("AbstractTreeBuilder");
		initializeClass(modelClass, element);
		return modelClass; 
	}

	protected void buildTreeNodeMaps() {
		folderTreeNodes = new HashMap<String, TreeNode>();
		domainTreeNodes = new HashMap<String, TreeNode>();
		elementTreeNodes = new HashMap<String, TreeNode>();
		List<TreeNode> treeNodes = TreeUtil.getTreeNodes(tree);
		Iterator<TreeNode> iterator = treeNodes.iterator();
		while (iterator.hasNext()) {
			TreeNode treeNode = iterator.next();
			String key = treeNode.getType();
			if (treeNode.getFolder()) {
				folderTreeNodes.put(key, treeNode);
			} else if (treeNode.getDomain()) {
				domainTreeNodes.put(key, treeNode);
			} else {
				if (key == null)
					key = treeNode.getElement();
				elementTreeNodes.put(key, treeNode);
			}
		}
	}

	public void initializeClass(ModelClass modelClass, Element element) throws Exception {
		initializeImportedClasses(modelClass, element);
		initializeInstanceOperations(modelClass, element);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Type type) {
		//String namespacePackageName = ProjectLevelHelper.getPackageName(context.getModule().getNamespace());
		String elementPackageName = ModelLayerHelper.getElementPackageName(type);
		String elementClassName = ModelLayerHelper.getElementClassName(type);
		
		modelClass.addImportedClass("nam.ui.design.AbstractTreeBuilder");
		modelClass.addImportedClass("nam.ui.tree.ModelTreeNode");
//		modelClass.addImportedClass(elementPackageName + "." + elementClassName);
//		modelClass.addImportedClass(elementPackageName + ".util." + elementClassName + "Util");

		modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.HashMap");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("java.util.List");
		modelClass.addImportedClass("java.util.Map");
		modelClass.addImportedClass("java.util.Set");
		super.initializeImportedClasses(modelClass);
	}

	/*
	 * Instance operations
	 */

	protected void initializeInstanceOperations(ModelClass modelClass, Element element) throws Exception {
		TreeNode rootNode = TreeUtil.getRootNode(tree);
		modelClass.addInstanceOperation(createOperation_createTree(rootNode, element));
		modelClass.addInstanceOperations(createOperations_createFolderNodes(rootNode, element));
		modelClass.addInstanceOperations(createOperations_createDomainNodes(rootNode, element));
		modelClass.addInstanceOperations(createOperations_createElementNodes(rootNode, element));
	}
	
	protected ModelOperation createOperation_createTree(TreeNode parentNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);

		TreeNode rootNode = TreeUtil.getRootNode(tree);
		if (rootNode == null)
			System.out.println();
		String rootNodeType = rootNode.getType();
		String rootNodeLabel = rootNode.getLabel();
		if (rootNodeLabel == null)
			rootNodeLabel = rootNodeType;
		String rootNodeTypeUncapped = NameUtil.uncapName(rootNodeType);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("createTree");
		modelOperation.addParameter(createParameter("ModelTreeNode", "rootNode"));
		modelOperation.addParameter(createParameter(rootNodeType, rootNodeTypeUncapped));
		
		modelOperation.addImportedClass(elementPackageName + "." + rootNodeType);
		modelOperation.addImportedClass(elementPackageName + ".util." + rootNodeType + "Util");
		
		Buf buf = new Buf();
		buf.putLine2("ModelTreeNode "+rootNodeTypeUncapped+"Node = treeNodeFactory.create"+rootNodeType+"Node("+rootNodeTypeUncapped+", correlationId);");
		buf.putLine2("treeNodeFactory.addNode(rootNode, "+rootNodeTypeUncapped+"Node);");
		
		TreeNode treeNode = rootNode;
		List<SubNode> subNodes = treeNode.getSubNodes();
		Iterator<SubNode> iterator = subNodes.iterator();
		while (iterator.hasNext()) {
			SubNode subNode = iterator.next();
			String fieldName = subNode.getData();
			String childKey = subNode.getType();
			TreeNode childNode = getNode(childKey);
			String childElement = childNode.getElement();
			if (childElement == null)
				childElement = childKey;
			buf.putLine2("create_"+childKey+"_node("+rootNodeTypeUncapped+"Node, "+rootNodeType+"Util.get"+fieldName+"("+rootNodeTypeUncapped+"));");
			//buf.putLine2("create_"+childKey+"_node(projectNode, project.get"+childElement+"());");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected Collection<ModelOperation> createOperations_createFolderNodes(TreeNode parentNode, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();	
		Set<String> keySet = folderTreeNodes.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			TreeNode folderNode = folderTreeNodes.get(key);
			modelOperations.add(createOperation_createFolderNode(folderNode, element));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_createFolderNode(TreeNode treeNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String treeNodeType = treeNode.getType();
		String treeNodeLabel = treeNode.getLabel();
		String treeNodeElement = treeNode.getElement();
		String treeNodeTypeUncapped = NameUtil.uncapName(treeNodeType);
		String treeNodeElementUncapped = NameUtil.uncapName(treeNodeElement);
		String treeNodeLabelUncapped = NameUtil.uncapName(treeNodeLabel);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeType+"_node");
		modelOperation.addParameter(createParameter("ModelTreeNode", "parentNode"));
		modelOperation.addParameter(createParameter("Collection<"+treeNodeElement+">", treeNodeElementUncapped+"Collection"));
		//modelOperation.addParameter(createParameter("Project", "project"));

		Buf buf = new Buf();
		buf.putLine2("if ("+treeNodeElementUncapped+"Collection.size() > 0) {");
		buf.putLine2("	ModelTreeNode "+treeNodeLabelUncapped+"Node = treeNodeFactory.createFolderNode(\""+treeNodeLabel+"\");");
		buf.putLine2("	treeNodeFactory.addNode(parentNode, "+treeNodeLabelUncapped+"Node);");
		
		List<SubNode> subNodes = treeNode.getSubNodes();
		Iterator<SubNode> iterator = subNodes.iterator();
		while (iterator.hasNext()) {
			SubNode subNode = iterator.next();
			String childKey = subNode.getType();
			TreeNode childNode = getNode(childKey);
			if (childNode.getDomain()) {
				String childNodeElement = childNode.getElement();
				String childNodeElementUncapped = NameUtil.uncapName(childNodeElement);
				//String childKeyUncapped = NameUtil.uncapName(childKey);
				//buf.putLine2("Collection<"+childNodeElement+"> "+childNodeElementUncapped+"List = "+childNodeElement+"Util.get"+treeNodeType+"("+treeNodeTypeUncapped+");");
				//buf.putLine2("create_"+childKey+"_nodes("+treeNodeLabelUncapped+"Node, "+childNodeElement+"Util.get"+treeNodeElement+"("+treeNodeElementUncapped+"));");
				buf.putLine2("	create_"+childKey+"_nodes("+treeNodeLabelUncapped+"Node, "+treeNodeElementUncapped+"Collection);");
			} else {
				String childNodeElement = childNode.getElement();
				String childNodeElementUncapped = NameUtil.uncapName(childNodeElement);
				//buf.putLine2("create_"+childNodeElement+"_nodes("+treeNodeLabelUncapped+"Node, "+childNodeElement+"Util.get"+treeNodeElement+"("+treeNodeElementUncapped+"));");
				buf.putLine2("	create_"+childKey+"_nodes("+treeNodeLabelUncapped+"Node, "+treeNodeElementUncapped+"Collection);");
			}
		}
		
		if (treeNode.getExpanded())
			buf.putLine2("	"+treeNodeLabelUncapped+"Node.setExpanded(true);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected Collection<ModelOperation> createOperations_createDomainNodes(TreeNode parentNode, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();	
//		List<String> children = parentNode.getchildren();
//		Iterator<String> iterator = children.iterator();
//		while (iterator.hasNext()) {
//			String childKey = iterator.next();
//			TreeNode domainNode = getNode(childKey);
//			modelOperations.add(createOperation_createDomainNode(domainNode, element));
//		}
		
		Set<String> keySet = domainTreeNodes.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			TreeNode domainNode = domainTreeNodes.get(key);
			modelOperations.add(createOperation_createDomainNodes(domainNode, element));
			modelOperations.add(createOperation_createDomainMap(domainNode, element));
			//modelOperations.add(createOperation_createDomainNode(domainNode, element));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_createDomainNodes(TreeNode treeNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String treeNodeType = treeNode.getType();
		String treeNodeLabel = treeNode.getLabel();
		String treeNodeElement = treeNode.getElement();
		String treeNodeData = treeNode.getData();
		String treeNodeTypeUncapped = NameUtil.uncapName(treeNodeType);
		String treeNodeElementUncapped = NameUtil.uncapName(treeNodeElement);
		String treeNodeDataCapped = NameUtil.capName(treeNodeData);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeType+"_nodes");
		modelOperation.addParameter(createParameter("ModelTreeNode", "parentNode"));
		modelOperation.addParameter(createParameter("Collection<"+treeNodeElement+">", treeNodeElementUncapped+"Collection"));
		//modelOperation.addParameter(createParameter("Project", "project"));

		Buf buf = new Buf();
		//buf.putLine2("Collection<"+treeNodeElement+"> "+treeNodeElementUncapped+"List = "+treeNodeElement+"Util.getModules(modules);");
		buf.putLine2("Map<String, List<"+treeNodeElement+">> map = create_"+treeNodeElement+"By"+treeNodeDataCapped+"_map("+treeNodeElementUncapped+"Collection);");
		buf.putLine2("Set<String> keySet = map.keySet();");
		buf.putLine2("Iterator<String> iterator = keySet.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	String groupId = iterator.next();");
		buf.putLine2("	ModelTreeNode domainNode = treeNodeFactory.createDomainNode(groupId);");
		buf.putLine2("	treeNodeFactory.addNode(parentNode, domainNode);");
		buf.putLine2("	List<"+treeNodeElement+"> list = map.get(groupId);");
		buf.putLine2("	create_"+treeNodeElement+"_nodes(domainNode, list);");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createDomainMap(TreeNode treeNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String treeNodeType = treeNode.getType();
		String treeNodeLabel = treeNode.getLabel();
		String treeNodeElement = treeNode.getElement();
		String treeNodeData = treeNode.getData();
		String treeNodeTypeUncapped = NameUtil.uncapName(treeNodeType);
		String treeNodeElementUncapped = NameUtil.uncapName(treeNodeElement);
		//String treeNodeLabelUncapped = NameUtil.uncapName(treeNodeLabel);
		String treeNodeDataCapped = NameUtil.capName(treeNodeData);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeElement+"By"+treeNodeDataCapped+"_map");
		modelOperation.addParameter(createParameter("Collection<"+treeNodeElement+">", treeNodeElementUncapped+"Collection"));
		modelOperation.setResultType("Map<String, List<"+treeNodeElement+">>");
		
		Buf buf = new Buf();
		buf.putLine2("Map<String, List<"+treeNodeElement+">> map = new HashMap<String, List<"+treeNodeElement+">>();");
		buf.putLine2("Iterator<"+treeNodeElement+"> iterator = "+treeNodeElementUncapped+"Collection.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+treeNodeElement+" "+treeNodeElementUncapped+" = iterator.next();");
		buf.putLine2("	String "+treeNodeData+" = "+treeNodeElementUncapped+".get"+treeNodeDataCapped+"();");
		buf.putLine2("	List<"+treeNodeElement+"> list = map.get("+treeNodeData+");");
		buf.putLine2("	if (list == null) {");
		buf.putLine2("		list = new ArrayList<"+treeNodeElement+">();");
		buf.putLine2("		map.put("+treeNodeData+", list);");
		buf.putLine2("	}");
		buf.putLine2("	list.add("+treeNodeElementUncapped+");");
		buf.putLine2("}");
		buf.putLine2("return map;");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_createDomainNode(TreeNode treeNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String treeNodeType = treeNode.getType();
		String treeNodeLabel = treeNode.getLabel();
		String treeNodeTypeUncapped = NameUtil.uncapName(treeNodeType);
		//String treeNodeLabelUncapped = NameUtil.uncapName(treeNodeLabel);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeType+"_node");
		modelOperation.addParameter(createParameter("ModelTreeNode", "parentNode"));
		modelOperation.addParameter(createParameter(treeNodeType, treeNodeTypeUncapped));
		//modelOperation.addParameter(createParameter("Project", "project"));

		Buf buf = new Buf();
		buf.putLine2("ModelTreeNode "+treeNodeTypeUncapped+"Node = treeNodeFactory.createDomainNode(\""+treeNodeType+"\");");
		buf.putLine2("treeNodeFactory.addNode(parentNode, "+treeNodeTypeUncapped+"Node);");
		
		List<SubNode> subNodes = treeNode.getSubNodes();
		Iterator<SubNode> iterator = subNodes.iterator();
		while (iterator.hasNext()) {
			SubNode subNode = iterator.next();
			String childKey = subNode.getType();
			TreeNode childNode = getNode(childKey);
			String childKeyUncapped = NameUtil.uncapName(childKey);
			buf.putLine2("create_"+childKey+"_node("+treeNodeTypeUncapped+"Node, "+childKeyUncapped+");");
		}
		
		//buf.putLine2("folderNode.setExpanded(true);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected Collection<ModelOperation> createOperations_createElementNodes(TreeNode parentNode, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();	
		Set<String> keySet = elementTreeNodes.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			TreeNode elementNode = elementTreeNodes.get(key);
			modelOperations.add(createOperation_createElementNodes(elementNode, element));
			modelOperations.add(createOperation_createElementNode(elementNode, element));
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_createElementNodes(TreeNode treeNode, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//String treeNodeType = treeNode.getType();
		String treeNodeLabel = treeNode.getLabel();
		String treeNodeElement = treeNode.getElement();
		//String treeNodeTypeUncapped = NameUtil.uncapName(treeNodeType);
		String treeNodeElementUncapped = NameUtil.uncapName(treeNodeElement);
		//String treeNodeLabelUncapped = NameUtil.uncapName(treeNodeLabel);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeElement+"_nodes");
		modelOperation.addParameter(createParameter("ModelTreeNode", "parentNode"));
		modelOperation.addParameter(createParameter("Collection<"+treeNodeElement+">", treeNodeElementUncapped+"Collection"));
		
		Buf buf = new Buf();
		buf.putLine2("Iterator<"+treeNodeElement+"> iterator = "+treeNodeElementUncapped+"Collection.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+treeNodeElement+" "+treeNodeElementUncapped+" = iterator.next();");
		buf.putLine2("	create_"+treeNodeElement+"_node(parentNode, "+treeNodeElementUncapped+");");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_createElementNode(TreeNode treeNode, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		String treeNodeElement = treeNode.getElement();
		String treeNodeElementUncapped = NameUtil.uncapName(treeNodeElement);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("create_"+treeNodeElement+"_node");
		modelOperation.addParameter(createParameter("ModelTreeNode", "parentNode"));
		modelOperation.addParameter(createParameter(treeNodeElement, treeNodeElementUncapped));

		modelOperation.addImportedClass(elementPackageName + "." + treeNodeElement);
		modelOperation.addImportedClass(elementPackageName + ".util." + treeNodeElement + "Util");

		Buf buf = new Buf();
		buf.putLine2("ModelTreeNode "+treeNodeElementUncapped+"Node = treeNodeFactory.create"+treeNodeElement+"Node(parentNode, "+treeNodeElementUncapped+", correlationId);");
		//buf.putLine2("treeNodeFactory.addNode(parentNode, "+treeNodeTypeUncapped+"Node);");
		
		List<SubNode> subNodes = treeNode.getSubNodes();
		Iterator<SubNode> iterator = subNodes.iterator();
		while (iterator.hasNext()) {
			SubNode subNode = iterator.next();
			String childKey = subNode.getType();
			TreeNode childNode = getNode(childKey);
			String childKeyUncapped = NameUtil.uncapName(childKey);
			//String childNodeElement = childNode.getElement();
			//String childNodeElementUncapped = NameUtil.uncapName(childNodeElement);
			//String childKeyUncapped = NameUtil.uncapName(childKey);
			//buf.putLine2("Collection<"+childNodeElement+"> "+childNodeElementUncapped+"List = "+treeNodeType+"Util.get"+childKey+"("+treeNodeTypeUncapped+");");
			buf.putLine2("create_"+childKey+"_node("+treeNodeElementUncapped+"Node, "+treeNodeElement+"Util.get"+childKey+"("+treeNodeElementUncapped+"));");
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected String createOperation_createTreeNodeOLD(Element element, TreeNode parentNode) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		TreeNode rootNode = TreeUtil.getRootNode(tree);
		String rootNodeType = rootNode.getType();
		String rootNodeLabel = rootNode.getLabel();
		if (rootNodeLabel == null)
			rootNodeLabel = rootNodeType;
		String rootNodeTypeUncapped = NameUtil.uncapName(rootNodeType);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("createTree");
		modelOperation.addParameter(createParameter("ModelTreeNode", "rootNode"));
		modelOperation.addParameter(createParameter(rootNodeType, rootNodeTypeUncapped));
		
		Buf buf = new Buf();
		buf.putLine2("ModelTreeNode "+rootNodeTypeUncapped+"Node = treeNodeFactory.create"+rootNodeType+"Node("+rootNodeTypeUncapped+", correlationId);");
		buf.putLine2("treeNodeFactory.addNode(rootNode, "+rootNodeTypeUncapped+"Node);");

		return buf.get();
	}

}

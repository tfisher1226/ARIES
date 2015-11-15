package aries.codegen;

import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Query;
import nam.model.Type;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


public abstract class AbstractManagementBeanProvider {

	protected GenerationContext context;
	
	protected Element element;
	
	protected Field field;


	public AbstractManagementBeanProvider(GenerationContext context) {
		this.context = context;
	}

	public void setElement(Element element) {
		this.element = element;
	}
	
	public void setField(Field field) {
		this.field = field;
	}

	protected String getElementClassName(Element element) {
		return ModelLayerHelper.getElementClassName(element);
	}


	/*
	 * ImportElements operation
	 * ------------------------
	 */
	
	public abstract void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element);
	
	/*
	 * GetAllElements operation
	 * ------------------------
	 */
	
	public abstract void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element);
	
	/*
	 * GetElementListByField operation
	 * -------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field);

	/*
	 * GetElementListByPage operation
	 * ------------------------------
	 */

	//TODO add sanity checks for pageIndex and pageSize params
	public abstract void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element);

	/*
	 * GetElementByCriteria operation
	 * ----------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element);
	
	/*
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element);
	
	/*
	 * GetElementByQuery operation
	 * ----------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query);
	
	/*
	 * GetElementListByQueryCondition operation
	 * ----------------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query);

	/*
	 * GetElementListByQueryCondition operation
	 * ----------------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query);

	/*
	 * GetElementById operation
	 * ------------------------
	 */
	
	public abstract void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element);

	/*
	 * GetElementByKey operation
	 * -------------------------
	 */
	
	public abstract void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element);

	/*
	 * GetElementsByKeys operation
	 * ---------------------------
	 */
	
	public abstract void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element);

	/*
	 * GetElementByField operation
	 * ---------------------------------
	 */
	
	public abstract void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element);
	
	/*
	 * AddElement related operations
	 * -----------------------------
	 */
	
	public abstract void generateSourceCode_AddElement(ModelOperation modelOperation, Element element);
	
	public abstract void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element);

	/*
	 * MoveElement related operations
	 * ------------------------------
	 */

	public abstract void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element);
	
	/*
	 * SaveElement related operations
	 * ------------------------------
	 */
	
	public abstract void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element);

	/*
	 * RemoveElement related operations
	 * --------------------------------
	 */

	public abstract void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element);
	
	public abstract void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element);
	
	public abstract void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element);

	public abstract void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element);


	
	protected void addImportedClassForHelper(ModelUnit modelUnit, Type type) {
		String uri = TypeUtil.getNamespace(type.getType());
		Namespace namespace = context.getNamespaceByUri(uri);
		String helperQualifiedName = ModelLayerHelper.getModelHelperQualifiedName(namespace);
//		if (helperQualifiedName == null)
//			System.out.println();
		modelUnit.addImportedClass(helperQualifiedName);
	}
	
	protected String checkAddAssureConsistencyInvocation(ModelOperation modelOperation, Element element) {
		boolean twoWaySelfReferencing = ElementUtil.isTwoWaySelfReferencing(element);

		Buf buf = new Buf();
		if (twoWaySelfReferencing) {
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
			String elementClassName = ModelLayerHelper.getElementClassName(element);
			String elementPackageName = ModelLayerHelper.getElementPackageName(element);

			modelOperation.addImportedClass(elementPackageName+".util."+elementClassName+"Util");
			buf.putLine2(elementClassName+"Util.assureConsistency("+elementNameUncapped+");");
		}
		return buf.get();
	}
	
}

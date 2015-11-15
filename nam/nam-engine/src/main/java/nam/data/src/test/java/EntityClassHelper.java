package nam.data.src.test.java;

import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class EntityClassHelper {

	private GenerationContext context;

	private Namespace entityNamespace;
	
	private String entityPackageName;
	
	private String entityClassName;
	
	private String entityBeanName;
	
	private String entityFixtureClassName;
	
	private String parentEntityClassName;
	
	private String parentEntityBeanName;

	private Element generalElement;
	
	private Element targetElement;
	
	private Element referencedElement;
	
	
	public EntityClassHelper(GenerationContext context) {
		this.context = context;
	}
	
	public Namespace getEntityNamespace() {
		return entityNamespace;
	}

	public void setEntityNamespace(Namespace entityNamespace) {
		this.entityNamespace = entityNamespace;
	}

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public String getEntityBeanName() {
		return entityBeanName;
	}

	public void setEntityBeanName(String entityBeanName) {
		this.entityBeanName = entityBeanName;
	}

	public String getEntityFixtureClassName() {
		return entityFixtureClassName;
	}

	public void setEntityFixtureClassName(String entityFixtureClassName) {
		this.entityFixtureClassName = entityFixtureClassName;
	}

	public String getParentEntityClassName() {
		return parentEntityClassName;
	}

	public void setParentEntityClassName(String parentEntityClassName) {
		this.parentEntityClassName = parentEntityClassName;
	}

	public String getParentEntityBeanName() {
		return parentEntityBeanName;
	}

	public void setParentEntityBeanName(String parentEntityBeanName) {
		this.parentEntityBeanName = parentEntityBeanName;
	}

	public Element getGeneralElement() {
		if (referencedElement != null)
			return referencedElement;
		return generalElement;
	}

	public Element getTargetElement() {
		return targetElement;
	}

	public Element getReferencedElement() {
		return referencedElement;
	}

	public void initialize(Namespace namespace, Element element, Field field) {
		entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
		entityPackageName = DataLayerHelper.getInferredEntityPackageName(namespace, element);
		entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		entityBeanName =  DataLayerHelper.getInferredEntityBeanName(namespace, element);
		entityNamespace = namespace;
		parentEntityClassName = null;
		parentEntityBeanName = null;
		generalElement = element;
		targetElement = element;

		if (field == null) {
			/*
			 * In this case, we check references for a "parent"
			 * which will exist for an "inverse" reference.
			 */
			List<Reference> references = ElementUtil.getReferences(element);
			Iterator<Reference> iterator = references.iterator();
			while (iterator.hasNext()) {
				Reference reference = iterator.next();
				if (reference.getInverse()) {
					String elementNameCapped = ModelLayerHelper.getElementNameCapped(reference);
					parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
					parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
					//only one "inverse" relationship allowed per element
					break;
				}
			}
			
		} else {
			String fieldType = field.getType();
			String fieldLocalPart = TypeUtil.getLocalPart(fieldType);
			String fieldNameCapped = NameUtil.capName(fieldLocalPart);
			//String fieldNameCapped = NameUtil.capName(reference.getName());
			String fieldNameUncapped = NameUtil.uncapName(fieldNameCapped);
			//String fieldNameUncapped = NameUtil.uncapName(reference.getName());
			String fieldNamespaceUri = TypeUtil.getNamespace(fieldType);
			Namespace fieldNamespace = context.getNamespaceByUri(fieldNamespaceUri);
			
			referencedElement = context.getElementByType(fieldType);
			if (referencedElement == null) {
				fieldLocalPart = NameUtil.uncapName(field.getName());
				fieldType = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(fieldNamespaceUri, fieldLocalPart);
				referencedElement = context.getElementByType(fieldType);
			}

			if (referencedElement != null) {
				targetElement = referencedElement;
				entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, referencedElement);
				entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, referencedElement);
				entityNamespace = fieldNamespace;

				if (field instanceof Reference) {
					Reference reference = (Reference) field;
					
					if (reference.getContained()) {
						fieldNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
						fieldNameUncapped = NameUtil.uncapName(fieldNameCapped);
						String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
						parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
						parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
						entityClassName = elementNameCapped + entityClassName; 
						entityBeanName = NameUtil.uncapName(entityClassName);
						
						String newElementLocalPart = fieldNameUncapped;
						String newElementTypeName = TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, newElementLocalPart);
		
						Element newElement = ElementUtil.cloneElement(referencedElement);
						Reference inverseReference = ModelLayerHelper.createReferenceToContainingOwner(namespace, element);
						ElementUtil.addField(newElement, inverseReference);
						newElement.setName(newElementLocalPart);
						newElement.setType(newElementTypeName);
						newElement.setStructure("item");
						//TODO newElement.setDerived(true);
						targetElement = newElement;
						
//					} else if (reference.getInverse()) {
//						String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
//						parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
//						parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
						
					} else {
						/*
						 * Look for any "inverse" relations of the referenced element,
						 * if any exist then get "parent" from it.
						 */
						List<Reference> references = ElementUtil.getReferences(referencedElement);
						Iterator<Reference> iterator = references.iterator();
						while (iterator.hasNext()) {
							Reference reference2 = iterator.next();
							if (reference2.getInverse()) {
								String elementNameCapped = ModelLayerHelper.getElementNameCapped(reference2);
								parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
								parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
								//only one "inverse" relationship allowed per element
								break;
							}
						}
					}
					
					String referenceType = reference.getType();
					String elementType = element.getType();
					String referenceNamespace = TypeUtil.getNamespace(referenceType);
					String elementNamespace = TypeUtil.getNamespace(elementType);
					if (!referenceNamespace.equals(elementNamespace)) {
						entityPackageName = DataLayerHelper.getEntityPackageName(referenceNamespace);
					}
					
				} else {
					entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
					entityPackageName = DataLayerHelper.getInferredEntityPackageName(fieldNamespace, referencedElement);
				}
			}
		}
	}
	
}

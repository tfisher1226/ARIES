package nam.ui.src.main.webapp.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.Grouping;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;

import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.PhoneNumber;
import org.aries.common.StreetAddress;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementInfoPaneXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementInfoPaneXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}

	public List<ModelFile> buildFiles(Information information, Element element) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);

		//create the main infoPane first - this will provide links to the other infoPane-files
		modelFiles.add(buildFile(information, element));

		//create an infoPane-file for each grouping
		List<Grouping> groupings = ElementUtil.getGroups(element);
		Iterator<Grouping> iterator = groupings.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Grouping grouping = iterator.next();
			//String groupingName = grouping.getName();
			//String groupingKey = NameUtil.uncapName(groupingName.replace(" ", ""));

			//create the infoPane-file for this grouping
			modelFiles.add(buildFile(information, element, grouping));
		}
		return modelFiles;
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "InfoPane.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
		return modelFile;
	}

	public ModelFile buildFile(Information information, Element element, Grouping grouping) throws Exception {
		//String packageName = NameUtil.getPackagePathFromNamespace(namespace.getUri());
		String projectPath = information.getName().toLowerCase();
		String elementName = NameUtil.uncapName(element.getName());
		String groupingName = NameUtil.capName(grouping.getName());
		String groupingKey = NameUtil.uncapName(groupingName.replace(" ", ""));
		
		String folderName = projectPath + "/section/" + elementName;
		String fileName = elementName + "InfoRecord_"+groupingKey+"Pane.xhtml";
		
		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element, grouping));
		return modelFile;
	}

	public String getFileContent(Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<aries:tabPane"); 
		buf.putLine("		width=\"500\""); 
		buf.putLine("		height=\"300\""); 
		buf.putLine("		id=\"#{domain}"+elementClassName+"InfoPane\">");
		buf.putLine("		");
		buf.putLine("		<ui:param name=\"width\" value=\"#{width-2}\" />");
		buf.putLine("		<ui:param name=\"height\" value=\"#{height-48}\" />");
		buf.putLine("		");

		List<Grouping> groupings = ElementUtil.getGroups(element);
		if (groupings.size() == 0) {
			buf.putLine("		<aries:tab"); 
			buf.putLine("			label=\""+elementClassName+" Information\">");
			buf.putLine("			");
			List<Field> fieldList = ElementUtil.getFields(element);
			buf.put(getFileContent(elementNameUncapped, fieldList));
			buf.putLine("		</aries:tab>");
			
		} else {
			Iterator<Grouping> iterator = groupings.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Grouping grouping = iterator.next();
				String groupingName = grouping.getName();
				String groupingKey = NameUtil.uncapName(groupingName.replace(" ", ""));

				if (i > 0)
					buf.putLine("		");
				buf.putLine("		<aries:tab"); 
				buf.putLine("			label=\""+groupingName+"\">");
				buf.putLine("			<ui:include src=\""+elementNameUncapped+"InfoRecord_"+groupingKey+"Pane.xhtml\" />");
				buf.putLine("		</aries:tab>");
			}
		}
		
		buf.putLine("	</aries:tabPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	public String getFileContent(Element element, Grouping grouping) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		String groupingName = NameUtil.capName(grouping.getName());
		String groupingKey = NameUtil.uncapName(groupingName.replace(" ", ""));

//		if (groupingName.equals("Roles"))
//			System.out.println();
		
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine("");
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<aries:panel");
		buf.putLine("		padding=\"10px\"");
		buf.putLine("		backgroundColor=\"inherit\">");
		buf.putLine("		");
		
		List<Field> fieldsList = ElementUtil.getFields2(grouping);
		if (fieldsList.size() == 1 && FieldUtil.isContained(fieldsList.get(0))) {
			buf.putLine("		<aries:label");
			buf.putLine("			height=\"20\"");
			buf.putLine("			value=\""+groupingName+" Information\""); 
			buf.putLine("			backgroundColor=\"inherit\" />");
			buf.putLine("		");
			buf.putLine("		<aries:spacer height=\"6\" />");
			buf.putLine("		<aries:borderPane");
			buf.putLine("			width=\"#{width-22}\"");
			buf.putLine("			height=\"#{height-46}\"");
			buf.putLine("			backgroundColor=\"inherit\">");
			buf.putLine("			");

		} else {
			buf.putLine("		<aries:groupPane");
			buf.putLine("			width=\"#{width-22}\"");
			buf.putLine("			height=\"#{height-2}\"");
			buf.putLine("			backgroundColor=\"inherit\"");
			buf.putLine("			label=\""+groupingName+" Information\">");
			buf.putLine("			");
			buf.putLine("			<ui:param name=\"labelWidth\" value=\"110\" />");
			buf.putLine("			");
		}
		
		buf.put(getFileContent(elementNameUncapped, fieldsList));
		
		if (fieldsList.size() == 1 && FieldUtil.isContained(fieldsList.get(0))) {
			buf.putLine("		</aries:borderPane>");
		} else {
			buf.putLine("		</aries:groupPane>");
		}
		buf.putLine("	</aries:panel>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	protected String getFileContent(String elementNameUncapped, List<Field> fieldList) {
		boolean phoneNumberHandled = false;
		Buf buf = new Buf();
		
		Iterator<Field> iterator = fieldList.iterator();
		for (int i=0; iterator.hasNext();) {
			Field field = iterator.next();
			
			if (FieldUtil.isId(field))
				continue;

			String fieldType = field.getType();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldNameSplit = NameUtil.splitStringUsingSpacesCapped(fieldNameCapped);
			String fieldInstanceId = elementNameUncapped + fieldNameCapped;
			String fieldClassName = TypeUtil.getClassName(fieldType);
			String fieldClassNameUncapped = NameUtil.uncapName(fieldClassName);
			String fieldStructure = field.getStructure();
			
			Element elementForField = context.getElementByType(fieldType);
			Enumeration enumerationForField = context.getEnumerationByType(fieldType);
//			if (elementForField == null)
//				System.out.println();
			
			if (i++ > 0)
				buf.putLine("			");
		
			if (field instanceof Attribute) {
				if (FieldUtil.isBoolean(field)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:inputBoolean id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} if (FieldUtil.isNumeric(field)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:inputInteger id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} else if (FieldUtil.isSecret(field)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:inputSecret id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} else if (FieldUtil.isString(field)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:inputText id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
					
				} else if (enumerationForField != null && fieldStructure.equals("item")) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:selectOneMenu id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" items=\"#{"+fieldNameUncapped+"List}\" />");
					
				} else if (enumerationForField != null && !fieldStructure.equals("item")) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<ui:include src=\"../"+fieldClassNameUncapped+"/"+fieldClassNameUncapped+"SelectField.xhtml\">");
					buf.putLine("				<ui:param name=\"label\" value=\""+fieldNameCapped+"\" />");
					buf.putLine("				<ui:param name=\"type\" value=\""+fieldClassName+"Select\" />");
					buf.putLine("				<ui:param name=\"name\" value=\""+elementNameUncapped+fieldNameCapped+"\" />");
					buf.putLine("				<ui:param name=\"items\" value=\"#{"+elementNameUncapped+"Helper.get"+fieldNameCapped+"("+elementNameUncapped+")}\" />");
					buf.putLine("			</ui:include>");
				}
			}
			
			if (field instanceof Reference) {
				String elementQualifiedName = null;
				if (elementForField == null)
					elementQualifiedName = TypeUtil.getQualifiedName(fieldType);
				else elementQualifiedName = ModelLayerHelper.getElementQualifiedName(elementForField);
				if (ElementUtil.isRoot(elementForField)) {
				}
				
				if (PersonName.class.getName().endsWith(elementQualifiedName)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:personName id=\"#{section}"+fieldNameCapped+"\" type=\""+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" name=\""+fieldInstanceId+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} else if (EmailAddress.class.getName().endsWith(elementQualifiedName)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:emailAddress id=\"#{section}"+fieldNameCapped+"\" type=\""+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" name=\""+fieldInstanceId+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} else if (StreetAddress.class.getName().endsWith(elementQualifiedName)) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:streetAddress id=\"#{section}"+fieldNameCapped+"\" type=\""+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" name=\""+fieldInstanceId+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
				
				} else if (PhoneNumber.class.getName().endsWith(elementQualifiedName)) {
					if (!phoneNumberHandled) {
						phoneNumberHandled = true;
						buf.putLine("			<aries:spacer height=\"6\" />");
						buf.putLine("			<aries:phoneNumber id=\"#{section}PhoneNumbers\" type=\"PhoneNumbers\" label=\"Phone Number(s)\" name=\""+elementNameUncapped+"PhoneNumbers\" value=\"#{"+elementNameUncapped+"Helper.getPhoneNumbers("+elementNameUncapped+")}\" />");
					}
					
				} else if (fieldStructure.equals("item")) {
					buf.putLine("			<aries:spacer height=\"6\" />");
					buf.putLine("			<aries:selectOneMenu id=\"#{section}"+fieldNameCapped+"\" label=\""+fieldNameSplit+"\" value=\"#{"+elementNameUncapped+"."+fieldNameUncapped+"}\" items=\"#{"+fieldNameUncapped+"List}\" defaultLabel=\"select "+fieldNameSplit+"\" />");

				} else if (fieldStructure.equals("list")) {
					buf.putLine("			<ui:include src=\"../"+fieldClassNameUncapped+"/"+fieldClassNameUncapped+"ListPane.xhtml\">");
					buf.putLine("				<ui:param name=\"itemManager\" value=\"#{"+fieldClassNameUncapped+"InfoManager}\" />");
					buf.putLine("				<ui:param name=\"listManager\" value=\"#{"+fieldClassNameUncapped+"ListManager}\" />");
					buf.putLine("			</ui:include>");

				} else {
					if (FieldUtil.isContained(field)) {
						buf.putLine("			<ui:include src=\"../"+fieldClassNameUncapped+"/"+fieldClassNameUncapped+"ListPane.xhtml\">");
						buf.putLine("				<ui:param name=\"itemManager\" value=\"#{"+fieldClassNameUncapped+"InfoManager}\" />");
						buf.putLine("				<ui:param name=\"listManager\" value=\"#{"+fieldClassNameUncapped+"ListManager}\" />");
						buf.putLine("			</ui:include>");
						
					} else {
						buf.putLine("			<aries:spacer height=\"6\" />");
						buf.putLine("			<ui:include src=\"../"+fieldClassNameUncapped+"/"+fieldClassNameUncapped+"SelectField.xhtml\">");
						buf.putLine("				<ui:param name=\"label\" value=\""+fieldNameCapped+"\" />");
						buf.putLine("				<ui:param name=\"type\" value=\""+fieldClassName+"Select\" />");
						buf.putLine("				<ui:param name=\"name\" value=\""+elementNameUncapped+fieldNameCapped+"\" />");
						buf.putLine("				<ui:param name=\"items\" value=\"#{"+elementNameUncapped+"Helper.get"+fieldNameCapped+"("+elementNameUncapped+")}\" />");
						buf.putLine("			</ui:include>");
					}
				}
			}
		}
		
		return buf.get();
	}

	
}

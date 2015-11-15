package nam.ui.src.main.webapp.admin;

import java.util.Iterator;
import java.util.List;

import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Project;
import nam.model.Reference;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ElementListTableXhtmlBuilder extends AbstractCompositionXHTMLBuilder {

	public ElementListTableXhtmlBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public ModelFile buildFile(Information information, Element element) throws Exception {
		String elementName = ModelLayerHelper.getElementNameUncapped(element);
		String folderName = ModelLayerHelper.getElementWebappFolder(element);
		String fileName = elementName + "ListTable.xhtml";

		ModelFile modelFile = createMainWebappFile(folderName, fileName);
		modelFile.setTextContent(getFileContent(element));
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
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
		buf.putLine("	");
		buf.putLine("	<!-- table panel -->");
		buf.putLine("	<aries:outputPane");
		buf.putLine("		id=\""+elementNameUncapped+"ListTablePanel\">");
		buf.putLine("		");
		buf.putLine("		<!-- context menu -->");
		buf.putLine("		<ui:include src=\""+elementNameUncapped+"ListMenu.xhtml\">");
		buf.putLine("			<ui:param name=\"tableId\" value=\""+elementNameUncapped+"ListTable\" />");
		buf.putLine("		</ui:include>");
		buf.putLine("		");
		buf.putLine("		<!-- table -->");
		buf.putLine("		<aries:table");
		buf.putLine("			id=\""+elementNameUncapped+"ListTable\"");
		buf.putLine("			value=\"#{"+elementNameUncapped+"ListManager.dataModel}\""); 
		buf.putLine("			rowCount=\"#{"+elementNameUncapped+"ListManager.dataModel.rowCount}\"");
		buf.putLine("			visible=\"#{"+elementNameUncapped+"ListManager.dataModel.rowCount > 0}\"");
		buf.putLine("			addIndexColumn=\"true\"");
		buf.putLine("			addSelectorColumn=\"true\"");
		buf.putLine("			onrowmousedown=\"process"+elementClassName+"ListMouseDown(event, '#{index}', '#{rowItem.key}', '#{rowItem.label}')\"");
		buf.putLine("			onrowdblclick=\"process"+elementClassName+"ListDoubleClick(event, '#{index}', '#{rowItem.key}', '#{rowItem.label}')\"");
		buf.putLine("			style=\"width: auto; max-width: auto; border-top-width: 0px; border-left-width: 0px\">");
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldClassName = ModelLayerHelper.getFieldClassName(field);
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
			String fieldNameSplit = NameUtil.splitStringUsingSpacesCapped(fieldNameCapped);
			String fieldClassNameUncapped = NameUtil.uncapName(fieldClassName);
			String structure = field.getStructure();

			if (FieldUtil.isId(field))
				continue;
			if (FieldUtil.isSecret(field))
				continue;
			if (!structure.equals("item"))
				continue;
			
			buf.putLine("			");
			buf.putLine("			<!-- "+fieldNameSplit.toUpperCase()+" -->");

			if (field instanceof Attribute) {
//				if (FieldUtil.isBoolean(field)) {
//					
//				}
//				if (FieldUtil.isNumeric(field)) {
//					
//				}
//				if (FieldUtil.isString(field)) {
					buf.putLine("			<aries:textColumn"); 
					buf.putLine("				width=\"100\"");
					buf.putLine("				header=\""+fieldNameSplit+"\">");
					
					if (FieldUtil.isUnique(field)) {
						buf.putLine("				<aries:link"); 
						buf.putLine("					value=\"#{rowItem."+elementNameUncapped+"."+fieldNameUncapped+"}\"");
						buf.putLine("					onmouseup=\"executeSelectOpen"+elementClassName+"(#{index}, #{rowItem."+elementNameUncapped+".id})\" />");

					} else {
						buf.putLine("				<h:outputText value=\"#{rowItem."+elementNameUncapped+"."+fieldNameUncapped+"}\" />");
					}
					buf.putLine("			</aries:textColumn>");
//				}
			}

			if (field instanceof Reference) {
				Element fieldElement = context.getElementByType(field.getType());
				
				buf.putLine("			<aries:textColumn"); 
				buf.putLine("				width=\"100\"");
				buf.putLine("				header=\""+fieldNameSplit+"\">");
				
				if (FieldUtil.isUnique(field)) {
					buf.putLine("				<aries:link"); 
					buf.putLine("					value=\"#{"+fieldClassNameUncapped+"Helper.toString(rowItem."+elementNameUncapped+"."+fieldNameUncapped+")}\"");
					buf.putLine("					onmouseup=\"execute#{domain}SelectOpen"+elementClassName+"(#{index}, #{rowItem."+elementNameUncapped+".id})\" />");

				} else {
					buf.putLine("				<h:outputText value=\"#{"+fieldClassNameUncapped+"Helper.toString(rowItem."+elementNameUncapped+"."+fieldNameUncapped+")}\" />");
				}
				buf.putLine("			</aries:textColumn>");
			}
		
//			buf.putLine("			<aries:textColumn"); 
//			buf.putLine("				width=\"100\"");
//			buf.putLine("				header=\"Name\">");
//			buf.putLine("				");
//			buf.putLine("				<aries:link"); 
//			buf.putLine("					value=\"#{"+elementNameUncapped+"Helper.toString(rowItem."+elementNameUncapped+")}\"");
//			buf.putLine("					onmouseup=\"execute#{domain}SelectOpen"+elementClassName+"(#{index}, #{rowItem."+elementNameUncapped+".id})\" />");
//			buf.putLine("			</aries:textColumn>");

		}
		
		buf.putLine("		</aries:table>");
		buf.putLine("	</aries:outputPane>");
		buf.putLine("</ui:composition>");
		return buf.get();
	}
	
}

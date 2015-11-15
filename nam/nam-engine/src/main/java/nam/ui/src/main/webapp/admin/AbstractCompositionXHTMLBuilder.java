package nam.ui.src.main.webapp.admin;

import nam.model.Module;
import nam.model.Project;
import nam.ui.src.main.webapp.AbstractXHTMLBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public abstract class AbstractCompositionXHTMLBuilder extends AbstractXHTMLBuilder {

	public AbstractCompositionXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
		super.initialize(project, module);
	}
	
	public void build(Project project, Module module) {
		super.build(project, module);
	}

	protected String generateCompositionXhtmlOpen() {
		Buf buf = new Buf();
		buf.putLine("<!DOCTYPE composition PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		buf.putLine();
		return buf.get();
	}

	protected String generateCompositionXhtmlHeader() {
		return generateCompositionXhtmlHeader(null);
	}

	protected String generateCompositionXhtmlHeader(String template) {
		Buf buf = new Buf();
		buf.putLine("<ui:composition"); 
		buf.putLine("	xmlns=\"http://www.w3.org/1999/xhtml\"");
		buf.putLine("	xmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		buf.putLine("	xmlns:h=\"http://xmlns.jcp.org/jsf/html\"");
		buf.putLine("	xmlns:c=\"http://xmlns.jcp.org/jsp/jstl/core\"");	
		buf.putLine("	xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
		buf.putLine("	xmlns:a4j=\"http://richfaces.org/a4j\"");
		buf.putLine("	xmlns:rich=\"http://richfaces.org/rich\"");
		if (template != null) {
			buf.putLine("	xmlns:aries=\"http://aries.org/jsf\"");
			buf.putLine("	template=\"/templates/"+template+"\">");
		} else {
			buf.putLine("	xmlns:aries=\"http://aries.org/jsf\">");
		}
		return buf.get();
	}
	
	protected String generateCompositionXhtmlClose() {
		Buf buf = new Buf();
		buf.putLine("</ui:composition>");
		return buf.get();
	}

	
}

package org.aries.ui.tag;

import java.io.IOException;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;


public class LogTagHandler extends TagHandler {

	protected final TagAttribute[] vars; 

	public LogTagHandler(TagConfig config) {
		super(config);
		vars = tag.getAttributes().getAll(); 
	}

	public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException, FacesException, FaceletException, ELException {
		//VariableMapper originalMapper = faceletContext.getVariableMapper();
		//VariableMapper newMapper = new CustomVariableMapper(originalMapper);
		
		ValueExpression message = faceletContext.getVariableMapper().resolveVariable("message");
		if (message != null)
			faceletContext.setAttribute("message", message.getValue(faceletContext));

		// setup variable map
		for (TagAttribute tagAttribute : this.vars) {
			String localName = tagAttribute.getLocalName();
			if (localName.equals("message")) {
				ValueExpression valueExpression = tagAttribute.getValueExpression(faceletContext, Object.class);
				ValueExpression resolveVariable = faceletContext.getVariableMapper().resolveVariable("dialogMessage");
				if (resolveVariable != null) {
					resolveVariable.isLiteralText();
					resolveVariable.getExpressionString();
					System.out.println(">>> "+resolveVariable.getExpressionString());
				}
			}
		}
		//faceletContext.setVariableMapper(newMapper);

		this.nextHandler.apply(faceletContext, parent);
	} 

}


package org.aries.ui.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.jboss.el.ValueExpressionImpl;
import org.jboss.el.ValueExpressionLiteral;


public class IsolatedTagHandler extends TagHandler {

	private FaceletContext faceletContext; 

	protected final TagAttribute[] vars;

	private ValueExpressionImpl wrapped;

	
	public IsolatedTagHandler(TagConfig config) {
		super(config);
		vars = tag.getAttributes().getAll(); 
	}

	public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException, FacesException, FaceletException, ELException {
		this.faceletContext = faceletContext;
		
		VariableMapper originalMapper = faceletContext.getVariableMapper();
		VariableMapper newMapper = new CustomVariableMapper(originalMapper);
		
		boolean flag = false;
		ValueExpression id = originalMapper.resolveVariable("id");
		if (id == null) {
//			System.out.println(">>>"+parent.getClass()+", "+parent.getId()+", "+faceletContext.getAttribute("id")+", null");
		} else {
			Object idValue = id.getValue(faceletContext);
//			System.out.println(">>>"+parent.getClass()+", "+parent.getId()+", "+faceletContext.getAttribute("id")+", "+idValue);
			if (idValue != null && idValue.equals("pageForm")) {
				flag = true;
			}
		}
		
		ValueExpression enabled = originalMapper.resolveVariable("enabled");
		ValueExpression manager = originalMapper.resolveVariable("manager");
		ValueExpression action = originalMapper.resolveVariable("action");
		ValueExpression event = originalMapper.resolveVariable("event");
		ValueExpression domain = originalMapper.resolveVariable("domain");
		ValueExpression section = originalMapper.resolveVariable("section");
		ValueExpression dialog = originalMapper.resolveVariable("dialog");
		ValueExpression logo = originalMapper.resolveVariable("logo");
		ValueExpression title = originalMapper.resolveVariable("title");
		ValueExpression heading = originalMapper.resolveVariable("heading");
		ValueExpression message = originalMapper.resolveVariable("message");
		ValueExpression render = originalMapper.resolveVariable("render");
		ValueExpression value = originalMapper.resolveVariable("value");
		ValueExpression width = originalMapper.resolveVariable("width");
		ValueExpression height = originalMapper.resolveVariable("height");
		ValueExpression items = originalMapper.resolveVariable("items");
		
		ValueExpression fontSize = originalMapper.resolveVariable("fontSize");
		ValueExpression fontStyle = originalMapper.resolveVariable("fontStyle");
		ValueExpression fontWeight = originalMapper.resolveVariable("fontWeight");
		ValueExpression textColor = originalMapper.resolveVariable("textColor");

		if (value != null && value.getValue(faceletContext) != null) {
//			TagAttribute attribute = getRequiredAttribute("value");
//			ValueExpression valueExpression = attribute.getValueExpression(faceletContext, Object.class);
//			Class type = valueExpression.getType(faceletContext);
//			faceletContext.setAttribute("value", valueExpression);
//			System.out.println();
			
			//System.out.println(">>>"+value.getValue(faceletContext));
			//System.out.println(">>>"+value.getValueReference(faceletContext));
			//System.out.println(">>>"+value.getExpressionString());
			//System.out.println(">>>"+value.getType(faceletContext));
			//System.out.println(">>>"+value.getExpectedType());
			//System.out.println(">>>"+value.isLiteralText());
			//TagValueExpression tagValueExpression = (TagValueExpression) value;
			//wrapped = (ValueExpressionImpl) tagValueExpression.getWrapped();
			//System.out.println(value);
			//System.out.println();
			
			//faceletContext.getELResolver().setValue(faceletContext, "value", value.getExpressionString(), value.getValue(faceletContext));
			//newMapper.setVariable("value", value.);
		}

//		if (value != null) {
//			TagValueExpression tagValueExpression = (TagValueExpression) value;
//			wrapped = (ValueExpressionImpl) tagValueExpression.getWrapped();
//		}

//		if (height != null && height.getValue(faceletContext).equals("23px"))
//			System.out.println();
//		if (this.vars.length > 0)
//			System.out.println();
		
		// setup variable map
		Set<String> set = new HashSet<String>();
		for (TagAttribute tagAttribute : this.vars) {
			String localName = tagAttribute.getLocalName();
			ValueExpression valueExpression = tagAttribute.getValueExpression(faceletContext, Object.class);
			newMapper.setVariable(localName, valueExpression);

			if (localName.contains("param")) {
				String values = (String) valueExpression.getValue(faceletContext);
				StringTokenizer stringTokenizer = new StringTokenizer(values, ",");
				while (stringTokenizer.hasMoreElements()) {
					String paramName = (String) stringTokenizer.nextElement();
					paramName = paramName.trim();
					
					if (paramName.equals("id")) {
						set.add("id");
						continue;
					}
					if (paramName.equals("enabled")) {
						set.add("enabled");
						continue;
					}
					if (paramName.equals("manager")) {
						set.add("manager");
						continue;
					}
					if (paramName.equals("action")) {
						set.add("action");
						continue;
					}
					if (paramName.equals("event")) {
						set.add("event");
						continue;
					}
					if (paramName.equals("domain")) {
						set.add("domain");
						continue;
					}
					if (paramName.equals("section")) {
						set.add("section");
						continue;
					}
					if (paramName.equals("dialog")) {
						set.add("dialog");
						continue;
					}
					if (paramName.equals("logo")) {
						set.add("logo");
						continue;
					}
					if (paramName.equals("title")) {
						set.add("title");
						continue;
					}
					if (paramName.equals("heading")) {
						set.add("heading");
						continue;
					}
					if (paramName.equals("message")) {
						set.add("message");
						continue;
					}
					if (paramName.equals("render")) {
						set.add("render");
						continue;
					}
					if (paramName.equals("value")) {
						set.add("value");
						continue;
					}
					if (paramName.equals("width")) {
						set.add("width");
						continue;
					}
					if (paramName.equals("height")) {
						set.add("height");
						continue;
					}
					if (paramName.equals("items")) {
						set.add("items");
						continue;
					}
					if (paramName.equals("fontSize")) {
						set.add("fontSize");
						continue;
					}
					if (paramName.equals("fontStyle")) {
						set.add("fontStyle");
						continue;
					}
					if (paramName.equals("fontWeight")) {
						set.add("fontWeight");
						continue;
					}
					if (paramName.equals("textColor")) {
						set.add("textColor");
						continue;
					}

					ValueExpression paramExpression = originalMapper.resolveVariable(paramName);
					if (paramExpression != null) {
						Object paramValue = paramExpression.getValue(faceletContext);
						if (paramValue != null)
							faceletContext.setAttribute(paramName, paramValue);
					}
				}
			}
		}
		
//		if (id != null && !set.contains("id"))
//			faceletContext.setAttribute("id", id.getValue(faceletContext));
		if (enabled != null && !set.contains("enabled"))
			faceletContext.setAttribute("enabled", enabled.getValue(faceletContext));
		if (manager != null && !set.contains("manager"))
			faceletContext.setAttribute("manager", manager.getValue(faceletContext));
		if (action != null && !set.contains("action"))
			faceletContext.setAttribute("action", action.getValue(faceletContext));
		if (event != null && !set.contains("event"))
			faceletContext.setAttribute("event", event.getValue(faceletContext));
		if (domain != null && !set.contains("domain"))
			faceletContext.setAttribute("domain", domain.getValue(faceletContext));
		if (section != null && !set.contains("section"))
			faceletContext.setAttribute("section", section.getValue(faceletContext));
		if (dialog != null && !set.contains("dialog"))
			faceletContext.setAttribute("dialog", dialog.getValue(faceletContext));
		if (logo != null && !set.contains("logo"))
			faceletContext.setAttribute("logo", logo.getValue(faceletContext));
		if (title != null && !set.contains("title"))
			faceletContext.setAttribute("title", title.getValue(faceletContext));
		if (heading != null && !set.contains("heading"))
			faceletContext.setAttribute("heading", heading.getValue(faceletContext));
		if (message != null && !set.contains("message"))
			faceletContext.setAttribute("message", message.getValue(faceletContext));
		if (render != null && !set.contains("render"))
			faceletContext.setAttribute("render", render.getValue(faceletContext));
		if (value != null && !set.contains("value"))
			faceletContext.setAttribute("value", value.getValue(faceletContext));
		if (width != null && !set.contains("width"))
			try {
				faceletContext.setAttribute("width", width.getValue(faceletContext));
			} catch (Exception e) {
				System.out.println();
			}
		if (height != null && !set.contains("height"))
			try {
				faceletContext.setAttribute("height", height.getValue(faceletContext));
			} catch (Exception e) {
				System.out.println();
			}
		if (items != null && !set.contains("items"))
			faceletContext.setAttribute("items", items.getValue(faceletContext));
		
		processSetting(faceletContext, originalMapper, "fontSize", fontSize, set);
		processSetting(faceletContext, originalMapper, "fontStyle", fontStyle, set);
		processSetting(faceletContext, originalMapper, "fontWeight", fontWeight, set);
		processSetting(faceletContext, originalMapper, "textColor", textColor, set);

		
		faceletContext.setVariableMapper(newMapper);

		id = newMapper.resolveVariable("id");
		if (id == null) {
			faceletContext.setAttribute("id", null);
			//System.out.println(">>>"+parent.getClass()+", "+parent.getId()+", "+faceletContext.getAttribute("id")+", null");
		} else {
			Object idValue = id.getValue(faceletContext);
			//System.out.println(">>>"+parent.getClass()+", "+parent.getId()+", "+faceletContext.getAttribute("id")+", "+idValue);
			//if (idValue == null)
				faceletContext.setAttribute("id", null);
//			if (idValue != null && idValue.equals("loginForm"))
//				System.out.println();
		}
//		if (flag)
//			System.out.println();
		
		try {
			this.nextHandler.apply(faceletContext, parent);
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

	protected void processSetting(FaceletContext faceletContext, VariableMapper originalMapper, String varName, ValueExpression varExpression, Set<String> set) {
		if (varExpression != null && !set.contains(varName)) {
			faceletContext.setAttribute(varName, varExpression.getValue(faceletContext));
		} else if (varExpression != null && set.contains(varName)) {
			if (varName.equals("textColor") && varExpression.getValue(faceletContext).equals("darkBlue"))
				System.out.println();
			Class<?> type = varExpression.getType(faceletContext);
			ValueReference valueReference = varExpression.getValueReference(faceletContext);
			boolean literalText = varExpression.isLiteralText();
			boolean readOnly = varExpression.isReadOnly(faceletContext);
			Class<?> expectedType = varExpression.getExpectedType();
			String expressionString = varExpression.getExpressionString();
			if (varExpression != null && varExpression.isLiteralText())
				originalMapper.setVariable(varName, new ValueExpressionLiteral(varExpression.getValue(faceletContext), String.class));
			else originalMapper.setVariable(varName, new ValueExpressionLiteral("", String.class));
		}
	} 


	public class CustomVariableMapper extends VariableMapper {

		private final VariableMapper originalMapper;

		private Map<String, ValueExpression> vars;

		public CustomVariableMapper(VariableMapper originalMapper) {
			this.originalMapper = originalMapper;
		}

		public ValueExpression resolveVariable(String variable) {
//			if (variable.equals("value"))
//				System.out.println();
			ValueExpression ve = null;
			try {
				if (this.vars != null) {
					ve = (ValueExpression) this.vars.get(variable);
				}
				if (ve == null) {
					ValueExpression fromParentVE = null;
					if (this != this.originalMapper)
						fromParentVE = this.originalMapper.resolveVariable(variable);
					if (fromParentVE != null && variable.equals("value")) {
						//String expressionString = fromParentVE.getExpressionString();
						//System.out.println(wrapped);
						//ValueExpressionImpl valueExpressionImpl = new ValueExpressionImpl();
						//valueExpressionImpl.setValue(faceletContext.getFacesContext().getELContext(), expressionString);
						//return wrapped;
					}
					if (fromParentVE != null && variable.equals("id")) {
						Object idValue = fromParentVE.getValue(faceletContext);
						//System.out.println(">>> "+idValue);
						if (idValue == null) {
							//faceletContext.setAttribute("id", null);
							return null;
						}
					}
					if (fromParentVE != null) {
						if (fromParentVE instanceof FromParentValueExpression) {
							ve = null;
						} else {
							ve = new FromParentValueExpression(fromParentVE);
						}
					}
				}
				return ve;

			} catch (StackOverflowError e) {
				throw new ELException("Could not Resolve Variable [Overflow]: " + variable, e);
			}
		} 

		public ValueExpression setVariable(String variable, ValueExpression expression) {
			if (vars == null) {
				vars = new HashMap<String, ValueExpression>();
			}
			ValueExpression valueExpression = vars.put(variable, expression);
			return valueExpression;
		}
	}

}


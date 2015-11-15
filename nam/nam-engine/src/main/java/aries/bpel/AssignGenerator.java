package aries.bpel;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.EndpointReferenceRole;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.impl.ToImpl;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.xpath10.Expr;
import org.eclipse.bpel.xpath10.UnaryExpr;
import org.eclipse.bpel.xpath10.parser.XPath10Factory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.codegen.util.TokenUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class AssignGenerator extends AbstractActivityGenerator<Assign> {

	private Buf localMethodSources;


	public AssignGenerator(GenerationContext context, Process process) { 
		super(context, process);
		this.verbose = false;
	}

	@Override
	public void generate(Assign assignActivity) throws Exception {
		boolean generateOperation = assignActivity.getName() != null && !isInsideCodeBlock();
		ModelOperation modelOperation = null;
		String operationName = null;
		
		if (generateOperation) {
			operationName = CodeUtil.getOperationName(assignActivity);
			modelOperation = new ModelOperation();
			modelOperation.setModifiers(Modifier.PROTECTED);
			modelOperation.setName(operationName);
			modelOperation.setResultType("void");
		}
		
		//this will hold source code
		Buf buf = new Buf();

		//get source code for method body
		EList<Copy> copyList = assignActivity.getCopy();
		Iterator<Copy> iterator = copyList.iterator();
		while (iterator.hasNext()) {
			Copy copy = iterator.next();
			String code = generate(copy);
			String[] lines = TokenUtil.tokenize(code, "\n");
			for (String line : lines)
				buf.putLine2(line);
			if (verbose)
				System.out.println("\nCopy task:\n"+code);
		}
		
		buf.put(generateSourceConditionStateAssignments(assignActivity));
		
		if (generateOperation) {
			if (!operationCache.contains(operationName)) {
				operationCache.add(operationName);
	
				//add source code to local operation
				addStatementsToOperation(modelOperation, buf);
				addInstanceOperation(modelOperation);
			}

			if (!isInsideFlow()) {
				//add invocation source code to current operation
				addInvocationSource(modelOperation);
			}
			
		} else {
			addStatementsToOperation(currentOperation, buf);
		}
	}
	
	protected String generate(Copy copyTask) throws Exception {
		localMethodSources = new Buf();

		//boolean keepSrcElementName = copyTask.getKeepSrcElementName();
		//boolean ignoreMissingFromData = copyTask.getIgnoreMissingFromData();
		//boolean ignoreUninitializedFromVariable = true;

		To to = copyTask.getTo();
		From from = copyTask.getFrom();
		Assert.notNull(to);
		Assert.notNull(from);

//		Variable variable = to.getVariable();
//		if (variable != null) {
//			String name = variable.getName();
//			if (name != null && name.contains("bookOrderResponse"))
//				System.out.println();
//			if (name == null)
//				System.out.println();
//		}

//		variable = to.getVariable();
//		if (variable != null) {
//			String name = variable.getName();
//			if (name != null && name.contains("bookPurchaseRequest"))
//				System.out.println();
//		}
		
		String[] toResultType = new String[1];
		String lhs = compileTo(to, toResultType);
		String rhs = compileFrom(from, toResultType);
		
		if (lhs.contains("XXAssignmentTypePlaceHolderXX")) {
			if (!StringUtils.isEmpty(toResultType[0]))
				lhs = lhs.replace("XXAssignmentTypePlaceHolderXX", (String) toResultType[0]);
			return localMethodSources.get();
		}
		
		if (lhs.contains("XXPlaceHolderXX")) {
			lhs = lhs.replace("XXPlaceHolderXX", rhs);
			localMethodSources.put(lhs+";");
			
		} else if (from.getExpression() != null) {
			localMethodSources.put(lhs+" = "+rhs+";");

		} else if (from.getLiteral() != null) {
			Variable toVariable = to.getVariable();
			if (toVariable != null && !isLocalVariable(toVariable))
				localMethodSources.put(lhs+" = "+rhs+";");

		} else {
			Variable toVariable = to.getVariable();
			Variable fromVariable = from.getVariable();
			Assert.notNull(toVariable, "To/variable must exist");
			Assert.notNull(fromVariable, "From/variable must exist");
			String toVariableType = TypeUtil.getTypeFromVariable(toVariable);
			String fromVariableType = TypeUtil.getTypeFromVariable(fromVariable);
			String toLocalPart = TypeUtil.getLocalPart(toVariableType).toLowerCase();
			String fromLocalPart = TypeUtil.getLocalPart(fromVariableType).toLowerCase();
	
			if (toLocalPart.equals(fromLocalPart)) {
				localMethodSources.put(lhs+" = "+rhs+";");
			} else if (toLocalPart.equals("string")) {
				localMethodSources.put(lhs+" = "+rhs+";");
			} else if (toLocalPart.equals("integer")) {
				localMethodSources.put(lhs+" = Integer.parseInt("+rhs+");");
			} else if (toLocalPart.equals("double")) {
				localMethodSources.put(lhs+" = Double.parseDouble("+rhs+");");
			} else if (toLocalPart.equals("float")) {
				localMethodSources.put(lhs+" = Float.parseFloat("+rhs+");");
			} else {
				localMethodSources.put(lhs+" = "+rhs+";");
			}
		}

		return localMethodSources.get();
	}

	protected String compileFrom(From from, String[] resultType) throws Exception {
		if (from.getExtensibilityElements().size() > 0) {
			return processExtensibilityElements(from.getExtensibilityElements());
		}

//		if (from.getVariable() != null && from.getVariable().getName().startsWith("bookOrderResponse"))
//			System.out.println();
		
		if (from.getLiteral() != null) {
			String output = processLiteral(from, resultType);
			return output;
		}

		if (from.getProperty() != null) {
			Property property = from.getProperty();
			String name = property.getName();
			Variable variable = getVariable(name);
			return null;
		}
		
		if (from.getPart() != null) {
			Part part = from.getPart();
			String partName = part.getName();
			Variable variable = from.getVariable();
			Assert.notNull(variable, "Variable must exist");
			boolean isGlobal = isGlobalVariable(variable);
			String variableName = CodeUtil.getVariableName(variable, isGlobal);
			String typeFromVariable = TypeUtil.getTypeFromVariable(variable);
			resultType[0] = TypeUtil.getClassName(typeFromVariable);
			//String variableName = variable.getName();
			//if (isGlobalVariable(variable))
			//	variableName = "this."+variableName;
			
			/*
			 * Do this because we have variables of type: "messageName_partName".
			 * If not then we get an extra "part" tacked on.
			 */
			if (!variableName.toLowerCase().contains(partName.toLowerCase()))
				variableName += ".get"+NameUtil.capName(partName)+"()";

			//Message message = variable.getMessageType();
			//Assert.notNull(message, "Message must exist");
			//String partName = from.getPart().getName();
			//String name = CodeUtil.getVariableName(variable);
			//String name = "this."+variable.getName()+"_"+partName;

			if (from.getQuery() != null) {
				Query query = from.getQuery();
				String fieldValue = query.getValue();
				//TODO we will need this soon, right now just strip out prefix
				if (fieldValue.contains(":")) {
					int index = fieldValue.indexOf(":"); 
					fieldValue = fieldValue.substring(index+1);
				}
				
				ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
				String queryValue = expressionGenerator.generate(fieldValue);

				String output = variableName+"."+queryValue;
				//String output = variableName+".get"+NameUtil.capName(queryValue)+"()";
				return output;
			}

			return variableName;
		}

		if (from.getVariable() != null) {
			Variable variableRef = from.getVariable();
			Variable variable = getVariable(variableRef.getName());
			if (variable == null)
				variable = variableRef; 
			String variableName = variable.getName();
			if (isGlobalVariable(variable))
				variableName = "this."+variableName;
			
			if (from.getQuery() != null) {
				Query query = from.getQuery();
				String field = query.getValue();
				String output = variableName+".get"+NameUtil.capName(field)+"()";
				return output;
			}

			//if (variable.getMessageType() != null) {
			//}

			//if (variable.getType() != null) {
			//}

			String name = CodeUtil.getVariableName(variable);
			return name;
		}

		if (from.getPartnerLink() != null) {
			PartnerLink partnerLink = from.getPartnerLink();
			EndpointReferenceRole endpointReference = from.getEndpointReference();
			return null;
		}

		if (from.getExpression() != null) {
			ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
			String output = expressionGenerator.generate(from.getExpression(), false);
			return output;
		}

		return null;
	}

	private String processExtensibilityElements(List extensibilityElements) throws Exception {
		return null;
	}

	private String processLiteral(From from, String[] resultType) throws Exception {
		String literal = from.getLiteral();
		if (literal != null && !literal.contains("$") && !literal.contains("/") && !literal.contains(".")) {
			
			//is there a function expressed here?
			if (literal.contains("(") && literal.contains(")")) { 
				Expr expr1 = XPath10Factory.create(literal);
				if (expr1 instanceof UnaryExpr)
					expr1 = ((UnaryExpr) expr1).getExpr();
				BPELExpressionParser parser = new BPELExpressionParser();
				parser.setVariablesMap(getGlobalVariables());
				parser.setLeftHandSide(true);
				parser.setVerbose(verbose);
				String output = parser.visit(expr1);
				return output;
			}
			
			//otherwise return as plain text
			return "\""+literal+"\"";
		}
			
		//Element literal = from.getElement();
		//Node literalNode = from.getElement().getFirstChild();
		Node literalNode = getLiteralNode(from.getElement());
		Node typeNode = getNextElementNode(literalNode.getFirstChild());

		//Map<String, EClass> map = context.getElementByQNameCache();
		String namespace = typeNode.getNamespaceURI();
		String localPart = typeNode.getLocalName();
		String typeName = org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
		//EClass eClass = map.get(typeName);
		//String javaType = TypeUtil.getJavaTypeFromEClassifer(eClass);
		String packageName = TypeUtil.getPackageName(typeName);
		String className = TypeUtil.getClassName(typeName);
		String variableName = NameUtil.uncapName(className);
		addImportedClass(packageName+"."+className);
		//resultType[0] = className;
		
		//sourceCode
		localMethodSources.putLine(className+" "+variableName+" = new "+className+"();");
		
		boolean done = false;
		Node typeNodeChild = typeNode.getFirstChild();
		if (typeNodeChild == null)
			return variableName;
		
		Node fieldNode = getNextElementNode(typeNodeChild);
		while (fieldNode != null && !done) {
			String fieldName = fieldNode.getNodeName();
			int indexOfColon = fieldName.indexOf(":");
			if (indexOfColon > -1)
				fieldName = NameUtil.getLocalNameFromXSDType(fieldName);
			Node valueNode = fieldNode.getFirstChild();
			String fieldValue = "null";
			if (valueNode != null && valueNode.getNodeValue() != null)
				fieldValue = "\""+valueNode.getNodeValue()+"\"";

			if (!StringUtils.isEmpty(fieldValue)) {
				String setterName = CodeUtil.getSetterName(fieldName);
				localMethodSources.putLine(variableName+"."+setterName+"("+fieldValue+");");
			}

			fieldNode = getNextElementNode(fieldNode.getNextSibling());
		}
		
		/*
		//Unmarshall from XML literal?
		//JAXBContext jaxbContext = JAXBContext.newInstance();
		//Unmarshaller u = jaxbContext.createUnmarshaller();
		//StringBuffer xmlStr = new StringBuffer(from.getLiteral());
		//Object object = u.unmarshal(new StreamSource(new StringReader(xmlStr.toString())));
		*/
		
		return variableName;
	}

	private Node getLiteralNode(Node node) {
		if (node.getFirstChild() != null) {
			Node firstChild = node.getFirstChild();
			if (firstChild instanceof Element && firstChild.getLocalName().toLowerCase().equals("literal"))
				return firstChild; 
			Node nextSibling = firstChild.getNextSibling();
			if (nextSibling instanceof Element && nextSibling.getLocalName().toLowerCase().equals("literal"))
				return nextSibling; 
		}
		return null;
	}

	private Node getNextElementNode(Node node) {
		if (node instanceof Element)
			return node; 
		Node sibling = node.getNextSibling();
		while (sibling != null) { 
			if (sibling instanceof Element)
				return sibling; 
			sibling = sibling.getNextSibling();
		}
		return null;
	}

	protected String compileTo(To to, String[] resultType) throws Exception {
		if (to.getProperty() != null) {
			Property property = to.getProperty();
			String name = property.getName();
			Variable variable = getVariable(name);
			return null;
		} 

//		Variable variable2 = to.getVariable();
//		if (variable2 != null) {
//			//Variable variable2 = getVariable(variableRef2.getName());
//			String variableName2 = variable2.getName();
//			if (variableName2.contains("bookMessage"))
//				System.out.println();
//		}
		
		if (to.getPart() != null) {
			Part part = to.getPart();
			String partName = part.getName();
			Variable variableRef = to.getVariable();
			Variable variable = getVariable(variableRef.getName());
			Assert.notNull(variable, "Variable must exist");
			Message message = variable.getMessageType();
			Assert.notNull(message, "Message must exist");
			//String variableName = variable.getName()+"_"+partName;
			boolean isGlobal = isGlobalVariable(variable);
			String variableName = CodeUtil.getVariableName(variable, isGlobal);
			//variableName += ".get"+NameUtil.capName(partName)+"()";

			//if (isGlobal)
			//	variableName = "this."+variableName;

			if (to.getQuery() != null) {
				Query query = to.getQuery();
				String fieldValue = query.getValue().trim();

				//we have an function here?
				if (fieldValue.contains("(") && fieldValue.contains(")")) {
					int openParenIndex = fieldValue.indexOf("(");
					int closeParenIndex = fieldValue.indexOf(")");
					String functionName = fieldValue.substring(0, openParenIndex).trim();
					String collectionExpression = fieldValue.substring(openParenIndex+1, closeParenIndex).trim();
					
					ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
					String output = expressionGenerator.generate(collectionExpression);

					if (functionName.equals("add")) {
						return variableName+"."+output+".add(XXPlaceHolderXX)";
					} else if (functionName.equals("addAll")) {
						return variableName+"."+output+".addAll(XXPlaceHolderXX)";
					} else if (functionName.equals("set")) {
						return variableName+"."+output+".set(XXPlaceHolderXX)";
					}
				}
				
				//we have an array here? (this is for a List)
				if (fieldValue.contains("[") && fieldValue.contains("]")) {
					int openBracketIndex = fieldValue.indexOf("[");
					int closeBracketIndex = fieldValue.indexOf("]");
					String arrayName = fieldValue.substring(0, openBracketIndex).trim();
					String arrayIndex = fieldValue.substring(openBracketIndex+1, closeBracketIndex).trim();
					if (arrayIndex.startsWith("$"))
						arrayIndex = arrayIndex.substring(1);
					if (isGlobalVariable(arrayIndex))
						arrayIndex = "this."+arrayIndex;
					//return "this."+variableName+"."+arrayName+"["+arrayIndex+"]";
					return variableName+".get"+NameUtil.capName(arrayName)+"().set("+arrayIndex+", XXPlaceHolderXX)";
				}
				
				String output = variableName+".set"+NameUtil.capName(fieldValue)+"(XXPlaceHolderXX)";
				return output;
			}

			//return variableName+".get"+NameUtil.capName(partName)+"()";
			return variableName;
		}

		if (to.getVariable() != null) {
			Variable variable = to.getVariable();
			//Variable variable = getVariable(variableRef.getName());
			Assert.notNull(variable, "Variable must exist");
			boolean isGlobal = isGlobalVariable(variable);
			String variableName = CodeUtil.getVariableName(variable, isGlobal);
			String typeFromVariable = TypeUtil.getTypeFromVariable(variable);

			if (typeFromVariable != null) {
				String typeClassName = TypeUtil.getClassName(typeFromVariable);
				resultType[0] = typeClassName;
			}
			
			if (to.getQuery() != null) {
				Query query = to.getQuery();
				String output = variableName;
				String partName = to.getElement().getAttribute("part");
				String fieldValue = query.getValue().trim();
				
				if (!StringUtils.isEmpty(partName))
					output += ".get"+NameUtil.capName(partName)+"()";
					
				//we have an function here?
				if (fieldValue.contains("(") && fieldValue.contains(")")) {
					int openParenIndex = fieldValue.indexOf("(");
					int closeParenIndex = fieldValue.indexOf(")");
					String functionName = fieldValue.substring(0, openParenIndex).trim();
					String collectionExpression = fieldValue.substring(openParenIndex+1, closeParenIndex).trim();
					
					ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
					output += "."+expressionGenerator.generate(collectionExpression);

					if (functionName.equals("add")) {
						return output+".add(XXPlaceHolderXX)";
					} else if (functionName.equals("addAll")) {
						return output+".addAll(XXPlaceHolderXX)";
					} else if (functionName.equals("set")) {
						return output+".set(XXPlaceHolderXX)";
					}
				}
				
				//output = variable.getName();
				output += ".set"+NameUtil.capName(fieldValue)+"(XXPlaceHolderXX)";
				return output;
			}

			//if (variable.getMessageType() != null) {
			//}

			//if (variable.getType() != null) {
			//}

			String output = variableName;
			String partName = to.getElement().getAttribute("part");
			
			if (StringUtils.isEmpty(partName))
				output += ".get"+NameUtil.capName(partName)+"()";
			
			if (!isGlobal && StringUtils.isEmpty(partName)) {
				//include the Type in front of the variable 
				String type = TypeUtil.getTypeFromVariable(variable);
				if (type != null) {
					String className = TypeUtil.getClassName(type);
					output = className+" "+variableName;
					
				} else if (isLocalVariable(variable)) {
					Variable variableRef = getLocalVariable(variable.getName());
					type = TypeUtil.getTypeFromVariable(variableRef);
					output = type+" "+variableName;
					
				} else
					output = "XXAssignmentTypePlaceHolderXX "+variableName;
			} else 
				output = variableName;
			if (!StringUtils.isEmpty(partName))
				output += ".set"+NameUtil.capName(partName)+"(XXPlaceHolderXX)";
			return output;
		} 

		if (to.getPartnerLink() != null) {
			PartnerLink partnerLink = to.getPartnerLink();
			Part part = to.getPart();
			return null;
		}

		if (to.getExpression() != null) {
			ExpressionGenerator expressionGenerator = new ExpressionGenerator(getVariables());
			String output = expressionGenerator.generate(to.getExpression());
			return output;
		}

		return null;
	}

	protected void verifyCopy(Copy copyTask) {
		To to = copyTask.getTo();
		From from = copyTask.getFrom();

		// If direct Message->Message copy
		if (from.getVariable() != null && from.getVariable().getMessageType() != null &&
				to.getVariable() != null && to.getVariable().getMessageType() != null) {

			// Check that the LValue/RValue message types match up.
			String lvar = to.getVariable().getName();
			String rvar = from.getVariable().getName();
			QName tlvalue = to.getVariable().getMessageType().getQName();
			QName trvalue = from.getVariable().getMessageType().getQName();

			if (!tlvalue.equals(trvalue))
				throw new RuntimeException("MismatchedMessageAssignment");
			return;
		}

		// If Message->Non-Message copy
		if (from.getVariable() != null && from.getVariable().getMessageType() != null &&
				(to.getVariable() == null || to.getVariable().getMessageType() == null)) {
			throw new RuntimeException("CopyFromMessageToNonMessage: "+ from.getVariable().getName());
		}

		// If Non-Message->Message copy
		if ((from.getVariable() == null || from.getVariable().getMessageType() == null) &&
				to.getVariable() != null && to.getVariable().getMessageType() != null) {
			throw new RuntimeException("CopyToMessageFromNonMessage: "+ to.getVariable().getName());
		}

		// If *->Partner Link copy
		if (to.getPartnerLink() != null && to.getPartnerLink().getPartnerRole() == null) {
			throw new RuntimeException("CopyToUndeclaredPartnerRole: "+ to.getPartnerLink().getName());
		}

		// If Partner Link->* copy
		if (from.getPartnerLink() != null) {
			if (from.getEndpointReference() != null && from.getPartnerLink().getMyRole() == null) {
				throw new RuntimeException("CopyFromUndeclaredPartnerRole, myRole: "+ from.getPartnerLink().getName());
			}

			if (from.getEndpointReference() != null && from.getPartnerLink().getPartnerRole() == null) {
				throw new RuntimeException("CopyFromUndeclaredPartnerRole, partnerRole: "+ from.getPartnerLink().getName());
			}
		}
	}

}

// Copyright (C) 2008 Luciano Garcia-Banuelos <lgbanuelos@gmail.com>
// Licensed under the terms of the GNU GPL; see COPYING for details.
package bpelfactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.namespace.QName;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Else;
import org.eclipse.bpel.model.ElseIf;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.If;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.RepeatUntil;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.While;
import org.eclipse.bpel.model.proxy.OperationProxy;
import org.eclipse.bpel.model.proxy.PortTypeProxy;
import org.eclipse.bpel.model.resource.BPELReader;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.Templates.Template;
import org.eclipse.bpel.ui.Templates.TemplateResource;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import simpel.Builder;
import simpel.FileHelper;
import simpel.SimPELLexer;
import simpel.SimPELParser;
import simpel.SimPELWalker;
import translation.Rewriter;
import uk.co.badgersinfoil.e4x.antlr.LinkedListTokenSource;
import uk.co.badgersinfoil.e4x.antlr.LinkedListTokenStream;
import uk.co.badgersinfoil.e4x.antlr.LinkedListTree;
import uk.co.badgersinfoil.e4x.antlr.LinkedListTreeAdaptor;

public class Translator {

	private BPELResource bpelResource;
	Stack<Object> context = new Stack<Object>();
	HashMap<Vertex, Object> activityMap = new HashMap<Vertex, Object>();
	HashMap<String, Object> symbolTab = new HashMap<String, Object>();
	IContainer container;

	private Builder builder = new Builder();
	private IFile targetFile;
	public Translator(IFile targetFile) {
		this.targetFile = targetFile;
	}
	public void generateAnnotated() {
		Resource bpmnResource = new ResourceSetImpl().getResource(URI
				.createURI(targetFile.getFullPath().toString()), true);
		BpmnDiagram diagram = (BpmnDiagram) bpmnResource.getContents().get(0);
		Pool pool = diagram.getPools().get(0);

		System.out.println(bpmnResource.getContents().get(0));

		// REWRITE the BPMN model
		// Every SESE region is annotated with a
		Rewriter rewriter = new Rewriter();
		rewriter.rewrite(pool);

		IPath path = new Path(targetFile.getFullPath().toString().replace(".bpmn", "_inter.bpmn_diagram"));
		try {
			new VisualGenerator(path, diagram);
		} catch (Exception e) { e.printStackTrace(); }	
	}

	public void translate() {
		Resource bpmnResource = new ResourceSetImpl().getResource(URI
				.createURI(targetFile.getFullPath().toString()), true);
		BpmnDiagram diagram = (BpmnDiagram) bpmnResource.getContents().get(0);
		Pool pool = diagram.getPools().get(0);

		System.out.println(bpmnResource.getContents().get(0));

		// Rewriting

		Rewriter rewriter = new Rewriter();
		rewriter.rewrite(pool);

		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("processName", pool.getName());
		args.put("namespace", Builder.plknamespace);
		Template template = BPELUIPlugin.INSTANCE.getTemplates()
		.getTemplateByName("Synchronous BPEL Process");
		TemplateResource bpelTemplateResource = null;
		for (TemplateResource resource : template.getResources()) {
			if (resource.getName(args).indexOf(".bpel") != -1)
				bpelTemplateResource = resource;
		}

		container = targetFile.getParent();
		IFile bpelFile = container.getFile(new Path(bpelTemplateResource
				.getName(args)));
		URI bpelFileURI = URI.createFileURI(bpelFile.getLocation().toString());
		bpelResource = (BPELResource) new BPELResourceFactoryImpl()
		.createResource(bpelFileURI);

		String bpelString = bpelTemplateResource.process(args);
		InputStream stream = new ByteArrayInputStream(bpelString.getBytes());
		BPELReader reader = new BPELReader();

		try {
			reader.read(bpelResource, stream);
			System.out.println(bpelResource.getContents().get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		translate(diagram);
	}

	private void translate(BpmnDiagram diagram) {
		Process process = (Process) bpelResource.getContents().get(0);

		builder.setProcess(process);

		translate(diagram.getPools().get(0), process);

		try {
			bpelResource.save(null);
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String streamContents = FileHelper.buildPLFileContents(process, builder.getPlMap(), builder.getNsMap());
		InputStream stream = null;
		if (streamContents != null) {
			IFile plnkFile = container.getFile(new Path(process.getName()+"_plnk.wsdl"));
			stream = new ByteArrayInputStream(
					FileHelper.buildPLFileContents(process, builder.getPlMap(), builder.getNsMap()).getBytes());
			try {
				if (plnkFile.exists())
					plnkFile.setContents(stream, true, true, null);
				else
					plnkFile.create(stream, true, null);
			} catch (Exception ex1) {
				ex1.printStackTrace();
			} finally {
				try {
					stream.close();
				} catch (Exception ex2) {
				}
			}
		}
		streamContents = FileHelper.buildDeployFileContents(process, builder.getPlMap(), builder.getNsMap());
		if (streamContents != null) {
			try {
				IFile deployFile = container.getFile(new Path("deploy.xml"));
				stream = new ByteArrayInputStream(
						streamContents.getBytes());

				if (deployFile.exists())
					deployFile.setContents(stream, true, true, null);
				else
					deployFile.create(stream, true, null);
			} catch (Exception ex1) {
				ex1.printStackTrace();
			} finally {
				try {
					stream.close();
				} catch (Exception ex2) {
				}
			}
		}
	}

	private void contextAddChild(Object child) {
		if (context.size() > 0) {
			Object top = context.peek();
			if (top instanceof Sequence)
				((Sequence) top).getActivities().add(
						(org.eclipse.bpel.model.Activity) child);
			else if (top instanceof Flow) {
				if (child instanceof Links)
					((Flow) top).setLinks((Links) child);
				else
					((Flow) top).getActivities().add(
							(org.eclipse.bpel.model.Activity) child);
			} else if (top instanceof If) {
				if (child instanceof ElseIf)
					((If) top).getElseIf().add((ElseIf) child);
				else if (child instanceof Else)
					((If) top).setElse((Else) child);
				else
					((If) top)
					.setActivity((org.eclipse.bpel.model.Activity) child);
			} else if (top instanceof ElseIf)
				((ElseIf) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof Else)
				((Else) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof While)
				((While) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof RepeatUntil)
				((RepeatUntil) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof Links)
				((Links) top).getChildren().add((Link) child);
			else if (top instanceof Pick) {
				if (child instanceof OnMessage)
					((Pick) top).getMessages().add((OnMessage) child);
				else if (child instanceof OnAlarm)
					((Pick) top).getAlarm().add((OnAlarm) child);
				else
					error("ERROR: Unsupported event type");
			} else if (top instanceof OnMessage)
				((OnMessage) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof OnAlarm)
				((OnAlarm) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			else if (top instanceof Scope) {
				if (child instanceof EventHandler)
					((Scope) top).setEventHandlers((EventHandler) child);
				else
					((Scope) top)
					.setActivity((org.eclipse.bpel.model.Activity) child);
			} else if (top instanceof EventHandler) {
				if (child instanceof OnEvent)
					((EventHandler) top).getEvents().add((OnEvent) child);
				else if (child instanceof OnAlarm)
					((EventHandler) top).getAlarm().add((OnAlarm) child);
			} else if (top instanceof OnEvent) {
				((OnEvent) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
			} else if (top instanceof Process)
				((Process) top)
				.setActivity((org.eclipse.bpel.model.Activity) child);
		}
	}

	private void contextOpenScope(Object act) {
		context.push(act);
	}

	private void contextCloseScope() {
		context.pop();
	}

	private void translate(Pool pool, Process process) {
		contextOpenScope(process);
		parsePoolAnnotations(pool, process);
		Vertex root = pool.getVertices().get(0);
		translate(root, process);
	}

	private void translate(Vertex vertex, Process proc) {
		if (vertex instanceof SubProcess)
			translate((SubProcess) vertex, proc);
		else
			translate((Activity) vertex, proc);
	}

	private void translate(Activity act, Process proc) {
		System.out.printf("Activity %s\n", act.getName());
		if (act.getEAnnotation("DUMMY") != null)
			return;
		org.eclipse.bpel.model.Activity bpelAct = (org.eclipse.bpel.model.Activity)parseActivityAnnotations(act,
				proc);

		if (bpelAct == null) {
			bpelAct = BPELFactory.eINSTANCE.createEmpty();
		}

		bpelAct.setName(act.getName());
		contextAddChild(bpelAct);
		activityMap.put(act, bpelAct);
	}

	private void parsePoolAnnotations(Pool pool, Process proc) {
		EAnnotation annotation = pool.getEAnnotation("BPEL");
		if (annotation != null
				&& annotation.getDetails().get("annotation") != null) {
			try {
				String text = annotation.getDetails().get("annotation");
				StringReader reader = new StringReader(text);

				ANTLRReaderStream stream = new ANTLRReaderStream(reader);

				SimPELLexer lexer = new SimPELLexer(stream);
				LinkedListTokenSource linker = new LinkedListTokenSource(lexer);
				LinkedListTokenStream tokenStream = new LinkedListTokenStream(
						linker);

				SimPELParser parser = new SimPELParser(tokenStream);
				parser.setTreeAdaptor(new LinkedListTreeAdaptor());
				parser.setInput(lexer, stream);

				SimPELParser.program_return parsingResult = null;
				try {
					parsingResult = parser.program();
				} catch (RecognitionException e) {
					e.printStackTrace();
				}
				LinkedListTree t = (LinkedListTree) parsingResult.getTree();

				if (t != null) {
					CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
					SimPELWalker walker = new SimPELWalker(nodes);

					nodes.setTokenStream(tokenStream);
					try {
						walker.setBuilder(builder);
						walker.program();
					} catch (RecognitionException e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Object parseActivityAnnotations(
			Activity act, Process proc) {
		Object result = null;
		EAnnotation annotation = act.getEAnnotation("BPEL");
		if (annotation != null
				&& annotation.getDetails().get("annotation") != null) {
			try {
				String text = annotation.getDetails().get("annotation");
				StringReader reader = new StringReader(text);

				ANTLRReaderStream stream = new ANTLRReaderStream(reader);

				builder.resetAssign();

				SimPELLexer lexer = new SimPELLexer(stream);
				LinkedListTokenSource linker = new LinkedListTokenSource(lexer);
				LinkedListTokenStream tokenStream = new LinkedListTokenStream(
						linker);

				SimPELParser parser = new SimPELParser(tokenStream);
				parser.setTreeAdaptor(new LinkedListTreeAdaptor());
				parser.setInput(lexer, stream);

				SimPELParser.program_return parsingResult = null;
				try {
					parsingResult = parser.program();
				} catch (RecognitionException e) {
					e.printStackTrace();
				}
				LinkedListTree t = (LinkedListTree) parsingResult.getTree();

				if (t != null) {
					CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
					SimPELWalker walker = new SimPELWalker(nodes);

					nodes.setTokenStream(tokenStream);
					try {
						walker.setBuilder(builder);
						walker.program();
					} catch (RecognitionException e) {
						e.printStackTrace();
					}
					result = walker.result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private void translate(SubProcess sp, Process proc) {
		if (sp.getEAnnotation("Sequence") != null)
			translateSequence(sp, proc);
		else if (sp.getEAnnotation("StructuredBlock") != null)
			translateStructuredBlock(sp, proc);
		else if (sp.getEAnnotation("StructuredCycle") != null)
			translateStructuredCycle(sp, proc);
		else if (sp.getEAnnotation("UnstructuredBlock") != null)
			translateUnstructuredBlock(sp, proc);
		else if (sp.getEAnnotation("UnstructuredCyclicBlock") != null)
			translateUnstructuredCyclicBlock(sp, proc);
		else if (sp.getEAnnotation("UnstructuredCycle") != null)
			translateUnstructuredCycle(sp, proc);
	}

	private void translateSequence(SubProcess sp, Process proc) {
		System.out.printf("Sequence %s\n", sp.getName());
		// --- BPEL
		Sequence seq = BPELFactory.eINSTANCE.createSequence();
		seq.setName(sp.getName());
		contextAddChild(seq);
		activityMap.put(sp, seq);
		contextOpenScope(seq);

		// ---
		Vertex current = null;
		// Select the entry vertex
		for (Vertex v : sp.getVertices()) {
			if (v.getIncomingEdges().size() == 0) {
				current = v;
				break;
			}
		}

		// Traverse the sequence
		while (current != null) {
			translate(current, proc);
			if (current.getOutgoingEdges().size() > 0)
				current = current.getOutgoingEdges().get(0).getTarget();
			else
				current = null;
		}
		// --- BPEL
		contextCloseScope();
		// ---
	}

	private void error(String msg) {
		System.err.println(msg);
		Runtime.getRuntime().exit(-1);
	}

	// -----------------------------------------------------------------

	// ----------------------------------------------------------
	// -- Translation of Structured Cyclic Patterns
	// ----------------------------------------------------------

	private void translateStructuredCycle(SubProcess sp, Process proc) {
		Activity entry = null, exit = null;

		// Select the entry and exit vertices
		for (Vertex v : sp.getVertices()) {
			if (v.getIncomingEdges().size() == 0)
				entry = (Activity)v;
			if (v.getOutgoingEdges().size() == 0)
				exit = (Activity)v;
		}

		// Skip the Start Event
		entry = (Activity) entry.getOutgoingEdges().get(0).getTarget();
		exit = (Activity) exit.getIncomingEdges().get(0).getSource();

		if (entry.getOutgoingEdges().get(0).getTarget().equals(exit))
			translateWhile(sp, proc, entry, exit);
		else if (entry.getIncomingEdges().get(0).getSource().equals(exit))
			translateRepeat(sp, proc, entry, exit);
		else
			translateRepeatWhile(sp, proc, entry, exit);
	}

	private void translateWhile(SubProcess sp, Process proc, Activity entry,
			Activity exit) {
		System.out.printf("While %s", sp.getName());
		// --- BPEL
		While _while = BPELFactory.eINSTANCE.createWhile();

		// condition
		SequenceEdge loopEdge = null;

		for (SequenceEdge edge : exit.getOutgoingEdges())
			if (!edge.isIsDefault()) {
				loopEdge = edge;
				break;
			}

		Condition cond = createCondition(loopEdge);
		if (cond != null)
			_while.setCondition(cond);

		_while.setName(sp.getName());
		contextAddChild(_while);
		activityMap.put(sp, _while);
		contextOpenScope(_while);
		// ---
		translate(entry.getIncomingEdges().get(0).getSource(), proc);
		contextCloseScope();
	}

	private void translateRepeat(SubProcess sp, Process proc, Activity entry,
			Activity exit) {
		System.out.printf("Repeat %s", sp.getName());

		// --- BPEL
		RepeatUntil repeatUntil = BPELFactory.eINSTANCE.createRepeatUntil();

		// condition
		SequenceEdge loopEdge = null;

		for (SequenceEdge edge : exit.getOutgoingEdges())
			if (!edge.isIsDefault()) {
				loopEdge = edge;
				break;
			}

		Condition cond = createCondition(loopEdge);
		if (cond != null)
			repeatUntil.setCondition(cond);

		repeatUntil.setName(sp.getName());
		contextAddChild(repeatUntil);
		activityMap.put(sp, repeatUntil);
		contextOpenScope(repeatUntil);
		// ---
		translate(entry.getOutgoingEdges().get(0).getTarget(), proc);
		contextCloseScope();
	}

	private Condition createCondition(SequenceEdge edge) {
		Condition cond = null;
		if (edge != null) {
			EAnnotation annotation = edge.getEAnnotation("BPEL");
			if (annotation != null
					&& annotation.getDetails().get("annotation") != null) {
				String text = annotation.getDetails().get("annotation");
				cond = BPELFactory.eINSTANCE.createCondition();
				cond.setBody(text.substring(1, text.length() - 1));
			}
		}
		return cond;
	}

	private void translateRepeatWhile(SubProcess sp, Process proc,
			Activity entry, Activity exit) {
		System.out.printf("Repeat-While %s", sp.getName());
		// --- BPEL
		Sequence outerSeq = BPELFactory.eINSTANCE.createSequence();
		outerSeq.setName(sp.getName() + "_firstIter");

		contextAddChild(outerSeq);
		activityMap.put(sp, outerSeq);
		contextOpenScope(outerSeq);

		translate(entry.getOutgoingEdges().get(0).getTarget(), proc);

		While _while = BPELFactory.eINSTANCE.createWhile();

		// condition
		SequenceEdge loopEdge = null;

		for (SequenceEdge edge : exit.getOutgoingEdges())
			if (!edge.isIsDefault()) {
				loopEdge = edge;
				break;
			}

		Condition cond = createCondition(loopEdge);
		if (cond != null)
			_while.setCondition(cond);

		_while.setName(sp.getName());
		contextAddChild(_while);
		contextOpenScope(_while);
		// ---
		Sequence innerSeq = BPELFactory.eINSTANCE.createSequence();		
		contextAddChild(innerSeq);
		contextOpenScope(innerSeq);
		translate(entry.getIncomingEdges().get(0).getSource(), proc);
		translate(entry.getOutgoingEdges().get(0).getTarget(), proc);

		contextCloseScope(); // Inner sequence part

		contextCloseScope(); // While part

		contextCloseScope(); // Outer sequence part
	}

	/* ************************************************************** 
	 * ***		Translation of Structured Acyclic Patterns
	 * **************************************************************/
	private void translateStructuredBlock(SubProcess sp, Process proc) {
		Activity entry = null, exit = null;

		// Select the entry and exit vertices
		for (Vertex v : sp.getVertices()) {
			if (v.getIncomingEdges().size() == 0)
				entry = (Activity)v;
			if (v.getOutgoingEdges().size() == 0)
				exit = (Activity)v;
		}

		// Skip the Start Event
		entry = (Activity) entry.getOutgoingEdges().get(0).getTarget();
		switch (entry.getActivityType().getValue()) {
		case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
			translateIf(sp, proc, entry, exit);
			break;
		case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
			translatePick(sp, proc, entry, exit);
			break;
		case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
			error("OR split found ! Still unsupported!");
			break;
		case ActivityType.GATEWAY_PARALLEL:
			translateFlow(sp, proc, entry, exit);
			break;
		}
	}

	private void translateFlow(SubProcess sp, Process proc, Activity entry, Activity exit) {
		System.out.printf("Flow %s\n", sp.getName());
		// --- BPEL
		Flow flow = BPELFactory.eINSTANCE.createFlow();
		flow.setName(sp.getName());
		contextAddChild(flow);
		activityMap.put(sp, flow);
		contextOpenScope(flow);
		// ---
		for (SequenceEdge edge : entry.getOutgoingEdges())
			translate(edge.getTarget(), proc);
		// --- BPEL
		contextCloseScope();
		// ---
	}

	private void translatePick(SubProcess sp, Process proc, Activity entry, Activity exit) {
		System.out.printf("Pick %s\n", sp.getName());
		// --- BPEL
		Pick pick = BPELFactory.eINSTANCE.createPick();
		pick.setName(sp.getName());
		contextAddChild(pick);
		activityMap.put(sp, pick);
		contextOpenScope(pick);
		builder.setContext(pick);
		// ---
		for (SequenceEdge edge : entry.getOutgoingEdges()) {
			Activity event = (Activity)edge.getTarget(); 
			translateEvent(event, proc);
			Object bpelEvent = activityMap.get(event);
			contextAddChild(bpelEvent);
			contextOpenScope(bpelEvent);
			Activity next = (Activity)event.getOutgoingEdges().get(0).getTarget();
			if (next.getActivityType() == ActivityType.TASK_LITERAL ||
					next.getActivityType() == ActivityType.SUB_PROCESS_LITERAL)
				translate((Vertex)next, proc);
			contextCloseScope();
		}
		// --- BPEL
		contextCloseScope();
		builder.resetContext();
		// ---
	}


	private void translateEvent(Activity event, Process proc) {
		System.out.printf("Event %s\n", event.getName());
		Object bpelAct = parseActivityAnnotations(event,
				proc);
		if (bpelAct == null) {
			if (event.getActivityType() == ActivityType.EVENT_INTERMEDIATE_MESSAGE_LITERAL)
				if (builder.getContext() instanceof Pick) {
					bpelAct = BPELFactory.eINSTANCE.createOnMessage();
					PortType portType = new PortTypeProxy(URI.createURI("http://local"), new QName("aPorttype"));
					Operation operation = new OperationProxy(URI.createURI("http://local"), portType, event.getName());
					((OnMessage)bpelAct).setPortType(portType);
					((OnMessage)bpelAct).setOperation(operation);
				} else {
					bpelAct = BPELFactory.eINSTANCE.createOnEvent();
					PortType portType = new PortTypeProxy(URI.createURI("http://local"), new QName("aPorttype"));
					Operation operation = new OperationProxy(URI.createURI("http://local"), portType, event.getName());
					((OnEvent)bpelAct).setPortType(portType);
					((OnEvent)bpelAct).setOperation(operation);
				}
			else if (event.getActivityType() == ActivityType.EVENT_INTERMEDIATE_TIMER_LITERAL)
				bpelAct = BPELFactory.eINSTANCE.createOnAlarm();
		}
		contextAddChild(bpelAct);
		activityMap.put(event, bpelAct);
	}

	private void translateIf(SubProcess sp, Process proc, Activity entry, Activity exit) {
		System.out.printf("If %s\n", sp.getName());

		// --- BPEL
		If _if = BPELFactory.eINSTANCE.createIf();
		_if.setName(sp.getName());

		contextAddChild(_if);
		activityMap.put(sp, _if);
		contextOpenScope(_if);
		// ---

		boolean first = true;
		for (SequenceEdge edge : entry.getOutgoingEdges()) {
			Vertex act = edge.getTarget();
			Condition cond = createCondition(edge);

			if (edge.isIsDefault()) {
				Else _else = BPELFactory.eINSTANCE.createElse();
				contextAddChild(_else);
				contextOpenScope(_else);
				translate(act, proc);
				contextCloseScope();
			} else if (first) {
				if (cond != null)
					_if.setCondition(cond);
				translate(act, proc);
				first = false;
			} else {
				ElseIf elseIf = BPELFactory.eINSTANCE.createElseIf();
				if (cond != null)
					elseIf.setCondition(cond);
				contextAddChild(elseIf);
				contextOpenScope(elseIf);
				translate(act, proc);
				contextCloseScope();
			}
		}
		// --- BPEL
		contextCloseScope();
		// ---
	}

	// ----------------------------------------------------------
	// -- Translation of Unstructured Acyclic Patterns
	// ----------------------------------------------------------
	private void translateUnstructuredBlock(SubProcess sp, Process proc) {
		System.out.printf("Unstructured %s\n", sp.getName());

		HashMap<SequenceEdge, Link> linkMap = new HashMap<SequenceEdge, Link>();
		// --- BPEL
		Flow flow = BPELFactory.eINSTANCE.createFlow();
		flow.setName(sp.getName());
		contextAddChild(flow);
		activityMap.put(sp, flow);
		contextOpenScope(flow);
		// ---
		// Links container
		Links links = BPELFactory.eINSTANCE.createLinks();
		contextAddChild(links);
		contextOpenScope(links);
		for (SequenceEdge edge : sp.getSequenceEdges()) {
			String linkName = String.format("%s__%s", ((Activity)edge.getSource()).getName(),((Activity)edge.getTarget()).getName());
			System.out.printf("\tLink %s\n", linkName);
			// --- BPEL
			Link link = BPELFactory.eINSTANCE.createLink();
			link.setName(linkName);
			contextAddChild(link);
			linkMap.put(edge, link);
			// ---
		}
		contextCloseScope();
		// TODO: Use a depth-first traversal ??
		for (Vertex v : sp.getVertices()) {
			if (v.getIncomingEdges().size() == 0 ||
					v.getOutgoingEdges().size() == 0)
				continue;
			translate(v, proc);
			org.eclipse.bpel.model.Activity act = (org.eclipse.bpel.model.Activity)activityMap.get(v);
			if (v.getOutgoingEdges().size() > 0)
				act.setSources(BPELFactory.eINSTANCE.createSources());
			if (v.getIncomingEdges().size() > 0)
				act.setTargets(BPELFactory.eINSTANCE.createTargets());

			for (SequenceEdge edge : v.getOutgoingEdges()) {
				if (edge.getTarget() != null && edge.getTarget().getOutgoingEdges().size() == 0)
					continue;
				System.out.printf("\tsource %s->%s\n", ((Activity)edge.getSource()).getName(),((Activity)edge.getTarget()).getName());
				Source source = BPELFactory.eINSTANCE.createSource();
				source.setLink(linkMap.get(edge));
				act.getSources().getChildren().add(source);
			}

			for (SequenceEdge edge : v.getIncomingEdges()) {
				if (edge.getSource() != null && edge.getSource().getIncomingEdges().size() == 0)
					continue;
				System.out.printf("\ttarget %s->%s\n", ((Activity)edge.getSource()).getName(),((Activity)edge.getTarget()).getName());
				Target target = BPELFactory.eINSTANCE.createTarget();
				target.setLink(linkMap.get(edge));
				act.getTargets().getChildren().add(target);
			}
		}
		// --- BPEL
		contextCloseScope();
		// ---
	}

	// ----------------------------------------------------------
	// -- Translation of Unstructured Cyclic Patterns
	// ----------------------------------------------------------
	private void translateUnstructuredCyclicBlock(SubProcess sp, Process proc) {
		System.out.printf("Scope [UnstructuredCyclicBlock] %s\n", sp.getName());
		// --- BPEL
		Scope scope = BPELFactory.eINSTANCE.createScope();
		scope.setName(sp.getName());
		contextAddChild(scope);
		activityMap.put(sp, scope);
		contextOpenScope(scope);
		// ---
		for (Vertex v : sp.getVertices())
			translate(v, proc);
		// --- BPEL
		contextCloseScope();
		// ---
	}

	// ----------------------------------------------------------
	// -- Translation of Unstructured Cycles
	// ----------------------------------------------------------
	private void translateUnstructuredCycle(SubProcess sp, Process proc) {
		System.out.printf("EventHandlers %s\n", sp.getName());
		// --- BPEL
		EventHandler handlers = BPELFactory.eINSTANCE.createEventHandler();
		contextAddChild(handlers);
		contextOpenScope(handlers);
		// ---
		Vertex entry = null;
		for (Vertex v : sp.getVertices())
			if (v.getIncomingEdges().size() == 0) {
				entry = v;
				break;
			}

		for (SequenceEdge edge : entry.getOutgoingEdges()) {
			Activity event = (Activity)edge.getTarget();
			translateEvent(event, proc);
			Object bpelEvent = activityMap.get(event);
			contextAddChild(bpelEvent);
			contextOpenScope(bpelEvent);
			Activity next = (Activity)event.getOutgoingEdges().get(0).getTarget();
			if (next.getActivityType() == ActivityType.TASK_LITERAL ||
					next.getActivityType() == ActivityType.SUB_PROCESS_LITERAL) {
				translate((Vertex)next, proc);
			}
			contextCloseScope();			
		}
		// --- BPEL
		contextCloseScope();
		// ---
	}
}

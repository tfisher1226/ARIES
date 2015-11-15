// Copyright (C) 2008 Luciano Garcia-Banuelos <lgbanuelos@gmail.com>
// Licensed under the terms of the GNU GPL; see COPYING for details.
package bpelfactory;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.generation.impl.BPMNVisual2ProcessGenerator;

public class VisualGenerator extends BPMNVisual2ProcessGenerator {
	
	public VisualGenerator(IPath path, BpmnDiagram diagram) {
		super(path);
		for (Pool pool : diagram.getPools())
			generate(pool);
		
		generateViews();
		save();		
	}

	Map<Activity, Activity> map = new HashMap<Activity, Activity>();
	
	private void generate(Pool pool) {
		Pool newPool = addPool(pool.getName());
		for (Vertex vertex : pool.getVertices())
			generate(newPool, vertex);
		for (SequenceEdge edge : pool.getSequenceEdges()) {
			Activity source = map.get(edge.getSource());
			Activity target = map.get(edge.getTarget());
			addSequenceEdge(source, target, edge.getName());
		}
	}
	
	private void generate(SubProcess sub) {
		SubProcess newSub = (SubProcess)map.get(sub);
		for (Vertex vertex : sub.getVertices())
			generate(newSub, vertex);
		for (SequenceEdge edge : sub.getSequenceEdges()) {
			Activity source = map.get(edge.getSource());
			Activity target = map.get(edge.getTarget());
			if (source != null && target != null)
				addSequenceEdge(source, target, edge.getName());
		}
	}

	private void generate(Graph graph, Vertex vertex) {
		if (vertex instanceof Activity) {
			Activity act = (Activity)vertex;
			Activity newAct = addActivity(graph, act.getName(), act.getActivityType().getValue());
			map.put(act, newAct);
			if (act.getActivityType() == ActivityType.SUB_PROCESS_LITERAL)
				generate((SubProcess)act);

			for (Association assoc : act.getAssociations()) {
				if (assoc.getSource() instanceof TextAnnotation) {
					TextAnnotation annotation = (TextAnnotation) assoc.getSource();
					TextAnnotation newAnnotation = addTextAnnotation(graph, annotation.getName());
					addAssociation(newAnnotation, newAct);
				}
			}
		}
	}
}

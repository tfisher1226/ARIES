package aries.bpel;

import java.util.Iterator;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.emf.common.util.EList;

import aries.generation.engine.GenerationContext;


public class FlowGenerator extends AbstractBlockGenerator<Flow> {

	public FlowGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}
	
	public boolean isInsideFlow() {
		return true;
	}

	@Override
	public void generate(Flow flow) throws Exception {
		EList<Activity> activities = flow.getActivities();
		Iterator<Activity> iterator = activities.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Activity activity = iterator.next();
			generateActivity(activity);
		}
	}

	public void generateActivity(Activity activity) throws Exception {
		if (activity instanceof Assign) {
			generateActivity((Assign) activity);
//		} else if (activity instanceof Flow) {
//			generateActivity((Flow) activity);
//		} else if (activity instanceof If) {
//			generateActivity((If) activity);
		} else if (activity instanceof Invoke) {
			generateActivity((Invoke) activity);
		} else if (activity instanceof Receive) {
			generateActivity((Receive) activity);
		} else if (activity instanceof Reply) {
			generateActivity((Reply) activity);
		} else if (activity instanceof Sequence) {
			generateActivity((Sequence) activity);
		}
	}
	
}

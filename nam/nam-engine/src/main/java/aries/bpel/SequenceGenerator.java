package aries.bpel;

import java.util.Iterator;

import org.aries.util.NameUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.emf.common.util.EList;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class SequenceGenerator extends AbstractBlockGenerator<Sequence> {

	public SequenceGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}
	
	@Override
	public void generate(Sequence sequence) throws Exception {
		boolean inBlock = false;

		Targets targets = sequence.getTargets();
		if (targets != null) {
			EList<Target> targetChildren = targets.getChildren();
			Iterator<Target> targetIterator = targetChildren.iterator();
			while (targetIterator.hasNext()) {
				Target target = targetIterator.next();
				Link link = target.getLink();
				String name = NameUtil.getFilteredName(link.getName());
				Buf buf = new Buf();
				buf.putLine2("if ("+name+") {");
				addStatementSources(buf);
				inBlock = true;
			}
		}
		
		EList<Activity> activities = sequence.getActivities();
		Iterator<Activity> iterator = activities.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Activity activity = iterator.next();
			//TODO
			//if (i == 0)
			//	Assert.isInstanceOf(Receive.class, activity, "A ReceiveTask must be the first task");
			generateActivity(activity);
		}
		
		if (inBlock) {
			Buf buf = new Buf();
			buf.putLine2("}");
			addStatementSources(buf);
		}
	}
	
}

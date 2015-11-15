package aries.bpel;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;

import aries.generation.engine.GenerationContext;


public class ScopeGenerator extends AbstractBlockGenerator<Scope> {

	public ScopeGenerator(GenerationContext context, Process process) {
		super(context, process);
		this.verbose = true;
	}
	
	@Override
	public void generate(Scope scope) throws Exception {
		Activity activity = scope.getActivity();
		generateActivity(activity);
	}
	
}

package nam.service.src.test.java;

import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


public class ServiceClassMainProvider extends AbstractBeanProvider {

	public ServiceClassMainProvider(GenerationContext context) {
		super(context);
	}

	protected String generate(ModelClass modelClass) {
		modelClass.addImportedClass("org.aries.launcher.Bootstrap");
		modelClass.addImportedClass("org.aries.launcher.Launcher");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine3("Bootstrap.INSTANCE.initialize(RUNTIME_HOME);");
		buf.putLine3("Launcher launcher = new Launcher(HOST, PORT);");
		buf.putLine3("launcher.initialize(FILE_NAME);");
		buf.putLine3("launcher.launch();");
		buf.putLine2("} catch(Exception e) {");
		buf.putLine3("throw new RuntimeException(e);");
		buf.putLine2("}");
		return buf.get();
	}

}

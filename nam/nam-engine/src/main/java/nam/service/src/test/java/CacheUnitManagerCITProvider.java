package nam.service.src.test.java;

import nam.model.Unit;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


/**
 * Provides the source code for a DataUnit Manager CIT {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitManagerCITProvider extends AbstractDataUnitManagerCITProvider {

	public CacheUnitManagerCITProvider(GenerationContext context) {
		super(context);
	}

//	public String getTestAddAsList(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type dataItem) {
//		Buf buf = new Buf();
//		return null;
//	}

	protected String getSource_RunTestAsRunnable(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		//buf.putLine2("if (isTransactional()) {");
		//buf.putLine2("	beginTransaction();");
		//buf.putLine2("}");
		//buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	runnable.run();");
		buf.putLine2("	if (isExceptionExpected) {");
		buf.putLine2("		if (isTransactional())");
		buf.putLine2("			clearTransaction();");
		buf.putLine2("		errorMessage = \"Exception should have been thrown\";");
		buf.putLine2("	}");
		//buf.putLine2("	");
		//buf.putLine2("	if (isTransactional()) {");
		//buf.putLine2("		commitTransaction();");
		//buf.putLine2("	}");
		buf.putLine3("	");
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	if (isTransactional())");
		//buf.putLine2("		rollbackTransaction();");
		buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}
	
	protected String getSource_RunTestAsCallable(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	T object = callable.call();");
		buf.putLine2("	if (isExceptionExpected) {");
		buf.putLine2("		if (isTransactional())");
		buf.putLine2("			clearTransaction();");
		buf.putLine2("		errorMessage = \"Exception should have been thrown\";");
		buf.putLine2("	}");
		buf.putLine3("	return object;");
		buf.putLine3("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}
	
}

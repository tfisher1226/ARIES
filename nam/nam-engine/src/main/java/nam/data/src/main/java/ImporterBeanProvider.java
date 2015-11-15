package nam.data.src.main.java;

import nam.data.DataLayerHelper;
import nam.model.Element;
import aries.codegen.AbstractBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;


public class ImporterBeanProvider extends AbstractBeanProvider {

	public ImporterBeanProvider(GenerationContext context) {
		super(context);
	}
	
	public String getJavadoc(Element element) {
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		
		Buf buf = new Buf();
		buf.putLine2("/**");
		buf.putLine2(" * Provides an importer for {@link "+entityClassName+"} type data.");
		buf.putLine2(" *"); 
		buf.putLine2(" * @author "+context.getAuthor());
		buf.putLine2(" */");
		return buf.get();
	}

	
	/*
	 * execute() methods
	 */

	public String getSourceCode_Execute(Element element, Object object) {
		return "";
	}

}

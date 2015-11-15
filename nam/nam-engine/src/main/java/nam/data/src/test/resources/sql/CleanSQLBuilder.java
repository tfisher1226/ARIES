package nam.data.src.test.resources.sql;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Unit;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class CleanSQLBuilder extends AbstractDataLayerFileBuilder {

	public CleanSQLBuilder(GenerationContext context) {
		super(context);
	}
	
	protected ModelFile buildFile(boolean isTest, Unit unit) throws Exception {
		ModelFile modelFile = createTestResourcesFile("sql", "clean.sql");
		modelFile.setTextContent(getFileContent(isTest, unit));
		return modelFile;
	}
	
	protected String getFileContent(boolean isTest, Unit unit) throws Exception {
		Buf buf = new Buf();
		buf.putLine("SET FOREIGN_KEY_CHECKS = 0;");
		buf.putLine("set autocommit = 0;");
		List<Element> elements = UnitUtil.getAllElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementClassName = ModelLayerHelper.getElementClassName(element);
			String tableName = NameUtil.splitStringUsingUnderscores(elementClassName);
			tableName = tableName.toLowerCase();
			buf.putLine("delete from `"+tableName+"`;");
		}
		buf.putLine("commit;");
		return buf.get();
	}

}

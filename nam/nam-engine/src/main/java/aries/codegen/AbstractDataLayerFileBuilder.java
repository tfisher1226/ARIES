package aries.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Persistence;
import nam.model.Unit;
import nam.model.util.PersistenceUtil;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public abstract class AbstractDataLayerFileBuilder extends AbstractFileBuilder {

	protected static final int JPA_VERSION_1 = 1;

	protected static final int JPA_VERSION_2 = 2;

	protected int jpaVersion = 2;


	public AbstractDataLayerFileBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile(Persistence persistence) throws Exception {
		context.setPersistence(persistence);
		return buildFile(false, persistence);
	}
	
	public ModelFile buildFile(boolean isTest, Persistence persistence) throws Exception {
		//do nothing by default
		return null;
	}
	
	public ModelFile buildFile(Unit unit) throws Exception {
		return buildFile(false, unit); 
	}

	protected ModelFile buildFile(boolean isTest, Unit unit) throws Exception {
		//do nothing by default
		return null;
	}
	
	public List<ModelFile> buildFiles(Persistence persistence) throws Exception {
		context.setPersistence(persistence);
		return buildFiles(false, persistence);
	}
	
	public List<ModelFile> buildFiles(boolean isTest, Persistence persistence) throws Exception {
		List<ModelFile> modelFiles = new ArrayList<ModelFile>();
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			if (unit.getRef() != null)
				continue;
			modelFiles.add(buildFile(isTest, unit));
		}
		return modelFiles;
	}

	protected String getEntityManagerFactoryName(Unit unit) {
		String emfName = NameUtil.uncapName(unit.getName()) + "EntityManagerFactory";
		return emfName;
	}

	protected String getQueriesfileName(Unit unit) {
		Persistence persistence = context.getPersistence();
		Collection<Unit> units = PersistenceUtil.getUnits(persistence);
		if (units.size() == 1)
			return "queries.xml";
		String unitName = NameUtil.uncapName(unit.getName());
		//unitName = NameUtil.splitStringUsingUnderscores(unitName);
		String fileName = "queries-"+unitName+".xml";
		return fileName;
	}

}

package nam.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Import;
import nam.model.Model;
import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;

import org.aries.util.NameUtil;


@SessionScoped
@Named("modelDataManager")
public class ModelDataManager implements Serializable {
	
	@Inject
	private ModelEventManager modelEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Model> getModelList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getModelList((Project) owner);
		
		} else {
		return getDefaultList();
	}
	}
	
	protected Collection<Model> getModelList(Project project) {
		Collection<Model> models = new ArrayList<Model>();
		List<Import> imports = ProjectUtil.getImports(project);
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import _import = iterator.next();
			Model model = new Model();
			mergeModel(model, _import);
		}
		return models;
	}
	
	protected void mergeModel(Model model, Import _import) {
		model.setDir(_import.getDir());
		model.setFile(_import.getFile());
		model.setInclude(_import.getInclude());
		model.setNamespace(_import.getNamespace());
		model.setPrefix(_import.getPrefix());
		model.setType(_import.getType());
		model.setRef(_import.getRef());
		
		String fileName = model.getFile();
		String baseName = NameUtil.getBaseName(fileName);
		String modelName = NameUtil.splitStringUsingSpaces(baseName);
		model.setName(modelName);
		
		List<Import> imports = _import.getImports();
		Iterator<Import> iterator = imports.iterator();
		while (iterator.hasNext()) {
			Import import1 = iterator.next();
			Model importedModel = new Model();
			mergeModel(importedModel, import1);
		}
	}

	public Collection<Model> getDefaultList() {
		return null;
	}
	
	public void saveModel(Model model) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				//TODO ProjectUtil.addModel((Project) owner, model);
			}
		}
	}
	
	public boolean removeModel(Model model) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				//TODO return ProjectUtil.removeModel((Project) owner, model);
			}
		}
		return false;
	}
	
}

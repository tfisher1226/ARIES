package nam.model.library;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Dependency;
import nam.model.Library;
import nam.model.dependency.DependencyListManager;
import nam.model.dependency.DependencyListObject;
import nam.model.util.LibraryUtil;


@SessionScoped
@Named("libraryHelper")
public class LibraryHelper extends AbstractElementHelper<Library> implements Serializable {
	
	@Override
	public boolean isEmpty(Library library) {
		return LibraryUtil.isEmpty(library);
	}
	
	@Override
	public String toString(Library library) {
		return LibraryUtil.toString(library);
	}
	
	@Override
	public String toString(Collection<Library> libraryList) {
		return LibraryUtil.toString(libraryList);
	}
	
	@Override
	public boolean validate(Library library) {
		return LibraryUtil.validate(library);
	}
	
	@Override
	public boolean validate(Collection<Library> libraryList) {
		return LibraryUtil.validate(libraryList);
	}
	
	public DataModel<DependencyListObject> getDependencies(Library library) {
		if (library == null)
			return null;
		return getDependencies(library.getDependencies());
	}
	
	public DataModel<DependencyListObject> getDependencies(Collection<Dependency> dependenciesList) {
		DependencyListManager dependencyListManager = BeanContext.getFromSession("dependencyListManager");
		DataModel<DependencyListObject> dataModel = dependencyListManager.getDataModel(dependenciesList);
		return dataModel;
	}
	
	public DataModel<DependencyListObject> getExclusions(Library library) {
		if (library == null)
			return null;
		return getExclusions(library.getExclusions());
	}
	
	public DataModel<DependencyListObject> getExclusions(Collection<Dependency> exclusionsList) {
		DependencyListManager dependencyListManager = BeanContext.getFromSession("dependencyListManager");
		DataModel<DependencyListObject> dataModel = dependencyListManager.getDataModel(exclusionsList);
		return dataModel;
	}
	
}

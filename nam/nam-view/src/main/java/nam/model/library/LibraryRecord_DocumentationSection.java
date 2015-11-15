package nam.model.library;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Library;
import nam.model.util.LibraryUtil;


@SessionScoped
@Named("libraryDocumentationSection")
public class LibraryRecord_DocumentationSection extends AbstractWizardPage<Library> implements Serializable {
	
	private Library library;
	
	
	public LibraryRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
	}
	
	
	public Library getLibrary() {
		return library;
	}
	
	public void setLibrary(Library library) {
		this.library = library;
	}
	
	@Override
	public void initialize(Library library) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setLibrary(library);
	}
	
	@Override
	public void validate() {
		if (library == null) {
			validator.missing("Library");
		} else {
		}
	}
	
}

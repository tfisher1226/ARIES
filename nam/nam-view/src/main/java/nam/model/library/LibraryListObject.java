package nam.model.library;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Library;
import nam.model.util.LibraryUtil;


public class LibraryListObject extends AbstractListObject<Library> implements Comparable<LibraryListObject>, Serializable {
	
	private Library library;
	
	
	public LibraryListObject(Library library) {
		this.library = library;
	}
	
	
	public Library getLibrary() {
		return library;
	}
	
	@Override
	public Object getKey() {
		return getKey(library);
	}
	
	public Object getKey(Library library) {
		return LibraryUtil.getKey(library);
	}
	
	@Override
	public String getLabel() {
		return getLabel(library);
	}
	
	public String getLabel(Library library) {
		return LibraryUtil.getLabel(library);
	}
	
	@Override
	public String toString() {
		return toString(library);
	}
	
	@Override
	public String toString(Library library) {
		return LibraryUtil.toString(library);
	}
	
	@Override
	public int compareTo(LibraryListObject other) {
		Object thisKey = getKey(this.library);
		Object otherKey = getKey(other.library);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		LibraryListObject other = (LibraryListObject) object;
		Object thisKey = LibraryUtil.getKey(this.library);
		Object otherKey = LibraryUtil.getKey(other.library);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}

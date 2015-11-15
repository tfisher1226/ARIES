package nam.model.library;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.convert.AbstractConverter;

import nam.model.Library;
import nam.model.util.LibraryUtil;


@FacesConverter(value = "libraryConverter", forClass = Library.class)
public class LibraryConverter extends AbstractConverter implements Converter, Serializable {
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (StringUtils.isEmpty(value))
			return null;
		LibraryListManager libraryListManager = BeanContext.getFromSession("libraryListManager");
		Library library = libraryListManager.getRecordByName(value);
		return library;
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null)
			return null;
		Library library = (Library) value;
		return LibraryUtil.toString(library);
	}
	
}

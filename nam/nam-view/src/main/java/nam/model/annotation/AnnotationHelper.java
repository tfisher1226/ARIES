package nam.model.annotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;

import org.aries.ui.manager.AbstractElementHelper;
import org.aries.ui.model.ListTableModel;


@SessionScoped
@Named("annotationHelper")
public class AnnotationHelper extends AbstractElementHelper<Annotation> implements Serializable {
	
	@Override
	public boolean isEmpty(Annotation annotation) {
		return AnnotationUtil.isEmpty(annotation);
	}
	
	@Override
	public String toString(Annotation annotation) {
		return AnnotationUtil.toString(annotation);
	}
	
	@Override
	public String toString(Collection<Annotation> annotationList) {
		return AnnotationUtil.toString(annotationList);
	}
	
	@Override
	public boolean validate(Annotation annotation) {
		return AnnotationUtil.validate(annotation);
	}
	
	@Override
	public boolean validate(Collection<Annotation> annotationList) {
		return AnnotationUtil.validate(annotationList);
	}
	
	public DataModel<String> getDetails(Annotation annotation) {
		if (annotation == null)
			return null;
		return getDetails(annotation.getDetails());
	}
	
	public DataModel<String> getDetails(org.aries.common.Map details) {
		List<Object> values = Arrays.asList(details.getEntries().toArray());
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<Object>(values);
		return dataModel;
	}

	public DataModel<String> getDetails(Map<String, String> detailsMap) {
		List<String> values = new ArrayList<String>(detailsMap.values());
		@SuppressWarnings("unchecked") DataModel<String> dataModel = new ListTableModel<String>(values);
		return dataModel;
	}
	
}

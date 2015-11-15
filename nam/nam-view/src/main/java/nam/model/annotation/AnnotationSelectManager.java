package nam.model.annotation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;


@SessionScoped
@Named("annotationSelectManager")
public class AnnotationSelectManager extends AbstractSelectManager<Annotation, AnnotationListObject> implements Serializable {
	
	@Inject
	private AnnotationDataManager annotationDataManager;
	
	@Inject
	private AnnotationHelper annotationHelper;
	
	
	@Override
	public String getClientId() {
		return "annotationSelect";
	}
	
	@Override
	public String getTitle() {
		return "Annotation Selection";
	}
	
	@Override
	protected Class<Annotation> getRecordClass() {
		return Annotation.class;
	}
	
	@Override
	public boolean isEmpty(Annotation annotation) {
		return annotationHelper.isEmpty(annotation);
	}
	
	@Override
	public String toString(Annotation annotation) {
		return annotationHelper.toString(annotation);
	}
	
	protected AnnotationHelper getAnnotationHelper() {
		return BeanContext.getFromSession("annotationHelper");
	}
	
	protected AnnotationListManager getAnnotationListManager() {
		return BeanContext.getFromSession("annotationListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshAnnotationList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Annotation> recordList) {
		AnnotationListManager annotationListManager = getAnnotationListManager();
		DataModel<AnnotationListObject> dataModel = annotationListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshAnnotationList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Annotation> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Annotation> annotationList = BeanContext.getFromConversation(instanceId);
		return annotationList;
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Annotation> annotationList) {
		Collections.sort(annotationList, new Comparator<Annotation>() {
			public int compare(Annotation annotation1, Annotation annotation2) {
				String text1 = AnnotationUtil.toString(annotation1);
				String text2 = AnnotationUtil.toString(annotation2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

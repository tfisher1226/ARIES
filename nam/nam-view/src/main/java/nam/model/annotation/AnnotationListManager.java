package nam.model.annotation;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Annotation;
import nam.model.util.AnnotationUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("annotationListManager")
public class AnnotationListManager extends AbstractDomainListManager<Annotation, AnnotationListObject> implements Serializable {

	@Inject
	private AnnotationDataManager annotationDataManager;

	@Inject
	private AnnotationEventManager annotationEventManager;

	@Inject
	private AnnotationInfoManager annotationInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "annotationList";
	}

	@Override
	public String getTitle() {
		return "Annotation List";
	}

	@Override
	public Object getRecordKey(Annotation annotation) {
		return AnnotationUtil.getKey(annotation);
	}

	@Override
	public String getRecordName(Annotation annotation) {
		return AnnotationUtil.getLabel(annotation);
	}

	@Override
	protected Class<Annotation> getRecordClass() {
		return Annotation.class;
	}

	@Override
	protected Annotation getRecord(AnnotationListObject rowObject) {
		return rowObject.getAnnotation();
	}
	
	@Override
	public Annotation getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? AnnotationUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Annotation annotation) {
		super.setSelectedRecord(annotation);
		fireSelectedEvent(annotation);
	}
	
	protected void fireSelectedEvent(Annotation annotation) {
		annotationEventManager.fireSelectedEvent(annotation);
	}
	
	public boolean isSelected(Annotation annotation) {
		Annotation selection = selectionContext.getSelection("annotation");
		boolean selected = selection != null && selection.equals(annotation);
		return selected;
	}
	
	@Override
	protected AnnotationListObject createRowObject(Annotation annotation) {
		AnnotationListObject listObject = new AnnotationListObject(annotation);
		listObject.setSelected(isSelected(annotation));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}

	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Annotation> createRecordList() {
		try {
			Collection<Annotation> annotationList = annotationDataManager.getAnnotationList();
			if (annotationList != null)
				return annotationList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public String viewAnnotation() {
		return viewAnnotation(selectedRecordKey);
	}

	public String viewAnnotation(Object recordKey) {
		Annotation annotation = recordByKeyMap.get(recordKey);
		return viewAnnotation(annotation);
	}
	
	public String viewAnnotation(Annotation annotation) {
		String url = annotationInfoManager.viewAnnotation(annotation);
		return url;
	}

	public String editAnnotation() {
		return editAnnotation(selectedRecordKey);
	}

	public String editAnnotation(Object recordKey) {
		Annotation annotation = recordByKeyMap.get(recordKey);
		return editAnnotation(annotation);
	}

	public String editAnnotation(Annotation annotation) {
		String url = annotationInfoManager.editAnnotation(annotation);
		return url;
	}
	
	public void removeAnnotation() {
		removeAnnotation(selectedRecordKey);
	}
	
	public void removeAnnotation(Object recordKey) {
		Annotation annotation = recordByKeyMap.get(recordKey);
		removeAnnotation(annotation);
	}
	
	public void removeAnnotation(Annotation annotation) {
		try {
			if (annotationDataManager.removeAnnotation(annotation))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void cancelAnnotation(@Observes @Cancelled Annotation annotation) {
		try {
			//Object key = AnnotationUtil.getKey(annotation);
			//recordByKeyMap.put(key, annotation);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("annotation");
		} catch (Exception e) {
			handleException(e);
		}
	}

	public boolean validateAnnotation(Collection<Annotation> annotationList) {
		return AnnotationUtil.validate(annotationList);
	}
	
	public void exportAnnotationList(@Observes @Export String tableId) {
		//String tableId = "pageForm:annotationListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}

}

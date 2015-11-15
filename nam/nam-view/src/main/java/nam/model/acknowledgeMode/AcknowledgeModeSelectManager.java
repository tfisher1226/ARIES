package nam.model.acknowledgeMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.AcknowledgeMode;


@SessionScoped
@Named("acknowledgeModeSelectManager")
public class AcknowledgeModeSelectManager extends AbstractSelectManager<AcknowledgeMode, AcknowledgeModeListObject> implements Serializable {
	
	@Inject
	private AcknowledgeModeHelper acknowledgeModeHelper;
	
	
	@Override
	public String getClientId() {
		return "acknowledgeModeSelect";
	}
	
	@Override
	public String getTitle() {
		return "AcknowledgeMode Selection";
	}
	
	@Override
	protected Class<AcknowledgeMode> getRecordClass() {
		return AcknowledgeMode.class;
	}
	
	@Override
	public String toString(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	protected AcknowledgeModeHelper getAcknowledgeModeHelper() {
		return BeanContext.getFromSession("acknowledgeModeHelper");
	}
	
	protected AcknowledgeModeListManager getAcknowledgeModeListManager() {
		return BeanContext.getFromSession("acknowledgeModeListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshAcknowledgeModeList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<AcknowledgeMode> recordList) {
		AcknowledgeModeListManager acknowledgeModeListManager = getAcknowledgeModeListManager();
		DataModel<AcknowledgeModeListObject> dataModel = acknowledgeModeListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshAcknowledgeModeList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<AcknowledgeMode> refreshRecords() {
		AcknowledgeMode[] values = AcknowledgeMode.values();
		List<AcknowledgeMode> masterList = new ArrayList<AcknowledgeMode>();
		for (AcknowledgeMode capability : values) {
			masterList.add(capability);
		}
		return masterList;
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
	public void sortRecords(List<AcknowledgeMode> acknowledgeModeList) {
		Collections.sort(acknowledgeModeList);
	}
	
}

package nam.model.acknowledgeMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.AbstractDomainListManager;

import nam.model.AcknowledgeMode;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("acknowledgeModeListManager")
public class AcknowledgeModeListManager extends AbstractDomainListManager<AcknowledgeMode, AcknowledgeModeListObject> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "acknowledgeModeList";
	}
	
	@Override
	public String getTitle() {
		return "AcknowledgeMode List";
	}
	
	@Override
	public Object getRecordKey(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	@Override
	public String getRecordName(AcknowledgeMode acknowledgeMode) {
		return acknowledgeMode.name();
	}
	
	@Override
	protected Class<AcknowledgeMode> getRecordClass() {
		return AcknowledgeMode.class;
	}
	
	@Override
	protected AcknowledgeMode getRecord(AcknowledgeModeListObject rowObject) {
		return rowObject.getAcknowledgeMode();
	}
	
	@Override
	public AcknowledgeMode getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? selectedRecord.name() : null;
	}
	
	@Override
	public void setSelectedRecord(AcknowledgeMode acknowledgeMode) {
		super.setSelectedRecord(acknowledgeMode);
	}
	
	public boolean isSelected(AcknowledgeMode acknowledgeMode) {
		AcknowledgeMode selection = selectionContext.getSelection("acknowledgeMode");
		boolean selected = selection != null && selection.equals(acknowledgeMode);
		return selected;
	}
	
	@Override
	protected AcknowledgeModeListObject createRowObject(AcknowledgeMode acknowledgeMode) {
		AcknowledgeModeListObject listObject = new AcknowledgeModeListObject(acknowledgeMode);
		listObject.setSelected(isSelected(acknowledgeMode));
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
	protected Collection<AcknowledgeMode> createRecordList() {
		try {
			Collection<AcknowledgeMode> acknowledgeModeList = Arrays.asList(AcknowledgeMode.values());
			return acknowledgeModeList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
}

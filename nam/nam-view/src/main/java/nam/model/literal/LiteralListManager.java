package nam.model.literal;

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

import nam.model.Literal;
import nam.model.util.LiteralUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("literalListManager")
public class LiteralListManager extends AbstractDomainListManager<Literal, LiteralListObject> implements Serializable {
	
	@Inject
	private LiteralDataManager literalDataManager;
	
	@Inject
	private LiteralEventManager literalEventManager;
	
	@Inject
	private LiteralInfoManager literalInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "literalList";
	}
	
	@Override
	public String getTitle() {
		return "Literal List";
	}
	
	@Override
	public Object getRecordKey(Literal literal) {
		return LiteralUtil.getKey(literal);
	}
	
	@Override
	public String getRecordName(Literal literal) {
		return LiteralUtil.getLabel(literal);
	}
	
	@Override
	protected Class<Literal> getRecordClass() {
		return Literal.class;
	}
	
	@Override
	protected Literal getRecord(LiteralListObject rowObject) {
		return rowObject.getLiteral();
	}
	
	@Override
	public Literal getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? LiteralUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Literal literal) {
		super.setSelectedRecord(literal);
		fireSelectedEvent(literal);
	}
	
	protected void fireSelectedEvent(Literal literal) {
		literalEventManager.fireSelectedEvent(literal);
	}
	
	public boolean isSelected(Literal literal) {
		Literal selection = selectionContext.getSelection("literal");
		boolean selected = selection != null && selection.equals(literal);
		return selected;
	}
	
	@Override
	protected LiteralListObject createRowObject(Literal literal) {
		LiteralListObject listObject = new LiteralListObject(literal);
		listObject.setSelected(isSelected(literal));
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
	protected Collection<Literal> createRecordList() {
		try {
			Collection<Literal> literalList = literalDataManager.getLiteralList();
			if (literalList != null)
				return literalList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewLiteral() {
		return viewLiteral(selectedRecordKey);
	}
	
	public String viewLiteral(Object recordKey) {
		Literal literal = recordByKeyMap.get(recordKey);
		return viewLiteral(literal);
	}
	
	public String viewLiteral(Literal literal) {
		String url = literalInfoManager.viewLiteral(literal);
		return url;
	}
	
	public String editLiteral() {
		return editLiteral(selectedRecordKey);
	}
	
	public String editLiteral(Object recordKey) {
		Literal literal = recordByKeyMap.get(recordKey);
		return editLiteral(literal);
	}
	
	public String editLiteral(Literal literal) {
		String url = literalInfoManager.editLiteral(literal);
		return url;
	}
	
	public void removeLiteral() {
		removeLiteral(selectedRecordKey);
	}
	
	public void removeLiteral(Object recordKey) {
		Literal literal = recordByKeyMap.get(recordKey);
		removeLiteral(literal);
	}
	
	public void removeLiteral(Literal literal) {
		try {
			if (literalDataManager.removeLiteral(literal))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelLiteral(@Observes @Cancelled Literal literal) {
		try {
			//Object key = LiteralUtil.getKey(literal);
			//recordByKeyMap.put(key, literal);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("literal");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateLiteral(Collection<Literal> literalList) {
		return LiteralUtil.validate(literalList);
	}
	
	public void exportLiteralList(@Observes @Export String tableId) {
		//String tableId = "pageForm:literalListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}

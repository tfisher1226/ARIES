package nam.ui.graphics;

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

import nam.ui.Graphics;
import nam.ui.design.SelectionContext;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsListManager")
public class GraphicsListManager extends AbstractDomainListManager<Graphics, GraphicsListObject> implements Serializable {
	
	@Inject
	private GraphicsDataManager graphicsDataManager;
	
	@Inject
	private GraphicsEventManager graphicsEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "graphicsList";
	}
	
	@Override
	public String getTitle() {
		return "Graphics List";
	}
	
	@Override
	public Object getRecordKey(Graphics graphics) {
		return GraphicsUtil.getKey(graphics);
	}
	
	@Override
	public String getRecordName(Graphics graphics) {
		return GraphicsUtil.toString(graphics);
	}
	
	@Override
	protected Class<Graphics> getRecordClass() {
		return Graphics.class;
	}
	
	@Override
	protected Graphics getRecord(GraphicsListObject rowObject) {
		return rowObject.getGraphics();
	}
	
	@Override
	public Graphics getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? GraphicsUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Graphics graphics) {
		super.setSelectedRecord(graphics);
		fireSelectedEvent(graphics);
	}
	
	protected void fireSelectedEvent(Graphics graphics) {
		graphicsEventManager.fireSelectedEvent(graphics);
	}
	
	public boolean isSelected(Graphics graphics) {
		Graphics selection = selectionContext.getSelection("graphics");
		boolean selected = selection != null && selection.equals(graphics);
		return selected;
	}
	
	@Override
	protected GraphicsListObject createRowObject(Graphics graphics) {
		GraphicsListObject listObject = new GraphicsListObject(graphics);
		listObject.setSelected(isSelected(graphics));
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
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(recordList);
	}
	
	@Override
	protected Collection<Graphics> createRecordList() {
		try {
			Collection<Graphics> graphicsList = graphicsDataManager.getGraphicsList();
			if (graphicsList != null)
				return graphicsList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewGraphics() {
		return viewGraphics(selectedRecordKey);
	}
	
	public String viewGraphics(Object recordKey) {
		Graphics graphics = recordByKeyMap.get(recordKey);
		return viewGraphics(graphics);
	}
	
	public String viewGraphics(Graphics graphics) {
		GraphicsInfoManager graphicsInfoManager = BeanContext.getFromSession("graphicsInfoManager");
		String url = graphicsInfoManager.viewGraphics(graphics);
		return url;
	}
	
	public String editGraphics() {
		return editGraphics(selectedRecordKey);
	}
	
	public String editGraphics(Object recordKey) {
		Graphics graphics = recordByKeyMap.get(recordKey);
		return editGraphics(graphics);
	}
	
	public String editGraphics(Graphics graphics) {
		GraphicsInfoManager graphicsInfoManager = BeanContext.getFromSession("graphicsInfoManager");
		String url = graphicsInfoManager.editGraphics(graphics);
		return url;
	}
	
	public void removeGraphics() {
		removeGraphics(selectedRecordKey);
	}
	
	public void removeGraphics(Object recordKey) {
		Graphics graphics = recordByKeyMap.get(recordKey);
		removeGraphics(graphics);
	}
	
	public void removeGraphics(Graphics graphics) {
		try {
			if (graphicsDataManager.removeGraphics(graphics))
				clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelGraphics(@Observes @Cancelled Graphics graphics) {
		try {
			//Object key = GraphicsUtil.getKey(graphics);
			//recordByKeyMap.put(key, graphics);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("graphics");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateGraphics(Collection<Graphics> graphicsList) {
		return GraphicsUtil.validate(graphicsList);
	}
	
	public void exportGraphicsList(@Observes @Export String tableId) {
		//String tableId = "pageForm:graphicsListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}

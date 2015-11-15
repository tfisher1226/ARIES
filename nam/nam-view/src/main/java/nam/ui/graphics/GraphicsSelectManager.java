package nam.ui.graphics;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.ui.Graphics;
import nam.ui.util.GraphicsUtil;


@SessionScoped
@Named("graphicsSelectManager")
public class GraphicsSelectManager extends AbstractSelectManager<Graphics, GraphicsListObject> implements Serializable {
	
	@Inject
	private GraphicsDataManager graphicsDataManager;
	
	
	@Override
	public String getClientId() {
		return "graphicsSelect";
	}
	
	@Override
	public String getTitle() {
		return "Graphics Selection";
	}
	
	@Override
	protected Class<Graphics> getRecordClass() {
		return Graphics.class;
	}
	
	@Override
	public boolean isEmpty(Graphics graphics) {
		return getGraphicsHelper().isEmpty(graphics);
	}
	
	@Override
	public String toString(Graphics graphics) {
		return getGraphicsHelper().toString(graphics);
	}
	
	protected GraphicsHelper getGraphicsHelper() {
		return BeanContext.getFromSession("graphicsHelper");
	}
	
	protected GraphicsListManager getGraphicsListManager() {
		return BeanContext.getFromSession("graphicsListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshGraphicsList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Graphics> recordList) {
		GraphicsListManager graphicsListManager = getGraphicsListManager();
		DataModel<GraphicsListObject> dataModel = graphicsListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshGraphicsList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Graphics> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Graphics> graphicsList = BeanContext.getFromConversation(instanceId);
		return graphicsList;
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
	public void sortRecords(List<Graphics> graphicsList) {
		Collections.sort(graphicsList, new Comparator<Graphics>() {
			public int compare(Graphics graphics1, Graphics graphics2) {
				String text1 = GraphicsUtil.toString(graphics1);
				String text2 = GraphicsUtil.toString(graphics2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

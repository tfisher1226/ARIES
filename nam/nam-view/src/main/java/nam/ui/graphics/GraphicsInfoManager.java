package nam.ui.graphics;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Graphics;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.GraphicsUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("graphicsInfoManager")
public class GraphicsInfoManager extends AbstractNamRecordManager<Graphics> implements Serializable {
	
	@Inject
	private GraphicsWizard graphicsWizard;
	
	@Inject
	private GraphicsDataManager graphicsDataManager;
	
	@Inject
	private GraphicsPageManager graphicsPageManager;
	
	@Inject
	private GraphicsEventManager graphicsEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private GraphicsHelper graphicsHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public GraphicsInfoManager() {
		setInstanceName("graphics");
	}
	
	
	public Graphics getGraphics() {
		return getRecord();
	}
	
	public Graphics getSelectedGraphics() {
		return selectionContext.getSelection("graphics");
	}
	
	@Override
	public Class<Graphics> getRecordClass() {
		return Graphics.class;
	}
	
	@Override
	public boolean isEmpty(Graphics graphics) {
		return graphicsHelper.isEmpty(graphics);
	}
	
	@Override
	public String toString(Graphics graphics) {
		return graphicsHelper.toString(graphics);
	}
	
	protected GraphicsHelper getGraphicsHelper() {
		return BeanContext.getFromSession("graphicsHelper");
	}
	
	@Override
	public void initialize() {
		Graphics graphics = selectionContext.getSelection("graphics");
		if (graphics != null)
			initialize(graphics);
	}
	
	protected void initialize(Graphics graphics) {
		GraphicsUtil.initialize(graphics);
		graphicsWizard.initialize(graphics);
		setContext("graphics", graphics);
	}
	
	public void handleGraphicsSelected(@Observes @Selected Graphics graphics) {
		selectionContext.setSelection("graphics",  graphics);
		graphicsPageManager.updateState(graphics);
		graphicsPageManager.refreshMembers();
		setRecord(graphics);
	}
	
//	public void handleUserInterfaceSelected(@Observes @Selected UserInterface userInterface) {
//		graphicsPageManager.updateState(userInterface);
//	}
	
	@Override
	public String newRecord() {
		return newGraphics();
	}
	
	public String newGraphics() {
		try {
			Graphics graphics = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("graphics",  graphics);
			String url = graphicsPageManager.initializeGraphicsCreationPage(graphics);
			graphicsPageManager.pushContext(graphicsWizard);
			initialize(graphics);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Graphics create() {
		Graphics graphics = GraphicsUtil.create();
		return graphics;
	}
	
	@Override
	public Graphics clone(Graphics graphics) {
		graphics = GraphicsUtil.clone(graphics);
		return graphics;
	}
	
	@Override
	public String viewRecord() {
		return viewGraphics();
	}
	
	public String viewGraphics() {
		Graphics graphics = selectionContext.getSelection("graphics");
		String url = viewGraphics(graphics);
		return url;
	}
	
	public String viewGraphics(Graphics graphics) {
		try {
			String url = graphicsPageManager.initializeGraphicsSummaryView(graphics);
			graphicsPageManager.pushContext(graphicsWizard);
			initialize(graphics);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editGraphics();
	}
	
	public String editGraphics() {
		Graphics graphics = selectionContext.getSelection("graphics");
		String url = editGraphics(graphics);
		return url;
	}
	
	public String editGraphics(Graphics graphics) {
		try {
			//graphics = clone(graphics);
			selectionContext.resetOrigin();
			selectionContext.setSelection("graphics",  graphics);
			String url = graphicsPageManager.initializeGraphicsUpdatePage(graphics);
			graphicsPageManager.pushContext(graphicsWizard);
			initialize(graphics);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveGraphics() {
		Graphics graphics = getGraphics();
		if (validateGraphics(graphics)) {
			saveGraphics(graphics);
		}
	}
	
	public void persistGraphics(Graphics graphics) {
		saveGraphics(graphics);
	}
	
	public void saveGraphics(Graphics graphics) {
		try {
			saveGraphicsToSystem(graphics);
			graphicsEventManager.fireAddedEvent(graphics);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveGraphicsToSystem(Graphics graphics) {
		graphicsDataManager.saveGraphics(graphics);
	}
	
	public void handleSaveGraphics(@Observes @Add Graphics graphics) {
		saveGraphics(graphics);
	}
	
	public void enrichGraphics(Graphics graphics) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Graphics graphics) {
		return validateGraphics(graphics);
	}
	
	public boolean validateGraphics(Graphics graphics) {
		Validator validator = getValidator();
		boolean isValid = GraphicsUtil.validate(graphics);
		Display display = getFromSession("display");
		display.setModule("graphicsInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveGraphics() {
		display = getFromSession("display");
		display.setModule("graphicsInfo");
		Graphics graphics = selectionContext.getSelection("graphics");
		if (graphics == null) {
			display.error("Graphics record must be selected.");
		}
	}
	
	public String handleRemoveGraphics(@Observes @Remove Graphics graphics) {
		display = getFromSession("display");
		display.setModule("graphicsInfo");
		try {
			display.info("Removing Graphics "+GraphicsUtil.getLabel(graphics)+" from the system.");
			removeGraphicsFromSystem(graphics);
			selectionContext.clearSelection("graphics");
			graphicsEventManager.fireClearSelectionEvent();
			graphicsEventManager.fireRemovedEvent(graphics);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeGraphicsFromSystem(Graphics graphics) {
		if (graphicsDataManager.removeGraphics(graphics))
			setRecord(null);
	}
	
	public void cancelGraphics() {
		BeanContext.removeFromSession("graphics");
		graphicsPageManager.removeContext(graphicsWizard);
	}
	
}

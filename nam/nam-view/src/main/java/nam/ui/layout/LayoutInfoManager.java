package nam.ui.layout;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Layout;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.LayoutUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("layoutInfoManager")
public class LayoutInfoManager extends AbstractNamRecordManager<Layout> implements Serializable {
	
	@Inject
	private LayoutWizard layoutWizard;
	
	@Inject
	private LayoutDataManager layoutDataManager;
	
	@Inject
	private LayoutPageManager layoutPageManager;
	
	@Inject
	private LayoutEventManager layoutEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private LayoutHelper layoutHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LayoutInfoManager() {
		setInstanceName("layout");
	}
	
	
	public Layout getLayout() {
		return getRecord();
	}
	
	public Layout getSelectedLayout() {
		return selectionContext.getSelection("layout");
	}
	
	@Override
	public Class<Layout> getRecordClass() {
		return Layout.class;
	}
	
	@Override
	public boolean isEmpty(Layout layout) {
		return layoutHelper.isEmpty(layout);
	}
	
	@Override
	public String toString(Layout layout) {
		return layoutHelper.toString(layout);
	}
	
	protected LayoutHelper getLayoutHelper() {
		return BeanContext.getFromSession("layoutHelper");
	}
	
	@Override
	public void initialize() {
		Layout layout = selectionContext.getSelection("layout");
		if (layout != null)
			initialize(layout);
	}
	
	protected void initialize(Layout layout) {
		LayoutUtil.initialize(layout);
		layoutWizard.initialize(layout);
		setContext("layout", layout);
	}
	
	public void handleLayoutSelected(@Observes @Selected Layout layout) {
		selectionContext.setSelection("layout",  layout);
		layoutPageManager.updateState(layout);
		layoutPageManager.refreshMembers();
		setRecord(layout);
	}
	
	@Override
	public String newRecord() {
		return newLayout();
	}
	
	public String newLayout() {
		try {
			Layout layout = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("layout",  layout);
			String url = layoutPageManager.initializeLayoutCreationPage(layout);
			layoutPageManager.pushContext(layoutWizard);
			initialize(layout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Layout create() {
		Layout layout = LayoutUtil.create();
		return layout;
	}
	
	@Override
	public Layout clone(Layout layout) {
		layout = LayoutUtil.clone(layout);
		return layout;
	}
	
	@Override
	public String viewRecord() {
		return viewLayout();
	}
	
	public String viewLayout() {
		Layout layout = selectionContext.getSelection("layout");
		String url = viewLayout(layout);
		return url;
	}
	
	public String viewLayout(Layout layout) {
		try {
			String url = layoutPageManager.initializeLayoutSummaryView(layout);
			layoutPageManager.pushContext(layoutWizard);
			initialize(layout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editLayout();
	}
	
	public String editLayout() {
		Layout layout = selectionContext.getSelection("layout");
		String url = editLayout(layout);
		return url;
	}
	
	public String editLayout(Layout layout) {
		try {
			//layout = clone(layout);
			selectionContext.resetOrigin();
			selectionContext.setSelection("layout",  layout);
			String url = layoutPageManager.initializeLayoutUpdatePage(layout);
			layoutPageManager.pushContext(layoutWizard);
			initialize(layout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveLayout() {
		Layout layout = getLayout();
		if (validateLayout(layout)) {
			if (isImmediate())
				persistLayout(layout);
			outject("layout", layout);
		}
	}
	
	public void persistLayout(Layout layout) {
		saveLayout(layout);
	}
	
	public void saveLayout(Layout layout) {
		try {
			saveLayoutToSystem(layout);
			layoutEventManager.fireAddedEvent(layout);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveLayoutToSystem(Layout layout) {
		layoutDataManager.saveLayout(layout);
	}
	
	public void handleSaveLayout(@Observes @Add Layout layout) {
		saveLayout(layout);
	}
	
	public void addLayout(Layout layout) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichLayout(Layout layout) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Layout layout) {
		return validateLayout(layout);
	}
	
	public boolean validateLayout(Layout layout) {
		Validator validator = getValidator();
		boolean isValid = LayoutUtil.validate(layout);
		Display display = getFromSession("display");
		display.setModule("layoutInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveLayout() {
		display = getFromSession("display");
		display.setModule("layoutInfo");
		Layout layout = selectionContext.getSelection("layout");
		if (layout == null) {
			display.error("Layout record must be selected.");
		}
	}
	
	public String handleRemoveLayout(@Observes @Remove Layout layout) {
		display = getFromSession("display");
		display.setModule("layoutInfo");
		try {
			display.info("Removing Layout "+LayoutUtil.getLabel(layout)+" from the system.");
			removeLayoutFromSystem(layout);
			selectionContext.clearSelection("layout");
			layoutEventManager.fireClearSelectionEvent();
			layoutEventManager.fireRemovedEvent(layout);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeLayoutFromSystem(Layout layout) {
		if (layoutDataManager.removeLayout(layout))
			setRecord(null);
	}
	
	public void cancelLayout() {
		BeanContext.removeFromSession("layout");
		layoutPageManager.removeContext(layoutWizard);
	}
	
}

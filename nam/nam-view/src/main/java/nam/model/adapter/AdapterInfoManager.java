package nam.model.adapter;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Adapter;
import nam.model.Project;
import nam.model.util.AdapterUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("adapterInfoManager")
public class AdapterInfoManager extends AbstractNamRecordManager<Adapter> implements Serializable {
	
	@Inject
	private AdapterWizard adapterWizard;
	
	@Inject
	private AdapterDataManager adapterDataManager;
	
	@Inject
	private AdapterPageManager adapterPageManager;
	
	@Inject
	private AdapterEventManager adapterEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private AdapterHelper adapterHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public AdapterInfoManager() {
		setInstanceName("adapter");
	}
	
	
	public Adapter getAdapter() {
		return getRecord();
	}
	
	public Adapter getSelectedAdapter() {
		return selectionContext.getSelection("adapter");
	}
	
	@Override
	public Class<Adapter> getRecordClass() {
		return Adapter.class;
	}
	
	@Override
	public boolean isEmpty(Adapter adapter) {
		return adapterHelper.isEmpty(adapter);
	}
	
	@Override
	public String toString(Adapter adapter) {
		return adapterHelper.toString(adapter);
	}
	
	@Override
	public void initialize() {
		Adapter adapter = selectionContext.getSelection("adapter");
		if (adapter != null)
			initialize(adapter);
	}
	
	protected void initialize(Adapter adapter) {
		AdapterUtil.initialize(adapter);
		adapterWizard.initialize(adapter);
		setContext("adapter", adapter);
	}
	
	public void handleAdapterSelected(@Observes @Selected Adapter adapter) {
		selectionContext.setSelection("adapter",  adapter);
		adapterPageManager.updateState(adapter);
		adapterPageManager.refreshMembers();
		setRecord(adapter);
	}
	
	@Override
	public String newRecord() {
		return newAdapter();
	}
	
	public String newAdapter() {
		try {
			Adapter adapter = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("adapter",  adapter);
			String url = adapterPageManager.initializeAdapterCreationPage(adapter);
			adapterPageManager.pushContext(adapterWizard);
			initialize(adapter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Adapter create() {
		Adapter adapter = AdapterUtil.create();
		return adapter;
	}
	
	@Override
	public Adapter clone(Adapter adapter) {
		adapter = AdapterUtil.clone(adapter);
		return adapter;
	}
	
	@Override
	public String viewRecord() {
		return viewAdapter();
	}
	
	public String viewAdapter() {
		Adapter adapter = selectionContext.getSelection("adapter");
		String url = viewAdapter(adapter);
		return url;
	}
	
	public String viewAdapter(Adapter adapter) {
		try {
			String url = adapterPageManager.initializeAdapterSummaryView(adapter);
			adapterPageManager.pushContext(adapterWizard);
			initialize(adapter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editAdapter();
	}
	
	public String editAdapter() {
		Adapter adapter = selectionContext.getSelection("adapter");
		String url = editAdapter(adapter);
		return url;
	}
	
	public String editAdapter(Adapter adapter) {
		try {
			//adapter = clone(adapter);
			selectionContext.resetOrigin();
			selectionContext.setSelection("adapter",  adapter);
			String url = adapterPageManager.initializeAdapterUpdatePage(adapter);
			adapterPageManager.pushContext(adapterWizard);
			initialize(adapter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveAdapter() {
		Adapter adapter = getAdapter();
		if (validateAdapter(adapter)) {
			if (isImmediate())
				persistAdapter(adapter);
			outject("adapter", adapter);
		}
	}
	
	public void persistAdapter(Adapter adapter) {
		saveAdapter(adapter);
	}
	
	public void saveAdapter(Adapter adapter) {
		try {
			saveAdapterToSystem(adapter);
			adapterEventManager.fireAddedEvent(adapter);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveAdapterToSystem(Adapter adapter) {
		adapterDataManager.saveAdapter(adapter);
	}
	
	public void handleSaveAdapter(@Observes @Add Adapter adapter) {
		saveAdapter(adapter);
	}
	
	public void addAdapter(Adapter adapter) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichAdapter(Adapter adapter) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Adapter adapter) {
		return validateAdapter(adapter);
	}
	
	public boolean validateAdapter(Adapter adapter) {
		Validator validator = getValidator();
		boolean isValid = AdapterUtil.validate(adapter);
		Display display = getFromSession("display");
		display.setModule("adapterInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveAdapter() {
		display = getFromSession("display");
		display.setModule("adapterInfo");
		Adapter adapter = selectionContext.getSelection("adapter");
		if (adapter == null) {
			display.error("Adapter record must be selected.");
		}
	}
	
	public String handleRemoveAdapter(@Observes @Remove Adapter adapter) {
		display = getFromSession("display");
		display.setModule("adapterInfo");
		try {
			display.info("Removing Adapter "+AdapterUtil.getLabel(adapter)+" from the system.");
			removeAdapterFromSystem(adapter);
			selectionContext.clearSelection("adapter");
			adapterEventManager.fireClearSelectionEvent();
			adapterEventManager.fireRemovedEvent(adapter);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeAdapterFromSystem(Adapter adapter) {
		if (adapterDataManager.removeAdapter(adapter))
			setRecord(null);
	}
	
	public void cancelAdapter() {
		BeanContext.removeFromSession("adapter");
		adapterPageManager.removeContext(adapterWizard);
	}
	
}

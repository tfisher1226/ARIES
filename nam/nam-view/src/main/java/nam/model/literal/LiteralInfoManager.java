package nam.model.literal;

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

import nam.model.Literal;
import nam.model.Project;
import nam.model.util.LiteralUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("literalInfoManager")
public class LiteralInfoManager extends AbstractNamRecordManager<Literal> implements Serializable {
	
	@Inject
	private LiteralWizard literalWizard;
	
	@Inject
	private LiteralDataManager literalDataManager;
	
	@Inject
	private LiteralPageManager literalPageManager;
	
	@Inject
	private LiteralEventManager literalEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private LiteralHelper literalHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LiteralInfoManager() {
		setInstanceName("literal");
	}
	
	
	public Literal getLiteral() {
		return getRecord();
	}
	
	public Literal getSelectedLiteral() {
		return selectionContext.getSelection("literal");
	}
	
	@Override
	public Class<Literal> getRecordClass() {
		return Literal.class;
	}
	
	@Override
	public boolean isEmpty(Literal literal) {
		return literalHelper.isEmpty(literal);
	}
	
	@Override
	public String toString(Literal literal) {
		return literalHelper.toString(literal);
	}
	
	@Override
	public void initialize() {
		Literal literal = selectionContext.getSelection("literal");
		if (literal != null)
			initialize(literal);
	}
	
	protected void initialize(Literal literal) {
		LiteralUtil.initialize(literal);
		literalWizard.initialize(literal);
		setContext("literal", literal);
	}
	
	public void handleLiteralSelected(@Observes @Selected Literal literal) {
		selectionContext.setSelection("literal",  literal);
		literalPageManager.updateState(literal);
		literalPageManager.refreshMembers();
		setRecord(literal);
	}
	
	@Override
	public String newRecord() {
		return newLiteral();
	}
	
	public String newLiteral() {
		try {
			Literal literal = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("literal",  literal);
			String url = literalPageManager.initializeLiteralCreationPage(literal);
			literalPageManager.pushContext(literalWizard);
			initialize(literal);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Literal create() {
		Literal literal = LiteralUtil.create();
		return literal;
	}
	
	@Override
	public Literal clone(Literal literal) {
		literal = LiteralUtil.clone(literal);
		return literal;
	}
	
	@Override
	public String viewRecord() {
		return viewLiteral();
	}
	
	public String viewLiteral() {
		Literal literal = selectionContext.getSelection("literal");
		String url = viewLiteral(literal);
		return url;
	}
	
	public String viewLiteral(Literal literal) {
		try {
			String url = literalPageManager.initializeLiteralSummaryView(literal);
			literalPageManager.pushContext(literalWizard);
			initialize(literal);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editLiteral();
	}
	
	public String editLiteral() {
		Literal literal = selectionContext.getSelection("literal");
		String url = editLiteral(literal);
		return url;
	}
	
	public String editLiteral(Literal literal) {
		try {
			//literal = clone(literal);
			selectionContext.resetOrigin();
			selectionContext.setSelection("literal",  literal);
			String url = literalPageManager.initializeLiteralUpdatePage(literal);
			literalPageManager.pushContext(literalWizard);
			initialize(literal);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveLiteral() {
		Literal literal = getLiteral();
		if (validateLiteral(literal)) {
			saveLiteral(literal);
		}
	}
	
	public void persistLiteral(Literal literal) {
		saveLiteral(literal);
	}
	
	public void saveLiteral(Literal literal) {
		try {
			saveLiteralToSystem(literal);
			literalEventManager.fireAddedEvent(literal);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveLiteralToSystem(Literal literal) {
		literalDataManager.saveLiteral(literal);
	}
	
	public void handleSaveLiteral(@Observes @Add Literal literal) {
		saveLiteral(literal);
	}
	
	public void enrichLiteral(Literal literal) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Literal literal) {
		return validateLiteral(literal);
	}
	
	public boolean validateLiteral(Literal literal) {
		Validator validator = getValidator();
		boolean isValid = LiteralUtil.validate(literal);
		Display display = getFromSession("display");
		display.setModule("literalInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveLiteral() {
		display = getFromSession("display");
		display.setModule("literalInfo");
		Literal literal = selectionContext.getSelection("literal");
		if (literal == null) {
			display.error("Literal record must be selected.");
		}
	}
	
	public String handleRemoveLiteral(@Observes @Remove Literal literal) {
		display = getFromSession("display");
		display.setModule("literalInfo");
		try {
			display.info("Removing Literal "+LiteralUtil.getLabel(literal)+" from the system.");
			removeLiteralFromSystem(literal);
			selectionContext.clearSelection("literal");
			literalEventManager.fireClearSelectionEvent();
			literalEventManager.fireRemovedEvent(literal);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeLiteralFromSystem(Literal literal) {
		if (literalDataManager.removeLiteral(literal))
			setRecord(null);
	}
	
	public void cancelLiteral() {
		BeanContext.removeFromSession("literal");
		literalPageManager.removeContext(literalWizard);
	}
	
}

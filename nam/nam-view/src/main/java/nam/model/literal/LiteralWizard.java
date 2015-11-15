package nam.model.literal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Literal;
import nam.model.Project;
import nam.model.util.LiteralUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("literalWizard")
@SuppressWarnings("serial")
public class LiteralWizard extends AbstractDomainElementWizard<Literal> implements Serializable {
	
	@Inject
	private LiteralDataManager literalDataManager;
	
	@Inject
	private LiteralPageManager literalPageManager;
	
	@Inject
	private LiteralEventManager literalEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Literal";
	}
	
	@Override
	public String getUrlContext() {
		return literalPageManager.getLiteralWizardPage();
	}
	
	@Override
	public void initialize(Literal literal) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(literalPageManager.getSections());
		super.initialize(literal);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		literalPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		literalPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		literalPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		literalPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Literal literal = getInstance();
		literalDataManager.saveLiteral(literal);
		literalEventManager.fireSavedEvent(literal);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Literal literal = getInstance();
		//TODO take this out soon
		if (literal == null)
			literal = new Literal();
		literalEventManager.fireCancelledEvent(literal);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Literal literal = selectionContext.getSelection("literal");
		String name = literal.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("literalWizard");
			display.error("Literal name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

package nam.model.command;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Command;
import nam.model.Project;
import nam.model.util.CommandUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("commandWizard")
@SuppressWarnings("serial")
public class CommandWizard extends AbstractDomainElementWizard<Command> implements Serializable {
	
	@Inject
	private CommandDataManager commandDataManager;
	
	@Inject
	private CommandPageManager commandPageManager;
	
	@Inject
	private CommandEventManager commandEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Command";
	}
	
	@Override
	public String getUrlContext() {
		return commandPageManager.getCommandWizardPage();
	}
	
	@Override
	public void initialize(Command command) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(commandPageManager.getSections());
		super.initialize(command);
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
		commandPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		commandPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		commandPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		commandPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Command command = getInstance();
		commandDataManager.saveCommand(command);
		commandEventManager.fireSavedEvent(command);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Command command = getInstance();
		//TODO take this out soon
		if (command == null)
			command = new Command();
		commandEventManager.fireCancelledEvent(command);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Command command = selectionContext.getSelection("command");
		String name = command.getName().name();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("commandWizard");
			display.error("Command name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}

package nam.model.command;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Command;
import nam.model.util.CommandUtil;


@SessionScoped
@Named("commandIdentificationSection")
public class CommandRecord_IdentificationSection extends AbstractWizardPage<Command> implements Serializable {
	
	private Command command;
	
	
	public CommandRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Command getCommand() {
		return command;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	@Override
	public void initialize(Command command) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setCommand(command);
	}
	
	@Override
	public void validate() {
		if (command == null) {
			validator.missing("Command");
		} else {
		}
	}
	
}

package nam.model.literal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@SessionScoped
@Named("literalConfigurationSection")
public class LiteralRecord_ConfigurationSection extends AbstractWizardPage<Literal> implements Serializable {
	
	private Literal literal;
	
	
	public LiteralRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Literal getLiteral() {
		return literal;
	}
	
	public void setLiteral(Literal literal) {
		this.literal = literal;
	}
	
	@Override
	public void initialize(Literal literal) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setLiteral(literal);
	}
	
	@Override
	public void validate() {
		if (literal == null) {
			validator.missing("Literal");
		} else {
		}
	}
	
}

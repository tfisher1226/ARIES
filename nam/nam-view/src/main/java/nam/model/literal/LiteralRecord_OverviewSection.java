package nam.model.literal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@SessionScoped
@Named("literalOverviewSection")
public class LiteralRecord_OverviewSection extends AbstractWizardPage<Literal> implements Serializable {
	
	private Literal literal;
	
	
	public LiteralRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
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

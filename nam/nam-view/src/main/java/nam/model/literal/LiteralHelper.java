package nam.model.literal;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@SessionScoped
@Named("literalHelper")
public class LiteralHelper extends AbstractElementHelper<Literal> implements Serializable {
	
	@Override
	public boolean isEmpty(Literal literal) {
		return LiteralUtil.isEmpty(literal);
	}
	
	@Override
	public String toString(Literal literal) {
		return LiteralUtil.toString(literal);
	}
	
	@Override
	public String toString(Collection<Literal> literalList) {
		return LiteralUtil.toString(literalList);
	}
	
	@Override
	public boolean validate(Literal literal) {
		return LiteralUtil.validate(literal);
	}
	
	@Override
	public boolean validate(Collection<Literal> literalList) {
		return LiteralUtil.validate(literalList);
	}
	
}

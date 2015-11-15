package nam.model.literal;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Literal;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("literalEventManager")
public class LiteralEventManager extends AbstractEventManager<Literal> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Literal getInstance() {
		return selectionContext.getSelection("literal");
	}
	
	public void removeLiteral() {
		Literal literal = getInstance();
		fireRemoveEvent(literal);
	}
	
}

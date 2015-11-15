package nam.model.literal;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Literal;
import nam.model.util.LiteralUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("literalDataManager")
public class LiteralDataManager implements Serializable {
	
	@Inject
	private LiteralEventManager literalEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Literal> getLiteralList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Literal> getDefaultList() {
		return null;
	}
	
	public void saveLiteral(Literal literal) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeLiteral(Literal literal) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}

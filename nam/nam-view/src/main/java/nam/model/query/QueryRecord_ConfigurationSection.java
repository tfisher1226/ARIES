package nam.model.query;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Query;
import nam.model.util.QueryUtil;


@SessionScoped
@Named("queryConfigurationSection")
public class QueryRecord_ConfigurationSection extends AbstractWizardPage<Query> implements Serializable {
	
	private Query query;
	
	
	public QueryRecord_ConfigurationSection() {
		setName("Configuration");
		setUrl("configuration");
	}
	
	
	public Query getQuery() {
		return query;
	}
	
	public void setQuery(Query query) {
		this.query = query;
	}
	
	@Override
	public void initialize(Query query) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setQuery(query);
	}
	
	@Override
	public void validate() {
		if (query == null) {
			validator.missing("Query");
		} else {
		}
	}
	
}

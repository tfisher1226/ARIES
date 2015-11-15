package aries.generation.model.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ModelPersistenceUnit extends ModelObject {

	protected String name;

	protected Boolean transacted;
    
	protected String transactionType;
    
	//protected Schemas schemas;
    
	protected List<String> packages;
    
	protected ModelDataSource dataSource;
    
	protected List<ModelEntity> entities;
    
	protected List<ModelQuery> queries;
    
	protected Properties properties;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getTransacted() {
		if (transacted == null)
			transacted = true;
		return transacted;
	}

	public void setTransacted(Boolean transacted) {
		this.transacted = transacted;
	}

	public String getTransactionType() {
		if (transactionType == null)
			transactionType = "JTA";
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<String> getPackages() {
		if (packages == null)
			packages = new ArrayList<String>();
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	public ModelDataSource getDataSource() {
		if (dataSource == null)
			dataSource = new ModelDataSource();
		return dataSource;
	}

	public void setDataSource(ModelDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ModelEntity> getEntities() {
		if (entities == null)
			entities = new ArrayList<ModelEntity>();
		return entities;
	}

	public void setEntities(List<ModelEntity> entities) {
		this.entities = entities;
	}

	public List<ModelQuery> getQueries() {
		if (queries == null)
			queries = new ArrayList<ModelQuery>();
		return queries;
	}

	public void setQueries(List<ModelQuery> queries) {
		this.queries = queries;
	}

	public Properties getProperties() {
		if (properties == null)
			properties = new Properties();
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	
}

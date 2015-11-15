package nam.model.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Domain;
import nam.model.util.DomainUtil;


@SessionScoped
@Named("domainSelectManager")
public class DomainSelectManager extends AbstractSelectManager<Domain, DomainListObject> implements Serializable {
	
	@Inject
	private DomainDataManager domainDataManager;

	@Inject
	private DomainHelper domainHelper;
	
	
	@Override
	public String getClientId() {
		return "domainSelect";
	}
	
	@Override
	public String getTitle() {
		return "Domain Selection";
	}
	
	@Override
	protected Class<Domain> getRecordClass() {
		return Domain.class;
	}
	
	@Override
	public boolean isEmpty(Domain domain) {
		return domainHelper.isEmpty(domain);
	}
	
	@Override
	public String toString(Domain domain) {
		return domainHelper.toString(domain);
	}
	
	protected DomainHelper getDomainHelper() {
		return BeanContext.getFromSession("domainHelper");
	}
	
	protected DomainListManager getDomainListManager() {
		return BeanContext.getFromSession("domainListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshDomainList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Domain> recordList) {
		DomainListManager domainListManager = getDomainListManager();
		DataModel<DomainListObject> dataModel = domainListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshDomainList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Domain> refreshRecords() {
		try {
			Collection<Domain> records = domainDataManager.getDomainList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Domain> domainList) {
		Collections.sort(domainList, new Comparator<Domain>() {
			public int compare(Domain domain1, Domain domain2) {
				String text1 = DomainUtil.toString(domain1);
				String text2 = DomainUtil.toString(domain2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

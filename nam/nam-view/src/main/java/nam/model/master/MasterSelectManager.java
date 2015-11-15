package nam.model.master;

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

import nam.model.Master;
import nam.model.util.MasterUtil;


@SessionScoped
@Named("masterSelectManager")
public class MasterSelectManager extends AbstractSelectManager<Master, MasterListObject> implements Serializable {
	
	@Inject
	private MasterDataManager masterDataManager;
	
	@Inject
	private MasterHelper masterHelper;
	
	
	@Override
	public String getClientId() {
		return "masterSelect";
	}
	
	@Override
	public String getTitle() {
		return "Master Selection";
	}
	
	@Override
	protected Class<Master> getRecordClass() {
		return Master.class;
	}
	
	@Override
	public boolean isEmpty(Master master) {
		return masterHelper.isEmpty(master);
	}
	
	@Override
	public String toString(Master master) {
		return masterHelper.toString(master);
	}
	
	protected MasterHelper getMasterHelper() {
		return BeanContext.getFromSession("masterHelper");
	}
	
	protected MasterListManager getMasterListManager() {
		return BeanContext.getFromSession("masterListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshMasterList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Master> recordList) {
		MasterListManager masterListManager = getMasterListManager();
		DataModel<MasterListObject> dataModel = masterListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshMasterList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Master> refreshRecords() {
		try {
			Collection<Master> records = masterDataManager.getMasterList();
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
	public void sortRecords(List<Master> masterList) {
		Collections.sort(masterList, new Comparator<Master>() {
			public int compare(Master master1, Master master2) {
				String text1 = MasterUtil.toString(master1);
				String text2 = MasterUtil.toString(master2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

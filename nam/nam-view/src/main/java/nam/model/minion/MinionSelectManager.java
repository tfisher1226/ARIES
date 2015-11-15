package nam.model.minion;

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

import nam.model.Minion;
import nam.model.util.MinionUtil;


@SessionScoped
@Named("minionSelectManager")
public class MinionSelectManager extends AbstractSelectManager<Minion, MinionListObject> implements Serializable {
	
	@Inject
	private MinionDataManager minionDataManager;
	
	@Inject
	private MinionHelper minionHelper;
	
	
	@Override
	public String getClientId() {
		return "minionSelect";
	}
	
	@Override
	public String getTitle() {
		return "Minion Selection";
	}
	
	@Override
	protected Class<Minion> getRecordClass() {
		return Minion.class;
	}
	
	@Override
	public boolean isEmpty(Minion minion) {
		return minionHelper.isEmpty(minion);
	}
	
	@Override
	public String toString(Minion minion) {
		return minionHelper.toString(minion);
	}
	
	protected MinionHelper getMinionHelper() {
		return BeanContext.getFromSession("minionHelper");
	}
	
	protected MinionListManager getMinionListManager() {
		return BeanContext.getFromSession("minionListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshMinionList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Minion> recordList) {
		MinionListManager minionListManager = getMinionListManager();
		DataModel<MinionListObject> dataModel = minionListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshMinionList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Minion> refreshRecords() {
		try {
			Collection<Minion> records = minionDataManager.getMinionList();
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
	public void sortRecords(List<Minion> minionList) {
		Collections.sort(minionList, new Comparator<Minion>() {
			public int compare(Minion minion1, Minion minion2) {
				String text1 = MinionUtil.toString(minion1);
				String text2 = MinionUtil.toString(minion2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

package nam.model.literal;

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

import nam.model.Literal;
import nam.model.util.LiteralUtil;


@SessionScoped
@Named("literalSelectManager")
public class LiteralSelectManager extends AbstractSelectManager<Literal, LiteralListObject> implements Serializable {
	
	@Inject
	private LiteralDataManager literalDataManager;
	
	@Inject
	private LiteralHelper literalHelper;
	
	
	@Override
	public String getClientId() {
		return "literalSelect";
	}
	
	@Override
	public String getTitle() {
		return "Literal Selection";
	}
	
	@Override
	protected Class<Literal> getRecordClass() {
		return Literal.class;
	}
	
	@Override
	public boolean isEmpty(Literal literal) {
		return literalHelper.isEmpty(literal);
	}
	
	@Override
	public String toString(Literal literal) {
		return literalHelper.toString(literal);
	}
	
	protected LiteralHelper getLiteralHelper() {
		return BeanContext.getFromSession("literalHelper");
	}
	
	protected LiteralListManager getLiteralListManager() {
		return BeanContext.getFromSession("literalListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshLiteralList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Literal> recordList) {
		LiteralListManager literalListManager = getLiteralListManager();
		DataModel<LiteralListObject> dataModel = literalListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshLiteralList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Literal> refreshRecords() {
		String instanceId = getInstanceId();
		if (instanceId == null)
			return null;
		List<Literal> literalList = BeanContext.getFromConversation(instanceId);
		return literalList;
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
	public void sortRecords(List<Literal> literalList) {
		Collections.sort(literalList, new Comparator<Literal>() {
			public int compare(Literal literal1, Literal literal2) {
				String text1 = LiteralUtil.toString(literal1);
				String text2 = LiteralUtil.toString(literal2);
				return text1.compareTo(text2);
			}
		});
	}
	
}

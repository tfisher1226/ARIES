package org.aries.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;
import org.aries.ui.dialog.MessageDialog;
import org.aries.ui.util.SeamConversationHelper;


@SuppressWarnings("serial")
public abstract class AbstractWizard<T> extends AbstractViewManager implements Serializable {
	
	protected Log log = LogFactory.getLog(getClass());

	private T instance;
	
	private String name;

	private String title;

//	private String pageTitle;

	private String origin;
	
	private boolean newMode;
	
	private int pageIndex;

	private int pageCount;

	protected AbstractWizardPage<T> page;

	private List<AbstractWizardPage<T>> pages;

	private Map<String, AbstractWizardPage<T>> pageMap;

	protected SeamConversationHelper seamConversationHelper;
	
	private Object mutex = new Object();

	
	public AbstractWizard() {
		pages = new ArrayList<AbstractWizardPage<T>>();
		pageMap = new HashMap<String, AbstractWizardPage<T>>();
		initialize();
	}
	
	public T getInstance() {
		return instance;
	}

	public void initialize() {
		initializePages();
	}
	
	public void initialize(T instance) {
		this.instance = instance;
		initialize();
	}
	
	protected void initializePages() {
		Iterator<AbstractWizardPage<T>> iterator = getPages().iterator();
		while (iterator.hasNext()) {
			AbstractWizardPage<T> page = iterator.next();
			page.initialize(instance);
		}
	}

	public void clearPages() {
		pageMap.clear();
		pages.clear();
	}

	public void reset() {
		for (AbstractWizardPage<T> page: pages)
			page.setVisible(false);
		if (pages.size() > 0) {
			page = getPage(0);
			page.setVisible(true);
		} else page = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isNewMode() {
		return newMode;
	}

	public void setNewMode(boolean newMode) {
		this.newMode = newMode;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public List<AbstractWizardPage<T>> getPages() {
		return pages;
	}

	public Map<String, AbstractWizardPage<T>> getPageMap() {
		return pageMap;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageIndex(String name) {
		String key = name.toLowerCase();
		AbstractWizardPage<T> page = pageMap.get(key);
		return pages.indexOf(page);
	}

	public void setPageIndex(int index) {
		this.pageIndex = index;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int count) {
		this.pageCount = count;
	}

	public AbstractWizardPage<T> getPage() {
		return page;
	}

	public AbstractWizardPage<T> getPage(String name) {
		AbstractWizardPage<T> page = pageMap.get(name.toLowerCase());
		return page;
	}

	public void setPage(String name) {
		synchronized (mutex) {
			this.page = pageMap.get(name.toLowerCase());
			this.pageIndex = pages.indexOf(page);
		}
	}
	
	public void setPage(AbstractWizardPage<T> page) {
		synchronized (mutex) {
			this.page = page;
		}
	}

	public void setPage(int index) {
		synchronized (mutex) {
			this.page = getPage(index);
			this.pageIndex = index;
		}
	}
	
	public AbstractWizardPage<T> getPage(int index) {
		synchronized (mutex) {
			return pages.get(index);
		}
	}
	
//	public String getPageFileName() {
//		return page != null ? page.getFileName() : null;
//	}

	public AbstractWizardPage<T> getFirstPage() {
		synchronized (mutex) {
			page = getPage(0);
			page.setEnabled(true);
			page.setVisible(true);
			return page;
		}
	}
	
	public AbstractWizardPage<T> getPreviousPage() {
		synchronized (mutex) {
			page.setVisible(false);
			AbstractWizardPage<T> previousPage = null;
			if (pageIndex > 0)
				pageIndex--;
			previousPage = pages.get(pageIndex);
			page = previousPage;
			page.setVisible(true);
			return page;
		}
	}
	
	public AbstractWizardPage<T> getNextPage() {
		synchronized (mutex) {
			if (!page.isValid())
				return page;
			
			page.setVisible(false);
			AbstractWizardPage<T> nextPage = null; //page.getNextPage();
			
			//if (nextPage != null) {
			//	pageIndex = pages.indexOf(nextPage);
			//} else {
				if (pageIndex+1 == pageCount)
					pageIndex = 0;
				else pageIndex++;
				nextPage = pages.get(pageIndex);
			//}
			
			page = nextPage;
			page.setEnabled(true);
			page.setVisible(true);
			return page;
		}
	}
	
//	public AbstractWizardPage getNextPage() {
//	synchronized (mutex) {
//		getPage().setVisible(false);
//		if (pageIndex+1 == pageCount)
//			pageIndex = 0;
//		else pageIndex++;
//		AbstractWizardPage page = getPage();
//		if (page == null)
//			page = getPage().getNextPage();
//		page.setVisible(true);
//		return page;
//	}
//}

	public void addPage(AbstractWizardPage<T> page) {
		synchronized (mutex) {
			String key = page.getName().toLowerCase();
			pageMap.put(key, page);
			pages.add(page);
			pageCount++;
		}
	}
	
//	public void setPage(int pageIndex) {
//		synchronized (mutex) {
//			AbstractWizardPage page = pages.get(pageIndex);
//			page.setVisible(true);
//			this.pageTitle = page.getTitle();
//			this.pageIndex = pageIndex;
//		}
//	}

	public MessageDialog getMessageDialog() {
		MessageDialog messageDialog = BeanContext.get("messageDialog");
		messageDialog.reset();
		return messageDialog;
	}

	public boolean isBackVisible() {
		return page != null ? page.isBackVisible() && newMode : false;
	}

	public boolean isBackEnabled() {
		return page != null ? page.isBackEnabled() && newMode : false;
	}

	public boolean isNextVisible() {
		return page != null ? page.isNextVisible() && newMode : false;
	}

	public boolean isNextEnabled() {
		return page != null ? page.isNextEnabled() && newMode : false;
	}

	public boolean isFinishVisible() {
		return page != null ? page.isFinishVisible() : false;
	}

	public boolean isFinishEnabled() {
		return page != null ? page.isFinishEnabled() : false;
	}

	public boolean isPopulateVisible() {
		return page != null ? page.isPopulateVisible() && newMode : false;
	}

	public boolean isPopulateEnabled() {
		return page != null ? page.isPopulateEnabled() && newMode : false;
	}

	public String refresh() {
		return page != null ? page.getUrl() : null;
	}

	public String first() {
		return getPage().getUrl();
	}
	
	public String back() {
		AbstractWizardPage<T> previousPage = getPreviousPage();
		String url = previousPage.getUrl();
		if (url != null)
			return url;
		return previousPage.getUrl();
	}

	public String next() {
		return getNextPage().getUrl();
	}

	public void populate() {
		//nothing by default
	}
	
	public boolean validate() {
		//true by default
		return true;
	}
	
	public String finish() {
		if (!validate())
			return null;
		try {
			//TODO do any other completion logic here?
			seamConversationHelper.end();
			return null;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public String cancel() {
		if (seamConversationHelper == null)
			seamConversationHelper = BeanContext.getFromSession("seamConversationHelper");
		seamConversationHelper.end();
		return origin;
	}

}

package org.aries.ui.wizard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.aries.ui.AbstractPanelManager;
import org.aries.ui.util.SeamConversationHelper;


@SuppressWarnings("serial")
public abstract class AbstractWizard extends AbstractPanelManager implements Serializable {

	private int panelCount;

	private int panelIndex;

	private AbstractWizardPanel page;

	private List<AbstractWizardPanel> pages;

	@Inject
	protected SeamConversationHelper seamConversationHelper;
	
	private Object mutex = new Object();

	
	public AbstractWizard() {
		pages = new ArrayList<AbstractWizardPanel>();
		initialize();
	}
	
	public void initialize() {
		super.initialize();
	}
	
	public void reset() {
		super.reset();
		for (AbstractWizardPanel page: pages)
			page.setVisible(false);
		setPage(0);
	}

	public int getPanelCount() {
		return panelCount;
	}

	public void setPanelCount(int panelCount) {
		this.panelCount = panelCount;
	}

	public int getPanelIndex() {
		return panelIndex;
	}

	public void setPanelIndex(int panelIndex) {
		this.panelIndex = panelIndex;
	}

	public AbstractWizardPanel getPanel() {
		return page;
	}

	public void setPanel(AbstractWizardPanel page) {
		synchronized (mutex) {
			this.page = page;
		}
	}

	public AbstractWizardPanel getPage() {
		return page;
	}
	
	public void setPage(int index) {
		synchronized (mutex) {
			this.page = getPanel(index);
		}
	}
	
	public AbstractWizardPanel getPanel(int index) {
		synchronized (mutex) {
			if (pages.size() > index)
				return pages.get(index);
			return null;
		}
	}

	public AbstractWizardPanel getPreviousPage() {
		synchronized (mutex) {
			AbstractWizardPanel previousPage = null;
			if (page != null) {
				page.setVisible(false);
				previousPage = page.getPreviousPage();
			}
			
			if (previousPage != null) {
				panelIndex = pages.indexOf(previousPage);
			} else {
				if (panelIndex == 0)
					panelIndex = panelCount-1;
				else panelIndex--;
				previousPage = pages.get(panelIndex);
			}
			
			page = previousPage;
			page.setVisible(true);
			return page;
		}
	}
	
//	public AbstractWizardPage getNextPage() {
//		synchronized (mutex) {
//			getPage().setVisible(false);
//			if (pageIndex+1 == pageCount)
//				pageIndex = 0;
//			else pageIndex++;
//			AbstractWizardPage page = getPage();
//			if (page == null)
//				page = getPage().getNextPage();
//			page.setVisible(true);
//			return page;
//		}
//	}
	
	public AbstractWizardPanel getNextPage() {
		synchronized (mutex) {
			if (!page.isValid())
				return page;
			
			page.setVisible(false);
			AbstractWizardPanel nextPage = page.getNextPage();
			
			if (nextPage != null) {
				panelIndex = pages.indexOf(nextPage);
			} else {
				if (panelIndex+1 == panelCount)
					panelIndex = 0;
				else panelIndex++;
				nextPage = pages.get(panelIndex);
			}
			
			page = nextPage;
			page.setVisible(true);
			return page;
		}
	}
	
	public void addPage(AbstractWizardPanel page) {
		synchronized (mutex) {
			//page.setVisible(pageCount == 0);
			pages.add(page);
			panelCount++;
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

	public boolean isBackEnabled() {
		return page != null? page.isBackEnabled() : false;
	}

	public boolean isNextEnabled() {
		return page != null? page.isNextEnabled() : false;
	}

	public boolean isFinishEnabled() {
		return page != null? page.isFinishEnabled() : false;
	}

	public void back() {
		getPreviousPage();
	}

	public void next() {
		getNextPage();
	}

	public void populate() {
		//nothing by default
	}
	
	public boolean validate() {
		//true by default
		return true;
	}
	
	public void finish() {
		if (!validate())
			return;
		try {
			//TODO do any other completion logic here?
			seamConversationHelper.end();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public String cancel() {
		super.cancel();
		seamConversationHelper.end();
		return null;
	}

}

package nam.ui.wizard;

import java.io.Serializable;

import org.aries.ui.util.SeamConversationHelper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;


@SuppressWarnings("serial")
public abstract class AbstractManager implements Serializable {

	@Logger
	private Log log;

	private String title;

	private String panelTitle;

	private int panelIndex;

	private int panelCount;

	@In(required = true)
	protected SeamConversationHelper seamConversationHelper;
	
	
	public AbstractManager() {
		initialize();
	}
	
	public void initialize() {
		//nothing by default
	}
	
	protected void reset() {
		//nothing by default
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageTitle() {
		return panelTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.panelTitle = pageTitle;
	}

	public int getPanelIndex() {
		return panelIndex;
	}

	public void setPanelIndex(int panelIndex) {
		this.panelIndex = panelIndex;
	}

	public int getPanelCount() {
		return panelCount;
	}

	public void setPanelCount(int panelCount) {
		this.panelCount = panelCount;
	}

	public void populate() {
		//nothing by default
	}
	
	public boolean validate() {
		//true by default
		return true;
	}
	
	public void submit() {
		if (!validate())
			return;
		try {
			//TODO do any other completion logic here?
			seamConversationHelper.end();
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void cancel() {
		seamConversationHelper.end();
	}

}

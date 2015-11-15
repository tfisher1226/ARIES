package org.aries.ui.wizard;

import java.io.Serializable;

import javax.inject.Inject;

import org.aries.ui.AbstractPanelManager;
import org.aries.ui.util.SeamConversationHelper;


@SuppressWarnings("serial")
public abstract class AbstractManager extends AbstractPanelManager implements Serializable {

	private int panelCount;

	private int panelIndex;

	@Inject
	protected SeamConversationHelper seamConversationHelper;
	
	
	public AbstractManager() {
		initialize();
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

	public void populate() {
		//nothing by default
	}
	
	public boolean validate() {
		//true by default
		return true;
	}
	
	public String submit() {
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
		super.cancel();
		seamConversationHelper.end();
		return null;
	}

}

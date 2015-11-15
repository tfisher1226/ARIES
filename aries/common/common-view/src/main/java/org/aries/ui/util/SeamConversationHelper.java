package org.aries.ui.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;
import org.aries.util.ObjectUtil;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.contexts.Lifecycle;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.ConversationEntries;
import org.jboss.seam.core.ConversationEntry;


//@ApplicationScoped
//@Named("conversationHelper")

@Startup
@AutoCreate
@Name("seamConversationHelper")
@Scope(ScopeType.SESSION)
public class SeamConversationHelper implements Serializable {

    private static Log log = LogFactory.getLog(SeamConversationHelper.class);
    
    //@Inject
    //private Conversation conversation;  
    
    
	public void outject(Object instance) {
		String name = ObjectUtil.getDefaultName(instance);
		outject(name, instance);
	}

	public void outject(String name, Object instance) {
		Contexts.getSessionContext().remove(name);
		Contexts.getSessionContext().set(name, instance);
		BeanContext.set(name, instance);
	}

	public void end() {
		//TODO non-seam if (conversation == null)
		//TODO non-seam 	return;
		//TODO non-seam if (!conversation.isTransient())
		//TODO non-seam 	conversation.end();
		String id = Conversation.instance().getId();
		Conversation.instance().leave();
		Conversation.instance().end();
		Lifecycle.destroyConversationContext(getSessionMap(), id);
		ConversationEntries.instance().removeConversationEntry(id);
	}

    public void restart() {
    	String id = Conversation.instance().getId();
    	log.info("leave conversation: "+id);
    	Conversation.instance().leave();
    	Conversation.instance().begin();
    	Lifecycle.destroyConversationContext(getSessionMap(), id);
    	ConversationEntries.instance().removeConversationEntry(id);
    }
    
    private Map<String, Object> getSessionMap() {
    	Map<String, Object> session = new HashMap<String, Object>();
    	String[] names = Contexts.getSessionContext().getNames();
    	for (String name : names)
    		session.put(name, Contexts.getSessionContext().get(name));
    	return session;
    }

	public void logStatus() {
		//TODO non-seam String id = conversation.getId();
		String id = Conversation.instance().getId();
		String viewId = Conversation.instance().getViewId();
		String rootId = Conversation.instance().getRootId();
		String parentId = Conversation.instance().getParentId();
        
		Set<String> conversationIds = ConversationEntries.instance().getConversationIds();
		ConversationEntry conversationEntry = ConversationEntries.instance().getConversationEntry(id);
        //boolean lock = conversationEntry.lock();
        
		if (id == null)
			log.info("**** Initial page-load");
		else log.info("**** Conversation Id: ["+id+"]: isTransient="+conversationIds.toString());
		//else log.info("**** Conversation Id: ["+id+"]: isTransient="+conversation.isTransient());
	}

}


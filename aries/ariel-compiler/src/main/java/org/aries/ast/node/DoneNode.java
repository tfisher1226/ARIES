package org.aries.ast.node;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Done node class for Ariel Abstract Syntax Tree.
 * 
 *  
 */
public class DoneNode extends CommandNode {

	private boolean needsReturn;
	
	private List<String> serviceIds;
	
	
	public DoneNode(Object node) {
		super(node);
		serviceIds = new ArrayList<String>();
	}

	public boolean isNeedsReturn() {
		return needsReturn;
	}

	public void setNeedsReturn(boolean needsReturn) {
		this.needsReturn = needsReturn;
	}

	public List<String> getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(List<String> serviceIds) {
		this.serviceIds = serviceIds;
	}
	
	public void addServiceId(String serviceId) {
		this.serviceIds.add(serviceId);
	}

}

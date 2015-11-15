package nam.model.statement;

import java.util.List;

import nam.model.Command;


public class DoneCommand extends Command {

	private boolean needsReturn;

	private List<String> serviceIds;

	
	public DoneCommand() {
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

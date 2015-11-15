package nam.model.statement;

import java.util.ArrayList;
import java.util.List;


public class InvokeCommand extends SendCommand {
	
	private List<ResponseBlock> responseBlocks = new ArrayList<ResponseBlock>();

	
	public InvokeCommand() {
	}

	public List<ResponseBlock> getResponseBlocks() {
		return responseBlocks;
	}

	public void setResponseBlocks(List<ResponseBlock> responseBlocks) {
		this.responseBlocks = responseBlocks;
	}

	public void addResponseBlock(ResponseBlock responseBlock) {
		responseBlocks.add(responseBlock);
	}

	public void addResponseBlocks(List<ResponseBlock> responseBlocks) {
		this.responseBlocks.addAll(responseBlocks);
	}
	
}

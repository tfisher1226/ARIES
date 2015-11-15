package nam;

import java.io.Serializable;


@SuppressWarnings("serial")
public class GenerationRequest implements Serializable {

	private String targetHome;

	
	public String getTargetHome() {
		return targetHome;
	}

	public void setTargetHome(String targetHome) {
		this.targetHome = targetHome;
	}

}

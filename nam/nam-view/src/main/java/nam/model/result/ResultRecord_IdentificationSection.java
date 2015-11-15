package nam.model.result;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Result;
import nam.model.util.ResultUtil;


@SessionScoped
@Named("resultIdentificationSection")
public class ResultRecord_IdentificationSection extends AbstractWizardPage<Result> implements Serializable {
	
	private Result result;
	
	
	public ResultRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	@Override
	public void initialize(Result result) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setResult(result);
	}
	
	@Override
	public void validate() {
		if (result == null) {
			validator.missing("Result");
		} else {
		}
	}
	
}

package nam.model.result;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Result;
import nam.model.util.ResultUtil;


@SessionScoped
@Named("resultOverviewSection")
public class ResultRecord_OverviewSection extends AbstractWizardPage<Result> implements Serializable {
	
	private Result result;
	
	
	public ResultRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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

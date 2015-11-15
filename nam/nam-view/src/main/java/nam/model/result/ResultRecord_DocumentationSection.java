package nam.model.result;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import nam.model.Result;
import nam.model.util.ResultUtil;


@SessionScoped
@Named("resultDocumentationSection")
public class ResultRecord_DocumentationSection extends AbstractWizardPage<Result> implements Serializable {
	
	private Result result;
	
	
	public ResultRecord_DocumentationSection() {
		setName("Documentation");
		setUrl("documentation");
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
		setBackEnabled(true);
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

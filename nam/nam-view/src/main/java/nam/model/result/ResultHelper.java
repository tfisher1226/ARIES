package nam.model.result;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Result;
import nam.model.util.ResultUtil;


@SessionScoped
@Named("resultHelper")
public class ResultHelper extends AbstractElementHelper<Result> implements Serializable {
	
	@Override
	public boolean isEmpty(Result result) {
		return ResultUtil.isEmpty(result);
	}
	
	@Override
	public String toString(Result result) {
		return ResultUtil.toString(result);
	}
	
	@Override
	public String toString(Collection<Result> resultList) {
		return ResultUtil.toString(resultList);
	}
	
	@Override
	public boolean validate(Result result) {
		return ResultUtil.validate(result);
	}
	
	@Override
	public boolean validate(Collection<Result> resultList) {
		return ResultUtil.validate(resultList);
	}
	
}

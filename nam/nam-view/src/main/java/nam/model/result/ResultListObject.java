package nam.model.result;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import nam.model.Result;
import nam.model.util.ResultUtil;


public class ResultListObject extends AbstractListObject<Result> implements Comparable<ResultListObject>, Serializable {
	
	private Result result;
	
	
	public ResultListObject(Result result) {
		this.result = result;
	}
	
	
	public Result getResult() {
		return result;
	}
	
	@Override
	public Object getKey() {
		return getKey(result);
	}
	
	public Object getKey(Result result) {
		return ResultUtil.getKey(result);
	}
	
	@Override
	public String getLabel() {
		return getLabel(result);
	}
	
	public String getLabel(Result result) {
		return ResultUtil.getLabel(result);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Result16.gif";
	}
	
	@Override
	public String toString() {
		return toString(result);
	}
	
	@Override
	public String toString(Result result) {
		return ResultUtil.toString(result);
	}
	
	@Override
	public int compareTo(ResultListObject other) {
		Object thisKey = getKey(this.result);
		Object otherKey = getKey(other.result);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		ResultListObject other = (ResultListObject) object;
		Object thisKey = ResultUtil.getKey(this.result);
		Object otherKey = ResultUtil.getKey(other.result);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}

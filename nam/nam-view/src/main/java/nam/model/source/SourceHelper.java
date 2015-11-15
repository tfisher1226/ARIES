package nam.model.source;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Source;
import nam.model.util.SourceUtil;


@SessionScoped
@Named("sourceHelper")
public class SourceHelper extends AbstractElementHelper<Source> implements Serializable {
	
	@Override
	public boolean isEmpty(Source source) {
		return SourceUtil.isEmpty(source);
	}
	
	@Override
	public String toString(Source source) {
		return SourceUtil.toString(source);
	}
	
	@Override
	public String toString(Collection<Source> sourceList) {
		return SourceUtil.toString(sourceList);
	}
	
	@Override
	public boolean validate(Source source) {
		return SourceUtil.validate(source);
	}
	
	@Override
	public boolean validate(Collection<Source> sourceList) {
		return SourceUtil.validate(sourceList);
	}
	
}

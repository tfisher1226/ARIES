package nam.ui.layout;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.ui.Layout;
import nam.ui.util.LayoutUtil;


@SessionScoped
@Named("layoutHelper")
public class LayoutHelper extends AbstractElementHelper<Layout> implements Serializable {
	
	@Override
	public boolean isEmpty(Layout layout) {
		return LayoutUtil.isEmpty(layout);
	}
	
	@Override
	public String toString(Layout layout) {
		return LayoutUtil.toString(layout);
	}
	
	@Override
	public String toString(Collection<Layout> layoutList) {
		return LayoutUtil.toString(layoutList);
	}
	
	@Override
	public boolean validate(Layout layout) {
		return LayoutUtil.validate(layout);
	}
	
	@Override
	public boolean validate(Collection<Layout> layoutList) {
		return LayoutUtil.validate(layoutList);
	}
	
}

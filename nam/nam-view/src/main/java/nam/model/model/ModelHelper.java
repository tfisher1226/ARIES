package nam.model.model;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Model;
import nam.model.util.ModelUtil;


@SessionScoped
@Named("modelHelper")
public class ModelHelper extends AbstractElementHelper<Model> implements Serializable {
	
	@Override
	public boolean isEmpty(Model model) {
		return ModelUtil.isEmpty(model);
	}
	
	@Override
	public String toString(Model model) {
		return ModelUtil.toString(model);
	}
	
	@Override
	public String toString(Collection<Model> modelList) {
		return ModelUtil.toString(modelList);
	}
	
	@Override
	public boolean validate(Model model) {
		return ModelUtil.validate(model);
	}
	
	@Override
	public boolean validate(Collection<Model> modelList) {
		return ModelUtil.validate(modelList);
	}
	
}

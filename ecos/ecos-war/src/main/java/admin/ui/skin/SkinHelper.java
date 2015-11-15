package admin.ui.skin;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Skin;
import admin.util.SkinUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("skinHelper")
@Scope(ScopeType.SESSION)
public class SkinHelper extends AbstractElementHelper<Skin> implements Serializable {
	
	@Override
	public boolean isEmpty(Skin skin) {
		return SkinUtil.isEmpty(skin);
	}
	
	@Override
	public String toString(Skin skin) {
		return SkinUtil.toString(skin);
	}
	
	@Override
	public String toString(Collection<Skin> skinList) {
		return SkinUtil.toString(skinList);
	}
	
	@Override
	public boolean validate(Skin skin) {
		return SkinUtil.validate(skin);
	}
	
	@Override
	public boolean validate(Collection<Skin> skinList) {
		return SkinUtil.validate(skinList);
	}
	
}

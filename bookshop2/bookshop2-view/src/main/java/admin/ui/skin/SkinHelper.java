package admin.ui.skin;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.manager.AbstractElementHelper;

import admin.Skin;
import admin.util.SkinUtil;


@SessionScoped
@Named("skinHelper")
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

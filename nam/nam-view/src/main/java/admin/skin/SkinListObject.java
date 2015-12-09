package admin.skin;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Skin;
import admin.util.SkinUtil;


public class SkinListObject extends AbstractListObject<Skin> implements Comparable<SkinListObject>, Serializable {
	
	private Skin skin;
	
	
	public SkinListObject(Skin skin) {
		this.skin = skin;
	}
	
	
	public Skin getSkin() {
		return skin;
	}
	
	@Override
	public Object getKey() {
		return getKey(skin);
	}
	
	public Object getKey(Skin skin) {
		return SkinUtil.getKey(skin);
	}
	
	@Override
	public String getLabel() {
		return getLabel(skin);
	}
	
	public String getLabel(Skin skin) {
		return SkinUtil.getLabel(skin);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Skin16.gif";
	}
	
	@Override
	public String toString() {
		return toString(skin);
	}
	
	@Override
	public String toString(Skin skin) {
		return SkinUtil.toString(skin);
	}
	
	@Override
	public int compareTo(SkinListObject other) {
		Object thisKey = getKey(this.skin);
		Object otherKey = getKey(other.skin);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		SkinListObject other = (SkinListObject) object;
		Object thisKey = SkinUtil.getKey(this.skin);
		Object otherKey = SkinUtil.getKey(other.skin);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}

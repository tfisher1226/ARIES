package admin.ui.skin;

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
	public String getLabel() {
		return toString(skin);
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
		String thisText = toString(this.skin);
		String otherText = toString(other.skin);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		SkinListObject other = (SkinListObject) object;
		Long thisId = this.skin.getId();
		Long otherId = other.skin.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}
	
}

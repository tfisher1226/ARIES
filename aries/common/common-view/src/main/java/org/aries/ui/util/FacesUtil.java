package org.aries.ui.util;

import java.nio.ByteBuffer;

import javax.faces.context.FacesContext;

import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;


public class FacesUtil {

	public static String getViewParameter(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String value = (String) facesContext.getExternalContext().getRequestParameterMap().get(name);
		if (value != null && value.trim().length() > 0)
			return value;
		return null;
	}
	
    public static Object getSkinHashCode() {
    	return getSkinHashCode(FacesContext.getCurrentInstance());
    }
    
    public static Object getSkinHashCode(FacesContext context) {
        Skin skin = SkinFactory.getInstance(context).getSkin(context);
        return getSkinHashCode(context, skin);
    }

    public static Object getSkinHashCode(FacesContext context, Skin skin) {
		int hashCode = skin.hashCode(context);
        ByteBuffer buf = ByteBuffer.allocate(4);
		byte[] bs = buf.putInt(hashCode).array();
        return bs;
    }

}

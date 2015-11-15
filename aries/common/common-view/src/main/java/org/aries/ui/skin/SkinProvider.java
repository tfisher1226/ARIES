package org.aries.ui.skin;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;


import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.aries.ui.util.FacesUtil;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;



@SessionScoped
@Named("skinProvider")
@SuppressWarnings("serial")
public class SkinProvider implements Serializable {

	protected Skin getSkin() {
		return getSkin(FacesContext.getCurrentInstance());
	}

	protected Skin getSkin(FacesContext context) {
		return SkinFactory.getInstance(context).getSkin(context);
	}

	public String getSkinName() {
		return getSkin().getName();
	}

	//@Override
	public Object get(Object key) {
		return getParameter(key);
	}

	//@Override
	public Set entrySet() {
		return Collections.emptySet();
	}

	public boolean containsKey(String key) {
		return key != null ? getSkin().containsProperty(key.toString()) : null;
	}

	public boolean containsProperty(String name) {
		return getSkin().containsProperty(name);
	}

	public Object getParameter(Object name) {
		return name != null ? getParameter(name.toString()) : null;
	}

	public Object getParameter(String name) {
		return getSkin().getParameter(FacesContext.getCurrentInstance(), name);
	}

	public Object getParameter(FacesContext context, String name) {
		return getSkin().getParameter(context, name);
	}

	public Object getParameter(FacesContext context, String name, Object defaultValue) {
		return getSkin().getParameter(context, name, defaultValue);
	}


	public String getStringParameter(String name) {
		return (String) getSkin().getParameter(FacesContext.getCurrentInstance(), name);
	}

	public Integer getColorParameter(String name) {
		return getSkin().getColorParameter(FacesContext.getCurrentInstance(), name);
	}

	public Integer getColorParameter(FacesContext context, String name) {
		return getSkin().getColorParameter(context, name);
	}

	public Integer getColorParameter(FacesContext context, String name, Object defaultValue) {
		return getSkin().getColorParameter(context, name, defaultValue);
	}

	public Integer getIntegerParameter(FacesContext context, String name) {
		return getSkin().getIntegerParameter(context, name);
	}

	public Integer getIntegerParameter(FacesContext context, String name, Object defaultValue) {
		return getSkin().getIntegerParameter(context, name, defaultValue);
	}

	public static Object skinHashCode() {
		return FacesUtil.getSkinHashCode();
	}

	public int hashCode(FacesContext context) {
		return getSkin().hashCode(context);
	}

	@Override
	public String toString() {
		return getSkin().toString();
	}

}

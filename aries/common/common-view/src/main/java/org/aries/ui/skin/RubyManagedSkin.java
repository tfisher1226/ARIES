package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class RubyManagedSkin extends ManagedSkin implements Serializable {
	
	public RubyManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);
		
		setHighlightBorderColor("#f55757");
		setFocusBorderColor("#980808");
		setThemeBackgroundColor(getSkinParameter("additionalBackgroundColor"));
		setThemeBorderColor("#f55757");
	}
}

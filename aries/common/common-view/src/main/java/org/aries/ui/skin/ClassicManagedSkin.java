package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ClassicManagedSkin extends ManagedSkin implements Serializable {
	
	public ClassicManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);
		
		setHighlightBorderColor("#f55757");
		setFocusBorderColor("#980808");
		setThemeBackgroundColor(getSkinParameter("additionalBackgroundColor"));
		setThemeBorderColor("#f55757");
	}
}

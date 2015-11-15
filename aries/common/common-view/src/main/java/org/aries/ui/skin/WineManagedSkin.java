package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class WineManagedSkin extends ManagedSkin implements Serializable {
	
	public WineManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);
		
		setHighlightBorderColor("#f55757");
		setFocusBorderColor("#980808");
		setThemeBackgroundColor(getSkinParameter("additionalBackgroundColor"));
		setThemeBorderColor("#f55757");
	}
}

package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class JapanCherryManagedSkin extends ManagedSkin implements Serializable {
	
	public JapanCherryManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);
		
		setHighlightBorderColor("#f55757");
		setFocusBorderColor("#980808");
		setThemeBackgroundColor(getSkinParameter("additionalBackgroundColor"));
		setThemeBorderColor("#f55757");
		
		//fffce5 light pink
	}
}

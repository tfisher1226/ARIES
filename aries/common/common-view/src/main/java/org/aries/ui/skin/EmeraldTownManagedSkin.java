package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class EmeraldTownManagedSkin extends ManagedSkin implements Serializable {
	
	public EmeraldTownManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);
		
		setHighlightBorderColor("#f55757");
		setFocusBorderColor("#980808");
		setThemeBorderColor("#f55757");
	}
}

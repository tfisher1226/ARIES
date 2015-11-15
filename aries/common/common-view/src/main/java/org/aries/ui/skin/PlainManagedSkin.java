package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class PlainManagedSkin extends ManagedSkin implements Serializable {

	public PlainManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);

		setTabDisabledFontStyle("normal");
		setTabDisabledFontWeight("normal");

		setHighlightBorderColor("#3399ff");
		setFocusBorderColor("#0033bb");
		setThemeBorderColor("#3399ff");
	}
}

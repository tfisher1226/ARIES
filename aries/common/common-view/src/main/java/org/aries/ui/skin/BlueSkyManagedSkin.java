package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BlueSkyManagedSkin extends ManagedSkin implements Serializable {
	
	public BlueSkyManagedSkin(SkinProvider skinProvider) {
		super(skinProvider);

		//TODO setBackgroundColor("#ecf4fe");
		setToolbarBackgroundColor("#fff");

		setFontSize("12");
		setTabFontSize("12");
		setTextColor("black");
		setHeaderTextColor("darkBlue");

		setLinkColor("darkBlue");//0078D0
		setLinkHoverColor("blue");
		setLinkVisitedColor("blue");

		setHighlightBorderColor("#3399ff");
		setFocusBorderColor("#0033bb");
		setThemeBorderColor("#3399ff");
		
		setTabTextColor("black");
		setTabActiveTextColor("darkBlue");
		setTabInactiveTextColor("darkGray");
		setTabDisabledTextColor("gray");
	}
}

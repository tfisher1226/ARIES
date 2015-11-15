package org.aries.ui.skin;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ManagedSkin implements Serializable {

	private SkinProvider skinProvider;

	private String width;
	private String height;
	
	private String margin;
	private String marginTop;
	private String marginLeft;
	private String marginRight;
	private String marginBottom;
	
	private String padding;
	private String paddingTop;
	private String paddingLeft;
	private String paddingRight;
	private String paddingBottom;
	
	private String borderStyle;
	private String borderWidth;
	private String borderColor;
	private String cornerRadius;
	
	private String backgroundColor;
	private String backgroundImage;
	private String gradientColor;
	private String gradientImage;
	
	private String fontSize;
	private String fontFamily;
	private String fontWeight;
	private String fontStyle;
	private String fontVariant;
	private String textColor;
	private String textDecoration;

	private String linkColor;
	private String linkHoverColor;
	private String linkVisitedColor;

	private String buttonFontFamily;
	private String buttonFontSize;
	private String buttonFontStyle;
	private String buttonFontVariant;
	private String buttonFontWeight;
	private String buttonTextColor;
	private String buttonTextDecoration;

	private String buttonBackgroundColor;
	private String buttonBackgroundImage;
	private String buttonGradientColor;
	private String buttonBorderStyle;
	private String buttonBorderWidth;
	private String buttonBorderColor;
	private String buttonControlColor;
	private String buttonCornerRadius;

	private String headerBackgroundColor;
	private String headerBackgroundImage;
	private String headerGradientColor;
	private String headerBorderStyle;
	private String headerBorderWidth;
	private String headerBorderColor;
	private String headerFontFamily;
	private String headerFontSize;
	private String headerFontStyle;
	private String headerFontVariant;
	private String headerFontWeight;
	private String headerTextColor;
	private String headerTextDecoration;
	
	private String toolbarBackgroundColor;
	private String toolbarBackgroundImage;
	private String toolbarGradientColor;
	private String toolbarBorderColor;
	private String toolbarBorderStyle;
	private String toolbarBorderWidth;
	private String toolbarFontFamily;
	private String toolbarFontSize;
	private String toolbarFontStyle;
	private String toolbarFontVariant;
	private String toolbarFontWeight;
	private String toolbarTextColor;
	private String toolbarTextDecoration;
	
	private String tabBackgroundColor;
	private String tabBackgroundImage;
	private String tabGradientColor;
	private String tabBorderColor;
	private String tabCornerRadius;
	private String tabFontFamily;
	private String tabFontSize;
	private String tabFontStyle;
	private String tabFontWeight;
	private String tabTextColor;
	private String tabTextDecoration;

	private String tabActiveBackgroundColor;
	private String tabActiveBackgroundImage;
	private String tabActiveGradientColor;
	private String tabActiveBorderColor;
	private String tabActiveFontFamily;
	private String tabActiveFontSize;
	private String tabActiveFontStyle;
	private String tabActiveFontWeight;
	private String tabActiveTextColor;
	private String tabActiveTextDecoration;
	
	private String tabInactiveBackgroundColor;
	private String tabInactiveBackgroundImage;
	private String tabInactiveGradientColor;
	private String tabInactiveBorderColor;
	private String tabInactiveFontFamily;
	private String tabInactiveFontSize;
	private String tabInactiveFontStyle;
	private String tabInactiveFontWeight;
	private String tabInactiveTextColor;
	private String tabInactiveTextDecoration;

	private String tabDisabledBackgroundColor;
	private String tabDisabledBackgroundImage;
	private String tabDisabledGradientColor;
	private String tabDisabledBorderColor;
	private String tabDisabledFontFamily;
	private String tabDisabledFontSize;
	private String tabDisabledFontStyle;
	private String tabDisabledFontWeight;
	private String tabDisabledTextColor;
	private String tabDisabledTextDecoration;

	private String tableBackgroundColor;
	private String tableBorderColor;
	private String tableBorderWidth;

	private String tableCellBackgroundColor;
	private String tableCellBackgroundImage;
	private String tableCellGradientColor;
	private String tableCellBorderColor;
	private String tableCellFontFamily;
	private String tableCellFontSize;
	private String tableCellFontStyle;
	private String tableCellFontVariant;
	private String tableCellFontWeight;
	private String tableCellTextColor;
	private String tableCellTextDecoration;

	private String tableHeaderBackgroundColor;
	private String tableHeaderBackgroundImage;
	private String tableHeaderGradientColor;
	private String tableHeaderBorderColor;
	private String tableHeaderFontFamily;
	private String tableHeaderFontSize;
	private String tableHeaderFontStyle;
	private String tableHeaderFontVariant;
	private String tableHeaderFontWeight;
	private String tableHeaderTextColor;
	private String tableHeaderTextDecoration;

	private String tableFooterBackgroundColor;
	private String tableFooterBackgroundImage;
	private String tableFooterGradientColor;
	private String tableFooterBorderColor;
	private String tableFooterFontFamily;
	private String tableFooterFontSize;
	private String tableFooterFontStyle;
	private String tableFooterFontVariant;
	private String tableFooterFontWeight;
	private String tableFooterTextColor;
	private String tableFooterTextDecoration;

	private String tableSubHeaderBackgroundColor;
	private String tableSubFooterBackgroundColor;

	private String tooltipBackgroundColor;
	private String tooltipBackgroundImage;
	private String tooltipGradientColor;
	private String tooltipBorderColor;
	private String tooltipBorderStyle;
	private String tooltipBorderWidth;
	private String tooltipFontFamily;
	private String tooltipFontSize;
	private String tooltipFontStyle;
	private String tooltipFontVariant;
	private String tooltipFontWeight;
	private String tooltipTextColor;
	private String tooltipTextDecoration;

	private String highlightBackgroundColor;
	private String highlightBackgroundImage;
	private String highlightBorderStyle;
	private String highlightBorderWidth;
	private String highlightBorderColor;
	private String highlightFontFamily;
	private String highlightFontSize;
	private String highlightFontStyle;
	private String highlightFontWeight;
	private String highlightTextColor;
	private String highlightTextDecoration;
	//private String highlightControlColor;
	
	private String focusBackgroundColor;
	private String focusBackgroundImage;
	private String focusGradientColor;
	private String focusBorderStyle;
	private String focusBorderWidth;
	private String focusBorderColor;
	private String focusFontFamily;
	private String focusFontSize;
	private String focusFontStyle;
	private String focusFontWeight;
	private String focusTextColor;
	private String focusTextDecoration;
	//private String focusControlColor;

	private String disabledBackgroundColor;
	private String disabledBackgroundImage;
	private String disabledBorderStyle;
	private String disabledBorderWidth;
	private String disabledBorderColor;
	private String disabledFontFamily;
	private String disabledFontSize;
	private String disabledFontStyle;
	private String disabledFontWeight;
	private String disabledTextColor;
	private String disabledTextDecoration;
	//private String disabledControlColor;
	
	private String themeBorderColor;
	private String themeBackgroundColor;
	
	
	
	protected ManagedSkin(SkinProvider skinProvider) {
		this.skinProvider = skinProvider;
		
		setWidth("auto");
		setHeight("auto");

		setMargin("0px");
		setMarginTop("0px");
		setMarginLeft("0px");
		setMarginRight("0px");
		setMarginBottom("0px");

		setPadding("0px");
		setPaddingTop("0px");
		setPaddingLeft("0px");
		setPaddingRight("0px");
		setPaddingBottom("0px");

		setBorderStyle("solid");
		setBorderWidth("0px");
		setBorderColor(getSkinParameter("panelBorderColor", "#bbb"));
		setCornerRadius(getSkinParameter("panelRadiusCorner"));

		setBackgroundColor(getSkinParameter("generalBackgroundColor"));
		setBackgroundImage("none");
		setGradientColor(getSkinParameter("headerGradientColor"));
		setGradientImage("none");

		setFontFamily(getSkinParameter("generalFamilyFont"));
		setFontSize(getSkinParameter("generalSizeFont"));
		setFontStyle("inherit");
		setFontWeight("normal");
		setTextColor(getSkinParameter("generalTextColor"));
		setTextDecoration("none");

		//1f78d0
		setLinkColor(getSkinParameter("generalLinkColor"));//0078D0
		setLinkHoverColor(getSkinParameter("hoverLinkColor"));//0090FF
		setLinkVisitedColor(getSkinParameter("visitedLinkColor"));

		setButtonBackgroundColor("inherit");
		setButtonBackgroundImage("none");
		setButtonGradientColor(getSkinParameter("headerGradientColor"));
		setButtonBorderColor("#bbb");
		setButtonBorderStyle("solid");
		setButtonBorderWidth("0px");
		//setButtonControlColor("#0033bb");
		setButtonCornerRadius(getSkinParameter("buttonRadiusCorner", "5px"));
		//setButtonCornerRadius("3px");

		setButtonFontFamily(getSkinParameter("buttonFamilyFont"));
		setButtonFontSize(getSkinParameter("buttonSizeFont"));
		setButtonFontStyle(getSkinParameter("buttonStyleFont"));
		setButtonFontVariant("normal");
		setButtonFontWeight(getSkinParameter("buttonWeightFont"));
		setButtonTextColor("black");
		setButtonTextDecoration("none");

		setHeaderFontFamily(getSkinParameter("headerFamilyFont"));
		setHeaderFontSize(getSkinParameter("headerSizeFont"));
		setHeaderFontStyle("normal");
		setHeaderFontVariant("normal");
		setHeaderFontWeight(getSkinParameter("headerWeightFont"));
		setHeaderTextColor(getSkinParameter("headerTextColor"));
		setHeaderTextDecoration("none");

		setHeaderBackgroundColor(getSkinParameter("headerBackgroundColor"));
		setHeaderBackgroundImage("none");
		setHeaderGradientColor(getSkinParameter("headerGradientColor"));
		setHeaderBorderColor(getSkinParameter("panelBorderColor", "#0033bb"));
		setHeaderBorderStyle("solid");
		setHeaderBorderWidth("0px");

		setToolbarBackgroundColor(getSkinParameter("tableBackgroundColor"));
		setToolbarBackgroundImage("none");
		setToolbarGradientColor(getSkinParameter("headerGradientColor"));
		setToolbarBorderColor(getSkinParameter("tableBorderColor"));
		setToolbarBorderStyle("solid");
		setToolbarBorderWidth("0px");
		setToolbarFontFamily("Arial, Verdana, sans-serif");
		setToolbarFontSize("14px");
		setToolbarFontStyle("normal");
		//setToolbarFontVariant("normal");
		setToolbarFontWeight("normal");
		setToolbarTextColor(getSkinParameter("generalTextColor"));
		setToolbarTextDecoration("none");
		
		setTabBackgroundColor(getSkinParameter("tabBackgroundColor"));
		setTabBackgroundImage("none");
		setTabBorderColor(getSkinParameter("panelBorderColor"));
		//setTabBorderStyle("solid");
		//setTabBorderWidth("0px");
		setTabFontFamily(getSkinParameter("tabFamilyFont"));
		setTabFontSize(getSkinParameter("tabSizeFont"));
		setTabFontWeight("normal");
		setTabTextColor("black");
		setTabTextDecoration("none");
		//setTabControlColor("#bbb");

		setTabActiveBackgroundColor(getSkinParameter("tabBackgroundColor"));
		setTabActiveBackgroundImage("none");
		setTabActiveGradientColor(getSkinParameter("headerGradientColor"));
		setTabActiveBorderColor(getSkinParameter("panelBorderColor"));
		//setTabActiveBorderStyle("solid");
		//setTabActiveBorderWidth("0px");
		setTabActiveFontFamily(getSkinParameter("tabFamilyFont"));
		setTabActiveFontSize(getSkinParameter("tabSizeFont"));
		setTabActiveFontStyle("normal");
		setTabActiveFontWeight("normal");
		setTabActiveTextColor("black");
		setTabActiveTextDecoration("none");
		//setTabActiveControlColor("#bbb");

		setTabInactiveBackgroundColor(getSkinParameter("tabBackgroundColor"));
		setTabInactiveBackgroundImage("none");
		setTabInactiveGradientColor(getSkinParameter("headerGradientColor"));
		setTabInactiveBorderColor("#bbb");
		//setTabInactiveBorderStyle("solid");
		//setTabInactiveBorderWidth("0px");
		setTabInactiveFontFamily(getSkinParameter("tabFamilyFont"));
		setTabInactiveFontSize(getSkinParameter("tabSizeFont"));
		setTabInactiveFontStyle("normal");
		setTabInactiveFontWeight("normal");
		setTabInactiveTextColor("black");
		setTabInactiveTextDecoration("none");
		//setTabInactiveControlColor("#bbb");

		setTabDisabledBackgroundColor(getSkinParameter("tabBackgroundColor"));
		setTabDisabledBackgroundImage("none");
		setTabDisabledGradientColor(getSkinParameter("headerGradientColor"));
		setTabDisabledBorderColor("#ddd");
		//setTabDisabledBorderStyle("solid");
		//setTabDisabledBorderWidth("0px");
		setTabDisabledFontFamily(getSkinParameter("tabFamilyFont"));
		setTabDisabledFontSize(getSkinParameter("tabSizeFont"));
		setTabDisabledFontStyle("italic");
		setTabDisabledFontWeight("normal");
		setTabDisabledTextColor(getSkinParameter("tabDisabledTextColor", "#bbb"));
		setTabDisabledTextDecoration("none");
		//setTabDisabledControlColor("#bbb");

		setTableBackgroundColor(getSkinParameter("tableBackgroundColor"));
		setTableBorderColor(getSkinParameter("tableBorderColor"));
		setTableBorderWidth(getSkinParameter("tableBorderWidth"));

		setTableHeaderBackgroundColor(getSkinParameter("tableHeaderBackgroundColor"));
		setTableHeaderBackgroundImage("none");
		setTableHeaderGradientColor(getSkinParameter("headerGradientColor"));
		setTableHeaderBorderColor(getSkinParameter("tableBorderColor"));
		//setTableHeaderBorderStyle("solid");
		//setTableHeaderBorderWidth("0px");
		setTableHeaderFontFamily("Arial, Verdana, sans-serif");
		setTableHeaderFontSize("14px");
		setTableHeaderFontStyle("normal");
		//setTableHeaderFontVariant("normal");
		setTableHeaderFontWeight("normal");
		setTableHeaderTextColor(getSkinParameter("tableHeaderTextColor"));
		setTableHeaderTextDecoration("none");
		//setTableHeaderControlColor("#bbb");

		setTableFooterBackgroundColor(getSkinParameter("tableFooterBackgroundColor"));
		setTableFooterBackgroundImage("none");
		setTableFooterGradientColor(getSkinParameter("headerGradientColor"));
		setTableFooterBorderColor(getSkinParameter("tableBorderColor"));
		//setTableFooterBorderStyle("solid");
		//setTableFooterBorderWidth("0px");
		setTableFooterFontFamily("Arial, Verdana, sans-serif");
		setTableFooterFontSize("14px");
		setTableFooterFontStyle("normal");
		//setTableFooterFontVariant("normal");
		setTableFooterFontWeight("normal");
		setTableFooterTextColor(getSkinParameter("tableHeaderTextColor"));
		setTableFooterTextDecoration("none");
		//setTableFooterControlColor("#bbb");

		setTableSubHeaderBackgroundColor(getSkinParameter("tableSubHeaderBackgroundColor"));
		setTableSubFooterBackgroundColor(getSkinParameter("tableSubfooterBackgroundColor"));

		setTableCellBackgroundColor(getSkinParameter("tableBackgroundColor"));
		setTableCellBackgroundImage("none");
		setTableCellGradientColor(getSkinParameter("headerGradientColor"));
		setTableCellBorderColor(getSkinParameter("tableBorderColor"));
		//setTableCellBorderStyle("solid");
		//setTableCellBorderWidth("0px");
		setTableCellFontFamily("Arial, Verdana, sans-serif");
		setTableCellFontSize("14px");
		setTableCellFontStyle("normal");
		//setTableCellFontVariant("normal");
		setTableCellFontWeight("normal");
		setTableCellTextColor(getSkinParameter("generalTextColor"));
		setTableCellTextDecoration("none");
		//setTableCellControlColor("#bbb");

		setTooltipBackgroundColor(getSkinParameter("tableBackgroundColor"));
		setTooltipBackgroundImage("none");
		setTooltipGradientColor(getSkinParameter("headerGradientColor"));
		setTooltipBorderColor(getSkinParameter("tableBorderColor"));
		setTooltipBorderStyle("solid");
		setTooltipBorderWidth("0px");
		setTooltipFontFamily("Arial, Verdana, sans-serif");
		setTooltipFontSize("14px");
		setTooltipFontStyle("normal");
		//setTooltipFontVariant("normal");
		setTooltipFontWeight("normal");
		setTooltipTextColor(getSkinParameter("generalTextColor"));
		setTooltipTextDecoration("none");

		setHighlightBackgroundColor("inherit");
		setHighlightBackgroundImage("none");
		setHighlightBorderColor("#3399ff");
		setHighlightBorderStyle("solid");
		setHighlightBorderWidth("0px");
		//setHighlightControlColor("black");
		setHighlightTextColor("black");
		setHighlightTextDecoration("none");

		setFocusBackgroundColor("inherit");
		setFocusBackgroundImage("none");
		setFocusBorderColor("#0033bb");
		setFocusBorderStyle("solid");
		setFocusBorderWidth("0px");
		setFocusTextColor("#0033bb");
		setFocusTextDecoration("none");
		//setFocusControlColor("black");

		setDisabledBackgroundColor("#eee");
		setDisabledBackgroundImage("none");
		setDisabledBorderColor("#ddd");
		setDisabledBorderStyle("solid");
		setDisabledBorderWidth("0px");
		setDisabledFontSize("14px");
		setDisabledFontFamily("Arial, Verdana, sans-serif");
		setDisabledFontWeight("normal");
		setDisabledTextColor(getSkinParameter("tabDisabledTextColor"));
		setDisabledTextDecoration("none");
		//setDisabledControlColor("#bbb");

		setThemeBackgroundColor(getSkinParameter("additionalBackgroundColor"));
		setThemeBorderColor("#3399ff");
	}

	public SkinProvider getskinProvider() {
		return skinProvider;
	}

	public void setskinProvider(SkinProvider skinProvider) {
		this.skinProvider = skinProvider;
	}

	protected String getSkinParameter(String name) {
		return getSkinParameter(name, null);
	}
	
	protected String getSkinParameter(String name, String defaultValue) {
		Object parameter = skinProvider.getParameter(name);
		if (parameter != null)
			return parameter.toString();
		return defaultValue;
	}

	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getMarginTop() {
		return marginTop;
	}
	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}
	public String getMarginLeft() {
		return marginLeft;
	}
	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}
	public String getMarginRight() {
		return marginRight;
	}
	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}
	public String getMarginBottom() {
		return marginBottom;
	}
	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public String getPaddingTop() {
		return paddingTop;
	}
	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;
	}
	public String getPaddingLeft() {
		return paddingLeft;
	}
	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public String getPaddingRight() {
		return paddingRight;
	}
	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;
	}
	public String getPaddingBottom() {
		return paddingBottom;
	}
	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;
	}
	public String getBorderStyle() {
		return borderStyle;
	}
	public void setBorderStyle(String borderStyle) {
		this.borderStyle = borderStyle;
	}
	public String getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getCornerRadius() {
		return cornerRadius;
	}
	public void setCornerRadius(String cornerRadius) {
		this.cornerRadius = cornerRadius;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getGradientColor() {
		return gradientColor;
	}
	public void setGradientColor(String gradientColor) {
		this.gradientColor = gradientColor;
	}
	public String getGradientImage() {
		return gradientImage;
	}
	public void setGradientImage(String gradientImage) {
		this.gradientImage = gradientImage;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getFontVariant() {
		return fontVariant;
	}
	public void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTextDecoration() {
		return textDecoration;
	}
	public void setTextDecoration(String textDecoration) {
		this.textDecoration = textDecoration;
	}
	public String getLinkColor() {
		return linkColor;
	}
	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}
	public String getLinkHoverColor() {
		return linkHoverColor;
	}
	public void setLinkHoverColor(String linkHoverColor) {
		this.linkHoverColor = linkHoverColor;
	}
	public String getLinkVisitedColor() {
		return linkVisitedColor;
	}
	public void setLinkVisitedColor(String linkVisitedColor) {
		this.linkVisitedColor = linkVisitedColor;
	}
	public String getButtonFontFamily() {
		return buttonFontFamily;
	}
	public void setButtonFontFamily(String buttonFontFamily) {
		this.buttonFontFamily = buttonFontFamily;
	}
	public String getButtonFontSize() {
		return buttonFontSize;
	}
	public void setButtonFontSize(String buttonFontSize) {
		this.buttonFontSize = buttonFontSize;
	}
	public String getButtonFontStyle() {
		return buttonFontStyle;
	}
	public void setButtonFontStyle(String buttonFontStyle) {
		this.buttonFontStyle = buttonFontStyle;
	}
	public String getButtonFontVariant() {
		return buttonFontVariant;
	}
	public void setButtonFontVariant(String buttonFontVariant) {
		this.buttonFontVariant = buttonFontVariant;
	}
	public String getButtonFontWeight() {
		return buttonFontWeight;
	}
	public void setButtonFontWeight(String buttonFontWeight) {
		this.buttonFontWeight = buttonFontWeight;
	}
	public String getButtonTextColor() {
		return buttonTextColor;
	}
	public void setButtonTextColor(String buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
	}
	public String getButtonTextDecoration() {
		return buttonTextDecoration;
	}
	public void setButtonTextDecoration(String buttonTextDecoration) {
		this.buttonTextDecoration = buttonTextDecoration;
	}
	public String getButtonBackgroundColor() {
		return buttonBackgroundColor;
	}
	public void setButtonBackgroundColor(String buttonBackgroundColor) {
		this.buttonBackgroundColor = buttonBackgroundColor;
	}
	public String getButtonBackgroundImage() {
		return buttonBackgroundImage;
	}
	public void setButtonBackgroundImage(String buttonBackgroundImage) {
		this.buttonBackgroundImage = buttonBackgroundImage;
	}
	public String getButtonGradientColor() {
		return buttonGradientColor;
	}
	public void setButtonGradientColor(String buttonGradientColor) {
		this.buttonGradientColor = buttonGradientColor;
	}
	public String getButtonBorderStyle() {
		return buttonBorderStyle;
	}
	public void setButtonBorderStyle(String buttonBorderStyle) {
		this.buttonBorderStyle = buttonBorderStyle;
	}
	public String getButtonBorderWidth() {
		return buttonBorderWidth;
	}
	public void setButtonBorderWidth(String buttonBorderWidth) {
		this.buttonBorderWidth = buttonBorderWidth;
	}
	public String getButtonBorderColor() {
		return buttonBorderColor;
	}
	public void setButtonBorderColor(String buttonBorderColor) {
		this.buttonBorderColor = buttonBorderColor;
	}
	public String getButtonControlColor() {
		return buttonControlColor;
	}
	public void setButtonControlColor(String buttonControlColor) {
		this.buttonControlColor = buttonControlColor;
	}
	public String getButtonCornerRadius() {
		return buttonCornerRadius;
	}
	public void setButtonCornerRadius(String buttonCornerRadius) {
		this.buttonCornerRadius = buttonCornerRadius;
	}
	public String getHeaderBackgroundColor() {
		return headerBackgroundColor;
	}
	public void setHeaderBackgroundColor(String headerBackgroundColor) {
		this.headerBackgroundColor = headerBackgroundColor;
	}
	public String getHeaderBackgroundImage() {
		return headerBackgroundImage;
	}
	public void setHeaderBackgroundImage(String headerBackgroundImage) {
		this.headerBackgroundImage = headerBackgroundImage;
	}
	public String getHeaderGradientColor() {
		return headerGradientColor;
	}
	public void setHeaderGradientColor(String headerGradientColor) {
		this.headerGradientColor = headerGradientColor;
	}
	public String getHeaderBorderStyle() {
		return headerBorderStyle;
	}
	public void setHeaderBorderStyle(String headerBorderStyle) {
		this.headerBorderStyle = headerBorderStyle;
	}
	public String getHeaderBorderWidth() {
		return headerBorderWidth;
	}
	public void setHeaderBorderWidth(String headerBorderWidth) {
		this.headerBorderWidth = headerBorderWidth;
	}
	public String getHeaderBorderColor() {
		return headerBorderColor;
	}
	public void setHeaderBorderColor(String headerBorderColor) {
		this.headerBorderColor = headerBorderColor;
	}
	public String getHeaderFontFamily() {
		return headerFontFamily;
	}
	public void setHeaderFontFamily(String headerFontFamily) {
		this.headerFontFamily = headerFontFamily;
	}
	public String getHeaderFontSize() {
		return headerFontSize;
	}
	public void setHeaderFontSize(String headerFontSize) {
		this.headerFontSize = headerFontSize;
	}
	public String getHeaderFontStyle() {
		return headerFontStyle;
	}
	public void setHeaderFontStyle(String headerFontStyle) {
		this.headerFontStyle = headerFontStyle;
	}
	public String getHeaderFontVariant() {
		return headerFontVariant;
	}
	public void setHeaderFontVariant(String headerFontVariant) {
		this.headerFontVariant = headerFontVariant;
	}
	public String getHeaderFontWeight() {
		return headerFontWeight;
	}
	public void setHeaderFontWeight(String headerFontWeight) {
		this.headerFontWeight = headerFontWeight;
	}
	public String getHeaderTextColor() {
		return headerTextColor;
	}
	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}
	public String getHeaderTextDecoration() {
		return headerTextDecoration;
	}
	public void setHeaderTextDecoration(String headerTextDecoration) {
		this.headerTextDecoration = headerTextDecoration;
	}
	public String getToolbarBackgroundColor() {
		return toolbarBackgroundColor;
	}
	public void setToolbarBackgroundColor(String toolbarBackgroundColor) {
		this.toolbarBackgroundColor = toolbarBackgroundColor;
	}
	public String getToolbarBackgroundImage() {
		return toolbarBackgroundImage;
	}
	public void setToolbarBackgroundImage(String toolbarBackgroundImage) {
		this.toolbarBackgroundImage = toolbarBackgroundImage;
	}
	public String getToolbarGradientColor() {
		return toolbarGradientColor;
	}
	public void setToolbarGradientColor(String toolbarGradientColor) {
		this.toolbarGradientColor = toolbarGradientColor;
	}
	public String getToolbarBorderColor() {
		return toolbarBorderColor;
	}
	public void setToolbarBorderColor(String toolbarBorderColor) {
		this.toolbarBorderColor = toolbarBorderColor;
	}
	public String getToolbarBorderStyle() {
		return toolbarBorderStyle;
	}
	public void setToolbarBorderStyle(String toolbarBorderStyle) {
		this.toolbarBorderStyle = toolbarBorderStyle;
	}
	public String getToolbarBorderWidth() {
		return toolbarBorderWidth;
	}
	public void setToolbarBorderWidth(String toolbarBorderWidth) {
		this.toolbarBorderWidth = toolbarBorderWidth;
	}
	public String getToolbarFontFamily() {
		return toolbarFontFamily;
	}
	public void setToolbarFontFamily(String toolbarFontFamily) {
		this.toolbarFontFamily = toolbarFontFamily;
	}
	public String getToolbarFontSize() {
		return toolbarFontSize;
	}
	public void setToolbarFontSize(String toolbarFontSize) {
		this.toolbarFontSize = toolbarFontSize;
	}
	public String getToolbarFontStyle() {
		return toolbarFontStyle;
	}
	public void setToolbarFontStyle(String toolbarFontStyle) {
		this.toolbarFontStyle = toolbarFontStyle;
	}
	public String getToolbarFontVariant() {
		return toolbarFontVariant;
	}
	public void setToolbarFontVariant(String toolbarFontVariant) {
		this.toolbarFontVariant = toolbarFontVariant;
	}
	public String getToolbarFontWeight() {
		return toolbarFontWeight;
	}
	public void setToolbarFontWeight(String toolbarFontWeight) {
		this.toolbarFontWeight = toolbarFontWeight;
	}
	public String getToolbarTextColor() {
		return toolbarTextColor;
	}
	public void setToolbarTextColor(String toolbarTextColor) {
		this.toolbarTextColor = toolbarTextColor;
	}
	public String getToolbarTextDecoration() {
		return toolbarTextDecoration;
	}
	public void setToolbarTextDecoration(String toolbarTextDecoration) {
		this.toolbarTextDecoration = toolbarTextDecoration;
	}
	public String getTabBackgroundColor() {
		return tabBackgroundColor;
	}
	public void setTabBackgroundColor(String tabBackgroundColor) {
		this.tabBackgroundColor = tabBackgroundColor;
	}
	public String getTabBackgroundImage() {
		return tabBackgroundImage;
	}
	public void setTabBackgroundImage(String tabBackgroundImage) {
		this.tabBackgroundImage = tabBackgroundImage;
	}
	public String getTabGradientColor() {
		return tabGradientColor;
	}
	public void setTabGradientColor(String tabGradientColor) {
		this.tabGradientColor = tabGradientColor;
	}
	public String getTabBorderColor() {
		return tabBorderColor;
	}
	public void setTabBorderColor(String tabBorderColor) {
		this.tabBorderColor = tabBorderColor;
	}
	public String getTabCornerRadius() {
		return tabCornerRadius;
	}
	public void setTabCornerRadius(String tabCornerRadius) {
		this.tabCornerRadius = tabCornerRadius;
	}
	public String getTabFontFamily() {
		return tabFontFamily;
	}
	public void setTabFontFamily(String tabFontFamily) {
		this.tabFontFamily = tabFontFamily;
	}
	public String getTabFontSize() {
		return tabFontSize;
	}
	public void setTabFontSize(String tabFontSize) {
		this.tabFontSize = tabFontSize;
	}
	public String getTabFontStyle() {
		return tabFontStyle;
	}
	public void setTabFontStyle(String tabFontStyle) {
		this.tabFontStyle = tabFontStyle;
	}
	public String getTabFontWeight() {
		return tabFontWeight;
	}
	public void setTabFontWeight(String tabFontWeight) {
		this.tabFontWeight = tabFontWeight;
	}
	public String getTabTextColor() {
		return tabTextColor;
	}
	public void setTabTextColor(String tabTextColor) {
		this.tabTextColor = tabTextColor;
	}
	public String getTabTextDecoration() {
		return tabTextDecoration;
	}
	public void setTabTextDecoration(String tabTextDecoration) {
		this.tabTextDecoration = tabTextDecoration;
	}
	public String getTabActiveBackgroundColor() {
		return tabActiveBackgroundColor;
	}
	public void setTabActiveBackgroundColor(String tabActiveBackgroundColor) {
		this.tabActiveBackgroundColor = tabActiveBackgroundColor;
	}
	public String getTabActiveBackgroundImage() {
		return tabActiveBackgroundImage;
	}
	public void setTabActiveBackgroundImage(String tabActiveBackgroundImage) {
		this.tabActiveBackgroundImage = tabActiveBackgroundImage;
	}
	public String getTabActiveGradientColor() {
		return tabActiveGradientColor;
	}
	public void setTabActiveGradientColor(String tabActiveGradientColor) {
		this.tabActiveGradientColor = tabActiveGradientColor;
	}
	public String getTabActiveBorderColor() {
		return tabActiveBorderColor;
	}
	public void setTabActiveBorderColor(String tabActiveBorderColor) {
		this.tabActiveBorderColor = tabActiveBorderColor;
	}
	public String getTabActiveFontFamily() {
		return tabActiveFontFamily;
	}
	public void setTabActiveFontFamily(String tabActiveFontFamily) {
		this.tabActiveFontFamily = tabActiveFontFamily;
	}
	public String getTabActiveFontSize() {
		return tabActiveFontSize;
	}
	public void setTabActiveFontSize(String tabActiveFontSize) {
		this.tabActiveFontSize = tabActiveFontSize;
	}
	public String getTabActiveFontStyle() {
		return tabActiveFontStyle;
	}
	public void setTabActiveFontStyle(String tabActiveFontStyle) {
		this.tabActiveFontStyle = tabActiveFontStyle;
	}
	public String getTabActiveFontWeight() {
		return tabActiveFontWeight;
	}
	public void setTabActiveFontWeight(String tabActiveFontWeight) {
		this.tabActiveFontWeight = tabActiveFontWeight;
	}
	public String getTabActiveTextColor() {
		return tabActiveTextColor;
	}
	public void setTabActiveTextColor(String tabActiveTextColor) {
		this.tabActiveTextColor = tabActiveTextColor;
	}
	public String getTabActiveTextDecoration() {
		return tabActiveTextDecoration;
	}
	public void setTabActiveTextDecoration(String tabActiveTextDecoration) {
		this.tabActiveTextDecoration = tabActiveTextDecoration;
	}
	public String getTabInactiveBackgroundColor() {
		return tabInactiveBackgroundColor;
	}
	public void setTabInactiveBackgroundColor(String tabInactiveBackgroundColor) {
		this.tabInactiveBackgroundColor = tabInactiveBackgroundColor;
	}
	public String getTabInactiveBackgroundImage() {
		return tabInactiveBackgroundImage;
	}
	public void setTabInactiveBackgroundImage(String tabInactiveBackgroundImage) {
		this.tabInactiveBackgroundImage = tabInactiveBackgroundImage;
	}
	public String getTabInactiveGradientColor() {
		return tabInactiveGradientColor;
	}
	public void setTabInactiveGradientColor(String tabInactiveGradientColor) {
		this.tabInactiveGradientColor = tabInactiveGradientColor;
	}
	public String getTabInactiveBorderColor() {
		return tabInactiveBorderColor;
	}
	public void setTabInactiveBorderColor(String tabInactiveBorderColor) {
		this.tabInactiveBorderColor = tabInactiveBorderColor;
	}
	public String getTabInactiveFontFamily() {
		return tabInactiveFontFamily;
	}
	public void setTabInactiveFontFamily(String tabInactiveFontFamily) {
		this.tabInactiveFontFamily = tabInactiveFontFamily;
	}
	public String getTabInactiveFontSize() {
		return tabInactiveFontSize;
	}
	public void setTabInactiveFontSize(String tabInactiveFontSize) {
		this.tabInactiveFontSize = tabInactiveFontSize;
	}
	public String getTabInactiveFontStyle() {
		return tabInactiveFontStyle;
	}
	public void setTabInactiveFontStyle(String tabInactiveFontStyle) {
		this.tabInactiveFontStyle = tabInactiveFontStyle;
	}
	public String getTabInactiveFontWeight() {
		return tabInactiveFontWeight;
	}
	public void setTabInactiveFontWeight(String tabInactiveFontWeight) {
		this.tabInactiveFontWeight = tabInactiveFontWeight;
	}
	public String getTabInactiveTextColor() {
		return tabInactiveTextColor;
	}
	public void setTabInactiveTextColor(String tabInactiveTextColor) {
		this.tabInactiveTextColor = tabInactiveTextColor;
	}
	public String getTabInactiveTextDecoration() {
		return tabInactiveTextDecoration;
	}
	public void setTabInactiveTextDecoration(String tabInactiveTextDecoration) {
		this.tabInactiveTextDecoration = tabInactiveTextDecoration;
	}
	public String getTabDisabledBackgroundColor() {
		return tabDisabledBackgroundColor;
	}
	public void setTabDisabledBackgroundColor(String tabDisabledBackgroundColor) {
		this.tabDisabledBackgroundColor = tabDisabledBackgroundColor;
	}
	public String getTabDisabledBackgroundImage() {
		return tabDisabledBackgroundImage;
	}
	public void setTabDisabledBackgroundImage(String tabDisabledBackgroundImage) {
		this.tabDisabledBackgroundImage = tabDisabledBackgroundImage;
	}
	public String getTabDisabledGradientColor() {
		return tabDisabledGradientColor;
	}
	public void setTabDisabledGradientColor(String tabDisabledGradientColor) {
		this.tabDisabledGradientColor = tabDisabledGradientColor;
	}
	public String getTabDisabledBorderColor() {
		return tabDisabledBorderColor;
	}
	public void setTabDisabledBorderColor(String tabDisabledBorderColor) {
		this.tabDisabledBorderColor = tabDisabledBorderColor;
	}
	public String getTabDisabledFontFamily() {
		return tabDisabledFontFamily;
	}
	public void setTabDisabledFontFamily(String tabDisabledFontFamily) {
		this.tabDisabledFontFamily = tabDisabledFontFamily;
	}
	public String getTabDisabledFontSize() {
		return tabDisabledFontSize;
	}
	public void setTabDisabledFontSize(String tabDisabledFontSize) {
		this.tabDisabledFontSize = tabDisabledFontSize;
	}
	public String getTabDisabledFontStyle() {
		return tabDisabledFontStyle;
	}
	public void setTabDisabledFontStyle(String tabDisabledFontStyle) {
		this.tabDisabledFontStyle = tabDisabledFontStyle;
	}
	public String getTabDisabledFontWeight() {
		return tabDisabledFontWeight;
	}
	public void setTabDisabledFontWeight(String tabDisabledFontWeight) {
		this.tabDisabledFontWeight = tabDisabledFontWeight;
	}
	public String getTabDisabledTextColor() {
		return tabDisabledTextColor;
	}
	public void setTabDisabledTextColor(String tabDisabledTextColor) {
		this.tabDisabledTextColor = tabDisabledTextColor;
	}
	public String getTabDisabledTextDecoration() {
		return tabDisabledTextDecoration;
	}
	public void setTabDisabledTextDecoration(String tabDisabledTextDecoration) {
		this.tabDisabledTextDecoration = tabDisabledTextDecoration;
	}
	public String getTableBackgroundColor() {
		return tableBackgroundColor;
	}
	public void setTableBackgroundColor(String tableBackgroundColor) {
		this.tableBackgroundColor = tableBackgroundColor;
	}
	public String getTableBorderColor() {
		return tableBorderColor;
	}
	public void setTableBorderColor(String tableBorderColor) {
		this.tableBorderColor = tableBorderColor;
	}
	public String getTableBorderWidth() {
		return tableBorderWidth;
	}
	public void setTableBorderWidth(String tableBorderWidth) {
		this.tableBorderWidth = tableBorderWidth;
	}
	public String getTableCellBackgroundColor() {
		return tableCellBackgroundColor;
	}
	public void setTableCellBackgroundColor(String tableCellBackgroundColor) {
		this.tableCellBackgroundColor = tableCellBackgroundColor;
	}
	public String getTableCellBackgroundImage() {
		return tableCellBackgroundImage;
	}
	public void setTableCellBackgroundImage(String tableCellBackgroundImage) {
		this.tableCellBackgroundImage = tableCellBackgroundImage;
	}
	public String getTableCellGradientColor() {
		return tableCellGradientColor;
	}
	public void setTableCellGradientColor(String tableCellGradientColor) {
		this.tableCellGradientColor = tableCellGradientColor;
	}
	public String getTableCellBorderColor() {
		return tableCellBorderColor;
	}
	public void setTableCellBorderColor(String tableCellBorderColor) {
		this.tableCellBorderColor = tableCellBorderColor;
	}
	public String getTableCellFontFamily() {
		return tableCellFontFamily;
	}
	public void setTableCellFontFamily(String tableCellFontFamily) {
		this.tableCellFontFamily = tableCellFontFamily;
	}
	public String getTableCellFontSize() {
		return tableCellFontSize;
	}
	public void setTableCellFontSize(String tableCellFontSize) {
		this.tableCellFontSize = tableCellFontSize;
	}
	public String getTableCellFontStyle() {
		return tableCellFontStyle;
	}
	public void setTableCellFontStyle(String tableCellFontStyle) {
		this.tableCellFontStyle = tableCellFontStyle;
	}
	public String getTableCellFontVariant() {
		return tableCellFontVariant;
	}
	public void setTableCellFontVariant(String tableCellFontVariant) {
		this.tableCellFontVariant = tableCellFontVariant;
	}
	public String getTableCellFontWeight() {
		return tableCellFontWeight;
	}
	public void setTableCellFontWeight(String tableCellFontWeight) {
		this.tableCellFontWeight = tableCellFontWeight;
	}
	public String getTableCellTextColor() {
		return tableCellTextColor;
	}
	public void setTableCellTextColor(String tableCellTextColor) {
		this.tableCellTextColor = tableCellTextColor;
	}
	public String getTableCellTextDecoration() {
		return tableCellTextDecoration;
	}
	public void setTableCellTextDecoration(String tableCellTextDecoration) {
		this.tableCellTextDecoration = tableCellTextDecoration;
	}
	public String getTableHeaderBackgroundColor() {
		return tableHeaderBackgroundColor;
	}
	public void setTableHeaderBackgroundColor(String tableHeaderBackgroundColor) {
		this.tableHeaderBackgroundColor = tableHeaderBackgroundColor;
	}
	public String getTableHeaderBackgroundImage() {
		return tableHeaderBackgroundImage;
	}
	public void setTableHeaderBackgroundImage(String tableHeaderBackgroundImage) {
		this.tableHeaderBackgroundImage = tableHeaderBackgroundImage;
	}
	public String getTableHeaderGradientColor() {
		return tableHeaderGradientColor;
	}
	public void setTableHeaderGradientColor(String tableHeaderGradientColor) {
		this.tableHeaderGradientColor = tableHeaderGradientColor;
	}
	public String getTableHeaderBorderColor() {
		return tableHeaderBorderColor;
	}
	public void setTableHeaderBorderColor(String tableHeaderBorderColor) {
		this.tableHeaderBorderColor = tableHeaderBorderColor;
	}
	public String getTableHeaderFontFamily() {
		return tableHeaderFontFamily;
	}
	public void setTableHeaderFontFamily(String tableHeaderFontFamily) {
		this.tableHeaderFontFamily = tableHeaderFontFamily;
	}
	public String getTableHeaderFontSize() {
		return tableHeaderFontSize;
	}
	public void setTableHeaderFontSize(String tableHeaderFontSize) {
		this.tableHeaderFontSize = tableHeaderFontSize;
	}
	public String getTableHeaderFontStyle() {
		return tableHeaderFontStyle;
	}
	public void setTableHeaderFontStyle(String tableHeaderFontStyle) {
		this.tableHeaderFontStyle = tableHeaderFontStyle;
	}
	public String getTableHeaderFontVariant() {
		return tableHeaderFontVariant;
	}
	public void setTableHeaderFontVariant(String tableHeaderFontVariant) {
		this.tableHeaderFontVariant = tableHeaderFontVariant;
	}
	public String getTableHeaderFontWeight() {
		return tableHeaderFontWeight;
	}
	public void setTableHeaderFontWeight(String tableHeaderFontWeight) {
		this.tableHeaderFontWeight = tableHeaderFontWeight;
	}
	public String getTableHeaderTextColor() {
		return tableHeaderTextColor;
	}
	public void setTableHeaderTextColor(String tableHeaderTextColor) {
		this.tableHeaderTextColor = tableHeaderTextColor;
	}
	public String getTableHeaderTextDecoration() {
		return tableHeaderTextDecoration;
	}
	public void setTableHeaderTextDecoration(String tableHeaderTextDecoration) {
		this.tableHeaderTextDecoration = tableHeaderTextDecoration;
	}
	public String getTableFooterBackgroundColor() {
		return tableFooterBackgroundColor;
	}
	public void setTableFooterBackgroundColor(String tableFooterBackgroundColor) {
		this.tableFooterBackgroundColor = tableFooterBackgroundColor;
	}
	public String getTableFooterBackgroundImage() {
		return tableFooterBackgroundImage;
	}
	public void setTableFooterBackgroundImage(String tableFooterBackgroundImage) {
		this.tableFooterBackgroundImage = tableFooterBackgroundImage;
	}
	public String getTableFooterGradientColor() {
		return tableFooterGradientColor;
	}
	public void setTableFooterGradientColor(String tableFooterGradientColor) {
		this.tableFooterGradientColor = tableFooterGradientColor;
	}
	public String getTableFooterBorderColor() {
		return tableFooterBorderColor;
	}
	public void setTableFooterBorderColor(String tableFooterBorderColor) {
		this.tableFooterBorderColor = tableFooterBorderColor;
	}
	public String getTableFooterFontFamily() {
		return tableFooterFontFamily;
	}
	public void setTableFooterFontFamily(String tableFooterFontFamily) {
		this.tableFooterFontFamily = tableFooterFontFamily;
	}
	public String getTableFooterFontSize() {
		return tableFooterFontSize;
	}
	public void setTableFooterFontSize(String tableFooterFontSize) {
		this.tableFooterFontSize = tableFooterFontSize;
	}
	public String getTableFooterFontStyle() {
		return tableFooterFontStyle;
	}
	public void setTableFooterFontStyle(String tableFooterFontStyle) {
		this.tableFooterFontStyle = tableFooterFontStyle;
	}
	public String getTableFooterFontVariant() {
		return tableFooterFontVariant;
	}
	public void setTableFooterFontVariant(String tableFooterFontVariant) {
		this.tableFooterFontVariant = tableFooterFontVariant;
	}
	public String getTableFooterFontWeight() {
		return tableFooterFontWeight;
	}
	public void setTableFooterFontWeight(String tableFooterFontWeight) {
		this.tableFooterFontWeight = tableFooterFontWeight;
	}
	public String getTableFooterTextColor() {
		return tableFooterTextColor;
	}
	public void setTableFooterTextColor(String tableFooterTextColor) {
		this.tableFooterTextColor = tableFooterTextColor;
	}
	public String getTableFooterTextDecoration() {
		return tableFooterTextDecoration;
	}
	public void setTableFooterTextDecoration(String tableFooterTextDecoration) {
		this.tableFooterTextDecoration = tableFooterTextDecoration;
	}
	public String getTableSubHeaderBackgroundColor() {
		return tableSubHeaderBackgroundColor;
	}
	public void setTableSubHeaderBackgroundColor(String tableSubHeaderBackgroundColor) {
		this.tableSubHeaderBackgroundColor = tableSubHeaderBackgroundColor;
	}
	public String getTableSubFooterBackgroundColor() {
		return tableSubFooterBackgroundColor;
	}
	public void setTableSubFooterBackgroundColor(String tableSubFooterBackgroundColor) {
		this.tableSubFooterBackgroundColor = tableSubFooterBackgroundColor;
	}
	public String getTooltipBackgroundColor() {
		return tooltipBackgroundColor;
	}
	public void setTooltipBackgroundColor(String tooltipBackgroundColor) {
		this.tooltipBackgroundColor = tooltipBackgroundColor;
	}
	public String getTooltipBackgroundImage() {
		return tooltipBackgroundImage;
	}
	public void setTooltipBackgroundImage(String tooltipBackgroundImage) {
		this.tooltipBackgroundImage = tooltipBackgroundImage;
	}
	public String getTooltipGradientColor() {
		return tooltipGradientColor;
	}
	public void setTooltipGradientColor(String tooltipGradientColor) {
		this.tooltipGradientColor = tooltipGradientColor;
	}
	public String getTooltipBorderColor() {
		return tooltipBorderColor;
	}
	public void setTooltipBorderColor(String tooltipBorderColor) {
		this.tooltipBorderColor = tooltipBorderColor;
	}
	public String getTooltipBorderStyle() {
		return tooltipBorderStyle;
	}
	public void setTooltipBorderStyle(String tooltipBorderStyle) {
		this.tooltipBorderStyle = tooltipBorderStyle;
	}
	public String getTooltipBorderWidth() {
		return tooltipBorderWidth;
	}
	public void setTooltipBorderWidth(String tooltipBorderWidth) {
		this.tooltipBorderWidth = tooltipBorderWidth;
	}
	public String getTooltipFontFamily() {
		return tooltipFontFamily;
	}
	public void setTooltipFontFamily(String tooltipFontFamily) {
		this.tooltipFontFamily = tooltipFontFamily;
	}
	public String getTooltipFontSize() {
		return tooltipFontSize;
	}
	public void setTooltipFontSize(String tooltipFontSize) {
		this.tooltipFontSize = tooltipFontSize;
	}
	public String getTooltipFontStyle() {
		return tooltipFontStyle;
	}
	public void setTooltipFontStyle(String tooltipFontStyle) {
		this.tooltipFontStyle = tooltipFontStyle;
	}
	public String getTooltipFontVariant() {
		return tooltipFontVariant;
	}
	public void setTooltipFontVariant(String tooltipFontVariant) {
		this.tooltipFontVariant = tooltipFontVariant;
	}
	public String getTooltipFontWeight() {
		return tooltipFontWeight;
	}
	public void setTooltipFontWeight(String tooltipFontWeight) {
		this.tooltipFontWeight = tooltipFontWeight;
	}
	public String getTooltipTextColor() {
		return tooltipTextColor;
	}
	public void setTooltipTextColor(String tooltipTextColor) {
		this.tooltipTextColor = tooltipTextColor;
	}
	public String getTooltipTextDecoration() {
		return tooltipTextDecoration;
	}
	public void setTooltipTextDecoration(String tooltipTextDecoration) {
		this.tooltipTextDecoration = tooltipTextDecoration;
	}
	
	public String getHighlightBackgroundColor() {
		return highlightBackgroundColor;
	}
	public void setHighlightBackgroundColor(String highlightBackgroundColor) {
		this.highlightBackgroundColor = highlightBackgroundColor;
	}
	public String getHighlightBackgroundImage() {
		return highlightBackgroundImage;
	}
	public void setHighlightBackgroundImage(String highlightBackgroundImage) {
		this.highlightBackgroundImage = highlightBackgroundImage;
	}
	public String getHighlightBorderStyle() {
		return highlightBorderStyle;
	}
	public void setHighlightBorderStyle(String highlightBorderStyle) {
		this.highlightBorderStyle = highlightBorderStyle;
	}
	public String getHighlightBorderWidth() {
		return highlightBorderWidth;
	}
	public void setHighlightBorderWidth(String highlightBorderWidth) {
		this.highlightBorderWidth = highlightBorderWidth;
	}
	public String getHighlightBorderColor() {
		return highlightBorderColor;
	}
	public void setHighlightBorderColor(String highlightBorderColor) {
		this.highlightBorderColor = highlightBorderColor;
	}
	public String getHighlightFontFamily() {
		return highlightFontFamily;
	}
	public void setHighlightFontFamily(String highlightFontFamily) {
		this.highlightFontFamily = highlightFontFamily;
	}
	public String getHighlightFontSize() {
		return highlightFontSize;
	}
	public void setHighlightFontSize(String highlightFontSize) {
		this.highlightFontSize = highlightFontSize;
	}
	public String getHighlightFontWeight() {
		return highlightFontWeight;
	}
	public void setHighlightFontWeight(String highlightFontWeight) {
		this.highlightFontWeight = highlightFontWeight;
	}
	public String getHighlightFontStyle() {
		return highlightFontStyle;
	}
	public void setHighlightFontStyle(String highlightFontStyle) {
		this.highlightFontStyle = highlightFontStyle;
	}
	public String getHighlightTextColor() {
		return highlightTextColor;
	}
	public void setHighlightTextColor(String highlightTextColor) {
		this.highlightTextColor = highlightTextColor;
	}
	public String getHighlightTextDecoration() {
		return highlightTextDecoration;
	}
	public void setHighlightTextDecoration(String highlightTextDecoration) {
		this.highlightTextDecoration = highlightTextDecoration;
	}
	public String getFocusBackgroundColor() {
		return focusBackgroundColor;
	}
	public void setFocusBackgroundColor(String focusBackgroundColor) {
		this.focusBackgroundColor = focusBackgroundColor;
	}
	public String getFocusBackgroundImage() {
		return focusBackgroundImage;
	}
	public void setFocusBackgroundImage(String focusBackgroundImage) {
		this.focusBackgroundImage = focusBackgroundImage;
	}
	public String getFocusGradientColor() {
		return focusGradientColor;
	}
	public void setFocusGradientColor(String focusGradientColor) {
		this.focusGradientColor = focusGradientColor;
	}
	public String getFocusBorderStyle() {
		return focusBorderStyle;
	}
	public void setFocusBorderStyle(String focusBorderStyle) {
		this.focusBorderStyle = focusBorderStyle;
	}
	public String getFocusBorderWidth() {
		return focusBorderWidth;
	}
	public void setFocusBorderWidth(String focusBorderWidth) {
		this.focusBorderWidth = focusBorderWidth;
	}
	public String getFocusBorderColor() {
		return focusBorderColor;
	}
	public void setFocusBorderColor(String focusBorderColor) {
		this.focusBorderColor = focusBorderColor;
	}
	public String getFocusFontFamily() {
		return focusFontFamily;
	}
	public void setFocusFontFamily(String focusFontFamily) {
		this.focusFontFamily = focusFontFamily;
	}
	public String getFocusFontSize() {
		return focusFontSize;
	}
	public void setFocusFontSize(String focusFontSize) {
		this.focusFontSize = focusFontSize;
	}
	public String getFocusFontWeight() {
		return focusFontWeight;
	}
	public void setFocusFontWeight(String focusFontWeight) {
		this.focusFontWeight = focusFontWeight;
	}
	public String getFocusFontStyle() {
		return focusFontStyle;
	}
	public void setFocusFontStyle(String focusFontStyle) {
		this.focusFontStyle = focusFontStyle;
	}
	public String getFocusTextColor() {
		return focusTextColor;
	}
	public void setFocusTextColor(String focusTextColor) {
		this.focusTextColor = focusTextColor;
	}
	public String getFocusTextDecoration() {
		return focusTextDecoration;
	}
	public void setFocusTextDecoration(String focusTextDecoration) {
		this.focusTextDecoration = focusTextDecoration;
	}
//	public String getFocusControlColor() {
//		return focusControlColor;
//	}
//	public void setFocusControlColor(String focusControlColor) {
//		this.focusControlColor = focusControlColor;
//	}
	public String getDisabledBackgroundColor() {
		return disabledBackgroundColor;
	}
	public void setDisabledBackgroundColor(String disabledBackgroundColor) {
		this.disabledBackgroundColor = disabledBackgroundColor;
	}
	public String getDisabledBackgroundImage() {
		return disabledBackgroundImage;
	}
	public void setDisabledBackgroundImage(String disabledBackgroundImage) {
		this.disabledBackgroundImage = disabledBackgroundImage;
	}
	public String getDisabledBorderStyle() {
		return disabledBorderStyle;
	}
	public void setDisabledBorderStyle(String disabledBorderStyle) {
		this.disabledBorderStyle = disabledBorderStyle;
	}
	public String getDisabledBorderWidth() {
		return disabledBorderWidth;
	}
	public void setDisabledBorderWidth(String disabledBorderWidth) {
		this.disabledBorderWidth = disabledBorderWidth;
	}
	public String getDisabledBorderColor() {
		return disabledBorderColor;
	}
	public void setDisabledBorderColor(String disabledBorderColor) {
		this.disabledBorderColor = disabledBorderColor;
	}
	public String getDisabledFontFamily() {
		return disabledFontFamily;
	}
	public void setDisabledFontFamily(String disabledFontFamily) {
		this.disabledFontFamily = disabledFontFamily;
	}
	public String getDisabledFontSize() {
		return disabledFontSize;
	}
	public void setDisabledFontSize(String disabledFontSize) {
		this.disabledFontSize = disabledFontSize;
	}
	public String getDisabledFontStyle() {
		return disabledFontStyle;
	}
	public void setDisabledFontStyle(String disabledFontStyle) {
		this.disabledFontStyle = disabledFontStyle;
	}
	public String getDisabledFontWeight() {
		return disabledFontWeight;
	}
	public void setDisabledFontWeight(String disabledFontWeight) {
		this.disabledFontWeight = disabledFontWeight;
	}
	public String getDisabledTextColor() {
		return disabledTextColor;
	}
	public void setDisabledTextColor(String disabledTextColor) {
		this.disabledTextColor = disabledTextColor;
	}
	public String getDisabledTextDecoration() {
		return disabledTextDecoration;
	}
	public void setDisabledTextDecoration(String disabledTextDecoration) {
		this.disabledTextDecoration = disabledTextDecoration;
	}
//	public String getDisabledControlColor() {
//		return disabledControlColor;
//	}
//	public void setDisabledControlColor(String disabledControlColor) {
//		this.disabledControlColor = disabledControlColor;
//	}

	public String getThemeBorderColor() {
		return themeBorderColor;
	}
	public void setThemeBorderColor(String themeBorderColor) {
		this.themeBorderColor = themeBorderColor;
	}
	public String getThemeBackgroundColor() {
		return themeBackgroundColor;
	}
	public void setThemeBackgroundColor(String themeBackgroundColor) {
		this.themeBackgroundColor = themeBackgroundColor;
	}
	
}
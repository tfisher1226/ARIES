package admin;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Skin", namespace = "http://admin", propOrder = {
	"id",
	"name",
	"width",
	"height",
	"margin",
	"marginTop",
	"marginLeft",
	"marginRight",
	"marginBottom",
	"padding",
	"paddingTop",
	"paddingLeft",
	"paddingRight",
	"paddingBottom",
	"borderStyle",
	"borderWidth",
	"borderColor",
	"cornerRadius",
	"backgroundColor",
	"backgroundImage",
	"gradientColor",
	"gradientImage",
	"fontSize",
	"fontFamily",
	"fontWeight",
	"fontStyle",
	"fontVariant",
	"textColor",
	"textDecoration",
	"linkColor",
	"linkHoverColor",
	"linkVisitedColor",
	"buttonFontFamily",
	"buttonFontSize",
	"buttonFontStyle",
	"buttonFontVariant",
	"buttonFontWeight",
	"buttonTextColor",
	"buttonTextDecoration",
	"buttonBackgroundColor",
	"buttonBackgroundImage",
	"buttonGradientColor",
	"buttonBorderStyle",
	"buttonBorderWidth",
	"buttonBorderColor",
	"buttonControlColor",
	"buttonCornerRadius",
	"headerBackgroundColor",
	"headerBackgroundImage",
	"headerGradientColor",
	"headerBorderStyle",
	"headerBorderWidth",
	"headerBorderColor",
	"headerFontFamily",
	"headerFontSize",
	"headerFontStyle",
	"headerFontVariant",
	"headerFontWeight",
	"headerTextColor",
	"headerTextDecoration",
	"toolbarBackgroundColor",
	"toolbarBackgroundImage",
	"toolbarGradientColor",
	"toolbarBorderColor",
	"toolbarBorderStyle",
	"toolbarBorderWidth",
	"toolbarFontFamily",
	"toolbarFontSize",
	"toolbarFontStyle",
	"toolbarFontVariant",
	"toolbarFontWeight",
	"toolbarTextColor",
	"toolbarTextDecoration",
	"tabBackgroundColor",
	"tabBackgroundImage",
	"tabGradientColor",
	"tabBorderColor",
	"tabCornerRadius",
	"tabFontFamily",
	"tabFontSize",
	"tabFontStyle",
	"tabFontWeight",
	"tabTextColor",
	"tabTextDecoration",
	"tabActiveBackgroundColor",
	"tabActiveBackgroundImage",
	"tabActiveGradientColor",
	"tabActiveBorderColor",
	"tabActiveFontFamily",
	"tabActiveFontSize",
	"tabActiveFontStyle",
	"tabActiveFontWeight",
	"tabActiveTextColor",
	"tabActiveTextDecoration",
	"tabInactiveBackgroundColor",
	"tabInactiveBackgroundImage",
	"tabInactiveGradientColor",
	"tabInactiveBorderColor",
	"tabInactiveFontFamily",
	"tabInactiveFontSize",
	"tabInactiveFontStyle",
	"tabInactiveFontWeight",
	"tabInactiveTextColor",
	"tabInactiveTextDecoration",
	"tabDisabledBackgroundColor",
	"tabDisabledBackgroundImage",
	"tabDisabledGradientColor",
	"tabDisabledBorderColor",
	"tabDisabledFontFamily",
	"tabDisabledFontSize",
	"tabDisabledFontStyle",
	"tabDisabledFontWeight",
	"tabDisabledTextColor",
	"tabDisabledTextDecoration",
	"tableBackgroundColor",
	"tableBorderColor",
	"tableBorderWidth",
	"tableCellBackgroundColor",
	"tableCellBackgroundImage",
	"tableCellGradientColor",
	"tableCellBorderColor",
	"tableCellFontFamily",
	"tableCellFontSize",
	"tableCellFontStyle",
	"tableCellFontVariant",
	"tableCellFontWeight",
	"tableCellTextColor",
	"tableCellTextDecoration",
	"tableHeaderBackgroundColor",
	"tableHeaderBackgroundImage",
	"tableHeaderGradientColor",
	"tableHeaderBorderColor",
	"tableHeaderFontFamily",
	"tableHeaderFontSize",
	"tableHeaderFontStyle",
	"tableHeaderFontVariant",
	"tableHeaderFontWeight",
	"tableHeaderTextColor",
	"tableHeaderTextDecoration",
	"tableFooterBackgroundColor",
	"tableFooterBackgroundImage",
	"tableFooterGradientColor",
	"tableFooterBorderColor",
	"tableFooterFontFamily",
	"tableFooterFontSize",
	"tableFooterFontStyle",
	"tableFooterFontVariant",
	"tableFooterFontWeight",
	"tableFooterTextColor",
	"tableFooterTextDecoration",
	"tableSubHeaderBackgroundColor",
	"tableSubFooterBackgroundColor",
	"tooltipBackgroundColor",
	"tooltipBackgroundImage",
	"tooltipGradientColor",
	"tooltipBorderColor",
	"tooltipBorderStyle",
	"tooltipBorderWidth",
	"tooltipFontFamily",
	"tooltipFontSize",
	"tooltipFontStyle",
	"tooltipFontVariant",
	"tooltipFontWeight",
	"tooltipTextColor",
	"tooltipTextDecoration",
	"highlightBackgroundColor",
	"highlightBackgroundImage",
	"highlightBorderStyle",
	"highlightBorderWidth",
	"highlightBorderColor",
	"highlightFontFamily",
	"highlightFontSize",
	"highlightFontStyle",
	"highlightFontWeight",
	"highlightTextColor",
	"highlightTextDecoration",
	"highlightControlColor",
	"focusBackgroundColor",
	"focusBackgroundImage",
	"focusGradientColor",
	"focusBorderStyle",
	"focusBorderWidth",
	"focusBorderColor",
	"focusFontFamily",
	"focusFontSize",
	"focusFontStyle",
	"focusFontWeight",
	"focusTextColor",
	"focusTextDecoration",
	"focusControlColor",
	"disabledBackgroundColor",
	"disabledBackgroundImage",
	"disabledBorderStyle",
	"disabledBorderWidth",
	"disabledBorderColor",
	"disabledFontFamily",
	"disabledFontSize",
	"disabledFontStyle",
	"disabledFontWeight",
	"disabledTextColor",
	"disabledTextDecoration",
	"disabledControlColor",
	"themeBorderColor",
	"themeBackgroundColor"
})
@XmlRootElement(name = "skin", namespace = "http://admin")
public class Skin implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "name", namespace = "http://admin", required = true)
	private String name;
	
	@XmlElement(name = "width", namespace = "http://admin")
	private String width;
	
	@XmlElement(name = "height", namespace = "http://admin")
	private String height;
	
	@XmlElement(name = "margin", namespace = "http://admin")
	private String margin;
	
	@XmlElement(name = "marginTop", namespace = "http://admin")
	private String marginTop;
	
	@XmlElement(name = "marginLeft", namespace = "http://admin")
	private String marginLeft;
	
	@XmlElement(name = "marginRight", namespace = "http://admin")
	private String marginRight;
	
	@XmlElement(name = "marginBottom", namespace = "http://admin")
	private String marginBottom;
	
	@XmlElement(name = "padding", namespace = "http://admin")
	private String padding;
	
	@XmlElement(name = "paddingTop", namespace = "http://admin")
	private String paddingTop;
	
	@XmlElement(name = "paddingLeft", namespace = "http://admin")
	private String paddingLeft;
	
	@XmlElement(name = "paddingRight", namespace = "http://admin")
	private String paddingRight;
	
	@XmlElement(name = "paddingBottom", namespace = "http://admin")
	private String paddingBottom;
	
	@XmlElement(name = "borderStyle", namespace = "http://admin")
	private String borderStyle;
	
	@XmlElement(name = "borderWidth", namespace = "http://admin")
	private String borderWidth;
	
	@XmlElement(name = "borderColor", namespace = "http://admin")
	private String borderColor;
	
	@XmlElement(name = "cornerRadius", namespace = "http://admin")
	private String cornerRadius;
	
	@XmlElement(name = "backgroundColor", namespace = "http://admin")
	private String backgroundColor;
	
	@XmlElement(name = "backgroundImage", namespace = "http://admin")
	private String backgroundImage;
	
	@XmlElement(name = "gradientColor", namespace = "http://admin")
	private String gradientColor;
	
	@XmlElement(name = "gradientImage", namespace = "http://admin")
	private String gradientImage;
	
	@XmlElement(name = "fontSize", namespace = "http://admin")
	private String fontSize;
	
	@XmlElement(name = "fontFamily", namespace = "http://admin")
	private String fontFamily;
	
	@XmlElement(name = "fontWeight", namespace = "http://admin")
	private String fontWeight;
	
	@XmlElement(name = "fontStyle", namespace = "http://admin")
	private String fontStyle;
	
	@XmlElement(name = "fontVariant", namespace = "http://admin")
	private String fontVariant;
	
	@XmlElement(name = "textColor", namespace = "http://admin")
	private String textColor;
	
	@XmlElement(name = "textDecoration", namespace = "http://admin")
	private String textDecoration;
	
	@XmlElement(name = "linkColor", namespace = "http://admin")
	private String linkColor;
	
	@XmlElement(name = "linkHoverColor", namespace = "http://admin")
	private String linkHoverColor;
	
	@XmlElement(name = "linkVisitedColor", namespace = "http://admin")
	private String linkVisitedColor;
	
	@XmlElement(name = "buttonFontFamily", namespace = "http://admin")
	private String buttonFontFamily;
	
	@XmlElement(name = "buttonFontSize", namespace = "http://admin")
	private String buttonFontSize;
	
	@XmlElement(name = "buttonFontStyle", namespace = "http://admin")
	private String buttonFontStyle;
	
	@XmlElement(name = "buttonFontVariant", namespace = "http://admin")
	private String buttonFontVariant;
	
	@XmlElement(name = "buttonFontWeight", namespace = "http://admin")
	private String buttonFontWeight;
	
	@XmlElement(name = "buttonTextColor", namespace = "http://admin")
	private String buttonTextColor;
	
	@XmlElement(name = "buttonTextDecoration", namespace = "http://admin")
	private String buttonTextDecoration;
	
	@XmlElement(name = "buttonBackgroundColor", namespace = "http://admin")
	private String buttonBackgroundColor;
	
	@XmlElement(name = "buttonBackgroundImage", namespace = "http://admin")
	private String buttonBackgroundImage;
	
	@XmlElement(name = "buttonGradientColor", namespace = "http://admin")
	private String buttonGradientColor;
	
	@XmlElement(name = "buttonBorderStyle", namespace = "http://admin")
	private String buttonBorderStyle;
	
	@XmlElement(name = "buttonBorderWidth", namespace = "http://admin")
	private String buttonBorderWidth;
	
	@XmlElement(name = "buttonBorderColor", namespace = "http://admin")
	private String buttonBorderColor;
	
	@XmlElement(name = "buttonControlColor", namespace = "http://admin")
	private String buttonControlColor;
	
	@XmlElement(name = "buttonCornerRadius", namespace = "http://admin")
	private String buttonCornerRadius;
	
	@XmlElement(name = "headerBackgroundColor", namespace = "http://admin")
	private String headerBackgroundColor;
	
	@XmlElement(name = "headerBackgroundImage", namespace = "http://admin")
	private String headerBackgroundImage;
	
	@XmlElement(name = "headerGradientColor", namespace = "http://admin")
	private String headerGradientColor;
	
	@XmlElement(name = "headerBorderStyle", namespace = "http://admin")
	private String headerBorderStyle;
	
	@XmlElement(name = "headerBorderWidth", namespace = "http://admin")
	private String headerBorderWidth;
	
	@XmlElement(name = "headerBorderColor", namespace = "http://admin")
	private String headerBorderColor;
	
	@XmlElement(name = "headerFontFamily", namespace = "http://admin")
	private String headerFontFamily;
	
	@XmlElement(name = "headerFontSize", namespace = "http://admin")
	private String headerFontSize;
	
	@XmlElement(name = "headerFontStyle", namespace = "http://admin")
	private String headerFontStyle;
	
	@XmlElement(name = "headerFontVariant", namespace = "http://admin")
	private String headerFontVariant;
	
	@XmlElement(name = "headerFontWeight", namespace = "http://admin")
	private String headerFontWeight;
	
	@XmlElement(name = "headerTextColor", namespace = "http://admin")
	private String headerTextColor;
	
	@XmlElement(name = "headerTextDecoration", namespace = "http://admin")
	private String headerTextDecoration;
	
	@XmlElement(name = "toolbarBackgroundColor", namespace = "http://admin")
	private String toolbarBackgroundColor;
	
	@XmlElement(name = "toolbarBackgroundImage", namespace = "http://admin")
	private String toolbarBackgroundImage;
	
	@XmlElement(name = "toolbarGradientColor", namespace = "http://admin")
	private String toolbarGradientColor;
	
	@XmlElement(name = "toolbarBorderColor", namespace = "http://admin")
	private String toolbarBorderColor;
	
	@XmlElement(name = "toolbarBorderStyle", namespace = "http://admin")
	private String toolbarBorderStyle;
	
	@XmlElement(name = "toolbarBorderWidth", namespace = "http://admin")
	private String toolbarBorderWidth;
	
	@XmlElement(name = "toolbarFontFamily", namespace = "http://admin")
	private String toolbarFontFamily;
	
	@XmlElement(name = "toolbarFontSize", namespace = "http://admin")
	private String toolbarFontSize;
	
	@XmlElement(name = "toolbarFontStyle", namespace = "http://admin")
	private String toolbarFontStyle;
	
	@XmlElement(name = "toolbarFontVariant", namespace = "http://admin")
	private String toolbarFontVariant;
	
	@XmlElement(name = "toolbarFontWeight", namespace = "http://admin")
	private String toolbarFontWeight;
	
	@XmlElement(name = "toolbarTextColor", namespace = "http://admin")
	private String toolbarTextColor;
	
	@XmlElement(name = "toolbarTextDecoration", namespace = "http://admin")
	private String toolbarTextDecoration;
	
	@XmlElement(name = "tabBackgroundColor", namespace = "http://admin")
	private String tabBackgroundColor;
	
	@XmlElement(name = "tabBackgroundImage", namespace = "http://admin")
	private String tabBackgroundImage;
	
	@XmlElement(name = "tabGradientColor", namespace = "http://admin")
	private String tabGradientColor;
	
	@XmlElement(name = "tabBorderColor", namespace = "http://admin")
	private String tabBorderColor;
	
	@XmlElement(name = "tabCornerRadius", namespace = "http://admin")
	private String tabCornerRadius;
	
	@XmlElement(name = "tabFontFamily", namespace = "http://admin")
	private String tabFontFamily;
	
	@XmlElement(name = "tabFontSize", namespace = "http://admin")
	private String tabFontSize;
	
	@XmlElement(name = "tabFontStyle", namespace = "http://admin")
	private String tabFontStyle;
	
	@XmlElement(name = "tabFontWeight", namespace = "http://admin")
	private String tabFontWeight;
	
	@XmlElement(name = "tabTextColor", namespace = "http://admin")
	private String tabTextColor;
	
	@XmlElement(name = "tabTextDecoration", namespace = "http://admin")
	private String tabTextDecoration;
	
	@XmlElement(name = "tabActiveBackgroundColor", namespace = "http://admin")
	private String tabActiveBackgroundColor;
	
	@XmlElement(name = "tabActiveBackgroundImage", namespace = "http://admin")
	private String tabActiveBackgroundImage;
	
	@XmlElement(name = "tabActiveGradientColor", namespace = "http://admin")
	private String tabActiveGradientColor;
	
	@XmlElement(name = "tabActiveBorderColor", namespace = "http://admin")
	private String tabActiveBorderColor;
	
	@XmlElement(name = "tabActiveFontFamily", namespace = "http://admin")
	private String tabActiveFontFamily;
	
	@XmlElement(name = "tabActiveFontSize", namespace = "http://admin")
	private String tabActiveFontSize;
	
	@XmlElement(name = "tabActiveFontStyle", namespace = "http://admin")
	private String tabActiveFontStyle;
	
	@XmlElement(name = "tabActiveFontWeight", namespace = "http://admin")
	private String tabActiveFontWeight;
	
	@XmlElement(name = "tabActiveTextColor", namespace = "http://admin")
	private String tabActiveTextColor;
	
	@XmlElement(name = "tabActiveTextDecoration", namespace = "http://admin")
	private String tabActiveTextDecoration;
	
	@XmlElement(name = "tabInactiveBackgroundColor", namespace = "http://admin")
	private String tabInactiveBackgroundColor;
	
	@XmlElement(name = "tabInactiveBackgroundImage", namespace = "http://admin")
	private String tabInactiveBackgroundImage;
	
	@XmlElement(name = "tabInactiveGradientColor", namespace = "http://admin")
	private String tabInactiveGradientColor;
	
	@XmlElement(name = "tabInactiveBorderColor", namespace = "http://admin")
	private String tabInactiveBorderColor;
	
	@XmlElement(name = "tabInactiveFontFamily", namespace = "http://admin")
	private String tabInactiveFontFamily;
	
	@XmlElement(name = "tabInactiveFontSize", namespace = "http://admin")
	private String tabInactiveFontSize;
	
	@XmlElement(name = "tabInactiveFontStyle", namespace = "http://admin")
	private String tabInactiveFontStyle;
	
	@XmlElement(name = "tabInactiveFontWeight", namespace = "http://admin")
	private String tabInactiveFontWeight;
	
	@XmlElement(name = "tabInactiveTextColor", namespace = "http://admin")
	private String tabInactiveTextColor;
	
	@XmlElement(name = "tabInactiveTextDecoration", namespace = "http://admin")
	private String tabInactiveTextDecoration;
	
	@XmlElement(name = "tabDisabledBackgroundColor", namespace = "http://admin")
	private String tabDisabledBackgroundColor;
	
	@XmlElement(name = "tabDisabledBackgroundImage", namespace = "http://admin")
	private String tabDisabledBackgroundImage;
	
	@XmlElement(name = "tabDisabledGradientColor", namespace = "http://admin")
	private String tabDisabledGradientColor;
	
	@XmlElement(name = "tabDisabledBorderColor", namespace = "http://admin")
	private String tabDisabledBorderColor;
	
	@XmlElement(name = "tabDisabledFontFamily", namespace = "http://admin")
	private String tabDisabledFontFamily;
	
	@XmlElement(name = "tabDisabledFontSize", namespace = "http://admin")
	private String tabDisabledFontSize;
	
	@XmlElement(name = "tabDisabledFontStyle", namespace = "http://admin")
	private String tabDisabledFontStyle;
	
	@XmlElement(name = "tabDisabledFontWeight", namespace = "http://admin")
	private String tabDisabledFontWeight;
	
	@XmlElement(name = "tabDisabledTextColor", namespace = "http://admin")
	private String tabDisabledTextColor;
	
	@XmlElement(name = "tabDisabledTextDecoration", namespace = "http://admin")
	private String tabDisabledTextDecoration;
	
	@XmlElement(name = "tableBackgroundColor", namespace = "http://admin")
	private String tableBackgroundColor;
	
	@XmlElement(name = "tableBorderColor", namespace = "http://admin")
	private String tableBorderColor;
	
	@XmlElement(name = "tableBorderWidth", namespace = "http://admin")
	private String tableBorderWidth;
	
	@XmlElement(name = "tableCellBackgroundColor", namespace = "http://admin")
	private String tableCellBackgroundColor;
	
	@XmlElement(name = "tableCellBackgroundImage", namespace = "http://admin")
	private String tableCellBackgroundImage;
	
	@XmlElement(name = "tableCellGradientColor", namespace = "http://admin")
	private String tableCellGradientColor;
	
	@XmlElement(name = "tableCellBorderColor", namespace = "http://admin")
	private String tableCellBorderColor;
	
	@XmlElement(name = "tableCellFontFamily", namespace = "http://admin")
	private String tableCellFontFamily;
	
	@XmlElement(name = "tableCellFontSize", namespace = "http://admin")
	private String tableCellFontSize;
	
	@XmlElement(name = "tableCellFontStyle", namespace = "http://admin")
	private String tableCellFontStyle;
	
	@XmlElement(name = "tableCellFontVariant", namespace = "http://admin")
	private String tableCellFontVariant;
	
	@XmlElement(name = "tableCellFontWeight", namespace = "http://admin")
	private String tableCellFontWeight;
	
	@XmlElement(name = "tableCellTextColor", namespace = "http://admin")
	private String tableCellTextColor;
	
	@XmlElement(name = "tableCellTextDecoration", namespace = "http://admin")
	private String tableCellTextDecoration;
	
	@XmlElement(name = "tableHeaderBackgroundColor", namespace = "http://admin")
	private String tableHeaderBackgroundColor;
	
	@XmlElement(name = "tableHeaderBackgroundImage", namespace = "http://admin")
	private String tableHeaderBackgroundImage;
	
	@XmlElement(name = "tableHeaderGradientColor", namespace = "http://admin")
	private String tableHeaderGradientColor;
	
	@XmlElement(name = "tableHeaderBorderColor", namespace = "http://admin")
	private String tableHeaderBorderColor;
	
	@XmlElement(name = "tableHeaderFontFamily", namespace = "http://admin")
	private String tableHeaderFontFamily;
	
	@XmlElement(name = "tableHeaderFontSize", namespace = "http://admin")
	private String tableHeaderFontSize;
	
	@XmlElement(name = "tableHeaderFontStyle", namespace = "http://admin")
	private String tableHeaderFontStyle;
	
	@XmlElement(name = "tableHeaderFontVariant", namespace = "http://admin")
	private String tableHeaderFontVariant;
	
	@XmlElement(name = "tableHeaderFontWeight", namespace = "http://admin")
	private String tableHeaderFontWeight;
	
	@XmlElement(name = "tableHeaderTextColor", namespace = "http://admin")
	private String tableHeaderTextColor;
	
	@XmlElement(name = "tableHeaderTextDecoration", namespace = "http://admin")
	private String tableHeaderTextDecoration;
	
	@XmlElement(name = "tableFooterBackgroundColor", namespace = "http://admin")
	private String tableFooterBackgroundColor;
	
	@XmlElement(name = "tableFooterBackgroundImage", namespace = "http://admin")
	private String tableFooterBackgroundImage;
	
	@XmlElement(name = "tableFooterGradientColor", namespace = "http://admin")
	private String tableFooterGradientColor;
	
	@XmlElement(name = "tableFooterBorderColor", namespace = "http://admin")
	private String tableFooterBorderColor;
	
	@XmlElement(name = "tableFooterFontFamily", namespace = "http://admin")
	private String tableFooterFontFamily;
	
	@XmlElement(name = "tableFooterFontSize", namespace = "http://admin")
	private String tableFooterFontSize;
	
	@XmlElement(name = "tableFooterFontStyle", namespace = "http://admin")
	private String tableFooterFontStyle;
	
	@XmlElement(name = "tableFooterFontVariant", namespace = "http://admin")
	private String tableFooterFontVariant;
	
	@XmlElement(name = "tableFooterFontWeight", namespace = "http://admin")
	private String tableFooterFontWeight;
	
	@XmlElement(name = "tableFooterTextColor", namespace = "http://admin")
	private String tableFooterTextColor;
	
	@XmlElement(name = "tableFooterTextDecoration", namespace = "http://admin")
	private String tableFooterTextDecoration;
	
	@XmlElement(name = "tableSubHeaderBackgroundColor", namespace = "http://admin")
	private String tableSubHeaderBackgroundColor;
	
	@XmlElement(name = "tableSubFooterBackgroundColor", namespace = "http://admin")
	private String tableSubFooterBackgroundColor;
	
	@XmlElement(name = "tooltipBackgroundColor", namespace = "http://admin")
	private String tooltipBackgroundColor;
	
	@XmlElement(name = "tooltipBackgroundImage", namespace = "http://admin")
	private String tooltipBackgroundImage;
	
	@XmlElement(name = "tooltipGradientColor", namespace = "http://admin")
	private String tooltipGradientColor;
	
	@XmlElement(name = "tooltipBorderColor", namespace = "http://admin")
	private String tooltipBorderColor;
	
	@XmlElement(name = "tooltipBorderStyle", namespace = "http://admin")
	private String tooltipBorderStyle;
	
	@XmlElement(name = "tooltipBorderWidth", namespace = "http://admin")
	private String tooltipBorderWidth;
	
	@XmlElement(name = "tooltipFontFamily", namespace = "http://admin")
	private String tooltipFontFamily;
	
	@XmlElement(name = "tooltipFontSize", namespace = "http://admin")
	private String tooltipFontSize;
	
	@XmlElement(name = "tooltipFontStyle", namespace = "http://admin")
	private String tooltipFontStyle;
	
	@XmlElement(name = "tooltipFontVariant", namespace = "http://admin")
	private String tooltipFontVariant;
	
	@XmlElement(name = "tooltipFontWeight", namespace = "http://admin")
	private String tooltipFontWeight;
	
	@XmlElement(name = "tooltipTextColor", namespace = "http://admin")
	private String tooltipTextColor;
	
	@XmlElement(name = "tooltipTextDecoration", namespace = "http://admin")
	private String tooltipTextDecoration;
	
	@XmlElement(name = "highlightBackgroundColor", namespace = "http://admin")
	private String highlightBackgroundColor;
	
	@XmlElement(name = "highlightBackgroundImage", namespace = "http://admin")
	private String highlightBackgroundImage;
	
	@XmlElement(name = "highlightBorderStyle", namespace = "http://admin")
	private String highlightBorderStyle;
	
	@XmlElement(name = "highlightBorderWidth", namespace = "http://admin")
	private String highlightBorderWidth;
	
	@XmlElement(name = "highlightBorderColor", namespace = "http://admin")
	private String highlightBorderColor;
	
	@XmlElement(name = "highlightFontFamily", namespace = "http://admin")
	private String highlightFontFamily;
	
	@XmlElement(name = "highlightFontSize", namespace = "http://admin")
	private String highlightFontSize;
	
	@XmlElement(name = "highlightFontStyle", namespace = "http://admin")
	private String highlightFontStyle;
	
	@XmlElement(name = "highlightFontWeight", namespace = "http://admin")
	private String highlightFontWeight;
	
	@XmlElement(name = "highlightTextColor", namespace = "http://admin")
	private String highlightTextColor;
	
	@XmlElement(name = "highlightTextDecoration", namespace = "http://admin")
	private String highlightTextDecoration;
	
	@XmlElement(name = "highlightControlColor", namespace = "http://admin")
	private String highlightControlColor;
	
	@XmlElement(name = "focusBackgroundColor", namespace = "http://admin")
	private String focusBackgroundColor;
	
	@XmlElement(name = "focusBackgroundImage", namespace = "http://admin")
	private String focusBackgroundImage;
	
	@XmlElement(name = "focusGradientColor", namespace = "http://admin")
	private String focusGradientColor;
	
	@XmlElement(name = "focusBorderStyle", namespace = "http://admin")
	private String focusBorderStyle;
	
	@XmlElement(name = "focusBorderWidth", namespace = "http://admin")
	private String focusBorderWidth;
	
	@XmlElement(name = "focusBorderColor", namespace = "http://admin")
	private String focusBorderColor;
	
	@XmlElement(name = "focusFontFamily", namespace = "http://admin")
	private String focusFontFamily;
	
	@XmlElement(name = "focusFontSize", namespace = "http://admin")
	private String focusFontSize;
	
	@XmlElement(name = "focusFontStyle", namespace = "http://admin")
	private String focusFontStyle;
	
	@XmlElement(name = "focusFontWeight", namespace = "http://admin")
	private String focusFontWeight;
	
	@XmlElement(name = "focusTextColor", namespace = "http://admin")
	private String focusTextColor;
	
	@XmlElement(name = "focusTextDecoration", namespace = "http://admin")
	private String focusTextDecoration;
	
	@XmlElement(name = "focusControlColor", namespace = "http://admin")
	private String focusControlColor;
	
	@XmlElement(name = "disabledBackgroundColor", namespace = "http://admin")
	private String disabledBackgroundColor;
	
	@XmlElement(name = "disabledBackgroundImage", namespace = "http://admin")
	private String disabledBackgroundImage;
	
	@XmlElement(name = "disabledBorderStyle", namespace = "http://admin")
	private String disabledBorderStyle;
	
	@XmlElement(name = "disabledBorderWidth", namespace = "http://admin")
	private String disabledBorderWidth;
	
	@XmlElement(name = "disabledBorderColor", namespace = "http://admin")
	private String disabledBorderColor;
	
	@XmlElement(name = "disabledFontFamily", namespace = "http://admin")
	private String disabledFontFamily;
	
	@XmlElement(name = "disabledFontSize", namespace = "http://admin")
	private String disabledFontSize;
	
	@XmlElement(name = "disabledFontStyle", namespace = "http://admin")
	private String disabledFontStyle;
	
	@XmlElement(name = "disabledFontWeight", namespace = "http://admin")
	private String disabledFontWeight;
	
	@XmlElement(name = "disabledTextColor", namespace = "http://admin")
	private String disabledTextColor;
	
	@XmlElement(name = "disabledTextDecoration", namespace = "http://admin")
	private String disabledTextDecoration;
	
	@XmlElement(name = "disabledControlColor", namespace = "http://admin")
	private String disabledControlColor;
	
	@XmlElement(name = "themeBorderColor", namespace = "http://admin")
	private String themeBorderColor;
	
	@XmlElement(name = "themeBackgroundColor", namespace = "http://admin")
	private String themeBackgroundColor;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Skin() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public String getHighlightFontStyle() {
		return highlightFontStyle;
	}
	
	public void setHighlightFontStyle(String highlightFontStyle) {
		this.highlightFontStyle = highlightFontStyle;
	}
	
	public String getHighlightFontWeight() {
		return highlightFontWeight;
	}
	
	public void setHighlightFontWeight(String highlightFontWeight) {
		this.highlightFontWeight = highlightFontWeight;
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
	
	public String getHighlightControlColor() {
		return highlightControlColor;
	}
	
	public void setHighlightControlColor(String highlightControlColor) {
		this.highlightControlColor = highlightControlColor;
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
	
	public String getFocusFontStyle() {
		return focusFontStyle;
	}
	
	public void setFocusFontStyle(String focusFontStyle) {
		this.focusFontStyle = focusFontStyle;
	}
	
	public String getFocusFontWeight() {
		return focusFontWeight;
	}
	
	public void setFocusFontWeight(String focusFontWeight) {
		this.focusFontWeight = focusFontWeight;
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
	
	public String getFocusControlColor() {
		return focusControlColor;
	}
	
	public void setFocusControlColor(String focusControlColor) {
		this.focusControlColor = focusControlColor;
	}
	
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
	
	public String getDisabledControlColor() {
		return disabledControlColor;
	}
	
	public void setDisabledControlColor(String disabledControlColor) {
		this.disabledControlColor = disabledControlColor;
	}
	
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
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			Skin other = (Skin) object;
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Skin other = (Skin) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Skin: name="+name;
	}
	
}

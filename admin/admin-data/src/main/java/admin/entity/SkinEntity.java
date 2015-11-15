package admin.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;


@Entity(name = "Skin")
@Table(name = "skin", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Cache(usage = READ_WRITE)
public class SkinEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "width")
	private String width;
	
	@Column(name = "height")
	private String height;
	
	@Column(name = "margin")
	private String margin;
	
	@Column(name = "margin_top")
	private String marginTop;
	
	@Column(name = "margin_left")
	private String marginLeft;
	
	@Column(name = "margin_right")
	private String marginRight;
	
	@Column(name = "margin_bottom")
	private String marginBottom;
	
	@Column(name = "padding")
	private String padding;
	
	@Column(name = "padding_top")
	private String paddingTop;
	
	@Column(name = "padding_left")
	private String paddingLeft;
	
	@Column(name = "padding_right")
	private String paddingRight;
	
	@Column(name = "padding_bottom")
	private String paddingBottom;
	
	@Column(name = "border_style")
	private String borderStyle;
	
	@Column(name = "border_width")
	private String borderWidth;
	
	@Column(name = "border_color")
	private String borderColor;
	
	@Column(name = "corner_radius")
	private String cornerRadius;
	
	@Column(name = "background_color")
	private String backgroundColor;
	
	@Column(name = "background_image")
	private String backgroundImage;
	
	@Column(name = "gradient_color")
	private String gradientColor;
	
	@Column(name = "gradient_image")
	private String gradientImage;
	
	@Column(name = "font_size")
	private String fontSize;
	
	@Column(name = "font_family")
	private String fontFamily;
	
	@Column(name = "font_weight")
	private String fontWeight;
	
	@Column(name = "font_style")
	private String fontStyle;
	
	@Column(name = "font_variant")
	private String fontVariant;
	
	@Column(name = "text_color")
	private String textColor;
	
	@Column(name = "text_decoration")
	private String textDecoration;
	
	@Column(name = "link_color")
	private String linkColor;
	
	@Column(name = "link_hover_color")
	private String linkHoverColor;
	
	@Column(name = "link_visited_color")
	private String linkVisitedColor;
	
	@Column(name = "button_font_family")
	private String buttonFontFamily;
	
	@Column(name = "button_font_size")
	private String buttonFontSize;
	
	@Column(name = "button_font_style")
	private String buttonFontStyle;
	
	@Column(name = "button_font_variant")
	private String buttonFontVariant;
	
	@Column(name = "button_font_weight")
	private String buttonFontWeight;
	
	@Column(name = "button_text_color")
	private String buttonTextColor;
	
	@Column(name = "button_text_decoration")
	private String buttonTextDecoration;
	
	@Column(name = "button_background_color")
	private String buttonBackgroundColor;
	
	@Column(name = "button_background_image")
	private String buttonBackgroundImage;
	
	@Column(name = "button_gradient_color")
	private String buttonGradientColor;
	
	@Column(name = "button_border_style")
	private String buttonBorderStyle;
	
	@Column(name = "button_border_width")
	private String buttonBorderWidth;
	
	@Column(name = "button_border_color")
	private String buttonBorderColor;
	
	@Column(name = "button_control_color")
	private String buttonControlColor;
	
	@Column(name = "button_corner_radius")
	private String buttonCornerRadius;
	
	@Column(name = "header_background_color")
	private String headerBackgroundColor;
	
	@Column(name = "header_background_image")
	private String headerBackgroundImage;
	
	@Column(name = "header_gradient_color")
	private String headerGradientColor;
	
	@Column(name = "header_border_style")
	private String headerBorderStyle;
	
	@Column(name = "header_border_width")
	private String headerBorderWidth;
	
	@Column(name = "header_border_color")
	private String headerBorderColor;
	
	@Column(name = "header_font_family")
	private String headerFontFamily;
	
	@Column(name = "header_font_size")
	private String headerFontSize;
	
	@Column(name = "header_font_style")
	private String headerFontStyle;
	
	@Column(name = "header_font_variant")
	private String headerFontVariant;
	
	@Column(name = "header_font_weight")
	private String headerFontWeight;
	
	@Column(name = "header_text_color")
	private String headerTextColor;
	
	@Column(name = "header_text_decoration")
	private String headerTextDecoration;
	
	@Column(name = "toolbar_background_color")
	private String toolbarBackgroundColor;
	
	@Column(name = "toolbar_background_image")
	private String toolbarBackgroundImage;
	
	@Column(name = "toolbar_gradient_color")
	private String toolbarGradientColor;
	
	@Column(name = "toolbar_border_color")
	private String toolbarBorderColor;
	
	@Column(name = "toolbar_border_style")
	private String toolbarBorderStyle;
	
	@Column(name = "toolbar_border_width")
	private String toolbarBorderWidth;
	
	@Column(name = "toolbar_font_family")
	private String toolbarFontFamily;
	
	@Column(name = "toolbar_font_size")
	private String toolbarFontSize;
	
	@Column(name = "toolbar_font_style")
	private String toolbarFontStyle;
	
	@Column(name = "toolbar_font_variant")
	private String toolbarFontVariant;
	
	@Column(name = "toolbar_font_weight")
	private String toolbarFontWeight;
	
	@Column(name = "toolbar_text_color")
	private String toolbarTextColor;
	
	@Column(name = "toolbar_text_decoration")
	private String toolbarTextDecoration;
	
	@Column(name = "tab_background_color")
	private String tabBackgroundColor;
	
	@Column(name = "tab_background_image")
	private String tabBackgroundImage;
	
	@Column(name = "tab_gradient_color")
	private String tabGradientColor;
	
	@Column(name = "tab_border_color")
	private String tabBorderColor;
	
	@Column(name = "tab_corner_radius")
	private String tabCornerRadius;
	
	@Column(name = "tab_font_family")
	private String tabFontFamily;
	
	@Column(name = "tab_font_size")
	private String tabFontSize;
	
	@Column(name = "tab_font_style")
	private String tabFontStyle;
	
	@Column(name = "tab_font_weight")
	private String tabFontWeight;
	
	@Column(name = "tab_text_color")
	private String tabTextColor;
	
	@Column(name = "tab_text_decoration")
	private String tabTextDecoration;
	
	@Column(name = "tab_active_background_color")
	private String tabActiveBackgroundColor;
	
	@Column(name = "tab_active_background_image")
	private String tabActiveBackgroundImage;
	
	@Column(name = "tab_active_gradient_color")
	private String tabActiveGradientColor;
	
	@Column(name = "tab_active_border_color")
	private String tabActiveBorderColor;
	
	@Column(name = "tab_active_font_family")
	private String tabActiveFontFamily;
	
	@Column(name = "tab_active_font_size")
	private String tabActiveFontSize;
	
	@Column(name = "tab_active_font_style")
	private String tabActiveFontStyle;
	
	@Column(name = "tab_active_font_weight")
	private String tabActiveFontWeight;
	
	@Column(name = "tab_active_text_color")
	private String tabActiveTextColor;
	
	@Column(name = "tab_active_text_decoration")
	private String tabActiveTextDecoration;
	
	@Column(name = "tab_inactive_background_color")
	private String tabInactiveBackgroundColor;
	
	@Column(name = "tab_inactive_background_image")
	private String tabInactiveBackgroundImage;
	
	@Column(name = "tab_inactive_gradient_color")
	private String tabInactiveGradientColor;
	
	@Column(name = "tab_inactive_border_color")
	private String tabInactiveBorderColor;
	
	@Column(name = "tab_inactive_font_family")
	private String tabInactiveFontFamily;
	
	@Column(name = "tab_inactive_font_size")
	private String tabInactiveFontSize;
	
	@Column(name = "tab_inactive_font_style")
	private String tabInactiveFontStyle;
	
	@Column(name = "tab_inactive_font_weight")
	private String tabInactiveFontWeight;
	
	@Column(name = "tab_inactive_text_color")
	private String tabInactiveTextColor;
	
	@Column(name = "tab_inactive_text_decoration")
	private String tabInactiveTextDecoration;
	
	@Column(name = "tab_disabled_background_color")
	private String tabDisabledBackgroundColor;
	
	@Column(name = "tab_disabled_background_image")
	private String tabDisabledBackgroundImage;
	
	@Column(name = "tab_disabled_gradient_color")
	private String tabDisabledGradientColor;
	
	@Column(name = "tab_disabled_border_color")
	private String tabDisabledBorderColor;
	
	@Column(name = "tab_disabled_font_family")
	private String tabDisabledFontFamily;
	
	@Column(name = "tab_disabled_font_size")
	private String tabDisabledFontSize;
	
	@Column(name = "tab_disabled_font_style")
	private String tabDisabledFontStyle;
	
	@Column(name = "tab_disabled_font_weight")
	private String tabDisabledFontWeight;
	
	@Column(name = "tab_disabled_text_color")
	private String tabDisabledTextColor;
	
	@Column(name = "tab_disabled_text_decoration")
	private String tabDisabledTextDecoration;
	
	@Column(name = "table_background_color")
	private String tableBackgroundColor;
	
	@Column(name = "table_border_color")
	private String tableBorderColor;
	
	@Column(name = "table_border_width")
	private String tableBorderWidth;
	
	@Column(name = "table_cell_background_color")
	private String tableCellBackgroundColor;
	
	@Column(name = "table_cell_background_image")
	private String tableCellBackgroundImage;
	
	@Column(name = "table_cell_gradient_color")
	private String tableCellGradientColor;
	
	@Column(name = "table_cell_border_color")
	private String tableCellBorderColor;
	
	@Column(name = "table_cell_font_family")
	private String tableCellFontFamily;
	
	@Column(name = "table_cell_font_size")
	private String tableCellFontSize;
	
	@Column(name = "table_cell_font_style")
	private String tableCellFontStyle;
	
	@Column(name = "table_cell_font_variant")
	private String tableCellFontVariant;
	
	@Column(name = "table_cell_font_weight")
	private String tableCellFontWeight;
	
	@Column(name = "table_cell_text_color")
	private String tableCellTextColor;
	
	@Column(name = "table_cell_text_decoration")
	private String tableCellTextDecoration;
	
	@Column(name = "table_header_background_color")
	private String tableHeaderBackgroundColor;
	
	@Column(name = "table_header_background_image")
	private String tableHeaderBackgroundImage;
	
	@Column(name = "table_header_gradient_color")
	private String tableHeaderGradientColor;
	
	@Column(name = "table_header_border_color")
	private String tableHeaderBorderColor;
	
	@Column(name = "table_header_font_family")
	private String tableHeaderFontFamily;
	
	@Column(name = "table_header_font_size")
	private String tableHeaderFontSize;
	
	@Column(name = "table_header_font_style")
	private String tableHeaderFontStyle;
	
	@Column(name = "table_header_font_variant")
	private String tableHeaderFontVariant;
	
	@Column(name = "table_header_font_weight")
	private String tableHeaderFontWeight;
	
	@Column(name = "table_header_text_color")
	private String tableHeaderTextColor;
	
	@Column(name = "table_header_text_decoration")
	private String tableHeaderTextDecoration;
	
	@Column(name = "table_footer_background_color")
	private String tableFooterBackgroundColor;
	
	@Column(name = "table_footer_background_image")
	private String tableFooterBackgroundImage;
	
	@Column(name = "table_footer_gradient_color")
	private String tableFooterGradientColor;
	
	@Column(name = "table_footer_border_color")
	private String tableFooterBorderColor;
	
	@Column(name = "table_footer_font_family")
	private String tableFooterFontFamily;
	
	@Column(name = "table_footer_font_size")
	private String tableFooterFontSize;
	
	@Column(name = "table_footer_font_style")
	private String tableFooterFontStyle;
	
	@Column(name = "table_footer_font_variant")
	private String tableFooterFontVariant;
	
	@Column(name = "table_footer_font_weight")
	private String tableFooterFontWeight;
	
	@Column(name = "table_footer_text_color")
	private String tableFooterTextColor;
	
	@Column(name = "table_footer_text_decoration")
	private String tableFooterTextDecoration;
	
	@Column(name = "table_sub_header_background_color")
	private String tableSubHeaderBackgroundColor;
	
	@Column(name = "table_sub_footer_background_color")
	private String tableSubFooterBackgroundColor;
	
	@Column(name = "tooltip_background_color")
	private String tooltipBackgroundColor;
	
	@Column(name = "tooltip_background_image")
	private String tooltipBackgroundImage;
	
	@Column(name = "tooltip_gradient_color")
	private String tooltipGradientColor;
	
	@Column(name = "tooltip_border_color")
	private String tooltipBorderColor;
	
	@Column(name = "tooltip_border_style")
	private String tooltipBorderStyle;
	
	@Column(name = "tooltip_border_width")
	private String tooltipBorderWidth;
	
	@Column(name = "tooltip_font_family")
	private String tooltipFontFamily;
	
	@Column(name = "tooltip_font_size")
	private String tooltipFontSize;
	
	@Column(name = "tooltip_font_style")
	private String tooltipFontStyle;
	
	@Column(name = "tooltip_font_variant")
	private String tooltipFontVariant;
	
	@Column(name = "tooltip_font_weight")
	private String tooltipFontWeight;
	
	@Column(name = "tooltip_text_color")
	private String tooltipTextColor;
	
	@Column(name = "tooltip_text_decoration")
	private String tooltipTextDecoration;
	
	@Column(name = "highlight_background_color")
	private String highlightBackgroundColor;
	
	@Column(name = "highlight_background_image")
	private String highlightBackgroundImage;
	
	@Column(name = "highlight_border_style")
	private String highlightBorderStyle;
	
	@Column(name = "highlight_border_width")
	private String highlightBorderWidth;
	
	@Column(name = "highlight_border_color")
	private String highlightBorderColor;
	
	@Column(name = "highlight_font_family")
	private String highlightFontFamily;
	
	@Column(name = "highlight_font_size")
	private String highlightFontSize;
	
	@Column(name = "highlight_font_style")
	private String highlightFontStyle;
	
	@Column(name = "highlight_font_weight")
	private String highlightFontWeight;
	
	@Column(name = "highlight_text_color")
	private String highlightTextColor;
	
	@Column(name = "highlight_text_decoration")
	private String highlightTextDecoration;
	
	@Column(name = "highlight_control_color")
	private String highlightControlColor;
	
	@Column(name = "focus_background_color")
	private String focusBackgroundColor;
	
	@Column(name = "focus_background_image")
	private String focusBackgroundImage;
	
	@Column(name = "focus_gradient_color")
	private String focusGradientColor;
	
	@Column(name = "focus_border_style")
	private String focusBorderStyle;
	
	@Column(name = "focus_border_width")
	private String focusBorderWidth;
	
	@Column(name = "focus_border_color")
	private String focusBorderColor;
	
	@Column(name = "focus_font_family")
	private String focusFontFamily;
	
	@Column(name = "focus_font_size")
	private String focusFontSize;
	
	@Column(name = "focus_font_style")
	private String focusFontStyle;
	
	@Column(name = "focus_font_weight")
	private String focusFontWeight;
	
	@Column(name = "focus_text_color")
	private String focusTextColor;
	
	@Column(name = "focus_text_decoration")
	private String focusTextDecoration;
	
	@Column(name = "focus_control_color")
	private String focusControlColor;
	
	@Column(name = "disabled_background_color")
	private String disabledBackgroundColor;
	
	@Column(name = "disabled_background_image")
	private String disabledBackgroundImage;
	
	@Column(name = "disabled_border_style")
	private String disabledBorderStyle;
	
	@Column(name = "disabled_border_width")
	private String disabledBorderWidth;
	
	@Column(name = "disabled_border_color")
	private String disabledBorderColor;
	
	@Column(name = "disabled_font_family")
	private String disabledFontFamily;
	
	@Column(name = "disabled_font_size")
	private String disabledFontSize;
	
	@Column(name = "disabled_font_style")
	private String disabledFontStyle;
	
	@Column(name = "disabled_font_weight")
	private String disabledFontWeight;
	
	@Column(name = "disabled_text_color")
	private String disabledTextColor;
	
	@Column(name = "disabled_text_decoration")
	private String disabledTextDecoration;
	
	@Column(name = "disabled_control_color")
	private String disabledControlColor;
	
	@Column(name = "theme_border_color")
	private String themeBorderColor;
	
	@Column(name = "theme_background_color")
	private String themeBackgroundColor;
	
	
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
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] name="+getName();
		return "getClass().getSimpleName(): name="+getName();
	}
	
}

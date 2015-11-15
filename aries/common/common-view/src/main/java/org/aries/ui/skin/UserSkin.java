package org.aries.ui.skin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.ui.event.ResetEvent;



@SessionScoped
@Named("userSkin")
@SuppressWarnings("serial")
public class UserSkin /*extends AbstractSkin*/ implements Serializable {

	private Map<String, ManagedSkin> blocks = new HashMap<String, ManagedSkin>();

	@Inject
	private SkinProvider skinProvider;
	
	private String skinId;
	

	public UserSkin() {
		skinId = "blueSky";
		initialize();
	}

	public String getSkinId() {
		return skinId;
	}

	public void setSkinId(String skinId) {
		this.skinId = skinId;
	}
	
	public void observeReset(@Observes ResetEvent event) {
		initialize();
	}
	
	public void initialize() {
		if (skinProvider != null) {
			blocks.put("default", createDefaultManagedSkin());
			blocks.put("blueSky", createBlueSkyManagedSkin());
			blocks.put("classic", createClassicManagedSkin());
			blocks.put("deepMarine", createDeepMarineManagedSkin());
			blocks.put("emeraldTown", createEmeraldTownManagedSkin());
			blocks.put("japanCherry", createJapanCherryManagedSkin());
			blocks.put("plain", createPlainManagedSkin());
			blocks.put("ruby", createRubyManagedSkin());
			blocks.put("wine", createWineManagedSkin());
			//setSkinId(skinProvider.getSkin().getName());
		}
	}
	
	public ManagedSkin createDefaultManagedSkin() {
		ManagedSkin block = new DefaultManagedSkin(skinProvider);
		return block;
	}

	public ManagedSkin createClassicManagedSkin() {
		ManagedSkin block = new ClassicManagedSkin(skinProvider);
		return block;
	}
	
	public ManagedSkin createBlueSkyManagedSkin() {
		ManagedSkin block = new BlueSkyManagedSkin(skinProvider);
		return block;
	}

	public ManagedSkin createDeepMarineManagedSkin() {
		ManagedSkin block = new DeepMarineManagedSkin(skinProvider);
		return block;
	}
	
	public ManagedSkin createEmeraldTownManagedSkin() {
		ManagedSkin block = new EmeraldTownManagedSkin(skinProvider);
		return block;
	}
	
	public ManagedSkin createJapanCherryManagedSkin() {
		ManagedSkin block = new JapanCherryManagedSkin(skinProvider);
		return block;
	}

	public ManagedSkin createPlainManagedSkin() {
		ManagedSkin block = new PlainManagedSkin(skinProvider);
		return block;
	}

	public ManagedSkin createRubyManagedSkin() {
		ManagedSkin block = new RubyManagedSkin(skinProvider);
		return block;
	}

	public ManagedSkin createWineManagedSkin() {
		ManagedSkin block = new WineManagedSkin(skinProvider);
		return block;
	}
	
	protected ManagedSkin getBlock(String skinId) {
		ManagedSkin block = blocks.get(skinId);
		if (block == null) {
			initialize();
			block = blocks.get(skinId);
		}
		return block;
	}


	/*
	 * Size
	 * ----
	 */

	public String getWidth() {
		return getWidth(getSkinId());
	}

	public String getWidth(String skinId) {
		return getBlock(skinId).getWidth();
	}

	public String getHeight() {
		return getHeight(getSkinId());
	}

	public String getHeight(String skinId) {
		return getBlock(skinId).getHeight();
	}


	/*
	 * Margin
	 * ------
	 */

	public String getMargin() {
		return getMargin(getSkinId());
	}

	public String getMargin(String skinId) {
		return getMarginTop(skinId) + " " + getMarginRight(skinId) + " " + getMarginBottom(skinId) + " " + getMarginLeft(skinId);
	}

	public String getMarginTop() {
		return getMarginTop(getSkinId());
	}

	public String getMarginTop(String skinId) {
		return getBlock(skinId).getMarginTop();
	}

	public String getMarginRight() {
		return getMarginRight(getSkinId());
	}

	public String getMarginRight(String skinId) {
		return getBlock(skinId).getMarginRight();
	}

	public String getMarginBottom() {
		return getMarginBottom(getSkinId());
	}

	public String getMarginBottom(String skinId) {
		return getBlock(skinId).getMarginBottom();
	}

	public String getMarginLeft() {
		return getMarginLeft(getSkinId());
	}

	public String getMarginLeft(String skinId) {
		return getBlock(skinId).getMarginLeft();
	}


	/*
	 * Padding
	 * -------
	 */

	public String getPadding() {
		return getPadding(getSkinId());
	}

	public String getPadding(String skinId) {
		return getPaddingTop(skinId) + " " + getPaddingRight(skinId) + " " + getPaddingBottom(skinId) + " " + getPaddingLeft(skinId);
	}

	public String getPaddingTop() {
		return getPaddingTop(getSkinId());
	}

	public String getPaddingTop(String skinId) {
		return getBlock(skinId).getPaddingTop();
	}

	public String getPaddingRight() {
		return getPaddingRight(getSkinId());
	}

	public String getPaddingRight(String skinId) {
		return getBlock(skinId).getPaddingRight();
	}

	public String getPaddingBottom() {
		return getPaddingBottom(getSkinId());
	}

	public String getPaddingBottom(String skinId) {
		return getBlock(skinId).getPaddingBottom();
	}

	public String getPaddingLeft() {
		return getPaddingLeft(getSkinId());
	}

	public String getPaddingLeft(String skinId) {
		return getBlock(skinId).getPaddingLeft();
	}


	/*
	 * Border
	 * ------
	 */

	public String getBorder() {
		return getBorder(getSkinId());
	}

	public String getBorder(String skinId) {
		return getBorderWidth(skinId) + " " + getBorderStyle(skinId) + " " + getBorderColor(skinId);
	}

	public String getBorderStyle() {
		return getBorderStyle(getSkinId());
	}

	public String getBorderStyle(String skinId) {
		return getBlock(skinId).getBorderStyle();
	}

	public String getBorderWidth() {
		return getBorderWidth(getSkinId());
	}

	public String getBorderWidth(String skinId) {
		return getBlock(skinId).getBorderWidth();
	}

	public String getBorderColor() {
		return getBorderColor(getSkinId());
	}

	public String getBorderColor(String skinId) {
		return getBlock(skinId).getBorderColor();
	}

	public String getCornerRadius() {
		return getCornerRadius(getSkinId());
	}

	public String getCornerRadius(String skinId) {
		return getBlock(skinId).getCornerRadius();
	}

	
	/*
	 * Background
	 * ----------
	 */
	
	public String getBackgroundColor() {
		return getBackgroundColor(getSkinId());
	}

	public String getBackgroundColor(String skinId) {
		return getBlock(skinId).getBackgroundColor();
	}

	public String getBackgroundImage() {
		//background-image: url(#{resource['org.richfaces.images:colHdrGrad.png']})
		return getBackgroundImage(getSkinId());
	}

	public String getBackgroundImage(String skinId) {
		return getBlock(skinId).getBackgroundImage();
	}

	public String getGradientColor() {
		return getGradientColor(getSkinId());
	}

	public String getGradientColor(String skinId) {
		return getBlock(skinId).getGradientColor();
	}

	public String getGradientImage() {
		return getGradientImage(getSkinId());
	}

	public String getGradientImage(String skinId) {
		return getBlock(skinId).getGradientImage();
	}


	/*
	 * Text
	 * ----
	 */

	public String getFontSize() {
		return getFontSize(getSkinId());
	}

	public String getFontSize(String skinId) {
		return getBlock(skinId).getFontSize();
	}

	public String getFontFamily() {
		return getFontFamily(getSkinId());
	}

	public String getFontFamily(String skinId) {
		return getBlock(skinId).getFontFamily();
	}

	public String getFontWeight() {
		return getFontWeight(getSkinId());
	}

	public String getFontWeight(String skinId) {
		return getBlock(skinId).getFontWeight();
	}

	public String getFontStyle() {
		return getFontStyle(getSkinId());
	}

	public String getFontStyle(String skinId) {
		return getBlock(skinId).getFontStyle();
	}
	
	public String getTextColor() {
		return getTextColor(getSkinId());
	}

	public String getTextColor(String skinId) {
		return getBlock(skinId).getTextColor();
	}

	public String getTextDecoration() {
		return getTextDecoration(getSkinId());
	}

	public String getTextDecoration(String skinId) {
		return getBlock(skinId).getTextDecoration();
	}

	public String getLinkColor() {
		return getLinkColor(getSkinId());
	}

	public String getLinkColor(String skinId) {
		return getBlock(skinId).getLinkColor();
	}

	public String getLinkHoverColor() {
		return getLinkHoverColor(getSkinId());
	}

	public String getLinkHoverColor(String skinId) {
		return getBlock(skinId).getLinkHoverColor();
	}

	public String getLinkVisitedColor() {
		return getLinkVisitedColor(getSkinId());
	}

	public String getLinkVisitedColor(String skinId) {
		return getBlock(skinId).getLinkVisitedColor();
	}

	
	/*
	 * Button
	 * ------
	 */
	
	public String getButtonBackgroundColor() {
		return getButtonBackgroundColor(getSkinId());
	}

	public String getButtonBackgroundColor(String skinId) {
		return getBlock(skinId).getButtonBackgroundColor();
	}

	public String getButtonBackgroundImage() {
		return getButtonBackgroundImage(getSkinId());
	}

	public String getButtonBackgroundImage(String skinId) {
		return getBlock(skinId).getButtonBackgroundImage();
	}

	public String getButtonGradientColor() {
		return getButtonGradientColor(getSkinId());
	}

	public String getButtonGradientColor(String skinId) {
		return getBlock(skinId).getButtonGradientColor();
	}
	
	public String getButtonBorderColor() {
		return getButtonBorderColor(getSkinId());
	}

	public String getButtonBorderColor(String skinId) {
		return getBlock(skinId).getButtonBorderColor();
	}

	public String getButtonBorderStyle() {
		return getButtonBorderStyle(getSkinId());
	}

	public String getButtonBorderStyle(String skinId) {
		return getBlock(skinId).getButtonBorderStyle();
	}
	
	public String getButtonBorderWidth() {
		return getButtonBorderWidth(getSkinId());
	}

	public String getButtonBorderWidth(String skinId) {
		return getBlock(skinId).getButtonBorderWidth();
	}
	
	public String getButtonCornerRadius() {
		return getButtonCornerRadius(getSkinId());
	}

	public String getButtonCornerRadius(String skinId) {
		return getBlock(skinId).getButtonCornerRadius();
	}
	
	public String getButtonFontFamily() {
		return getButtonFontFamily(getSkinId());
	}

	public String getButtonFontFamily(String skinId) {
		return getBlock(skinId).getButtonFontFamily();
	}

	public String getButtonFontSize() {
		return getButtonFontSize(getSkinId());
	}

	public String getButtonFontSize(String skinId) {
		return getBlock(skinId).getButtonFontSize();
	}
	
	public String getButtonFontStyle() {
		return getButtonFontStyle(getSkinId());
	}

	public String getButtonFontStyle(String skinId) {
		return getBlock(skinId).getButtonFontStyle();
	}
	
	public String getButtonFontVariant() {
		return getButtonFontVariant(getSkinId());
	}

	public String getButtonFontVariant(String skinId) {
		return getBlock(skinId).getButtonFontVariant();
	}
	
	public String getButtonFontWeight() {
		return getButtonFontWeight(getSkinId());
	}

	public String getButtonFontWeight(String skinId) {
		return getBlock(skinId).getButtonFontWeight();
	}
	
	public String getButtonTextColor() {
		return getButtonTextColor(getSkinId());
	}

	public String getButtonTextColor(String skinId) {
		return getBlock(skinId).getButtonTextColor();
	}

	public String getButtonTextDecoration() {
		return getButtonTextDecoration(getSkinId());
	}

	public String getButtonTextDecoration(String skinId) {
		return getBlock(skinId).getButtonTextDecoration();
	}
	
	
	/*
	 * Header
	 * ------
	 */
	
	public String getHeaderBackgroundColor() {
		return getHeaderBackgroundColor(getSkinId());
	}

	public String getHeaderBackgroundColor(String skinId) {
		return getBlock(skinId).getHeaderBackgroundColor();
	}

	public String getHeaderBackgroundImage() {
		return getHeaderBackgroundImage(getSkinId());
	}

	public String getHeaderBackgroundImage(String skinId) {
		return getBlock(skinId).getHeaderBackgroundImage();
	}

	public String getHeaderBorderColor() {
		return getHeaderBorderColor(getSkinId());
	}

	public String getHeaderBorderColor(String skinId) {
		return getBlock(skinId).getHeaderBorderColor();
	}

	public String getHeaderBorderWidth() {
		return getHeaderBorderWidth(getSkinId());
	}

	public String getHeaderBorderWidth(String skinId) {
		return getBlock(skinId).getHeaderBorderWidth();
	}
	
	public String getHeaderFontFamily() {
		return getHeaderFontFamily(getSkinId());
	}

	public String getHeaderFontFamily(String skinId) {
		return getBlock(skinId).getHeaderFontFamily();
	}

	public String getHeaderFontSize() {
		return getHeaderFontSize(getSkinId());
	}

	public String getHeaderFontSize(String skinId) {
		return getBlock(skinId).getHeaderFontSize();
	}
	
	public String getHeaderFontStyle() {
		return getHeaderFontStyle(getSkinId());
	}

	public String getHeaderFontStyle(String skinId) {
		return getBlock(skinId).getHeaderFontStyle();
	}
	
	public String getHeaderFontWeight() {
		return getHeaderFontWeight(getSkinId());
	}

	public String getHeaderFontWeight(String skinId) {
		return getBlock(skinId).getHeaderFontWeight();
	}
	
	public String getHeaderTextColor() {
		return getHeaderTextColor(getSkinId());
	}

	public String getHeaderTextColor(String skinId) {
		return getBlock(skinId).getHeaderTextColor();
	}

	public String getHeaderTextDecoration() {
		return getHeaderTextDecoration(getSkinId());
	}

	public String getHeaderTextDecoration(String skinId) {
		return getBlock(skinId).getHeaderTextDecoration();
	}

	
	/*
	 * Toolbar
	 * -------
	 */
	
	public String getToolbarBackgroundColor() {
		return getToolbarBackgroundColor(getSkinId());
	}

	public String getToolbarBackgroundColor(String skinId) {
		return getBlock(skinId).getToolbarBackgroundColor();
	}

	public String getToolbarBackgroundImage() {
		return getToolbarBackgroundImage(getSkinId());
	}

	public String getToolbarBackgroundImage(String skinId) {
		return getBlock(skinId).getToolbarBackgroundImage();
	}

	public String getToolbarBorderColor() {
		return getToolbarBorderColor(getSkinId());
	}

	public String getToolbarBorderColor(String skinId) {
		return getBlock(skinId).getToolbarBorderColor();
	}

	public String getToolbarBorderWidth() {
		return getToolbarBorderWidth(getSkinId());
	}

	public String getToolbarBorderWidth(String skinId) {
		return getBlock(skinId).getToolbarBorderWidth();
	}
	
	public String getToolbarFontFamily() {
		return getToolbarFontFamily(getSkinId());
	}

	public String getToolbarFontFamily(String skinId) {
		return getBlock(skinId).getToolbarFontFamily();
	}

	public String getToolbarFontSize() {
		return getToolbarFontSize(getSkinId());
	}

	public String getToolbarFontSize(String skinId) {
		return getBlock(skinId).getToolbarFontSize();
	}
	
	public String getToolbarFontStyle() {
		return getToolbarFontStyle(getSkinId());
	}

	public String getToolbarFontStyle(String skinId) {
		return getBlock(skinId).getToolbarFontStyle();
	}
	
	public String getToolbarFontWeight() {
		return getToolbarFontWeight(getSkinId());
	}

	public String getToolbarFontWeight(String skinId) {
		return getBlock(skinId).getToolbarFontWeight();
	}
	
	public String getToolbarTextColor() {
		return getToolbarTextColor(getSkinId());
	}

	public String getToolbarTextColor(String skinId) {
		return getBlock(skinId).getToolbarTextColor();
	}

	public String getToolbarTextDecoration() {
		return getToolbarTextDecoration(getSkinId());
	}

	public String getToolbarTextDecoration(String skinId) {
		return getBlock(skinId).getToolbarTextDecoration();
	}
	
	
	/*
	 * Tab
	 * ---
	 */

	public String getTabBackgroundColor() {
		return getTabBackgroundColor(getSkinId());
	}

	public String getTabBackgroundColor(String skinId) {
		return getBlock(skinId).getTabBackgroundColor();
	}

	public String getTabBackgroundImage() {
		return getTabBackgroundImage(getSkinId());
	}

	public String getTabBackgroundImage(String skinId) {
		return getBlock(skinId).getTabBackgroundImage();
	}

	public String getTabGradientColor() {
		return getTabGradientColor(getSkinId());
	}

	public String getTabGradientColor(String skinId) {
		return getBlock(skinId).getTabGradientColor();
	}

	public String getTabBorderColor() {
		return getTabBorderColor(getSkinId());
	}

	public String getTabBorderColor(String skinId) {
		return getBlock(skinId).getTabBorderColor();
	}

//	public String getTabBorderWidth() {
//		return getTabBorderWidth(getSkinId());
//	}
//
//	public String getTabBorderWidth(String skinId) {
//		return getBlock(skinId).getTabBorderWidth();
//	}

//	public String getTabBorderStyle() {
//		return getTabBorderStyle(getSkinId());
//	}
//
//	public String getTabBorderStyle(String skinId) {
//		return getBlock(skinId).getTabBorderStyle();
//	}

	public String getTabCornerRadius() {
		return getTabCornerRadius(getSkinId());
	}

	public String getTabCornerRadius(String skinId) {
		return getBlock(skinId).getTabCornerRadius();
	}
	
	public String getTabFontFamily() {
		return getTabFontFamily(getSkinId());
	}

	public String getTabFontFamily(String skinId) {
		return getBlock(skinId).getTabFontFamily();
	}

	public String getTabFontSize() {
		return getTabFontSize(getSkinId());
	}

	public String getTabFontSize(String skinId) {
		return getBlock(skinId).getTabFontSize();
	}
	
	public String getTabFontStyle() {
		return getTabFontStyle(getSkinId());
	}

	public String getTabFontStyle(String skinId) {
		return getBlock(skinId).getTabFontStyle();
	}
	
	public String getTabFontWeight() {
		return getTabFontWeight(getSkinId());
	}

	public String getTabFontWeight(String skinId) {
		return getBlock(skinId).getTabFontWeight();
	}
	
	public String getTabTextColor() {
		return getTabTextColor(getSkinId());
	}

	public String getTabTextColor(String skinId) {
		return getBlock(skinId).getTabTextColor();
	}

	public String getTabTextDecoration() {
		return getTabTextDecoration(getSkinId());
	}

	public String getTabTextDecoration(String skinId) {
		return getBlock(skinId).getTabTextDecoration();
	}
	
	
	/*
	 * Tab Active
	 * ----------
	 */
	
	public String getTabActiveBackgroundColor() {
		return getTabActiveBackgroundColor(getSkinId());
	}

	public String getTabActiveBackgroundColor(String skinId) {
		return getBlock(skinId).getTabActiveBackgroundColor();
	}

	public String getTabActiveBackgroundImage() {
		return getTabActiveBackgroundImage(getSkinId());
	}

	public String getTabActiveBackgroundImage(String skinId) {
		return getBlock(skinId).getTabActiveBackgroundImage();
	}

	public String getTabActiveGradientColor() {
		return getTabActiveGradientColor(getSkinId());
	}

	public String getTabActiveGradientColor(String skinId) {
		return getBlock(skinId).getTabActiveGradientColor();
	}

	public String getTabActiveBorderColor() {
		return getTabActiveBorderColor(getSkinId());
	}

	public String getTabActiveBorderColor(String skinId) {
		return getBlock(skinId).getTabActiveBorderColor();
	}

//	public String getTabActiveBorderWidth() {
//		return getTabActiveBorderWidth(getSkinId());
//	}
//
//	public String getTabActiveBorderWidth(String skinId) {
//		return getBlock(skinId).getTabActiveBorderWidth();
//	}

//	public String getTabActiveBorderStyle() {
//		return getTabActiveBorderStyle(getSkinId());
//	}
//
//	public String getTabActiveBorderStyle(String skinId) {
//		return getBlock(skinId).getTabActiveBorderStyle();
//	}

	public String getTabActiveFontFamily() {
		return getTabActiveFontFamily(getSkinId());
	}

	public String getTabActiveFontFamily(String skinId) {
		return getBlock(skinId).getTabActiveFontFamily();
	}

	public String getTabActiveFontSize() {
		return getTabActiveFontSize(getSkinId());
	}

	public String getTabActiveFontSize(String skinId) {
		return getBlock(skinId).getTabActiveFontSize();
	}
	
	public String getTabActiveFontStyle() {
		return getTabActiveFontStyle(getSkinId());
	}

	public String getTabActiveFontStyle(String skinId) {
		return getBlock(skinId).getTabActiveFontStyle();
	}
	
	public String getTabActiveFontWeight() {
		return getTabActiveFontWeight(getSkinId());
	}

	public String getTabActiveFontWeight(String skinId) {
		return getBlock(skinId).getTabActiveFontWeight();
	}
	
	public String getTabActiveTextColor() {
		return getTabActiveTextColor(getSkinId());
	}

	public String getTabActiveTextColor(String skinId) {
		return getBlock(skinId).getTabActiveTextColor();
	}

	public String getTabActiveTextDecoration() {
		return getTabActiveTextDecoration(getSkinId());
	}

	public String getTabActiveTextDecoration(String skinId) {
		return getBlock(skinId).getTabActiveTextDecoration();
	}
	
	
	/*
	 * Tab Inactive
	 * ------------
	 */
	
	public String getTabInactiveBackgroundColor() {
		return getTabInactiveBackgroundColor(getSkinId());
	}

	public String getTabInactiveBackgroundColor(String skinId) {
		return getBlock(skinId).getTabInactiveBackgroundColor();
	}

	public String getTabInactiveBackgroundImage() {
		return getTabInactiveBackgroundImage(getSkinId());
	}

	public String getTabInactiveBackgroundImage(String skinId) {
		return getBlock(skinId).getTabInactiveBackgroundImage();
	}

	public String getTabInactiveGradientColor() {
		return getTabInactiveGradientColor(getSkinId());
	}

	public String getTabInactiveGradientColor(String skinId) {
		return getBlock(skinId).getTabInactiveGradientColor();
	}

	public String getTabInactiveBorderColor() {
		return getTabInactiveBorderColor(getSkinId());
	}

	public String getTabInactiveBorderColor(String skinId) {
		return getBlock(skinId).getTabInactiveBorderColor();
	}

//	public String getTabInactiveBorderWidth() {
//		return getTabInactiveBorderWidth(getSkinId());
//	}
//
//	public String getTabInactiveBorderWidth(String skinId) {
//		return getBlock(skinId).getTabInactiveBorderWidth();
//	}

//	public String getTabInactiveBorderStyle() {
//		return getTabInactiveBorderStyle(getSkinId());
//	}
//
//	public String getTabInactiveBorderStyle(String skinId) {
//		return getBlock(skinId).getTabInactiveBorderStyle();
//	}

	public String getTabInactiveFontFamily() {
		return getTabInactiveFontFamily(getSkinId());
	}

	public String getTabInactiveFontFamily(String skinId) {
		return getBlock(skinId).getTabInactiveFontFamily();
	}

	public String getTabInactiveFontSize() {
		return getTabInactiveFontSize(getSkinId());
	}

	public String getTabInactiveFontSize(String skinId) {
		return getBlock(skinId).getTabInactiveFontSize();
	}
	
	public String getTabInactiveFontStyle() {
		return getTabInactiveFontStyle(getSkinId());
	}

	public String getTabInactiveFontStyle(String skinId) {
		return getBlock(skinId).getTabInactiveFontStyle();
	}
	
	public String getTabInactiveFontWeight() {
		return getTabInactiveFontWeight(getSkinId());
	}

	public String getTabInactiveFontWeight(String skinId) {
		return getBlock(skinId).getTabInactiveFontWeight();
	}
	
	public String getTabInactiveTextColor() {
		return getTabInactiveTextColor(getSkinId());
	}

	public String getTabInactiveTextColor(String skinId) {
		return getBlock(skinId).getTabInactiveTextColor();
	}

	public String getTabInactiveTextDecoration() {
		return getTabInactiveTextDecoration(getSkinId());
	}

	public String getTabInactiveTextDecoration(String skinId) {
		return getBlock(skinId).getTabInactiveTextDecoration();
	}
	
	
	/*
	 * Tab Disabled
	 * ------------
	 */
	
	public String getTabDisabledBackgroundColor() {
		return getTabDisabledBackgroundColor(getSkinId());
	}

	public String getTabDisabledBackgroundColor(String skinId) {
		return getBlock(skinId).getTabDisabledBackgroundColor();
	}

	public String getTabDisabledBackgroundImage() {
		return getTabDisabledBackgroundImage(getSkinId());
	}

	public String getTabDisabledBackgroundImage(String skinId) {
		return getBlock(skinId).getTabDisabledBackgroundImage();
	}

	public String getTabDisabledGradientColor() {
		return getTabDisabledGradientColor(getSkinId());
	}

	public String getTabDisabledGradientColor(String skinId) {
		return getBlock(skinId).getTabDisabledGradientColor();
	}

	public String getTabDisabledBorderColor() {
		return getTabDisabledBorderColor(getSkinId());
	}

	public String getTabDisabledBorderColor(String skinId) {
		return getBlock(skinId).getTabDisabledBorderColor();
	}

//	public String getTabDisabledBorderWidth() {
//		return getTabDisabledBorderWidth(getSkinId());
//	}
//
//	public String getTabDisabledBorderWidth(String skinId) {
//		return getBlock(skinId).getTabDisabledBorderWidth();
//	}

//	public String getTabDisabledBorderStyle() {
//		return getTabDisabledBorderStyle(getSkinId());
//	}
//
//	public String getTabDisabledBorderStyle(String skinId) {
//		return getBlock(skinId).getTabDisabledBorderStyle();
//	}

	public String getTabDisabledFontFamily() {
		return getTabDisabledFontFamily(getSkinId());
	}

	public String getTabDisabledFontFamily(String skinId) {
		return getBlock(skinId).getTabDisabledFontFamily();
	}

	public String getTabDisabledFontSize() {
		return getTabDisabledFontSize(getSkinId());
	}

	public String getTabDisabledFontSize(String skinId) {
		return getBlock(skinId).getTabDisabledFontSize();
	}
	
	public String getTabDisabledFontStyle() {
		return getTabDisabledFontStyle(getSkinId());
	}

	public String getTabDisabledFontStyle(String skinId) {
		return getBlock(skinId).getTabDisabledFontStyle();
	}
	
	public String getTabDisabledFontWeight() {
		return getTabDisabledFontWeight(getSkinId());
	}

	public String getTabDisabledFontWeight(String skinId) {
		return getBlock(skinId).getTabDisabledFontWeight();
	}
	
	public String getTabDisabledTextColor() {
		return getTabDisabledTextColor(getSkinId());
	}

	public String getTabDisabledTextColor(String skinId) {
		return getBlock(skinId).getTabDisabledTextColor();
	}

	public String getTabDisabledTextDecoration() {
		return getTabDisabledTextDecoration(getSkinId());
	}

	public String getTabDisabledTextDecoration(String skinId) {
		return getBlock(skinId).getTabDisabledTextDecoration();
	}
	
	
	/*
	 * Table
	 * -----
	 */

	public String getTableBackgroundColor() {
		return getTableBackgroundColor(getSkinId());
	}

	public String getTableBackgroundColor(String skinId) {
		return getBlock(skinId).getTableBackgroundColor();
	}

	public String getTableBorderColor() {
		return getTableBorderColor(getSkinId());
	}

	public String getTableBorderColor(String skinId) {
		return getBlock(skinId).getTableBorderColor();
	}

	public String getTableBorderWidth() {
		return getTableBorderWidth(getSkinId());
	}

	public String getTableBorderWidth(String skinId) {
		return getBlock(skinId).getTableBorderWidth();
	}
	
	
	/*
	 * Table Header
	 * ------------
	 */
	
	public String getTableHeaderBackgroundColor() {
		return getTableHeaderBackgroundColor(getSkinId());
	}

	public String getTableHeaderBackgroundColor(String skinId) {
		return getBlock(skinId).getTableHeaderBackgroundColor();
	}

	public String getTableHeaderBackgroundImage() {
		return getTableHeaderBackgroundImage(getSkinId());
	}

	public String getTableHeaderBackgroundImage(String skinId) {
		return getBlock(skinId).getTableHeaderBackgroundImage();
	}

	public String getTableHeaderGradientColor() {
		return getTableHeaderGradientColor(getSkinId());
	}

	public String getTableHeaderGradientColor(String skinId) {
		return getBlock(skinId).getTableHeaderGradientColor();
	}

	public String getTableHeaderBorderColor() {
		return getTableHeaderBorderColor(getSkinId());
	}

	public String getTableHeaderBorderColor(String skinId) {
		return getBlock(skinId).getTableHeaderBorderColor();
	}

//	public String getTableHeaderBorderWidth() {
//		return getTableHeaderBorderWidth(getSkinId());
//	}
//
//	public String getTableHeaderBorderWidth(String skinId) {
//		return getBlock(skinId).getTableHeaderBorderWidth();
//	}

//	public String getTableHeaderBorderStyle() {
//		return getTableHeaderBorderStyle(getSkinId());
//	}
//
//	public String getTableHeaderBorderStyle(String skinId) {
//		return getBlock(skinId).getTableHeaderBorderStyle();
//	}

	public String getTableHeaderFontFamily() {
		return getTableHeaderFontFamily(getSkinId());
	}

	public String getTableHeaderFontFamily(String skinId) {
		return getBlock(skinId).getTableHeaderFontFamily();
	}

	public String getTableHeaderFontSize() {
		return getTableHeaderFontSize(getSkinId());
	}

	public String getTableHeaderFontSize(String skinId) {
		return getBlock(skinId).getTableHeaderFontSize();
	}
	
	public String getTableHeaderFontStyle() {
		return getTableHeaderFontStyle(getSkinId());
	}

	public String getTableHeaderFontStyle(String skinId) {
		return getBlock(skinId).getTableHeaderFontStyle();
	}
	
	public String getTableHeaderFontWeight() {
		return getTableHeaderFontWeight(getSkinId());
	}

	public String getTableHeaderFontWeight(String skinId) {
		return getBlock(skinId).getTableHeaderFontWeight();
	}
	
	public String getTableHeaderTextColor() {
		return getTableHeaderTextColor(getSkinId());
	}

	public String getTableHeaderTextColor(String skinId) {
		return getBlock(skinId).getTableHeaderTextColor();
	}

	public String getTableHeaderTextDecoration() {
		return getTableHeaderTextDecoration(getSkinId());
	}

	public String getTableHeaderTextDecoration(String skinId) {
		return getBlock(skinId).getTableHeaderTextDecoration();
	}
	
	public String getTableSubHeaderBackgroundColor() {
		return getTableSubHeaderBackgroundColor(getSkinId());
	}

	public String getTableSubHeaderBackgroundColor(String skinId) {
		return getBlock(skinId).getTableSubHeaderBackgroundColor();
	}

	
	/*
	 * Table Footer
	 * ------------
	 */
	
	public String getTableFooterBackgroundColor() {
		return getTableFooterBackgroundColor(getSkinId());
	}

	public String getTableFooterBackgroundColor(String skinId) {
		return getBlock(skinId).getTableFooterBackgroundColor();
	}

	public String getTableFooterBackgroundImage() {
		return getTableFooterBackgroundImage(getSkinId());
	}

	public String getTableFooterBackgroundImage(String skinId) {
		return getBlock(skinId).getTableFooterBackgroundImage();
	}

	public String getTableFooterGradientColor() {
		return getTableFooterGradientColor(getSkinId());
	}

	public String getTableFooterGradientColor(String skinId) {
		return getBlock(skinId).getTableFooterGradientColor();
	}

	public String getTableFooterBorderColor() {
		return getTableFooterBorderColor(getSkinId());
	}

	public String getTableFooterBorderColor(String skinId) {
		return getBlock(skinId).getTableFooterBorderColor();
	}

//	public String getTableFooterBorderWidth() {
//		return getTableFooterBorderWidth(getSkinId());
//	}
//
//	public String getTableFooterBorderWidth(String skinId) {
//		return getBlock(skinId).getTableFooterBorderWidth();
//	}

//	public String getTableFooterBorderStyle() {
//		return getTableFooterBorderStyle(getSkinId());
//	}
//
//	public String getTableFooterBorderStyle(String skinId) {
//		return getBlock(skinId).getTableFooterBorderStyle();
//	}

	public String getTableFooterFontFamily() {
		return getTableFooterFontFamily(getSkinId());
	}

	public String getTableFooterFontFamily(String skinId) {
		return getBlock(skinId).getTableFooterFontFamily();
	}

	public String getTableFooterFontSize() {
		return getTableFooterFontSize(getSkinId());
	}

	public String getTableFooterFontSize(String skinId) {
		return getBlock(skinId).getTableFooterFontSize();
	}
	
	public String getTableFooterFontStyle() {
		return getTableFooterFontStyle(getSkinId());
	}

	public String getTableFooterFontStyle(String skinId) {
		return getBlock(skinId).getTableFooterFontStyle();
	}
	
	public String getTableFooterFontWeight() {
		return getTableFooterFontWeight(getSkinId());
	}

	public String getTableFooterFontWeight(String skinId) {
		return getBlock(skinId).getTableFooterFontWeight();
	}
	
	public String getTableFooterTextColor() {
		return getTableFooterTextColor(getSkinId());
	}

	public String getTableFooterTextColor(String skinId) {
		return getBlock(skinId).getTableFooterTextColor();
	}

	public String getTableFooterTextDecoration() {
		return getTableFooterTextDecoration(getSkinId());
	}

	public String getTableFooterTextDecoration(String skinId) {
		return getBlock(skinId).getTableFooterTextDecoration();
	}
	
	public String getTableSubFooterBackgroundColor() {
		return getTableSubFooterBackgroundColor(getSkinId());
	}

	public String getTableSubFooterBackgroundColor(String skinId) {
		return getBlock(skinId).getTableSubFooterBackgroundColor();
	}


	/*
	 * Table Cell
	 * ----------
	 */
	
	public String getTableCellBackgroundColor() {
		return getTableCellBackgroundColor(getSkinId());
	}

	public String getTableCellBackgroundColor(String skinId) {
		return getBlock(skinId).getTableCellBackgroundColor();
	}

	public String getTableCellBackgroundImage() {
		return getTableCellBackgroundImage(getSkinId());
	}

	public String getTableCellBackgroundImage(String skinId) {
		return getBlock(skinId).getTableCellBackgroundImage();
	}

	public String getTableCellGradientColor() {
		return getTableCellGradientColor(getSkinId());
	}

	public String getTableCellGradientColor(String skinId) {
		return getBlock(skinId).getTableCellGradientColor();
	}

	public String getTableCellBorderColor() {
		return getTableCellBorderColor(getSkinId());
	}

	public String getTableCellBorderColor(String skinId) {
		return getBlock(skinId).getTableCellBorderColor();
	}

//	public String getTableCellBorderWidth() {
//		return getTableCellBorderWidth(getSkinId());
//	}
//
//	public String getTableCellBorderWidth(String skinId) {
//		return getBlock(skinId).getTableCellBorderWidth();
//	}

//	public String getTableCellBorderStyle() {
//		return getTableCellBorderStyle(getSkinId());
//	}
//
//	public String getTableCellBorderStyle(String skinId) {
//		return getBlock(skinId).getTableCellBorderStyle();
//	}

	public String getTableCellFontFamily() {
		return getTableCellFontFamily(getSkinId());
	}

	public String getTableCellFontFamily(String skinId) {
		return getBlock(skinId).getTableCellFontFamily();
	}

	public String getTableCellFontSize() {
		return getTableCellFontSize(getSkinId());
	}

	public String getTableCellFontSize(String skinId) {
		return getBlock(skinId).getTableCellFontSize();
	}
	
	public String getTableCellFontStyle() {
		return getTableCellFontStyle(getSkinId());
	}

	public String getTableCellFontStyle(String skinId) {
		return getBlock(skinId).getTableCellFontStyle();
	}
	
	public String getTableCellFontWeight() {
		return getTableCellFontWeight(getSkinId());
	}

	public String getTableCellFontWeight(String skinId) {
		return getBlock(skinId).getTableCellFontWeight();
	}
	
	public String getTableCellTextColor() {
		return getTableCellTextColor(getSkinId());
	}

	public String getTableCellTextColor(String skinId) {
		return getBlock(skinId).getTableCellTextColor();
	}

	public String getTableCellTextDecoration() {
		return getTableCellTextDecoration(getSkinId());
	}

	public String getTableCellTextDecoration(String skinId) {
		return getBlock(skinId).getTableCellTextDecoration();
	}
	
	
	
	/*
	 * Tooltip
	 * -------
	 */

	public String getTooltipBackgroundColor() {
		return getTooltipBackgroundColor(getSkinId());
	}

	public String getTooltipBackgroundColor(String skinId) {
		return getBlock(skinId).getTooltipBackgroundColor();
	}

	public String getTooltipBackgroundImage() {
		return getTooltipBackgroundImage(getSkinId());
	}

	public String getTooltipBackgroundImage(String skinId) {
		return getBlock(skinId).getTooltipBackgroundImage();
	}

	public String getTooltipGradientColor() {
		return getTooltipGradientColor(getSkinId());
	}

	public String getTooltipGradientColor(String skinId) {
		return getBlock(skinId).getTooltipGradientColor();
	}

	public String getTooltipBorderColor() {
		return getTooltipBorderColor(getSkinId());
	}

	public String getTooltipBorderColor(String skinId) {
		return getBlock(skinId).getTooltipBorderColor();
	}

	public String getTooltipBorderWidth() {
		return getTooltipBorderWidth(getSkinId());
	}

	public String getTooltipBorderWidth(String skinId) {
		return getBlock(skinId).getTooltipBorderWidth();
	}

	public String getTooltipBorderStyle() {
		return getTooltipBorderStyle(getSkinId());
	}

	public String getTooltipBorderStyle(String skinId) {
		return getBlock(skinId).getTooltipBorderStyle();
	}

	public String getTooltipFontFamily() {
		return getTooltipFontFamily(getSkinId());
	}

	public String getTooltipFontFamily(String skinId) {
		return getBlock(skinId).getTooltipFontFamily();
	}

	public String getTooltipFontSize() {
		return getTooltipFontSize(getSkinId());
	}

	public String getTooltipFontSize(String skinId) {
		return getBlock(skinId).getTooltipFontSize();
	}
	
	public String getTooltipFontStyle() {
		return getTooltipFontStyle(getSkinId());
	}

	public String getTooltipFontStyle(String skinId) {
		return getBlock(skinId).getTooltipFontStyle();
	}
	
	public String getTooltipFontWeight() {
		return getTooltipFontWeight(getSkinId());
	}

	public String getTooltipFontWeight(String skinId) {
		return getBlock(skinId).getTooltipFontWeight();
	}
	
	public String getTooltipTextColor() {
		return getTooltipTextColor(getSkinId());
	}

	public String getTooltipTextColor(String skinId) {
		return getBlock(skinId).getTooltipTextColor();
	}

	public String getTooltipTextDecoration() {
		return getTooltipTextDecoration(getSkinId());
	}

	public String getTooltipTextDecoration(String skinId) {
		return getBlock(skinId).getTooltipTextDecoration();
	}

	
	/*
	 * Highlight
	 * ---------
	 */
	
	public String getHighlightBackgroundColor() {
		return getHighlightBackgroundColor(getSkinId());
	}

	public String getHighlightBackgroundColor(String skinId) {
		return getBlock(skinId).getHighlightBackgroundColor();
	}

	public String getHighlightBackgroundImage() {
		return getHighlightBackgroundImage(getSkinId());
	}

	public String getHighlightBackgroundImage(String skinId) {
		return getBlock(skinId).getHighlightBackgroundImage();
	}

	public String getHighlightBorderColor() {
		return getHighlightBorderColor(getSkinId());
	}

	public String getHighlightBorderColor(String skinId) {
		return getBlock(skinId).getHighlightBorderColor();
	}

	public String getHighlightBorderWidth() {
		return getHighlightBorderWidth(getSkinId());
	}

	public String getHighlightBorderWidth(String skinId) {
		return getBlock(skinId).getHighlightBorderWidth();
	}
	
	public String getHighlightFontFamily() {
		return getHighlightFontFamily(getSkinId());
	}

	public String getHighlightFontFamily(String skinId) {
		return getBlock(skinId).getHighlightFontFamily();
	}

	public String getHighlightFontSize() {
		return getHighlightFontSize(getSkinId());
	}

	public String getHighlightFontSize(String skinId) {
		return getBlock(skinId).getHighlightFontSize();
	}
	
	public String getHighlightFontStyle() {
		return getHighlightFontStyle(getSkinId());
	}

	public String getHighlightFontStyle(String skinId) {
		return getBlock(skinId).getHighlightFontStyle();
	}
	
	public String getHighlightFontWeight() {
		return getHighlightFontWeight(getSkinId());
	}

	public String getHighlightFontWeight(String skinId) {
		return getBlock(skinId).getHighlightFontWeight();
	}
	
	public String getHighlightTextColor() {
		return getHighlightTextColor(getSkinId());
	}

	public String getHighlightTextColor(String skinId) {
		return getBlock(skinId).getHighlightTextColor();
	}

	public String getHighlightTextDecoration() {
		return getHighlightTextDecoration(getSkinId());
	}

	public String getHighlightTextDecoration(String skinId) {
		return getBlock(skinId).getHighlightTextDecoration();
	}
	
	
	/*
	 * Focus
	 * -----
	 */
	
	public String getFocusBackgroundColor() {
		return getFocusBackgroundColor(getSkinId());
	}

	public String getFocusBackgroundColor(String skinId) {
		return getBlock(skinId).getFocusBackgroundColor();
	}

	public String getFocusBackgroundImage() {
		return getFocusBackgroundImage(getSkinId());
	}

	public String getFocusBackgroundImage(String skinId) {
		return getBlock(skinId).getFocusBackgroundImage();
	}

	public String getFocusBorderColor() {
		return getFocusBorderColor(getSkinId());
	}

	public String getFocusBorderColor(String skinId) {
		return getBlock(skinId).getFocusBorderColor();
	}

	public String getFocusBorderWidth() {
		return getFocusBorderWidth(getSkinId());
	}

	public String getFocusBorderWidth(String skinId) {
		return getBlock(skinId).getFocusBorderWidth();
	}
	
	public String getFocusFontFamily() {
		return getFocusFontFamily(getSkinId());
	}

	public String getFocusFontFamily(String skinId) {
		return getBlock(skinId).getFocusFontFamily();
	}

	public String getFocusFontSize() {
		return getFocusFontSize(getSkinId());
	}

	public String getFocusFontSize(String skinId) {
		return getBlock(skinId).getFocusFontSize();
	}
	
	public String getFocusFontStyle() {
		return getFocusFontStyle(getSkinId());
	}

	public String getFocusFontStyle(String skinId) {
		return getBlock(skinId).getFocusFontStyle();
	}
	
	public String getFocusFontWeight() {
		return getFocusFontWeight(getSkinId());
	}

	public String getFocusFontWeight(String skinId) {
		return getBlock(skinId).getFocusFontWeight();
	}
	
	public String getFocusTextColor() {
		return getFocusTextColor(getSkinId());
	}

	public String getFocusTextColor(String skinId) {
		return getBlock(skinId).getFocusTextColor();
	}

	public String getFocusTextDecoration() {
		return getFocusTextDecoration(getSkinId());
	}

	public String getFocusTextDecoration(String skinId) {
		return getBlock(skinId).getFocusTextDecoration();
	}
	
	
	/*
	 * Disabled
	 * --------
	 */
	
	public String getDisabledBackgroundColor() {
		return getDisabledBackgroundColor(getSkinId());
	}

	public String getDisabledBackgroundColor(String skinId) {
		return getBlock(skinId).getDisabledBackgroundColor();
	}

	public String getDisabledBackgroundImage() {
		return getDisabledBackgroundImage(getSkinId());
	}

	public String getDisabledBackgroundImage(String skinId) {
		return getBlock(skinId).getDisabledBackgroundImage();
	}

	public String getDisabledBorderColor() {
		return getDisabledBorderColor(getSkinId());
	}

	public String getDisabledBorderColor(String skinId) {
		return getBlock(skinId).getDisabledBorderColor();
	}

	public String getDisabledBorderWidth() {
		return getDisabledBorderWidth(getSkinId());
	}

	public String getDisabledBorderWidth(String skinId) {
		return getBlock(skinId).getDisabledBorderWidth();
	}
	
	public String getDisabledFontFamily() {
		return getDisabledFontFamily(getSkinId());
	}

	public String getDisabledFontFamily(String skinId) {
		return getBlock(skinId).getDisabledFontFamily();
	}

	public String getDisabledFontSize() {
		return getDisabledFontSize(getSkinId());
	}

	public String getDisabledFontSize(String skinId) {
		return getBlock(skinId).getDisabledFontSize();
	}
	
	public String getDisabledFontStyle() {
		return getDisabledFontStyle(getSkinId());
	}

	public String getDisabledFontStyle(String skinId) {
		return getBlock(skinId).getDisabledFontStyle();
	}
	
	public String getDisabledFontWeight() {
		return getDisabledFontWeight(getSkinId());
	}

	public String getDisabledFontWeight(String skinId) {
		return getBlock(skinId).getDisabledFontWeight();
	}
	
	public String getDisabledTextColor() {
		return getDisabledTextColor(getSkinId());
	}

	public String getDisabledTextColor(String skinId) {
		return getBlock(skinId).getDisabledTextColor();
	}

	public String getDisabledTextDecoration() {
		return getDisabledTextDecoration(getSkinId());
	}

	public String getDisabledTextDecoration(String skinId) {
		return getBlock(skinId).getDisabledTextDecoration();
	}
	
	
	/*
	 * Theme Enhanced
	 * --------------
	 */
	
	public String getThemeBackgroundColor() {
		return getThemeBackgroundColor(getSkinId());
	}

	public String getThemeBackgroundColor(String skinId) {
		return getBlock(skinId).getThemeBackgroundColor();
	}

	public String getThemeBorderColor() {
		return getThemeBorderColor(getSkinId());
	}

	public String getThemeBorderColor(String skinId) {
		return getBlock(skinId).getThemeBorderColor();
	}
	

}

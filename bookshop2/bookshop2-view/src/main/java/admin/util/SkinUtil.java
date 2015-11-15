package admin.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aries.util.Validator;
import org.aries.util.ObjectUtil;

import admin.Skin;


public class SkinUtil {
	
	public static boolean isEmpty(Skin skin) {
		if (skin == null)
			return true;
		boolean status = false;
		status |= StringUtils.isEmpty(skin.getName());
		return status;
	}
	
	public static boolean isEmpty(Collection<Skin> skinList) {
		if (skinList == null  || skinList.size() == 0)
			return true;
		Iterator<Skin> iterator = skinList.iterator();
		while (iterator.hasNext()) {
			Skin skin = iterator.next();
			if (!isEmpty(skin))
				return false;
		}
		return true;
	}
	
	public static String toString(Skin skin) {
		if (isEmpty(skin))
			return "";
		String text = "";
		text += skin.getName();
		return text;
	}
	
	public static String toString(Collection<Skin> skinList) {
		if (isEmpty(skinList))
			return "";
		StringBuffer buf = new StringBuffer();
		Iterator<Skin> iterator = skinList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Skin skin = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(skin);
			buf.append(text);
		}
		return buf.toString();
	}
	
	public static Skin create() {
		Skin skin = new Skin();
		initialize(skin);
		return skin;
	}
	
	public static void initialize(Skin skin) {
		//nothing for now
	}
	
	public static boolean validate(Skin skin) {
		if (skin == null)
			return false;
		Validator validator = Validator.getValidator();
		validator.notEmpty(skin.getName(), "\"Name\" must be specified");
		
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static boolean validate(Collection<Skin> skinList) {
		Validator validator = Validator.getValidator();
		Iterator<Skin> iterator = skinList.iterator();
		while (iterator.hasNext()) {
			Skin skin = iterator.next();
			//TODO break or accumulate?
			validate(skin);
		}
		boolean isValid = validator.isValid();
		return isValid;
	}
	
	public static void sortRecords(List<Skin> skinList) {
		Collections.sort(skinList, new Comparator<Skin>() {
			public int compare(Skin skin1, Skin skin2) {
				String text1 = SkinUtil.toString(skin1);
				String text2 = SkinUtil.toString(skin2);
				return text1.compareTo(text2);
			}
		});
	}
	
	public static void sortRecordsByName(List<Skin> skinList) {
		Collections.sort(skinList, new Comparator<Skin>() {
			public int compare(Skin skin1, Skin skin2) {
				String text1 = skin1.getName();
				String text2 = skin2.getName();
				return text1.compareTo(text2);
			}
		});
	}
	
	public static Skin clone(Skin skin) {
		if (skin == null)
			return null;
		Skin clone = create();
		clone.setId(ObjectUtil.clone(skin.getId()));
		clone.setName(ObjectUtil.clone(skin.getName()));
		clone.setWidth(ObjectUtil.clone(skin.getWidth()));
		clone.setHeight(ObjectUtil.clone(skin.getHeight()));
		clone.setMargin(ObjectUtil.clone(skin.getMargin()));
		clone.setMarginTop(ObjectUtil.clone(skin.getMarginTop()));
		clone.setMarginLeft(ObjectUtil.clone(skin.getMarginLeft()));
		clone.setMarginRight(ObjectUtil.clone(skin.getMarginRight()));
		clone.setMarginBottom(ObjectUtil.clone(skin.getMarginBottom()));
		clone.setPadding(ObjectUtil.clone(skin.getPadding()));
		clone.setPaddingTop(ObjectUtil.clone(skin.getPaddingTop()));
		clone.setPaddingLeft(ObjectUtil.clone(skin.getPaddingLeft()));
		clone.setPaddingRight(ObjectUtil.clone(skin.getPaddingRight()));
		clone.setPaddingBottom(ObjectUtil.clone(skin.getPaddingBottom()));
		clone.setBorderStyle(ObjectUtil.clone(skin.getBorderStyle()));
		clone.setBorderWidth(ObjectUtil.clone(skin.getBorderWidth()));
		clone.setBorderColor(ObjectUtil.clone(skin.getBorderColor()));
		clone.setCornerRadius(ObjectUtil.clone(skin.getCornerRadius()));
		clone.setBackgroundColor(ObjectUtil.clone(skin.getBackgroundColor()));
		clone.setBackgroundImage(ObjectUtil.clone(skin.getBackgroundImage()));
		clone.setGradientColor(ObjectUtil.clone(skin.getGradientColor()));
		clone.setGradientImage(ObjectUtil.clone(skin.getGradientImage()));
		clone.setFontSize(ObjectUtil.clone(skin.getFontSize()));
		clone.setFontFamily(ObjectUtil.clone(skin.getFontFamily()));
		clone.setFontWeight(ObjectUtil.clone(skin.getFontWeight()));
		clone.setFontStyle(ObjectUtil.clone(skin.getFontStyle()));
		clone.setFontVariant(ObjectUtil.clone(skin.getFontVariant()));
		clone.setTextColor(ObjectUtil.clone(skin.getTextColor()));
		clone.setTextDecoration(ObjectUtil.clone(skin.getTextDecoration()));
		clone.setLinkColor(ObjectUtil.clone(skin.getLinkColor()));
		clone.setLinkHoverColor(ObjectUtil.clone(skin.getLinkHoverColor()));
		clone.setLinkVisitedColor(ObjectUtil.clone(skin.getLinkVisitedColor()));
		clone.setButtonFontFamily(ObjectUtil.clone(skin.getButtonFontFamily()));
		clone.setButtonFontSize(ObjectUtil.clone(skin.getButtonFontSize()));
		clone.setButtonFontStyle(ObjectUtil.clone(skin.getButtonFontStyle()));
		clone.setButtonFontVariant(ObjectUtil.clone(skin.getButtonFontVariant()));
		clone.setButtonFontWeight(ObjectUtil.clone(skin.getButtonFontWeight()));
		clone.setButtonTextColor(ObjectUtil.clone(skin.getButtonTextColor()));
		clone.setButtonTextDecoration(ObjectUtil.clone(skin.getButtonTextDecoration()));
		clone.setButtonBackgroundColor(ObjectUtil.clone(skin.getButtonBackgroundColor()));
		clone.setButtonBackgroundImage(ObjectUtil.clone(skin.getButtonBackgroundImage()));
		clone.setButtonGradientColor(ObjectUtil.clone(skin.getButtonGradientColor()));
		clone.setButtonBorderStyle(ObjectUtil.clone(skin.getButtonBorderStyle()));
		clone.setButtonBorderWidth(ObjectUtil.clone(skin.getButtonBorderWidth()));
		clone.setButtonBorderColor(ObjectUtil.clone(skin.getButtonBorderColor()));
		clone.setButtonControlColor(ObjectUtil.clone(skin.getButtonControlColor()));
		clone.setButtonCornerRadius(ObjectUtil.clone(skin.getButtonCornerRadius()));
		clone.setHeaderBackgroundColor(ObjectUtil.clone(skin.getHeaderBackgroundColor()));
		clone.setHeaderBackgroundImage(ObjectUtil.clone(skin.getHeaderBackgroundImage()));
		clone.setHeaderGradientColor(ObjectUtil.clone(skin.getHeaderGradientColor()));
		clone.setHeaderBorderStyle(ObjectUtil.clone(skin.getHeaderBorderStyle()));
		clone.setHeaderBorderWidth(ObjectUtil.clone(skin.getHeaderBorderWidth()));
		clone.setHeaderBorderColor(ObjectUtil.clone(skin.getHeaderBorderColor()));
		clone.setHeaderFontFamily(ObjectUtil.clone(skin.getHeaderFontFamily()));
		clone.setHeaderFontSize(ObjectUtil.clone(skin.getHeaderFontSize()));
		clone.setHeaderFontStyle(ObjectUtil.clone(skin.getHeaderFontStyle()));
		clone.setHeaderFontVariant(ObjectUtil.clone(skin.getHeaderFontVariant()));
		clone.setHeaderFontWeight(ObjectUtil.clone(skin.getHeaderFontWeight()));
		clone.setHeaderTextColor(ObjectUtil.clone(skin.getHeaderTextColor()));
		clone.setHeaderTextDecoration(ObjectUtil.clone(skin.getHeaderTextDecoration()));
		clone.setToolbarBackgroundColor(ObjectUtil.clone(skin.getToolbarBackgroundColor()));
		clone.setToolbarBackgroundImage(ObjectUtil.clone(skin.getToolbarBackgroundImage()));
		clone.setToolbarGradientColor(ObjectUtil.clone(skin.getToolbarGradientColor()));
		clone.setToolbarBorderColor(ObjectUtil.clone(skin.getToolbarBorderColor()));
		clone.setToolbarBorderStyle(ObjectUtil.clone(skin.getToolbarBorderStyle()));
		clone.setToolbarBorderWidth(ObjectUtil.clone(skin.getToolbarBorderWidth()));
		clone.setToolbarFontFamily(ObjectUtil.clone(skin.getToolbarFontFamily()));
		clone.setToolbarFontSize(ObjectUtil.clone(skin.getToolbarFontSize()));
		clone.setToolbarFontStyle(ObjectUtil.clone(skin.getToolbarFontStyle()));
		clone.setToolbarFontVariant(ObjectUtil.clone(skin.getToolbarFontVariant()));
		clone.setToolbarFontWeight(ObjectUtil.clone(skin.getToolbarFontWeight()));
		clone.setToolbarTextColor(ObjectUtil.clone(skin.getToolbarTextColor()));
		clone.setToolbarTextDecoration(ObjectUtil.clone(skin.getToolbarTextDecoration()));
		clone.setTabBackgroundColor(ObjectUtil.clone(skin.getTabBackgroundColor()));
		clone.setTabBackgroundImage(ObjectUtil.clone(skin.getTabBackgroundImage()));
		clone.setTabGradientColor(ObjectUtil.clone(skin.getTabGradientColor()));
		clone.setTabBorderColor(ObjectUtil.clone(skin.getTabBorderColor()));
		clone.setTabCornerRadius(ObjectUtil.clone(skin.getTabCornerRadius()));
		clone.setTabFontFamily(ObjectUtil.clone(skin.getTabFontFamily()));
		clone.setTabFontSize(ObjectUtil.clone(skin.getTabFontSize()));
		clone.setTabFontStyle(ObjectUtil.clone(skin.getTabFontStyle()));
		clone.setTabFontWeight(ObjectUtil.clone(skin.getTabFontWeight()));
		clone.setTabTextColor(ObjectUtil.clone(skin.getTabTextColor()));
		clone.setTabTextDecoration(ObjectUtil.clone(skin.getTabTextDecoration()));
		clone.setTabActiveBackgroundColor(ObjectUtil.clone(skin.getTabActiveBackgroundColor()));
		clone.setTabActiveBackgroundImage(ObjectUtil.clone(skin.getTabActiveBackgroundImage()));
		clone.setTabActiveGradientColor(ObjectUtil.clone(skin.getTabActiveGradientColor()));
		clone.setTabActiveBorderColor(ObjectUtil.clone(skin.getTabActiveBorderColor()));
		clone.setTabActiveFontFamily(ObjectUtil.clone(skin.getTabActiveFontFamily()));
		clone.setTabActiveFontSize(ObjectUtil.clone(skin.getTabActiveFontSize()));
		clone.setTabActiveFontStyle(ObjectUtil.clone(skin.getTabActiveFontStyle()));
		clone.setTabActiveFontWeight(ObjectUtil.clone(skin.getTabActiveFontWeight()));
		clone.setTabActiveTextColor(ObjectUtil.clone(skin.getTabActiveTextColor()));
		clone.setTabActiveTextDecoration(ObjectUtil.clone(skin.getTabActiveTextDecoration()));
		clone.setTabInactiveBackgroundColor(ObjectUtil.clone(skin.getTabInactiveBackgroundColor()));
		clone.setTabInactiveBackgroundImage(ObjectUtil.clone(skin.getTabInactiveBackgroundImage()));
		clone.setTabInactiveGradientColor(ObjectUtil.clone(skin.getTabInactiveGradientColor()));
		clone.setTabInactiveBorderColor(ObjectUtil.clone(skin.getTabInactiveBorderColor()));
		clone.setTabInactiveFontFamily(ObjectUtil.clone(skin.getTabInactiveFontFamily()));
		clone.setTabInactiveFontSize(ObjectUtil.clone(skin.getTabInactiveFontSize()));
		clone.setTabInactiveFontStyle(ObjectUtil.clone(skin.getTabInactiveFontStyle()));
		clone.setTabInactiveFontWeight(ObjectUtil.clone(skin.getTabInactiveFontWeight()));
		clone.setTabInactiveTextColor(ObjectUtil.clone(skin.getTabInactiveTextColor()));
		clone.setTabInactiveTextDecoration(ObjectUtil.clone(skin.getTabInactiveTextDecoration()));
		clone.setTabDisabledBackgroundColor(ObjectUtil.clone(skin.getTabDisabledBackgroundColor()));
		clone.setTabDisabledBackgroundImage(ObjectUtil.clone(skin.getTabDisabledBackgroundImage()));
		clone.setTabDisabledGradientColor(ObjectUtil.clone(skin.getTabDisabledGradientColor()));
		clone.setTabDisabledBorderColor(ObjectUtil.clone(skin.getTabDisabledBorderColor()));
		clone.setTabDisabledFontFamily(ObjectUtil.clone(skin.getTabDisabledFontFamily()));
		clone.setTabDisabledFontSize(ObjectUtil.clone(skin.getTabDisabledFontSize()));
		clone.setTabDisabledFontStyle(ObjectUtil.clone(skin.getTabDisabledFontStyle()));
		clone.setTabDisabledFontWeight(ObjectUtil.clone(skin.getTabDisabledFontWeight()));
		clone.setTabDisabledTextColor(ObjectUtil.clone(skin.getTabDisabledTextColor()));
		clone.setTabDisabledTextDecoration(ObjectUtil.clone(skin.getTabDisabledTextDecoration()));
		clone.setTableBackgroundColor(ObjectUtil.clone(skin.getTableBackgroundColor()));
		clone.setTableBorderColor(ObjectUtil.clone(skin.getTableBorderColor()));
		clone.setTableBorderWidth(ObjectUtil.clone(skin.getTableBorderWidth()));
		clone.setTableCellBackgroundColor(ObjectUtil.clone(skin.getTableCellBackgroundColor()));
		clone.setTableCellBackgroundImage(ObjectUtil.clone(skin.getTableCellBackgroundImage()));
		clone.setTableCellGradientColor(ObjectUtil.clone(skin.getTableCellGradientColor()));
		clone.setTableCellBorderColor(ObjectUtil.clone(skin.getTableCellBorderColor()));
		clone.setTableCellFontFamily(ObjectUtil.clone(skin.getTableCellFontFamily()));
		clone.setTableCellFontSize(ObjectUtil.clone(skin.getTableCellFontSize()));
		clone.setTableCellFontStyle(ObjectUtil.clone(skin.getTableCellFontStyle()));
		clone.setTableCellFontVariant(ObjectUtil.clone(skin.getTableCellFontVariant()));
		clone.setTableCellFontWeight(ObjectUtil.clone(skin.getTableCellFontWeight()));
		clone.setTableCellTextColor(ObjectUtil.clone(skin.getTableCellTextColor()));
		clone.setTableCellTextDecoration(ObjectUtil.clone(skin.getTableCellTextDecoration()));
		clone.setTableHeaderBackgroundColor(ObjectUtil.clone(skin.getTableHeaderBackgroundColor()));
		clone.setTableHeaderBackgroundImage(ObjectUtil.clone(skin.getTableHeaderBackgroundImage()));
		clone.setTableHeaderGradientColor(ObjectUtil.clone(skin.getTableHeaderGradientColor()));
		clone.setTableHeaderBorderColor(ObjectUtil.clone(skin.getTableHeaderBorderColor()));
		clone.setTableHeaderFontFamily(ObjectUtil.clone(skin.getTableHeaderFontFamily()));
		clone.setTableHeaderFontSize(ObjectUtil.clone(skin.getTableHeaderFontSize()));
		clone.setTableHeaderFontStyle(ObjectUtil.clone(skin.getTableHeaderFontStyle()));
		clone.setTableHeaderFontVariant(ObjectUtil.clone(skin.getTableHeaderFontVariant()));
		clone.setTableHeaderFontWeight(ObjectUtil.clone(skin.getTableHeaderFontWeight()));
		clone.setTableHeaderTextColor(ObjectUtil.clone(skin.getTableHeaderTextColor()));
		clone.setTableHeaderTextDecoration(ObjectUtil.clone(skin.getTableHeaderTextDecoration()));
		clone.setTableFooterBackgroundColor(ObjectUtil.clone(skin.getTableFooterBackgroundColor()));
		clone.setTableFooterBackgroundImage(ObjectUtil.clone(skin.getTableFooterBackgroundImage()));
		clone.setTableFooterGradientColor(ObjectUtil.clone(skin.getTableFooterGradientColor()));
		clone.setTableFooterBorderColor(ObjectUtil.clone(skin.getTableFooterBorderColor()));
		clone.setTableFooterFontFamily(ObjectUtil.clone(skin.getTableFooterFontFamily()));
		clone.setTableFooterFontSize(ObjectUtil.clone(skin.getTableFooterFontSize()));
		clone.setTableFooterFontStyle(ObjectUtil.clone(skin.getTableFooterFontStyle()));
		clone.setTableFooterFontVariant(ObjectUtil.clone(skin.getTableFooterFontVariant()));
		clone.setTableFooterFontWeight(ObjectUtil.clone(skin.getTableFooterFontWeight()));
		clone.setTableFooterTextColor(ObjectUtil.clone(skin.getTableFooterTextColor()));
		clone.setTableFooterTextDecoration(ObjectUtil.clone(skin.getTableFooterTextDecoration()));
		clone.setTableSubHeaderBackgroundColor(ObjectUtil.clone(skin.getTableSubHeaderBackgroundColor()));
		clone.setTableSubFooterBackgroundColor(ObjectUtil.clone(skin.getTableSubFooterBackgroundColor()));
		clone.setTooltipBackgroundColor(ObjectUtil.clone(skin.getTooltipBackgroundColor()));
		clone.setTooltipBackgroundImage(ObjectUtil.clone(skin.getTooltipBackgroundImage()));
		clone.setTooltipGradientColor(ObjectUtil.clone(skin.getTooltipGradientColor()));
		clone.setTooltipBorderColor(ObjectUtil.clone(skin.getTooltipBorderColor()));
		clone.setTooltipBorderStyle(ObjectUtil.clone(skin.getTooltipBorderStyle()));
		clone.setTooltipBorderWidth(ObjectUtil.clone(skin.getTooltipBorderWidth()));
		clone.setTooltipFontFamily(ObjectUtil.clone(skin.getTooltipFontFamily()));
		clone.setTooltipFontSize(ObjectUtil.clone(skin.getTooltipFontSize()));
		clone.setTooltipFontStyle(ObjectUtil.clone(skin.getTooltipFontStyle()));
		clone.setTooltipFontVariant(ObjectUtil.clone(skin.getTooltipFontVariant()));
		clone.setTooltipFontWeight(ObjectUtil.clone(skin.getTooltipFontWeight()));
		clone.setTooltipTextColor(ObjectUtil.clone(skin.getTooltipTextColor()));
		clone.setTooltipTextDecoration(ObjectUtil.clone(skin.getTooltipTextDecoration()));
		clone.setHighlightBackgroundColor(ObjectUtil.clone(skin.getHighlightBackgroundColor()));
		clone.setHighlightBackgroundImage(ObjectUtil.clone(skin.getHighlightBackgroundImage()));
		clone.setHighlightBorderStyle(ObjectUtil.clone(skin.getHighlightBorderStyle()));
		clone.setHighlightBorderWidth(ObjectUtil.clone(skin.getHighlightBorderWidth()));
		clone.setHighlightBorderColor(ObjectUtil.clone(skin.getHighlightBorderColor()));
		clone.setHighlightFontFamily(ObjectUtil.clone(skin.getHighlightFontFamily()));
		clone.setHighlightFontSize(ObjectUtil.clone(skin.getHighlightFontSize()));
		clone.setHighlightFontStyle(ObjectUtil.clone(skin.getHighlightFontStyle()));
		clone.setHighlightFontWeight(ObjectUtil.clone(skin.getHighlightFontWeight()));
		clone.setHighlightTextColor(ObjectUtil.clone(skin.getHighlightTextColor()));
		clone.setHighlightTextDecoration(ObjectUtil.clone(skin.getHighlightTextDecoration()));
		clone.setHighlightControlColor(ObjectUtil.clone(skin.getHighlightControlColor()));
		clone.setFocusBackgroundColor(ObjectUtil.clone(skin.getFocusBackgroundColor()));
		clone.setFocusBackgroundImage(ObjectUtil.clone(skin.getFocusBackgroundImage()));
		clone.setFocusGradientColor(ObjectUtil.clone(skin.getFocusGradientColor()));
		clone.setFocusBorderStyle(ObjectUtil.clone(skin.getFocusBorderStyle()));
		clone.setFocusBorderWidth(ObjectUtil.clone(skin.getFocusBorderWidth()));
		clone.setFocusBorderColor(ObjectUtil.clone(skin.getFocusBorderColor()));
		clone.setFocusFontFamily(ObjectUtil.clone(skin.getFocusFontFamily()));
		clone.setFocusFontSize(ObjectUtil.clone(skin.getFocusFontSize()));
		clone.setFocusFontStyle(ObjectUtil.clone(skin.getFocusFontStyle()));
		clone.setFocusFontWeight(ObjectUtil.clone(skin.getFocusFontWeight()));
		clone.setFocusTextColor(ObjectUtil.clone(skin.getFocusTextColor()));
		clone.setFocusTextDecoration(ObjectUtil.clone(skin.getFocusTextDecoration()));
		clone.setFocusControlColor(ObjectUtil.clone(skin.getFocusControlColor()));
		clone.setDisabledBackgroundColor(ObjectUtil.clone(skin.getDisabledBackgroundColor()));
		clone.setDisabledBackgroundImage(ObjectUtil.clone(skin.getDisabledBackgroundImage()));
		clone.setDisabledBorderStyle(ObjectUtil.clone(skin.getDisabledBorderStyle()));
		clone.setDisabledBorderWidth(ObjectUtil.clone(skin.getDisabledBorderWidth()));
		clone.setDisabledBorderColor(ObjectUtil.clone(skin.getDisabledBorderColor()));
		clone.setDisabledFontFamily(ObjectUtil.clone(skin.getDisabledFontFamily()));
		clone.setDisabledFontSize(ObjectUtil.clone(skin.getDisabledFontSize()));
		clone.setDisabledFontStyle(ObjectUtil.clone(skin.getDisabledFontStyle()));
		clone.setDisabledFontWeight(ObjectUtil.clone(skin.getDisabledFontWeight()));
		clone.setDisabledTextColor(ObjectUtil.clone(skin.getDisabledTextColor()));
		clone.setDisabledTextDecoration(ObjectUtil.clone(skin.getDisabledTextDecoration()));
		clone.setDisabledControlColor(ObjectUtil.clone(skin.getDisabledControlColor()));
		clone.setThemeBorderColor(ObjectUtil.clone(skin.getThemeBorderColor()));
		clone.setThemeBackgroundColor(ObjectUtil.clone(skin.getThemeBackgroundColor()));
		return clone;
	}
	
}

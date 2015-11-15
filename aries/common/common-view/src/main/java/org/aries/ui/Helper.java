package org.aries.ui;

import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.util.NameUtil;
import org.aries.util.ResourceUtil;
import org.joda.time.DateTime;



@SessionScoped
@Named("helper")
public class Helper extends AbstractPanelManager implements Serializable {
	
	private String contentWidth = "1000";

	private String contentHeight = "auto";

	private String wizard_leftSection_width = "160";

	private String wizard_leftSection_marginLeft = "20";
	
	private String wizard_leftSection_marginRight = "20";
	
	private String wizard_leftSection_height = "auto";

	private String wizard_middleSection_width = "500";

	private String wizard_middleSection_height = "240";

	private String wizard_rightSection_width = "205";
	
	private String wizard_rightSection_height = "auto";
	
	private String wizard_rightSection_marginLeft = "20";
	
	private String wizard_rightSection_marginRight = "20";
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss MMM d");


	public String getTime() {
		return toTimeString(new Date());
	}
	
	public String getContentWidth() {
		return contentWidth;
	}

	public void setContentWidth(String contentWidth) {
		this.contentWidth = contentWidth;
	}

	public String getContentHeight() {
		return contentHeight;
	}

	public void setContentHeight(String contentHeight) {
		this.contentHeight = contentHeight;
	}

	public String getWizard_leftSection_width() {
		return wizard_leftSection_width;
	}

	public void setWizard_leftSection_width(String wizard_leftSection_width) {
		this.wizard_leftSection_width = wizard_leftSection_width;
	}

	public String getWizard_leftSection_height() {
		return wizard_leftSection_height;
	}

	public void setWizard_leftSection_height(String wizard_leftSection_height) {
		this.wizard_leftSection_height = wizard_leftSection_height;
	}

	public String getWizard_leftSection_marginLeft() {
		return wizard_leftSection_marginLeft;
	}

	public void setWizard_leftSection_marginLeft(
			String wizard_leftSection_marginLeft) {
		this.wizard_leftSection_marginLeft = wizard_leftSection_marginLeft;
	}

	public String getWizard_leftSection_marginRight() {
		return wizard_leftSection_marginRight;
	}

	public void setWizard_leftSection_marginRight(
			String wizard_leftSection_marginRight) {
		this.wizard_leftSection_marginRight = wizard_leftSection_marginRight;
	}

	public String getWizard_middleSection_width() {
		return wizard_middleSection_width;
	}

	public void setWizard_middleSection_width(String wizard_middleSection_width) {
		this.wizard_middleSection_width = wizard_middleSection_width;
	}

	public String getWizard_middleSection_height() {
		return wizard_middleSection_height;
	}

	public void setWizard_middleSection_height(String wizard_middleSection_height) {
		this.wizard_middleSection_height = wizard_middleSection_height;
	}

	public String getWizard_rightSection_width() {
		return wizard_rightSection_width;
	}

	public void setWizard_rightSection_width(String wizard_rightSection_width) {
		this.wizard_rightSection_width = wizard_rightSection_width;
	}

	public String getWizard_rightSection_height() {
		return wizard_rightSection_height;
	}

	public void setWizard_rightSection_height(String wizard_rightSection_height) {
		this.wizard_rightSection_height = wizard_rightSection_height;
	}

	public String getWizard_rightSection_marginLeft() {
		return wizard_rightSection_marginLeft;
	}

	public void setWizard_rightSection_marginLeft(
			String wizard_rightSection_marginLeft) {
		this.wizard_rightSection_marginLeft = wizard_rightSection_marginLeft;
	}

	public String getWizard_rightSection_marginRight() {
		return wizard_rightSection_marginRight;
	}

	public void setWizard_rightSection_marginRight(
			String wizard_rightSection_marginRight) {
		this.wizard_rightSection_marginRight = wizard_rightSection_marginRight;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	@PostConstruct
	public void create() {
		BeanContext.set("helper",  this);
	}
	
	@PreDestroy
	public void destroy() {
		BeanContext.set("helper",  null);
	}
	
	public String capitalize(String string) {
		return NameUtil.capName(string);
	}
	
	public String uncapitalize(String string) {
		return NameUtil.uncapName(string);
	}
	
	public String toTimeString(Date dateTime) {
		//DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateTime != null ? timeFormat.format(dateTime) : "";
	}
	
	public String toTimeString(DateTime dateTime) {
		return dateTime != null ? dateTime.toString("yyyy-MM-dd [HH:mm:ss]") : "";
	}
	
	public String toSimpleDate(Date dateTime) {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		return dateTime != null ? formatter.format(dateTime) : "";
	}
	
	public String toDateString(Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		return dateTime != null ? formatter.format(dateTime) : "";
	}

	public String toDateString(DateTime dateTime) {
		return dateTime != null ? dateTime.toString("yyyy-MM-dd") : "";
	}

	public String toActiveString(Boolean active) {
		return active ? "Active" : "Inactive";
	}
	
	public Boolean toActiveInactiveBoolean(String active) {
		return active.equals("Inactive") ? Boolean.FALSE : Boolean.TRUE;
	}
	
	public String convert(String dimension) {
		if (dimension.equals("auto"))
			return dimension;
		if (dimension.endsWith("%"))
			return dimension;
		if (dimension.endsWith("px"))
			return dimension;
		return dimension + "px";
	}

	public String getDateAsWordString() {
		dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
		String date = dateFormat.format(new Date());
		return date;
	}
	
	public String toCamelCase(String text) {
		if (text.contains(" "))
			text = NameUtil.toCamelCase(text, " ");
		if (text.contains(" "))
			text = NameUtil.toCamelCase(text, "-");
		text = text.replace(" ",  "");
		text = text.replace("\t",  "");
		return text;
	}
	
	public boolean imageExists(String path) {
		String prefix = "META-INF";
		String fullPath = prefix + "/" + path;
		URL resource = ResourceUtil.getResource(fullPath);
		return resource != null;
	}
	
}

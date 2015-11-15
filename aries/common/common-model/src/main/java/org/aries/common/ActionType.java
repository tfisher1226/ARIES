package org.aries.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
	/**
	 * <p>Java class for ActionType complex type.
	 *
	 * <p>The class represents null XML schema type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType name="ActionType">
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 *
	 */

@XmlType(name = "ActionType", namespace = "http://www.aries.org/common")
@XmlEnum
public enum ActionType {

	PROCESS,
	CANCEL,
	UNDO;

	public static ActionType fromValue(String name) {
		return valueOf(name);
	}

	public String value() {
		return name();
	}

}
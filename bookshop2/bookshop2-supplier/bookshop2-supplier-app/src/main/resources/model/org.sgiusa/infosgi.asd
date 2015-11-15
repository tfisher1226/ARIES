<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<xs:schema 
	version="1.0" 
	jaxb:version="2.0"
	elementFormDefault="qualified" 
	attributeFormDefault="qualified" 
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:common="http://www.aries.org/common/0.0.1"
	xmlns:sgiusa="http://www.sgiusa.org/model/0.0.1" 
	targetNamespace="http://www.sgiusa.org/model/0.0.1">
	
	<xs:import namespace="http://www.aries.org/common/0.0.1" schemaLocation="aries-common-0.0.1.xsd"/>


nullable = ["true", "false"]
required = ["true", "false"]
minOccurs = [any positive number <= maxOccurs]
maxOccurs = [any positive number >= minOccurs or "unbounded"]
relation = ["one-to-one", "many-to-one", "many-to-many"]
cachable = ["none", "readonly", "readwrite"]
cascade = {"all", "refresh", "merge", "persist", "delete"}
fetch = ["eager", "lazy"]


<domain name="sgiusa">

	<type name="Role">
		<value name="MANAGER" index=""/>
		<xs:enumeration value="USER" />
		<xs:enumeration value="HOST" />
	</type>

	<element name="users" type="Users" />
		<reference name="record" type="sgiusa:User" minOccurs="0" maxOccurs="unbounded" />
	</element>

	
	<element name="User" cachable="read-write">
		<attribute name="id" type="xs:long" key="true" />
		<attribute name="userId" type="xs:string" required="true" unique="true"/>
		<attribute name="password" type="xs:string" required="true" />
		<attribute name="enabled" type="xs:boolean" />
		<attribute name="firstName" type="xs:string" required="true" />
		<attribute name="lastName" type="xs:string" required="true" />
		<attribute name="emailAccount" type="common:EmailAccount" cascade="all" />
		<attribute name="emailAddress" type="common:EmailAddress" cascade="all" />
		<attribute name="streetAddress" type="common:StreetAddress" cascade="all" />
		<attribute name="homePhone" type="common:PhoneNumber" cascade="all" />
		<attribute name="cellPhone" type="common:PhoneNumber" cascade="all" />
		<attribute name="role" type="sgiusa:Role" relation="many-to-many" cascade="none" required="true" />
		<attribute name="permissions" type="sgiusa:Permission" relation="one-to-many" cascade="all" />
		<attribute name="preferences" type="sgiusa:Preferences" relation="one-to-many" cascade="all" />
	</element>



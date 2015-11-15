-- MySQL dump 10.13  Distrib 5.1.54, for Win32 (ia32)
--
-- Host: localhost    Database: adminDB
-- ------------------------------------------------------
-- Server version	5.1.54-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `adminDB`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `admindb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `adminDB`;

--
-- Table structure for table `abstract_event`
--

DROP TABLE IF EXISTS `abstract_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abstract_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `abstract_note`
--

DROP TABLE IF EXISTS `abstract_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abstract_note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `abstract_note_author_fk` (`author_id`),
  CONSTRAINT `abstract_note_author_fk` FOREIGN KEY (`author_id`) REFERENCES `abstract_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `abstract_user`
--

DROP TABLE IF EXISTS `abstract_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abstract_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  `email_address_id` bigint(20) DEFAULT NULL,
  `phone_number_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_id_2` (`user_id`),
  UNIQUE KEY `email_address_id` (`email_address_id`),
  KEY `abstract_user_phone_number_fk` (`phone_number_id`),
  KEY `abstract_user_email_address_fk` (`email_address_id`),
  CONSTRAINT `abstract_user_email_address_fk` FOREIGN KEY (`email_address_id`) REFERENCES `email_address` (`id`),
  CONSTRAINT `abstract_user_phone_number_fk` FOREIGN KEY (`phone_number_id`) REFERENCES `phone_number` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) DEFAULT NULL,
  `file_data` tinyblob,
  `file_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_account`
--

DROP TABLE IF EXISTS `email_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_account_email_boxe`
--

DROP TABLE IF EXISTS `email_account_email_boxe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_account_email_boxe` (
  `email_account_id` bigint(20) NOT NULL,
  `email_boxe_id` bigint(20) NOT NULL,
  UNIQUE KEY `email_boxe_id` (`email_boxe_id`),
  KEY `email_account_email_boxe_inverse_fk` (`email_boxe_id`),
  KEY `email_account_email_boxe_fk` (`email_account_id`),
  CONSTRAINT `email_account_email_boxe_fk` FOREIGN KEY (`email_account_id`) REFERENCES `email_account` (`id`),
  CONSTRAINT `email_account_email_boxe_inverse_fk` FOREIGN KEY (`email_boxe_id`) REFERENCES `email_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_address`
--

DROP TABLE IF EXISTS `email_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `phone_number_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email_address_phone_number_fk` (`phone_number_id`),
  CONSTRAINT `email_address_phone_number_fk` FOREIGN KEY (`phone_number_id`) REFERENCES `phone_number` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_address_list`
--

DROP TABLE IF EXISTS `email_address_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_address_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_address_list_addresse`
--

DROP TABLE IF EXISTS `email_address_list_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_address_list_addresse` (
  `email_address_list_id` bigint(20) NOT NULL,
  `addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `addresse_id` (`addresse_id`),
  KEY `email_address_list_addresse_fk` (`email_address_list_id`),
  KEY `email_address_list_addresse_inverse_fk` (`addresse_id`),
  CONSTRAINT `email_address_list_addresse_inverse_fk` FOREIGN KEY (`addresse_id`) REFERENCES `email_address` (`id`),
  CONSTRAINT `email_address_list_addresse_fk` FOREIGN KEY (`email_address_list_id`) REFERENCES `email_address_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_box`
--

DROP TABLE IF EXISTS `email_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_box` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `email_account_id` bigint(20) DEFAULT NULL,
  `parent_box_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `email_box_parent_box_fk` (`parent_box_id`),
  KEY `email_box_email_account_fk` (`email_account_id`),
  CONSTRAINT `email_box_email_account_fk` FOREIGN KEY (`email_account_id`) REFERENCES `email_account` (`id`),
  CONSTRAINT `email_box_parent_box_fk` FOREIGN KEY (`parent_box_id`) REFERENCES `email_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_box_message`
--

DROP TABLE IF EXISTS `email_box_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_box_message` (
  `email_box_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  UNIQUE KEY `message_id` (`message_id`),
  KEY `email_box_message_fk` (`email_box_id`),
  KEY `email_box_message_inverse_fk` (`message_id`),
  CONSTRAINT `email_box_message_inverse_fk` FOREIGN KEY (`message_id`) REFERENCES `email_message` (`id`),
  CONSTRAINT `email_box_message_fk` FOREIGN KEY (`email_box_id`) REFERENCES `email_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message`
--

DROP TABLE IF EXISTS `email_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `send_as_html` tinyint(1) DEFAULT NULL,
  `smtp_host` varchar(255) DEFAULT NULL,
  `smtp_port` varchar(255) DEFAULT NULL,
  `source_id` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `from_address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email_message_from_address_fk` (`from_address_id`),
  CONSTRAINT `email_message_from_address_fk` FOREIGN KEY (`from_address_id`) REFERENCES `email_address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_admin_addresse`
--

DROP TABLE IF EXISTS `email_message_admin_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_admin_addresse` (
  `email_message_id` bigint(20) NOT NULL,
  `admin_addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `admin_addresse_id` (`admin_addresse_id`),
  KEY `email_message_admin_addresse_fk` (`email_message_id`),
  KEY `email_message_admin_addresse_inverse_fk` (`admin_addresse_id`),
  CONSTRAINT `email_message_admin_addresse_inverse_fk` FOREIGN KEY (`admin_addresse_id`) REFERENCES `email_address_list` (`id`),
  CONSTRAINT `email_message_admin_addresse_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_attachment`
--

DROP TABLE IF EXISTS `email_message_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_attachment` (
  `email_message_id` bigint(20) NOT NULL,
  `attachment_id` bigint(20) NOT NULL,
  UNIQUE KEY `attachment_id` (`attachment_id`),
  KEY `email_message_attachment_fk` (`email_message_id`),
  KEY `email_message_attachment_inverse_fk` (`attachment_id`),
  CONSTRAINT `email_message_attachment_inverse_fk` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`id`),
  CONSTRAINT `email_message_attachment_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_bcc_addresse`
--

DROP TABLE IF EXISTS `email_message_bcc_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_bcc_addresse` (
  `email_message_id` bigint(20) NOT NULL,
  `bcc_addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `bcc_addresse_id` (`bcc_addresse_id`),
  KEY `email_message_bcc_addresse_fk` (`email_message_id`),
  KEY `email_message_bcc_addresse_inverse_fk` (`bcc_addresse_id`),
  CONSTRAINT `email_message_bcc_addresse_inverse_fk` FOREIGN KEY (`bcc_addresse_id`) REFERENCES `email_address_list` (`id`),
  CONSTRAINT `email_message_bcc_addresse_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_cc_addresse`
--

DROP TABLE IF EXISTS `email_message_cc_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_cc_addresse` (
  `email_message_id` bigint(20) NOT NULL,
  `cc_addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `cc_addresse_id` (`cc_addresse_id`),
  KEY `email_message_cc_addresse_fk` (`email_message_id`),
  KEY `email_message_cc_addresse_inverse_fk` (`cc_addresse_id`),
  CONSTRAINT `email_message_cc_addresse_inverse_fk` FOREIGN KEY (`cc_addresse_id`) REFERENCES `email_address_list` (`id`),
  CONSTRAINT `email_message_cc_addresse_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_replyto_addresse`
--

DROP TABLE IF EXISTS `email_message_replyto_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_replyto_addresse` (
  `email_message_id` bigint(20) NOT NULL,
  `replyto_addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `replyto_addresse_id` (`replyto_addresse_id`),
  KEY `email_message_replyto_addresse_fk` (`email_message_id`),
  KEY `email_message_replyto_addresse_inverse_fk` (`replyto_addresse_id`),
  CONSTRAINT `email_message_replyto_addresse_inverse_fk` FOREIGN KEY (`replyto_addresse_id`) REFERENCES `email_address_list` (`id`),
  CONSTRAINT `email_message_replyto_addresse_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `email_message_to_addresse`
--

DROP TABLE IF EXISTS `email_message_to_addresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_message_to_addresse` (
  `email_message_id` bigint(20) NOT NULL,
  `to_addresse_id` bigint(20) NOT NULL,
  UNIQUE KEY `to_addresse_id` (`to_addresse_id`),
  KEY `email_message_to_addresse_fk` (`email_message_id`),
  KEY `email_message_to_addresse_inverse_fk` (`to_addresse_id`),
  CONSTRAINT `email_message_to_addresse_inverse_fk` FOREIGN KEY (`to_addresse_id`) REFERENCES `email_address_list` (`id`),
  CONSTRAINT `email_message_to_addresse_fk` FOREIGN KEY (`email_message_id`) REFERENCES `email_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` tinyint(1) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_user_fk` (`user_id`),
  CONSTRAINT `permission_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission_capability`
--

DROP TABLE IF EXISTS `permission_capability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_capability` (
  `permission_id` bigint(20) NOT NULL,
  `capabilities` varchar(255) DEFAULT NULL,
  KEY `FK64CA4D88F198FD49` (`permission_id`),
  CONSTRAINT `FK64CA4D88F198FD49` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `email_address_id` bigint(20) DEFAULT NULL,
  `name_id` bigint(20) NOT NULL,
  `phone_number_id` bigint(20) DEFAULT NULL,
  `street_address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `person_phone_number_fk` (`phone_number_id`),
  KEY `person_email_address_fk` (`email_address_id`),
  KEY `person_street_address_fk` (`street_address_id`),
  KEY `person_name_fk` (`name_id`),
  CONSTRAINT `person_name_fk` FOREIGN KEY (`name_id`) REFERENCES `person_name` (`id`),
  CONSTRAINT `person_email_address_fk` FOREIGN KEY (`email_address_id`) REFERENCES `email_address` (`id`),
  CONSTRAINT `person_phone_number_fk` FOREIGN KEY (`phone_number_id`) REFERENCES `phone_number` (`id`),
  CONSTRAINT `person_street_address_fk` FOREIGN KEY (`street_address_id`) REFERENCES `street_address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person_name`
--

DROP TABLE IF EXISTS `person_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `middle_initial` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone_number`
--

DROP TABLE IF EXISTS `phone_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_number` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` varchar(255) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `number` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preferences`
--

DROP TABLE IF EXISTS `preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preferences` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enable_tooltips` tinyint(1) DEFAULT NULL,
  `selected_node` bigint(20) DEFAULT NULL,
  `theme_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preferences_open_node`
--

DROP TABLE IF EXISTS `preferences_open_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preferences_open_node` (
  `preferences_id` bigint(20) NOT NULL,
  `open_nodes` tinyint(1) DEFAULT NULL,
  `openNodes_KEY` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`preferences_id`,`openNodes_KEY`),
  KEY `FK12142250FF17B111` (`preferences_id`),
  CONSTRAINT `FK12142250FF17B111` FOREIGN KEY (`preferences_id`) REFERENCES `preferences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conditional` tinyint(1) DEFAULT NULL,
  `role_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_type` (`role_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_group`
--

DROP TABLE IF EXISTS `role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_group` (
  `role_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`group_id`),
  KEY `role_group_fk` (`role_id`),
  KEY `role_group_inverse_fk` (`group_id`),
  CONSTRAINT `role_group_inverse_fk` FOREIGN KEY (`group_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_group_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skin`
--

DROP TABLE IF EXISTS `skin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `background_color` varchar(255) DEFAULT NULL,
  `background_image` varchar(255) DEFAULT NULL,
  `border_color` varchar(255) DEFAULT NULL,
  `border_style` varchar(255) DEFAULT NULL,
  `border_width` varchar(255) DEFAULT NULL,
  `button_background_color` varchar(255) DEFAULT NULL,
  `button_background_image` varchar(255) DEFAULT NULL,
  `button_border_color` varchar(255) DEFAULT NULL,
  `button_border_style` varchar(255) DEFAULT NULL,
  `button_border_width` varchar(255) DEFAULT NULL,
  `button_control_color` varchar(255) DEFAULT NULL,
  `button_corner_radius` varchar(255) DEFAULT NULL,
  `button_font_family` varchar(255) DEFAULT NULL,
  `button_font_size` varchar(255) DEFAULT NULL,
  `button_font_style` varchar(255) DEFAULT NULL,
  `button_font_variant` varchar(255) DEFAULT NULL,
  `button_font_weight` varchar(255) DEFAULT NULL,
  `button_gradient_color` varchar(255) DEFAULT NULL,
  `button_text_color` varchar(255) DEFAULT NULL,
  `button_text_decoration` varchar(255) DEFAULT NULL,
  `corner_radius` varchar(255) DEFAULT NULL,
  `disabled_background_color` varchar(255) DEFAULT NULL,
  `disabled_background_image` varchar(255) DEFAULT NULL,
  `disabled_border_color` varchar(255) DEFAULT NULL,
  `disabled_border_style` varchar(255) DEFAULT NULL,
  `disabled_border_width` varchar(255) DEFAULT NULL,
  `disabled_control_color` varchar(255) DEFAULT NULL,
  `disabled_font_family` varchar(255) DEFAULT NULL,
  `disabled_font_size` varchar(255) DEFAULT NULL,
  `disabled_font_style` varchar(255) DEFAULT NULL,
  `disabled_font_weight` varchar(255) DEFAULT NULL,
  `disabled_text_color` varchar(255) DEFAULT NULL,
  `disabled_text_decoration` varchar(255) DEFAULT NULL,
  `focus_background_color` varchar(255) DEFAULT NULL,
  `focus_background_image` varchar(255) DEFAULT NULL,
  `focus_border_color` varchar(255) DEFAULT NULL,
  `focus_border_style` varchar(255) DEFAULT NULL,
  `focus_border_width` varchar(255) DEFAULT NULL,
  `focus_control_color` varchar(255) DEFAULT NULL,
  `focus_font_family` varchar(255) DEFAULT NULL,
  `focus_font_size` varchar(255) DEFAULT NULL,
  `focus_font_style` varchar(255) DEFAULT NULL,
  `focus_font_weight` varchar(255) DEFAULT NULL,
  `focus_gradient_color` varchar(255) DEFAULT NULL,
  `focus_text_color` varchar(255) DEFAULT NULL,
  `focus_text_decoration` varchar(255) DEFAULT NULL,
  `font_family` varchar(255) DEFAULT NULL,
  `font_size` varchar(255) DEFAULT NULL,
  `font_style` varchar(255) DEFAULT NULL,
  `font_variant` varchar(255) DEFAULT NULL,
  `font_weight` varchar(255) DEFAULT NULL,
  `gradient_color` varchar(255) DEFAULT NULL,
  `gradient_image` varchar(255) DEFAULT NULL,
  `header_background_color` varchar(255) DEFAULT NULL,
  `header_background_image` varchar(255) DEFAULT NULL,
  `header_border_color` varchar(255) DEFAULT NULL,
  `header_border_style` varchar(255) DEFAULT NULL,
  `header_border_width` varchar(255) DEFAULT NULL,
  `header_font_family` varchar(255) DEFAULT NULL,
  `header_font_size` varchar(255) DEFAULT NULL,
  `header_font_style` varchar(255) DEFAULT NULL,
  `header_font_variant` varchar(255) DEFAULT NULL,
  `header_font_weight` varchar(255) DEFAULT NULL,
  `header_gradient_color` varchar(255) DEFAULT NULL,
  `header_text_color` varchar(255) DEFAULT NULL,
  `header_text_decoration` varchar(255) DEFAULT NULL,
  `height` varchar(255) DEFAULT NULL,
  `highlight_background_color` varchar(255) DEFAULT NULL,
  `highlight_background_image` varchar(255) DEFAULT NULL,
  `highlight_border_color` varchar(255) DEFAULT NULL,
  `highlight_border_style` varchar(255) DEFAULT NULL,
  `highlight_border_width` varchar(255) DEFAULT NULL,
  `highlight_control_color` varchar(255) DEFAULT NULL,
  `highlight_font_family` varchar(255) DEFAULT NULL,
  `highlight_font_size` varchar(255) DEFAULT NULL,
  `highlight_font_style` varchar(255) DEFAULT NULL,
  `highlight_font_weight` varchar(255) DEFAULT NULL,
  `highlight_text_color` varchar(255) DEFAULT NULL,
  `highlight_text_decoration` varchar(255) DEFAULT NULL,
  `link_color` varchar(255) DEFAULT NULL,
  `link_hover_color` varchar(255) DEFAULT NULL,
  `link_visited_color` varchar(255) DEFAULT NULL,
  `margin` varchar(255) DEFAULT NULL,
  `margin_bottom` varchar(255) DEFAULT NULL,
  `margin_left` varchar(255) DEFAULT NULL,
  `margin_right` varchar(255) DEFAULT NULL,
  `margin_top` varchar(255) DEFAULT NULL,
  `padding` varchar(255) DEFAULT NULL,
  `padding_bottom` varchar(255) DEFAULT NULL,
  `padding_left` varchar(255) DEFAULT NULL,
  `padding_right` varchar(255) DEFAULT NULL,
  `padding_top` varchar(255) DEFAULT NULL,
  `tab_active_background_color` varchar(255) DEFAULT NULL,
  `tab_active_background_image` varchar(255) DEFAULT NULL,
  `tab_active_border_color` varchar(255) DEFAULT NULL,
  `tab_active_font_family` varchar(255) DEFAULT NULL,
  `tab_active_font_size` varchar(255) DEFAULT NULL,
  `tab_active_font_style` varchar(255) DEFAULT NULL,
  `tab_active_font_weight` varchar(255) DEFAULT NULL,
  `tab_active_gradient_color` varchar(255) DEFAULT NULL,
  `tab_active_text_color` varchar(255) DEFAULT NULL,
  `tab_active_text_decoration` varchar(255) DEFAULT NULL,
  `tab_background_color` varchar(255) DEFAULT NULL,
  `tab_background_image` varchar(255) DEFAULT NULL,
  `tab_border_color` varchar(255) DEFAULT NULL,
  `tab_corner_radius` varchar(255) DEFAULT NULL,
  `tab_disabled_background_color` varchar(255) DEFAULT NULL,
  `tab_disabled_background_image` varchar(255) DEFAULT NULL,
  `tab_disabled_border_color` varchar(255) DEFAULT NULL,
  `tab_disabled_font_family` varchar(255) DEFAULT NULL,
  `tab_disabled_font_size` varchar(255) DEFAULT NULL,
  `tab_disabled_font_style` varchar(255) DEFAULT NULL,
  `tab_disabled_font_weight` varchar(255) DEFAULT NULL,
  `tab_disabled_gradient_color` varchar(255) DEFAULT NULL,
  `tab_disabled_text_color` varchar(255) DEFAULT NULL,
  `tab_disabled_text_decoration` varchar(255) DEFAULT NULL,
  `tab_font_family` varchar(255) DEFAULT NULL,
  `tab_font_size` varchar(255) DEFAULT NULL,
  `tab_font_style` varchar(255) DEFAULT NULL,
  `tab_font_weight` varchar(255) DEFAULT NULL,
  `tab_gradient_color` varchar(255) DEFAULT NULL,
  `tab_inactive_background_color` varchar(255) DEFAULT NULL,
  `tab_inactive_background_image` varchar(255) DEFAULT NULL,
  `tab_inactive_border_color` varchar(255) DEFAULT NULL,
  `tab_inactive_font_family` varchar(255) DEFAULT NULL,
  `tab_inactive_font_size` varchar(255) DEFAULT NULL,
  `tab_inactive_font_style` varchar(255) DEFAULT NULL,
  `tab_inactive_font_weight` varchar(255) DEFAULT NULL,
  `tab_inactive_gradient_color` varchar(255) DEFAULT NULL,
  `tab_inactive_text_color` varchar(255) DEFAULT NULL,
  `tab_inactive_text_decoration` varchar(255) DEFAULT NULL,
  `tab_text_color` varchar(255) DEFAULT NULL,
  `tab_text_decoration` varchar(255) DEFAULT NULL,
  `table_background_color` varchar(255) DEFAULT NULL,
  `table_border_color` varchar(255) DEFAULT NULL,
  `table_border_width` varchar(255) DEFAULT NULL,
  `table_cell_background_color` varchar(255) DEFAULT NULL,
  `table_cell_background_image` varchar(255) DEFAULT NULL,
  `table_cell_border_color` varchar(255) DEFAULT NULL,
  `table_cell_font_family` varchar(255) DEFAULT NULL,
  `table_cell_font_size` varchar(255) DEFAULT NULL,
  `table_cell_font_style` varchar(255) DEFAULT NULL,
  `table_cell_font_variant` varchar(255) DEFAULT NULL,
  `table_cell_font_weight` varchar(255) DEFAULT NULL,
  `table_cell_gradient_color` varchar(255) DEFAULT NULL,
  `table_cell_text_color` varchar(255) DEFAULT NULL,
  `table_cell_text_decoration` varchar(255) DEFAULT NULL,
  `table_footer_background_color` varchar(255) DEFAULT NULL,
  `table_footer_background_image` varchar(255) DEFAULT NULL,
  `table_footer_border_color` varchar(255) DEFAULT NULL,
  `table_footer_font_family` varchar(255) DEFAULT NULL,
  `table_footer_font_size` varchar(255) DEFAULT NULL,
  `table_footer_font_style` varchar(255) DEFAULT NULL,
  `table_footer_font_variant` varchar(255) DEFAULT NULL,
  `table_footer_font_weight` varchar(255) DEFAULT NULL,
  `table_footer_gradient_color` varchar(255) DEFAULT NULL,
  `table_footer_text_color` varchar(255) DEFAULT NULL,
  `table_footer_text_decoration` varchar(255) DEFAULT NULL,
  `table_header_background_color` varchar(255) DEFAULT NULL,
  `table_header_background_image` varchar(255) DEFAULT NULL,
  `table_header_border_color` varchar(255) DEFAULT NULL,
  `table_header_font_family` varchar(255) DEFAULT NULL,
  `table_header_font_size` varchar(255) DEFAULT NULL,
  `table_header_font_style` varchar(255) DEFAULT NULL,
  `table_header_font_variant` varchar(255) DEFAULT NULL,
  `table_header_font_weight` varchar(255) DEFAULT NULL,
  `table_header_gradient_color` varchar(255) DEFAULT NULL,
  `table_header_text_color` varchar(255) DEFAULT NULL,
  `table_header_text_decoration` varchar(255) DEFAULT NULL,
  `table_sub_footer_background_color` varchar(255) DEFAULT NULL,
  `table_sub_header_background_color` varchar(255) DEFAULT NULL,
  `text_color` varchar(255) DEFAULT NULL,
  `text_decoration` varchar(255) DEFAULT NULL,
  `theme_background_color` varchar(255) DEFAULT NULL,
  `theme_border_color` varchar(255) DEFAULT NULL,
  `toolbar_background_color` varchar(255) DEFAULT NULL,
  `toolbar_background_image` varchar(255) DEFAULT NULL,
  `toolbar_border_color` varchar(255) DEFAULT NULL,
  `toolbar_border_style` varchar(255) DEFAULT NULL,
  `toolbar_border_width` varchar(255) DEFAULT NULL,
  `toolbar_font_family` varchar(255) DEFAULT NULL,
  `toolbar_font_size` varchar(255) DEFAULT NULL,
  `toolbar_font_style` varchar(255) DEFAULT NULL,
  `toolbar_font_variant` varchar(255) DEFAULT NULL,
  `toolbar_font_weight` varchar(255) DEFAULT NULL,
  `toolbar_gradient_color` varchar(255) DEFAULT NULL,
  `toolbar_text_color` varchar(255) DEFAULT NULL,
  `toolbar_text_decoration` varchar(255) DEFAULT NULL,
  `tooltip_background_color` varchar(255) DEFAULT NULL,
  `tooltip_background_image` varchar(255) DEFAULT NULL,
  `tooltip_border_color` varchar(255) DEFAULT NULL,
  `tooltip_border_style` varchar(255) DEFAULT NULL,
  `tooltip_border_width` varchar(255) DEFAULT NULL,
  `tooltip_font_family` varchar(255) DEFAULT NULL,
  `tooltip_font_size` varchar(255) DEFAULT NULL,
  `tooltip_font_style` varchar(255) DEFAULT NULL,
  `tooltip_font_variant` varchar(255) DEFAULT NULL,
  `tooltip_font_weight` varchar(255) DEFAULT NULL,
  `tooltip_gradient_color` varchar(255) DEFAULT NULL,
  `tooltip_text_color` varchar(255) DEFAULT NULL,
  `tooltip_text_decoration` varchar(255) DEFAULT NULL,
  `width` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `street_address`
--

DROP TABLE IF EXISTS `street_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `street_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip_code_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `street_address_zip_code_fk` (`zip_code_id`),
  CONSTRAINT `street_address_zip_code_fk` FOREIGN KEY (`zip_code_id`) REFERENCES `zip_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `last_update` datetime DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `password_salt` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `cell_phone_id` bigint(20) DEFAULT NULL,
  `email_account_id` bigint(20) DEFAULT NULL,
  `email_address_id` bigint(20) NOT NULL,
  `home_phone_id` bigint(20) DEFAULT NULL,
  `preferences_id` bigint(20) DEFAULT NULL,
  `street_address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `user_home_phone_fk` (`home_phone_id`),
  KEY `user_preferences_fk` (`preferences_id`),
  KEY `user_email_address_fk` (`email_address_id`),
  KEY `user_cell_phone_fk` (`cell_phone_id`),
  KEY `user_email_account_fk` (`email_account_id`),
  KEY `user_street_address_fk` (`street_address_id`),
  CONSTRAINT `user_street_address_fk` FOREIGN KEY (`street_address_id`) REFERENCES `street_address` (`id`),
  CONSTRAINT `user_cell_phone_fk` FOREIGN KEY (`cell_phone_id`) REFERENCES `phone_number` (`id`),
  CONSTRAINT `user_email_account_fk` FOREIGN KEY (`email_account_id`) REFERENCES `email_account` (`id`),
  CONSTRAINT `user_email_address_fk` FOREIGN KEY (`email_address_id`) REFERENCES `email_address` (`id`),
  CONSTRAINT `user_home_phone_fk` FOREIGN KEY (`home_phone_id`) REFERENCES `phone_number` (`id`),
  CONSTRAINT `user_preferences_fk` FOREIGN KEY (`preferences_id`) REFERENCES `preferences` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_permission`
--

DROP TABLE IF EXISTS `user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_permission` (
  `user_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  UNIQUE KEY `permission_id` (`permission_id`),
  KEY `user_permission_inverse_fk` (`permission_id`),
  KEY `user_permission_fk` (`user_id`),
  CONSTRAINT `user_permission_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_permission_inverse_fk` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_role_inverse_fk` (`role_id`),
  KEY `user_role_fk` (`user_id`),
  CONSTRAINT `user_role_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_inverse_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zip_code`
--

DROP TABLE IF EXISTS `zip_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zip_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `extension` varchar(255) DEFAULT NULL,
  `number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-03 19:26:33

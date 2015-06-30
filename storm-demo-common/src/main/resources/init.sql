/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.1.73-log : Database - storm
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`storm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `storm`;

/*Table structure for table `behavior` */

DROP TABLE IF EXISTS `behavior`;

CREATE TABLE `behavior` (
  `customerId` int(32) NOT NULL DEFAULT '0',
  `goodsId` int(32) NOT NULL DEFAULT '0',
  `type` varchar(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `behavior` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `category` */

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`) values (1,'lvzimin'),(2,'hahaha');

/*Table structure for table `customer_tag_weight` */

DROP TABLE IF EXISTS `customer_tag_weight`;

CREATE TABLE `customer_tag_weight` (
  `customerId` int(32) NOT NULL DEFAULT '0',
  `tagName` varchar(50) NOT NULL DEFAULT '',
  `weight` int(32) DEFAULT NULL,
  PRIMARY KEY (`customerId`,`tagName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_tag_weight` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `tags` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`name`,`tags`) values (1,'手机','生活,工具'),(2,'篮球','运动,生活');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

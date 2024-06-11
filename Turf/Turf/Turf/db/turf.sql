/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.22 : Database - turf_booking_ssc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`turf_booking_ssc` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `turf_booking_ssc`;

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `slot_id` int DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `bookings` */

insert  into `bookings`(`book_id`,`user_id`,`slot_id`,`date_time`,`status`) values 
(1,1,3,'12212121','active'),
(17,3,2,'2021-10-2020','pending'),
(16,1,3,'2021-04-13 00:43:08','paid');

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int DEFAULT NULL,
  `sender_type` varchar(50) DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `receiver_type` varchar(50) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`sender_type`,`receiver_id`,`receiver_type`,`message`,`date_time`) values 
(1,1,'turf',2,'user','Hai','322'),
(2,1,'user',1,'turf','Hello','32'),
(3,1,'turf',2,'user','hello','2021-02-10 01:22:47'),
(4,1,'turf',1,'user','yes','2021-02-10 01:23:02');

/*Table structure for table `commision` */

DROP TABLE IF EXISTS `commision`;

CREATE TABLE `commision` (
  `commision_id` int NOT NULL AUTO_INCREMENT,
  `percentage` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`commision_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `commision` */

insert  into `commision`(`commision_id`,`percentage`,`date_time`) values 
(8,'90','2021-02-09 11:38:16');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `complaint` varchar(50) DEFAULT NULL,
  `reply` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`user_id`,`complaint`,`reply`,`date_time`) values 
(1,1,'No food ','Sorry',NULL),
(2,2,'Low Quality','pending',NULL),
(3,1,'nnnnn','pending',NULL),
(4,1,'logib error','pending','2021-04-15'),
(5,1,'login error','pending','2021-04-15'),
(6,1,'login error','pending','2021-04-15'),
(7,1,'stghgb','pending','2021-04-15');

/*Table structure for table `facilities` */

DROP TABLE IF EXISTS `facilities`;

CREATE TABLE `facilities` (
  `facility_id` int NOT NULL AUTO_INCREMENT,
  `turf_id` int DEFAULT NULL,
  `facility` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`facility_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `facilities` */

insert  into `facilities`(`facility_id`,`turf_id`,`facility`,`description`,`image`,`date`) values 
(2,1,'Play Ground','Work Place','static/facility_images/ac8ef78a-c910-4130-93c9-3c9a961d3bffPicture1.jpg','2021-02-09'),
(3,1,'Canteen','Food','static/facility_images/316ce1ed-4788-4597-9658-c758aa2fae07brocoli.jpg','2021-02-09'),
(4,1,'Accomodation','Home','static/facility_images/e2f8572e-c6ea-4fd4-abed-45a83eb9a2c25.png','2021-02-09');

/*Table structure for table `feedbacks` */

DROP TABLE IF EXISTS `feedbacks`;

CREATE TABLE `feedbacks` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `turf_id` int DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `feedbacks` */

insert  into `feedbacks`(`feedback_id`,`user_id`,`turf_id`,`title`,`description`,`date_time`) values 
(1,1,1,'bhhbhbhbh','hhghghghh','67/90/90'),
(2,1,1,'tuii','tuii','2021-04-13 14:06:32'),
(3,1,1,'dghj','dghj','2021-04-13 14:06:54'),
(4,1,1,'ryuu','tuii','2021-04-13 14:15:29'),
(5,1,1,'dhj','fjkk','2021-04-13 14:15:40'),
(6,1,1,'ooo','fhh','2021-04-15 11:17:29');

/*Table structure for table `images` */

DROP TABLE IF EXISTS `images`;

CREATE TABLE `images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `turf_id` int DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `image_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `images` */

insert  into `images`(`image_id`,`turf_id`,`image`,`date_time`,`image_type`) values 
(1,1,'static/ec1dce87-f7af-473e-988b-6086cd70a84e5.png','2021-02-10 00:08:05','game'),
(2,2,'static/9decc5cf-8bf4-4a26-8702-00cb0a25a7305.png','2021-03-05 10:17:04','game'),
(3,2,'static/1765a7e5-9dab-4a7a-b145-e835e8853be4brocoli.jpg','2021-03-15 09:22:22','game');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'ann','ann','turf'),
(2,'admin','admin','admin'),
(3,'turf','turf','turf'),
(4,'ammu','ammu','user'),
(5,'hsha','hahah','user'),
(6,'hsha','hahah','user'),
(7,'abijith','abijith','user');

/*Table structure for table `matches` */

DROP TABLE IF EXISTS `matches`;

CREATE TABLE `matches` (
  `match_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int DEFAULT NULL,
  `opp_user_id` int DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`match_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `matches` */

insert  into `matches`(`match_id`,`book_id`,`opp_user_id`,`status`) values 
(1,1,2,'active'),
(2,1,2,'pending'),
(3,16,3,'pending'),
(4,17,1,'accepted');

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int DEFAULT NULL,
  `payment_mode` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

insert  into `payments`(`payment_id`,`book_id`,`payment_mode`,`date_time`) values 
(1,1,'online','2/2/2'),
(2,16,'online','2021-04-22 12:50:18'),
(3,16,'online','2021-04-22 12:50:21'),
(4,16,'online','2021-04-22 12:50:28'),
(5,16,'online','2021-04-22 12:53:09'),
(6,16,'online','2021-04-22 12:53:34');

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `rate_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `turf_id` int DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `review` varchar(500) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rate_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `ratings` */

insert  into `ratings`(`rate_id`,`user_id`,`turf_id`,`rate`,`review`,`date_time`) values 
(7,1,1,'5.0','sfghsd','2021-04-18 11:35:15');

/*Table structure for table `slots` */

DROP TABLE IF EXISTS `slots`;

CREATE TABLE `slots` (
  `slot_id` int NOT NULL AUTO_INCREMENT,
  `turf_id` int DEFAULT NULL,
  `day` varchar(50) DEFAULT NULL,
  `from_time` varchar(50) DEFAULT NULL,
  `to_time` varchar(50) DEFAULT NULL,
  `amount` decimal(50,0) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`slot_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `slots` */

insert  into `slots`(`slot_id`,`turf_id`,`day`,`from_time`,`to_time`,`amount`,`date_time`) values 
(3,1,'Sunday','15:34','15:39',100,'2021-02-09 15:48:10'),
(2,1,'Thursday','15:34','15:34',100,'2021-02-09 15:50:20');

/*Table structure for table `turfs` */

DROP TABLE IF EXISTS `turfs`;

CREATE TABLE `turfs` (
  `turf_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `owner_first_name` varchar(50) DEFAULT NULL,
  `owner_last_name` varchar(50) DEFAULT NULL,
  `turf_place` varchar(50) DEFAULT NULL,
  `landmark` varchar(50) DEFAULT NULL,
  `pincode` varchar(50) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`turf_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `turfs` */

insert  into `turfs`(`turf_id`,`login_id`,`owner_first_name`,`owner_last_name`,`turf_place`,`landmark`,`pincode`,`latitude`,`longitude`,`phone`,`email`) values 
(1,1,'Ann Treata','Regina T M','Kozhikode','Kochi','688765','98765432101234567','4354334532344433','7012758728','treatspetscorner@gmail.com'),
(2,3,'ammu','turf','turf place','hjhhhj','787878','7778','7887788','7878878799','treatspetscorner@gmail.com');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `house_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `pincode` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`phone`,`email`) values 
(1,4,'Ammu','Kutty','Paliyethara','kkd','43434343','989898989','ann@123.com'),
(2,10,'hh','hh','hhnn','vgvtgvg','878778','787878','bhbhb'),
(3,5,'bhbh','hhg','hghgh','87','87bh78v','87878','878hhjjh'),
(4,6,'wtwywg','shshsh','shshshs','bshsjs','123456','9876543210','abc@gmail.com'),
(5,7,'abijith','nm','abcd','kaloor','653008','9876543210','abijith@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

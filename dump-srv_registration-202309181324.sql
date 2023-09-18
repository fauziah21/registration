-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: srv_registration
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `refreshtoken`
--

DROP TABLE IF EXISTS `refreshtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refreshtoken` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refreshtoken`
--

LOCK TABLES `refreshtoken` WRITE;
/*!40000 ALTER TABLE `refreshtoken` DISABLE KEYS */;
INSERT INTO `refreshtoken` VALUES (-49,'2023-09-18 04:28:32.000000','959b49f1-3166-4937-8413-871afcde8959',7),(-48,'2023-09-18 04:34:27.000000','445c3298-36e1-4d2a-ae81-4f84b62a86b8',7),(1,'2023-09-18 04:54:44.000000','c2508a1c-3f61-46a8-89b7-05eba7f78479',7);
/*!40000 ALTER TABLE `refreshtoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refreshtoken_seq`
--

DROP TABLE IF EXISTS `refreshtoken_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refreshtoken_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refreshtoken_seq`
--

LOCK TABLES `refreshtoken_seq` WRITE;
/*!40000 ALTER TABLE `refreshtoken_seq` DISABLE KEYS */;
INSERT INTO `refreshtoken_seq` VALUES (250);
/*!40000 ALTER TABLE `refreshtoken_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_account`
--

DROP TABLE IF EXISTS `regs_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(100) NOT NULL,
  `account_email` varchar(100) NOT NULL,
  `account_password` text NOT NULL,
  `account_phone` varchar(15) NOT NULL,
  `account_credit_card` varchar(100) DEFAULT NULL,
  `account_card_cvv` varchar(100) DEFAULT NULL,
  `account_card_exp` date DEFAULT NULL,
  `account_card_name` varchar(100) DEFAULT NULL,
  `account_status` varchar(50) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_account`
--

LOCK TABLES `regs_account` WRITE;
/*!40000 ALTER TABLE `regs_account` DISABLE KEYS */;
INSERT INTO `regs_account` VALUES (9,'fauzia','fauziahaulia21@gmail.com','$2a$10$rJ4q12KTyBQz6fKYuLZhgOuK5RIZL.lA1EpazcSrtoyfHlkBklb66','082183958200','$2a$10$Pu4Yt1C9KEuSUy/ZAXx7SuSYSsK/nBIwiyegNibGIkp3aSzRoJugO','$2a$10$1YLaGbceiJiJ4BU3gqBgZuIkfFPWh2oNOEEyAAuu4Q0Dsd/YjArW2','2023-10-01','$2a$10$Ucygjq0oBPhCb3AVG4/wg.tXYX8k56V4Zs69WAymr.VuZlFO8z/zq','TERDAFTAR','2023-09-18 12:49:16','2023-09-18 12:49:16');
/*!40000 ALTER TABLE `regs_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_otp`
--

DROP TABLE IF EXISTS `regs_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_otp` (
  `otp_id` int(11) NOT NULL AUTO_INCREMENT,
  `otp_email` varchar(50) NOT NULL,
  `otp_code` varchar(6) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `otp_expired_at` datetime DEFAULT NULL,
  PRIMARY KEY (`otp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_otp`
--

LOCK TABLES `regs_otp` WRITE;
/*!40000 ALTER TABLE `regs_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `regs_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_payment`
--

DROP TABLE IF EXISTS `regs_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `payment_total` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `payment_status` varchar(100) DEFAULT NULL,
  `payment_subscription_id` int(11) NOT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_payment`
--

LOCK TABLES `regs_payment` WRITE;
/*!40000 ALTER TABLE `regs_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `regs_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_subs_detail`
--

DROP TABLE IF EXISTS `regs_subs_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_subs_detail` (
  `subs_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `subscription_id` int(11) NOT NULL,
  `subs_detail_desc` text DEFAULT NULL,
  `subs_detail_duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`subs_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_subs_detail`
--

LOCK TABLES `regs_subs_detail` WRITE;
/*!40000 ALTER TABLE `regs_subs_detail` DISABLE KEYS */;
INSERT INTO `regs_subs_detail` VALUES (1,1,'Skipping',30),(2,1,'Barbel',20),(3,2,'Lari',60),(4,2,'dumbell',30);
/*!40000 ALTER TABLE `regs_subs_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_subs_member`
--

DROP TABLE IF EXISTS `regs_subs_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_subs_member` (
  `subs_member_id` int(11) NOT NULL AUTO_INCREMENT,
  `subscription_id` int(11) NOT NULL,
  `subs_member_status` char(1) DEFAULT NULL,
  `subs_member_session` int(11) DEFAULT NULL,
  `subs_member_account_id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`subs_member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_subs_member`
--

LOCK TABLES `regs_subs_member` WRITE;
/*!40000 ALTER TABLE `regs_subs_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `regs_subs_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_subs_schedule`
--

DROP TABLE IF EXISTS `regs_subs_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_subs_schedule` (
  `subs_schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `subscription_id` int(11) NOT NULL,
  `subs_schedule` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`subs_schedule_id`),
  KEY `regs_subs_schedule_FK` (`subscription_id`),
  CONSTRAINT `regs_subs_schedule_FK` FOREIGN KEY (`subscription_id`) REFERENCES `regs_subscription` (`sub_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_subs_schedule`
--

LOCK TABLES `regs_subs_schedule` WRITE;
/*!40000 ALTER TABLE `regs_subs_schedule` DISABLE KEYS */;
INSERT INTO `regs_subs_schedule` VALUES (1,1,'Senin'),(2,1,'Kamis'),(3,2,'Rabu'),(4,2,'Sabtu');
/*!40000 ALTER TABLE `regs_subs_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regs_subscription`
--

DROP TABLE IF EXISTS `regs_subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regs_subscription` (
  `sub_id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_menu` varchar(100) NOT NULL,
  `sub_price` int(11) NOT NULL,
  `sub_duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`sub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regs_subscription`
--

LOCK TABLES `regs_subscription` WRITE;
/*!40000 ALTER TABLE `regs_subscription` DISABLE KEYS */;
INSERT INTO `regs_subscription` VALUES (1,'Overweight dan Obesity',70000,8),(2,'Menjaga Kebugaran',50000,8);
/*!40000 ALTER TABLE `regs_subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` enum('ROLE_ADMIN','ROLE_USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (5,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'srv_registration'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-18 13:24:01

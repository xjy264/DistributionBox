-- MySQL dump 10.13  Distrib 9.6.0, for macos15.7 (arm64)
--
-- Host: 127.0.0.1    Database: distributionbox
-- ------------------------------------------------------
-- Server version	9.6.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '483fb746-00cd-11f1-8e15-25359a6630bc:1-290';

--
-- Table structure for table `sys_inspection`
--

DROP TABLE IF EXISTS `sys_inspection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_inspection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inspection_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `guardian_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `inspection_time` datetime DEFAULT NULL,
  `first_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `second_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fourth_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fifth_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `appearance_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `earlier_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `later_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `switch_model` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `current_rating` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `traverse_section` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `supply_voltage` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `a_starting` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `a_running` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `b_starting` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `b_running` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `c_starting` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `c_running` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_inspection`
--

LOCK TABLES `sys_inspection` WRITE;
/*!40000 ALTER TABLE `sys_inspection` DISABLE KEYS */;
INSERT INTO `sys_inspection` VALUES (1,'BOX-AUTO-001','AUTO巡检员',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'AUTO_TEST'),(2,NULL,'u1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,'巡检员-1771136820','监护-1771136820','2026-02-15 10:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'联调巡检');
/*!40000 ALTER TABLE `sys_inspection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_inspection_box`
--

DROP TABLE IF EXISTS `sys_inspection_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_inspection_box` (
  `inspection_id` int NOT NULL,
  `box_id` int NOT NULL,
  PRIMARY KEY (`inspection_id`,`box_id`),
  KEY `idx_inspection_box_box` (`box_id`),
  CONSTRAINT `fk_inspection_box_inspection` FOREIGN KEY (`inspection_id`) REFERENCES `sys_inspection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='inspection-box relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_inspection_box`
--

LOCK TABLES `sys_inspection_box` WRITE;
/*!40000 ALTER TABLE `sys_inspection_box` DISABLE KEYS */;
INSERT INTO `sys_inspection_box` VALUES (1,1),(2,6),(3,7),(3,8);
/*!40000 ALTER TABLE `sys_inspection_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_emergency_repair`
--

DROP TABLE IF EXISTS `sys_emergency_repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_emergency_repair` (
  `id` int NOT NULL AUTO_INCREMENT,
  `report_user` varchar(100) DEFAULT NULL COMMENT 'report user',
  `report_time` datetime DEFAULT NULL COMMENT 'report time',
  `fix_user` varchar(100) DEFAULT NULL COMMENT 'repair user',
  `fix_time` datetime DEFAULT NULL COMMENT 'repair time',
  `components` varchar(255) DEFAULT NULL COMMENT 'components replaced',
  `fault_phenomenon` varchar(255) DEFAULT NULL COMMENT 'fault phenomenon',
  `fault_reason` varchar(255) DEFAULT NULL COMMENT 'fault reason',
  `fix_process` text COMMENT 'fix process',
  `remark` varchar(255) DEFAULT NULL COMMENT 'remark',
  `first_url` varchar(500) DEFAULT NULL COMMENT 'image1',
  `second_url` varchar(500) DEFAULT NULL COMMENT 'image2',
  `third_url` varchar(500) DEFAULT NULL COMMENT 'image3',
  `fourth_url` varchar(500) DEFAULT NULL COMMENT 'image4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='emergency repair';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_emergency_repair`
--

LOCK TABLES `sys_emergency_repair` WRITE;
/*!40000 ALTER TABLE `sys_emergency_repair` DISABLE KEYS */;
INSERT INTO `sys_emergency_repair` VALUES (1,'报修-1771136632','2026-02-14 16:00:00','抢修-1771136632','2026-02-14 16:00:00','联调更换','联调故障','联调原因','联调处理','联调抢修',NULL,NULL,NULL,NULL),(2,'报修-1771136820','2026-02-14 16:00:00','抢修-1771136820','2026-02-14 16:00:00','联调更换','联调故障','联调原因','联调处理','联调抢修',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_emergency_repair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_emergency_repair_box`
--

DROP TABLE IF EXISTS `sys_emergency_repair_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_emergency_repair_box` (
  `emergency_repair_id` int NOT NULL,
  `box_id` int NOT NULL,
  PRIMARY KEY (`emergency_repair_id`,`box_id`),
  KEY `idx_repair_box_box` (`box_id`),
  CONSTRAINT `fk_repair_box_repair` FOREIGN KEY (`emergency_repair_id`) REFERENCES `sys_emergency_repair` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='repair-box relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_emergency_repair_box`
--

LOCK TABLES `sys_emergency_repair_box` WRITE;
/*!40000 ALTER TABLE `sys_emergency_repair_box` DISABLE KEYS */;
INSERT INTO `sys_emergency_repair_box` VALUES (1,5),(1,6),(2,7),(2,8);
/*!40000 ALTER TABLE `sys_emergency_repair_box` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-20 10:16:43

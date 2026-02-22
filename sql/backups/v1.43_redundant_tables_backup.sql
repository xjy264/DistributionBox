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

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '483fb746-00cd-11f1-8e15-25359a6630bc:1-302';

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `score` int DEFAULT NULL,
  `times` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course`
--

DROP TABLE IF EXISTS `student_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_course` (
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course`
--

LOCK TABLES `student_course` WRITE;
/*!40000 ALTER TABLE `student_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_check`
--

DROP TABLE IF EXISTS `sys_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_check` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `state` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_check`
--

LOCK TABLES `sys_check` WRITE;
/*!40000 ALTER TABLE `sys_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_fix`
--

DROP TABLE IF EXISTS `sys_fix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_fix` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `result` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `box_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_fix`
--

LOCK TABLES `sys_fix` WRITE;
/*!40000 ALTER TABLE `sys_fix` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_fix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_maintenance`
--

DROP TABLE IF EXISTS `sys_maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_maintenance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `report_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `report_time` datetime DEFAULT NULL,
  `fix_user` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fix_time` datetime DEFAULT NULL,
  `components` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fault_phenomenon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fault_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fix_process` text COLLATE utf8mb4_general_ci,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `first_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `second_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `third_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fourth_url` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_maintenance`
--

LOCK TABLES `sys_maintenance` WRITE;
/*!40000 ALTER TABLE `sys_maintenance` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_record`
--

DROP TABLE IF EXISTS `sys_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `terminal_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `temperature` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `running_current` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `starting_current` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `leakage` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `voltage` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `insulation` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ground` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `number` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pid` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_record`
--

LOCK TABLES `sys_record` WRITE;
/*!40000 ALTER TABLE `sys_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tax`
--

DROP TABLE IF EXISTS `sys_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tax` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pre` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `after` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `rate` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fund` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tax`
--

LOCK TABLES `sys_tax` WRITE;
/*!40000 ALTER TABLE `sys_tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tax` ENABLE KEYS */;
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

-- Dump completed on 2026-02-20 10:28:19

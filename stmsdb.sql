-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: stms
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tutor_session_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tutor_session_id` (`tutor_session_id`),
  CONSTRAINT `forum_ibfk_1` FOREIGN KEY (`tutor_session_id`) REFERENCES `tutor_session` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum_question`
--

DROP TABLE IF EXISTS `forum_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_user_id` bigint DEFAULT NULL,
  `forum_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author_user_id` (`author_user_id`),
  KEY `forum_id` (`forum_id`),
  CONSTRAINT `forum_question_ibfk_1` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `forum_question_ibfk_2` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_question`
--

LOCK TABLES `forum_question` WRITE;
/*!40000 ALTER TABLE `forum_question` DISABLE KEYS */;
INSERT INTO `forum_question` VALUES (5,'sscdscsdcscdsds','2025-04-08 15:38:18.733065','Sdasda',1,1);
/*!40000 ALTER TABLE `forum_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum_reply`
--

DROP TABLE IF EXISTS `forum_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_reply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `author_user_id` bigint DEFAULT NULL,
  `parent_reply_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkouuwmoj07029ckmsjmnb58kk` (`parent_reply_id`),
  KEY `question_id` (`question_id`),
  KEY `author_user_id` (`author_user_id`),
  CONSTRAINT `FKkouuwmoj07029ckmsjmnb58kk` FOREIGN KEY (`parent_reply_id`) REFERENCES `forum_reply` (`id`),
  CONSTRAINT `forum_reply_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `forum_question` (`id`) ON DELETE CASCADE,
  CONSTRAINT `forum_reply_ibfk_2` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_reply`
--

LOCK TABLES `forum_reply` WRITE;
/*!40000 ALTER TABLE `forum_reply` DISABLE KEYS */;
INSERT INTO `forum_reply` VALUES (8,'fudged','2025-04-08 15:38:36.569884',1,NULL,5),(9,'RWRW','2025-04-08 15:42:38.278884',4,NULL,5);
/*!40000 ALTER TABLE `forum_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prev_quiz_result`
--

DROP TABLE IF EXISTS `prev_quiz_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prev_quiz_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `prev_score` double DEFAULT NULL,
  `prev_submitted_at` datetime(6) DEFAULT NULL,
  `quiz_quiz_id` bigint DEFAULT NULL,
  `user_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_user_id` (`user_user_id`),
  KEY `quiz_quiz_id` (`quiz_quiz_id`),
  CONSTRAINT `prev_quiz_result_ibfk_1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `prev_quiz_result_ibfk_2` FOREIGN KEY (`quiz_quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prev_quiz_result`
--

LOCK TABLES `prev_quiz_result` WRITE;
/*!40000 ALTER TABLE `prev_quiz_result` DISABLE KEYS */;
INSERT INTO `prev_quiz_result` VALUES (8,0,'2025-04-08 15:43:22.737513',4,4);
/*!40000 ALTER TABLE `prev_quiz_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `quiz_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `quiz_id` (`quiz_id`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (7,'d','SRS','MULTIPLE_CHOICE',4);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_options`
--

DROP TABLE IF EXISTS `question_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_options` (
  `question_id` bigint NOT NULL,
  `options` varchar(255) DEFAULT NULL,
  KEY `question_id` (`question_id`),
  CONSTRAINT `question_options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_options`
--

LOCK TABLES `question_options` WRITE;
/*!40000 ALTER TABLE `question_options` DISABLE KEYS */;
INSERT INTO `question_options` VALUES (7,'a'),(7,' b'),(7,' c'),(7,' d');
/*!40000 ALTER TABLE `question_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `quiz_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `tutor_session_id` bigint NOT NULL,
  PRIMARY KEY (`quiz_id`),
  KEY `tutor_session_id` (`tutor_session_id`),
  CONSTRAINT `quiz_ibfk_1` FOREIGN KEY (`tutor_session_id`) REFERENCES `tutor_session` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (4,'Q1',1);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_result`
--

DROP TABLE IF EXISTS `quiz_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answers` int NOT NULL,
  `score` double DEFAULT NULL,
  `submitted_at` datetime(6) DEFAULT NULL,
  `quiz_quiz_id` bigint DEFAULT NULL,
  `student_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_user_id` (`student_user_id`),
  KEY `quiz_quiz_id` (`quiz_quiz_id`),
  CONSTRAINT `quiz_result_ibfk_1` FOREIGN KEY (`student_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `quiz_result_ibfk_2` FOREIGN KEY (`quiz_quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_result`
--

LOCK TABLES `quiz_result` WRITE;
/*!40000 ALTER TABLE `quiz_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_result_answer_map`
--

DROP TABLE IF EXISTS `quiz_result_answer_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_result_answer_map` (
  `quiz_result_id` bigint NOT NULL,
  `answer_map` varchar(255) DEFAULT NULL,
  `answer_map_key` bigint NOT NULL,
  PRIMARY KEY (`quiz_result_id`,`answer_map_key`),
  CONSTRAINT `quiz_result_answer_map_ibfk_1` FOREIGN KEY (`quiz_result_id`) REFERENCES `quiz_result` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_result_answer_map`
--

LOCK TABLES `quiz_result_answer_map` WRITE;
/*!40000 ALTER TABLE `quiz_result_answer_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_result_answer_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'FACULTY'),(2,'STUDENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor_session`
--

DROP TABLE IF EXISTS `tutor_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor_session` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_code` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `day_of_week` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `meeting_medium` varchar(255) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `creator_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgm7gc9byr4rpctiur045vcumd` (`creator_id`),
  CONSTRAINT `FKgm7gc9byr4rpctiur045vcumd` FOREIGN KEY (`creator_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_session`
--

LOCK TABLES `tutor_session` WRITE;
/*!40000 ALTER TABLE `tutor_session` DISABLE KEYS */;
INSERT INTO `tutor_session` VALUES (1,'TC1','Test Course 1','Monday','lorem ipsum dolor set amet ','11:30:00','AS 303','Physical','10:30:00',1),(2,'TC2','Test Course 2','Friday','lorem ipsum dolor set amet','14:25:00','Classroom 3','Physical','13:00:00',1),(3,'TC3','Test Course 3','Wednesday','lorem ipsum dolor set amet','17:30:00','meet.google.com/?2da1','Online','16:30:00',1);
/*!40000 ALTER TABLE `tutor_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor_session_students`
--

DROP TABLE IF EXISTS `tutor_session_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor_session_students` (
  `tutor_session_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  PRIMARY KEY (`tutor_session_id`,`student_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `tutor_session_students_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `tutor_session_students_ibfk_2` FOREIGN KEY (`tutor_session_id`) REFERENCES `tutor_session` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor_session_students`
--

LOCK TABLES `tutor_session_students` WRITE;
/*!40000 ALTER TABLE `tutor_session_students` DISABLE KEYS */;
INSERT INTO `tutor_session_students` VALUES (1,4);
/*!40000 ALTER TABLE `tutor_session_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_verified` bit(1) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otp_expiry_time` bigint DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'achonma.tanko@aun.edu.ng','Achonma',_binary '','Tanko',NULL,NULL,'$2a$10$lsPXY/ToqBR6zYO/kx2L9Ol9rx5KwRJ/TaS3/nHA98mzOgdVUnaDC','+2349030634372','A00022937'),(4,'debbie@yopmail.com','Debbie',_binary '','Galumje',NULL,NULL,'$2a$10$68fpH3G0Zm2XpDWZysQyUuCIVAwfcaFOrFxcGuFNioWUWYeB2vp2q','+2348032345849','A00024256');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(4,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-22  8:59:29

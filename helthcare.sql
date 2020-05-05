-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 05, 2020 at 02:24 AM
-- Server version: 5.7.26
-- PHP Version: 7.0.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `helthcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE IF NOT EXISTS `appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `paid` bit(1) NOT NULL,
  `patient_id` bigint(20) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmgspx6jj6lf80ayfwccy2n63` (`patient_id`),
  KEY `FK6tvfdmt5t7txdorh47qin7rp` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `date`, `number`, `paid`, `patient_id`, `session_id`) VALUES
(2, '2020-05-24', 1, b'1', 1, 2),
(3, '2020-03-29', 1, b'0', 1, 3),
(4, '2020-05-17', 0, b'0', 1, 1),
(5, '2020-05-24', 1, b'0', 1, 1),
(6, '2020-05-17', 1, b'0', 1, 1),
(7, '2020-05-17', 1, b'0', 1, 1),
(8, '2020-05-10', 1, b'0', 1, 1),
(9, '2020-05-10', 1, b'0', 1, 2),
(10, '2020-05-10', 2, b'0', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `doctor_session`
--

DROP TABLE IF EXISTS `doctor_session`;
CREATE TABLE IF NOT EXISTS `doctor_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `max_count` int(11) DEFAULT NULL,
  `doctor_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfupff8d7933cjqxwk26w48if` (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctor_session`
--

INSERT INTO `doctor_session` (`id`, `day`, `max_count`, `doctor_id`, `description`, `price`) VALUES
(1, 'SUNDAY', 22, 4, 'Sunday @ 8am', 2000),
(2, 'SUNDAY', 22, 4, 'Sunday @ 1pm', 4000),
(3, 'SUNDAY', 22, 4, 'Sundat @ 5.30pm', 3600),
(4, 'MONDAY', 10, 4, 'Monday @ 5.30pm', 2500),
(5, 'TUESDAY', 10, 4, 'Tuseday @ 6pm', 2500),
(6, 'WEDNESDAY', 1, 4, 'test', 123);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `price` double NOT NULL,
  `appinment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa6foqf20wgjvp4pjdo3xsp2wp` (`appinment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `date`, `price`, `appinment_id`) VALUES
(1, '2020-03-27', 4000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_DOCTOR'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `password`, `role_id`) VALUES
(1, 'user@user.com', 'Test User name', '202cb962ac59075b964b07152d234b70', 1),
(4, 'doc@doc.com', 'Test Doctor name', '202cb962ac59075b964b07152d234b70', 2),
(5, 'admin@admin.com', 'Admin name', '202cb962ac59075b964b07152d234b70', 3),
(6, 'doc2@doc.com', 'Doc 2 name', '202cb962ac59075b964b07152d234b70', 1),
(8, 'doc3@user.com', 'doc3', '202cb962ac59075b964b07152d234b70', 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `FK6tvfdmt5t7txdorh47qin7rp` FOREIGN KEY (`session_id`) REFERENCES `doctor_session` (`id`),
  ADD CONSTRAINT `FKkmgspx6jj6lf80ayfwccy2n63` FOREIGN KEY (`patient_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `doctor_session`
--
ALTER TABLE `doctor_session`
  ADD CONSTRAINT `FKfupff8d7933cjqxwk26w48if` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FKa6foqf20wgjvp4pjdo3xsp2wp` FOREIGN KEY (`appinment_id`) REFERENCES `appointment` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

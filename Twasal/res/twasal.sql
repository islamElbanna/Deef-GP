-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 02, 2013 at 09:51 AM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `twasal`
--
CREATE DATABASE `twasal` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `twasal`;

-- --------------------------------------------------------

--
-- Table structure for table `wordsmapping`
--

CREATE TABLE IF NOT EXISTS `wordsmapping` (
  `wordkey` varchar(50) NOT NULL,
  `translation` text CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`wordkey`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1256;

--
-- Dumping data for table `wordsmapping`
--

INSERT INTO `wordsmapping` (`wordkey`, `translation`) VALUES
('ذهب', '100,lradius,-172,-145,418|104,lradius,-189,-153,472|108,lradius,-172,-145,418|112,lradius,-172,-145,418|116,lradius,-189,-153,472|120,lradius,-172,-145,418|play,1'),
('مدرسة', '75,lhumerus,-65,37,-82|80,lradius,-339,-30,284|80,rhumerus,-37,-22,14|80,rradius,1,25,87|80,l4Dist,-44,-15,-9|80,l3Dist,-63,-18,-3|80,l4Prox,-44,-15,-9|80,l3Prox,-63,-18,-3|80,lradius,-79,-16,307|85,lradius,-79,-32,257|90,lradius,-79,-40,257|95,lradius,-79,-32,257|100,lradius,-79,-40,257|'),
('ولد', '10,lradius,35,17,-6|10,lhand,23,5,-2|15,lradius,51,17,-6|20,lradius,35,17,-6|24,lradius,51,17,-6|28,lradius,35,17,-6|32,lradius,51,17,-6|');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

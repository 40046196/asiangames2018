CREATE DATABASE `asiangames2018` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `country` (
  `countryId` varchar(3) NOT NULL,
  `countryName` varchar(45) DEFAULT NULL,
  `countryFlag` blob,
  PRIMARY KEY (`countryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sport` (
  `sportId` varchar(3) NOT NULL,
  `sportName` varchar(45) DEFAULT NULL,
  `sportIcon` blob,
  `sportImage` blob,
  PRIMARY KEY (`sportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `athlete` (
  `athleteId` varchar(7) NOT NULL,
  `athleteName` varchar(25) NOT NULL,
  `familyName` varchar(25) DEFAULT NULL,
  `birthdate` varchar(10) NOT NULL,
  `countryId` varchar(3) NOT NULL,
  `sportId` varchar(7) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `beginning` varchar(200) DEFAULT NULL,
  `coach` varchar(200) DEFAULT NULL,
  `memorable` varchar(200) DEFAULT NULL,
  `influence` varchar(200) DEFAULT NULL,
  `nickname` varchar(200) DEFAULT NULL,
  `language` varchar(200) DEFAULT NULL,
  `hobbies` varchar(200) DEFAULT NULL,
  `photo` blob, 
  PRIMARY KEY (`athleteId`,`countryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `athleteHighlight` (
  `athleteId` varchar(7) NOT NULL,
  `league` varchar(50) NOT NULL,
  `rank` varchar(10) NOT NULL,
  `event` varchar(3) NOT NULL,
  `year` varchar(7) NOT NULL,
  `location` varchar(30) NOT NULL,
  PRIMARY KEY (`league`, `athleteId`, `year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `athleteSocial` (
  `athleteId` varchar(7) NOT NULL,
  `socialmedia` varchar(50) NOT NULL,
  PRIMARY KEY (`athleteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



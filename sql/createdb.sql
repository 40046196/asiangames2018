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
  `athleteName` varchar(100) DEFAULT NULL,
  `familyName` varchar(100) DEFAULT NULL,
  `birthdate` varchar(10) DEFAULT NULL,
  `birthCity` varchar(100) DEFAULT NULL,
  `birthCountry` varchar(100) DEFAULT NULL,
  `countryId` varchar(3) DEFAULT NULL,
  `sportId` varchar(7) DEFAULT NULL,
  `height` int(3) DEFAULT '0',
  `weight` int(3) DEFAULT '0',
  `photo` mediumblob,
  PRIMARY KEY (`athleteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `athletebiography` (
  `athleteId` varchar(7) NOT NULL,
  `beginning` varchar(500) DEFAULT NULL,
  `debut` varchar(500) DEFAULT NULL,
  `reason` varchar(1000) DEFAULT NULL,
  `coach` varchar(500) DEFAULT NULL,
  `training` varchar(500) DEFAULT NULL,
  `ambition` varchar(500) DEFAULT NULL,
  `awards` varchar(500) DEFAULT NULL,
  `hero` varchar(500) DEFAULT NULL,
  `memorable` varchar(1000) DEFAULT NULL,
  `influence` varchar(500) DEFAULT NULL,
  `nickname` varchar(500) DEFAULT NULL,
  `relatives` varchar(500) DEFAULT NULL,
  `injuries` varchar(1000) DEFAULT NULL,
  `education` varchar(500) DEFAULT NULL,
  `language` varchar(200) DEFAULT NULL,
  `hobbies` varchar(500) DEFAULT NULL,
  `additionalInformation` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`athleteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `athletehighlight` (
  `athleteId` varchar(7) NOT NULL,
  `league` varchar(100) NOT NULL,
  `rank` varchar(100) NOT NULL,
  `event` varchar(100) NOT NULL,
  `year` varchar(25) NOT NULL,
  `location` varchar(100) NOT NULL,
  `result` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`league`,`athleteId`,`rank`,`event`,`year`,`location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `athletesocial` (
  `athleteId` varchar(7) NOT NULL,
  `socialmedia` varchar(100) NOT NULL,
  PRIMARY KEY (`athleteId`,`socialmedia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `athleteMedals` (
  `athleteId` varchar(7) NOT NULL,
  `gold` int(1) DEFAULT '0',
  `silver` int(1) DEFAULT '0',
  `bronze` int(1) DEFAULT '0',
  PRIMARY KEY (`athleteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--  THE STORE PROCEDURE 
delimiter $$
DROP PROCEDURE IF EXISTS insertAthlete$$

CREATE PROCEDURE insertAthlete
( IN athleteId varchar(7)
, IN athleteName VARCHAR(50)
, IN familyName VARCHAR(50)
, IN birthDate VARCHAR(10)
, IN birthCity VARCHAR(20)
, IN birthCountry VARCHAR(20)
, IN countryId VARCHAR(3) 
, IN sportId VARCHAR(2)
, IN height INT
, IN weight INT
, IN photo mediumblob
, IN gold INT
, IN silver INT
, IN bronze INT
, IN beginning VARCHAR(200)
, IN debut VARCHAR(200)
, IN reason VARCHAR(300)
, IN coach VARCHAR(200)
, IN training VARCHAR(200)
, IN ambition VARCHAR(200)
, IN awards VARCHAR(200)
, IN hero VARCHAR(200)
, IN memorable VARCHAR(200)
, IN influence VARCHAR(200)
, IN nickname VARCHAR(200)
, IN relatives VARCHAR(200)
, IN injuries VARCHAR(200)
, IN education VARCHAR(200)
, IN language VARCHAR(200)
, IN hobbies VARCHAR(200)
, IN additionalInformation VARCHAR(500))
BEGIN

INSERT IGNORE INTO athlete (
athleteId
, athleteName
, familyName
, birthDate
, birthCity
, birthCountry
, countryId
, sportId
, height
, weight
, photo)

    VALUES (athleteId
, athleteName
, familyName
, birthDate
, birthCity
, birthCountry
, countryId
, sportId
, height
, weight
, photo);

INSERT INTO athletemedals (
athleteId
, gold
, silver
, bronze)
    VALUES 
(athleteId
, gold
, silver
, bronze)  
ON DUPLICATE KEY 
UPDATE gold = gold, 
  silver=silver,
  bronze=bronze;

INSERT IGNORE INTO athletebiography (
athleteId
, beginning
, debut
, reason
, coach
, training
, ambition
, awards
, hero
, memorable
, influence
, nickname
, relatives
, injuries
, education
, language
, hobbies
, additionalInformation)
   VALUES 
(athleteId
, beginning
, debut
, reason
, coach
, training
, ambition
, awards
, hero
, memorable
, influence
, nickname
, relatives
, injuries
, education
, language
, hobbies
, additionalInformation);


END$$
# change the delimiter back to semicolon
DELIMITER ;



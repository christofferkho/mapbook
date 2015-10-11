CREATE DATABASE `cs123a` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `osmuser` varchar(255) DEFAULT NULL,
  `osmpass` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `iduser_UNIQUE` (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `location` (
  `idlocation` int(11) NOT NULL AUTO_INCREMENT,
  `locname` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`idlocation`),
  UNIQUE KEY `idlocation_UNIQUE` (`idlocation`),
  KEY `iduser_idx` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `path` (
  `idpath` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `idlocation` int(11) NOT NULL,
  PRIMARY KEY (`idpath`),
  UNIQUE KEY `idpath_UNIQUE` (`idpath`),
  KEY `idlocation_idx` (`idlocation`),
  CONSTRAINT `idlocation` FOREIGN KEY (`idlocation`) REFERENCES `location` (`idlocation`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP SCHEMA IF EXISTS world;
DROP SCHEMA IF EXISTS sakila;
DROP SCHEMA IF EXISTS userdetails;
DROP SCHEMA IF EXISTS details;

CREATE SCHEMA details;

CREATE TABLE details . doctors (
  `DID` INT NOT NULL AUTO_INCREMENT,
  `F_Name` VARCHAR(45) NULL,
  `L_Name` VARCHAR(45) NULL,
  `Username` VARCHAR(45) NULL,
  `Password` VARCHAR(45) NULL,
  PRIMARY KEY (`DID`),
  UNIQUE INDEX `DID_UNIQUE` (`DID` ASC) VISIBLE);

CREATE TABLE details . patients (
  `PID` INT NOT NULL AUTO_INCREMENT,
  `F_Name` VARCHAR(45) NULL,
  `L_Name` VARCHAR(45) NULL,
  `Username` VARCHAR(45) NULL,
  `Password` VARCHAR(45) NULL,
  PRIMARY KEY (`PID`),
  UNIQUE INDEX `PID_UNIQUE` (`PID` ASC) VISIBLE);

CREATE TABLE details . bookings (
    `BID` INT NOT NULL AUTO_INCREMENT,
    `DID` INT NOT NULL,
    `PID` INT NOT NULL,
    `Date` VARCHAR(45) NULL,
    PRIMARY KEY (`BID`),
    FOREIGN KEY (`DID`) REFERENCES details . doctors (`DID`),
    FOREIGN KEY (`PID`) REFERENCES details . patients (`PID`),
    UNIQUE INDEX `BID_UNIQUE` (`BID` ASC) VISIBLE);

INSERT INTO details . doctors
VALUES 
(001, "andrew", "meyer", "andrew03meyer", SHA("pass")),
(002, "peter", "smith", "ps1", SHA("pass")),
(003, "peter", "hughes", "ph1", SHA("pass")),
(004, "cal", "sherv", "callum", SHA("pass")),
(005, "john", "doe", "jd1", SHA("pass")),
(006, "louis", "theobald", "lt1", SHA("egg"));

INSERT INTO details . patients
VALUES 
(001, "amber", "smith", "as1", SHA("pass")),
(002, "peter", "smith", "ps1", SHA("pass")),
(003, "scarlett", "johns", "ph1", SHA("pass")),
(004, "eep", "pope", "callum", SHA("pass")),
(005, "john", "doe", "jd1", SHA("pass")),
(006, "louis", "theobald", "lt1", SHA("egg"));

INSERT INTO details . bookings
VALUES
(001, 001, 001, "02-2024"),
(002, 001, 003, "02-2024"),
(003, 001, 005, "03-2024");
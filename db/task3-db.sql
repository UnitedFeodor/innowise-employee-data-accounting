-- MySQL Workbench Synchronization
-- Generated: 2023-03-16 23:29
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: fedor

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER SCHEMA `employee-data-accounting`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;

ALTER TABLE `employee-data-accounting`.`employee` 
DROP FOREIGN KEY `fk_employee_companies1`;

ALTER TABLE `employee-data-accounting`.`role` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ,
CHANGE COLUMN `r_id` `r_id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `employee-data-accounting`.`employee` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ,
CHANGE COLUMN `e_id` `e_id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `employee-data-accounting`.`company` 
CHARACTER SET = utf8 , COLLATE = utf8_general_ci ,
CHANGE COLUMN `c_id` `c_id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `employee-data-accounting`.`employee` 
DROP FOREIGN KEY `fk_employee_role`;

ALTER TABLE `employee-data-accounting`.`employee` ADD CONSTRAINT `fk_employee_role`
  FOREIGN KEY (`e_role_id`)
  REFERENCES `employee-data-accounting`.`role` (`r_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_employee_companies1`
  FOREIGN KEY (`e_company_id`)
  REFERENCES `employee-data-accounting`.`company` (`c_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

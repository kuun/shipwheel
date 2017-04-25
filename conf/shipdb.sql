USE shipdb;

DROP TABLE IF EXISTS `ship_user` CASCADE;

CREATE TABLE `ship_user` (
  `id`       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`     VARCHAR(36)  NOT NULL UNIQUE,
  `password` VARCHAR(128) NOT NULL, -- value is sha256sum
  `is_admin` BOOL         NOT NULL
);
INSERT INTO `ship_user` (`name`, `password`, `is_admin`)
VALUES ('admin', '2660fd05954ed585a9b3b43ec589ee018f0e211ecb7e474e5ee369d0785d0bea', TRUE);

use shipdb;

DROP TABLE IF EXISTS `ship_user` CASCADE;

-- password value is sha256sum
CREATE TABLE ship_user (
  `id`       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`     VARCHAR(32)  NOT NULL UNIQUE,
  `password` VARCHAR(128) NOT NULL,
  `is_admin` BOOL         NOT NULL
);
INSERT INTO `ship_user` (`name`, `password`, `is_admin`)
VALUES ('admin', 'fc9a0fbd8bcb2e5a50e629163886ce78b45372bd6e3bfe22b6b051a44e3d6c25', TRUE);


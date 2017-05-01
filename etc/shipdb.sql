use shipdb;

DROP TABLE IF EXISTS `ship_user` CASCADE;
DROP TABLE IF EXISTS `ship_nic_addr` CASCADE;
DROP TABLE IF EXISTS `ship_nic_route` CASCADE;
DROP TABLE IF EXISTS `ship_node_nic` CASCADE;
DROP TABLE IF EXISTS `ship_node` CASCADE;

-- password value is sha256sum
CREATE TABLE `ship_user` (
  `id`       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`     VARCHAR(32)  NOT NULL UNIQUE,
  `password` VARCHAR(128) NOT NULL,
  `is_admin` BOOL         NOT NULL
);
INSERT INTO `ship_user` (`name`, `password`, `is_admin`)
VALUES ('admin', 'fc9a0fbd8bcb2e5a50e629163886ce78b45372bd6e3bfe22b6b051a44e3d6c25', TRUE);

CREATE TABLE `ship_node` (
  `id` INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ip` VARCHAR(20) NOT NULL
);
INSERT INTO `ship_node` (ip) VALUES ('10.0.0.1');
INSERT INTO `ship_node` (ip) VALUES ('10.0.0.2');

CREATE TABLE `ship_node_nic` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`    VARCHAR(20) NOT NULL,
  `node_id` INT         NOT NULL,
  FOREIGN KEY (node_id) REFERENCES ship_node(id) ON DELETE RESTRICT
);

CREATE TABLE `ship_nic_addr` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nic_id`  INT         NOT NULL,
  `node_id` INT         NOT NULL,
  `ip`      VARCHAR(20) NOT NULL UNIQUE,
  `mask`    VARCHAR(20) NOT NULL,
  FOREIGN KEY (nic_id)  REFERENCES ship_node_nic(id) ON DELETE RESTRICT,
  FOREIGN KEY (node_id) REFERENCES ship_node(id)     ON DELETE RESTRICT
);

CREATE TABLE `ship_nic_route` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `subnet`  VARCHAR(20) NOT NULL,
  `mask`    VARCHAR(20) NOT NULL,
  `nic_id`  INT         NOT NULL REFERENCES ship_node_nic(id),
  `node_id` INT         NOT NULL,
  `gateway` VARCHAR(20),
  FOREIGN KEY (nic_id)  REFERENCES ship_node_nic(id) ON DELETE RESTRICT,
  FOREIGN KEY (node_id) REFERENCES ship_node(id)     ON DELETE RESTRICT
);

CREATE TABLE `ship_dns` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `node_id` INT         NOT NULL,
  `dns`     VARCHAR(20) NOT NULL,
  FOREIGN KEY (node_id) REFERENCES ship_node(id) ON DELETE RESTRICT
);
INSERT INTO `ship_dns` (node_id, dns) VALUES (1, '');
INSERT INTO `ship_dns` (node_id, dns) VALUES (2, '');

COMMIT;


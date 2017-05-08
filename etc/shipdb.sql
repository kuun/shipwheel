use shipdb;

DROP TABLE IF EXISTS `ship_user` CASCADE;
DROP TABLE IF EXISTS `ship_conn_rule` CASCADE;
DROP TABLE IF EXISTS `ship_iface_addr` CASCADE;
DROP TABLE IF EXISTS `ship_iface_route` CASCADE;
DROP TABLE IF EXISTS `ship_node_iface` CASCADE;
DROP TABLE IF EXISTS `ship_dns` CASCADE;
DROP TABLE IF EXISTS `ship_node` CASCADE;
DROP TABLE IF EXISTS `ship_man_addr` CASCADE;

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

CREATE TABLE `ship_node_iface` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`    VARCHAR(20) NOT NULL,
  `node_id` INT         NOT NULL,
  FOREIGN KEY (node_id) REFERENCES ship_node(id) ON DELETE RESTRICT
);

CREATE TABLE `ship_iface_addr` (
  `id`       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `iface_id` INT         NOT NULL,
  `node_id`  INT         NOT NULL,
  `ip`       VARCHAR(20) NOT NULL,
  `mask`     VARCHAR(20) NOT NULL,
  FOREIGN KEY (iface_id)  REFERENCES ship_node_iface(id) ON DELETE RESTRICT,
  FOREIGN KEY (node_id) REFERENCES ship_node(id)     ON DELETE RESTRICT
);

CREATE TABLE `ship_iface_route` (
  `id`      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `dst_net`  VARCHAR(20) NOT NULL,
  `dst_mask`    VARCHAR(20) NOT NULL,
  `iface_id`  INT         NOT NULL,
  `node_id` INT         NOT NULL,
  `gateway` VARCHAR(20),
  FOREIGN KEY (iface_id)  REFERENCES ship_node_iface(id) ON DELETE RESTRICT,
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

CREATE TABLE `ship_man_addr` (
  `id`         INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `iface_name` VARCHAR(20) NOT NULL,
  `ip`         VARCHAR(20) NOT NULL,
  `mask`       VARCHAR(20) NOT NULL,
  `gateway`    VARCHAR(20) NOT NULL DEFAULT ''
);
INSERT INTO `ship_man_addr` (iface_name, ip, mask, gateway) VALUES ('man', '192.168.0.1', '255.255.255.0', '');

CREATE TABLE `ship_conn_rule` (
  `id`             INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `rule_type`      INT         NOT NULL,
  `direct`         INT         NOT NULL,
  `listen_addr_id` INT         NOT NULL,
  `listen_port`    INT         NOT NULL,
  `dst_addr`       VARCHAR(20) NOT NULL,
  `dst_port`       INT         NOT NULL,
  `send_addr_id`   INT         NOT NULL,
  `status`         BOOLEAN     NOT NULL,
  FOREIGN KEY (listen_addr_id) REFERENCES ship_iface_addr(id) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (send_addr_id)   REFERENCES ship_iface_addr(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMIT;


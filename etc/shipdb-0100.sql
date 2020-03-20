BEGIN TRANSACTION;

DROP TABLE IF EXISTS `ship_user`;
DROP TABLE IF EXISTS `ship_conn_rule`;
DROP TABLE IF EXISTS `ship_iface_addr`;
DROP TABLE IF EXISTS ship_route;
DROP TABLE IF EXISTS ship_iface;
DROP TABLE IF EXISTS `ship_dns`;
DROP TABLE IF EXISTS ship_engine;
DROP TABLE IF EXISTS `ship_man_addr`;

-- password value is sha256sum
CREATE TABLE `ship_user`
(
    `id`       INTEGER      NOT NULL PRIMARY KEY,
    `name`     VARCHAR(32)  NOT NULL UNIQUE,
    `password` VARCHAR(128) NOT NULL,
    `is_admin` BOOL         NOT NULL
);
INSERT INTO `ship_user` (`name`, `password`, `is_admin`)
VALUES ('admin', 'fc9a0fbd8bcb2e5a50e629163886ce78b45372bd6e3bfe22b6b051a44e3d6c25', TRUE);

CREATE TABLE ship_engine
(
    `id` INTEGER     NOT NULL PRIMARY KEY,
    `ip` VARCHAR(20) NOT NULL
);
INSERT INTO ship_engine (ip)
VALUES ('244.0.0.2');
INSERT INTO ship_engine (ip)
VALUES ('244.0.0.5');

CREATE TABLE ship_iface
(
    `id`      INTEGER     NOT NULL PRIMARY KEY,
    `name`    VARCHAR(20) NOT NULL,
    engine_id INTEGER     NOT NULL,
    FOREIGN KEY (engine_id) REFERENCES ship_engine (id) ON DELETE RESTRICT
);

CREATE TABLE `ship_iface_addr`
(
    `id`       INTEGER     NOT NULL PRIMARY KEY,
    `iface_id` INTEGER     NOT NULL,
    `ip`       VARCHAR(20) NOT NULL,
    `mask`     VARCHAR(20) NOT NULL,
    FOREIGN KEY (iface_id) REFERENCES ship_iface (id) ON DELETE RESTRICT
);

CREATE TABLE `ship_route`
(
    `id`       INTEGER     NOT NULL PRIMARY KEY,
    `dst_net`  VARCHAR(20) NOT NULL,
    `dst_mask` VARCHAR(20) NOT NULL,
    `iface_id` INTEGER     NOT NULL,
    `gateway`  VARCHAR(20),
    FOREIGN KEY (iface_id) REFERENCES ship_iface (id) ON DELETE RESTRICT
);

CREATE TABLE `ship_dns`
(
    `id`        INTEGER     NOT NULL PRIMARY KEY,
    `engine_id` INTEGER     NOT NULL,
    `dns`       VARCHAR(20) NOT NULL,
    FOREIGN KEY (engine_id) REFERENCES ship_engine (id) ON DELETE RESTRICT
);
INSERT INTO `ship_dns` (engine_id, dns)
VALUES (1, '114.114.114.114');
INSERT INTO `ship_dns` (engine_id, dns)
VALUES (2, '114.114.114.114');

CREATE TABLE `ship_man_addr`
(
    `id`         INTEGER     NOT NULL PRIMARY KEY,
    `iface_name` VARCHAR(20) NOT NULL,
    `ip`         VARCHAR(20) NOT NULL,
    `mask`       VARCHAR(20) NOT NULL,
    `gateway`    VARCHAR(20) NOT NULL DEFAULT ''
);
INSERT INTO `ship_man_addr` (iface_name, ip, mask, gateway)
VALUES ('man', '192.168.0.1', '255.255.255.0', '');

CREATE TABLE `ship_conn_rule`
(
    `id`             INTEGER     NOT NULL PRIMARY KEY,
    `rule_type`      INTEGER     NOT NULL,
    `direct`         INTEGER     NOT NULL,
    `listen_addr_id` INTEGER     NOT NULL,
    `listen_port`    INTEGER     NOT NULL,
    `dst_addr`       VARCHAR(20) NOT NULL,
    `dst_port`       INTEGER     NOT NULL,
    `send_addr_id`   INTEGER     NOT NULL,
    `status`         BOOLEAN     NOT NULL,
    FOREIGN KEY (listen_addr_id) REFERENCES ship_iface_addr (id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (send_addr_id) REFERENCES ship_iface_addr (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMIT;

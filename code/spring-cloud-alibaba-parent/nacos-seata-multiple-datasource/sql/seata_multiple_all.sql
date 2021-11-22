-- Stock
DROP DATABASE IF EXISTS seata_multiple_stock;
CREATE DATABASE seata_multiple_stock;
USE seata_multiple_stock;
CREATE TABLE product (
    id               INT(11)  NOT NULL AUTO_INCREMENT,
    price            DOUBLE   NOT NULL,
    stock            INT(11)  NOT NULL DEFAULT 0,
    last_update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
INSERT INTO product (id, price, stock) VALUES (1, 5, 10);

CREATE TABLE undo_log (
    id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    branch_id     BIGINT(20)   NOT NULL,
    xid           VARCHAR(100) NOT NULL,
    context       VARCHAR(128) NOT NULL,
    rollback_info LONGBLOB     NOT NULL,
    log_status    INT(11)      NOT NULL,
    log_created   DATETIME     NOT NULL,
    log_modified  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY `ux_undo_log` (xid, branch_id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;


-- Pay
DROP DATABASE IF EXISTS seata_multiple_pay;
CREATE DATABASE seata_multiple_pay;
USE seata_multiple_pay;
CREATE TABLE account (
    id               INT(11)  NOT NULL AUTO_INCREMENT,
    balance          DOUBLE   NOT NULL DEFAULT 0,
    last_update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
INSERT INTO account (id, balance) VALUES (1, 1);

CREATE TABLE undo_log (
    id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    branch_id     BIGINT(20)   NOT NULL,
    xid           VARCHAR(100) NOT NULL,
    context       VARCHAR(128) NOT NULL,
    rollback_info LONGBLOB     NOT NULL,
    log_status    INT(11)      NOT NULL,
    log_created   DATETIME     NOT NULL,
    log_modified  DATETIME     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY `ux_undo_log` (xid, branch_id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;



-- Order
DROP DATABASE IF EXISTS seata_multiple_order;
CREATE DATABASE seata_multiple_order;
USE seata_multiple_order;
CREATE TABLE orders (
    id               INT(11)        NOT NULL AUTO_INCREMENT,
    user_id          INT(11)        NOT NULL,
    product_id       INT(11)        NOT NULL,
    pay_amount       DECIMAL(10, 0) NOT NULL,
    status           VARCHAR(100)   NOT NULL,
    add_time         DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
CREATE TABLE undo_log (
      id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
      branch_id     BIGINT(20)   NOT NULL,
      xid           VARCHAR(100) NOT NULL,
      context       VARCHAR(128) NOT NULL,
      rollback_info LONGBLOB     NOT NULL,
      log_status    INT(11)      NOT NULL,
      log_created   DATETIME     NOT NULL,
      log_modified  DATETIME     NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY `ux_undo_log` (xid, branch_id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
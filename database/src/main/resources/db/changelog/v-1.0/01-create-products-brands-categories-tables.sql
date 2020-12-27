CREATE TABLE `web-shop`.`categories` (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id));
GO

CREATE TABLE `web-shop`.`brands` (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id));
GO

CREATE TABLE `web-shop`.`products` (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    picture VARCHAR(255) NOT NULL,
    brand_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (id),
    INDEX category_id_fk_idx (category_id ASC) VISIBLE,
    CONSTRAINT brand_id_fk
    FOREIGN KEY (id)
    REFERENCES `web-shop`.`brands` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT category_id_fk
    FOREIGN KEY (category_id)
    REFERENCES `web-shop`.`categories` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
GO
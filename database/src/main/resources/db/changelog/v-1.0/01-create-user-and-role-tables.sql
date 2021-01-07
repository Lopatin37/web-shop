CREATE TABLE `web-shop`.`users` (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id));
GO

CREATE TABLE `web-shop`.`roles` (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id));
GO

CREATE TABLE `web-shop`.`users_roles` (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    INDEX user_id_fk_idx (user_id ASC) VISIBLE,
    INDEX role_id_fk_idx (role_id ASC) VISIBLE,
    CONSTRAINT user_id_fk
    FOREIGN KEY (user_id)
    REFERENCES `web-shop`.`users` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT role_id_fk
    FOREIGN KEY (role_id)
    REFERENCES `web-shop`.`roles` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
GO
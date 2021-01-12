CREATE TABLE `web-shop`.`pictures_data` (
    `id` INT NOT NULL,
    `data` LONGBLOB NULL,
    PRIMARY KEY (`id`));
GO

CREATE TABLE `web-shop`.`pictures` (
    `id` INT NOT NULL,
    `content_type` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `picture_data_id` INT NOT NULL,
    `product_id` INT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `picture_data_id_UNIQUE` (`picture_data_id` ASC) VISIBLE,
    INDEX `FK43hu51t487tsmo7tltxmdx9br_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `FK43hu51t487tsmo7tltxmdx9br`
    FOREIGN KEY (`product_id`)
    REFERENCES `web-shop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FKe9cv52k04xoy6cj8xy308gnw3`
    FOREIGN KEY (`picture_data_id`)
    REFERENCES `web-shop`.`pictures_data` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
GO
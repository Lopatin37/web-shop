INSERT INTO `web-shop`.`users` (`username`, `password`)
VALUES ('Ivan','$2y$12$jfZsQp3jxwtpS9Y9XsLPbuHIvc0ZODB4KK6RBV9I9kPNXUUgooE.u'),
       ('Petr','$2y$12$V8hLsR/jbW4BehaK9pGg9ecr7VfgQ2B6de/Hjx3CXhd5CK3bHNpXm'),
       ('Viktor','$2y$12$ipW9bcnQ21G2Mt7bO7i5/.tEl4g4dmvNuVgdyOf8hbykl/o3govl.'),
       ('Sergey','$2y$12$35wFQ7XoLCAdnKvYvSulp.n/UNhepK/8JfdlHyRIyPbA669rM2LFK'),
       ('Anna','$2y$12$kYeUTVoW7l8uYUP/qBBl6uAhduVzBS8V3hPr29CTzZCdJ/h9y/F72'),
       ('Elena','$2y$12$kEd9KoVefX.kZku1HX0Km.sn4DgT0cgA0QqUFBX3srm1m7HrBMo/G'),
       ('Olga','$2y$12$g3jKg8XBKAPOULMtaWGyn.2tLW2cwQr8YVEr.2hpOyTwxT7VWtczy'),
       ('Oleg','$2y$12$t59Reo2X8EtJlPCl4KM.3e/V4nrIVR2ADuVqTDj4PAGquUHgpqLTO'),
       ('Igor','$2y$12$B03t46Pedt6/yf7Zm/ffBu.MyI.TNW4PLNcp9a4QThoV7Ror8aSMK'),
       ('Masha','$2y$12$SmWTKt6PaPWKn.QQGFY9futlLzeqAobcFPd2qv7LkfZMkvnjMYV32');
GO

INSERT INTO `web-shop`.`roles` (`name`)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
GO


INSERT INTO `web-shop`.`users_roles` (`user_id`, `role_id`)
VALUES ('1','1'),
       ('2','2'),
       ('3','2'),
       ('4','2'),
       ('5','2'),
       ('6','2'),
       ('7','2'),
       ('8','2'),
       ('9','2'),
       ('10','2');
GO
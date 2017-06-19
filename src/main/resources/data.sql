DELETE FROM `User` WHERE `mobile`=123456789 and `email`='admin@gmail.com';
DELETE FROM `User` WHERE `id`=1;
INSERT INTO `User` (`id`, `active`, `address`, `dni`, `email`, `mobile`, `password`, `registrationDate`, `username`) VALUES (1, b'0', NULL, NULL, 'admin@gmail.com', 123456789, '$2a$10$V78h.x0EpGctkxXcPxgQyOzJ2ezAR9ltxjm1ReUFbIXbOcDTd7t0e', NULL, 'admin');
DELETE FROM `Authorization` WHERE `user_id`=1;
INSERT INTO `Authorization` (`role`, `user_id`) VALUES ('ADMIN', 1);
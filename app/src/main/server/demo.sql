CREATE DATABASE `demo`;
USE `demo`;

CREATE TABLE `people` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name` TEXT,
  `last_name` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
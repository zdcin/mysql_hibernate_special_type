CREATE TABLE `test_each_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `json` json DEFAULT NULL,
  `testenum` enum('A','BB') DEFAULT NULL,
  `geo` point DEFAULT NULL,
  `geo_range` polygon DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


INSERT INTO `test_each_type` (`id`)
VALUES
	(1);

DROP PROCEDURE IF EXISTS `imap_contacts`.`GetUserById`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`GetUserById` (IN u_key BINARY(4))
LANGUAGE SQL
DETERMINISTIC
READS SQL DATA
SQL SECURITY DEFINER
COMMENT 'Retourne les méta-données d\'un utilisateur'
BEGIN
	SELECT HEX(`key`) AS `key`, `fname`, `lname`, `email`, `registration` FROM `users` WHERE `key` = u_key;
END;
|

DELIMITER ;

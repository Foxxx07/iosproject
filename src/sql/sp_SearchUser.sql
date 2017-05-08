DROP PROCEDURE IF EXISTS `imap_contacts`.`SearchUser`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`SearchUser` (IN search_str VARCHAR(100), IN in_bool_mode BOOLEAN)
LANGUAGE SQL
NOT DETERMINISTIC
READS SQL DATA
SQL SECURITY DEFINER
COMMENT 'Recherche un utilisateur'
BEGIN
	IF 0 < LENGTH(search_str) THEN
		IF in_bool_mode THEN
			SELECT HEX(`key`) AS `key` FROM `users` WHERE MATCH(`fname`, `lname`) AGAINST(search_str IN BOOLEAN MODE);
		ELSE
			SELECT HEX(`key`) AS `key` FROM `users` WHERE MATCH(`fname`, `lname`) AGAINST(search_str);
		END IF;
	END IF;
END;
|

DELIMITER ;

DROP PROCEDURE IF EXISTS `imap_contacts`.`SearchUser`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`SearchUser` (IN search_str VARCHAR(100), IN offset_pos INT, IN limit_l INT, IN in_bool_mode BOOLEAN)
LANGUAGE SQL
NOT DETERMINISTIC
READS SQL DATA
SQL SECURITY DEFINER
COMMENT 'Recherche un utilisateur'
BEGIN
	IF 0 < LENGTH(search_str) THEN
		IF limit_l IS NULL THEN
			SET limit_l = -1;
		END IF;

		IF offset_pos IS NULL THEN
			SET offset_pos = 0;
		END IF;

		IF in_bool_mode THEN
			SELECT HEX(`key`) AS `key` FROM `users` WHERE MATCH(`fname`, `lname`) AGAINST(search_str IN BOOLEAN MODE) LIMIT offset_pos, limit_l;
		ELSE
			SELECT HEX(`key`) AS `key` FROM `users` WHERE MATCH(`fname`, `lname`) AGAINST(search_str) LIMIT offset_pos, limit_l;
		END IF;
	END IF;
END;
|

DELIMITER ;

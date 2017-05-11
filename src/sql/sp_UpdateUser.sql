DROP PROCEDURE IF EXISTS `imap_contacts`.`UpdateUser`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`UpdateUser` (IN u_key BINARY(4), IN u_fname VARCHAR(50), IN u_lname VARCHAR(50), IN u_email VARBINARY(254), IN u_password BINARY(32))
LANGUAGE SQL
DETERMINISTIC
MODIFIES SQL DATA
SQL SECURITY DEFINER
COMMENT 'Modifie les méta-données d\'un utilisateur'
BEGIN
	IF EXISTS(SELECT 1 FROM `users` WHERE `key` = u_key) THEN
		IF (u_fname IS NOT NULL AND 0 < LENGTH(u_fname)) THEN
			UPDATE `users` SET `fname` = u_fname WHERE `key` = u_key;
		END IF;

		IF (u_lname IS NOT NULL AND 0 < LENGTH(u_lname)) THEN
			UPDATE `users` SET `lname` = u_lname WHERE `key` = u_key;
		END IF;

		IF (u_email IS NOT NULL AND RPAD(0x00, 254, 0x00) <> RPAD(u_email, 254, 0x00)) THEN
			UPDATE `users` SET `email` = u_email WHERE `key` = u_key;
		END IF;

		IF (u_password IS NOT NULL AND RPAD(0x00, 32, 0x00) <> u_password) THEN
			UPDATE `users` SET `password` = u_password WHERE `key` = u_key;
		END IF;
	END IF;
END;
|

DELIMITER ;

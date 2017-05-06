DROP PROCEDURE IF EXISTS `imap_contacts`.`CheckCredentials`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`CheckCredentials` (IN u_email BINARY(120), IN u_password BINARY(32))
LANGUAGE SQL
NOT DETERMINISTIC
READS SQL DATA
SQL SECURITY DEFINER
COMMENT 'Test les informations d\'identification'
BEGIN
	DECLARE STORED_PASSWORD			BINARY(32) DEFAULT NULL;
	DECLARE CURR_SELECT_PASSWORD	CURSOR FOR SELECT `password` FROM `users` WHERE `email` = u_email;

	OPEN	CURR_SELECT_PASSWORD;
	FETCH	CURR_SELECT_PASSWORD INTO STORED_PASSWORD;
	CLOSE	CURR_SELECT_PASSWORD;

	IF STORED_PASSWORD IS NOT NULL THEN
		IF STORED_PASSWORD <=> u_password THEN
			SELECT TRUE;
		END IF;
	END IF;
END;
|

DELIMITER ;

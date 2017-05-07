DROP PROCEDURE IF EXISTS `imap_contacts`.`CreateUser`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`CreateUser` (IN u_key BINARY(4), IN u_fname VARCHAR(50), IN u_lname VARCHAR(50), IN u_email VARBINARY(254), IN u_password BINARY(32))
LANGUAGE SQL
DETERMINISTIC
MODIFIES SQL DATA
SQL SECURITY DEFINER
COMMENT 'Créer un utilisateur'
BEGIN
	IF 0 = LENGTH(u_fname) THEN
		SIGNAL SQLSTATE VALUE '45000' SET MYSQL_ERRNO = 10000, MESSAGE_TEXT = 'User fname cannot be empty';
	ELSEIF 0 = LENGTH(u_lname) THEN
		SIGNAL SQLSTATE VALUE '45000' SET MYSQL_ERRNO = 10001, MESSAGE_TEXT = 'User lname cannot be empty';
	ELSEIF RPAD(0x00, 254, 0x00) <=> u_email THEN
		SIGNAL SQLSTATE VALUE '45000' SET MYSQL_ERRNO = 10002, MESSAGE_TEXT = 'User email cannot be empty';
	ELSEIF RPAD(0x00, 32, 0x00) <=> u_password THEN
		SIGNAL SQLSTATE VALUE '45000' SET MYSQL_ERRNO = 10003, MESSAGE_TEXT = 'User password cannot be empty';
	ELSE
		INSERT INTO `users` VALUE (u_key, u_fname, u_lname, u_email, u_password, UNIX_TIMESTAMP());
	END IF;
END;
|

DELIMITER ;

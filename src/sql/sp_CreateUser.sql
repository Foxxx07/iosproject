DROP PROCEDURE IF EXISTS `imap_contacts`.`CreateUser`;

DELIMITER |

CREATE DEFINER = `imap_contacts` PROCEDURE `imap_contacts`.`CreateUser` (IN u_key BINARY(4), IN u_fname VARCHAR(50), IN u_lname VARCHAR(50), IN u_email BINARY(120), IN u_password BINARY(32))
LANGUAGE SQL
DETERMINISTIC
MODIFIES SQL DATA
SQL SECURITY DEFINER
COMMENT 'Créer un utilisateur'
BEGIN
	INSERT INTO `users` VALUE (u_key, u_fname, u_lname, u_email, u_password, UNIX_TIMESTAMP());
END;
|

DELIMITER ;

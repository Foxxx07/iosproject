DROP DATABASE IF EXISTS `imap_contacts`;
CREATE SCHEMA IF NOT EXISTS `imap_contacts` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `imap_contacts` ;

CREATE TABLE IF NOT EXISTS `imap_contacts`.`users` (
	`key`			BINARY(4)		NOT NULL			COMMENT 'Identifiant unique d’un utilisateur',
	`fname`			VARCHAR(50)		NOT NULL			COMMENT 'Prénom de l’utilisateur',
	`lname`			VARCHAR(50)		NOT NULL			COMMENT 'Nom de famille de l’utilisateur',
	`email`			BINARY(120)		NOT NULL			COMMENT 'e-mail principal de l’utilisateur',
	`password`		BINARY(32)		NOT NULL			COMMENT 'Hash: SHA256',
	`registration`	BIGINT UNSIGNED NOT NULL DEFAULT 0	COMMENT 'Timestamp de création du compte',
PRIMARY KEY (`key`),
UNIQUE		INDEX `email_UNIQUE`	USING BTREE (`email` ASC)				COMMENT 'Index unique d’une adresse e-mail d’un utilisateur',
FULLTEXT	INDEX `names_FULLTEXT`				(`fname` ASC, `lname` ASC)	COMMENT 'Index pour la recherche FULLTEXT d’un utilisateur, par son prénom-nom')
ENGINE	= InnoDB
COMMENT	= 'Tables contenant les méta données des utilisateurs';

CREATE TABLE IF NOT EXISTS `imap_contacts`.`friends` (
	`relation_key`	BINARY(64)	NOT NULL				COMMENT 'Clé indéxant la relation',
	`user_a`		BINARY(4)	NOT NULL				COMMENT 'Identifiant de l\'utilisateur A, l\'initiateur de la relation',
	`user_b`		BINARY(4)	NOT NULL				COMMENT 'Identifiant de l\'utilisateur B',
	`state`			BIT(1)		NOT NULL DEFAULT b'0'	COMMENT 'Etat de la relation',
PRIMARY KEY (`relation_key`),
INDEX `fk_friends.uA_to_users.pk_idx` USING BTREE (`user_a` ASC) COMMENT 'FK pointant vers l\'utilisateur A',
INDEX `fk_friends.uB_to_users.pk_idx` USING BTREE (`user_b` ASC) COMMENT 'FK pointant vers l\'utilisateur B',
CONSTRAINT `fk_friends.uA_to_users.pk`
	FOREIGN KEY (`user_a`)
		REFERENCES `imap_contacts`.`users` (`key`)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
CONSTRAINT `fk_friends.uB_to_users.pk`
	FOREIGN KEY (`user_b`)
		REFERENCES `imap_contacts`.`users` (`key`)
	ON DELETE CASCADE
	ON UPDATE CASCADE)
ENGINE	= InnoDB
COMMENT	= 'Tables contenant les relation d\'amis entre utilisateurs';

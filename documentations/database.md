# Introduction

## MariaDB
> TODO: La documentation de la mise en service de MariaDB.

## Galera Cluster
> TODO: La documentation de la mise en service de Galera Cluster.

## MaxScale
> TODO: La documentation de la mise en service de MaxScale.

# Tables

## users

| Nom            | Information                                                                               |
| -------------- | ------------------------------------------------------------------------------------------|
| *key*          | La clé unique identifiant l'utilisateur.<br>La valeur est une clé sur 4 octects.          |
| *fname*        | Le prénom de l'utilisateur.                                                               |
| *lname*        | Le nom de famille de l'utilisateur.                                                       |
| *email*        | L'adresse e-mail d'authentification de l'utilisateur.                                     |
| *password*     | Le mot de passe d'authentification de l'utilisateur, hashé avec sha256.                   |
| *registration* | Le timestamp d'enregistrement du compte.                                                  |

# Procédures

## CheckCredentials
```sql
CheckCredentials (IN u_email BINARY(120), IN u_password BINARY(32))
```
* `u_email`		: Adresse e-mail de l'utilisateur.
* `u_password`	: Mot de passe de l'utilisateur.

`CheckCredentials` test l'authentification d'un utilisateur.

> Note: `u_password` est le hash *sha256* du mot de passe.

**Exmple**
 ```sql
-- Test l'authentification d'un utilisateur.
-- 52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12 : $ sha256sum <(echo -n "@azerty123#")
CALL CheckCredentials("maiuri.gaetan@protonmail.ch", 0x52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12);
-- OU
CALL CheckCredentials("maiuri.gaetan@protonmail.ch", x'52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12');
```

**Retour**
* Si l'authentification échoue, alors aucune ligne n'est retourné.
* Si l'authentification à réussi, alors le retour sera:

> | TRUE |
> |:----:|
> | 1    |

## GetUserById
```sql
GetUserById (IN u_key BINARY(4))
```
* `u_key`		: Clé unique de l'utilisateur.

`GetUserById` récupère les méta-données d'un utilisateur par sa clé.

**Exmple**
 ```sql
-- Récupère les méta-données d'un utilisateur par sa clé.
CALL GetUserById(0x00000001);
-- OU
CALL GetUserById(x'00000001');
```

**Retour**
* Si l'utilisateur n'existe pas, alors aucune ligne n'est retourné.
* Si l'utilisateur existe, alors le retour sera:

> | fname   | lname  | email                       | registration |
> | ------- | ------ | --------------------------- | ------------ |
> | Gaëtan  | Maiuri | maiuri.gaetan@protonmail.ch |   1494075896 |

## CreateUser
```sql
CreateUser (IN u_key BINARY(4), IN u_fname VARCHAR(50), IN u_lname VARCHAR(50), IN u_email BINARY(120), IN u_password BINARY(32))
```
* `u_key`		: Clé unique de l'utilisateur.
* `u_fname`		: Prénom de l'utilisateur.
* `u_lname`		: Nom de l'utilisateur.
* `u_email`		: Adresse e-mail de l'utilisateur.
* `u_password`	: Mot de passe de l'utilisateur.

`CreateUser` créer un nouvel utilisateur.

> Note: `u_password` est le hash *sha256* du mot de passe.

**Exmple**
 ```sql
-- Créer un nouvel utilisateur.
CreateUser(0x00000002, "Gaëtan", "Maiuri", "maiuri.gaetan@protonmail.ch", 0xf2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9);
-- OU
CreateUser(x'00000002', "Gaëtan", "Maiuri", "maiuri.gaetan@protonmail.ch", x'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9');
```

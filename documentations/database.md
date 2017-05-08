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

## CreateUser
```sql
CreateUser (IN u_key BINARY(4), IN u_fname VARCHAR(50), IN u_lname VARCHAR(50), IN u_email VARBINARY(254), IN u_password BINARY(32))
```
* `u_key`		: Clé unique de l'utilisateur.
* `u_fname`		: Prénom de l'utilisateur.
* `u_lname`		: Nom de l'utilisateur.
* `u_email`		: Adresse e-mail de l'utilisateur.
* `u_password`	: Mot de passe de l'utilisateur.

`CreateUser` créer un nouvel utilisateur.

> Note: `u_password` est le hash *sha256* du mot de passe.

**Exemple**
 ```sql
-- Créer un nouvel utilisateur.
-- 52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12 : $ sha256sum <(echo -n "@azerty123#")
CALL CreateUser(0x00000001, "Gaëtan", "Maiuri", "maiuri.gaetan@protonmail.ch", 0x52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12);
-- OU
CALL CreateUser(x'00000001', "Gaëtan", "Maiuri", "maiuri.gaetan@protonmail.ch", x'52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12');

-- Avec l'adresse e-mail en héxadecimal.
CALL CreateUser(0x00000001, "Gaëtan", "Maiuri", 0x6d61697572692e67616574616e4070726f746f6e6d61696c2e6368, 0x52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12);
-- OU
CALL CreateUser(x'00000001', "Gaëtan", "Maiuri", x'6d61697572692e67616574616e4070726f746f6e6d61696c2e6368', x'52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12');
```
**Erreurs**

| N     | Condition                           |
|:-----:| ----------------------------------- |
| 10000 | 0 = LENGTH(`u_fname`)               |
| 10001 | 0 = LENGTH(`u_lname`)               |
| 10002 | RPAD(0x00, 254, 0x00) <=> `u_email`   |
| 10003 | RPAD(0x00, 32, 0x00) <=> `u_password` |

## GetUserByCredentials
```sql
GetUserByCredentials (IN u_email VARBINARY(254), IN u_password BINARY(32))
```
* `u_email`		: Adresse e-mail de l'utilisateur.
* `u_password`	: Mot de passe de l'utilisateur.

`GetUserByCredentials` récupère les méta-données d'un utilisateur par ses informations de connexion.

> Note: `u_password` est le hash *sha256* du mot de passe.

**Exemple**
 ```sql
-- Récupère les méta-données d'un utilisateur par ses informations de connexion.
-- 52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12 : $ sha256sum <(echo -n "@azerty123#")
CALL GetUserByCredentials("maiuri.gaetan@protonmail.ch", 0x52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12);
-- OU
CALL GetUserByCredentials("maiuri.gaetan@protonmail.ch", x'52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12');

-- Avec l'adresse e-mail en héxadecimal.
CALL GetUserByCredentials(0x6d61697572692e67616574616e4070726f746f6e6d61696c2e6368, 0x52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12);
-- OU
CALL GetUserByCredentials(x'6d61697572692e67616574616e4070726f746f6e6d61696c2e6368', x'52a664c8e678831be343774d67febd9f193ff7dea63f36172b90b1706f477d12');
```

**Retour**
* Si l'authentification échoue, alors aucune ligne n'est retourné.
* Si l'authentification à réussi, alors le résultat sera le retour de:

```sql
CALL GetUserById(`[user-id]`);
```

**Erreurs**

| N     | Condition                           |
|:-----:| ----------------------------------- |
| 10002 | RPAD(0x00, 254, 0x00) <=> `u_email`   |
| 10003 | RPAD(0x00, 32, 0x00) <=> `u_password` |

## GetUserById
```sql
GetUserById (IN u_key BINARY(4))
```
* `u_key`		: Clé unique de l'utilisateur.

`GetUserById` récupère les méta-données d'un utilisateur par sa clé.

**Exemple**
 ```sql
-- Récupère les méta-données d'un utilisateur par sa clé.
CALL GetUserById(0x00000001);
-- OU
CALL GetUserById(x'00000001');
```

**Retour**
* Si l'utilisateur n'existe pas, alors aucune ligne n'est retourné.
* Si l'utilisateur existe, alors le retour sera:

> | key      | fname   | lname  | email                       | registration |
> | -------- | ------- | ------ | --------------------------- | ------------ |
> | 00000001 | Gaëtan  | Maiuri | maiuri.gaetan@protonmail.ch |   1494075896 |

## SetFriendship
```sql
SetFriendship (IN r_key BINARY(64), IN u_key_a BINARY(4), IN u_key_b BINARY(4))
```
* `r_key`		: La clé de relation à utiliser si aucune entrée n'existe, sinon: `NULL`.
* `u_key_a`		: Clé unique de l'utilisateur (créant|acceptant) la demande.
* `u_key_b`		: Clé unique de l'utilisateur (reçevant|ayant créé) la demande.

`SetFriendship` créer ou confirme une demande d'amis.

**Exemple**
 ```sql
-- Créer une demande d'amis de la part de 'A' pour 'B'.
CALL SetFriendship(0xd212cab79eac2f5438d8120664c13174d02cce8dd13d97fa585a77534316a82d1e99dfbcb031fbbe3854e3d18f39938ec6ab3f7f7d39338f8c2038f8757bb80d, 0x00000001, 0x00000002);
-- OU
CALL SetFriendship(x'd212cab79eac2f5438d8120664c13174d02cce8dd13d97fa585a77534316a82d1e99dfbcb031fbbe3854e3d18f39938ec6ab3f7f7d39338f8c2038f8757bb80d', x'00000001', x'00000002');

-- Accepte la demande d'amis de la part de 'A' pour 'B'.
-- `r_key` peut être à NULL, car nous savons avec certitude qu'une entrée existe,
-- sinon, l'erreur 10006 sera émise.
CALL SetFriendship(NULL, 0x00000002, 0x00000001);
-- OU
CALL SetFriendship(NULL, x'00000002', x'00000001');
```

**Erreurs**

| N     | Condition             |
|:-----:| --------------------- |
| 10004 | `u_key_a` <=> u_key_b   |
| 10005 | `u_key_a` IS NULL       |
| 10005 | `u_key_b` IS NULL       |
| 10006 | `r_key` IS NULL         |

## DeleteFriendship
```sql
DeleteFriendship (IN u_key_a BINARY(4), IN u_key_b BINARY(4))
```
* `u_key_a`		: Clé unique de l'utilisateur (créant|acceptant) la demande.
* `u_key_b`		: Clé unique de l'utilisateur (reçevant|ayant créé) la demande.

`DeleteFriendship` supprime une relation d'amis.

**Exemple**
 ```sql
-- Supprime une relation d'amis entre 'A' et 'B'.
CALL DeleteFriendship(0x00000001, 0x00000002);
-- OU
CALL DeleteFriendship(x'00000001', x'00000002');
```

**Erreurs**

| N     | Condition             |
|:-----:| --------------------- |
| 10004 | `u_key_a` <=> u_key_b   |
| 10005 | `u_key_a` IS NULL       |
| 10005 | `u_key_b` IS NULL       |

## GetFriendship
```sql
GetFriendship (IN u_key_a BINARY(4), IN u_key_b BINARY(4))
```
* `u_key_a`		: Clé unique de l'utilisateur (créant|acceptant) la demande.
* `u_key_b`		: Clé unique de l'utilisateur (reçevant|ayant créé) la demande.

`GetFriendship` récupère l'état d'une relation d'ami.

**Exemple**
 ```sql
-- Récupère l'état d'une relation d'ami entre 'A' et 'B'.
CALL GetFriendship(0x00000001, 0x00000002);
-- OU
CALL GetFriendship(x'00000001', x'00000002');
```

**Retour**
* Si la relation n'existe pas, alors aucune ligne n'est retourné.
* Si la relation existe, mais que l'utilisateur 'B' n'a pas encore accepté la demande, alors le retour sera:

> | confirmed |
> |:---------:|
> | 0         |

* Sinon:

> | confirmed |
> |:---------:|
> | 1         |

**Erreurs**

| N     | Condition             |
|:-----:| --------------------- |
| 10004 | `u_key_a` <=> `u_key_b`   |
| 10005 | `u_key_a` IS NULL       |
| 10005 | `u_key_b` IS NULL       |

## SearchUser
```sql
SearchUser (IN search_str VARCHAR(100), IN in_bool_mode BOOLEAN)
```
* `search_str`		: La chaîne à rechercher.
* `in_bool_mode`	: TRUE si la recherche doit avoir l'attribut `IN BOOLEAN MODE`, sinon la recherche se fera avec l'attribut `IN NATURAL LANGUAGE MODE`.

`SearchUser` recherche un utilisateur.

> Note: si `in_bool_mode` est à `TRUE`, alors la chaîne doit êtres formaté correctement, sinon, la recherche se pourra se faire, et une erreur sera retournée.
>
> Exemple:
> ```sql
CALL SearchUser("ga*,+", TRUE);
-- ERROR 1064 (42000): syntax error, unexpected $end
```

**Exemple**
 ```sql
-- Recherche un utilisateur.
CALL SearchUser("ga*", TRUE);
-- OU
CALL SearchUser("gaetan", FALSE);
```

**Retour**
* Si il n'y a aucun résultat, alors aucune ligne n'est retourné.
* Sinon:

> | key |
> | --------- |
> | 00000001  |

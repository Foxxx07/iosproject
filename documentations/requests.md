# Comminication vers l'API

## Sommaire
* Résumé
* Client
* Serveur
* Méthodes
* Collections
	* Collection: /u/
	* Collection: /friends/
	* Collection: /pos/
	* Collection: /config/

# Résumé
* Les messages envoyé à l'API sera sous:
	* `application/x-www-form-urlencoded`: pour l'envoi de text (converti en ASCII)
	* `multipart/form-data`: pour l'envoi de données tel quel, utilisé pour l'envoi de fichier.
* L'intéraction avec l'API se fera en **REST**.
* Les réponse de l'API sera en **JSON**.

# Client
L'application envera des message sous la forme:<br>
*URL encoded data, example:*
```plain
fname=john&lname=doe&email=john.doe%40protonmail.ch&message=hello+you
```
> Note: les données envoyé diffère suivant la ressource distante.

# Serveur
L'API répondra en JSON, sous la forme:

```json
{
	"e": 0,
	"c": 0,
	"data": {}
}
```
> Note:
> * **e**: (*int*) indique le code d'erreur.
> * **c**: (*int*) indique le code de résultat de l'appel (si non `e`).
> * **data**: (*multi*) les données (si non `e` ; optionnel).

# Méthodes
Les méthodes HTTP utilisé seront:

* `DELETE`	: supprime des (ressources|informations|méta-données|fichiers).
* `GET`		: (récupère|liste) des (ressources|informations|méta-données|fichiers).
* `HEAD`	: récupère les informations d'un(e) (ressource|fichier).
* `POST`	: envoi des (informations|méta-données).
* `PUT`		: (créer|met à jour) un fichier.

# Collections
Les collections utilisé seront:

* `/u`
	* *[id-user]*
* `/friends`
	* *[id-user-b]*
* `/pos`
	* *[id-user]*
* `/conf`

> Note:<br>
> * **/u/**			: collection des utilisateurs.<br>
> * **/friends/**	: collection de raltion d'ami.<br>
> * **/pos/**		: collection des positions.<br>
> * **/conf/**		: collection des configuration.

## Collection: /u/
* **GET** :
	* /u/				: liste les méta-données de tous les utilisateurs.
	* /u/*[n]*			: pagination à la position `[n]`.
	* /u/*[id-user]*	: liste les méta-données de l'utilisateur `[id-user]`.
* **POST** :
	* /u/				: créer un utilisateur.
	* /u/*[id-user]*	: met à jour les méta-données de l'utilisateur `[id-user]`.

## Collection: /friends/
* **GET** :
* **POST** :

## Collection: /pos/
* **GET** :
* **POST** :

## Collection: /config/
* **GET** :
* **POST** :

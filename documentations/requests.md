# Comminication vers l'API
* Les messages échangé entre l'API et l'application seront structuré en **JSON**.
* L'intéraction avec l'API se fera en REST.

# Client
```json
{}
```
> Note: les données envoyé diffère suivant la ressource distante.

# Serveur
JSON de réponse:
```json
{
	"e": 0,
	"c": 0,
	"data": {}
}
```
> Note:
> * e: (int) indique le code d'erreur
> * c: (int) indique le code de résultat de l'appel (si non `e`)
> * data: (multi) les données (si non `e` ; optionnel)

# Collections
* /u/
	* [id-user]/
* /pos/
	* [id-user]/
* /conf/

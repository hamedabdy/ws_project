Serveur de Streaming de Vidéo
=============================
*Projet du WEB SOA et JEE - M1 Informatique Paris V Descartes*


# Introduction

## General

Ce projet consiste a animer les technologies apris dans les cours de JEE et WEB SOA en première année de Master Informatique au sein d'université de Paris V Descartes.

## Projet

Ce projet est developé avec les technologies suivantes:

* Eclipse Kepler (environment du development (IDE))
* Maven (management des dependences)
* Java
  * HTTPServlet 3.0.1 (coté serveur)
  * Apache Tomcat 7.0 (serveur de lancement)
* Basho Riak 1.4.4 (Base de données NO-SQL)
* JSON (communication entre Serveur, base de données et client)
* HTML5 + CSS3 (coté Client)

Le but de ce projet est de créer un serveur de streaming de vidéo avec les techonologies cité au-dessus similaire au produits existants comme par exemple: Youtube, Vimeo, DailyMotion et etc.
Ce projet ainsi donne la possibilité aux utilisateurs de charger (Upload) leurs propres vidéo au serveur et d'être capable de la voire en directe sur le site.


# Fonctionnalités Techniques

Expliquation étape par étape:
### Coté Client

#### Chargement de la vidéo

Du coté client, la methode POST est utilisé pour envoyer la vidèo au serveur par une formulaire simple du HTML.

#### Lecture vidéo

La lacture de vidéo est faite en utilisant API lecture vidéo du HTML5.

### Coté Serveur

**Toute les requetes d'échanges entre le serveur et la base de données et faite en `HTTP` et `JSON`**

#### Procedure stockage vidéo dans la base Riak
Tout d'abord le serveur est developé par la classe `HTTPServlet` du package `javax.servlet-api` qui est chargé dans le fichier `pom.xml` pour que Maven puisse recuperer les fichiers `.jar` de ce package automatiquement.
Ainsi ce dernier utilise l'annotation `@MultiPartConfig` pour être capable recevoir des fichiers.
Le serveur est toujours à l'écoute des changements à partir du client. C'est à dire, en beneficiant de sa méthode `doPost()`, o peut recuperer la vidès charger par l'utilisateur.
Dans une deuxième étape, les informations et la vidéo recuperer du site sont stocké dans la base Riak via son interface `HTTP` et grâce à son API (`riak-client`) pour JAVA.

#### Recuperation et affichage de la vidéo à partir Riak
Ensuite selon demande de l'utilisateur pour voir une vidéo, la méthode `doGet()` est proviqué et donc la vidéo ainsi avec ses propres informations complementaire sont envoyé vers le client (par affichage sur une page HTML). L'utilisateur aura donc la possiblité de télécharger cette vidéo ou la voire directement sur le site.


# Conclusion

Apart le fait que j'ai trouvé cette module très interessante et que j'avais fait un peu de developpement JAVA Servlet en troisieme année de la Licence et puis encore j'ai developé une application client/serveur en utilisant d'autre langages, mais je dois dire que cette module m'a rajouter plus connaissances sur l'architecture et la façon comment chaque module du serveur, de la base de données et du web interagissent entre eux.

# A propos

Ce projet est réalisé developé et testé par:

[Hamed Abdy] (https://github.com/hamedabdy)


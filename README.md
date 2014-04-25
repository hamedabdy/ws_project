Serveur de Streaming de Vidéo
=============================
*Projet du WEB SOA et JEE - M1 Informatique Paris V Descartes*


# Introduction

## General

Ce projet consiste à animer les technologies appris dans les cours de JEE et WEB SOA en première année de Master Informatique au sein d'université de Paris V Descartes.

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

Le but de ce projet est de créer un serveur de streaming de vidéo avec les techonologies cité au-dessus similaire aux produits existants comme par exemple: Youtube, Vimeo, DailyMotion et etc.
Ce projet ainsi donne la possibilité aux utilisateurs de charger (Upload) leurs propres vidéo au serveur et d'être capable de la voir en directe sur le site.


# Fonctionnalités Techniques

Explication étape par étape:

### Coté Client

#### Chargement de la vidéo

Du coté client, la methode POST est utilisé pour envoyer la vidèo au serveur par un formulaire simple du HTML.

#### Lecture vidéo

La lecture de vidéo est faite en utilisant l'API de la lecture vidéo du HTML5.

### Coté Serveur

*Toute les requetes d'échanges entre le serveur et la base de données et faite en `HTTP` et `JSON`*

#### Procedure stockage vidéo dans la base Riak
Tout d'abord le serveur est developé par la classe `HTTPServlet` du package `javax.servlet-api` qui est chargé dans le fichier `pom.xml` pour que Maven puisse recuperer les fichiers `.jar` de ce package automatiquement.
Ainsi ce dernier utilise l'annotation `@MultiPartConfig` pour être capable recevoir des fichiers.
Le serveur est toujours à l'écoute des changements à partir du client. C'est à dire, en profitant de sa méthode `doPost()`, on peut recuperer les vidèos charger par l'utilisateur.
Dans une deuxième étape, les informations et la vidéo recuperer du site sont stocké dans la base Riak via son interface `HTTP` et grâce à son API (`riak-client`) pour JAVA.

#### Recuperation et affichage de la vidéo à partir Riak
Ensuite selon demande de l'utilisateur pour voir une vidéo, la méthode `doGet()` est provoqué et donc la vidéo ainsi avec ses propres informations complementaire sont envoyé vers le client (par affichage sur une page HTML). L'utilisateur aura donc la possiblité de télécharger cette vidéo ou la voir directement sur le site.


# Installation

## Prérequis

* Eclipse Kepler JEE IDE
 * Maven
* Apache Tomcat 7.0
* Basho Riak 1.4.8

#### 1. Demarer le serveur de base de données
Il suffit de lancer cette ligne de commande dans un Terminal (Unix ou Linux **uniquement**):

`.//chemin/vers/riakX/bin/riak start`

#### 2. Demarer l'application JAVA

Depuis l'interface Eclipse, demarere le projet comme un serveur Apache Tomcat 7.


# Conclusion

Apart le fait que j'ai trouvé ce module très interessant et que j'avais fait un peu de developpement JAVA Servlet en troisieme année de la Licence et puis encore j'ai developé une application client/serveur en utilisant d'autre langages, mais je dois dire que cette module m'a rajouter plus connaissances sur l'architecture et la façon comment chaque module du serveur, de la base de données et du web interagissent entre eux.

# A propos

Ce projet est réalisé, developé et testé par:

[Hamed Abdy] (https://github.com/hamedabdy)


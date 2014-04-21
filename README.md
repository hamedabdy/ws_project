Projet Concert d'à Côté
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
* API du site last.fm
* Base de Données
  * Hyper SQL Database (HSQLDB) 1.8 (Base de données SQL)
  * Javax Persistence (EntityManager)
* JSON (communication entre Serveur, base de données et client)
* HTML (coté Client)

Le but de ce projet est de créer un serveur pour recouperer les informations concernant les concerts dans le monde entier avec les techonologies cité au-dessus, l'idée transpirer de: [Le Concert d'à Côté] (http://concert-dacote.heroku.com)

# Fonctionnalités Techniques

Expliquation étape par étape:
### Coté Client

La page d'acceuil est charger avec le menu qui nous méne a choisir les fonctionnalités souhaité.

### Coté Serveur

#### Procedure stockage concert dans la base HSQLDB

Le serveur fait une requete à l'une des API de last.fm pour recuperer les derniers evenements.
Après avoir les recuperer sous format de `JSON`, il créé ensuite un objet `Concert` qui contient tous les informations concernant un evenement. Finalement ce dernier est stocké dans la base en utilisant `javax.persistence` et la méthode `commit()` de la `EntityManager`.

#### Recuperation et affichage concert à partir HSQLDB
Ensuite selon demande de l'utilisateur, la méthode `doGet()` est provoqué et donc la liste des concerts present dans la base est affiché sur une page HTML


# Installation

## Prérequis

* Eclipse Kepler JEE IDE
* Apache Tomcat 7.0
* Hyper SQL Database (HSQLDB) 1.8

#### 1. Demarer le serveur de base de données
Il suffit de lancer cette ligne de commande dans un Terminal ou Command Prompt:

`java -cp /chemin/vers/hsqldbX/hsqldb/lib/hsqldb.jar oeg.hsldb.Server`

#### 2. Demarer l'application JAVA

Depuis l'interface Eclipse, demarere le projet comme un serveur Apache Tomcat 7.


# Conclusion

Apart le fait que j'ai trouvé cette module très interessante et que j'avais fait un peu de developpement JAVA Servlet en troisieme année de la Licence et puis encore j'ai developé une application client/serveur en utilisant d'autre langages, mais je dois dire que cette module m'a rajouter plus connaissances sur l'architecture et la façon comment chaque module du serveur, de la base de données et du web interagissent entre eux.

# A propos

Ce projet est réalisé, developé et testé par:

[Hamed Abdy] (https://github.com/hamedabdy)


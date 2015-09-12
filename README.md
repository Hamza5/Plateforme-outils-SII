# Plateforme d'outils SII & Combinateur d'évidences

### Projet de fin d'études en licence Informatique générale

### 2014/2015

##### Hamza ABBAD & Ahmed ZEBOUCHI

## Présentation
Dans le cadre du projet de fin d'études de licence, nous avons réalisé deux applications autour de
théorie des connaissances incertaines et imprécises.

* __Combinateur d'évidences__

  Ce logiciel permet d'appliquer la théorie de Dempster-Shafer sur un ensemble des connaissances incertaines.
  Il peut fusionner les connaissances à partir de plusieurs sources indépendantes en utilisant des méthodes
  différentes. Il offre également la possibilité de prendre une décision selon un critère.

  Il est composé de deux parties, le noyau et l'interface graphique de l'utilisateur.
  - Le noyau est un exécutable programmé en Java qui fonctionne sous ligne de commande. Il prend comme
  paramètres deux chemins de fichiers :
      - Le premier fichier doit contenir les données nécessaires pour appliquer la théorie de Dempster-Shafer
      et fusionner les connaissances, ce fichier est sous format XML et porte l'extention `.dsto.xml`. 
      - Le deuxième est écrit après l'exécution et contiendra les résultats du calcul, il porte l'extention `.dsto.xml`.
  - L'interface graphique est le moyen qui aide l'utilisateur à saisir les connaissances et les sources.
  Elle permet de générer le fichier d'entrée pour le noyau et d'afficher les résultats à partir du fichier de sortie.

* __Plateforme d'outils SII__

  C'est une application représente une interface graphique regroupant plusieurs outils. Elle facilite
  l'intéraction de l'utilisateur avec ces outils en offrant une interface commune pour les utiliser.
  Il existe plusieurs outils et parmi d'eux on trouve notre *Combinateur d'évidences*.

## Dépendances

Le **Combinateur d'évidences** nécessite *Java Runtime Environment* (JRE) pour le fonctionnement du noyau, et
*Python 3*, *PyQt4* pour le fonctionnement de l'interface graphique. L'installation de la bibliothèque *lxml*
est recommandée.

La **Plateforme d'outils SII* à besoin aussi de *Java Runtime Environment* pour fonctionner. Certain de ses
outils nécessitent *MATLAB*.

## Installation

Télécharger la dernière version à partir du *Releases* de la page du projet dans GitHub. Ouvrir l'installation
et suivre les instructions. Patienter pendant l'installation. Deux raccourcis seront placés sur le bureau et
dans le menu démarrer correspondant à ces deux logiciels.

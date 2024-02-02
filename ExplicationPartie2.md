*ALEXANDRE Benjamin*
*MIAGE 1*
*Groupe 2*

## Compte-Rendu du TP1-Partie 2 : 

---

## Scénario 1 : 

* Coté client : 

On crée un socket client avec le port correspondant au serveur afin de réaliser la connexion.
Ensuite pour chaque message reçu par le serveur, le client renvoie ce qu'il faut afin de ce connecter.
Pour finir, le client lance la commande `ftp > quit` afin de se déconnecter.

* Coté Serveur : 

Tout à déjà été codé pour la partie 1 .

---

## Scénario 2 : 

* Coté client : 

Comme pour le client 1, le client se connecter avec son login et password en fonction des message reçu par le serveur.
Ensuite le client envoie la commande PING et va attendre de recevoir des réponses du serveur, s'il reçoit en dernier le message PONG, c'est que tout c'est passé correctement, sinon c'est qu'il y a eu une erreur.


* Coté Serveur : 

Pour le serveur, on reçoit d'abord le PING du client, on renvoie que le PING à bien été reçu et on envoie à la suite le message PONG.
Ensuite on peut obtenir deux message différents du client, soit il a bien reçu le PONG, soit il ne la pas reçu.

---

## Scénario 3 : 

* Coté client : 

Le client se connecte avec son login et password.
On envoie ensuite en premier le message `EPSV` afin de demandé au serveur d'ouvrir un nouveau serveur pour le transfère de données.
Ensuite si cela se déroule correctement, on crée un nouveau socket client pour les données et on envoie au serveur la commande LINE.
Ensuite on affiche les réponses du serveur.


* Coté Serveur : 

On ouvre un serveur de données et on accept la connexion du nouveau socket client.
Ensuite on vérifie que le fichier demandé existe.
Si cela est bon on parcours chaque ligne du fichier et quand nous sommes à la bonne ligne on l'envoie au client avec les derniers messages.

---
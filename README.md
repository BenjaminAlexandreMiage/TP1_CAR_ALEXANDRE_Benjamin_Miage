*ALEXANDRE Benjamin*
*MIAGE 1*
*Groupe 2*

## Compte-Rendu du TP1 : 

---

##  Partie 1 : 

**Le scénario** : *Un utilisateur doit pouvoir se connecter avec un login et un mot de passe, et utiliser la commande quit pour se déconnecter* 

**La conception** : 

### Les variables : 

- *login1* : L'identifiant écrit en brute pour que le client ce connecte.
- *mdp1* : Le mot de passe en lien avec l'identifiant.
- *boucle* : boolean qui permet de gérer la boucle infinie du serveur.

### Les événements : 

Le client doit d'abord entrer son login, s'il est correcte il reçoit le code 331, sinon il reçoit le code d'erreur 430.

Ensuite le client doit entrer son mot de passe, s'il est correcte il reçoit le code 230, sinon il reçoit le code 430.

Ensuite, la boucle infinie se lance en attendant une commande du client, si le client envoie la commande  `ftp> quit` alors il reçoit le code 221, sinon pour le moment pour tout autres commandes, il reçoit le code 500 car aucune autre commande n'est reconnue par le serveur.

### Tester le programme :

Il faut ouvrir un premier terminale pour le serveur et un second pour le client.

Pour le serveur, il faut dans un premier temps se rendre dans le dossier où se situe le fichier ServeurFTP.java et lancer la commande : `> javac ServeurFTP.java`  afin de compiler la classe.

Ensuite il faut lancer la commande : `> java ServeurFTP` afin de lancer le programme. 

Du côté du client, il faut lancer la commande : `> ftp localhost 2121` afin de se connecter au serveur (via le port).

Ensuite il ne reste plus qu'à se connecter avec le bon identifiant et le bon mot de passe.

---
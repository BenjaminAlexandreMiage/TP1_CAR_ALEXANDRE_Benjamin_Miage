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

##  Partie 2 :

**La conception** :

### Mise en place de la fonction bin :

Quand dans l'invite de commande ftp nous lançons la commande `> bin`, le serveur reçoit le message *TYPE I*.

Dans ce cas, le serveur renvoie un message : *200 Commande bin réalisé avec succès" au client*.

### Mise en place de la fonction get :

Avec la commande client `> get UnFichier.txt`, le serveur reçoit le message *EPSV*.

Dans ce cas, le serveur ouvre un nouveau ServerSocket avec par exemple le port *2424* et ensuite envoie le message suivant au client : *229 Entering Extended Passive Mode (|||2424|)*.

Ensuite le client envoie automatiquement le message *RETR UnFicher.txt* au serveur.

Dans ce cas le serveur commence par récupérer le fichier s'il existe, sinon il envoie un message d'erreur au client (soit il manque le nom di fichier en argument ou alors ce dernier n'existe pas).
Si le fichier existe, on ouvre le fichier en lecture et on envoie au client que l'on accepte la connexion puis on copie le fichier et à la fin le serveur envoie au client le message suivant : *226 Fichier correctement transféré* .

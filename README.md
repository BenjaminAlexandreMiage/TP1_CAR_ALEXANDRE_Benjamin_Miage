*ALEXANDRE Benjamin*
*MIAGE 1*
*Groupe 2*

## Comment étendre le programme ?

Le programme de mon serveur est seulement composé de la fonction main.

Dans cette fonction, la première partie (du début de la fonction jusqu'au while) permet au client de se connecter avec son login et son password.

La second partie est la boucle while qui est infinie tant que le client n'a pas exécuté la commande `ftp > quit`.
Cette boucle permet au client d'exécuté les commandes qu'il veut et il recevra une réponse du serveur si ce dernier sait répondre à la demande ou non.

Pour permettre au serveur de supporter de nouvelles commandes, il suffit d'ajouter le code nécessaire dans la boucle while avec ce que le serveur va recevoir du client et ce qu'il doit renvoyer au client.

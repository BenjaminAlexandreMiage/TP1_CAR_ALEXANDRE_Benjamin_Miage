import java.net.*;
import java.io.*;
import java.util.*;

public class ServeurFTP {

    public static void main(String[] args) throws IOException{

        //Création brute d'un login et mot de passe
        String login1 = "Benjamin";
        String mdp1 = "a";

        //On affiche que le serveur est prêt (et son port)
        System.out.print("Serveur prêt à accepter des connexions sur le port 2121");
        
        // On crée le socket du serveur
        ServerSocket serv = new ServerSocket(2121) ;

        // On récupère le socket lors de la connexion 
        Socket s2 = serv.accept();

        //On récupère l'inputStream 
        InputStream in = s2.getInputStream();
        Scanner scanner = new Scanner(in);

        //On récupère le outputStream et on envoie le message au client
        OutputStream out = s2.getOutputStream();
        String str1 = "220 Service ready\r\n";
        out.write(str1.getBytes());

        //On attend le prochain message (l'identifiant)
        String str = scanner.nextLine();

        //Variable pour que le programme fonctionne en continue tan que le client n'a pas fait la commande 'quit' 
        boolean boucle = true;

        //Si le message est bien un login valide, on envoie que la réponse est valide, sinon on envoie l'erreur
        if(str.equals("USER "+login1)){
            System.out.print("\n");
            System.out.print(str);
            out = s2.getOutputStream();
            str1 = "331 login is okay\r\n";
            out.write(str1.getBytes());
        }
        else{
            str1 = "430 Login invalide\r\n";
            out.write(str1.getBytes());
        } 

        //On attend le prochain message (le mot de passe)
        str = scanner.nextLine();
        
        //Si le message est bien le mot de passe valide, on envoie que la réponse est valide, sinon on envoie l'erreur
        if(str.equals("PASS "+mdp1)){
            System.out.print("\n");
            System.out.print(str);
            out = s2.getOutputStream();
            str1 = "230 mdp is okay\r\n";
            out.write(str1.getBytes());
        }
        else{
            str1 = "430 password invalide\r\n";
            out.write(str1.getBytes());
        } 

        //Si tout est valide, on lance la boucle infinie
        while(boucle){

            //On attend le prochain message
            str = scanner.nextLine();

            //Si la commande est 'quit'
            if(str.equals("QUIT")){

                //Message pour le client
                str1 = "221 deconnexion\r\n";
                //On envoie le message au client
                out.write(str1.getBytes());
                //On affiche sur le serveur que les client c'est déconnecté
                System.out.print("\n");
                System.out.print("Client déconnecté");
                System.out.print("\n");
                //On arrete la boucle
                boucle = false;
            }
            else{
                // Si on recoit une commande inconnue (pour le moment autre que 'quit')
                str1 = "500 commande non reconnu\r\n";
                out.write(str1.getBytes());
            }
        }

    }
}
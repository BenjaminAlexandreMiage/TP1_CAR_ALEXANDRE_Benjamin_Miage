import java.net.*;
import java.io.*;
import java.util.*;

public class Client2FTP{
    
    public static void main(String[] args) throws IOException{

        //On crée le socket du client
        Socket socketClient = new Socket(InetAddress.getLocalHost(),2121);

        //On crée son input,son output, son scanner 
        InputStream inClient = socketClient.getInputStream();
        Scanner scannerClient = new Scanner(inClient);
        OutputStream out = socketClient.getOutputStream();

        //On attend le premier message du serveur et on l'affiche 
        String strClient = scannerClient.nextLine();
        String reponseClient;

        System.out.print(strClient);
        System.out.print("\n");

        //Comme pour le client il y a la partie connexion avec le login et le password
        if(strClient.equals("220 Service ready")){
            reponseClient = "USER miage\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        strClient = scannerClient.nextLine();

        System.out.print(strClient);
        System.out.print("\n");

        if(strClient.equals("331 login is okay")){
            reponseClient = "PASS car\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        //On attend le prochain message du serveur 
        strClient = scannerClient.nextLine();

        System.out.print(strClient);
        System.out.print("\n");
        
        //Le client envoie la commande PING
        if(strClient.equals("230 mdp is okay")){
            reponseClient = "PING\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        //Le client attend un message du serveur
        strClient = scannerClient.nextLine();
        System.out.print(strClient);
        System.out.print("\n");

        //Le ping a bien été reçu par le serveur
        if(strClient.equals("200 PING command ok")){

            strClient = scannerClient.nextLine();
            System.out.print(strClient);
            System.out.print("\n");

            //Le client reçoit le message PONG
            if(strClient.equals("PONG")){
                
                //Le client répond au serveur et lance la commande QUIT
                reponseClient = "200 PONG command ok\r\n";
                out.write(reponseClient.getBytes());

                reponseClient = "QUIT\r\n";
                out.write(reponseClient.getBytes());

            }
            //Le client ne reçoit pas le message PONG
            else{

                //Le client répond au serveur et lance la commande QUIT
                reponseClient = "502 Unknown command\r\n";
                out.write(reponseClient.getBytes());

                reponseClient = "QUIT\r\n";
                out.write(reponseClient.getBytes());

            }
        }
        else{
            System.out.print("erreur");
        }

        //On affiche le dernier message du serveur 
        strClient = scannerClient.nextLine();
        System.out.print(strClient);
        System.out.print("\n");


    }


}
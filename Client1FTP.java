import java.net.*;
import java.io.*;
import java.util.*;

public class Client1FTP{
    
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

        //Le client doit envoyer son login
        if(strClient.equals("220 Service ready")){
            reponseClient = "USER miage\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        //On attend un message du serveur
        strClient = scannerClient.nextLine();

        System.out.print(strClient);
        System.out.print("\n");

        //Le client doit donner son password
        if(strClient.equals("331 login is okay")){
            reponseClient = "PASS car\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        //On attend un message du serveur 
        strClient = scannerClient.nextLine();

        System.out.print(strClient);
        System.out.print("\n");
        
        //Le client lance la commande QUIT
        if(strClient.equals("230 mdp is okay")){
            reponseClient = "QUIT\r\n";
            out.write(reponseClient.getBytes());
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
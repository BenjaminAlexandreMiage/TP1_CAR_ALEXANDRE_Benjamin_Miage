import java.net.*;
import java.io.*;
import java.util.*;

public class Client3FTP{
    
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

        strClient = scannerClient.nextLine();

        System.out.print(strClient);
        System.out.print("\n");
        
        //Le client envoie le message EPSV afin d'ouvrir un nouveau serveur pour les datas
        if(strClient.equals("230 mdp is okay")){

            reponseClient = "EPSV\r\n";
            out.write(reponseClient.getBytes());

            strClient = scannerClient.nextLine();
            System.out.print(strClient);
            System.out.print("\n");

            //Si le nouveau serveur est ouvert
            if(strClient.startsWith("229")){

                //On créer un nouveau socket client pour les datas
                Socket socketClientData = new Socket(InetAddress.getLocalHost(),2424);

                //Le client envoie la commande Line
                reponseClient = "LINE texteClient3.txt 2\r\n";
                out.write(reponseClient.getBytes());

                strClient = scannerClient.nextLine();
                System.out.print(strClient);
                System.out.print("\n");

                //Si la connexion des données est accepté
                if(strClient.equals("150 connexion de données accepté")){

                    //On attend la ligne demandé
                    strClient = scannerClient.nextLine();
                    System.out.print(strClient);
                    System.out.print("\n");

                    //On attend le dernier message du serveur 
                    strClient = scannerClient.nextLine();
                    System.out.print(strClient);
                    System.out.print("\n");

                }

            }
            else{
                System.out.print("erreur");
            }

        }
        else{
            System.out.print("erreur");
        }

        //Le client lance la commande QUIT
        reponseClient = "QUIT\r\n";
        out.write(reponseClient.getBytes());

        //On affiche le dernier message du serveur 
        strClient = scannerClient.nextLine();
        System.out.print(strClient);
        System.out.print("\n");

    }


}
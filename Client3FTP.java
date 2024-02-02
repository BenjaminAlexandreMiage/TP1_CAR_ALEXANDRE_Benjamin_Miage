import java.net.*;
import java.io.*;
import java.util.*;

public class Client3FTP{
    
    public static void main(String[] args) throws IOException{

        Socket socketClient = new Socket(InetAddress.getLocalHost(),2121);

        InputStream inClient = socketClient.getInputStream();
        Scanner scannerClient = new Scanner(inClient);

        String strClient = scannerClient.nextLine();
        String reponseClient;

        OutputStream out = socketClient.getOutputStream();

        System.out.print(strClient);
        System.out.print("\n");

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
        
        if(strClient.equals("230 mdp is okay")){

            reponseClient = "EPSV\r\n";
            out.write(reponseClient.getBytes());

            strClient = scannerClient.nextLine();
            System.out.print(strClient);
            System.out.print("\n");

            if(strClient.startsWith("229")){

                Socket socketClientData = new Socket(InetAddress.getLocalHost(),2424);
                reponseClient = "LINE texteClient3.txt 2\r\n";
                out.write(reponseClient.getBytes());

                strClient = scannerClient.nextLine();
                System.out.print(strClient);
                System.out.print("\n");

                if(strClient.equals("150 connexion de données accepté")){

                    strClient = scannerClient.nextLine();
                    System.out.print(strClient);
                    System.out.print("\n");

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

        reponseClient = "QUIT\r\n";
        out.write(reponseClient.getBytes());

        strClient = scannerClient.nextLine();
        System.out.print(strClient);
        System.out.print("\n");

    }


}
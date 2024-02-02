import java.net.*;
import java.io.*;
import java.util.*;

public class Client1FTP{
    
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
            reponseClient = "QUIT\r\n";
            out.write(reponseClient.getBytes());
        }
        else{
            System.out.print("erreur");
        }

        strClient = scannerClient.nextLine();
        System.out.print(strClient);
        System.out.print("\n");



    }


}
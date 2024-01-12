import java.net.*;
import java.io.*;
import java.util.*;

public class ServeurFTP {

    public static void main(String[] args) throws IOException{

         System.out.print("Serveur prêt à accepter des connexions sur le port 2121");
        
        // On crée le socket du serveur
        ServerSocket serv = new ServerSocket(2121) ;

        // On récupère le socket lors de la connexion 
        Socket s2 = serv.accept();

        //On récupère l'inputStream 
        InputStream in = s2.getInputStream();
        Scanner scanner = new Scanner(in);

        //On récupère le outputStream
        OutputStream out = s2.getOutputStream();
        String str1 = "220 Service ready\r\n";
        out.write(str1.getBytes());

        //On attend le prochain message 
        String str = scanner.nextLine();

        
        
    }
}
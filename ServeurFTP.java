import java.net.*;
import java.io.*;
import java.util.*;

public class ServeurFTP {

    public static void main(String[] args) throws IOException{

        String login1 = "Benjamin";
        String mdp1 = "a";

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

        if(str.equals("USER "+login1)){
            System.out.print("\n");
            System.out.print(str);
            out = s2.getOutputStream();
            str1 = "331 login is okay\r\n";
            out.write(str1.getBytes());
        }

        str = scanner.nextLine();
        
        if(str.equals("PASS "+mdp1)){
            System.out.print("\n");
            System.out.print(str);
            out = s2.getOutputStream();
            str1 = "230 mdp is okay\r\n";
            out.write(str1.getBytes());

            str = scanner.nextLine();
        }

        while(true){
            if(str.equals("QUIT")){
                System.out.print("Client déconnecté");
            }
            else{
                out = s2.getOutputStream();
                str1 = "500 commande non reconnu\r\n";
                out.write(str1.getBytes());
            }
        }

    }
}
import java.net.*;
import java.io.*;
import java.util.*;

public class ServeurFTP {

    private static ServerSocket servData;

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

        str = scanner.nextLine();

        if(str.equals("SYST")){
                str1 = "215 UNIX system type\r\n";
                out.write(str1.getBytes());
            }

        str = scanner.nextLine();

        if(str.equals("FEAT")){
                str1 = "211 system status\r\n";
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

            // Si la commande est 'bin'
            else if(str.equals("TYPE I")){
                System.out.print("\n");
                System.out.print(str);
                //Message pour le client
                str1 = "200 Commande bin réalisé avec succès\r\n";
                //On envoie le message au client
                out.write(str1.getBytes());
            }

            // Si la commande est 'get' on reçoit ce premier message 
            else if(str.equals("EPSV")){
                System.out.print("\n");
                System.out.print(str);
                // On ouvre un nouveau ServerSocket pour traiter les données
                servData = new ServerSocket(2424);
                //Message pour le client
                str1 = "229 Entering Extended Passive Mode (|||2424|) \r\n";
                //On envoie le message au client
                out.write(str1.getBytes());
            }

            // Message reçu après le traitement du message 'EPSV'
            else if(str.startsWith("RETR")){
            
            // Va permettre de récupère chaque partie du message 
            String[] coupageDuMessage = str.split(" ");

            // On vérifie qu'il y a bien un argument à la commande 
            if (coupageDuMessage.length == 2) {

                //On récupère le nom du fichier 
                String nomDuFicher = coupageDuMessage[1];
                // Va permettre d'ouvrir le fichier
                File fichier = new File(nomDuFicher);

                //Si le fichier existe 
                if (fichier.exists() && fichier.isFile()) {
                    try (
                        
                        //Le serverSocket accept la connexion du socket
                        Socket dataSocket = servData.accept(); 
                        
                        //Il va permettre de faire une lecture du fichier
                        FileInputStream fileInputStream = new FileInputStream(fichier);

                        //Va permettre de faire le recopiage du fichier byte par byte
                        OutputStream dataOut = dataSocket.getOutputStream()) {

                        //Message pour le client
                        String dataReponse = "150 connexion de données accepté\r\n";
                        out.write(dataReponse.getBytes());

                        //Tableau pour conserver les bytes du fichier
                        byte[] tabByte = new byte[1024];
                        
                        //Pour utiliser la fonction dataOut
                        int lectureBytes;

                        //Permet le recopiage du fichier
                        while ((lectureBytes = fileInputStream.read(tabByte)) != -1) {
                                dataOut.write(tabByte, 0, lectureBytes);
                            }

                        //Message pour le client
                        String reponse = "226 Fichier correctement transféré\r\n";
                        out.write(reponse.getBytes());
                    }
                // Cas ou le fichier n'est pas trouvé
                } else {
                    String response = "550 fichier non trouvé\r\n";
                    out.write(response.getBytes());
                }
            // Cas ou aucun argument est donné à la fonction get
            } else {
                String response = "501 probleme d'argument\r\n";
                out.write(response.getBytes());
            }
                

            }

            else{
                // Si on recoit une commande inconnue (pour le moment autre que 'quit')
                str1 = "500 commande non reconnu\r\n";
                out.write(str1.getBytes());
                System.out.print("\n");
                System.out.print("message non traité : "+str);
            }
        }

    }
}
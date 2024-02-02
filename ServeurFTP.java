import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.file.*; 

public class ServeurFTP {

    private static ServerSocket servData;
    private static String cheminRepertoireServeur;

    public static void main(String[] args) throws IOException{

        //Création brute d'un login et mot de passe
        String login1 = "miage";
        String mdp1 = "car";

        //On récupère le chemin courant du serveur
        String cheminRepertoireServeur = System.getProperty("user.dir");
        Path path = Paths.get(cheminRepertoireServeur);
        System.out.print(path.normalize());

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
            //Si le mot de passe est invalide
            else{
                str1 = "430 password invalide\r\n";
                out.write(str1.getBytes());
            }
        }
        //Si le login est invalide
        else{
            str1 = "430 Login invalide\r\n";
            out.write(str1.getBytes());
        } 

    


        //Si tout est valide, on lance la boucle infinie
        while(boucle){

            //On attend le prochain message
            str = scanner.nextLine();

            if(str.equals("SYST")){
                str1 = "215 UNIX system type\r\n";
                out.write(str1.getBytes());
            }

            else if(str.equals("FEAT")){
                str1 = "211 system status\r\n";
                out.write(str1.getBytes());
            }

            //Si la commande est 'quit'
            else if(str.equals("QUIT")){

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
                str1 = "229 Entering Extended Passive Mode (|||2424|)\r\n";
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

                        dataSocket.close();
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
                //On ferme le nouveau serveur
                servData.close();

            }

            // Pour mettre en mode ascii
            else if(str.equals("TYPE A")){
                System.out.print("\n");
                System.out.print(str);
                //Message pour le client
                str1 = "200 Mode ascii validé\r\n";
                //On envoie le message au client
                out.write(str1.getBytes());
            }

            //Pour la commande dir
            else if(str.startsWith("LIST")){
                
                //Le serverSocket accept la connexion du socket
                Socket dataSocket = servData.accept(); 

                // Va permettre de récupère chaque partie du message 
                String[] coupageDuMessage = str.split(" ");

                try(

                    OutputStream dataOut = dataSocket.getOutputStream()){

                        //Message pour le client
                        String dataReponse = "150 connexion de données accepté\r\n";
                        out.write(dataReponse.getBytes());

                        //On récupère la chemin courant
                        String cheminRepertoire = System.getProperty("user.dir");
                        //On récupère le fichier courant
                        File repertoireCourant = new File(cheminRepertoire);
                        //On récupère les fichiers du fichier courant
                        File listeFichier [] = repertoireCourant.listFiles();

                        //Si la commande possède 2 arguments
                        if(coupageDuMessage.length == 2){
                            //On récupère le deuxième argument
                            listeFichier = new File[1];
                            //On mets dans la liste le fichier que l'on veut
                            listeFichier[0] = new File(repertoireCourant+"/"+coupageDuMessage[1]);
                        }

                        //Pour fichier de la liste on envoie son nom au client
                        for(File item : listeFichier){
                            String reponseFichier = item.getName()+"\r\n";
                            dataOut.write(reponseFichier.getBytes());
                        }

                        //Dernier message pour le client
                        String reponse = "226 affichage des fichiers réussie\r\n";
                        out.write(reponse.getBytes());
                }

                //On ferme le socket et le serveur des données
                dataSocket.close();
                servData.close();        
            }

            //Pour la commande cd
            else if(str.startsWith("CWD")){
                
                //On coupe la commande
                String[] coupageDuMessage = str.split(" ");

                //Si juste la commande CD, on remet le repertoire courant sur celui du serveur 
                if (coupageDuMessage.length == 1){

                    File repertoireCourant = new File(cheminRepertoireServeur);
                    System.setProperty("user.dir",cheminRepertoireServeur);
                    String reponse = "200 accès au dossier réussie\r\n";
                    out.write(reponse.getBytes());
                }

                //Si deux arguments 
                else if (coupageDuMessage.length == 2){
                    
                    //On récupère le dossier dans lequel on veut entrer
                    String cheminRepertoire = System.getProperty("user.dir")+"/"+coupageDuMessage[1];
                    File repertoireCourant = new File(cheminRepertoire);
                        
                    //Si on trouve le dossier
                    if(repertoireCourant.exists() && repertoireCourant.isDirectory()){
                        //On change le répertoire courant
                        System.setProperty("user.dir",cheminRepertoire);
                        String reponse = "200 accès au dossier réussie\r\n";
                        out.write(reponse.getBytes());
                    }
                    //Cas où on ne trouve pas le dossier
                    else{
                        String reponse = "500 le dossier n'existe pas\r\n";
                        out.write(reponse.getBytes());
                    }

                }

            }

            //Si on reçoit la commande PING
            else if(str.equals("PING")){
                str1 = "200 PING command ok\r\n";
                out.write(str1.getBytes());
                System.out.print("\n");
                System.out.print("PING receive");
                //On répond PONG
                str1 = "PONG\r\n";
                out.write(str1.getBytes());
    
            }

            //Si la commande PONG est bien reçu par le client
            else if (str.equals("200 PONG command ok")){
                System.out.print("\n");
                System.out.print("200 PONG command ok");
            }
            //Si la commande PONG n'est pas reçu par le client
            else if (str.equals("502 Unknown command")){
                System.out.print("\n");
                System.out.print("502 Unknown command");
            }

            //Si on reçoit la commande Line 
            else if(str.startsWith("LINE")){

                System.out.print("\n");
                System.out.print(str);
            
                String[] coupageDuMessage = str.split(" ");

                //On vérifie qu'il y a bien trois arguments
                if (coupageDuMessage.length==3){

                    //On récupère le nom du fichier et le numéro de la ligne 
                    String nomDuFicher = coupageDuMessage[1];
                    int numLigneChoisie = Integer.valueOf(Integer.valueOf(coupageDuMessage[2]));
                    File fichier = new File(nomDuFicher);

                    //Si le fichier existe
                    if (fichier.exists() && fichier.isFile()) {
                
                        //On accepte la nouvelle connexion
                        Socket dataSocket = servData.accept(); 
                        FileInputStream fileInputStream = new FileInputStream(fichier);
                        OutputStream dataOut = dataSocket.getOutputStream();
            
                        String dataReponse = "150 connexion de données accepté\r\n";
                        out.write(dataReponse.getBytes());

                        //Pour nous permettre de lire les lignes
                        FileReader fileReader = new FileReader(nomDuFicher);
                        BufferedReader reader = new BufferedReader(fileReader);

                        int numLigne = 1;

                        //Lit la première ligne
                        String line = reader.readLine();

                        while (line != null){
                            
                            //Si c'est le numéro de la ligne que l'on souhaite
                            if(numLigne==numLigneChoisie){
                                
                                //On envoie au client le contenue de la ligne
                                String ligne = line+"\r\n";
                                out.write(ligne.getBytes());

                                numLigne ++;
                                line = reader.readLine();
                            }
                            //Si ce n'est pas le numéro de la ligne que l'on souhaite
                            else{
                                numLigne ++;
                                line = reader.readLine();
                            }

                    }
                        
                    String reponse = "226 Fichier correctement transféré\r\n";
                    out.write(reponse.getBytes());

                    dataSocket.close();
                    
                }
                //Si le fichier n'est pas trouvé
                else{
                    String reponse = "500 fichier non trouvé\r\n";
                    out.write(reponse.getBytes());
                }

                }
                

            }


            // Si on recoit une commande inconnue (pour le moment autre que 'quit')
            else{
                str1 = "500 commande non reconnu\r\n";
                out.write(str1.getBytes());
                System.out.print("\n");
                System.out.print("message non traité : "+str);
            }
        }

    }
}
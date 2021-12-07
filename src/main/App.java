package main;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        startApp();
    }

    public static void startApp() {

        try{
                TraceManager.loadFile();

            //ADD HERE DISPLAY OF FILE SUCCESSFULLY DECODED
            try {
                TraceManager.resultFileWriter.close();
            } catch (IOException e) {
                System.out.println("ERREUR LORS DE LA FERMETURE DU FICHIER");
            }
        } catch (Exception e) {
            System.out.println("ERRUR : INCONNU");
            System.out.println("POSSIBILITE TRAME NON ETHERNET");
            System.out.println("POSSIBILITE TRAME INCOMPLETE/FICHIER CORROMPU");
            System.out.println("POSSIBILITE FICHIER NON CHOISI/DROITS D'ACCES");
        }
    }
}

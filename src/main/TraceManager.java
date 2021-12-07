package main;

import Layer2.Layer2;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TraceManager {

    public static File loadedFile;
    public static File resultFile;
    public static FileWriter resultFileWriter;
    public static ArrayList<String> tramesString = new ArrayList<>();


    public static void loadFile() {
        //Select a file from disk to laod the trace.
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            loadedFile = jfc.getSelectedFile();
            if (cleanFile()) {
                createResultFile();
                decodeTrace();
            }
        }


    }

    private static void createResultFile() {
        resultFile = new File(loadedFile.getAbsolutePath().replace(".txt", "_Decoded.txt"));
        try {
            resultFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resultFileWriter = new FileWriter(resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean cleanFile() {
        Scanner myReader = null;
        try {
            myReader = new Scanner(loadedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String data = "";
        boolean b = true;
        String REGEX = "\\s{2,100}";

        int oldOffset = 0;
        int oldLineLength = 0;
        int oldOffsetLength = 0;

        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            String[] splits = data.split(REGEX);

            //if (data.length()<5) continue;
            if (!data.replace(" ", "").matches("[0-9a-f]{6,}+.+")) continue; //Permet de sauter les sauts de lignes et les lignes qui contiennent du text

            String offset = splits[0];
            String bytes = splits[1];
            //System.out.println(bytes);

            if (!offset.matches("-?[0-9a-f]+")) {
                System.out.println("ERREUR : OFFSET NON HEX");
                System.out.println(data);
                return false;
            }
            if (!bytes.replace(" ", "").matches("-?[0-9a-f]+")) {
                System.out.println("ERREUR : OCTET NON HEX");
                System.out.println(data);
                return false;
            }

            int offsetInt = Integer.parseInt(offset, 16);
            int lineLength = bytes.replace(" ", "").length() / 2;

           if (offsetInt == 0) {
               oldLineLength = lineLength;
               oldOffsetLength = offset.length();
               oldOffset = 0;
               tramesString.add(bytes + " ");
           }
           else {
               if (offsetInt != oldOffset + oldLineLength) {
                   System.out.println("ERREUR : ERREUR VALEUR OFFSET");
                   System.out.println(data);
                   System.out.println("VALEUR ATTENDUE : " + Integer.toHexString(oldOffset + oldLineLength) + " (" + (oldOffset + oldLineLength) + ")");
                   return false;
               }

               if (oldOffsetLength != offset.length()) {
                   System.out.println("ERREUR : TAILLE OFFSET DIFFERENTS");
                   System.out.println(data);
                   System.out.println("TAILLE ANCIEN OFFSET : " + oldOffsetLength);
                   return false;
               }

               tramesString.set(tramesString.size() - 1, tramesString.get(tramesString.size() - 1) + bytes + " ");
               oldOffset = offsetInt;
           }

        }

        //tramesString.forEach(s -> System.out.println(s)); //Permet d'afficher les trames chargé par l'application
        return true;
    }

    public static void decodeTrace() {
        //Starts the process of decoding the trace.
        final int[] i = {1};
        tramesString.forEach(trame -> {
            try {
                System.out.println("Trame " + i[0] + " : +++++++++++++++++++++++++++++++++++++++++++++++++++\n ");
                resultFileWriter.write("Trame " + i[0] + " : +++++++++++++++++++++++++++++++++++++++++++++++++++\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Layer2.toLayer(trame);
            i[0]++;
        });
    }

    public static String getByte(String trame, int i) {
        int realIndex = 0;
        if (i == 1) realIndex = 0;
        else realIndex = (i - 1) * 3;


        return trame.substring(realIndex, realIndex + 2);
    }

    public static String getByteInRange(String trame, int i, int j) {
        int realIndexI = 0;
        if (i == 1) realIndexI = 0;
        else realIndexI = (i-1)*3;

        int realIndexJ = 0;
        if (j == 1) realIndexJ = 0;
        else realIndexJ = (j-1) * 3 + 2;

        return trame.substring(realIndexI, realIndexJ);
    }

    public static String getMissingBytes(String trame, int i) {
        int realIndexI = 0;
        if (i == 1) realIndexI = 0;
        else realIndexI = (i-1)*3;

        return trame.substring(realIndexI);
    }

    public static String hexToIP(String hex) {
        if (hex.length() != 11) {
            System.out.println("ERREUR HEXTOIP : STRING DOES NOT CORRESPOND TO AN IP");
            return null;
        }
        return Integer.parseInt(getByte(hex, 1), 16) + "." + Integer.parseInt(getByte(hex, 2), 16) + "." + Integer.parseInt(getByte(hex, 3), 16) + "." + Integer.parseInt(getByte(hex, 4), 16);
    }

}

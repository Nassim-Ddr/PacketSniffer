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
            cleanFile();
        }

        createResultFile();

        decodeTrace();
    }

    private static void createResultFile() {
        resultFile = new File(loadedFile.getAbsolutePath().replace(".txt", "Decoded.txt"));
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

    private static void cleanFile() {
        //Erreurs possible:
            //- de 2 octs dans l'offset
            //Les octes ne formes pas un rectangle (nbr d'octet de ligne en ligne differents)
            //Erreurs dans l'offset
        Scanner myReader = null;
        try {
            myReader = new Scanner(loadedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String data = "";
        boolean b = true;
        while (myReader.hasNextLine() & b) {
            data = myReader.nextLine();
            try {
                if (data.length() > 4) {
                    Integer.parseInt(data.substring(0, 3), 16);
                    String tmp = data.split("  ")[1];
                    tmp = tmp.split("   ")[0];
                    if (data.startsWith("0000")) {
                        tramesString.add(tmp + " ");
                    } else {
                        tramesString.set(tramesString.size() - 1, tramesString.get(tramesString.size() - 1) + tmp + " ");
                    }
                }
            }
            catch (NumberFormatException  e) {

            }


        }

        myReader.close();


        //trames.forEach(s -> System.out.println(s));
    }

    public static void decodeTrace() {
        //Starts the process of decoding the trace.
        final int[] i = {1};
        tramesString.forEach(trame -> {
            try {
                System.out.println("Trame " + i[0] + " : \n ");
                resultFileWriter.write("Trame " + i[0] + " : \n");
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

        return trame.substring(realIndexI, trame.length() - 1);
    }

}

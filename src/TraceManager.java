import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TraceManager {

    public static File loadedFile;

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
            Scanner myReader = null;
            try {
                myReader = new Scanner(loadedFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        }
    }

    public static void decodeTrace() {
        //Starts the process of decoding the trace.
    }

    public static void saveDecode() {
        //Saves the decoded trace on a .txt file
    }

}

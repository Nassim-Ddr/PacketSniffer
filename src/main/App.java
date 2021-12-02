package main;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        startApp();
    }

    public static void startApp() {

        TraceManager.loadFile();
        //ADD HERE DISPLAY OF FILE SUCCESSFULLY DECODED
        try {
            TraceManager.resultFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

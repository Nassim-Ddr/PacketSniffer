package Layer3;

import main.TraceManager;

import java.io.IOException;

public class Layer3 {
    public static void toLayer(String trame, String ehterNetType) {
        //Find the protocol and create an object of that proto
        if (ehterNetType.equals("08 00")) {
            Ip trameIP = new Ip(trame);
            trameIP.display();
            trameIP.nextLayer();
        } else {
            System.out.println("Protocol non pris en charge");
            try {
                TraceManager.resultFileWriter.write("PROTOCOL NON PRIS EN CHARGE\n");
            } catch (IOException e) {

            }
        }
    }
}

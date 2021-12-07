package Layer4;

import main.TraceManager;

import java.io.IOException;

public class Layer4 {
    public static void toLayer(String trame, String protocol) {
        //Find the protocol and create an object of that proto
        if (protocol.equals("11")){
        Udp trameUdp = new Udp(trame);
        trameUdp.display();
        trameUdp.nextLayer();

        } else {
            System.out.println("Protocol non pris en charge");
            try {
                TraceManager.resultFileWriter.write("PROTOCOL NON PRIS EN CHARGE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

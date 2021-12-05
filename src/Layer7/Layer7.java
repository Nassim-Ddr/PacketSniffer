package Layer7;

public class Layer7 {

    public static void toLayer(String trame, String srcPort, String desPort) {
        //Find the protocol and create an object of that proto
        //There not a "real" way to detect the app protocol
        //We can still use some "hints" to detect the protocol
            //For example the Ports in udp : 67 & 68 are mostly DHCP and 53 is DNS but this not 100% sure ;v
            //that s why this function takes srcPort and desPort

        if ((srcPort.equals("00 44") & desPort.equals("00 43")) || (srcPort.equals("00 43") & desPort.equals("00 44"))) {
            Dhcp trameDhcp = new Dhcp(trame);
            trameDhcp.display();
        }
        else if (srcPort.equals("00 35") || desPort.equals("00 35")) {
            Dns trameDns = new Dns(trame);
            trameDns.display();
        }
        else System.out.println("Application protocol unknown");
    }
}

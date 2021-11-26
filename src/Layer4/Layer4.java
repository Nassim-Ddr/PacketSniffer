package Layer4;

public class Layer4 {
    public static void toLayer(String trame) {
        //Find the protocol and create an object of that proto
        Udp trameUdp = new Udp(trame);
        trameUdp.display();
        trameUdp.nextLayer();
    }
}

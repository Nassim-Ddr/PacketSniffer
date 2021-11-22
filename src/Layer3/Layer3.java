package Layer3;

public class Layer3 {
    public static void toLayer(String trame) {
        //Find the protocol and create an object of that proto
        Ip trameIP = new Ip(trame);
        trameIP.display();
        trameIP.nextLayer();
    }
}

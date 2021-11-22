package Layer2;

public class Layer2 {
    public static void toLayer(String trame) {
        //Un seul protocole Ethernet
        Ethernet trameEthernet = new Ethernet(trame);
        trameEthernet.display();
        trameEthernet.nextLayer();
    }
}

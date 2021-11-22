package Layer2;
import Layer3.Layer3;
import main.TraceManager;

import java.io.IOException;


public class Ethernet {

    private String desAdr;
    private String srcAdr;
    private String type;
    private String data;

    public Ethernet() { }

    public Ethernet(String trame) {
        desAdr = TraceManager.getByteInRange(trame, 1, 6);
        srcAdr = TraceManager.getByteInRange(trame, 7, 12);
        type = TraceManager.getByteInRange(trame, 13, 14);
        data = TraceManager.getMissingBytes(trame, 15);
    }

    public void display() {

        try {
            writeResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Display parameters in a formatted way.
        System.out.println("ETHERNET : ");
        System.out.println("\tSource address : " + desAdr + "\n"); //DISPLAY IN AA:AA:AA:AA:AA:AA
        System.out.println("\tDestination address : " + desAdr + "\n"); //DISPLAY IN AA:AA:AA:AA:AA:AA
        System.out.println("\tType : " + type + "\n"); //CREATE HASHMAP WITH HEX AND STRING
        System.out.println("\tData : " + data + "\n");



    }

    public void writeResult() throws IOException {
        TraceManager.resultFileWriter.write("Ethernet : \n");
        TraceManager.resultFileWriter.write("\tSource address : " + desAdr + "\n");
        TraceManager.resultFileWriter.write("\tDestination address : " + desAdr + "\n");
        TraceManager.resultFileWriter.write("\tType : " + type + "\n");
        TraceManager.resultFileWriter.write("\tData : " + data + "\n");
    }

    public void nextLayer() {
        //Calls NextLayer
        Layer3.toLayer(data);
    }

    public String getDesAdr() {
        return desAdr;
    }

    public void setDesAdr(String desAdr) {
        this.desAdr = desAdr;
    }

    public String getSrcAdr() {
        return srcAdr;
    }

    public void setSrcAdr(String srcAdr) {
        this.srcAdr = srcAdr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

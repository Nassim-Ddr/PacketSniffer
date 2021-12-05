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
        //trame = "08 00 20 0A AC 96 08 00 20 0A 70 66 08 00 4F 00 00 7C CB C9 00 00 FF 01 B9 7F 84 E3 3D 05 C0 21 9F 06 07 27 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 33 00 08 00 A2 56 2F 00 00 00 29 36 8C 41 00 03 86 2B 08 09 0A 0B 0C 0D 0E 0F 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1F 20 21 22 23 24 25 26 27 28 29 2A 2B 2C 2D 2E 2F 30 31 32 33 34 35 36 37";
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
        //System.out.println("\tData : " + data + "\n");



    }

    public void writeResult() throws IOException {
        TraceManager.resultFileWriter.write("Ethernet : \n");
        TraceManager.resultFileWriter.write("\tSource address : " + desAdr + "\n");
        TraceManager.resultFileWriter.write("\tDestination address : " + desAdr + "\n");
        TraceManager.resultFileWriter.write("\tType : " + type + "\n");
        //TraceManager.resultFileWriter.write("\tData : " + data + "\n");
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

package Layer4;

import Layer7.Layer7;
import main.TraceManager;

import java.io.IOException;

public class Udp {
    private String srcPrt;
    private String desPrt;
    private String length;
    private String checkSum;
    private String data;


    public Udp() {
    }

    public Udp(String trame) {
        srcPrt = TraceManager.getByteInRange(trame, 1, 2);
        desPrt = TraceManager.getByteInRange(trame, 3, 4);
        length = TraceManager.getByteInRange(trame, 5, 6);
        checkSum = TraceManager.getByteInRange(trame, 7, 8);
        data = TraceManager.getMissingBytes(trame, 9);
    }

    public void display() {
        try {
            writeResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("UDP : ");
        System.out.println("\tSource port : " + srcPrt + " (" + Integer.parseInt(srcPrt.replace(" ", ""), 16) + ")");
        System.out.println("\tDestination port : " + desPrt + " (" + Integer.parseInt(desPrt.replace(" ", ""), 16) + ")");
        System.out.println("\tLength : " + length + " (" + Integer.parseInt(length.replace(" ", ""), 16) + ")");
        System.out.println("\tChecksum : " + checkSum);
        //System.out.println("\tdata (udp payload) : " + data + "\n");

    }

    public void writeResult() throws IOException {
        TraceManager.resultFileWriter.write("UDP : ");
        TraceManager.resultFileWriter.write("\tSource port : " + srcPrt +" (" + Integer.parseInt(srcPrt.replace(" ", ""), 16) + ")"+ "\n");
        TraceManager.resultFileWriter.write("\tDestination port : " + desPrt + " (" + Integer.parseInt(desPrt.replace(" ", ""), 16) + ")"+"\n");
        TraceManager.resultFileWriter.write("\tLength : " + length + " (" + Integer.parseInt(length.replace(" ", ""), 16) + ")"+"\n");
        TraceManager.resultFileWriter.write("\tChecksum : " + checkSum + "\n");
        //TraceManager.resultFileWriter.write("\tdata (udp payload) : " + data + "\n");

    }

    public void nextLayer() {

        Layer7.toLayer(data, srcPrt, desPrt);
    }

    public String getSrcPrt() {
        return srcPrt;
    }

    public void setSrcPrt(String srcPrt) {
        this.srcPrt = srcPrt;
    }

    public String getDesPrt() {
        return desPrt;
    }

    public void setDesPrt(String desPrt) {
        this.desPrt = desPrt;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

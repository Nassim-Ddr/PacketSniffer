package Layer3;

import Layer4.Layer4;
import main.TraceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ip {

    private String version;
    private String ihl;
    private String tos;
    private String totalLength;
    private String id;
    private String flagsFragmentOffset;
    private String ttl;
    private String protocol;
    private String checkSum;
    private String srcAdr;
    private String desAdr;
    private String opt;
        private String optToDisplay;
    private String data;

    public static Map<String, String> ipOptions  = new HashMap<String, String>() {{
        //Planning on putting options
        put("00", "END OF OPTION LIST");
        put("01", "NO OPERATION");
        put("07", "RECORD ROUTE");
    }};

    public static Map<String, String> ipProtocols  = new HashMap<String, String>() {{
        //Planning on putting options
        put("01", "ICMP");
        put("02", "IGMP");
        put("06", "TCP");
        put("08", "EGP");
        put("09", "IGP");
        put("11", "UDP");
        put("24", "XTP");
        put("2E", "RSVP");
    }};

    public Ip() { }

    public Ip(String trame) {
        //Init the parameters.
        version = TraceManager.getByte(trame, 1);
        ihl = String.valueOf(version.charAt(1));
        version = String.valueOf(version.charAt(0));
        tos = TraceManager.getByte(trame, 2);
        totalLength = TraceManager.getByteInRange(trame, 3, 4);
        id = TraceManager.getByteInRange(trame, 5, 6);
        flagsFragmentOffset = TraceManager.getByteInRange(trame, 7, 8);
        ttl = TraceManager.getByte(trame, 9);
        protocol = TraceManager.getByte(trame, 10);
        checkSum = TraceManager.getByteInRange(trame, 11, 12);
        srcAdr = TraceManager.getByteInRange(trame, 13, 16);
        desAdr = TraceManager.getByteInRange(trame, 17, 20);

        int headerLength = Integer.parseInt(ihl, 16) * 4;
        if (headerLength > 20) opt = TraceManager.getByteInRange(trame, 21, headerLength);
        else opt = "None";

        handleOptions();

        data = TraceManager.getMissingBytes(trame, headerLength + 1 );
        //ADD HERE THE CODE TO LOAD THE OPTIONS
        //U NEED TO :
        //1-Calculate how much byte u have in the options through the total length in byte - 20 for the min
        //Extract them with the Trace Manager
        //Display the options
        //Init the Data var
        //Display data
    }

    private void handleOptions() {
        List<String> options = new ArrayList<>();
        int optionLength = Integer.parseInt(ihl, 16) * 4 - 20;
        int i = 1;
        while (i <= optionLength) {
            String option = TraceManager.getByte(opt, i);
            if (Integer.parseInt(option, 16) > 1) {
                int optionSize = Integer.parseInt(TraceManager.getByte(opt, i+1), 16);
                option = TraceManager.getByteInRange(opt, i, optionSize);
                i = optionSize + 1;
            } else {
                i ++;
            }
            options.add(option);
        }

        String toPrint = "";
        int optIndex = 1;
        for(String s : options ) {
            String firstByte = TraceManager.getByte(s, 1);
            String toPrintOpt = "";
            toPrintOpt += "Option " +optIndex + ": "+ ipOptions.get(firstByte) +  "(" +firstByte + ")"+ " \n";

            if (Integer.parseInt(firstByte) > 1) {
                //RECORD ROUTE
                toPrintOpt += "\t\t length : " + TraceManager.getByte(s, 2) + "\n";
                toPrintOpt += "\t\t pointer : " + TraceManager.getByte(s, 3) + "\n";
                for (int j = 4; j<40; j+=4) {
                    toPrintOpt += "\t\t ip "+j/4+" : " + TraceManager.getByteInRange(s, j, j+3) + "\n";
                }
            }
            toPrint += "\t" + toPrintOpt;
            optIndex +=1;
        }
        optToDisplay = toPrint;
    }

    public void display() {

        try {
            writeResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ip : ");
        System.out.println("\tVersion : " + version + "\n");  //CREATE HASHMAP WITH HEX AND STRING
        System.out.println("\tIHL : " + ihl + "\n");
        System.out.println("\tTOS : " + tos + "\n");
        System.out.println("\tTotal length : " + totalLength + "\n");
        System.out.println("\tIdentification : " + id + "\n");
        System.out.println("\tFragmentOffset : " + flagsFragmentOffset + "\n"); //DISPLAY ALSO FLAGS
        System.out.println("\tTTL : " + ttl + "\n");
        System.out.println("\tProtocol : " + protocol + "\n"); //CREATE HASHMAP WITH HEX AND STRING
        System.out.println("\tChecksum : " + checkSum + "\n");
        System.out.println("\tSrcAdr : " + srcAdr + "\n"); //Display SRC
        System.out.println("\tDesAdr : " + desAdr + "\n"); //Display DEC
        System.out.println("\tOptions : " + /*opt +*/ "\n"); //Display the different options one by one
        System.out.println(optToDisplay + "\n");
        //System.out.println("\tData : " + data + "\n");
    }

    public void writeResult() throws IOException {
        TraceManager.resultFileWriter.write("IP : \n");
        TraceManager.resultFileWriter.write("\tVersion : " + version + "\n");
        TraceManager.resultFileWriter.write("\tIHL : " + ihl + "\n");
        TraceManager.resultFileWriter.write("\tTOS : " + tos + "\n");
        TraceManager.resultFileWriter.write("\tTotal length : " + totalLength + "\n");
        TraceManager.resultFileWriter.write("\tIdentification : " + id + "\n");
        TraceManager.resultFileWriter.write("\tFragmentOffset : " + flagsFragmentOffset + "\n");
        TraceManager.resultFileWriter.write("\tTTL : " + ttl + "\n");
        TraceManager.resultFileWriter.write("\tProtocol : " + protocol + "\n");
        TraceManager.resultFileWriter.write("\tChecksum : " + checkSum + "\n");
        TraceManager.resultFileWriter.write("\tSrcAdr : " + srcAdr + "\n");
        TraceManager.resultFileWriter.write("\tDesAdr : " + desAdr + "\n");
        TraceManager.resultFileWriter.write("\tOptions : " + /*opt +*/ "\n");
        TraceManager.resultFileWriter.write(optToDisplay + "\n");
        //TraceManager.resultFileWriter.write("\tData : " + data + "\n");

    }

    public void nextLayer() {
        //Calls NextLayer
        Layer4.toLayer(data);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIhl() {
        return ihl;
    }

    public void setIhl(String ihl) {
        this.ihl = ihl;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlagsFragmentOffset() {
        return flagsFragmentOffset;
    }

    public void setFlagsFragmentOffset(String flagsFragmentOffset) {
        this.flagsFragmentOffset = flagsFragmentOffset;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getSrcAdr() {
        return srcAdr;
    }

    public void setSrcAdr(String srcAdr) {
        this.srcAdr = srcAdr;
    }

    public String getDesAdr() {
        return desAdr;
    }

    public void setDesAdr(String desAdr) {
        this.desAdr = desAdr;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

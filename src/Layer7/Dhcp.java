package Layer7;

import main.TraceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dhcp {

    private static String opCode;
    private static String hardwareType;
    private static String hardwareAdrLength;
    private static String hops;
    private static String transactionId;
    private static String seconds;
    private static String flags;
    private static String clientIpAdr;
    private static String urIpAdr;
    private static String serverIpAdr;
    private static String relayIp; //relyagent in wireshark
    private static String clientHardwareAdr;
    private static String serverName;
    private static String bootfileName;
    private static String magicCookie;
    private static String options;
    private String optionsToDisplay;


    public Dhcp() { }
    public Dhcp(String trame) {
        opCode = TraceManager.getByte(trame, 1);
        hardwareType = TraceManager.getByte(trame, 2);
        hardwareAdrLength = TraceManager.getByte(trame, 3);
        hops = TraceManager.getByte(trame, 4);
        transactionId = TraceManager.getByteInRange(trame, 5, 8);
        seconds = TraceManager.getByteInRange(trame, 9, 10);
        flags = TraceManager.getByteInRange(trame, 11, 12);
        clientIpAdr = TraceManager.getByteInRange(trame, 13, 16);
        urIpAdr = TraceManager.getByteInRange(trame, 17, 20);
        serverIpAdr = TraceManager.getByteInRange(trame, 21, 24);
        relayIp = TraceManager.getByteInRange(trame, 25, 28);
        clientHardwareAdr = TraceManager.getByteInRange(trame,29, 44);
        serverName = TraceManager.getByteInRange(trame, 45, 108);
        bootfileName = TraceManager.getByteInRange(trame, 109, 236);
        magicCookie = TraceManager.getByteInRange(trame, 237, 240);
        options = TraceManager.getMissingBytes(trame, 241);
        handleOptions();
    }

    public void display() {

        try {
            writeResult();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("DHCP : ");
        System.out.println("\tOperation code : " + opCode);
        System.out.println("\tHardware type : " + hardwareType);
        System.out.println("\tHardware address length : " + hardwareAdrLength);
        System.out.println("\tHops : " + hops);
        System.out.println("\tTransaction ID : " + transactionId);
        System.out.println("\tSeconds : " + seconds);
        System.out.println("\tFlags : " + flags);
        System.out.println("\tClient IP : " + clientIpAdr);
        System.out.println("\tYour IP : " + urIpAdr);
        System.out.println("\tNext serveur IP : " + serverIpAdr);
        System.out.println("\tRelay agent IP : " + relayIp);
        System.out.println("\tClient MAC : " + clientHardwareAdr); //THERE IS PADDING HERE
        System.out.println("\tServer name : " + serverName);
        System.out.println("\tBoot file name : " + bootfileName);
        System.out.println("\tMagic cookie : " + magicCookie);

        System.out.println("\tOptions : \n" + optionsToDisplay);

        System.out.println("\t");
    }

    private void handleOptions() {
        int i = 1;
        List<String> optionList = new ArrayList<>();
        while (i <= options.length()) {
            String option = TraceManager.getByte(options, i);
            if (option.equals("ff")) i=options.length()+1;
            else {
                int optionLength = Integer.parseInt(TraceManager.getByte(options, i+1), 16);
                option = TraceManager.getByteInRange(options, i, i+optionLength+1);
                i += optionLength+2;

            }
            optionList.add(option);
        }

        String toPrint = "";
        int optIndex = 1;
        for (String option : optionList) {
            String firstByte = TraceManager.getByte(option,1);
            toPrint += "\tOption " + optIndex + " :" + DhcpOptions.get(firstByte) + "("+ firstByte +")\n";
            if (!firstByte.equals("ff")) {
                toPrint += "\t\tLength : " + TraceManager.getByte(option, 2) + "\n";
                toPrint += "\t\tValue : " + TraceManager.getMissingBytes(option, 3) + "\n\n";
            }
            optIndex++;
        }
        optionsToDisplay = toPrint;
    }

    public void writeResult() throws IOException{
        TraceManager.resultFileWriter.write("DHCP : ");
        TraceManager.resultFileWriter.write("\tOperation code : " + opCode + "\n");
        TraceManager.resultFileWriter.write("\tHardware type : " + hardwareType + "\n");
        TraceManager.resultFileWriter.write("\tHardware address length : " + hardwareAdrLength + "\n");
        TraceManager.resultFileWriter.write("\tHops : " + hops + "\n");
        TraceManager.resultFileWriter.write("\tTransaction ID : " + transactionId + "\n");
        TraceManager.resultFileWriter.write("\tSeconds : " + seconds + "\n");
        TraceManager.resultFileWriter.write("\tFlags : " + flags + "\n");
        TraceManager.resultFileWriter.write("\tClient IP : " + clientIpAdr + "\n");
        TraceManager.resultFileWriter.write("\tYour IP : " + urIpAdr + "\n");
        TraceManager.resultFileWriter.write("\tNext serveur IP : " + serverIpAdr + "\n");
        TraceManager.resultFileWriter.write("\tRelay agent IP : " + relayIp + "\n");
        TraceManager.resultFileWriter.write("\tClient MAC : " + clientHardwareAdr + "\n");
        TraceManager.resultFileWriter.write("\tServer name : " + serverName + "\n");
        TraceManager.resultFileWriter.write("\tBoot file name : " + bootfileName + "\n");
        TraceManager.resultFileWriter.write("\tMagic cookie : " + magicCookie + "\n");
        TraceManager.resultFileWriter.write("\tOptions : \n" + optionsToDisplay + "\n");
    }

    public void nextLayer() {

    }

    public static Map<String, String> DhcpTypes  = new HashMap<String, String>() {{
        //Planning on putting options
        put("01", "DHCP DISCOVER");
        put("02", "DHCP OFFER");
        put("03", "DHCP REQUEST");
        put("04", "DHCP DECLINE");
        put("05", "DHCP ACK");
        put("06", "DHCP NAK");
        put("07", "DHCP RELEASE");
        put("08", "DHCP INFORM");
    }};

    public static Map<String, String> DhcpOptions  = new HashMap<String, String>() {{
        //Map of an array : <Key of the options, length of the option, value of options>
        put("35", "TYPE");
        put("01", "SUBNET MASK");
        put("3d", "CLIENT IDENTIFIER");
        put("32", "REQUEST IP ADDRESS");
        put("37", "DHCP DECLINE");
        put("ff", "END");
        put("3a", "RENEWAL TIME VALUE");
        put("3b", "REBINDING TIME VALUE");
        put("33", "IP ADDRESS LEASE TIME");
        put("36", "DHCP SERVER IDENTIFIER");
    }};

    public static String getOpCode() {
        return opCode;
    }

    public static void setOpCode(String opCode) {
        Dhcp.opCode = opCode;
    }

    public static String getHardwareType() {
        return hardwareType;
    }

    public static void setHardwareType(String hardwareType) {
        Dhcp.hardwareType = hardwareType;
    }

    public static String getHardwareAdrLength() {
        return hardwareAdrLength;
    }

    public static void setHardwareAdrLength(String hardwareAdrLength) {
        Dhcp.hardwareAdrLength = hardwareAdrLength;
    }

    public static String getHops() {
        return hops;
    }

    public static void setHops(String hops) {
        Dhcp.hops = hops;
    }

    public static String getTransactionId() {
        return transactionId;
    }

    public static void setTransactionId(String transactionId) {
        Dhcp.transactionId = transactionId;
    }

    public static String getSeconds() {
        return seconds;
    }

    public static void setSeconds(String seconds) {
        Dhcp.seconds = seconds;
    }

    public static String getFlags() {
        return flags;
    }

    public static void setFlags(String flags) {
        Dhcp.flags = flags;
    }

    public static String getClientIpAdr() {
        return clientIpAdr;
    }

    public static void setClientIpAdr(String clientIpAdr) {
        Dhcp.clientIpAdr = clientIpAdr;
    }

    public static String getUrIpAdr() {
        return urIpAdr;
    }

    public static void setUrIpAdr(String urIpAdr) {
        Dhcp.urIpAdr = urIpAdr;
    }

    public static String getServerIpAdr() {
        return serverIpAdr;
    }

    public static void setServerIpAdr(String serverIpAdr) {
        Dhcp.serverIpAdr = serverIpAdr;
    }

    public static String getRelayIp() {
        return relayIp;
    }

    public static void setRelayIp(String relayIp) {
        Dhcp.relayIp = relayIp;
    }

    public static String getClientHardwareAdr() {
        return clientHardwareAdr;
    }

    public static void setClientHardwareAdr(String clientHardwareAdr) {
        Dhcp.clientHardwareAdr = clientHardwareAdr;
    }

    public static String getServerName() {
        return serverName;
    }

    public static void setServerName(String serverName) {
        Dhcp.serverName = serverName;
    }

    public static String getBootfileName() {
        return bootfileName;
    }

    public static void setBootfileName(String bootfileName) {
        Dhcp.bootfileName = bootfileName;
    }

    public static String getMagicCookie() {
        return magicCookie;
    }

    public static void setMagicCookie(String magicCookie) {
        Dhcp.magicCookie = magicCookie;
    }

    public static String getOptions() {
        return options;
    }

    public static void setOptions(String options) {
        Dhcp.options = options;
    }
}

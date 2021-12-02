package Layer7;

import main.TraceManager;

import java.util.HashMap;
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
    private static String gatewayIp; //relyagent in wireshark
    private static String clientHardwareAdr;
    private static String serverName;
    private static String bootfileName;
    private static String magicCookie;
    private static String options;


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
        gatewayIp = TraceManager.getByteInRange(trame, 25, 28);
        clientHardwareAdr = TraceManager.getByteInRange(trame,29, 44);
        serverName = TraceManager.getByteInRange(trame, 45, 108);
        bootfileName = TraceManager.getByteInRange(trame, 109, 236);
        magicCookie = TraceManager.getByte(trame, 237);
        options = TraceManager.getMissingBytes(trame, 238);
    }

    public void display() {

    }

    public void writeResult() {

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

    public static Map<String, String[]> DhcpOptions  = new HashMap<String, String[]>() {{
        //Map of an array : <Key of the options, length of the option, value of options>
        put("35", new String[] {"TYPE"});
        put("3d", new String[] {"CLIENT IDENTIFIER"});
        put("32", new String[] {"REQUEST IP ADDRESS"});
        put("37", new String[] {"DHCP DECLINE"});
        put("ff", new String[] {"END"});
        put("3a", new String[] {"RENEWAL TIME VALUE"});
        put("3b", new String[] {"REBINDING TIME VALUE"});
        put("33", new String[] {"IP ADDRESS LEASE TIME"});
        put("36", new String[] {"DHCP SERVER IDENTIFIER"});
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

    public static String getGatewayIp() {
        return gatewayIp;
    }

    public static void setGatewayIp(String gatewayIp) {
        Dhcp.gatewayIp = gatewayIp;
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

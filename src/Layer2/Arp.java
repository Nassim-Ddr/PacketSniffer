package Layer2;

public class Arp {

    private String hardware;
    private String protocol;
    private String hLen;
    private String pLen;
    private String operation;
    private String senderHA;
    private String senderIA;
    private String targetHA;
    private String targetIA;

    public Arp() { }

    public Arp(String data) {
        //Init the parameters.
    }

    public void display() {
        //Display parameters in a formatted way.
    }

    public void nextLayer() {
        //Calls NextLayer
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String gethLen() {
        return hLen;
    }

    public void sethLen(String hLen) {
        this.hLen = hLen;
    }

    public String getpLen() {
        return pLen;
    }

    public void setpLen(String pLen) {
        this.pLen = pLen;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSenderHA() {
        return senderHA;
    }

    public void setSenderHA(String senderHA) {
        this.senderHA = senderHA;
    }

    public String getSenderIA() {
        return senderIA;
    }

    public void setSenderIA(String senderIA) {
        this.senderIA = senderIA;
    }

    public String getTargetHA() {
        return targetHA;
    }

    public void setTargetHA(String targetHA) {
        this.targetHA = targetHA;
    }

    public String getTargetIA() {
        return targetIA;
    }

    public void setTargetIA(String targetIA) {
        this.targetIA = targetIA;
    }
}

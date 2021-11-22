package Layer4;

public class Udp {
    private String srcPrt;
    private String desPrt;
    private String length;
    private String checkSum;
    private String udpPayload;

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

    public String getUdpPayload() {
        return udpPayload;
    }

    public void setUdpPayload(String udpPayload) {
        this.udpPayload = udpPayload;
    }
}

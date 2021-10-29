package Layer3;

public class Ip {

    private String type;
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
    private String data;

    public Ip() { }

    public Ip(String data) {
        //Init the parameters.
    }

    public void display() {
        //Display parameters in a formatted way.
    }

    public void nextLayer() {
        //Calls NextLayer
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

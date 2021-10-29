package Layer3;

public class Icmp {

    private String type;
    private String code;
    private String checkSum;

    public Icmp() { }

    public Icmp(String data) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}

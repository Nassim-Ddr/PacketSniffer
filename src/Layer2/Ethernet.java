package Layer2;

public class Ethernet {

    private String desAdr;
    private String srcAdr;
    private String type;
    private String data;

    public Ethernet() { }

    public Ethernet(String s) {
        //Init the parameters.
    }

    public void display() {
        //Display parameters in a formatted way.
    }

    public void nextLayer() {
        //Calls NextLayer
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

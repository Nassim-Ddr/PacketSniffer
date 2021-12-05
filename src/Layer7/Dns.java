package Layer7;

import main.TraceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dns {
    //great example: https://www.firewall.cx/networking-topics/protocols/domain-name-system-dns/160-protocols-dns-query.html
    //another example for response: https://www.firewall.cx/networking-topics/protocols/domain-name-system-dns/161-protocols-dns-response.html


    private String transactionID;
    private String flags;
    private String questions;
    private String answerRRs;
    private String authorityRRs;
    private String additionalRRs;
    //Queries start from here :

    private String trame;


    private List<String[]> queries = new ArrayList<>(); //[0] : name; [1] : type; [3] : dnsClass
    private List<String[]> answers = new ArrayList<>();//[0] : name; [1] : type; [2] : class; [3] : ttl; [4] : datalength; [5] : cname
    private List<String[]> authoritative = new ArrayList<>();
    private List<String[]> additional = new ArrayList<>();

    public static Map<String, String> recordTypes  = new HashMap<String, String>() {{
        //Planning on putting options
        put("00 01", "A (HOST ADDRESS)");
        put("00 1C", "AAAA (IPV6 HOST ADDRESS)");
        put("00 05", "CNAME");
        put("00 0F", "MX (MAIL EXCHANGE)");
        put("00 02", "NS (NAME SERVER RECORD)");
        put("00 0C", "PTR (POINTER RESOURCE RECORD)");
    }};

    public Dns() {}
    public Dns(String trame) {
        this.trame = trame;
        transactionID = TraceManager.getByteInRange(trame, 1, 2);
        flags = TraceManager.getByteInRange(trame, 3, 4);
        questions = TraceManager.getByteInRange(trame, 5, 6);
        answerRRs = TraceManager.getByteInRange(trame, 7, 8);
        authorityRRs = TraceManager.getByteInRange(trame, 9, 10);
        additionalRRs = TraceManager.getByteInRange(trame, 11, 12);

        String name = "";
        String type = "";
        String dnsClass = "";
        String ttl = "";
        String dataLength = "";
        String data = "";

        //QUERY FIELD :
        // domain-name is represented as a series of labels.
        // A label consists of a length octet followed by that number of octets representing the name itself.
        // And is terminated by a label of length zero.
        int i = 13;
        while (!TraceManager.getByte(trame, i).equals("00")) i++;
        name = TraceManager.getByteInRange(trame, 13, i);

        type = TraceManager.getByteInRange(trame, i+1, i+2);
        dnsClass = TraceManager.getByteInRange(trame, i+3, i+4);
        String[] query = {name, type, dnsClass};
        queries.add(query);

        //ANSWER FIELD :
        i +=5; //i positioned to the first answer byte
        int nbrAnswers = Integer.parseInt(answerRRs.replace(" ", ""), 16);
        for (int j = 0; j < nbrAnswers; j++) {
            String  firstByte = TraceManager.getByte(trame, i);
            if (firstByte.charAt(0) == 'c') {
                //pointer to String
                name = TraceManager.getByteInRange(trame, i, i+1);
                i += 2;
            }else {
                //this is the name
                String offset = "c0 " + Integer.toHexString(i-1);
                while (!TraceManager.getByte(trame, i).equals("00")) i++;
                name = TraceManager.getByteInRange(trame, 13, i);
                i++;
            }
            type = TraceManager.getByteInRange(trame, i, i+1);
            dnsClass = TraceManager.getByteInRange(trame, i+2, i+3);
            i +=4;
            ttl = TraceManager.getByteInRange(trame, i, i+3);
            i +=4;
            dataLength = TraceManager.getByteInRange(trame, i, i+1);
            data = TraceManager.getByteInRange(trame, i+2, i + 1 + Integer.parseInt(dataLength.replace(" ", ""), 16));
            i += 2 + Integer.parseInt(dataLength.replace(" ", ""), 16); //I now points to second answer

            String[] answer = {name, type, dnsClass, ttl, dataLength, data};
            answers.add(answer);
        }

        int nbrAuthoritative = Integer.parseInt(authorityRRs.replace(" ", ""), 16);
        for (int j = 0; j < nbrAuthoritative; j++) {
            String  firstByte = TraceManager.getByte(trame, i);
            if (firstByte.charAt(0) == 'c') {
                //pointer to String
                name = TraceManager.getByteInRange(trame, i, i+1);
                i += 2;
            }else {
                //this is the name
                while (!TraceManager.getByte(trame, i).equals("00")) i++;
                name = TraceManager.getByteInRange(trame, 13, i);
                i++;
            }
            type = TraceManager.getByteInRange(trame, i, i+1);
            dnsClass = TraceManager.getByteInRange(trame, i+2, i+3);
            i +=4;
            ttl = TraceManager.getByteInRange(trame, i, i+3);
            i +=4;
            dataLength = TraceManager.getByteInRange(trame, i, i+1);
            data = TraceManager.getByteInRange(trame, i+2, i + 1 + Integer.parseInt(dataLength.replace(" ", ""), 16));
            i += 2 + Integer.parseInt(dataLength.replace(" ", ""), 16); //I now points to second answer

            String[] autho = {name, type, dnsClass, ttl, dataLength, data};
            authoritative.add(autho);
        }

        int nbrAdditional = Integer.parseInt(additionalRRs.replace(" ", ""), 16);
        for (int j = 0; j < nbrAdditional; j++) {
            String  firstByte = TraceManager.getByte(trame, i);
            if (firstByte.charAt(0) == 'c') {
                //pointer to String
                name = TraceManager.getByteInRange(trame, i, i+1);
                i += 2;
            }else {
                //this is the name
                while (!TraceManager.getByte(trame, i).equals("00")) i++;
                name = TraceManager.getByteInRange(trame, 13, i);
                i++;
            }
            type = TraceManager.getByteInRange(trame, i, i+1);
            dnsClass = TraceManager.getByteInRange(trame, i+2, i+3);
            i +=4;
            ttl = TraceManager.getByteInRange(trame, i, i+3);
            i +=4;
            dataLength = TraceManager.getByteInRange(trame, i, i+1);
            data = TraceManager.getByteInRange(trame, i+2, i + 1 + Integer.parseInt(dataLength.replace(" ", ""), 16));
            i += 2 + Integer.parseInt(dataLength.replace(" ", ""), 16); //I now points to second answer

            String[] additional = {name, type, dnsClass, ttl, dataLength, data};
            authoritative.add(additional);
        }




    }

    public void display() {
        System.out.println("DNS : ");
        System.out.println("\tTransaction ID : " + transactionID);
        System.out.println("\tFlags : " + flags);
        System.out.println("\tQuestions : " + questions + " (" + Integer.parseInt(questions.replace(" ", ""), 16) +")");
        System.out.println("\tAnswer RRs : " + answerRRs + " (" + Integer.parseInt(answerRRs.replace(" ", ""), 16) +")");
        System.out.println("\tAuthority RRs : " + authorityRRs + " (" + Integer.parseInt(authorityRRs.replace(" ", ""), 16) +")");
        System.out.println("\tAdditional RRs : " + additionalRRs + " (" + Integer.parseInt(additionalRRs.replace(" ", ""), 16) +")");
        System.out.println("\tQueries : ");


        for (String[] query : queries) {
            System.out.println("\t\tName : "  + hexLabelsToAscii(query[0])+"   ("+ query[0]+ ")");
            System.out.println("\t\tType : " + query[1] + " =>" + recordTypes.get(query[1]));
            System.out.println("\t\tClass : " + query[2]);
        }


        System.out.println("\tAnswers : ");
        int i = 1;
        for (String[] answer : answers) {
            System.out.println("\t\tAnswer : " + i);
            System.out.println("\t\t\tName : " + answer[0] + "  ("+ hexLabelsToAscii(answer[0]) +")");
            System.out.println("\t\t\tType : " + answer[1]+ " =>" + recordTypes.get(answer[1]));
            System.out.println("\t\t\tClass : " + answer[2]);
            System.out.println("\t\t\tTtl : " + answer[3] + "  (" + Integer.parseInt(answer[3].replace(" ", ""), 16) + ")");
            System.out.println("\t\t\tData length : " + answer[4]);
            if (answer[1].equals("00 01"))
                System.out.println("\t\t\tData : " + answer[5] + "(" + TraceManager.hexToIP(answer[5])+")");
            else if (answer[1].equals("00 01") || answer[1].equals("00 02") || answer[1].equals("00 05"))
                System.out.println("\t\t\tData : " + answer[5] + "(" + hexLabelsToAscii(answer[5])+")");
            else System.out.println("\t\t\tData : " + answer[5]);
            i++;
        }


        System.out.println("\tAuthoritative name servers : ");
        i = 1;
        for (String[] autho : authoritative) {
            System.out.println("\t\tAnswer : " + i);
            System.out.println("\t\t\tName : " + autho[0] + "  ("+ hexLabelsToAscii(autho[0]) +")");
            System.out.println("\t\t\tType : " + autho[1]+ " =>" + recordTypes.get(autho[1]));
            System.out.println("\t\t\tClass : " + autho[2]);
            System.out.println("\t\t\tTtl : " + autho[3] + "  (" + Integer.parseInt(autho[3].replace(" ", ""), 16) + ")");
            System.out.println("\t\t\tData length : " + autho[4]);

            if (autho[1].equals("00 01"))
                System.out.println("\t\t\tData : " + autho[5] + "(" + TraceManager.hexToIP(autho[5])+")");
            else if (autho[1].equals("00 01") || autho[1].equals("00 02"))
                System.out.println("\t\t\tData : " + autho[5] + "(" + hexLabelsToAscii(autho[5])+")");
            else System.out.println("\t\t\tData : " + autho[5]);

            i++;
        }


        System.out.println("\tAdditional records : ");
        i = 1;
        for (String[] add : additional) {
            System.out.println("\t\tAnswer : " + i);
            System.out.println("\t\t\tName : " + add[0] + "  ("+ hexLabelsToAscii(add[0]) +")");
            System.out.println("\t\t\tType : " + add[1]+ " =>" + recordTypes.get(add[1]));
            System.out.println("\t\t\tClass : " + add[2]);
            System.out.println("\t\t\tTtl : " + add[3] + "  (" + Integer.parseInt(add[3].replace(" ", ""), 16) + ")");
            System.out.println("\t\t\tData length : " + add[4]);
            if (add[1].equals("00 01"))
                System.out.println("\t\t\tData : " + add[5] + "(" + TraceManager.hexToIP(add[5])+")");
            else if (add[1].equals("00 01") || add[1].equals("00 02"))
                System.out.println("\t\t\tData : " + add[5] + "(" + hexLabelsToAscii(add[5])+")");
            else System.out.println("\t\t\tData : " + add[5]);
            i++;
        }
    }

    public void writeResults() {

    }

    private String hexLabelsToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        int length = hexStr.replace(" ", "").length()/2;
        int i = 1;
        boolean b = true;
        while( i < length & b) {
            String str = TraceManager.getByte(hexStr, i);
            //if (str.equals("00")) break;
            if (str.charAt(0) == 'c') {
                b = false;
                int offSet = Integer.parseInt(TraceManager.getByteInRange(hexStr, i, i+1).replace(" ", "").substring(1), 16);
                int j = 1;
                while (!TraceManager.getByte(trame, offSet + j).equals("00") & TraceManager.getByte(trame, offSet + j).charAt(0) != 'c') j++;
                if (TraceManager.getByte(trame, offSet + j).charAt(0) == 'c') j++;
                String s = hexLabelsToAscii(TraceManager.getByteInRange(trame, offSet + 1, offSet + j));
                output.append("."+s);
            }
            else {

                char c = (char) Integer.parseInt(str, 16);
                if (Character.isLetterOrDigit(c))
                    output.append(c);
                else output.append('.');
            }
            i += 1;

        }
        if (output.charAt(0) == '.') output.deleteCharAt(0);
        if (output.charAt(output.length()-1) == '.') output.deleteCharAt(output.length()-1);
        return output.toString();
    }

    /*
    * StringBuilder output = new StringBuilder("");
        String hexseq = hexStr.replace(" ", "");

        int i = 0;
        while( i < hexseq.length()) {
            String str = hexseq.substring(i, i+2);
            if (str.charAt(0) == 'c') {
                int offSet = Integer.parseInt(hexseq.substring(i+1, i+4), 16) + 1;
                int index = 1;
                while (!TraceManager.getByte(trame, index + offSet).equals("00")) index++;
                String offsetStr = TraceManager.getByteInRange(trame, offSet + 1, offSet+index - 1);
                output.append("."+hexLabelsToAscii(offsetStr));
                i += 4;
            }
            else {
                char c = (char) Integer.parseInt(str, 16);

                if (Character.isLetterOrDigit(c))
                    output.append(c);
                else output.append('.');

            }
            i += 2;
        }
        if (output.charAt(0) == '.') output.deleteCharAt(0);
        if (output.charAt(output.length()-1) == '.') output.deleteCharAt(output.length()-1);
        return output.toString();*/



    public void nextLayer() {

    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswerRRs() {
        return answerRRs;
    }

    public void setAnswerRRs(String answerRRs) {
        this.answerRRs = answerRRs;
    }

    public String getAuthorityRRs() {
        return authorityRRs;
    }

    public void setAuthorityRRs(String authorityRRs) {
        this.authorityRRs = authorityRRs;
    }

    public String getAdditionalRRs() {
        return additionalRRs;
    }

    public void setAdditionalRRs(String additionalRRs) {
        this.additionalRRs = additionalRRs;
    }
}

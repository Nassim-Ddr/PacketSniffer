package Layer7;

public class Dns {
    // VERY IMPORATNT : §§!!!!!
    //The ulr is stored as asciii code
    //u can find the cahrs in the Queries field and subfield name:
        //The field starts with the number (generally 04))
        //Than it is a sequence of ascii characters
        //Until a character (08) is read than is t an other part of the url
        //Then it ends with 03 + Sequence char + 00 : this sequence char is .com/.net.org ...
        //great example: https://www.firewall.cx/networking-topics/protocols/domain-name-system-dns/160-protocols-dns-query.html
    //In other words u need :
        //calculate the name field absed on the udp length AND the known fields of dns
        //Than read the hex as Ascii and u got the name :)
}

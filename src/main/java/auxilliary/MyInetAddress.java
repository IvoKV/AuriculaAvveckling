package auxilliary;

import java.net.UnknownHostException;

public class MyInetAddress {

    //private long inetAddress;


    public static void getIpAddress() throws UnknownHostException {

        var ip = java.net.InetAddress.getLocalHost();
        var hostname = ip.getHostName();
        //var address = ip.getAddress();


        System.out.println("Your current IP address : " + ip);
        System.out.println("Your current Hostname : " + hostname);
        //System.out.println("Your current address : " + address.toString());

    }

}

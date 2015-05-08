package utils;

import java.util.* ;
import java.net.InetAddress ;
import java.net.NetworkInterface ;
import java.net.SocketException ;
import java.net.UnknownHostException ;

public class Common {
    public static byte[] getMac() {
        byte[] mac = new byte[6] ;
        try {
            InetAddress inetAddr = InetAddress.getLocalHost();
            mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress() ;

        } catch (SocketException e) {
            e.printStackTrace() ;
        } catch (UnknownHostException e) {
            e.printStackTrace() ;
        }
        return mac ;
    }

    public static byte[] getMachineMacByIP() {
        try {
           /*
            * Above method often returns "127.0.0.1", In this case we need to
            * check all the available network interfaces
            */
            Enumeration<NetworkInterface> nInterfaces = NetworkInterface
                .getNetworkInterfaces();
            while (nInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = nInterfaces.nextElement() ;
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    String address = inetAddresses.nextElement()
                        .getHostAddress();
                    if (!address.equals("127.0.0.1")) {
                        return networkInterface.getHardwareAddress();
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error = " + e.getMessage());
        }
        return null;
    }

    public static boolean isChinese(char a) {
        int v = (int)a ;
        return (v >= 19968 && v <= 171941) ;
    }

    public static String toUnicode(String s) {
        //String as[] = new String[s.length()] ;
        //String s1 = "" ;
        //for (int i = 0; i < s.length(); i++) {
        //  if (isChinese(s.charAt(i))) {
        //      as[i] = Integer.toHexString(s.charAt(i) & 0xffff) ;
        //  } else {
        //      as[i] ="" + s.charAt(i) ;
        //  }
        //  s1 ="" + s1 + as[i] ;
        //}
        String s1 = new String("\\u") ;
        for (char ch : s.toCharArray()) {
            if (ch > 128) {
                s1 = s1 + Integer.toHexString(ch);
            } else {
                s1 = s1 + ch ;
            }
        }
        System.out.println(s1) ;
        return s ;
    }

    public static java.sql.Timestamp str2timestamp(String str) throws Exception{
        java.text.SimpleDateFormat df1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date11 = null;
        try {
            df1.parse(str);
        }catch(java.text.ParseException e){
            e.printStackTrace();
        }
        String time = df1.format(date11);
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(time);
        return ts;
    }

}

package ir.fararayaneh.erp.utils;


import ir.fararayaneh.erp.commons.CommonsFormat;



public class IPHandler {

    public static  String createForPing(String ip1, String ip2, String ip3, String ip4){
        return String.format(CommonsFormat.IP_FORMAT,ip1,ip2,ip3,ip4);
    }

    public static  String creatorForRest(String preIpPart, String ip, String lastIpPart){
        return String.format(CommonsFormat.IP_FORMAT3,preIpPart,ip,lastIpPart);
    }

    public static  String[] splitter(String ip){
        return ip.split(CommonsFormat.IP_FORMAT2);
    }
}

package ir.fararayaneh.erp.commons;

public class CommonsFormat {
    public static final String IP_FORMAT = "%1$s.%2$s.%3$s.%4$s";
    public static final String IP_FORMAT2 = "\\.";
    public static final String IP_FORMAT3 = "%1$s%2$s%3$s";
    public static final String FORMAT_1 = "%1s%2s";
    public static final String FORMAT_2 = "%1$s %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s %10$s %11$s";
    public static final String PRESENTATION_DATE_FORMAT = "%1$s %2$s:%3$s";
    public static final String PRESENTATION_DATE_FORMAT2 = "%1$s %2$s %3$s %4$s  %5$s:%6$s";
    public static final String FORMAT_3 = "%1s : %2s";
    public static final String DATE_SHARE_FORMAT_TO_STRING = "dd-MMM-yy HH:mm:ss"; //for send to oracle and receive from it
    public static final String DATE_SHARE_FORMAT_FROM_STRING = "%1$S-%2$s-%3$S %4$S:%5$S:%6$S"; //for send to oracle and receive from it
    public static final String CONTACT_FORMAT = "name:%1s , phone:%2s";
    public static final String FORMAT_4 = "%1s:%2s?permanentClient=0&id=%3s&fireBaseId=%4s"; //permanentClient : 0 or 1, if=1 ==> we are temp client(like web), else we are fix client (mobile)
    public static final String FORMAT_5 = "%1s : %2s\n%3s : %4s\n%5s : %6s\n%7s : %8s\n%9s : %10s\n%11s : %12s\n%13s : %14s\n%15s : %16s";
    public static final String FORMAT_6 = "%1s %2s ";
    public static final String FORMAT_7 = "%1s %2s %3s ";

}

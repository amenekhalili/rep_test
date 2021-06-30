package ir.fararayaneh.erp.utils.file_helper;

import android.net.Uri;

public class StringUriConvertor {

    public static String uriToString(Uri uri){
        return uri.toString();
    }

    public static Uri stringToUri(String uri){
        return Uri.parse(uri);
    }
}

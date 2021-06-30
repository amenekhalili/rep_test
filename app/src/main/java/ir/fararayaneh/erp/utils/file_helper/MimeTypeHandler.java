package ir.fararayaneh.erp.utils.file_helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.util.Objects;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;


public class MimeTypeHandler {

    /**
     * @return .mp3 and so on ...
     */
    public static String getFileSuffixFromURI(Context context, Uri uri){

        String suffix = Commons.NULL;
        String calculatedSuffix = getFileSuffixFromURIByCursor(context,uri);
        if(!calculatedSuffix.equals(Commons.NULL)){
            suffix = calculatedSuffix;
        } else if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            if(cr.getType(uri)!=null){
                suffix = cr.getType(uri).contains("/")?"."+cr.getType(uri).substring(cr.getType(uri).indexOf("/")+1):"."+cr.getType(uri);
            }
        } else {
            suffix = getFileSuffixFromFileExtension(StringUriConvertor.uriToString(uri));
        }

        return suffix.equals(Commons.NULL)?CommonsDownloadFile.IMAGE_SUFFIX_NAME:suffix;
    }
    //only call here
    private static String getFileSuffixFromFileExtension(String url){

        String mimeType= Commons.NULL;
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(url);
        if(fileExtension!=null){
            mimeType = "."+MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType.equals(Commons.NULL)?CommonsDownloadFile.IMAGE_SUFFIX_NAME:mimeType;
    }

    /**
     * @return .mp3 and so on ...//only call here
     */
    private static String getFileSuffixFromURIByCursor(Context context,Uri uri) {

        String fileExtension = Commons.NULL;
        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i("arash", "Display Name: " + displayName);

                if(displayName.contains(Commons.DOT) && !displayName.contains("/")){
                    fileExtension = displayName.substring(displayName.lastIndexOf(Commons.DOT));
                }

                /*int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i("arash", "Size: " + size);*/
            }
        } catch (Exception e){
            Log.i("arash", "Size: exeption" );
        }finally {
            if(cursor!=null){
            cursor.close();
            }
        }
        return fileExtension;
    }


   /**
    * return value >>>> .mp3 or .doc or ....
    * @param fileName : GUID.Suffix
    */
    public static String getFileSuffixFromName(String fileName){
        Log.i("arash", "getFileSuffixFromName1: "+fileName);
        String suffix = Commons.SPACE;
        if(fileName.contains(Commons.DOT)){
            suffix = fileName.substring(fileName.lastIndexOf(Commons.DOT));
        }
        Log.i("arash", "getFileSuffixFromName2: "+fileName);

        if(suffix.equals(Commons.SPACE)){
            return  CommonsDownloadFile.IMAGE_SUFFIX_NAME;
        }
        return suffix.contains("/")?CommonsDownloadFile.IMAGE_SUFFIX_NAME:suffix;//--->> by default we send .jpg as suffix
    }

    /**
     * @param fileName : GUID.Suffix
     */
    public static String getGUIDFromName(String fileName){
        Log.i("arash", "getGUIDFromName1: "+fileName);
        String name = "";
        if(fileName.contains(".")){
            name = fileName.substring(0,fileName.lastIndexOf("."));
        }
        Log.i("arash", "getGUIDFromName2: "+name);
        return name;
    }

    /**
     * @param fileName : GUID.Suffix
     */
    public static String getURLFromName(String fileName){
        return SharedPreferenceProvider.getBaseAttachIp(App.getAppContext())+fileName;
    }

    /**
     * @param fileName : GUID.Suffix
     */
    public static String getFilePathFromName(String folderName,String fileName){
        return CreateFolderFileHandler.createFile(folderName, fileName).getAbsolutePath();
    }

    public static String getFolderPathFromName(String folderName){
        return Objects.requireNonNull(CreateFolderFileHandler.createFolder(folderName)).getAbsolutePath();
    }


    public static String suffixToMimeType(String fileSuffix){

        if(fileSuffix.contains(".aac")){
            return "audio/aac";
        } else if(fileSuffix.contains(".abw")){
            return "application/x-abiword";
        }else if(fileSuffix.contains(".arc")){
            return "application/x-freearc";
        }else if(fileSuffix.contains(".avi")){
            return "video/x-msvideo";
        }else if(fileSuffix.contains(".azw")){
            return "application/vnd.amazon.ebook";
        }else if(fileSuffix.contains(".bin")){
            return "application/octet-stream";
        }else if(fileSuffix.contains(".octet-stream")){
            return "application/octet-stream";
        }else if(fileSuffix.contains(".bmp")){
            return "image/bmp";
        }else if(fileSuffix.contains(".bz")){
            return "application/x-bzip";
        }else if(fileSuffix.contains(".bz2")){
            return "application/x-bzip2";
        }else if(fileSuffix.contains(".csh")){
            return "application/x-csh";
        }else if(fileSuffix.contains(".css")){
            return "text/css";
        }else if(fileSuffix.contains(".csv")){
            return "text/csv";
        }else if(fileSuffix.contains(".doc")){
            return "application/msword";
        }else if(fileSuffix.contains(".docx")){
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }else if(fileSuffix.contains(".eot")){
            return "application/vnd.ms-fontobject";
        }else if(fileSuffix.contains(".epub")){
            return "application/epub+zip";
        }else if(fileSuffix.contains(".gz")){
            return "application/gzip";
        }else if(fileSuffix.contains(".gif")){
            return "image/gif";
        }else if(fileSuffix.contains(".htm")){
            return "text/html";
        }else if(fileSuffix.contains(".html")){
            return "text/html";
        }else if(fileSuffix.contains(".ico")){
            return "image/vnd.microsoft.icon";
        }else if(fileSuffix.contains(".ics")){
            return "text/calendar";
        }else if(fileSuffix.contains(".jar")){
            return "application/java-archive";
        }else if(fileSuffix.contains(".jpeg")){
            return "image/jpeg";
        }else if(fileSuffix.contains(".jpg")){
            return "image/jpeg";
        }else if(fileSuffix.contains(".js")){
            return "text/javascript";
        }else if(fileSuffix.contains(".json")){
            return "application/json";
        }else if(fileSuffix.contains(".jsonld")){
            return "application/ld+json";
        }else if(fileSuffix.contains(".mid")){
            return "audio/midi";
        }else if(fileSuffix.contains(".midi")){
            return "audio/x-midi";
        }else if(fileSuffix.contains(".mjs")){
            return "text/javascript";
        }else if(fileSuffix.contains(".mp3")){
            return "audio/mpeg";
        }else if(fileSuffix.contains(".mpeg")){
            return "video/mpeg";
        }else if(fileSuffix.contains(".mpkg")){
            return "application/vnd.apple.installer+xml";
        }else if(fileSuffix.contains(".odp")){
            return "application/vnd.oasis.opendocument.presentation";
        }else if(fileSuffix.contains(".ods")){
            return "application/vnd.oasis.opendocument.spreadsheet";
        }else if(fileSuffix.contains(".odt")){
            return "application/vnd.oasis.opendocument.text";
        }else if(fileSuffix.contains(".oga")){
            return "audio/ogg";
        }else if(fileSuffix.contains(".ogv")){
            return "video/ogg";
        }else if(fileSuffix.contains(".ogx")){
            return "application/ogg";
        }else if(fileSuffix.contains(".otf")){
            return "font/otf";
        }else if(fileSuffix.contains(".png")){
            return "image/png";
        }else if(fileSuffix.contains(".pdf")){
            return "application/pdf";
        }else if(fileSuffix.contains(".php")){
            return "appliction/php";
        }else if(fileSuffix.contains(".ppt")){
            return "application/vnd.ms-powerpoint";
        }else if(fileSuffix.contains(".pptx")){
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }else if(fileSuffix.contains(".rar")){
            return "application/x-rar-compressed";
        }else if(fileSuffix.contains(".rtf")){
            return "application/rtf";
        }else if(fileSuffix.contains(".sh")){
            return "application/x-sh";
        }else if(fileSuffix.contains(".svg")){
            return "image/svg+xml";
        }else if(fileSuffix.contains(".swf")){
            return "application/x-shockwave-flash";
        }else if(fileSuffix.contains(".tar")){
            return "application/x-tar";
        }else if(fileSuffix.contains(".tif")){
            return "image/tiff";
        }else if(fileSuffix.contains(".tiff")){
            return "image/tiff";
        }else if(fileSuffix.contains(".ts")){
            return "video/mp2t";
        }else if(fileSuffix.contains(".ttf")){
            return "font/ttf";
        }else if(fileSuffix.contains(".txt")){
            return "text/plain";
        }else if(fileSuffix.contains(".vsd")){
            return "application/vnd.visio";
        }else if(fileSuffix.contains(".wav")){
            return "audio/wav";
        }else if(fileSuffix.contains(".weba")){
            return "audio/webm";
        }else if(fileSuffix.contains(".webm")){
            return "video/webm";
        }else if(fileSuffix.contains(".webp")){
            return "image/webp";
        }else if(fileSuffix.contains(".woff")){
            return "font/woff";
        }else if(fileSuffix.contains(".woff2")){
            return "font/woff2";
        }else if(fileSuffix.contains(".xhtml")){
            return "application/xhtml+xml";
        }else if(fileSuffix.contains(".xls")){
            return "application/vnd.ms-excel";
        }else if(fileSuffix.contains(".xlsx")){
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }else if(fileSuffix.contains(".xml")){
            return "application/xml";
        }else if(fileSuffix.contains(".xul")){
            return "application/vnd.mozilla.xul+xml";
        }else if(fileSuffix.contains(".zip")){
            return "application/zip";
        }else if(fileSuffix.contains(".3gp")){
            return "video/3gpp";
        }else if(fileSuffix.contains(".3g2")){
            return "video/3gpp2";
        }else if(fileSuffix.contains(".7z")){
            return "application/x-7z-compressed";
        }else if (fileSuffix.contains(".apk")) {
            return "application/vnd.android.package-archive";
        } else{
            return "image/png";
        }
    }


}

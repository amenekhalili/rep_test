package ir.fararayaneh.erp.utils.file_helper;

import java.io.File;

public class FileSizeHelper {

    /**
     * @return in kb
     */
    public static int getFileSize(File file){

        return Integer.parseInt(String.valueOf(file.length()/1024));
    }
}

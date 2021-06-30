package ir.fararayaneh.erp.utils.file_helper;


import android.os.Environment;
import java.io.File;
import java.util.Objects;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;


public class CreateFolderFileHandler {

    public static File createFolder(String folderName){

        File myFolder = new File(Environment.getExternalStorageDirectory(), folderName);
        if (!myFolder.exists()) {
            if(myFolder.mkdirs()){
                return myFolder;
            } else {
                ThrowableLoggerHelper.logMyThrowable("/***CreateFolderFileHandler***createFolder ...!myFolder.mkdirs()");
                return null;
            }
        }
        return myFolder;
    }

    public static File createFile(String folderName,String fileName){
        return new File( Objects.requireNonNull(createFolder(folderName)).getPath(),fileName);
    }

}

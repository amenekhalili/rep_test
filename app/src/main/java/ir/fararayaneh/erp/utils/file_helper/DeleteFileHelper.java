package ir.fararayaneh.erp.utils.file_helper;

import java.io.File;
import java.io.IOException;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class DeleteFileHelper {

    /**
     * delete file if is exist
     * @param filePath
     */

    public static boolean delete(String filePath){

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                try {
                    if(file.getCanonicalFile().delete()){
                        return true;
                    } else {
                        return App.getAppContext().deleteFile(file.getName());
                    }
                } catch (IOException e) {
                    ThrowableLoggerHelper.logMyThrowable(e.getMessage());
                    return false;
                }
            }
        } else {
            return true;
        }
    }
}

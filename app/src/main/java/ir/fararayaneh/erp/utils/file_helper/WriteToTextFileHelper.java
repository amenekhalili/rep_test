package ir.fararayaneh.erp.utils.file_helper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class WriteToTextFileHelper {

    /**
     * @param txtBody      : the text that should be write in file
     */
    public static File CreateAndWrite(String textFileName, String txtBody) {
        File textFile = CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, textFileName);
        try {
            FileWriter writer = new FileWriter(textFile);
            writer.append(txtBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            ThrowableLoggerHelper.logMyThrowable("WriteToTextFileHelper/CreateAndWrite"+e.getMessage());
        }
        return textFile;
    }
}

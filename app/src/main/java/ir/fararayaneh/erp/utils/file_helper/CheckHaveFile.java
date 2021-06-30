package ir.fararayaneh.erp.utils.file_helper;

import java.io.File;

//deprecated if use AndroidNetWorking

public class CheckHaveFile {

    public static boolean haveFile(String folderName,String fileName){

        File myFolder= CreateFolderFileHandler.createFolder(folderName);
        if(myFolder!=null){
        File  myFile = new File(myFolder, fileName);
            return myFile.exists();
        } else {
            return false;
        }

    }
}

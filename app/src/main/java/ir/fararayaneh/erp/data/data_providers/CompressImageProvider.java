package ir.fararayaneh.erp.data.data_providers;

import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;
import id.zelory.compressor.Compressor;
import io.reactivex.Flowable;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.FileToUriHelper;


/**
 * the compressed image write on original image
 */
public class CompressImageProvider implements IDataProvider<Flowable<File>> {

    private static final int MAX_CROPPED_WIDTH = 640;
    private static final int MAX_CROPPED_HEIGHT = 480;
    private static final int QUALITY = 75;

    @Override
    public Flowable<File> data(IModels iModels) {

        final GlobalModel globalModel =(GlobalModel)iModels;
       //------------------------------------------------------------------------>>

        CreateFolderFileHandler.createFolder(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME);
        File tempFile = FileToUriHelper.getFile(globalModel.getContext(),CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,CommonsDownloadFile.TEMP_FILE_NAME,globalModel.getUri());

        return new Compressor(globalModel.getContext())
                .setMaxWidth(MAX_CROPPED_WIDTH)
                .setMaxHeight(MAX_CROPPED_HEIGHT)
                .setQuality(QUALITY)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME)
                .compressToFileAsFlowable(tempFile,SharedPreferenceProvider.getCurrentAttachmentName(globalModel.getContext()));
    }

}

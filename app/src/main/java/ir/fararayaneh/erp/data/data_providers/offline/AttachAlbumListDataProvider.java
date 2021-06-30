package ir.fararayaneh.erp.data.data_providers.offline;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.IDataProvider;
import ir.fararayaneh.erp.data.models.GlobalModel;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.middle.AttachAlbumRecycleModel;
import ir.fararayaneh.erp.data.models.middle.DownloadState;
import ir.fararayaneh.erp.utils.file_helper.CheckHaveFile;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.file_helper.DeleteFileHelper;
import ir.fararayaneh.erp.utils.file_helper.FileSizeHelper;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

/**
 * create before download list of AttachAlbumRecycleModel
 */
public class AttachAlbumListDataProvider implements IDataProvider<List<AttachAlbumRecycleModel>> {
    @Override
    public List<AttachAlbumRecycleModel> data(IModels iModels) {


        GlobalModel globalModel = (GlobalModel) iModels;

        ArrayList<String> fileNameArrayList = globalModel.getStringArrayList();

        ArrayList<AttachAlbumRecycleModel> attachAlbumRecycleModelsList = new ArrayList<>();

        for (int i = 0; i < fileNameArrayList.size(); i++) {
            AttachAlbumRecycleModel attachAlbumRecycleModel =
                    new AttachAlbumRecycleModel(fileNameArrayList.get(i),
                            getProperDownloadState(fileNameArrayList.get(i)),
                            globalModel.isMyBoolean());
            attachAlbumRecycleModelsList.add(attachAlbumRecycleModel);
        }

        return attachAlbumRecycleModelsList;
    }

    /**
     * گر ما فایل را داریم اما سایز آن از50  کیلوبایت کمتر است
     * بهتر است فایل پاک شده و دوباره دانلود شود
     * چون ممکن است مربوط به دانلود نامناسب باشد که مجدد باید دانلود شود
     */
    private DownloadState getProperDownloadState(String fileName) {
        if( CheckHaveFile.haveFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,fileName)){
            if(FileSizeHelper.getFileSize(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,fileName))
                    < CommonsDownloadFile.MINIMUM_FILE_SIZE_FOR_DOWNLOAD){
                DeleteFileHelper.delete(MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME,fileName));
                return DownloadState.BEFORE_DOWNLOAD;
            }
            return DownloadState.DOWNLOAD_COMPLETE;
        }
        return DownloadState.BEFORE_DOWNLOAD;
    }
}

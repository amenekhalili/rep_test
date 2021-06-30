package ir.fararayaneh.erp.data.data_providers.queries;

import io.reactivex.Single;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.utils.database_handler.RealmCloseHelper;
import ir.fararayaneh.erp.utils.file_helper.CreateFolderFileHandler;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;

public class AllDataBaseBackupQuery extends BaseQueries {

    @Override
    public Single<IModels> data(final IModels iModels) {
        super.data(iModels);
        return Single.create(emitter -> realm.executeTransactionAsync(realm -> realm.writeCopyTo(CreateFolderFileHandler.createFile(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, CommonsDownloadFile.REALM_BACKUP_BASE_NAME +"-"+ SharedPreferenceProvider.getUserId(App.getAppContext()) +"-"+ CustomTimeHelper.getCurrentDate().getTime())),
                () -> {
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(App.getNullRXModel());
                    }
                    RealmCloseHelper.closeRealm(realm);
                }
                , error -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(error);
                    }
                    ThrowableLoggerHelper.logMyThrowable("AllDataBaseBackupQuery***data/" + error.getMessage());
                    RealmCloseHelper.closeRealm(realm);
                }
        ));
    }
}

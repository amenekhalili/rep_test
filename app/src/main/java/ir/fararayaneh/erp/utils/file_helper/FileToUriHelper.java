package ir.fararayaneh.erp.utils.file_helper;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class FileToUriHelper {

    /**
     *قاعدتا فقط زمانی میشود getUri
     * انجام داد که فایل ما روی sdCart ذخیره باشد
     */
    public static Uri getUri(File file, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Uri.fromFile(file);
        } else {
            return FileProvider.getUriForFile(context, CommonsDownloadFile.AUTHORITIES_FILE_PROVIDER, file);
        }
    }


    /**
     * attention :
     *  fileName or  folderName must be different from file name of resource file of uri
     *                نمیتوان همان فایل را درون همان فایل ریخت !
     */

    // TODO: 1/14/2019 create in another tread for huge file
    public static File getFile(Context context, String folderName, String fileName, Uri uri) {

        File file = new File(CreateFolderFileHandler.createFolder(folderName), fileName);
        int maxBufferSize = 1024 * 1024;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            int bytesAvailable = Objects.requireNonNull(inputStream).available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            FileOutputStream outputStream = new FileOutputStream(file);
            int read;
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            ThrowableLoggerHelper.logMyThrowable("FileToUriHelper**getFile/" + e.getMessage());
        }

        return file;
    }


}

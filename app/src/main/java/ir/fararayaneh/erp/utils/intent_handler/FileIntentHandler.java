package ir.fararayaneh.erp.utils.intent_handler;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.commons.CommonRequestCodes;
import ir.fararayaneh.erp.commons.CommonsLogErrorNumber;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class FileIntentHandler {

    public static void goToFile(BaseActivity activity) {

        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            //Intent intent2 = Intent.createChooser(intent, activity.getResources().getString(R.string.error));
            activity.startActivityForResult(intent, CommonRequestCodes.FILE);
        } catch (Exception e) {
            if (activity.getView() != null) {
                activity.getView().showMessageSomeProblems(CommonsLogErrorNumber.error_137);
            }
        }
    }


    public static void openExistFile(Context context, Uri mUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (mUri.toString().contains(".doc") || mUri.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(mUri, "application/msword");
        } else if (mUri.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(mUri, "application/pdf");
        } else if (mUri.toString().contains(".ppt") || mUri.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(mUri, "application/vnd.ms-powerpoint");
        } else if (mUri.toString().contains(".xls") || mUri.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(mUri, "application/vnd.ms-excel");
        } else if (mUri.toString().contains(".zip") || mUri.toString().contains(".rar")) {
            // ZIP Files
            intent.setDataAndType(mUri, "application/zip");
        } else if (mUri.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(mUri, "application/rtf");
        } else if (mUri.toString().contains(".wav") || mUri.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(mUri, "audio/x-wav");
        } else if (mUri.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(mUri, "image/gif");
        } else if (mUri.toString().contains(".jpg") || mUri.toString().contains(".jpeg") || mUri.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(mUri, "image/jpeg");
        } else if (mUri.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(mUri, "text/plain");
        } else if (mUri.toString().contains(".3gp") || mUri.toString().contains(".mpg") || mUri.toString().contains(".mpeg") || mUri.toString().contains(".mpe") || mUri.toString().contains(".mp4") || mUri.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(mUri, "video/*");
            // Apk files
        } else if (mUri.toString().contains(".apk")) {
            intent.setDataAndType(mUri, "application/vnd.android.package-archive");
            Log.i("arash", "openExistFile: apk apk apk apk " + mUri.getPath());
        } else {
            intent.setDataAndType(mUri, "*/*");// <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ThrowableLoggerHelper.logMyThrowable(e.getMessage() + "/***FileIntentHandler***openExistFile");

        }
    }

}

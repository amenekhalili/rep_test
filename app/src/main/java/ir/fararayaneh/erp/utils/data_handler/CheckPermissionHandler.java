package ir.fararayaneh.erp.utils.data_handler;

import android.Manifest;
import android.content.Context;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import io.reactivex.Single;
import ir.fararayaneh.erp.R;

// TODO: 1/12/2019 add new permission here

public class CheckPermissionHandler {

    public static Single<TedPermissionResult> getAttachPermissions(Context context){

        return TedRx2Permission.with(context)
                .setRationaleTitle(context.getString(R.string.access_permission))
                .setRationaleMessage(context.getString(R.string.access_permission_message))
                .setRationaleConfirmText(context.getString(R.string.ok))
                .setPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS
                )
                .setGotoSettingButton(true)
                .setDeniedCloseButtonText(context.getString(R.string.cancel))
                .setDeniedTitle(context.getString(R.string.access_permission))
                .setDeniedMessage(context.getString(R.string.access_permission_message))
                .setGotoSettingButtonText(context.getString(R.string.setting))
                .request();
    }



    public static Single<TedPermissionResult> getLocationPermissions(Context context){
        return TedRx2Permission.with(context)
                .setRationaleTitle(context.getString(R.string.access_permission))
                .setRationaleMessage(context.getString(R.string.access_permission_message))
                .setRationaleConfirmText(context.getString(R.string.ok))
                .setPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .setGotoSettingButton(true)
                .setDeniedCloseButtonText(context.getString(R.string.cancel))
                .setDeniedTitle(context.getString(R.string.access_permission))
                .setDeniedMessage(context.getString(R.string.access_permission_message))
                .setGotoSettingButtonText(context.getString(R.string.setting))
                .request();
    }

    public static Single<TedPermissionResult> getCameraPermissions(Context context){

        return TedRx2Permission.with(context)
                .setRationaleTitle(context.getString(R.string.access_permission))
                .setRationaleMessage(context.getString(R.string.access_permission_message))
                .setRationaleConfirmText(context.getString(R.string.ok))
                .setPermissions(Manifest.permission.CAMERA)
                .setGotoSettingButton(true)
                .setDeniedCloseButtonText(context.getString(R.string.cancel))
                .setDeniedTitle(context.getString(R.string.access_permission))
                .setDeniedMessage(context.getString(R.string.access_permission_message))
                .setGotoSettingButtonText(context.getString(R.string.setting))
                .request();
    }

    /**
     * needed for db backUp, upload and download apk or file
     */
    public static Single<TedPermissionResult> getFilePermissions(Context context){

        return TedRx2Permission.with(context)
                .setRationaleTitle(context.getString(R.string.access_permission))
                .setRationaleMessage(context.getString(R.string.access_permission_message))
                .setRationaleConfirmText(context.getString(R.string.ok))
                .setPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .setGotoSettingButton(true)
                .setDeniedCloseButtonText(context.getString(R.string.cancel))
                .setDeniedTitle(context.getString(R.string.access_permission))
                .setDeniedMessage(context.getString(R.string.access_permission_message))
                .setGotoSettingButtonText(context.getString(R.string.setting))
                .request();
    }


    public static Single<TedPermissionResult> getFileAndLocationPermissions(Context context){

        return TedRx2Permission.with(context)
                .setRationaleTitle(context.getString(R.string.access_permission))
                .setRationaleMessage(context.getString(R.string.access_permission_message))
                .setRationaleConfirmText(context.getString(R.string.ok))
                .setPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .setGotoSettingButton(true)
                .setDeniedCloseButtonText(context.getString(R.string.cancel))
                .setDeniedTitle(context.getString(R.string.access_permission))
                .setDeniedMessage(context.getString(R.string.access_permission_message))
                .setGotoSettingButtonText(context.getString(R.string.setting))
                .request();
    }
}

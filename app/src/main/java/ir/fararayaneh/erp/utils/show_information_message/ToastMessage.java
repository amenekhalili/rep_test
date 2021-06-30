package ir.fararayaneh.erp.utils.show_information_message;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;


import com.pd.chocobar.ChocoBar;

import java.util.Objects;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.custom_ios_dialog.iOSDialog;
import ir.fararayaneh.erp.custom_views.custom_ios_dialog.iOSDialogBuilder;
import ir.fararayaneh.erp.utils.GetATTResources;
import ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.IIOSDialogeListener;

public class ToastMessage {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void nonUiToast(Context context, final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> show(context,msg));
    }

    // TODO: 1/20/2019 remove leak of  iOSDialog listener
    public static void showIosDialog(Context context, String title, String message, boolean cancelable) {
        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(cancelable)
                .setPositiveListener(context.getString(R.string.ok), iOSDialog::dismiss)
                .build().show();
    }

    // TODO: 1/20/2019 remove leak of  iOSDialog listener
    public static void showIosDialogType2(Context context, String title, String message, String btnText, boolean cancelable, IIOSDialogeListener iIOSDialogeListener) {
        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(cancelable)
                .setPositiveListener(btnText, iOSDialog -> {
                    if (iIOSDialogeListener != null) {
                        iIOSDialogeListener.confirm();
                    }
                    iOSDialog.dismiss();
                })
                .build().show();
    }


    public static void showIosDialogTypeConfirmCancle(Context context, String title, String message, String btnPositiveText,String btnNegativeText, boolean cancelable, IIOSDialogeListener iIOSDialogeListener) {
        new iOSDialogBuilder(context)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(true)
                .setCancelable(cancelable)
                .setPositiveListener(btnPositiveText, iOSDialog -> {
                    if (iIOSDialogeListener != null) {
                        iIOSDialogeListener.confirm();
                    }
                    iOSDialog.dismiss();
                })
                .setNegativeListener(btnNegativeText,iOSDialog -> {
                    if (iIOSDialogeListener != null) {
                        iIOSDialogeListener.cancle();
                    }
                    iOSDialog.dismiss();
                })
                .build().show();
    }

    public static void showRedSnack(Activity activity, String text) {
        ChocoBar.builder().setActivity(activity)
                .setText(text)
                .setDuration(ChocoBar.LENGTH_LONG)
                .red()
                .show();
    }

    public static void showOrangeSnack(Activity activity, String text) {
        ChocoBar.builder().setActivity(activity)
                .setText(text)
                .setDuration(ChocoBar.LENGTH_LONG)
                .orange()
                .show();
    }

    public static void showGreenSnack(Activity activity, String text) {
        ChocoBar.builder().setActivity(activity)
                .setText(text)
                .setDuration(ChocoBar.LENGTH_LONG)
                .green()
                .show();
    }

    public static void showGreenSnackWithAction(Activity activity, String text, String action, View.OnClickListener actionListener) {
        ChocoBar.builder().setActivity(activity)
                .setText(text)
                .setActionText(action)
                .setActionTextColor(GetATTResources.resId(activity, new int[]{R.attr.colorRedAttr}))
                .setActionClickListener(actionListener)
                .setDuration(ChocoBar.LENGTH_LONG)
                .green()
                .show();
    }

    public static void showSimpleScrollAbleDialog(Activity activity, String text) {
        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.alert_simple_dialoge, null);
        final AppCompatTextView textView = v.findViewById(R.id.txt_alert_simple_dialoge);
        textView.setText(text);
        adb.setView(v);
        adb.create();
        adb.setCancelable(true);
        adb.show();
    }


}

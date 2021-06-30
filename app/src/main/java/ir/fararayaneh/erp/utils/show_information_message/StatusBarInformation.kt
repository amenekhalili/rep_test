package ir.fararayaneh.erp.utils.show_information_message

import android.app.Activity
import android.util.Log
import com.fede987.statusbaralert.StatusBarAlert
import com.fede987.statusbaralert.StatusBarAlertView
import ir.fararayaneh.erp.R
import ir.fararayaneh.erp.utils.GetATTResources


fun showStatusBarNotif(activity: Activity, msg: String) {

    val statusBarAlertview: StatusBarAlertView? =
            StatusBarAlert.Builder(activity)
                    .autoHide(false)
                    //.withDuration(1000000)
                    .showProgress(true)
                    .withText(msg)
                    .withAlertColor(GetATTResources.resId(activity, intArrayOf(R.attr.colorRedAttr)))
                    .build()
    statusBarAlertview?.showIndeterminateProgress()
}

fun hideStatusBar(activity: Activity) {
    StatusBarAlert.hide(activity, Runnable {})
}

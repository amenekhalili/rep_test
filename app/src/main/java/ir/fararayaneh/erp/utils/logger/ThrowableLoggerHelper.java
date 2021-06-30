package ir.fararayaneh.erp.utils.logger;
import android.content.Context;
import android.util.Log;
import ir.fararayaneh.erp.R;

public class ThrowableLoggerHelper {

    public static String rebuildMyThrowable(String msg, Context context) {
        Log.i("arash", "rebuildMyThrowable: " + msg);
        return context.getString(R.string.some_problem_error);
    }

    public static void logMyThrowable(String msg) {
        Log.i("arash", "MyThrowable: " + msg);
    }
}

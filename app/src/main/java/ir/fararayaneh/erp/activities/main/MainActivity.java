package ir.fararayaneh.erp.activities.main;


import android.annotation.SuppressLint;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;

// TODO: 2/3/2019
/**
 * فیچر هایی که ایجاد شده اند و استفاده نشده اند
 * //-----------------------------------------------------------------
 * 1-باتوم شیت مربوط اتچمنت ها و نقشه (فیچر های مربوط به چند نقطه و مسیر و پلی گان و نمایش جیوجیسون آیکون ندارند)  با صدا زدن متد زیر از بیس ویو
 * showAttachmentBottomSheet
 *بحث گرفتن پرمیژن در اکتیویتی مقصد هندل میشود
 * لایه روت باید
 * <com.flipboard.bottomsheet.BottomSheetLayout
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     xmlns:tools="http://schemas.android.com/tools"
 *     android:id="@+id/global_bottom_sheet"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent">
 *
 * در باترنایف هم معرفی میشود  باشد و
 * //-----------------------------------------------------------------
 * استفاده از فریمورک utilMap
 * برای ایجاد خط و پلی گان
 * //-----------------------------------------------------------------
 * استفاده از فریمورک کار با جیوجیسون
 * //-----------------------------------------------------------------
 *  پیدا کردن لوکیشن یوزر با استفاده از
 *  getUserLiveLocation
 *  در بیس پرزنتر که در آن بحث چک کردن وجود اپلیکیشن mock
 * و HaveSomeAppsHandler
 * هندل شده است
 * اما در جاهای دیگر هم اگر لازم است جدا گانه استفاده شود
 * //-----------------------------------------------------------------
 * کار فیچر جا به جایی اکتیویتی
 * slidableactivity
 * //-----------------------------------------------------------------
 *کار با فیچر blur
 * //-----------------------------------------------------------------
 * انیمیشن در مپ با کلاس
 * MapAnimator
 * //-----------------------------------------------------------------
 * کشیدن منحنی در مپ با
 * com.github.sarweshkumar47:curve-fit:1.1.0
 * //-----------------------------------------------------------------
 * استفاده از سوکت و فایربیس
 * //-----------------------------------------------------------------
 * مشاهده فرم کد ها
 * IntentModel intentModel=new IntentModel();
 *             intentModel.setSomeInt(CommonsFormCode.TIME_SHEET_FORM_CODE);
 *             ActivityIntentFactoryHandler.goToProperActivity(activity,CommonsFormCode.TIME_SHEET_FORM_CODE,intentModel);
 * //-----------------------------------------------------------------
 * استفاده از کتابخانه جاستیفای
 * //-----------------------------------------------------------------
 *  * استفاده از کتابخانه
 *  shap of view
 * //-----------------------------------------------------------------
 * استفاده از کتابخونه دیالوگ مولتی سلکت
 * implementation 'com.github.abumoallim:Android-Multi-Select-Dialog:v1.9'
 * //-----------------------------------------------------------------
 *
 *
 */

public class MainActivity extends BaseActivity<MainPresenter, MainView> {

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        view=new MainView(this);
    }

    @Override
    protected void initPresenter() {
        presenter=new MainPresenter(weekView,this,false);
    }

}

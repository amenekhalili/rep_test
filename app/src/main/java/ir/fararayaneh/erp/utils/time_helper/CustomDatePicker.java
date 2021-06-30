package ir.fararayaneh.erp.utils.time_helper;


import androidx.annotation.Nullable;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class CustomDatePicker {

    private final static int minYear = 2018;
    private final static int maxYear = 2035;
    private final static int maxDay = 31;
    private final static int minDay = 1;

    //todo add new type of calendar
    public static void showProperDateTimePicker(BaseActivity activity, final  ISelectDateListener iSelectDateListener){

        switch (SharedPreferenceProvider.getCalendarType(activity)){
            case Commons.HIJRI_CALENDAR:
                hijriDateTimePicker(activity,iSelectDateListener);
                break;
            case Commons.GREGORIAN_CALENDAR:
                gregorianDateTimePicker(activity,iSelectDateListener);
                break;
        }

    }

    private static void gregorianDateTimePicker(BaseActivity activity, ISelectDateListener iSelectDateListener) {
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                activity.getResources().getString(R.string.please_select),
                activity.getResources().getString(R.string.ok),
                activity.getResources().getString(R.string.cancel)
        );
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(minYear, Calendar.JANUARY, minDay).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(maxYear, Calendar.DECEMBER, maxDay).getTime());
        //dateTimeDialogFragment.setDefaultDateTime(CustomTimeHelper.getCurrentTime());
        dateTimeDialogFragment.show(activity.getSupportFragmentManager(), "dialog_time");
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                if (iSelectDateListener != null) {
                    iSelectDateListener.onDateSelection(date);
                }
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                if (iSelectDateListener != null) {
                    iSelectDateListener.onDateNotSelection();
                }
            }
        });
    }


    private static void hijriDateTimePicker(final BaseActivity activity, final ISelectDateListener iSelectDateListener) {

        new com.alirezaafkar.sundatepicker.DatePicker.Builder()
                //.minDate(calendar)
                .build(new DateSetListener() {
                    @Override
                    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
                        hijriTimePicker(activity,year,month,day,iSelectDateListener);
                    }
                })
                .show(activity.getSupportFragmentManager(), "");

    }

    private static void hijriTimePicker(final BaseActivity activity,int year,int month,int day,final ISelectDateListener iSelectDateListener){

        TimePickerDialog timePickerDialog = TimePickerDialog
                .newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                if(iSelectDateListener!=null){
                    int[] gregorianDate=CustomTimeHelper.shamsToGregorianConvector(year,month,day);
                    iSelectDateListener.onDateSelection(CustomTimeHelper.getDateFromGeorgianNumberMonthZeroBase(gregorianDate[0],gregorianDate[1],gregorianDate[2],hourOfDay,minute));
                }
            }
        }, CustomTimeHelper.getCurrentHourOfDay(), CustomTimeHelper.getCurrentMinute(), true);

         timePickerDialog.show(activity.getFragmentManager(), "timepickerdialog");
    }


}


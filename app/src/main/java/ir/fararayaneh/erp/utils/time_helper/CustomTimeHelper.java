package ir.fararayaneh.erp.utils.time_helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonTimeZone;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

import static ir.fararayaneh.erp.commons.Commons.MINIMUM_TIME_PART_GERGORIAN;
import static ir.fararayaneh.erp.commons.Commons.MINIMUM_TIME_PART_GERGORIAN_2;
import static ir.fararayaneh.erp.commons.Commons.MINIMUM_TIME_PART_HEJRI;

/**
 * توجه :ماه های calender
 * به صورت پیشفرض بین 1 تا 11 است بنابراین در زمان استفاده از آن باید دقت نمود
 * بنا براین در زمان دادن ماه به آن باید یک ماه کم کرد و در زمان گرفتن ماه از ان باید یک ماه به آن اضافه نمود
 *
 *
 *
 * مهم مهم مهم مهم مهم−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−
 *ضمنا هم تابع
 * calandar
 * هم تابع
 * Date()
 * مقدار تاریخ را براساس لوکیشن و تایم زون واقعی یوزر برمیگرداند و برای محاسبه زمان تایم زون های غیر از تایم زون یوزر
 * از
 * calander.get(Calendar.HOUR_OF_DAY) , ....
 * باید استفاده نمود
 *
 */
public class CustomTimeHelper {

    /**
     * change in local , do not change the language of date in this method
     * تایم لوکال واقعی
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * change in local , do not change the language of date in this method
     */
    public static Date longToDateConvertor(long date) {
        return new Date(date);
    }

    public static long dateToLongConvertor(Date date) {
        return date == null ? 0 : date.getTime();
    }

    /**
     * get diff in millySecond
     */
    public static long getDiffDate(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    //---------------------------------------------------------------------------
    public static boolean isDateBetweenFinancialDate(Context context, long date) {
        return date >= SharedPreferenceProvider.getStartFinancialDate(context) &&
                date <= SharedPreferenceProvider.getEndFinancialDate(context);
    }

    //---------------------------------------------------------------------------
    public static Long getFakeGergorianStartFinancialDate(Context context) {
        switch (SharedPreferenceProvider.getCalendarType(context)) {
            case Commons.HIJRI_CALENDAR:
                return getFirstDayShamsiYearInGregorianFormat();
            case Commons.GREGORIAN_CALENDAR:
                return getFirstDayGergorianYearInGregorianFormat();

        }
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Long getFakeGergorianEndFinancialDate(Context context) {
        switch (SharedPreferenceProvider.getCalendarType(context)) {
            case Commons.HIJRI_CALENDAR:
                return getLastDayShamsiYearInGregorianFormat();
            case Commons.GREGORIAN_CALENDAR:
                return getLastDayGergorianYearInGregorianFormat();
        }
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * این تابع تاریخ میلادی اولین روز سال شمسی جاری را برمیگرداند
     */
    public static long getFirstDayShamsiYearInGregorianFormat() {
        CalendarTool calendarTool = new CalendarTool();
        CalendarTool calendarTool2 = new CalendarTool();
        calendarTool2.setIranianDate(calendarTool.getIranianYear(), 1, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendarTool2.getGregorianYear());
        calendar.set(Calendar.MONTH, calendarTool2.getGregorianMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendarTool2.getGregorianDay());
        return calendar.getTimeInMillis();
    }

    /**
     * این تابع تاریخ میلادی اولین روز سال میلادی جاری را برمیگرداند
     */
    public static long getFirstDayGergorianYearInGregorianFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * این تابع تاریخ میلادی آخرین روز سال شمسی جاری را برمیگرداند
     */
    public static long getLastDayShamsiYearInGregorianFormat() {

        Calendar calendar = Calendar.getInstance();
        CalendarTool calendarTool = new CalendarTool();
        CalendarTool calendarTool2 = new CalendarTool();
        calendarTool2.setIranianDate(calendarTool.getIranianYear(), 12, 29);
        calendar.set(Calendar.YEAR, calendarTool2.getGregorianYear());
        calendar.set(Calendar.MONTH, calendarTool2.getGregorianMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendarTool2.getGregorianDay());
        return calendar.getTimeInMillis();
    }

    /**
     * این تابع تاریخ میلادی آخرین روز سال میلادی جاری را برمیگرداند
     */
    public static long getLastDayGergorianYearInGregorianFormat() {

        Calendar calendar = Calendar.getInstance();
        CalendarTool calendarTool = new CalendarTool();
        CalendarTool calendarTool2 = new CalendarTool();
        calendarTool2.setGregorianDate(calendarTool.getGregorianYear(), 12, 29);
        calendar.set(Calendar.YEAR, calendarTool2.getGregorianYear());
        calendar.set(Calendar.MONTH, calendarTool2.getGregorianMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendarTool2.getGregorianDay());
        return calendar.getTimeInMillis();
    }

    //---------------------------------------------------------------------------

    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getCurrentHourOfDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @param year  should be in gregorian time
     * @param month should be in gregorian time  month is zeroBase (0-->>12)
     * @param day   should be in gregorian time
     * @return date in gregorian time
     */
    public static Date getDateFromGeorgianNumberMonthZeroBase(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        return calendar.getTime();
    }


    /**
     * @param year  in gergorian format
     * @param month in gergorian format (not zeroBase (1--->12))
     * @param day   in gergorian format
     */
    public static int[] gergorianToShamsiConvertor(int year, int month, int day) {

        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(year, month, day);
        return new int[]{calendarTool.getIranianYear(), calendarTool.getIranianMonth(), calendarTool.getIranianDay()};
    }


    /**
     * @param year  in shamsi format
     * @param month in shamsi format (not zeroBase (1--->12))
     * @param day   in shamsi format
     */
    public static int[] shamsToGregorianConvector(int year, int month, int day) {
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setIranianDate(year, month, day);
        return new int[]{calendarTool.getGregorianYear(), calendarTool.getGregorianMonth(), calendarTool.getGregorianDay()};
    }

    /**
     * برای نمایش تاریخ و ساعت در فرمت مناسب به یوزر
     */
    public static String getPresentableDate(Date date, Context context) {
        //todo add another method if have another calender type
        switch (SharedPreferenceProvider.getCalendarType(context)) {
            /*case Commons.GREGORIAN_CALENDAR:
                return gregorianPresentableDate(date);*/
            case Commons.HIJRI_CALENDAR:
                return shamsiPresentableDate(date);
            default:
                return gregorianPresentableDate(date);
        }
    }

    public static String getPresentableMessageDate(Date date, Context context) {
        //todo add another method if have another calender type
        if (getDiffDate(date, getCurrentDate()) < 5000) {
            return Commons.NOW;
        } else {
            return getPresentableNumberDate(date, context);
        }
    }

    public static String getPresentableChatDate(Date date, Context context) {
        //todo add another method if have another calender type
        if (getDiffDate(date, getCurrentDate()) < 5000) {
            return Commons.NOW;
        } else {
            return getPresentableNumberDateWithOutHour(date, context);
        }
    }

    /**
     * @param date in any presentable format
     *             <p>
     *             check date is  :   Commons.MINIMUM_TIME;
     */
    public static boolean checkMyTimeNotNull(String date) {

        return !date.contains(MINIMUM_TIME_PART_GERGORIAN)
                && !date.contains(MINIMUM_TIME_PART_GERGORIAN_2)
                && !date.contains(MINIMUM_TIME_PART_HEJRI);
    }

    /**
     * برای نمایش تاریخ و ساعت در فرمت مناسب به یوزر
     * به صورت عددی
     */
    public static String getPresentableNumberDate(Date date, Context context) {
        //todo add another method if have another calender type
        switch (SharedPreferenceProvider.getCalendarType(context)) {
           /* case Commons.GREGORIAN_CALENDAR:
                return gregorianPresentableDate(date);*/
            case Commons.HIJRI_CALENDAR:
                return shamsiNumberPresentableDate(date);
            default:
                return gregorianPresentableDate(date);
        }
    }

    public static String getPresentableNumberDateWithOutHour(Date date, Context context) {
        //todo add another method if have another calender type
        switch (SharedPreferenceProvider.getCalendarType(context)) {
            /*case Commons.GREGORIAN_CALENDAR:
                return gregorianPresentableDateWithOutHour(date);*/
            case Commons.HIJRI_CALENDAR:
                return shamsiNumberPresentableDateWithOutHour(date);
            default:
                return gregorianPresentableDateWithOutHour(date);
        }
    }

    private static String gregorianPresentableDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return String.format(CommonsFormat.PRESENTATION_DATE_FORMAT, calendarTool.getGregorianDate(), getTwoDigitNumber(cal.get(Calendar.HOUR_OF_DAY)), cal.get(Calendar.MINUTE));

    }

    private static String gregorianPresentableDateWithOutHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return calendarTool.getGregorianDate();

    }

    private static String shamsiPresentableDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return String.format(CommonsFormat.PRESENTATION_DATE_FORMAT2, calendarTool.getIranianWeekDayStr(), calendarTool.getIranianDay(), calendarTool.getIranianMonthStr(), calendarTool.getIranianYear(), getTwoDigitNumber(cal.get(Calendar.HOUR_OF_DAY)), getTwoDigitNumber(cal.get(Calendar.MINUTE)));

    }

    private static String shamsiNumberPresentableDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return String.format(CommonsFormat.PRESENTATION_DATE_FORMAT, calendarTool.getIranianDate(), getTwoDigitNumber(cal.get(Calendar.HOUR_OF_DAY)), getTwoDigitNumber(cal.get(Calendar.MINUTE)));

    }

    private static String shamsiNumberPresentableDateWithOutHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setGregorianDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return calendarTool.getIranianDate();

    }

    /**
     * برای نمایش طول زمان در فرمت مناسب به یوزر
     */
    public static String getPresentableWorkDuration(Date start, Date end, Activity activity) {
        int[] myComponent = getTimeDurationComponents(start, end);
        Log.i("arash", "getPresentableWorkDuration: " + myComponent[0]);
        return String.format(CommonsFormat.FORMAT_2,
                myComponent[0], activity.getResources().getString(R.string.days),
                activity.getResources().getString(R.string.and),
                myComponent[1], activity.getResources().getString(R.string.hours),
                activity.getResources().getString(R.string.and),
                myComponent[2], activity.getResources().getString(R.string.minutes),
                activity.getResources().getString(R.string.and),
                myComponent[3], activity.getResources().getString(R.string.seconds)
        );
    }

    /**
     * get time duration in days,hours,minutes,seconds
     */
    public static int[] getTimeDurationComponents(Date start, Date end) {
        final long DAY_IN_MILLI_SECOND = 86400000;
        final long HOUR_IN_MILLI_SECOND = 3600000;
        final long MINUTE_IN_MILLI_SECOND = 60000;
        long diffTime = getDiffDate(start, end);
        int daysNumber = 0;
        int hoursNumber = 0;
        int minutesNumber = 0;
        int secondsNumber;

        if (diffTime > DAY_IN_MILLI_SECOND) {
            daysNumber = (int) (diffTime / DAY_IN_MILLI_SECOND);
            diffTime = diffTime % DAY_IN_MILLI_SECOND;
        }
        if (diffTime > HOUR_IN_MILLI_SECOND) {
            hoursNumber = (int) (diffTime / HOUR_IN_MILLI_SECOND);
            diffTime = diffTime % HOUR_IN_MILLI_SECOND;
        }

        if (diffTime > MINUTE_IN_MILLI_SECOND) {
            minutesNumber = (int) (diffTime / MINUTE_IN_MILLI_SECOND);
            diffTime = diffTime % MINUTE_IN_MILLI_SECOND;
        }

        secondsNumber = (int) (diffTime / 1000);

        return new int[]{daysNumber, hoursNumber, minutesNumber, secondsNumber};
    }

//----------------------------------------------------------------------------------------------

    /**
     * nokte mohem : date hamishe meghdar ra ba tavajoh be time zoni ke karbar gharar darad (vagheii) meghdar
     * barmigardanad, hata agar timeZone taghire karde bashad
     * ama calander, meghdar bar hasbe time zone tanzime shode tavasote user midahad
     * ama agar az calander , date begirim, dobare bar hasbe time zone vagheii meghdar midahad
     *
     * @param inputDateLocal    //--> tarikh vorodi be che zabani ast
     * @param inputDateTimeZone //--> tarikh vorodi bar hasbe kodam mogheyat ast
     *                          <p>
     *                          exmple :()
     *                          dateInSomeFormat:    "15-Apr-19 17:30:39",
     *                          dateFormat:          CommonsFormat.DATE_SHARE_FORMAT,
     *                          inputDateLocal:      Locale.ENGLISH
     *                          inputDateTimeZone:   "America/Vancouver"
     *                          <p>
     *                          return value : (if we are in tehran now): Tue Apr 16 05:00:39 GMT+04:30 2019
     */
    public static Date stringDateToMyLocalDateConvertor(String dateInSomeFormat, String dateFormat, Locale inputDateLocal, String inputDateTimeZone) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, inputDateLocal);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(inputDateTimeZone));
        try {
            return simpleDateFormat.parse(dateInSomeFormat);
        } catch (ParseException e) {
            ThrowableLoggerHelper.logMyThrowable("CustomTimeHelper/stringDateToLongConvertor" + e.getMessage());
            return stringDateToMyLocalDateConvertor(Commons.MINIMUM_TIME, dateFormat, inputDateLocal, inputDateTimeZone);//for prevent from null date
        }
    }

    /**
     * @param outputTimeZone timeZone that we need my date convert to it in gregorian calendar
     * @param inputDate      هر عددی که به اینجا میرسد باید براساس لوکال واقعی موبایل باشد
     *
     * @return 15-Apr-19 17:37:39 (even my lang is persian and my local is iran)
     */
    public static String longLocalDateToOtherLocalConverter(String outputTimeZone, long inputDate, String format) {

        Calendar outputCalendar = Calendar.getInstance(TimeZone.getTimeZone(outputTimeZone));
        outputCalendar.setTimeInMillis(inputDate);// تایم لوکال واقعی موبایل
        return String.format(
                Locale.ENGLISH,
                format,
                getTwoDigitNumber(outputCalendar.get(Calendar.DAY_OF_MONTH))
                , getGregorianMonthAbbreviation(outputCalendar.get(Calendar.MONTH))
                , getTwoDigitNumber(getGregorianYearInTwoDigit(outputCalendar.get(Calendar.YEAR)))
                , getTwoDigitNumber(outputCalendar.get(Calendar.HOUR_OF_DAY))
                , getTwoDigitNumber(outputCalendar.get(Calendar.MINUTE))
                , getTwoDigitNumber(outputCalendar.get(Calendar.SECOND))
        );
    }


    /**
     * برای دریافت تایم لحظه ای GMT
     */
    public static long getLocalGreenwichLongTime(){
        Calendar outputCalendar = Calendar.getInstance();
        outputCalendar.setTimeZone(TimeZone.getTimeZone(CommonTimeZone.SERVER_TIME_ZONE));
        return outputCalendar.getTimeInMillis()
                - outputCalendar.get(Calendar.ZONE_OFFSET)
                ;

    }

    /**
     * @param monthNumberZeroBase : from 0 to 11
     */
    private static String getGregorianMonthAbbreviation(int monthNumberZeroBase) {

        String[] monthName = new String[12];
        monthName[0] = "Jan";
        monthName[1] = "Feb";
        monthName[2] = "Mar";
        monthName[3] = "Apr";
        monthName[4] = "May";
        monthName[5] = "Jun";//June
        monthName[6] = "Jul";//July
        monthName[7] = "Aug";
        monthName[8] = "Sep";//Sept
        monthName[9] = "Oct";
        monthName[10]= "Nov";
        monthName[11] = "Dec";
        return monthName[monthNumberZeroBase];
    }

    private static int getGregorianYearInTwoDigit(int fourDigitYear) {
        String myDigit = String.valueOf(fourDigitYear);
        return myDigit.length() > 2 ? Integer.valueOf(String.valueOf(fourDigitYear).substring(2).trim()) : Integer.valueOf(myDigit);
    }

    private static String getTwoDigitNumber(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }

}

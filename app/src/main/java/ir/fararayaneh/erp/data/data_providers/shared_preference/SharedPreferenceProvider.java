package ir.fararayaneh.erp.data.data_providers.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import androidx.appcompat.app.AppCompatDelegate;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.utils.UUIDHelper;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;
import me.leolin.shortcutbadger.ShortcutBadger;

import static ir.fararayaneh.erp.commons.Commons.NO_HAVE_CONFIGS_FOR_SERVER;


/**
 * here save apps's config objects like base server url and etc
 */
public class SharedPreferenceProvider {

    private static final String sharedPreferencesKeyName = "SHARED_PREFERENCES_KEY_NAME";
    private static final String mServerConfigs = "mServerConfigs";
    private static final String mAppThemes = "APP_THEMES";
    private static final String mDeviceId = "DEVICE_ID";
    private static final String mUserId = "USER_ID";
    private static final String mOrganization = "ORGANIZATION";
    private static final String mCurrentAttachmentName = "CURRENT_ATTACHMENT_NAME";
    private static final String mCalendarType = "CALENDAR_TYPE";
    private static final String mForceLocation = "FORCE_LOCATION"; //-->در برخی از فرم ها که گرفتن لوکیشن ضروری نیست با این کانفیگ تعیین میکنیم که آیا یوزر را مجبور کنیم به گرفتن لوکیشن یوزر یا خیر
    private static final String mDisplayableWarehouseList = "FORCE_GOOD_UNIT"; //آیا باید به یوزر ابعاد کالا را نمایش داد یا خیر اگر مقدار این کانفیگ صفر بود یعنی به ازای همه انبار های سیستم، این کانفیگ خاموش است اما اگر مقدار آن یک باشد یعنی ممکن است برای بعضی انبار ها روشن باشد و باری بعضی انبار ها خاموش باشد
    private static final String mMonetaryUnitId = "MONETARY_UNIT_ID";
    private static final String mMonetaryUnitName = "MONETARY_UNIT_Name";
    private static final String mSellEmporiumId = "SELL_EMPORIUM_ID";
    private static final String mSellEmporiumName = "SELL_EMPORIUM_NAME";
    private static final String mCountryDivisionId = "COUNTRY_DIVISION_ID";
    private static final String mStatusIdDefault = "STATUS_ID_DEFAULT";
    private static final String mStartFinancialDate = "START_FINANCIAL_DATE";
    private static final String mEndFinancialDate = "END_FINANCIAL_DATE";
    private static final String mTaxPercent = "TAX_PERCENT";
    private static final String mBadgeChat = "BADGE_CHAT";
    private static final String mBadgeCartable = "BADGE_CARTABLE";
    private static final String mChatRoomOnline = "CHATROOM_ONLINE";
    private static final String mChatRoomOnlineIsPrivate = "CHATROOM_ONLINE_IS_PRIVATE";
    private static final String mChatListOnline = "CHATLIST_ONLINE";
    private static final String mNodeIp = "NODE_IP";
    private static final String mOnlineServerIp = "ONLINE_SERVER_IP";//for online page in app
    private static final String mNodeRestServerPort = "REST_SERVER_PORT";
    private static final String mNodeServerPort = "NODE_SERVER_PORT";
    private static final String mAttachIp = "ATTACH_IP";
    private static final String mCompanyName = "companyName";
    private static final String mUploadProcess = "uploadProcess";
    private static final String mPrecisionAmountLength = "precisionAmountLength";
    private static final String mPrecisionPriceLength = "precisionPriceLength";
    private static final String mLocalLang = "localLang";
    private static final String mEvacuateProcess= "evacuateProcess";
    private static final String mNightMode= "nightMode";
    private static final String mIntroduce = "introduce";


    //----------------------------------------------------------------------------------->>

    /**
     * for when user were be log outed or financialDate were be expired
     */
    public static void clearAllPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();

        Log.i("arash", "clearAllPreferencesqqqqqqqqqqqqqqqqqqq: "+SharedPreferenceProvider.getDeviceId(context));
    }
    //----------------------------------------------------------------------------------->>
    /**
     *agar dar cartable report ham bodim ta zamani ke karbar roye radif gheir normal click nakarde niaz be kam kardan nadarim
     * har ja khastim meghdari be baje set konim az in function estefade mikonim
     * dar onResume main activity va dar
     * dar socket cartable va query message be badje ezafe mikonim
     * ba click roye tab kartable ya radif kartable  va vorood be chatroom az an kam mikonim
     *
     * nokte injast ke tedade nakhande haye har chatroom dar table chatmember negahdari va kam va ziad mishavad
     *  ama badge koli chatroom va kartable va icon barname inja modiriat mishavad
     */
    public static void setBadgeNumber(Context context, int chatChangeValue, int cartableChangeValue){
        SharedPreferenceProvider.changeBadgeCartable(context,cartableChangeValue);
        SharedPreferenceProvider.changeBadgeChat(context,chatChangeValue);
        setBadgeAppIcon(context,SharedPreferenceProvider.getBadgeCartable(context)+SharedPreferenceProvider.getBadgeChat(context));
    }

    private static void setBadgeAppIcon(Context context,int count){
        if (ShortcutBadger.isBadgeCounterSupported(context)) {
            if(count>=0){
                ShortcutBadger.applyCount(context, count);
            } else {
                ShortcutBadger.applyCount(context, 0);
            }
        }
    }
    //----------------------------------------------------------------------------------->>

    /**
     * no need to cartable online
     */
    public static void setOnlineChatroom(Context context,String chatroomId,boolean isPrivate){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mChatRoomOnline, chatroomId);
        editor.apply();
        editor.commit();

        setOnlineChatroomIsPrivate(context,isPrivate);
    }

    public static String getOnlineChatroom(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mChatRoomOnline, Commons.NULL_INTEGER);
    }
    //----------------------------------------------------------------------------------->>

    private static void setOnlineChatroomIsPrivate(Context context,boolean isPrivate){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(mChatRoomOnlineIsPrivate, isPrivate);
        editor.apply();
        editor.commit();
    }

    public static boolean getOnlineChatroomIsPrivate(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(mChatRoomOnlineIsPrivate, false);
    }
    //----------------------------------------------------------------------------------->>
    //no need ... but
    public static void setOnlineChatList(Context context,boolean isChatListOnline){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(mChatListOnline, isChatListOnline);
        editor.apply();
        editor.commit();
    }

    public static boolean getOnlineChatList(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(mChatListOnline, true);
    }
    //----------------------------------------------------------------------------------->>

    /**
     * @param changeValue is + or -
     */
    private static void changeBadgeCartable(Context context,int changeValue){

        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mBadgeCartable, getBadgeCartable(context)+changeValue<0?0:getBadgeCartable(context)+changeValue);
        editor.apply();
        editor.commit();

    }

    public static int getBadgeCartable(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mBadgeCartable, 0);
    }
    //----------------------------------------------------------------------------------->>
    public static void changeNightMode(Context context,int nightMode){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mNightMode, nightMode);
        editor.apply();
        editor.commit();

    }

    public static int getNightMode(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mNightMode, AppCompatDelegate.MODE_NIGHT_NO);
    }
    //----------------------------------------------------------------------------------->>

    /**
     * @param ShowIntroduce if is false , we do not show introduce
     */
    public static void setShowIntroduc(Context context,boolean ShowIntroduce){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(mIntroduce, ShowIntroduce);
        editor.apply();
        editor.commit();

    }

    public static boolean canShowIntroduc(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(mIntroduce, true);
    }
    //----------------------------------------------------------------------------------->>

    /**
     * @param changeValue is + or -
     */
    private static void changeBadgeChat(Context context,int changeValue){

        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mBadgeChat, getBadgeChat(context)+changeValue<0?0:getBadgeChat(context)+changeValue);
        editor.apply();
        editor.commit();
    }

    public static int getBadgeChat(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mBadgeChat, 0);
    }
    //----------------------------------------------------------------------------------->>
    public static void setEndFinancialDate(Context context,long endFinancialDate){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(mEndFinancialDate, endFinancialDate);
        editor.apply();
        editor.commit();
    }

    public static long getEndFinancialDate(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getLong(mEndFinancialDate, CustomTimeHelper.getFakeGergorianEndFinancialDate(context));
    }
    //----------------------------------------------------------------------------------->>
    public static void setTaxPercent(Context context,int taxPercent){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mTaxPercent, taxPercent);
        editor.apply();
        editor.commit();
    }

    public static int getTaxPercent(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mTaxPercent, Commons.DEFAULT_TAX_PERCENT);
    }
    //----------------------------------------------------------------------------------->>
    public static void setPrecisionAmountLength(Context context,int precisionAmountLength){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mPrecisionAmountLength, precisionAmountLength);
        editor.apply();
        editor.commit();
    }

    public static int getPrecisionAmountLength(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mPrecisionAmountLength, Commons.DEFAULT_PRECISION_AMOUNT_LENGTH);

    }
    //----------------------------------------------------------------------------------->>
    public static void setPrecisionPriceLength(Context context,int precisionPriceLength){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mPrecisionPriceLength, precisionPriceLength);
        editor.apply();
        editor.commit();
    }

    public static int getPrecisionPriceLength(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mPrecisionPriceLength, Commons.DEFAULT_PRECISION_PRICE_LENGTH);
    }
    //----------------------------------------------------------------------------------->>
    public static void setStartFinancialDate(Context context,long startFinancialDate){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(mStartFinancialDate, startFinancialDate);
        editor.apply();
        editor.commit();
    }

    public static long getStartFinancialDate(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getLong(mStartFinancialDate, CustomTimeHelper.getFakeGergorianStartFinancialDate(context));
    }
    //----------------------------------------------------------------------------------->>
    /**
     *
     * @param forceLocation --> 0 no need to get location, 1 --> need to get location
     */
    public static void setForceLocation(Context context,int forceLocation){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mForceLocation, forceLocation);
        editor.apply();
        editor.commit();
    }

    public static int getForceLocation(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getInt(mForceLocation, Commons.NO_NEED_FORCE_LOCATION);
    }
    //----------------------------------------------------------------------------------->>
    /**
     *
     * @param displayableWarehouseList --> warehouse id that can show width , height and so on as "12544:5454:5455454:5854"
     */
    public static void setDisplayableWarehouseList(Context context, String displayableWarehouseList){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mDisplayableWarehouseList, displayableWarehouseList);
        editor.apply();
        editor.commit();
    }

    public static String getDisplayableWarehouseList(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mDisplayableWarehouseList, Commons.SPACE);
    }

    //----------------------------------------------------------------------------------->>

    public static void setSellEmporiumId(Context context,String sellEmporiumId){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mSellEmporiumId, sellEmporiumId);
        editor.apply();
        editor.commit();
    }

    public static String getSellEmporiumId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mSellEmporiumId, Commons.NULL_INTEGER);
    }
    //----------------------------------------------------------------------------------->>
    public static void setSellEmporiumName(Context context,String sellEmporiumName){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mSellEmporiumName, sellEmporiumName);
        editor.apply();
        editor.commit();
    }

    public static String getSellEmporiumName(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mSellEmporiumName, Commons.NULL);
    }
    //----------------------------------------------------------------------------------->>
    public static void setMonetaryUnitId(Context context,String monetaryUnitId){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mMonetaryUnitId, monetaryUnitId);
        editor.apply();
        editor.commit();
    }

    public static String getMonetaryUnitId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mMonetaryUnitId, Commons.NULL_INTEGER);
    }

    //----------------------------------------------------------------------------------->>
    public static void setMonetaryUnitName(Context context,String monetaryUnitName){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mMonetaryUnitName, monetaryUnitName);
        editor.apply();
        editor.commit();
    }

    public static String getMonetaryUnitName(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mMonetaryUnitName, Commons.NULL);
    }
    //----------------------------------------------------------------------------------->>
    public static void setCalendarType(Context context,String calendarType){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mCalendarType, calendarType);
        editor.apply();
        editor.commit();
    }

    public static String getCalendarType(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = Commons.HIJRI_CALENDAR;
        return sharedPref.getString(mCalendarType, defaultValue);

    }
    //----------------------------------------------------------------------------------->>
    public static void setCurrentAttachmentName(Context context,String suffix){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mCurrentAttachmentName, UUIDHelper.getProperUUID() +suffix);
        editor.apply();
        editor.commit();
    }

    public static String getCurrentAttachmentName(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = CommonsDownloadFile.IMAGE_SUFFIX_NAME;
        return sharedPref.getString(mCurrentAttachmentName, defaultValue);

    }
    //----------------------------------------------------------------------------------->>
    public static void setDeviceId(Context context,String googleDeviceId){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mDeviceId, googleDeviceId);
        editor.apply();
        editor.commit();
    }

    public static String getDeviceId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mDeviceId, Commons.NULL_CAP);
    }

    //----------------------------------------------------------------------------------->>
    public static void setUserId(Context context,String userId){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mUserId, userId);
        editor.apply();
        editor.commit();
    }

    public static String getUserId(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = Commons.NULL_INTEGER;
        return sharedPref.getString(mUserId, defaultValue);
    }
    // ----------------------------------------------------------------------------------->>
    public static void setCompanyName(Context context,String nodeIpWithHttp){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mCompanyName, nodeIpWithHttp);
        editor.apply();
        editor.commit();
    }

    public static String getCompanyName(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = Commons.NULL_CAP;
        return sharedPref.getString(mCompanyName, defaultValue);
    }
    // ----------------------------------------------------------------------------------->>
    public static void setNodeIp(Context context,String nodeIpWithHttp){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mNodeIp, nodeIpWithHttp);
        editor.apply();
        editor.commit();
    }

    /**
     *
     * @return some ip like : http://111.111.111.111
     */
    public static String getNodeIp(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mNodeIp, Commons.NULL);
    }

    // ----------------------------------------------------------------------------------->>
    public static void setOnlineServerIp(Context context,String onlineServerIpWithHttp){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mOnlineServerIp, onlineServerIpWithHttp);
        editor.apply();
        editor.commit();
    }

    /**
     *
     * @return some ip like : http://111.111.111.111
     */
    public static String getOnlineServerIp(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mOnlineServerIp, CommonUrls.ERP_ONLINE_URL);
    }

    // ----------------------------------------------------------------------------------->>
    public static void setNodePort(Context context,String nodeServerPort){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mNodeServerPort, nodeServerPort);
        editor.apply();
        editor.commit();
    }


    public static String getNodePort(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mNodeServerPort, CommonUrls.NODE_SERVER_PORT);
    }

    // ----------------------------------------------------------------------------------->>
    public static void setNodeRestServerPort(Context context,String restServerPort){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mNodeRestServerPort, restServerPort);
        editor.apply();
        editor.commit();
    }

    public static String getNodeRestServerPort(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mNodeRestServerPort, CommonUrls.NODE_SERVER_REST_PORT);
    }


    // ----------------------------------------------------------------------------------->>
    public static void setBaseAttachIp(Context context,String attachBaseURL){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mAttachIp, attachBaseURL);
        editor.apply();
        editor.commit();
    }


    public static String getBaseAttachIp(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = CommonUrls.BASE_ATTACH_URL;
        return sharedPref.getString(mAttachIp, defaultValue);
    }
    // ----------------------------------------------------------------------------------->>
    public static void setOrganization(Context context,String organization){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mOrganization, organization);
        editor.apply();
        editor.commit();
    }

    public static String getOrganization(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        String defaultValue = Commons.NULL_CAP;
        return sharedPref.getString(mOrganization, defaultValue);
    }
    // ----------------------------------------------------------------------------------->>
    public static void setCountryDivissionID(Context context,String countryDivision){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mCountryDivisionId, countryDivision);
        editor.apply();
        editor.commit();
    }

    public static String getCountryDivisionID(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mCountryDivisionId, Commons.NULL_INTEGER);
    }
    // ----------------------------------------------------------------------------------->>
    public static void setStatusIdDefualt(Context context,String statusId){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mStatusIdDefault, statusId);
        editor.apply();
        editor.commit();
    }

    public static String getStatusIdDefualt(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mStatusIdDefault, Commons.NULL_INTEGER);
    }
    // ----------------------------------------------------------------------------------->>
    /**
     *we save ip base of server
     */
    public static void setServerConfigs(Context context, String serverConfigs){
    SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(mServerConfigs, serverConfigs);
    editor.apply();
    editor.commit();
    }

    public static String getServerConfigs(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mServerConfigs, NO_HAVE_CONFIGS_FOR_SERVER);
    }
    //----------------------------------------------------------------------------------->>
    public static void setAppTheme(Context context, int appTheme){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(mAppThemes, appTheme);
        editor.apply();
        editor.commit();
    }

    public static int getAppTheme(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        int defaultValue = R.style.DefaultAppTheme;
        return sharedPref.getInt(mAppThemes, defaultValue);
    }
    //----------------------------------------------------------------------------------->>
    public static void setUploadProcess(Context context, boolean startUploadProcess){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(mUploadProcess, startUploadProcess);
        editor.apply();
        editor.commit();
    }

    public static boolean haveUploadProcess(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(mUploadProcess, false);
    }
    //----------------------------------------------------------------------------------->>

    /**
     * for evacuate unSync and syncError packet
     */
    public static void setEvacuationProcess(Context context, boolean startEvacuateProcess){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(mEvacuateProcess, startEvacuateProcess);
        editor.apply();
        editor.commit();
    }

    public static boolean haveEvacuateProcess(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(mEvacuateProcess, false);
    }

    //----------------------------------------------------------------------------------->>

    public static void setLocalLanguage(Context context,String LocalLang) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mLocalLang, LocalLang);
        editor.apply();
        editor.commit();

    }
    public static String getLocalLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesKeyName, Context.MODE_PRIVATE);
        return sharedPref.getString(mLocalLang, Commons.ENGLISH_LOCAL);
    }




}

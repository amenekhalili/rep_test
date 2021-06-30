package ir.fararayaneh.erp.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import java.util.Objects;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.activities.splash.SplashActivity;
import ir.fararayaneh.erp.commons.CommonNotification;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.services.EvacuatePacketForeGroundService;
import ir.fararayaneh.erp.services.LiveLocationForeGroundService;
import ir.fararayaneh.erp.utils.package_handler.CheckAndroidOVersion;
import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;
import static ir.fararayaneh.erp.commons.CommonNotification.ACTION_STOP_SERVICE;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_DESCRIPTION;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_ID;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_NAME;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_DOWNLOAD_UPLOAD_FILE_DESCRIPTION;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_CHANNEL_ID;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_CHANNEL_NAME;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_FILE_DESCRIPTION;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_LOCATION_CHANNEL_ID;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_LOCATION_CHANNEL_NAME;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_LOCATION_DESCRIPTION;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_UPLOAD_FILE_CHANNEL_ID;
import static ir.fararayaneh.erp.commons.CommonNotification.FOREGROUND_NOTIFICATIONS_UPLOAD_FILE_CHANNEL_NAME;
import static ir.fararayaneh.erp.commons.CommonNotification.NOTIFICATIONS_CHANNEL_DESCRIPTION;
import static ir.fararayaneh.erp.commons.CommonNotification.NOTIFICATIONS_CHANNEL_ID;
import static ir.fararayaneh.erp.commons.CommonNotification.NOTIFICATIONS_CHANNEL_NAME;


public class NotificationHelper {


    private static long[] VIBRATE_PATTERN = new long[]{100,100};
    private static int LIGHT_NOTIFICATION_COLOR = Color.BLUE;


    public static void notif(Context context, String title, String content, int notifId,boolean shouldVibrate) {


        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent openApplicationIntent = PendingIntent.getActivity(context, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATIONS_CHANNEL_ID)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(title)
                .setContentText(content)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setContentIntent(pendingIntent)
                .setVisibility(VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(LIGHT_NOTIFICATION_COLOR, 1000, 1000)
                .setColor(LIGHT_NOTIFICATION_COLOR);

        if(shouldVibrate){
            builder.setVibrate(VIBRATE_PATTERN);
        }
        if(!App.isAppPowerOn()){
            builder.addAction(R.drawable.erp, context.getString(R.string.open_application), openApplicationIntent);
        }

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (CheckAndroidOVersion.SDKVersionAboveO()) {

            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATIONS_CHANNEL_ID,
                            NOTIFICATIONS_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(Color.WHITE);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att);
            if(shouldVibrate){
                channel.setVibrationPattern(VIBRATE_PATTERN);
                channel.enableVibration(true);
            }
            channel.setDescription(NOTIFICATIONS_CHANNEL_DESCRIPTION);
            Objects.requireNonNull(mNotificationManager).createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        Objects.requireNonNull(notificationManager).notify(notifId, builder.build());
    }
    //-----------------------------------------------------------------------------------------
    public static Notification setForeGroundDownloadAPKNotification(Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(context.getString(R.string.download_new_apk))
                .setContentText(context.getString(R.string.downloading))
                .setProgress(0, 100, true)
                .setColor(LIGHT_NOTIFICATION_COLOR)

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(VISIBILITY_PUBLIC)
                .setLights(LIGHT_NOTIFICATION_COLOR, 300, 300);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            NotificationChannel channel =
                    new NotificationChannel(FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_ID,
                            FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_HIGH);

            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            channel.setLightColor(Color.WHITE);

            channel.setDescription(FOREGROUND_NOTIFICATIONS_DOWNLOAD_APK_CHANNEL_DESCRIPTION);
            Objects.requireNonNull(mNotificationManager)
                    .createNotificationChannel(channel);
        }
        return builder.build();
    }
    //-----------------------------------------------------------------------------------------
    public static Notification setForeGroundUploadFileNotification(Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, FOREGROUND_NOTIFICATIONS_UPLOAD_FILE_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setContentTitle(context.getString(R.string.upload_files))
                .setContentText(context.getString(R.string.uploading))
                .setProgress(0, 100, true)
                .setColor(LIGHT_NOTIFICATION_COLOR)

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(VISIBILITY_PUBLIC)
                .setLights(LIGHT_NOTIFICATION_COLOR, 300, 300);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            NotificationChannel channel =
                    new NotificationChannel(FOREGROUND_NOTIFICATIONS_UPLOAD_FILE_CHANNEL_ID,
                            FOREGROUND_NOTIFICATIONS_UPLOAD_FILE_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_HIGH);

            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            channel.setLightColor(Color.WHITE);

            channel.setDescription(FOREGROUND_NOTIFICATIONS_DOWNLOAD_UPLOAD_FILE_DESCRIPTION);
            Objects.requireNonNull(mNotificationManager)
                    .createNotificationChannel(channel);
        }
        return builder.build();
    }
    //-----------------------------------------------------------------------------------------
    public static Notification setForeGroundEvacuatePacketNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setContentTitle(context.getString(R.string.updataing))
                .setContentText(context.getString(R.string.app_name))
                .setProgress(0, 100, true)
                .setColor(LIGHT_NOTIFICATION_COLOR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(VISIBILITY_PUBLIC)
                .setLights(LIGHT_NOTIFICATION_COLOR, 300, 300);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            NotificationChannel channel =
                    new NotificationChannel(FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_CHANNEL_ID,
                            FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT);

            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            channel.setLightColor(Color.WHITE);

            channel.setDescription(FOREGROUND_NOTIFICATIONS_EVACUATION_PACKET_FILE_DESCRIPTION);
            Objects.requireNonNull(mNotificationManager)
                    .createNotificationChannel(channel);
        }
        return builder.build();
    }
    //-----------------------------------------------------------------------------------------
    public static Notification setForeGroundLocationNotification(Context context) {

        Intent stopSelf = new Intent(context, LiveLocationForeGroundService.class);
        stopSelf.setAction(ACTION_STOP_SERVICE);
        PendingIntent pStopSelf = PendingIntent.getService(context, 0, stopSelf,PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, FOREGROUND_NOTIFICATIONS_LOCATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .setContentTitle(context.getString(R.string.location_notif))
                //.setContentText(context.getString(R.string.app_name))
                .setColor(LIGHT_NOTIFICATION_COLOR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(VISIBILITY_PUBLIC)
                //.setContentIntent(pStopSelf)
                .addAction(android.R.drawable.stat_sys_warning,"stop",pStopSelf)
                .setLights(LIGHT_NOTIFICATION_COLOR, 300, 300);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            NotificationChannel channel =
                    new NotificationChannel(FOREGROUND_NOTIFICATIONS_LOCATION_CHANNEL_ID,
                            FOREGROUND_NOTIFICATIONS_LOCATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_DEFAULT);

            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            channel.setLightColor(Color.WHITE);

            channel.setDescription(FOREGROUND_NOTIFICATIONS_LOCATION_DESCRIPTION);
            Objects.requireNonNull(mNotificationManager)
                    .createNotificationChannel(channel);
        }
        return builder.build();
    }
    //-----------------------------------------------------------------------------------------
    public static void clearAllNotif(Context context) {
        if(!SharedPreferenceProvider.haveUploadProcess(context)){
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancelAll();
       }
    }
    //-----------------------------------------------------------------------------------------
    public static void playSoundMessageReceived(Context context) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.chat_alert_received);
            mediaPlayer.start();
    }
    //-----------------------------------------------------------------------------------------
    public static boolean isDeviceSilence(Context context){
        AudioManager alarmManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (alarmManager != null ? alarmManager.getRingerMode() : AudioManager.RINGER_MODE_SILENT) {
            case AudioManager.RINGER_MODE_SILENT:
            case AudioManager.RINGER_MODE_VIBRATE:
               return true;
               default:
                return false;
        }
    }



}

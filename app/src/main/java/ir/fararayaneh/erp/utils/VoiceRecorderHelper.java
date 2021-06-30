package ir.fararayaneh.erp.utils;


import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonRequestCodes;

public class VoiceRecorderHelper {

    public static void record(BaseActivity activity,String filePath){

        AndroidAudioRecorder.with(activity)
                // Required
                .setFilePath(filePath)
                .setColor(activity.getResources().getColor(GetATTResources.resId(activity,new int[] {R.attr.colorPrimaryAttr})))
                .setRequestCode(CommonRequestCodes.VOICE)
                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)
                .record();
    }


}

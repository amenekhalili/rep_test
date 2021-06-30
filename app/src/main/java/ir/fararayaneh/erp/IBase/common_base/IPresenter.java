package ir.fararayaneh.erp.IBase.common_base;

import android.os.Bundle;

import ir.fararayaneh.erp.data.models.middle.IntentModel;


public interface IPresenter {

    void onStart();
    void onCreate(IntentModel intentModel);
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
    void onBackPress();
    void onSaveInstantstate(Bundle outState);
    void onRestoreInstantstate(Bundle savedInstanceState);

}

package ir.fararayaneh.erp.utils.location_handler;

import android.location.Location;

import ir.fararayaneh.erp.IBase.common_base.IListeners;

public interface ILiveLocationListener extends IListeners {

    void getLocation(Location location);
    void getError(String error);
    void forbiddenApp(); // for situation that HaveSomeAppsHandler  show forbidden apps
    void mockLocation(); // for situation that MockLocationHelper  show forbidden apps
}

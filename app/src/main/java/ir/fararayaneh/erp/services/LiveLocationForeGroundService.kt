package ir.fararayaneh.erp.services

/**
https://developer.android.com/training/location

ACCESS_COARSE_LOCATION permission
ACCESS_FINE_LOCATION permission.
ACCESS_BACKGROUND_LOCATION permission. (all-the-time access to location for android 10 (no need because we use a foreground service))

 حتما درخواست پرمیژن های دو گانه بالا و درخواست
 روشن کردن GPS در خارج از این سرویس داده شود
 */
import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import ir.fararayaneh.erp.commons.CommonNotification
import ir.fararayaneh.erp.commons.CommonNotification.ACTION_STOP_SERVICE
import ir.fararayaneh.erp.utils.NotificationHelper
import ir.fararayaneh.erp.utils.package_handler.CheckAndroidOVersion

class LiveLocationForeGroundService : Service() {
    private var canStartProcess = true
    private var fusedClient: FusedLocationProviderClient? = null
    private val _startId =-1000

    //----------------------------------------------------
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        if (ACTION_STOP_SERVICE == intent?.action) {
            stopForegroundLocationService()
        } else {
            setupForegroundNotify()
        }
        return START_STICKY
    }

    /**
     * FOREGROUND_NOTIFICATIONS_UPLOAD_ID != 0
     * notify priority should be greater than low
     */
    private fun setupForegroundNotify() {
        /* after maximum 5 second, this code should be ran*/
        startForeground(CommonNotification.FOREGROUND_LOCATION_ID, NotificationHelper.setForeGroundLocationNotification(this))
        if (canStartProcess ) {
            changeStartProcessState(false)
            startProcess()
        }
    }

    private fun startProcess() {
        requestLocationUpdates()
    }

    fun changeStartProcessState(canStartAgain : Boolean){
        canStartProcess = canStartAgain;
    }
    override fun onDestroy() {
        stopForegroundLocationService()
        super.onDestroy()
    }

    private fun stopForegroundLocationService() {
        stopForeground(true)
        stopSelf()
    }

//----------------------------------------------------


    private fun requestLocationUpdates() {

        val request = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000 //get location from other apps !
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY//todo change to PRIORITY_BALANCED_POWER_ACCURACY or other
        }

        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        val permissionFineLocation = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCoarsLocation = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permissionFineLocation == PackageManager.PERMISSION_GRANTED && permissionCoarsLocation == PackageManager.PERMISSION_GRANTED) {
            fusedClient?.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location = locationResult.lastLocation
                    Log.i("arash", "onLocationResult: 111111111111111 "+location.toString())
                }
            }, null)
        } else {
            stopForegroundLocationService()
            //todo add needed work
        }
    }


}

 fun intentToLocationForeGroundService(context: Context) {
        if (CheckAndroidOVersion.SDKVersionAboveO()) {
            context.startForegroundService(Intent(context,LiveLocationForeGroundService::class.java))
        } else {
            context.startService(Intent(context, LiveLocationForeGroundService::class.java))
        }
}
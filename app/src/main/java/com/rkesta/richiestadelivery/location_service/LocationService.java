package com.rkesta.richiestadelivery.location_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.rkesta.richiestadelivery.api.WebService;
import com.rkesta.richiestadelivery.app.constant;

public class LocationService extends Service {
    /**
     * Start service in your main activity,like this
     * startService(new Intent(context, LocationService.class));
     *
     * for stop,
     * stopService(new Intent(context, LocationService.class));
     */
    private GoogleLocationService googleLocationService;

    @Override
    public void onCreate() {
        super.onCreate();
        //start the handler for getting locations
        //create component

        updateLocation(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    //get current location os user
    private void updateLocation(Context context) {
        googleLocationService = new GoogleLocationService(context, new LocationUpdateListener() {
            @Override
            public void canReceiveLocationUpdates() {
            }

            @Override
            public void cannotReceiveLocationUpdates() {
            }

            //update location to our servers for tracking purpose
            @Override
            public void updateLocation(Location location) {
                if (location != null ) {
                    Log.v("updated location ", location.getLatitude()+"/"+ location.getLongitude());

                    final Location loc = location ;
                    if(!constant.Driver_ID.isEmpty()){
                    new Thread(new Runnable() {
                        public void run() {
                            // a potentially time consuming task
                            WebService WS = new WebService();
                            WS.RK_DeliveryContactLocationUpdate(constant.Driver_ID,loc.getLongitude()+"",loc.getLatitude()+"");
                            Log.d("Location", " updated to server: "+loc.getLongitude()+","+loc.getLatitude()+"");
                        }
                    }).start();
                    }else{

                    }

                }
            }

            @Override
            public void updateLocationName(String localityName, Location location) {

                googleLocationService.stopLocationUpdates();
            }
        });
        googleLocationService.startUpdates();
    }


    IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        public LocationService getServerInstance() {
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //stop location updates on stopping the service
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleLocationService != null) {
            googleLocationService.stopLocationUpdates();
        }
    }
}
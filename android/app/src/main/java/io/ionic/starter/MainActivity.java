package io.ionic.starter;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


import static androidx.core.app.JobIntentService.enqueueWork;
import static java.security.AccessController.getContext;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.getcapacitor.BridgeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BridgeActivity {

  private static final int PERMISSION_REQUEST_CODE = 200;
  private static final int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
  private static final int LOCATION_REFRESH_DISTANCE = 1; // 5 meters to update

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //this.listenFuseLocation();
    //this.listenLocation();
    this.callWorkManager();

  }

  private void listenLocation() {
    if (!checkLocationPermission())
      ActivityCompat.requestPermissions(this,
        new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    LocationRepository locationRepository = new LocationRepository(getApplicationContext());
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
      LOCATION_REFRESH_DISTANCE, locationRepository::createOne);
  }

  private boolean checkLocationPermission() {
    int coarsePermission = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
    int finePermission = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
    return coarsePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED;
  }

  protected void listenFuseLocation() {
    if (!checkLocationPermission())
      ActivityCompat.requestPermissions(this,
        new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    LocationRequest locationRequest = new LocationRequest.Builder(1000L).build();

    LocationCallback locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
          return;
        }
        for (Location location : locationResult.getLocations()) {
          if (location != null) {

          }
        }
      }
    };

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
  }

  protected void callWorkManager() {
    WorkManager workManager = WorkManager.getInstance(getApplicationContext());
    PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(LocationWorker.class, 15, TimeUnit.MINUTES).build();
    workManager.enqueue(periodicWork);
    //workManager.enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.KEEP, periodicWork);
  }


}

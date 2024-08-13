package io.ionic.starter;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationWorker extends Worker {

  private static final int PERMISSION_REQUEST_CODE = 200;
  private static final long MILLIS_10MIN = 600000L;
  private static final long MILLIS_5MIN = 300000L;

  public LocationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
    super(context, params);
  }

  @Override
  public Result doWork() {
    this.getFusedLocation();
    return Result.success();
  }

  private boolean checkLocationPermission() {
    int coarsePermission = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
    int finePermission = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
    return coarsePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED;
  }

  protected void getFusedLocation() {
    if (!checkLocationPermission())
      ActivityCompat.requestPermissions((Activity) getApplicationContext(),
        new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    LocationRepository locationRepository = new LocationRepository(getApplicationContext());
    FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    LocationRequest locationRequest = new LocationRequest.Builder(MILLIS_5MIN).setMinUpdateDistanceMeters(100F).build();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      fusedLocationClient.requestLocationUpdates(locationRequest, getApplicationContext().getMainExecutor(),locationRepository::createOne);
    }
  }
}

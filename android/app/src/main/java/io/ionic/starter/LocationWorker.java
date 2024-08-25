package io.ionic.starter;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationWorker extends Worker {

  private static final long MILLIS_5MIN = 300000L;
  private static final float DISTANCE_100M = 100F;
  private LocationRepository locationRepository;

  public LocationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
    super(context, params);
  }

  @Override
  public Result doWork() {
    try {
      this.locationRepository = new LocationRepository(getApplicationContext());
      this.getFusedLocation();
      this.getLocationManager();
      return Result.success();
    } catch (Throwable throwable) {
      return Result.failure();
    }
  }

  private boolean checkLocationPermission() {
    final int coarsePermission = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
    final int finePermission = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
    return coarsePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED;
  }

  protected void getFusedLocation() {
    final FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    final LocationRequest locationRequest = new LocationRequest.Builder(MILLIS_5MIN).setMinUpdateDistanceMeters(DISTANCE_100M).build();
    if (checkLocationPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
      fusedLocationClient.requestLocationUpdates(locationRequest, getApplicationContext().getMainExecutor(), locationRepository::createOne);
  }

  protected void getLocationManager() {
    final LocationManager locationManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    if (checkLocationPermission() && gpsEnabled)
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MILLIS_5MIN, DISTANCE_100M, locationRepository::createOne, Looper.getMainLooper());
  }

}

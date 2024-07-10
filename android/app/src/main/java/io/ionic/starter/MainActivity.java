package io.ionic.starter;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

  private static final int PERMISSION_REQUEST_CODE = 200;
  private static final int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
  private static final int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.listenLocation();
  }

  private void listenLocation() {
    if (!checkLocationPermission())
      ActivityCompat.requestPermissions(this,
        new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    LocationRepository locationRepository = new LocationRepository();
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
      LOCATION_REFRESH_DISTANCE, locationRepository::createOne);
  }

  private boolean checkLocationPermission() {
    int coarsePermission = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
    int finePermission = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
    return coarsePermission == PackageManager.PERMISSION_GRANTED && finePermission == PackageManager.PERMISSION_GRANTED;
  }
}

package io.ionic.starter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationRepository {

  private FirebaseFunctions functions;
  private Context context;

  public LocationRepository(Context context) {
    this.functions = FirebaseFunctions.getInstance();
    this.context = context;
  }

  //https://firebase.google.com/docs/functions/callable?hl=fr&gen=2nd#java
  public Task<String> createOne(Location location) {
    try {
      return functions.getHttpsCallable("onCallCreateOne")
        .call(this.mapper(location))
        .continueWith(task -> (String) task.getResult().getData());
    } catch (JSONException e) {
      return null;
    }
  }

  private JSONObject mapper(Location location) throws JSONException {
    JSONObject data = new JSONObject();
    JSONObject jsonLocation = new JSONObject();
    jsonLocation.put("lat", location.getLatitude());
    jsonLocation.put("lng", location.getLongitude());
    jsonLocation.put("time", location.getTime());
    jsonLocation.put("user", Build.MANUFACTURER + "-" + Build.DEVICE);
    jsonLocation.put("date", new Date());
    jsonLocation.put("address", this.getCompleteAddressString(location));
    data.put("document", jsonLocation);
    data.put("collection", "locations");
    return data;
  }

  private String getCompleteAddressString(Location location) {
    String strAdd = "";
    Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
    try {
      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
      if (addresses != null) {
        Address returnedAddress = addresses.get(0);
        StringBuilder strReturnedAddress = new StringBuilder();

        for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
          strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
        }
        strAdd = strReturnedAddress.toString();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strAdd;
  }
}

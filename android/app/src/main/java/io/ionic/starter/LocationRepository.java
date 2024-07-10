package io.ionic.starter;

import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class LocationRepository {

  private FirebaseFunctions functions;

  public LocationRepository() {
    this.functions = FirebaseFunctions.getInstance();
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
    jsonLocation.put("user", location.getProvider());
    jsonLocation.put("date", new Date());
    data.put("document", jsonLocation);
    data.put("collection", "locations");
    return data;
  }

}

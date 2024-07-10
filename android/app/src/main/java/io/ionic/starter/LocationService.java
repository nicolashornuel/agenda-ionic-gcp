package io.ionic.starter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import org.json.JSONException;

public class LocationService {

  /*public class LocationWorker extends Worker {
  public LocationWorker(
    @NonNull Context context,
    @NonNull WorkerParameters params) {
    super(context, params);
  }

  @Override
  public Result doWork() {
    Context applicationContext = getApplicationContext();

    return Result.success();
  }



}*/


  /*  public void initFirebase() throws FileNotFoundException {
    FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
      .setApplicationId("1:1076193011248:android:7a29e8b01eb32afb8eaff5")
      .setApiKey("AIzaSyCgbl89dby3f5eocBAeyfUUKGzcc1DLzeE");
    FirebaseApp.initializeApp(this, builder.build());
  }*/

  //Context applicationContext = getApplicationContext();
  //workManager = WorkManager.getInstance(applicationContext);
  //workManager.enqueue(OneTimeWorkRequest.from(LocationWorker.class));
  //PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(LocationWorker.class, 15, TimeUnit.MINUTES).addTag(TAG).build();
  //workManager.enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);

}

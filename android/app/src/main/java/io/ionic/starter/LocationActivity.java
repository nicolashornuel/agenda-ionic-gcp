package io.ionic.starter;

import android.os.Bundle;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.getcapacitor.BridgeActivity;
import java.util.concurrent.TimeUnit;

public class LocationActivity extends BridgeActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.callWorkManager();
  }

  protected void callWorkManager() {
    WorkManager workManager = WorkManager.getInstance(getApplicationContext());
    WorkRequest workRequest = new PeriodicWorkRequest.Builder(LocationWorker.class, 15, TimeUnit.MINUTES).build();
    workManager.enqueue(workRequest);
  }
}

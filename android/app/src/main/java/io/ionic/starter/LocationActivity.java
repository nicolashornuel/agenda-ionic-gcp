package io.ionic.starter;

import android.os.Bundle;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.getcapacitor.BridgeActivity;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BridgeActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.callWorkManager();
  }

  protected void callWorkManager() {
    WorkManager workManager = WorkManager.getInstance(getApplicationContext());
    PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(LocationWorker.class, 15, TimeUnit.MINUTES).build();
    workManager.enqueue(periodicWork);
  }
}

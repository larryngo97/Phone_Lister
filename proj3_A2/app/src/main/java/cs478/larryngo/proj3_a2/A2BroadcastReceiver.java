package cs478.larryngo.proj3_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class A2BroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = "A2 Broadcast Receiver";
    private String EXTRA_PERM_ID;
    private String EXTRA_PHONE_ID;
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Bundle extras = arg1.getExtras();
        EXTRA_PERM_ID = extras.getString("perm_kaboom"); //not needed, for debug
        EXTRA_PHONE_ID = extras.getString("EXTRA_PHONE_ID");

        Log.i(TAG, "Permission is " + EXTRA_PERM_ID); //debug
        Log.i(TAG, "Phone name is: " + EXTRA_PHONE_ID);

        String action = arg1.getAction();
        if (action.equals("cs478.larryngo.proj3.TOAST_INTENT")) //makes sure right intent was used, for debug
        {
            Log.i(TAG, "A2 got called to action!");
            Toast.makeText(arg0, "A2 got called to action!", Toast.LENGTH_SHORT).show();
        }

        if(EXTRA_PHONE_ID != null) //dont show message in case string could not be found
        {
            Toast.makeText(arg0, "Starting website for " + EXTRA_PHONE_ID, Toast.LENGTH_SHORT).show(); //displays toast to user that the website is starting for this phone
        }
    }
}

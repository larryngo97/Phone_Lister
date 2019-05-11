package cs478.larryngo.proj3_a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class A1BroadcastReceiver extends BroadcastReceiver{
    private final String TAG = "A1BroadcastReceiver";
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Log.i(TAG,  "A1 got called to action!");
        //Toast.makeText(arg0, "A1 got called to action!", Toast.LENGTH_SHORT).show();
        Bundle extras = arg1.getExtras();
        String website = arg1.getStringExtra("EXTRA_WEB_ID");

        Log.i(TAG, "Website is " + website);
        if (website != null)
        {
            Intent intent = new Intent(arg0, WebpageActivity.class); //passes onto the webpage activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //makes sure the intent can be used
            intent.putExtra("EXTRA_WEB_ID", website);
            arg0.startActivity(intent); //starts activity
        }
    }
}

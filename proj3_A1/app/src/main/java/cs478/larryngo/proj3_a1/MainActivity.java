package cs478.larryngo.proj3_a1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button permissionButton;
    private TextView welcomeTextView;
    private final int PERMISSION_CODE = 1;

    private A1BroadcastReceiver a1receiver;
    private final static String TOAST_BROADCAST = "cs478.larryngo.proj3.TOAST_INTENT";

    private final String TAG = "APP_1";
    private String welcomeText = "Welcome to app1! Click the button below " +
            "to see if you have permission from app3 to launch app2";
    private final static String APP_1_NAME = "cs478.larryngo.proj3_a1";
    private final static String APP_2_NAME = "cs478.larryngo.proj3_a2";
    private final static String APP_3_NAME = "cs478.larryngo.proj3_a3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.a1_welcome_text);
        permissionButton = findViewById(R.id.a1_button);
        welcomeTextView.setText(welcomeText);

        permissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        "edu.uic.cs478.s19.kaboom") == PackageManager.PERMISSION_GRANTED)
                {
                    //Toast.makeText(MainActivity.this, "You have permission", Toast.LENGTH_SHORT).show();
                    //Permission granted already
                    IntentFilter filter = new IntentFilter();
                    a1receiver = new A1BroadcastReceiver();
                    registerReceiver(a1receiver, filter);


                    Log.i(TAG, "Sending broadcast to APP_2");
                    Intent intent = new Intent(TOAST_BROADCAST);
                    intent.putExtra("perm_kaboom", "edu.uic.cs478.s19.kaboom");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    intent.setComponent(new ComponentName(APP_2_NAME, "cs478.larryngo.proj3_a2.A2BroadcastReceiver"));
                    sendBroadcast(intent);

                    Intent app2 = getPackageManager().getLaunchIntentForPackage(APP_2_NAME);
                    startActivity(app2);
                }
                else
                {
                    //Toast.makeText(MainActivity.this,"No permission", Toast.LENGTH_SHORT).show();
                    //Permission not granted. Will ask user

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {"edu.uic.cs478.s19.kaboom"},
                            PERMISSION_CODE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MainActivity.this,"Permission granted!", Toast.LENGTH_SHORT).show();
                    IntentFilter filter = new IntentFilter();
                    a1receiver = new A1BroadcastReceiver();
                    registerReceiver(a1receiver, filter);


                    Log.i(TAG, "Sending broadcast to APP_2");
                    Intent intent = new Intent(TOAST_BROADCAST);
                    intent.putExtra("perm_kaboom", "edu.uic.cs478.s19.kaboom");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    intent.setComponent(new ComponentName(APP_2_NAME, "cs478.larryngo.proj3_a2.A2BroadcastReceiver"));
                    sendBroadcast(intent);

                    Intent app2 = getPackageManager().getLaunchIntentForPackage(APP_2_NAME);
                    startActivity(app2);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "++ ON START ++");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "++ ON RESUME ++");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "++ ON PAUSE ++");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "++ ON STOP ++");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "++ ON DESTROY ++");
        //unregisters the receiver
        if (a1receiver != null)
        {
            Log.i("onDestroy", "Destroying receiver " + a1receiver.toString());
            unregisterReceiver(a1receiver);
            a1receiver = null;
        }
        super.onDestroy();
    }
}

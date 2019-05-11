package cs478.larryngo.proj3_a3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements A3Fragment1.ListSelectionListener {

    private final String TAG = "APP_3";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FrameLayout listFragmentLayout, imageFragmentLayout;
    private A3Fragment1 listFragment;
    private A3Fragment2 imageFragment;
    private final static String TOAST_BROADCAST = "cs478.larryngo.proj3.TOAST_INTENT";
    private final static String APP_1_NAME = "cs478.larryngo.proj3_a1";
    private final static String APP_2_NAME = "cs478.larryngo.proj3_a2";
    private final static String APP_3_NAME = "cs478.larryngo.proj3_a3";
    private int choiceSelection = -1; //use to indicate a choice has not been selected

    private ArrayList<String> websites = new ArrayList<String>(Arrays.asList(
            "https://www.apple.com/iphone-xs", "https://store.google.com/product/pixel_3",
            "https://www.samsung.com/us/mobile/galaxy-s10", "https://consumer.huawei.com/en/phones/mate20-x",
            "https://www.oneplus.com/6t", "https://www.lg.com/us/mobile-phones/v40-thinq",
            "https://www.mi.com/global/mi9/", "https://www.htc.com/uk/smartphones/htc-u12-life/"));

    private ArrayList<String> phoneName = new ArrayList<String>(Arrays.asList(
            "Apple iPhone XS", "Google Pixel 3",
            "Samsung Galaxy S10", "Huawei Mate 20",
            "OnePlus 6T", "LG V40",
            "Xiaomi Mi9", "HTC U12 Life"));

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.phoneicon);


        listFragment = new A3Fragment1();
        imageFragment = new A3Fragment2();

        listFragmentLayout = (FrameLayout) findViewById(R.id.fragment_frame_list);
        imageFragmentLayout = (FrameLayout) findViewById(R.id.fragment_frame_image);

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame_list, listFragment);
                //.replace(R.id.fragment_frame_image, imageFragment);
        fragmentTransaction.commit();


        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) //if app started in portrait
        {
            Log.i(TAG, "++ PHONE STARTED IN PORTRAIT MODE ++");
            listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));

        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE) //if app started in landscape
        {
            Log.i(TAG, "++ PHONE STARTED IN LANDSCAPE MODE ++");
            listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setLayout();
            }
        });


    }

    @Override
    public void onListSelection(int index)
    {
        Log.i(TAG, "++ Selected item at index " + index + " ++");
        if(!imageFragment.isAdded()) //if image fragment could be found
        {
            Log.i(TAG, "++ imageFragment was not added! Adding now ++");

            //add the fragment forcefully
            fragmentManager = this.getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.fragment_frame_image, imageFragment)
                    .addToBackStack(null);
            Log.i(TAG, "++ Items in back stack :" + fragmentManager.getBackStackEntryCount());
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions(); //prevents errors by executing the commands
            Log.i(TAG, "++ Successfully added imageFragment! ++");
        }
        imageFragment.setPhoneImage(index); //sets the phone image
        choiceSelection = index;
    }

    public void setLayout()
    {
        Log.i(TAG, "++ SETTING UP LAYOUT ++");

        if(!imageFragment.isAdded()) //if imagefragment cannot be found, then show only list
        {
            listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else
        {
            int orientation = getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT) //if app started in portrait
            {
                Log.i(TAG, "++ LAYOUT: PHONE IN PORTRAIT MODE ++");
                listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)); //list disappears
                imageFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); //image takes all screen

            }
            else if(orientation == Configuration.ORIENTATION_LANDSCAPE) //if app started in landscape
            {
                Log.i(TAG, "++ LAYOUT: PHONE IN LANDSCAPE MODE ++");
                listFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT, 1)); //list takes 1/3 screen
                imageFragmentLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2)); //image takes 2/3 screen
            }
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCount = fragmentManager.getBackStackEntryCount(); //gets count of the back stack
        if (backStackCount != 0) //if theres a current back stack
        {
            Log.i(TAG, "++ POPPING STACK ++");
            fragmentManager.popBackStack(); //pops the stack, reformats the layout //pops the back stack to 0
            Log.i(TAG, "++ CURRENT BACK STACK COUNT: " + backStackCount + " ++");
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.actionbar_menu_option1: //broadcast to a1 and a2
                if(choiceSelection == -1)
                {
                    Toast.makeText(MainActivity.this, "A phone was not selected. Pick a phone first!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //broadcasting to A2 to display a toast message
                    Log.i(TAG, "Sending broadcast to A2");
                    Intent a2intent = new Intent(TOAST_BROADCAST);
                    a2intent.putExtra("EXTRA_PHONE_ID", phoneName.get(choiceSelection));
                    a2intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    a2intent.setComponent(new ComponentName("cs478.larryngo.proj3_a2", "cs478.larryngo.proj3_a2.A2BroadcastReceiver"));
                    sendBroadcast(a2intent);

                    //broadcasting to A1 to display the WebView
                    Intent a1intent = new Intent(TOAST_BROADCAST);
                    a1intent.putExtra("EXTRA_WEB_ID", websites.get(choiceSelection));
                    a1intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    a1intent.setComponent(new ComponentName(APP_1_NAME, "cs478.larryngo.proj3_a1.A1BroadcastReceiver"));
                    sendBroadcast(a1intent);
                }
                break;
            case R.id.actionbar_menu_option2: //exits app
                Log.i(TAG, "Exiting app");
                System.exit(0);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        super.onDestroy();
    }
}

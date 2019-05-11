package cs478.larryngo.proj3_a3;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class A3Fragment1 extends ListFragment {
    private final static String TAG = "A3Fragment1";
    private ListSelectionListener listener = null;
    static final String OLD_POSITION = "oldPos";
    Integer mOldPosition = null;

    private ArrayList<String> phoneNames = new ArrayList<String>(Arrays.asList(
            "iPhone XS", "Google Pixel 3", "Samsung Galaxy S10", "Huawei Mate 20",
            "OnePlus 6T", "LG V40 ThinQ", "Xiaomi Mi 9", "HTC U12 Life"));


    public interface ListSelectionListener{
        void onListSelection(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        View retView =  super.onCreateView(inflater, container, savedInstanceState) ;
        if (savedInstanceState != null) {
            int oldPosition = savedInstanceState.getInt(OLD_POSITION) ;
            Log.i(TAG, "++ OLD_POSITION = " + oldPosition + " ++") ;
            mOldPosition = oldPosition;
        }
        else {
            mOldPosition = null;
        }
        return retView ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //adapter = ArrayAdapter.createFromResource(getActivity(), R.array.list_smartphones, android.R.layout.simple_list_item_1); // sets up the list
        //setListAdapter(adapter); //displays the list
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //makes the list only be able to be clicked one at a time
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.titles_item, phoneNames)); //displays list
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            Log.i(TAG, "A3Fragment1 attached!");
            listener = (ListSelectionListener) context; //gets the data from the attachment into the listener
        } catch (ClassCastException ex){
            throw new ClassCastException("Could not attach listener");
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.i(TAG, "++ CLICKED ON ITEM: " + position + " ++");
        Log.i(TAG, "++ CHILD COUNT: " + l.getChildCount() + " ++");
        //Log.i(TAG, "LV: " + l.toString());
        getListView().setItemChecked(position, true); //highlights the choice
        listener.onListSelection(position); //does further functions in MainActivity
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mOldPosition != null)
        {
            int oldPosition = mOldPosition;
            getListView().setSelection(oldPosition);
            listener.onListSelection(oldPosition);
        }
    }
}

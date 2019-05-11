package cs478.larryngo.proj3_a3;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class A3Fragment2 extends Fragment {
    private View view;
    private ImageView imageView;
    private ArrayAdapter<Integer> adapter;
    private final static String TAG = "A3Fragment2";

    public ArrayList<Integer> phoneImages = new ArrayList<Integer>(Arrays.asList(
            R.drawable.phone_image_iphonexs, R.drawable.phone_image_pixel3,
            R.drawable.phone_image_galaxys10, R.drawable.phone_image_mate20,
            R.drawable.phone_image_oneplus6t, R.drawable.phone_image_lgv40,
            R.drawable.phone_image_xiaomimi9, R.drawable.phone_image_htcu12));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment2_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.fragment_imageView);
        adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_gallery_item, phoneImages);
    }

    public void setPhoneImage(int position)
    {
        final int pos = position;
        imageView.setImageResource(phoneImages.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "++ Clicked on image " + pos + " ++");
            }
        });
    }

}

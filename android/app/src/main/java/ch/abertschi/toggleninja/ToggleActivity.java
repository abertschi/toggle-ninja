package ch.abertschi.toggleninja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abertschi on 09/07/16.
 */
public class ToggleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toggle_activity);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> data = new ArrayList<>();
        data.add("Wifi");
        data.add("Hotspot");
        data.add("Bluetooth");
        data.add("Flashlight");
        data.add("Text to speech");
        data.add("Flight mode");
        data.add("Alarm sound");
        data.add("Volume");
        data.add("Vibration");
        data.add("GPS");

        // specify an adapter (see also next example)
        ToggleAdapter mAdapter = new ToggleAdapter(data, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}

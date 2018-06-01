package com.fanwe.www.viewtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.viewtracker.FViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewTracker mViewTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewTracker = new FViewTracker(this);
        mViewTracker.setCallback(new ViewTracker.Callback()
        {
            @Override
            public void onTrack(int x, int y, View source)
            {
                Log.i(TAG, x + "," + y);
            }
        });

        mViewTracker.setSource(findViewById(R.id.btn_source));
        mViewTracker.setTarget(findViewById(R.id.btn_target));
        mViewTracker.start();
    }
}

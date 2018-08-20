package com.fanwe.www.viewtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.viewtracker.FViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker.Position;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewTracker mViewTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_source).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 触发一次追踪信息更新
                getViewTracker().update();
            }
        });
    }

    public ViewTracker getViewTracker()
    {
        if (mViewTracker == null)
        {
            mViewTracker = new FViewTracker();

            // 设置回调对象
            mViewTracker.setCallback(new ViewTracker.Callback()
            {
                /**
                 * 按照指定的位置{@link Position}追踪到target后回调，回调source相对于父布局的x和y值
                 *
                 * @param x      source相对于父布局的x值
                 * @param y      source相对于父布局的y值
                 * @param source 源view
                 * @param target 目标view
                 */
                @Override
                public void onUpdate(int x, int y, View source, View target)
                {
                    Log.i(TAG, x + "," + y);
                }
            });

            mViewTracker
                    // 设置源view
                    .setSource(findViewById(R.id.btn_source))
                    // 设置目标view
                    .setTarget(findViewById(R.id.btn_target))
                    // 设置要追踪的位置，默认左上角对齐
                    .setPosition(ViewTracker.Position.TopLeft);
        }
        return mViewTracker;
    }
}

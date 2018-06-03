package com.fanwe.www.viewtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.viewtracker.FViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker;
import com.fanwe.lib.viewtracker.update.ActivityUpdater;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    /**
     * 创建对象
     */
    private final ViewTracker mViewTracker = new FViewTracker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 设置回调对象
         */
        mViewTracker.setCallback(new ViewTracker.Callback()
        {
            /**
             * source按照指定的位置追踪到target后回调
             *
             * @param x      追踪到target后，source相对于父布局的x
             * @param y      追踪到target后，source相对于父布局的y
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
                /**
                 * 设置源view
                 */
                .setSource(findViewById(R.id.btn_source))
                /**
                 * 设置目标view
                 */
                .setTarget(findViewById(R.id.btn_target))
                /**
                 * 设置要追踪的位置
                 */
                .setPosition(ViewTracker.Position.TopLeft)
                /**
                 * 设置实时更新对象，可以实时更新追踪信息
                 */
                .setUpdater(new ActivityUpdater(this));

        /**
         * 触发一次追踪信息更新
         */
        mViewTracker.update();
        /**
         * 开始实时更新追踪信息，调用此方法前必须先设置一个实时更新对象
         */
        mViewTracker.start();
    }
}

package com.fanwe.www.viewtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.viewtracker.FViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker.Position;
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
             * 在更新追踪信息之前会调用此方法来决定可不可以更新，默认true-可以更新
             *
             * @param source       源view
             * @param sourceParent 源view的父view
             * @param target       目标view
             * @return true-可以更新，false-不要更新
             */
            @Override
            public boolean canUpdate(View source, View sourceParent, View target)
            {
                return super.canUpdate(source, sourceParent, target);
            }

            /**
             * 按照指定的位置{@link Position}追踪到target后回调，回调source在x和y方向需要是什么值才可以到指定的位置
             *
             * @param x            source相对于父布局需要的x值
             * @param y            source相对于父布局需要的y值
             * @param source       源view
             * @param sourceParent 源view的父view
             * @param target       目标view
             */
            @Override
            public void onUpdate(int x, int y, View source, View sourceParent, View target)
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

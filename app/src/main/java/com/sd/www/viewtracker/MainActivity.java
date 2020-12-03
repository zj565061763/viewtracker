package com.sd.www.viewtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.viewtracker.FViewTracker;
import com.sd.lib.viewtracker.ViewTracker;
import com.sd.lib.viewtracker.ViewTracker.Position;
import com.sd.www.viewtracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;
    private ViewTracker mViewTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
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

            // 设置源view
            mViewTracker.setSource(mBinding.viewSource);
            // 设置目标view
            mViewTracker.setTarget(mBinding.viewTarget);
        }
        return mViewTracker;
    }

    @Override
    public void onClick(View v)
    {
        if (v == mBinding.btnTopLeft)
        {
            getViewTracker().setPosition(Position.TopLeft);
            getViewTracker().update();
        } else if (v == mBinding.btnTopCenter)
        {
            getViewTracker().setPosition(Position.TopCenter);
            getViewTracker().update();
        } else if (v == mBinding.btnTopRight)
        {
            getViewTracker().setPosition(Position.TopRight);
            getViewTracker().update();
        } else if (v == mBinding.btnLeftCenter)
        {
            getViewTracker().setPosition(Position.LeftCenter);
            getViewTracker().update();
        } else if (v == mBinding.btnCenter)
        {
            getViewTracker().setPosition(Position.Center);
            getViewTracker().update();
        } else if (v == mBinding.btnRightCenter)
        {
            getViewTracker().setPosition(Position.RightCenter);
            getViewTracker().update();
        } else if (v == mBinding.btnBottomLeft)
        {
            getViewTracker().setPosition(Position.BottomLeft);
            getViewTracker().update();
        } else if (v == mBinding.btnBottomCenter)
        {
            getViewTracker().setPosition(Position.BottomCenter);
            getViewTracker().update();
        } else if (v == mBinding.btnBottomRight)
        {
            getViewTracker().setPosition(Position.BottomRight);
            getViewTracker().update();
        }
    }
}

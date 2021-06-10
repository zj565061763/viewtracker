package com.sd.demo.viewtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.viewtracker.databinding.ActivityMainBinding;
import com.sd.lib.viewtracker.ViewTracker;
import com.sd.lib.viewtracker.ViewTracker.Position;
import com.sd.lib.viewtracker.ext.FPositionTracker;
import com.sd.lib.viewtracker.updater.ViewUpdater;
import com.sd.lib.viewtracker.updater.impl.OnPreDrawUpdater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;
    private FPositionTracker mViewTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        getViewTracker().setSource(mBinding.viewSource);
        getViewTracker().setTarget(mBinding.viewTarget);
    }

    private FPositionTracker getViewTracker() {
        if (mViewTracker == null) {
            mViewTracker = new FPositionTracker() {
                @NonNull
                @Override
                protected ViewUpdater createTargetUpdater() {
                    return new OnPreDrawUpdater();
                }
            };
            // 设置回调对象
            mViewTracker.setCallback(new ViewTracker.Callback() {
                /**
                 * 按照指定的位置{@link Position}追踪到target后回调，回调source相对于父布局的x和y值
                 *
                 * @param x      source相对于父布局的x值
                 * @param y      source相对于父布局的y值
                 * @param source 源view
                 * @param target 目标view
                 */
                @Override
                public void onUpdate(int x, int y, @NonNull View source, @NonNull View target) {
                    Log.i(TAG, x + "," + y);
                    source.layout(x, y, x + source.getMeasuredWidth(), y + source.getMeasuredHeight());
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
    public void onClick(View v) {
        if (v == mBinding.btnTopLeft) {
            getViewTracker().setPosition(Position.TopLeft);
            getViewTracker().start();
        } else if (v == mBinding.btnTopCenter) {
            getViewTracker().setPosition(Position.TopCenter);
            getViewTracker().start();
        } else if (v == mBinding.btnTopRight) {
            getViewTracker().setPosition(Position.TopRight);
            getViewTracker().start();
        } else if (v == mBinding.btnLeftCenter) {
            getViewTracker().setPosition(Position.LeftCenter);
            getViewTracker().start();
        } else if (v == mBinding.btnCenter) {
            getViewTracker().setPosition(Position.Center);
            getViewTracker().start();
        } else if (v == mBinding.btnRightCenter) {
            getViewTracker().setPosition(Position.RightCenter);
            getViewTracker().start();
        } else if (v == mBinding.btnBottomLeft) {
            getViewTracker().setPosition(Position.BottomLeft);
            getViewTracker().start();
        } else if (v == mBinding.btnBottomCenter) {
            getViewTracker().setPosition(Position.BottomCenter);
            getViewTracker().start();
        } else if (v == mBinding.btnBottomRight) {
            getViewTracker().setPosition(Position.BottomRight);
            getViewTracker().start();
        }
    }
}
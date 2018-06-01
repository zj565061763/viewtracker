/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.viewtracker;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import java.lang.ref.WeakReference;

/**
 * 可以让PopView显示在Target的某个位置
 */
public abstract class FViewTracker implements ViewTracker
{
    private ViewDrawListener mDrawListener;

    private WeakReference<View> mSource;
    private WeakReference<View> mTarget;

    private Position mPosition = Position.TopRight;
    private int mMarginX;
    private int mMarginY;

    private final int[] mLocationTarget = {0, 0};
    private final int[] mLocationParent = {0, 0};
    private int mSourceX;
    private int mSourceY;

    private boolean mIsDebug;

    public FViewTracker(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");

        getDrawListener().setView(activity.findViewById(android.R.id.content));
    }

    @Override
    public ViewTracker setDebug(boolean debug)
    {
        mIsDebug = debug;
        return this;
    }

    @Override
    public ViewTracker setSource(final View source)
    {
        final View old = getSource();
        if (old != source)
            mSource = source == null ? null : new WeakReference<>(source);
        return this;
    }

    @Override
    public ViewTracker setTarget(final View target)
    {
        final View old = getTarget();
        if (old != target)
            mTarget = target == null ? null : new WeakReference<>(target);
        return this;
    }

    @Override
    public ViewTracker setPosition(Position position)
    {
        if (position == null)
            throw new NullPointerException("position is null");

        mPosition = position;
        return this;
    }

    @Override
    public ViewTracker setMarginX(int marginX)
    {
        mMarginX = marginX;
        return this;
    }

    @Override
    public ViewTracker setMarginY(int marginY)
    {
        mMarginY = marginY;
        return this;
    }

    @Override
    public View getSource()
    {
        return mSource == null ? null : mSource.get();
    }

    @Override
    public View getTarget()
    {
        return mTarget == null ? null : mTarget.get();
    }

    @Override
    public ViewTracker track(boolean track)
    {
        if (track)
        {
            getDrawListener().register();
            updatePosition();
        } else
        {
            getDrawListener().unregister();
        }
        return this;
    }

    private ViewDrawListener getDrawListener()
    {
        if (mDrawListener == null)
        {
            mDrawListener = new ViewDrawListener()
            {
                @Override
                protected void onRegisterChanged(boolean isRegister)
                {
                    super.onRegisterChanged(isRegister);
                    if (mIsDebug)
                        Log.i(ViewTracker.class.getSimpleName(), FViewTracker.this + " DrawListener isRegister:" + isRegister);
                }

                @Override
                protected void onDraw()
                {
                    updatePosition();
                }
            };
        }
        return mDrawListener;
    }

    /**
     * 刷新追踪信息
     */
    public final void updatePosition()
    {
        final View source = getSource();
        if (source == null)
            return;

        final View target = getTarget();
        if (target == null)
            return;

        final ViewParent viewParent = source.getParent();
        if (viewParent == null)
            return;

        ((View) viewParent).getLocationOnScreen(mLocationParent);
        getTarget().getLocationOnScreen(mLocationTarget);

        mSourceX = mLocationTarget[0] - mLocationParent[0] + mMarginX;
        mSourceY = mLocationTarget[1] - mLocationParent[1] + mMarginY;

        switch (mPosition)
        {
            case TopLeft:
                layoutTopLeft(source, target);
                break;
            case TopCenter:
                layoutTopCenter(source, target);
                break;
            case TopRight:
                layoutTopRight(source, target);
                break;

            case LeftCenter:
                layoutLeftCenter(source, target);
                break;
            case Center:
                layoutCenter(source, target);
                break;
            case RightCenter:
                layoutRightCenter(source, target);
                break;

            case BottomLeft:
                layoutBottomLeft(source, target);
                break;
            case BottomCenter:
                layoutBottomCenter(source, target);
                break;
            case BottomRight:
                layoutBottomRight(source, target);
                break;

            case TopOutsideLeft:
                layoutTopOutsideLeft(source, target);
                break;
            case TopOutsideCenter:
                layoutTopOutsideCenter(source, target);
                break;
            case TopOutsideRight:
                layoutTopOutsideRight(source, target);
                break;

            case BottomOutsideLeft:
                layoutBottomOutsideLeft(source, target);
                break;
            case BottomOutsideCenter:
                layoutBottomOutsideCenter(source, target);
                break;
            case BottomOutsideRight:
                layoutBottomOutsideRight(source, target);
                break;

            case LeftOutsideTop:
                layoutLeftOutsideTop(source, target);
                break;
            case LeftOutsideCenter:
                layoutLeftOutsideCenter(source, target);
                break;
            case LeftOutsideBottom:
                layoutLeftOutsideBottom(source, target);
                break;

            case RightOutsideTop:
                layoutRightOutsideTop(source, target);
                break;
            case RightOutsideCenter:
                layoutRightOutsideCenter(source, target);
                break;
            case RightOutsideBottom:
                layoutRightOutsideBottom(source, target);
                break;
            default:
                break;
        }

        onTrack(mSourceX, mSourceY, source);
    }

    //---------- position start----------

    private void layoutTopLeft(View source, View target)
    {
    }

    private void layoutTopCenter(View source, View target)
    {
        mSourceX += (target.getWidth() - source.getWidth()) / 2;
    }

    private void layoutTopRight(View source, View target)
    {
        mSourceX += (target.getWidth() - source.getWidth());
    }

    private void layoutLeftCenter(View source, View target)
    {
        mSourceY += (target.getHeight() - source.getHeight()) / 2;
    }

    private void layoutCenter(View source, View target)
    {
        layoutTopCenter(source, target);
        layoutLeftCenter(source, target);
    }

    private void layoutRightCenter(View source, View target)
    {
        layoutTopRight(source, target);
        layoutLeftCenter(source, target);
    }

    private void layoutBottomLeft(View source, View target)
    {
        mSourceY += target.getHeight() - source.getHeight();
    }

    private void layoutBottomCenter(View source, View target)
    {
        layoutTopCenter(source, target);
        layoutBottomLeft(source, target);
    }

    private void layoutBottomRight(View source, View target)
    {
        layoutTopRight(source, target);
        layoutBottomLeft(source, target);
    }

    private void layoutTopOutsideLeft(View source, View target)
    {
        layoutTopLeft(source, target);
        mSourceY -= source.getHeight();
    }

    private void layoutTopOutsideCenter(View source, View target)
    {
        layoutTopCenter(source, target);
        mSourceY -= source.getHeight();
    }

    private void layoutTopOutsideRight(View source, View target)
    {
        layoutTopRight(source, target);
        mSourceY -= source.getHeight();
    }

    private void layoutBottomOutsideLeft(View source, View target)
    {
        layoutBottomLeft(source, target);
        mSourceY += source.getHeight();
    }

    private void layoutBottomOutsideCenter(View source, View target)
    {
        layoutBottomCenter(source, target);
        mSourceY += source.getHeight();
    }

    private void layoutBottomOutsideRight(View source, View target)
    {
        layoutBottomRight(source, target);
        mSourceY += source.getHeight();
    }

    private void layoutLeftOutsideTop(View source, View target)
    {
        layoutTopLeft(source, target);
        mSourceX -= source.getWidth();
    }

    private void layoutLeftOutsideCenter(View source, View target)
    {
        layoutLeftCenter(source, target);
        mSourceX -= source.getWidth();
    }

    private void layoutLeftOutsideBottom(View source, View target)
    {
        layoutBottomLeft(source, target);
        mSourceX -= source.getWidth();
    }

    private void layoutRightOutsideTop(View source, View target)
    {
        layoutTopRight(source, target);
        mSourceX += source.getWidth();
    }

    private void layoutRightOutsideCenter(View source, View target)
    {
        layoutRightCenter(source, target);
        mSourceX += source.getWidth();
    }

    private void layoutRightOutsideBottom(View source, View target)
    {
        layoutBottomRight(source, target);
        mSourceX += source.getWidth();
    }

    //---------- position end----------

    protected abstract void onTrack(int x, int y, View source);
}

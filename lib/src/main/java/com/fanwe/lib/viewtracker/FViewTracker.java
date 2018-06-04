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

import android.view.View;
import android.view.ViewParent;

import com.fanwe.lib.viewtracker.update.Updater;

import java.lang.ref.WeakReference;

/**
 * view的位置追踪
 */
public class FViewTracker implements ViewTracker
{
    private WeakReference<View> mSource;
    private WeakReference<View> mTarget;

    private Position mPosition = Position.TopRight;
    private int mMarginX;
    private int mMarginY;

    private final int[] mLocationTarget = {0, 0};
    private final int[] mLocationParent = {0, 0};
    private int mX;
    private int mY;

    private Callback mCallback;
    private boolean mIsStarted;

    @Override
    public ViewTracker setCallback(Callback callback)
    {
        mCallback = callback;
        return this;
    }

    @Override
    public ViewTracker setSource(final View source)
    {
        final View old = getSource();
        if (old != source)
        {
            mSource = source == null ? null : new WeakReference<>(source);

            if (mCallback != null)
                mCallback.onSourceChanged(source, old);
        }
        return this;
    }

    @Override
    public ViewTracker setTarget(final View target)
    {
        final View old = getTarget();
        if (old != target)
        {
            mTarget = target == null ? null : new WeakReference<>(target);

            if (mCallback != null)
                mCallback.onTargetChanged(target, old);
        }
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

    private Updater mUpdater;

    @Override
    public ViewTracker setUpdater(Updater updater)
    {
        if (mUpdater != updater)
        {
            mUpdater = updater;
            if (updater != null)
                updater.setUpdatable(this);
        }
        return this;
    }

    @Override
    public boolean start()
    {
        if (mUpdater == null)
            throw new NullPointerException(Updater.class.getName() + " instance must be provided before this, see the setUpdater(Updater) method");

        boolean result = false;
        if (update())
            result = mUpdater.start();

        updateStarted();
        return result;
    }

    @Override
    public void stop()
    {
        if (mUpdater != null)
            mUpdater.stop();

        updateStarted();
    }

    @Override
    public boolean isStarted()
    {
        if (mUpdater != null)
            return mUpdater.isStarted();
        return false;
    }

    private void updateStarted()
    {
        final boolean started = isStarted();
        if (mIsStarted != started)
        {
            mIsStarted = started;

            if (mCallback != null)
                mCallback.onStateChanged(started);
        }
    }

    @Override
    public final boolean update()
    {
        if (mCallback == null)
        {
            stop();
            return false;
        }

        final View source = getSource();
        if (source == null)
        {
            stop();
            return false;
        }

        final View target = getTarget();
        if (target == null)
        {
            stop();
            return false;
        }

        final ViewParent parent = source.getParent();
        if (parent == null)
        {
            stop();
            return false;
        }

        final View sourceParent = (View) parent;

        if (!mCallback.canUpdate(source, sourceParent, target))
            return false;

        sourceParent.getLocationOnScreen(mLocationParent);
        getTarget().getLocationOnScreen(mLocationTarget);

        mX = mLocationTarget[0] - mLocationParent[0] + mMarginX;
        mY = mLocationTarget[1] - mLocationParent[1] + mMarginY;

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

        mCallback.onUpdate(mX, mY, source, sourceParent, target);
        return true;
    }

    //---------- position start----------

    private void layoutTopLeft(View source, View target)
    {
    }

    private void layoutTopCenter(View source, View target)
    {
        mX += (target.getWidth() - source.getWidth()) >> 1;
    }

    private void layoutTopRight(View source, View target)
    {
        mX += target.getWidth() - source.getWidth();
    }

    private void layoutLeftCenter(View source, View target)
    {
        mY += (target.getHeight() - source.getHeight()) >> 1;
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
        mY += target.getHeight() - source.getHeight();
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
        mY -= source.getHeight();
    }

    private void layoutTopOutsideCenter(View source, View target)
    {
        layoutTopCenter(source, target);
        mY -= source.getHeight();
    }

    private void layoutTopOutsideRight(View source, View target)
    {
        layoutTopRight(source, target);
        mY -= source.getHeight();
    }

    private void layoutBottomOutsideLeft(View source, View target)
    {
        layoutBottomLeft(source, target);
        mY += source.getHeight();
    }

    private void layoutBottomOutsideCenter(View source, View target)
    {
        layoutBottomCenter(source, target);
        mY += source.getHeight();
    }

    private void layoutBottomOutsideRight(View source, View target)
    {
        layoutBottomRight(source, target);
        mY += source.getHeight();
    }

    private void layoutLeftOutsideTop(View source, View target)
    {
        layoutTopLeft(source, target);
        mX -= source.getWidth();
    }

    private void layoutLeftOutsideCenter(View source, View target)
    {
        layoutLeftCenter(source, target);
        mX -= source.getWidth();
    }

    private void layoutLeftOutsideBottom(View source, View target)
    {
        layoutBottomLeft(source, target);
        mX -= source.getWidth();
    }

    private void layoutRightOutsideTop(View source, View target)
    {
        layoutTopRight(source, target);
        mX += source.getWidth();
    }

    private void layoutRightOutsideCenter(View source, View target)
    {
        layoutRightCenter(source, target);
        mX += source.getWidth();
    }

    private void layoutRightOutsideBottom(View source, View target)
    {
        layoutBottomRight(source, target);
        mX += source.getWidth();
    }

    //---------- position end----------
}

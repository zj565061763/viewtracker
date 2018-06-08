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

    private ViewSize mViewSizeX;
    private ViewSize mViewSizeY;

    private final int[] mLocationTarget = {0, 0};
    private final int[] mLocationParent = {0, 0};
    private int mX;
    private int mY;

    private Callback mCallback;

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
    public ViewTracker setMarginX(View view, boolean add)
    {
        if (view != null)
        {
            if (mViewSizeX == null)
                mViewSizeX = new ViewSize(true);

            mViewSizeX.setView(view);
            mViewSizeX.setAdd(add);
        } else
        {
            mViewSizeX = null;
        }
        return this;
    }

    @Override
    public ViewTracker setMarginY(View view, boolean add)
    {
        if (view != null)
        {
            if (mViewSizeY == null)
                mViewSizeY = new ViewSize(false);

            mViewSizeY.setView(view);
            mViewSizeY.setAdd(add);
        } else
        {
            mViewSizeY = null;
        }
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
    public final boolean update()
    {
        if (mCallback == null)
            return false;

        final View source = getSource();
        final View target = getTarget();

        if (!mCallback.canUpdate(source, target))
            return false;

        if (source == null || target == null)
            return false;

        final ViewParent parent = source.getParent();
        if (parent == null)
            return false;

        ((View) parent).getLocationOnScreen(mLocationParent);
        getTarget().getLocationOnScreen(mLocationTarget);

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

            case Left:
                layoutLeft(source, target);
                break;
            case Top:
                layoutTop(source, target);
                break;
            case Right:
                layoutRight(source, target);
                break;
            case Bottom:
                layoutBottom(source, target);
                break;
        }

        mX += getMarginXTotal();
        mY += getMarginYTotal();

        mCallback.onUpdate(mX, mY, source, ((View) parent), target);
        return true;
    }

    private int getMarginXTotal()
    {
        int result = mMarginX;

        if (mViewSizeX != null)
            result += mViewSizeX.getDeltaSize();

        return result;
    }

    private int getMarginYTotal()
    {
        int result = mMarginY;

        if (mViewSizeY != null)
            result += mViewSizeY.getDeltaSize();

        return result;
    }

    private int getX_alignLeft()
    {
        return mLocationTarget[0] - mLocationParent[0];
    }

    private int getX_alignRight(View source, View target)
    {
        return getX_alignLeft() + (target.getWidth() - source.getWidth());
    }

    private int getX_alignCenter(View source, View target)
    {
        return getX_alignLeft() + (target.getWidth() - source.getWidth()) / 2;
    }

    private int getY_alignTop()
    {
        return mLocationTarget[1] - mLocationParent[1];
    }

    private int getY_alignBottom(View source, View target)
    {
        return getY_alignTop() + (target.getHeight() - source.getHeight());
    }

    private int getY_alignCenter(View source, View target)
    {
        return getY_alignTop() + (target.getHeight() - source.getHeight()) / 2;
    }

    //---------- position start----------

    private void layoutTopLeft(View source, View target)
    {
        mY = getY_alignTop();
        mX = getX_alignLeft();
    }

    private void layoutTopCenter(View source, View target)
    {
        mY = getY_alignTop();
        mX = getX_alignCenter(source, target);
    }

    private void layoutTopRight(View source, View target)
    {
        mY = getY_alignTop();
        mX = getX_alignRight(source, target);
    }


    private void layoutLeftCenter(View source, View target)
    {
        mX = getX_alignLeft();
        mY = getY_alignCenter(source, target);
    }

    private void layoutCenter(View source, View target)
    {
        mX = getX_alignCenter(source, target);
        mY = getY_alignCenter(source, target);
    }

    private void layoutRightCenter(View source, View target)
    {
        mX = getX_alignRight(source, target);
        mY = getY_alignCenter(source, target);
    }


    private void layoutBottomLeft(View source, View target)
    {
        mY = getY_alignBottom(source, target);
        mX = getX_alignLeft();
    }

    private void layoutBottomCenter(View source, View target)
    {
        mY = getY_alignBottom(source, target);
        mX = getX_alignCenter(source, target);
    }

    private void layoutBottomRight(View source, View target)
    {
        mY = getY_alignBottom(source, target);
        mX = getX_alignRight(source, target);
    }


    private void layoutLeft(View source, View target)
    {
        mX = getX_alignLeft();
        mY = source.getTop();
    }

    private void layoutTop(View source, View target)
    {
        mY = getY_alignTop();
        mX = source.getLeft();
    }

    private void layoutRight(View source, View target)
    {
        mX = getX_alignRight(source, target);
        mY = source.getTop();
    }

    private void layoutBottom(View source, View target)
    {
        mY = getY_alignBottom(source, target);
        mX = source.getLeft();
    }

    //---------- position end----------

    private static final class ViewSize
    {
        private WeakReference<View> mView;
        private boolean mIsAdd;
        private boolean mIsWidth;

        public ViewSize(boolean isWidth)
        {
            mIsWidth = isWidth;
        }

        public void setAdd(boolean add)
        {
            mIsAdd = add;
        }

        private View getView()
        {
            return mView == null ? null : mView.get();
        }

        public void setView(View view)
        {
            final View old = getView();
            if (old != view)
                mView = view == null ? null : new WeakReference<>(view);
        }

        public int getDeltaSize()
        {
            final View view = getView();
            if (view == null)
                return 0;

            final int size = mIsWidth ? view.getWidth() : view.getHeight();
            return mIsAdd ? size : -size;
        }
    }
}

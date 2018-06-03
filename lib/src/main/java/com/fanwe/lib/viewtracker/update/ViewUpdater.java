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
package com.fanwe.lib.viewtracker.update;

import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

/**
 * View实时刷新
 */
public class ViewUpdater implements Updater
{
    private WeakReference<View> mView;
    private boolean mIsStarted;

    private Update mUpdate;

    @Override
    public void setUpdate(Update update)
    {
        mUpdate = update;
    }

    @Override
    public final boolean start()
    {
        stop();

        final View view = getView();
        if (view != null)
        {
            final ViewTreeObserver observer = view.getViewTreeObserver();
            if (observer.isAlive())
            {
                observer.removeOnPreDrawListener(mListener);
                observer.addOnPreDrawListener(mListener);
                setStarted(true);
            }
        }

        return isStarted();
    }

    @Override
    public final void stop()
    {
        final View view = getView();
        if (view != null)
        {
            final ViewTreeObserver observer = view.getViewTreeObserver();
            if (observer.isAlive())
                observer.removeOnPreDrawListener(mListener);
        }

        setStarted(false);
    }

    @Override
    public final boolean isStarted()
    {
        return mIsStarted;
    }

    public final void setView(View view)
    {
        final View old = getView();
        if (old != view)
        {
            final boolean isStartedBefore = mIsStarted;
            stop();

            mView = view == null ? null : new WeakReference<>(view);

            if (isStartedBefore)
            {
                start();
                if (!isStarted())
                    throw new RuntimeException("start failure when view changed");
            }
        }
    }

    private View getView()
    {
        final View view = mView == null ? null : mView.get();

        if (view == null)
            setStarted(false);

        return view;
    }

    private void setStarted(boolean started)
    {
        if (mIsStarted != started)
        {
            mIsStarted = started;
            onStateChanged(started);
        }
    }

    private final ViewTreeObserver.OnPreDrawListener mListener = new ViewTreeObserver.OnPreDrawListener()
    {
        @Override
        public boolean onPreDraw()
        {
            if (mUpdate != null)
                mUpdate.update();
            return true;
        }
    };

    protected void onStateChanged(boolean started)
    {
    }

}

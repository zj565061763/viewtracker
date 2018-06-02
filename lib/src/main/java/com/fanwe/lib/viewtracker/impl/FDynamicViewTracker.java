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
package com.fanwe.lib.viewtracker.impl;

import android.app.Activity;
import android.util.Log;

import com.fanwe.lib.viewtracker.DynamicViewTracker;
import com.fanwe.lib.viewtracker.ViewTracker;
import com.fanwe.lib.viewtracker.utils.ViewDrawListener;

/**
 * 动态实时追踪view位置
 */
public class FDynamicViewTracker extends FViewTracker implements DynamicViewTracker
{
    private final ViewDrawListener mDrawListener;

    public FDynamicViewTracker(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");

        mDrawListener = new ViewDrawListener()
        {
            @Override
            protected void onRegisterChanged(boolean isRegister)
            {
                super.onRegisterChanged(isRegister);
                if (isDebug())
                    Log.i(ViewTracker.class.getSimpleName(), FDynamicViewTracker.this + " ViewDrawListener isRegister:" + isRegister);
            }

            @Override
            protected void onDraw()
            {
                track();
            }
        };
        mDrawListener.setView(activity.findViewById(android.R.id.content));
    }

    @Override
    public boolean start()
    {
        if (track())
            mDrawListener.register();

        return isTracking();
    }

    @Override
    public boolean isTracking()
    {
        return mDrawListener.isRegister();
    }

    @Override
    public void stop()
    {
        mDrawListener.unregister();
    }
}

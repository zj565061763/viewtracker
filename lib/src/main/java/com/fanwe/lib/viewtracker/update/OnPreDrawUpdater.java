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

import android.view.ViewTreeObserver;

/**
 * 设置监听对象到view中来实现实时刷新
 * <p>
 * 监听对象：{@link android.view.ViewTreeObserver.OnPreDrawListener}
 */
public class OnPreDrawUpdater extends ViewTreeObserverUpdater
{
    @Override
    protected boolean register(ViewTreeObserver observer)
    {
        observer.removeOnPreDrawListener(mListener);
        observer.addOnPreDrawListener(mListener);
        return true;
    }

    @Override
    protected void unregister(ViewTreeObserver observer)
    {
        observer.removeOnPreDrawListener(mListener);
    }

    private final ViewTreeObserver.OnPreDrawListener mListener = new ViewTreeObserver.OnPreDrawListener()
    {
        @Override
        public boolean onPreDraw()
        {
            final Updatable updatable = getUpdatable();
            if (updatable != null)
                updatable.update();
            return true;
        }
    };
}

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

import java.lang.ref.WeakReference;

public abstract class BaseViewUpdater extends BaseUpdater
{
    private WeakReference<View> mView;

    public final Updater setView(View view)
    {
        final View old = getView();
        if (old != view)
        {
            final boolean isStartedBefore = isStarted();
            stop();

            mView = view == null ? null : new WeakReference<>(view);

            if (isStartedBefore)
            {
                start();
                if (!isStarted())
                    throw new RuntimeException("start failure when view changed");
            }
        }
        return this;
    }

    public final View getView()
    {
        final View view = mView == null ? null : mView.get();

        if (view == null)
            setStarted(false);

        return view;
    }
}

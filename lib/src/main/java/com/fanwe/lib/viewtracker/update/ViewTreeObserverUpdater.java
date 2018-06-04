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

public abstract class ViewTreeObserverUpdater extends BaseViewUpdater
{
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
                if (register(observer))
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
                unregister(observer);
        }

        setStarted(false);
    }

    /**
     * 注册监听
     *
     * @param observer
     * @return true-注册成功
     */
    protected abstract boolean register(ViewTreeObserver observer);

    /**
     * 取消注册监听
     *
     * @param observer
     */
    protected abstract void unregister(ViewTreeObserver observer);
}

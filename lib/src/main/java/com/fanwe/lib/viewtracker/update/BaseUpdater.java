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

public abstract class BaseUpdater implements Updater
{
    private boolean mIsStarted;
    private Updatable mUpdatable;

    @Override
    public final void setUpdatable(Updatable updatable)
    {
        mUpdatable = updatable;
    }

    @Override
    public final Updatable getUpdatable()
    {
        return mUpdatable;
    }

    /**
     * 设置是否已经开始实时更新
     *
     * @param started
     */
    protected final void setStarted(boolean started)
    {
        if (mIsStarted != started)
        {
            mIsStarted = started;
            onStateChanged(started);
        }
    }

    @Override
    public final boolean isStarted()
    {
        return mIsStarted;
    }

    /**
     * 是否已经开始实时更新状态变化
     *
     * @param started
     */
    protected void onStateChanged(boolean started)
    {
    }
}

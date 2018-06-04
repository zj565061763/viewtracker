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

import android.app.Activity;

/**
 * 设置监听对象到Activity中id为android.R.id.conent的view来实现实时更新
 * <p>
 * 监听对象：{@link android.view.ViewTreeObserver.OnPreDrawListener}
 */
public class ActivityOnPreDrawUpdater extends OnPreDrawUpdater
{
    public ActivityOnPreDrawUpdater(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");
        setView(activity.findViewById(android.R.id.content));
    }
}

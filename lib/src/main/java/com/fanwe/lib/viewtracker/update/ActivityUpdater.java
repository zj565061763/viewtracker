package com.fanwe.lib.viewtracker.update;

import android.app.Activity;

/**
 * Activity实时刷新
 */
public class ActivityUpdater extends ViewUpdater
{
    public ActivityUpdater(Activity activity)
    {
        if (activity == null)
            throw new NullPointerException("activity is null");
        setView(activity.findViewById(android.R.id.content));
    }
}

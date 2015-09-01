package com.airplay.app;

import android.app.Application;

/**
 * Created by liyulong on 15/8/29.
 */
public class MyApplication extends Application
{
    public static Application instance = null;
    public MyApplication()
    {
        super();
        instance = this;
    }
}

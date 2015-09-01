package com.airplay.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by liyulong on 15/8/29.
 */
public class AirplayService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}

package com.droidairplay.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by liyulong on 15/9/1.
 */
public class AirPlayService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}

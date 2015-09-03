package com.droidairplay.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by liyulong on 15/9/1.
 */
public class AirPlayService extends Service
{
    private IBinder binder = new AirPlayBinder();

    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }


    public class AirPlayBinder extends Binder
    {
        public AirPlayService getService()
        {
            return AirPlayService.this;
        }
    }

}

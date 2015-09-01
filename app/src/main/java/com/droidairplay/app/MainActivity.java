package com.droidairplay.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import droid.airplay.AirPlayServer;


public class MainActivity extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    boolean isOnAir = false;
    public void startAirPlay(View view)
    {
        String msg = "bug occur!";
        if(isOnAir && AirPlayServer.getIstance().isOn)
        {
            msg = "airplay already on!";
        }
        else
        {
            final AirPlayServer airPlayServer = AirPlayServer.getIstance();
            airPlayServer.setRtspPort(8998);
            Thread airThread = new Thread(airPlayServer);
            airThread.start();
            //airPlayServer.run();
            msg = "starting...";
            isOnAir = true;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

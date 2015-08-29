package com.airplay.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import nz.co.iswe.android.airplay.AirPlayServer;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    boolean isOnAir = false;
    Thread airThread = null;
    public void startAirplay(View view)
    {
        String msg = "bug occur!";
        if(isOnAir)
        {
            msg = "airplay already on!";
        }
        else
        {
            final AirPlayServer airPlayServer = AirPlayServer.getIstance();
            airPlayServer.setRtspPort(8998);
            airThread = new Thread(airPlayServer);
            airThread.start();
            //airPlayServer.run();
            msg = "starting...";
            isOnAir = true;
        }
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void stopAirplay(View view)//TODO:实现停止
    {
        String msg = "bug occur!";
        if(!isOnAir)
        {
            msg = "has not been started!";
        }
        else
        {
            try
            {
                airThread.stop();
            }
            catch (Exception e)
            {

            }
            msg = "has been stoped";
        }
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

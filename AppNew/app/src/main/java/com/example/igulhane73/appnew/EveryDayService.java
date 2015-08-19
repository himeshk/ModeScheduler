package com.example.igulhane73.appnew;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.Toast;

import com.example.igulhane73.appnew.dbOps.ConfigDatabaseOperations;
import com.example.igulhane73.appnew.info.ConfigTableData;

import java.util.Calendar;

/**
 * Created by HimeshK on 8/19/2015.
 */
public class EveryDayService extends Service {
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("In here" );
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        ConfigDatabaseOperations cdp = new ConfigDatabaseOperations(getBaseContext());
        Calendar cl = Calendar.getInstance();
        int currentDay = cl.get(Calendar.DAY_OF_WEEK);
        String daysToCheck[] = null;
        if (currentDay == Calendar.SUNDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SunDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.MonDay;
        }
        if (currentDay == Calendar.MONDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.MonDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.TuesDay;
        }
        if (currentDay == Calendar.TUESDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.TuesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.WednesDay;
        }
        if (currentDay == Calendar.WEDNESDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.WednesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.ThursDay;
        }
        if (currentDay == Calendar.THURSDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.ThursDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.FriDay;
        }
        if (currentDay == Calendar.FRIDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.FriDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SaturDay;
        }
        if (currentDay == Calendar.SATURDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SaturDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SunDay;
        }

        Cursor cr = cdp.retrieveNewTimeConfig(cdp.getReadableDatabase() ,"( " + daysToCheck[0] + " = '1' OR " +
                daysToCheck[1] + " = '1') " +
                " AND " + ConfigTableData.TimeConfigTableInfo.active + " = true "  , null);
        if(cr.moveToFirst()) {
            Toast.makeText(getApplicationContext() , " yes ", Toast.LENGTH_LONG);
        }
        while (true){}
        //stopSelf();
        //return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

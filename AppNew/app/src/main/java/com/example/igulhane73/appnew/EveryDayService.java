package com.example.igulhane73.appnew;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

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
        //System.out.println("In here" );
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        ConfigDatabaseOperations cdp = new ConfigDatabaseOperations(getApplicationContext());
        Calendar cl = Calendar.getInstance();
        int currentDay = cl.get(Calendar.DAY_OF_WEEK);
        String daysToCheck[] = new String[2];
        int dayOfTheWeek[] =  new int[2];
        if (currentDay == Calendar.SUNDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SunDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.MonDay;
            dayOfTheWeek[0] = Calendar.SUNDAY;
            dayOfTheWeek[1] = Calendar.MONDAY;
        }
        if (currentDay == Calendar.MONDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.MonDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.TuesDay;
            dayOfTheWeek[0] = Calendar.MONDAY;
            dayOfTheWeek[1] = Calendar.TUESDAY;

        }
        if (currentDay == Calendar.TUESDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.TuesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.WednesDay;
            dayOfTheWeek[0] = Calendar.TUESDAY;
            dayOfTheWeek[1] = Calendar.WEDNESDAY;

        }
        if (currentDay == Calendar.WEDNESDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.WednesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.ThursDay;
            dayOfTheWeek[0] = Calendar.WEDNESDAY;
            dayOfTheWeek[1] = Calendar.THURSDAY;

        }
        if (currentDay == Calendar.THURSDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.ThursDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.FriDay;
            dayOfTheWeek[0] = Calendar.THURSDAY;
            dayOfTheWeek[1] = Calendar.FRIDAY;

        }
        if (currentDay == Calendar.FRIDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.FriDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SaturDay;
            dayOfTheWeek[0] = Calendar.FRIDAY;
            dayOfTheWeek[1] = Calendar.SATURDAY;

        }
        if (currentDay == Calendar.SATURDAY){
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SaturDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SunDay;
            dayOfTheWeek[0] = Calendar.SATURDAY;
            dayOfTheWeek[1] = Calendar.SUNDAY;

        }
        try {
            Cursor cr = cdp.retrieveNewTimeConfig(cdp.getReadableDatabase(), "( " + daysToCheck[0] + " = '1' OR " +
                    daysToCheck[1] + " = '1') " +
                    " AND " + ConfigTableData.TimeConfigTableInfo.active, null);
            if (cr.moveToFirst()) {
                do {
                    // setting alarm for all alarms for the day and tomo ;
                    AudioManager audioManager = (AudioManager)getSystemService(getApplication().AUDIO_SERVICE);
                    Intent temp = new Intent(getApplicationContext(), ChangeModeService.class);
                    Intent temp2 = new Intent(getApplicationContext(), ChangeModeService.class);
                    temp.putExtra(ConfigTableData.TimeConfigTableInfo.name, cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.name)));
                    temp.setAction("-" + cr.getInt(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.id)));
                    temp.setType("StartAlaram-" + cr.getInt(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.id)));
                    temp2.setAction("+" + cr.getInt(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.id)));
                    temp2.setType("StopAlaram+" + cr.getInt(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.id)));
                    temp.putExtra("mode", cr.getInt(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.mode)));
                    temp2.putExtra("mode", audioManager.getRingerMode());
                    PendingIntent pd = PendingIntent.getService(getApplicationContext(),2 , temp ,PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent pd1 = PendingIntent.getService(getApplicationContext(), 2, temp2, PendingIntent.FLAG_UPDATE_CURRENT);

                    cl.set(Calendar.HOUR  , Integer.parseInt(cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.time)).split(":")[0]));
                    cl.set(Calendar.MINUTE  , Integer.parseInt(cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.time)).split(":")[1].split(" ")[0]));
                    cl.set(Calendar.AM_PM  , cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.time)).split(":")[1].split(" ")[1].equals("AM")?Calendar.AM:Calendar.PM);
                    if (cr.getString(cr.getColumnIndex(daysToCheck[0])).equals("1")) {
                        cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[0]);
                        Log.d("Start Time Day 1", "" + cl.getTimeInMillis());
                        am.setExact(am.ELAPSED_REALTIME_WAKEUP , cl.getTimeInMillis(),pd);
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[1]);
                        Log.d("Start Time Day 2", "" + cl.getTimeInMillis());
                        am.setExact(am.ELAPSED_REALTIME_WAKEUP , cl.getTimeInMillis(),pd);
                    }
                    cl.set(Calendar.HOUR  , Integer.parseInt(cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.Etime)).split(":")[0]));
                    cl.set(Calendar.MINUTE  , Integer.parseInt(cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.Etime)).split(":")[1].split(" ")[0]));
                    System.out.println(cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.Etime)).split(":")[1].split(" ")[1]);
                    cl.set(Calendar.AM_PM  , cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.Etime)).split(":")[1].split(" ")[1].equals("AM")?Calendar.AM:Calendar.PM);
                    if (cr.getString(cr.getColumnIndex(daysToCheck[0])).equals("1")) {
                        cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[0]);
                        Log.d("Stop Time Day 1", "" + cl.getTimeInMillis());
                        am.setExact(am.ELAPSED_REALTIME_WAKEUP, cl.getTimeInMillis(), pd1);
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[1]);
                        Log.d("Stop Time Day 2", "" + cl.getTimeInMillis());
                        am.setExact(am.ELAPSED_REALTIME_WAKEUP , cl.getTimeInMillis(),pd1);
                    }
                }while(cr.moveToNext());
            }
        }
        catch(Exception e){
            Log.d(" Adding pending intent", e.getMessage());
        }
        finally {
            cdp.close();
        }
        //while (true){}
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

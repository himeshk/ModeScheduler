package com.example.igulhane73.appnew;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.igulhane73.appnew.dbOps.ConfigDatabaseOperations;
import com.example.igulhane73.appnew.info.ConfigTableData;
import com.example.igulhane73.appnew.utils.AddingPDS;

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
        // creating configDatabase
        ConfigDatabaseOperations cdp = new ConfigDatabaseOperations(getApplicationContext());
        Calendar cl = Calendar.getInstance();
        int currentDay = cl.get(Calendar.DAY_OF_WEEK);
        Log.d("OsrtComand EyDayService" ,"EveryDayService" );
        String daysToCheck[] = new String[2];
        int dayOfTheWeek[] =  new int[2];
        //checking current day
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
                    AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
                    int id = cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.id);
                    String time = cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.time)) ;
                    String mode = cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.mode));
                    String name = cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.name));
                    int hour = Integer.parseInt(time.split(":")[0]);
                    int minute = Integer.parseInt(time.split(":")[1].split(" ")[0]);
                    String AMPM = (time.split(":")[1].split(" ")[1]).trim();
                    cl.set(Calendar.HOUR  , hour);
                    cl.set(Calendar.MINUTE  , minute);
                    cl.set(Calendar.SECOND , 50);
                    cl.set(Calendar.AM_PM, AMPM.equals("AM") ? Calendar.AM : Calendar.PM);
                    cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[0]);
                    Log.d(" Lets see" , " " + (Calendar.getInstance().getTimeInMillis() -   cl.getTimeInMillis()));
                    if (cr.getString(cr.getColumnIndex(daysToCheck[0])).equals("1") && ((Calendar.getInstance()).getTimeInMillis() <= cl.getTimeInMillis())) {
                        AddingPDS.addPI(getApplicationContext(), 2, getString(R.string.startMode) , name, id, "-", mode, time, 1 ,dayOfTheWeek[0] );
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        AddingPDS.addPI(getApplicationContext(), 3, getString(R.string.startMode), name, id, "-", mode, time, 2 , dayOfTheWeek[1]);
                    }
                    time = cr.getString(cr.getColumnIndex(ConfigTableData.TimeConfigTableInfo.Etime)) ;
                    mode = "" + am.getRingerMode();
                    AMPM = (time.split(":")[1].split(" ")[1]).trim();
                    hour = Integer.parseInt(time.split(":")[0]);
                    minute = Integer.parseInt(time.split(":")[1].split(" ")[0]);
                    cl.set(Calendar.HOUR  , hour);
                    cl.set(Calendar.MINUTE  , minute);
                    cl.set(Calendar.SECOND , 50);
                    cl.set(Calendar.AM_PM, AMPM.equals("AM") ? Calendar.AM : Calendar.PM);
                    cl.set(Calendar.DAY_OF_WEEK , dayOfTheWeek[0]);
                    if (cr.getString(cr.getColumnIndex(daysToCheck[0])).equals("1") && ((Calendar.getInstance()).getTimeInMillis() <= cl.getTimeInMillis()) ) {
                        AddingPDS.addPI(getApplicationContext(), 4, getString(R.string.stopMode), name, id, "+", mode, time, 1, dayOfTheWeek[0]);
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        AddingPDS.addPI(getApplicationContext(), 5, getString(R.string.stopMode), name, id, "+", mode, time, 2,dayOfTheWeek[1]);
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

package com.example.igulhane73.appnew.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.util.Log;

import com.example.igulhane73.appnew.ChangeModeService;
import com.example.igulhane73.appnew.R;
import com.example.igulhane73.appnew.dbOps.ConfigDatabaseOperations;
import com.example.igulhane73.appnew.info.ConfigTableData;

import java.util.Calendar;

/**
 * Created by HimeshK on 8/21/2015.
 */
public class AddingPDS {
    public static void addPI(Context context ,int requestId , String type_of_alarm , String name , int id , String startSymbol , String mode , String time , int day , int day_of_the_week){
        Calendar cl = get_Calendar(time , day_of_the_week);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
/*        PendingIntent pd = PendingIntent.getService(context ,requestId, temp ,PendingIntent.FLAG_NO_CREATE);
        Log.d(" Current pd ", (pd == null) + "");
        Log.d(" info ", temp.getAction());
        Log.d(" Time Now and Later" , cl.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() + " ");
*/      PendingIntent pd = get_PendingIntent(context,name , requestId ,id , type_of_alarm ,startSymbol  , mode , day , PendingIntent.FLAG_UPDATE_CURRENT);
        am.setExact(am.RTC_WAKEUP, cl.getTimeInMillis(), pd);
    }
    public static Calendar get_Calendar(String time , int day_of_the_week){
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1].split(" ")[0]);
        String AMPM = (time.split(":")[1].split(" ")[1]).trim();
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.HOUR  , hour);
        cl.set(Calendar.MINUTE, minute);
        cl.set(Calendar.SECOND, 50);
        cl.set(Calendar.AM_PM, AMPM.equals("AM") ? Calendar.AM : Calendar.PM);
        cl.set(Calendar.DAY_OF_WEEK, day_of_the_week);
        Log.d("AM PM", "" + AMPM.equals("AM"));
        return cl;
    }
    public static PendingIntent get_PendingIntent(Context context , String name , int requestCode , int id , String startSymbol , String type_of_alarm , String mode , int day , int type_of_alarm1){
        Intent temp = new Intent(context, ChangeModeService.class);
        temp.putExtra(ConfigTableData.TimeConfigTableInfo.name, name);
        String setActionStart = startSymbol +  id;
        temp.setType(type_of_alarm + id);
        temp.putExtra(ConfigTableData.TimeConfigTableInfo.mode, mode);
        temp.setAction(day + "" + setActionStart);
        //Log.d("Start Time Day 1", "" + cl.getTimeInMillis());
        return (PendingIntent.getService(context , requestCode ,temp , type_of_alarm1));
    }
    public static void updateAlarmById(int id , Context context) {
        ConfigDatabaseOperations cdp = new ConfigDatabaseOperations(context);
        Calendar cl = Calendar.getInstance();
        int currentDay = cl.get(Calendar.DAY_OF_WEEK);
        Log.d("OsrtComand EyDayService", "EveryDayService");
        String daysToCheck[] = new String[2];
        int dayOfTheWeek[] = new int[2];
        //checking current day
        if (currentDay == Calendar.SUNDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SunDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.MonDay;
            dayOfTheWeek[0] = Calendar.SUNDAY;
            dayOfTheWeek[1] = Calendar.MONDAY;
        }
        if (currentDay == Calendar.MONDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.MonDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.TuesDay;
            dayOfTheWeek[0] = Calendar.MONDAY;
            dayOfTheWeek[1] = Calendar.TUESDAY;

        }
        if (currentDay == Calendar.TUESDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.TuesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.WednesDay;
            dayOfTheWeek[0] = Calendar.TUESDAY;
            dayOfTheWeek[1] = Calendar.WEDNESDAY;

        }
        if (currentDay == Calendar.WEDNESDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.WednesDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.ThursDay;
            dayOfTheWeek[0] = Calendar.WEDNESDAY;
            dayOfTheWeek[1] = Calendar.THURSDAY;

        }
        if (currentDay == Calendar.THURSDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.ThursDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.FriDay;
            dayOfTheWeek[0] = Calendar.THURSDAY;
            dayOfTheWeek[1] = Calendar.FRIDAY;

        }
        if (currentDay == Calendar.FRIDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.FriDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SaturDay;
            dayOfTheWeek[0] = Calendar.FRIDAY;
            dayOfTheWeek[1] = Calendar.SATURDAY;
        }
        if (currentDay == Calendar.SATURDAY) {
            daysToCheck[0] = ConfigTableData.TimeConfigTableInfo.SaturDay;
            daysToCheck[1] = ConfigTableData.TimeConfigTableInfo.SunDay;
            dayOfTheWeek[0] = Calendar.SATURDAY;
            dayOfTheWeek[1] = Calendar.SUNDAY;
        }
        try {
            Cursor cr = cdp.retrieveNewTimeConfig(cdp.getReadableDatabase(), "( " + daysToCheck[0] + " = '1' OR " +
                    daysToCheck[1] + " = '1') " +
                    " AND " + ConfigTableData.TimeConfigTableInfo.id + " = " + id, null);
            if (cr.moveToFirst()) {
                do {
                    // setting alarm for all alarms for the day and tomo ;
                    AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);


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
                    Log.d(" Lets see" , " " + Calendar.getInstance().getTimeInMillis() + " "  + cl.getTimeInMillis());
                    if (cr.getString(cr.getColumnIndex(daysToCheck[0])).equals("1") && ((Calendar.getInstance()).getTimeInMillis() <= cl.getTimeInMillis())) {
                        AddingPDS.addPI(context, 2, context.getString(R.string.startMode) , name, id, "-", mode, time, 1 ,dayOfTheWeek[0] );
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        AddingPDS.addPI(context, 3, context.getString(R.string.startMode), name, id, "-", mode, time, 2 , dayOfTheWeek[1]);
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
                        AddingPDS.addPI(context, 4, context.getString(R.string.stopMode), name, id, "+", mode, time, 1, dayOfTheWeek[0]);
                    }
                    if (cr.getString(cr.getColumnIndex(daysToCheck[1])).equals("1")) {
                        AddingPDS.addPI(context, 5, context.getString(R.string.stopMode), name, id, "+", mode, time, 2,dayOfTheWeek[1]);
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
    }
}

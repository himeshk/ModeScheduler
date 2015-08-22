package com.example.igulhane73.appnew.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.igulhane73.appnew.ChangeModeService;
import com.example.igulhane73.appnew.info.ConfigTableData;

import java.util.Calendar;

/**
 * Created by HimeshK on 8/21/2015.
 */
public class AddingPDS {
    public static void addPI(Context context ,int requestId , String type_of_alarm , String name , int id , String startSymbol , String mode , String time , int day){
        Intent temp = new Intent(context, ChangeModeService.class);
        temp.putExtra(ConfigTableData.TimeConfigTableInfo.name, name);
        String setActionStart = startSymbol +  id;
        temp.setType(type_of_alarm + id);
        temp.putExtra("mode", mode);
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1].split(" ")[0]);
        String AMPM = (time.split(":")[1].split(" ")[1]).trim();
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.HOUR  , hour);
        cl.set(Calendar.MINUTE  , minute);
        cl.set(Calendar.SECOND , 50);
        cl.set(Calendar.AM_PM, AMPM.equals("AM") ? Calendar.AM : Calendar.PM);
        Log.d("AM PM", "" + AMPM.equals("AM"));
        temp.setAction(day + "" + setActionStart);
        Log.d("Start Time Day 1", "" + cl.getTimeInMillis());
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pd = PendingIntent.getService(context ,requestId, temp ,PendingIntent.FLAG_NO_CREATE);
        Log.d(" Current pd ", (pd == null) + "");
        Log.d(" info ", temp.getAction());
        Log.d(" Time Now and Later" , cl.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() + " ");
        pd = PendingIntent.getService(context ,requestId, temp ,PendingIntent.FLAG_UPDATE_CURRENT);
        am.setExact(am.RTC_WAKEUP, cl.getTimeInMillis(), pd);
    }


}

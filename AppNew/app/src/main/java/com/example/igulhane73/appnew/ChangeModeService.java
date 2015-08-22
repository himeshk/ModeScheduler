package com.example.igulhane73.appnew;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by HimeshK on 8/19/2015.
 */
public class ChangeModeService extends Service {
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        AudioManager audioManager = (AudioManager)getSystemService(getApplication().AUDIO_SERVICE);
        String id = intent.getAction();
        String mode = intent.getStringExtra("mode");
        Log.d(" In change mode " , mode);
        if (mode.equals("All")){
            Log.d(" mode " ,   mode);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if (mode.equals("Priority")){
            Log.d(" mode ", mode);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if (mode.equals("None")){
            Log.d(" mode ", mode);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else {
            Log.d(" in ringer ", "" + audioManager.getRingerMode());
            audioManager.setRingerMode(Integer.parseInt(mode));
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}

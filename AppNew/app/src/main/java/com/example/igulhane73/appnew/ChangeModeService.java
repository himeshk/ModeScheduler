package com.example.igulhane73.appnew;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

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

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}

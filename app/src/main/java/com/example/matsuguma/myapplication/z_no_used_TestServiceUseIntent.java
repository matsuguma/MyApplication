package com.example.matsuguma.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


/**
 * Created by matsuguma on 2015/04/28.
 */
public class z_no_used_TestServiceUseIntent extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 開始時にコール
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "インテントを利用してサービス起動", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 終了時にコール
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "インテントを利用してサービス終了", Toast.LENGTH_LONG).show();
    }

}

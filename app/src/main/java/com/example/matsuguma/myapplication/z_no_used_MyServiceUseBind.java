package com.example.matsuguma.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.os.RemoteException;

/**
 * Created by matsuguma on 2015/04/28.
 */
public class z_no_used_MyServiceUseBind extends Service {
    // サービス開始時にコール
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "バインドを利用してサービス起動", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "バインドを利用してサービス終了", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

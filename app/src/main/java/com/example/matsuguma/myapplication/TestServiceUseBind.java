package com.example.matsuguma.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.util.Log;
import android.widget.Toast;
import android.os.RemoteException;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by matsuguma on 2015/04/24.
 */
public class TestServiceUseBind extends Service {

    private final RemoteCallbackList<IMyServiceUseBindCallback> mCallbacks = new RemoteCallbackList<IMyServiceUseBindCallback>();

    // タイマー間隔(ms)
    private int TIMER_INTERVAL = 10000;

    // インタフェースの実装
    private IMyServiceUseBind.Stub mService = new IMyServiceUseBind.Stub() {

        @Override
        public void registerCallback(IMyServiceUseBindCallback callback) throws RemoteException {
            mCallbacks.register(callback);
        }
        @Override
        public void unregisterCallback(IMyServiceUseBindCallback callback) throws RemoteException {
            mCallbacks.unregister(callback);
        }
        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }
    };

    // timer
    private Timer timer = new Timer();

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 通知処理
            // sendNotification();

            // Callbackリストの処理を開始
            int n = mCallbacks.beginBroadcast();

            for (int i = 0; i < n; i++) {
                try {
                    // Callback Interfaceをたたく
                    mCallbacks.getBroadcastItem(i).result();
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
                // 終わり
                mCallbacks.finishBroadcast();
            }
        }
    };

    @Override
    public void onCreate() {
        Toast.makeText(this, "サービス-onCreate()", Toast.LENGTH_SHORT).show();
        super.onCreate();

        // Callback Interfaceをたたくためのタイマータスクを実行
        timer.schedule(task, 0, TIMER_INTERVAL);

    }

    // サービス開始時にコール
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "サービス-onBind()", Toast.LENGTH_SHORT).show();
        return mService;
    }

    // サービス終了時にコール
    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "サービス-onUnbind()", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "サービス-onDestroy()", Toast.LENGTH_SHORT).show();
        // タイマーを破棄する
        timer.cancel();

        super.onDestroy();
    }

    /**
     * ステータスバーに通知する
     */
    /*
    private void sendNotification() {

        // Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        // i.setData(Uri.parse("http://www.google.com/"));
        // PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, i, 0);

        Intent intent = new Intent(getApplicationContext(), LookLifeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, intent, 0);



        Notification notification;

        if (Build.VERSION.SDK_INT < 16) {
            notification = new Notification.Builder(this)
                    .setContentTitle("Title!")
                    .setContentText("Content Text!")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.default_image)
                    .setAutoCancel(true)
                    .setTicker("サービスからの応答")
                    .setVibrate(new long[] {0, 100, 300, 1000})
                    .getNotification();

        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle("Title!")
                    .setContentText("Content Text!")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.default_image)
                    .setAutoCancel(true)
                    .setTicker("サービスからの応答")
                    .setVibrate(new long[] {0, 100, 300, 1000})
                    .build();
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1000, notification);
    }
*/

}

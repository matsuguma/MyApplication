package com.example.matsuguma.myapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by matsuguma on 2015/05/12.
 */
public class DownloadService extends IntentService{

    // public static final int UPDATE_PROGRESS = 8344;

    /**
     * コンストラクタ
     */
    public DownloadService() {
        super("DownloadService");
    }

    /**
     * 非同期処理を行うメソッド。
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra("url");
        // ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream("/sdcard/matsuguma/test.JPEG");

            byte data[] = new byte[1024];// TODO
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
                // receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();

            // ステータスバーに通知する
            sendNotification();

            input.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);
        // receiver.send(UPDATE_PROGRESS, resultData);
    }


    /**
     * ステータスバーにサービスの状態を通知する
     */
    private void sendNotification() {
        Intent intent = new Intent(getApplicationContext(), LookLifeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, intent, 0);

        Notification notification = new Notification.Builder(this)
                .setTicker("Ticker-ダウンロード完了しました。")
                .setContentTitle("Title-ダウンロード")
                .setContentText("Content Text-イメージ画像のダウンロードが完了しました。")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.default_image)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(1234, notification);
    }
}

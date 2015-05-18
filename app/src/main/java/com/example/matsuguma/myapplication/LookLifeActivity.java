package com.example.matsuguma.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.String;
import android.os.Handler;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.LinearLayout;

/**
 * Created by matsuguma on 2015/04/22.
 */
public class LookLifeActivity extends Activity implements View.OnClickListener, z_no_used_LookLifeFragment.OnFragmentInteractionListener {

    // アクティビティ名
    private final String ACTIVITY_NAME = "LookLifeActivity";

    // サービス起動時のインテント
    private Intent intentUseBind = null;
    // private Intent intentUseIntent = null;

    // サービス
    private IMyServiceUseBind mService = null;

    // プロフィール表示フラグメント
    ProfileFragment profileFragment = new ProfileFragment();

    // 画面要素
    TextView manFragmentCount;
    TextView ladyFragmentCount;
    TextView totalFragmentCount;

    // プロセス間通信のためのコネクション
    private ServiceConnection connect = new ServiceConnection() {

        // コネクション開始時
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // サービス起動
            mService = IMyServiceUseBind.Stub.asInterface(service);

            try {
                // コールバックを登録
                mService.registerCallback(mCallback);
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        }

        // サービスが異常終了した時
        @Override
        public void onServiceDisconnected(ComponentName name) {
            unbindService(connect);
            mService = null;
        }

    };

    // コールバック
    private IMyServiceUseBindCallback mCallback = new IMyServiceUseBindCallback.Stub() {
        @Override
        public void result() throws RemoteException {
            Log.v("LookLife", "Callback：サービスからコールバックされました。");
            // フラグメントの総数をカウントする
            sumFragment();

            // sendNotification();

        }
    };

    private SQLiteDatabase mydb;

    ProgressDialog mProgressDialog;

    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_look_life);
        outputActivityStatusInfo("onCreate");

        // buttonにリスナーをセット
        this.findViewById(R.id.man_fragment_button).setOnClickListener(this);
        this.findViewById(R.id.lady_fragment_button).setOnClickListener(this);
        this.findViewById(R.id.service_start_button).setOnClickListener(this);
        this.findViewById(R.id.service_end_button).setOnClickListener(this);
        this.findViewById(R.id.sum_button).setOnClickListener(this);
        this.findViewById(R.id.delete_button).setOnClickListener(this);

        this.findViewById(R.id.download_service_start_1).setOnClickListener(this);
        this.findViewById(R.id.download_service_start_2).setOnClickListener(this);
        // this.findViewById(R.id.download_service_end).setOnClickListener(this);

        this.findViewById(R.id.display_data_button).setOnClickListener(this);
        this.findViewById(R.id.search_data_button).setOnClickListener(this);
        this.findViewById(R.id.delete_data_button).setOnClickListener(this);
        // this.findViewById(R.id.drop_db_button).setOnClickListener(this);


        // 初期状態ではサービス終了ボタンは非活性とする
        this.findViewById(R.id.service_end_button).setEnabled(false);
        this.findViewById(R.id.sum_button).setEnabled(false);

        // フラグメントカウントの表示ビュー要素をセットする
        manFragmentCount = (TextView) this.findViewById(R.id.man_fragment_Text);
        ladyFragmentCount = (TextView) this.findViewById(R.id.lady_fragment_Text);
        totalFragmentCount = (TextView)this.findViewById(R.id.total_fragment_Text);

        // サービスクラスを指定
        // intentUseIntent = new Intent(IMyServiceUseIntent.class.getName());
        // intentUseIntent = new Intent(this, TestServiceUseIntent.class);

        // DBを作成する
        createDB();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("タイトル");
        mProgressDialog.setMessage("メッセージ");
    }

    @Override
    protected void onStart(){
        super.onStart();

        outputActivityStatusInfo("onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();

        outputActivityStatusInfo("onResume");

        // Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/matsuguma/test.JPEG");

        try {
            // File file = new File("/sdcard/matsuguma/test.JPEG");
            // File file = new File("/storage/matsuguma/test.JPEG");
            // InputStream inputStream = null;
            Bitmap rowBitmap = null;
            try {
                // inputStream = new FileInputStream(file);
                // decoder->decode returned falseエラー対策
                // Bitmap rowBitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                rowBitmap = BitmapFactory.decodeFile("/sdcard/matsuguma/test.JPEG");
                bitmap = Bitmap.createScaledBitmap(rowBitmap, rowBitmap.getWidth()/10, rowBitmap.getHeight()/10, true);
            } catch (Exception ex) {
                //
            } finally {
                /*
                if (null != inputStream) {
                    inputStream.close();
                }
                */
                if (null != rowBitmap) {
                    rowBitmap.recycle();
                    rowBitmap = null;
                }
            }
        } catch (Exception e) {

        }

        if (null != bitmap) {
            // scaledImg= Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/10, bitmap.getHeight()/10, true);
            ((ImageView)this.findViewById(R.id.download_image_view)).setImageBitmap(bitmap);
        } else {
            ((ImageView)this.findViewById(R.id.download_image_view)).setImageDrawable(getResources().getDrawable(R.drawable.default_image));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        outputActivityStatusInfo("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        outputActivityStatusInfo("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        outputActivityStatusInfo("onDestroy");

        if (null != mService) {
            unbindService(connect);
        }

        // TODO
        // mydb.execSQL("drop table personal_table");
        mydb.close();


        if(bitmap!=null)
        {
            bitmap.recycle();
            bitmap=null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        outputActivityStatusInfo("onRestart");
    }

    public void onClick(View view) {
        int clickedViewId = view.getId();
        if (clickedViewId == R.id.man_fragment_button) {
            outputOperationLog("man_fragment_buttonを押下しました。");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ManProfileFragment manProfileFragment = new ManProfileFragment();
            manProfileFragment.setEntryNo(Integer.toString(fragmentManager.getBackStackEntryCount() + 1));

            fragmentTransaction.add(R.id.profile_fragment, manProfileFragment, Integer.toString(fragmentManager.getBackStackEntryCount() + 1));

            // fragmentTransaction.replace(R.id.profile_fragment, manProfileFragment, "man_profileFragment");
            // バックスタックにトランザクション情報を登録する
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            fragmentTransaction.commit();
        } else if (clickedViewId == R.id.lady_fragment_button) {
            outputOperationLog("lady_fragment_buttonを押下しました。");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LadyProfileFragment ladyProfileFragment = new LadyProfileFragment();
            ladyProfileFragment.setEntryNo(Integer.toString(fragmentManager.getBackStackEntryCount() + 1));


            fragmentTransaction.add(R.id.profile_fragment, ladyProfileFragment, Integer.toString(fragmentManager.getBackStackEntryCount() + 1));
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            fragmentTransaction.commit();
            // サービス開始ボタン押下
        } else if (clickedViewId == R.id.service_start_button) {
            intentUseBind = new Intent(IMyServiceUseBind.class.getName());
            // サービスとの接続を開始する
            // BIND_AUTO_CREATEを指定することでサービスが停止しているときは起動する
            bindService(intentUseBind, connect, BIND_AUTO_CREATE);

            this.findViewById(R.id.service_start_button).setEnabled(false);
            this.findViewById(R.id.service_end_button).setEnabled(true);
            this.findViewById(R.id.sum_button).setEnabled(true);
            // サービス終了ボタン押下
        } else if (clickedViewId == R.id.service_end_button) {
            // サービスとの接続を切断する
            unbindService(connect);
            mService = null;

            this.findViewById(R.id.service_start_button).setEnabled(true);
            this.findViewById(R.id.service_end_button).setEnabled(false);
            this.findViewById(R.id.sum_button).setEnabled(false);
            // 合計ボタン押下
        } else if (clickedViewId == R.id.sum_button) {
            try {
                int res = mService.add(Integer.parseInt(((TextView) this.findViewById(R.id.man_fragment_Text)).getText().toString()),
                        Integer.parseInt(((TextView) this.findViewById(R.id.lady_fragment_Text)).getText().toString()));
                ((TextView) this.findViewById(R.id.total_fragment_Text)).setText(Integer.toString(res));
            } catch (RemoteException re) {
                re.printStackTrace();
            }
            // 削除ボタン押下
        } else if (clickedViewId == R.id.delete_button) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // fragmentTransaction.remove(fragmentManager.findFragmentByTag(Integer.toString(fragmentManager.getBackStackEntryCount())));// うまく動作しない。

            // 1つ前のフラグメントを取り出す
            fragmentManager.popBackStack();

            fragmentTransaction.commit();
        } else if (clickedViewId == R.id.download_service_start_1) {

            // mProgressDialog.show();
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra("url", "http://fujifilm.jp/personal/digitalcamera/x/fujifilm_x30/sample_images/img/index/ff_x30_004.JPG");

            // intent.putExtra("receiver", new DownloadReceiver(new Handler()));
            startService(intent);
        } else if (clickedViewId == R.id.download_service_start_2) {

            // mProgressDialog.show();
            Intent intent = new Intent(this, DownloadService.class);
            // NG intent.putExtra("url", "http://fujifilm.jp/personal/digitalcamera/x/fujinon_lens_xf10_24mmf4_r_ois/sample_images/img/index/ff_xf10_24mmf4_r_ois_021.JPG");
            intent.putExtra("url", "http://fujifilm.jp/personal/digitalcamera/x/fujifilm_x30/sample_images/img/index/ff_x30_001.JPG");
            // intent.putExtra("url", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQk0mf5HTn3DQG-9jBNWXuYalMtlfHM2ypRfazGwg4d1Uvyo0p1sd264_vLuQ");

            // intent.putExtra("receiver", new DownloadReceiver(new Handler()));
            startService(intent);
            /*
        } else if (clickedViewId == R.id.download_service_end) {
            Toast.makeText(this, "処理不要なので未実装", Toast.LENGTH_SHORT).show();
            */
            // 登録ボタン押下
        } else if (clickedViewId == R.id.display_data_button) {
            displayDB();
        } else if (clickedViewId == R.id.search_data_button) {
            searchData();
        } else if (clickedViewId == R.id.delete_data_button) {
            deleteData();
        }
        /*
        else if (clickedViewId == R.id.drop_db_button) {

            Toast.makeText(this, "未実装", Toast.LENGTH_SHORT).show();
        }
        */
    }

    /**
     * Activityの状態をトースト表示及びログ出力する
     * @param status Activityの状態
     */
    private void outputActivityStatusInfo(String status) {
        Toast.makeText(this, ACTIVITY_NAME + "-" +status, Toast.LENGTH_SHORT).show();
        // System.out.println(status);
        Log.v("LookLife", "LookLifeActivity-LifeCycle：" + status);
    }

    /**
     * 操作ログを出力する
     * @param operation 操作名
     */
    private void outputOperationLog(String operation) {
        Log.v("LookLife", "Fragment-LifeCycle-OPE：" + operation);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO Auto-generated method stub
    }

    /**
     * manとladyのフラグメント数を足しこむ
     */
    private void sumFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*
                int res = Integer.parseInt(manFragmentCount.getText().toString()) +
                        Integer.parseInt(ladyFragmentCount.getText().toString());
                totalFragmentCount.setText(Integer.toString(res));
                */
                try {
                    int res = mService.add(Integer.parseInt(manFragmentCount.getText().toString()),
                            Integer.parseInt(ladyFragmentCount.getText().toString()));
                    totalFragmentCount.setText(Integer.toString(res));
                } catch (RemoteException re) {
                    re.printStackTrace();
                }

            }
        });
    }

    /**
     * DB作成または接続
     */
    private void createDB() {
        // DBを作成する
        PersonalInfoDBHelper personalInfoDBHelper = new PersonalInfoDBHelper(this);
        // DB操作用のインスタンスを取得
        mydb = personalInfoDBHelper.getReadableDatabase();
    }
    /**
     * DBの登録内容を表示する
     */
    private void displayDB() {
        Cursor c = mydb.query("personal_table", new String[]{"name", "telNumber"}, null, null, null, null, null);
        boolean isEof = c.moveToFirst();

        ((LinearLayout) this.findViewById(R.id.regist_personal_info_text)).removeAllViews();
        while (isEof) {
            TextView tv = new TextView(this);
            tv.setText(String.format("お名前：%s、電話番号：%s", c.getString(0), c.getString(1)));
            isEof = c.moveToNext();


            ((LinearLayout) this.findViewById(R.id.regist_personal_info_text)).addView(tv);
        }
        c.close();
    }

    private void deleteData() {
        String name = ((TextView) this.findViewById(R.id.target_name)).getText().toString();
        // mydb.delete("personal_table", "name = '" + name + "'", null);
        mydb.delete("personal_table", "name=?", new String[]{name});
    }

    private void searchData() {
        String name = ((TextView) this.findViewById(R.id.target_name)).getText().toString();

        // Cursor c = mydb.query("personal_table", new String[]{"name", "telNumber"}, "name = '" + name + "'", null, null, null, null);

        Cursor c = mydb.query("personal_table", new String[]{"name", "telNumber"}, "name=?", new String[]{name}, null, null, null);

        ((LinearLayout) this.findViewById(R.id.regist_personal_info_text)).removeAllViews();
        // Cursorを先頭に移動する 検索結果が0件の場合にはfalseが返る
        if (c.moveToFirst()) {
            TextView tv = new TextView(this);
            tv.setText(String.format("お名前：%s、電話番号：%s", c.getString(0), c.getString(1)));
            ((LinearLayout) this.findViewById(R.id.regist_personal_info_text)).addView(tv);
        } else {
            TextView tv = new TextView(this);
            tv.setText("該当データがありません");
            ((LinearLayout) this.findViewById(R.id.regist_personal_info_text)).addView(tv);
        }
    }

    /**
     * DBにデータを登録する
     * フラグメントからのコールバックメソッド
     * @param name
     * @param telNumber
     */
    public void registPersonalInfo(String name, String telNumber) {
        // データの追加
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("telNumber", telNumber);
        mydb.insert("personal_table", null, values);
    }

    /**
     * レシーバクラス
     */
    /*
    public class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                mProgressDialog.setProgress(progress);
                if (progress == 100) {
                    mProgressDialog.dismiss();
                }
            }
        }
    }
    */

}

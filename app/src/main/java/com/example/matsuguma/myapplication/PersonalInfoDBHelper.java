package com.example.matsuguma.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matsuguma on 2015/05/08.
 */
public class PersonalInfoDBHelper extends SQLiteOpenHelper {

    // DB名
    private static final String DB_NAME = "personal.db";

    // DBのバージョン
    private static final int DB_VERSION = 1;

    // テーブル作成SQL
    private static final String TABLE_CREATE =
            "create table personal_table ( "
            + "_id integer primary key autoincrement not null, "
            + "name text not null, "
            + "telNumber text not null);";

    /**
     * コンストラクタ
     * DBがない場合は自動生成される
     * @param context
     */
    public PersonalInfoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * このDBを初めて使用するときに実行される処理
     * DBが作成されていない場合に呼び出される
     * テーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

        // 必要に応じて、他テーブルの作成や初期データの挿入をここで行う。
        // 初期データの挿入
        db.execSQL("insert into personal_table(name,telNumber) values ('テスト太郎', '88');");
        db.execSQL("insert into personal_table(name,telNumber) values ('テスト花子', '55');");
    }

    /**
     * アプリケーションの更新などによって、DBのバージョンが上がった場合に実行される処理
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // NOP
    }


}

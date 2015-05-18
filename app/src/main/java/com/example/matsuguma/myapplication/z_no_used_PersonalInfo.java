package com.example.matsuguma.myapplication;

/**
 * Created by matsuguma on 2015/05/07.
 * 人物データクラス
 */
public class z_no_used_PersonalInfo {
    private String name;
    private String telNumber;

    /**
     * コンストラクタ
     * @param name       氏名
     * @param telNumber 電話番号
     */
    /*
    public PersonalInfo(String name, String telNumber) {
        this.name = name;
        this.telNumber = telNumber;
    }
    */

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }
}

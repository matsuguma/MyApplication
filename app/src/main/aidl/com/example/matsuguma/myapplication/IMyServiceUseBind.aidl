// IMySeviceUseBind.aidl
package com.example.matsuguma.myapplication;

import com.example.matsuguma.myapplication.IMyServiceUseBindCallback;

// Declare any non-default types here with import statements

interface IMyServiceUseBind {
    oneway void registerCallback(IMyServiceUseBindCallback callback);
    oneway void unregisterCallback(IMyServiceUseBindCallback callback);

    // 加算メソッド
    int add(int x, int y);
}

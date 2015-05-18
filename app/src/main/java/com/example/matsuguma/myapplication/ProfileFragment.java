package com.example.matsuguma.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    public String entryNo;

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public ProfileFragment() {
        // Required empty public constructor
        outputStatusLog("コンストラクタ実行");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        outputStatusLog("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputStatusLog("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        outputStatusLog("onCreateView");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((TextView)view.findViewById(R.id.entry_no_text)).setText(getEntryNo());

        ((Button)view.findViewById(R.id.regist_button)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        outputStatusLog("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        outputStatusLog("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        outputStatusLog("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        outputStatusLog("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        outputStatusLog("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        outputStatusLog("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        outputStatusLog("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        outputStatusLog("onDetach");
    }

    /**
     * 状態をログを出力する
     * @param status 状態
     */
    private void outputStatusLog(String status) {
        Log.v("LookLife", "Fragment-LifeCycle-Profile：" + status);
    }

    protected void drawStackOrder(String gender, int color) {
        TextView textView = new TextView(this.getActivity());
        textView.setBackgroundColor(color);
        textView.setText(gender);
        textView.setTextColor(Color.WHITE);
        ((LinearLayout) this.getActivity().findViewById(R.id.gender_fragment_stack)).addView(textView);
    }

    @Override
    public void onClick(View v) {
        try {
            LookLifeActivity activity = (LookLifeActivity) getActivity();
            activity.registPersonalInfo(((EditText)this.getView().findViewById(R.id.name_edit_text)).getText().toString(), ((EditText)this.getView().findViewById(R.id.tel_edit_text)).getText().toString());
        } catch (ClassCastException e) {
            throw new ClassCastException("activity が OnOkBtnClickListener を実装していません.");
        }
    }
}

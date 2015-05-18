package com.example.matsuguma.myapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;

/**
 * Created by matsuguma on 2015/04/23.
 */
public class ManProfileFragment extends ProfileFragment {

    private final String MAN_PROFILE_FRAGMENT_NAME = "ManProfileFragment";
    Activity activity = new Activity();

    public ManProfileFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        this.activity = activity;
        super.onAttach(this.activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int fragmentCounter = Integer.parseInt(((TextView) this.activity.findViewById(R.id.man_fragment_Text)).getText().toString());

        ((TextView)this.activity.findViewById(R.id.man_fragment_Text)).setText(Integer.toString(fragmentCounter + 1));

        // スタック順番の設定
        super.drawStackOrder("MAN",Color.rgb(100, 149, 237));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.setBackgroundColor(Color.rgb(100, 149, 237));
        view.findViewById(R.id.profile_main_layout).setVisibility(View.VISIBLE);

        return view;
    }

    /**
     * フラグメントが削除される際は
     * ・カウンターを1減らす。
     * ・スタック順序の画像を1つ減らす。
     */
    @Override
        public void onDestroyView() {
            super.onDestroyView();
        int fragmentCounter = Integer.parseInt(((TextView) activity.findViewById(R.id.man_fragment_Text)).getText().toString());

        ((TextView)activity.findViewById(R.id.man_fragment_Text)).setText(Integer.toString(fragmentCounter - 1));

        ((LinearLayout) this.activity.findViewById(R.id.gender_fragment_stack)).removeViewAt(((LinearLayout) this.activity.findViewById(R.id.gender_fragment_stack)).getChildCount()-1);
    }
}

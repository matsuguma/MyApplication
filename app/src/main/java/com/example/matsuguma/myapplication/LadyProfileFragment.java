package com.example.matsuguma.myapplication;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by matsuguma on 2015/04/23.
 */
public class LadyProfileFragment extends ProfileFragment {

    private final String LADY_PROFILE_FRAGMENT_NAME = "LadyProfileFragment";
    Activity activity = new Activity();

    public LadyProfileFragment() {
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

        if (null != ((TextView)this.activity.findViewById(R.id.lady_fragment_Text))) {
            int fragmentCounter = Integer.parseInt(((TextView) this.activity.findViewById(R.id.lady_fragment_Text)).getText().toString());
            ((TextView)this.activity.findViewById(R.id.lady_fragment_Text)).setText(Integer.toString(fragmentCounter + 1));

            // スタック順番の設定
            super.drawStackOrder("LADY",Color.rgb(132, 112, 255));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.setBackgroundColor(Color.rgb(132, 112, 255));
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
        int fragmentCounter = Integer.parseInt(((TextView) activity.findViewById(R.id.lady_fragment_Text)).getText().toString());

        ((TextView)activity.findViewById(R.id.lady_fragment_Text)).setText(Integer.toString(fragmentCounter - 1));

        ((LinearLayout) this.activity.findViewById(R.id.gender_fragment_stack)).removeViewAt(((LinearLayout) this.activity.findViewById(R.id.gender_fragment_stack)).getChildCount()-1);

    }
}

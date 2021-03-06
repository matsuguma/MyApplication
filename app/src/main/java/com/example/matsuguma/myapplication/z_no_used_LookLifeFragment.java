package com.example.matsuguma.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link z_no_used_LookLifeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link z_no_used_LookLifeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class z_no_used_LookLifeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LookLifeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static z_no_used_LookLifeFragment newInstance(String param1, String param2) {
        z_no_used_LookLifeFragment fragment = new z_no_used_LookLifeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public z_no_used_LookLifeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.z_no_used_fragment_look_life, container, false);

        view.findViewById(R.id.man_fragment_button).setOnClickListener(this);
        view.findViewById(R.id.lady_fragment_button).setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        int clickedViewId = view.getId();
        if (clickedViewId == R.id.man_fragment_button) {
            outputOperationLog("man_fragment_buttonを押下しました。");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ManProfileFragment manProfileFragment = new ManProfileFragment();
            fragmentTransaction.add(R.id.profile_fragment, manProfileFragment, "man_profileFragment");// この場合は前回に突っ込んでいたフラグメントをいったんonDestroyView()にまでしてバックスタックにつむ。
            // fragmentTransaction.replace(R.id.profile_fragment, manProfileFragment, "man_profileFragment");// この場合は前回につんでいたフラグメントをonPause()しないでバックスアックにつむ
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else if (clickedViewId == R.id.lady_fragment_button) {
            outputOperationLog("lady_fragment_buttonを押下しました。");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LadyProfileFragment ladyProfileFragment = new LadyProfileFragment();
            fragmentTransaction.add(R.id.profile_fragment, ladyProfileFragment, "lady_profileFragment");
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else if(clickedViewId == R.id.sweet_fragment_button) {
            outputOperationLog("sweet_fragment_buttonを押下しました。");

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LadyProfileFragment ladyProfileFragment = new LadyProfileFragment();
            fragmentTransaction.add(R.id.profile_fragment, ladyProfileFragment, "lady_profileFragment");
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }

    }

    /**
     * 操作ログを出力する
     * @param operation 操作名
     */
    private void outputOperationLog(String operation) {
        Log.v("LookLife", "Fragment-LifeCycle-OPE：" + operation);
    }

}

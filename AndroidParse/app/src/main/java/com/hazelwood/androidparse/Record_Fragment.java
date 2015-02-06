package com.hazelwood.androidparse;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hazelwood on 2/5/15.
 */
public class Record_Fragment extends Fragment {
    private static final String ARG_ONE = "argument_two_section";

    public static Record_Fragment newInstance(String text) {
        Record_Fragment fragment = new Record_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_ONE, text);
        fragment.setArguments(args);
        return fragment;
    }

    public Record_Fragment() {
    }

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.record_form, container, false);
        return rootView;
    }
}

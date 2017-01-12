package com.example.qsd.edictionary.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qsd.edictionary.R;

/**
 * 记忆法页面的说明
 */
public class DetialsFragment extends Fragment {

    public DetialsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detials, container, false);
    }

}

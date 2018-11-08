package com.uaeh.garza.garzamapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uaeh.garza.garzamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GarzaMappFragment extends Fragment {


    public GarzaMappFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garza_mapp, container, false);
    }

}

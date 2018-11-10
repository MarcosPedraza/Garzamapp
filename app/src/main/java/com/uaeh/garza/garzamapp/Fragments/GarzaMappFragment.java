package com.uaeh.garza.garzamapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uaeh.garza.garzamapp.MapsActivity;
import com.uaeh.garza.garzamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GarzaMappFragment extends Fragment {


    public GarzaMappFragment() {
        // Required empty public constructor
    }

    //widgets

    Button btn_mapa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_garza_mapp, container, false);

        btn_mapa = view.findViewById(R.id.btn_nav);


        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

}

package com.uaeh.garza.garzamapp.Dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.uaeh.garza.garzamapp.R;

public class DialogAcercaDe  extends DialogFragment {

    private Button btn_ok;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_acerca_de,container,false);

        btn_ok = view.findViewById(R.id.btn_dialog_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}

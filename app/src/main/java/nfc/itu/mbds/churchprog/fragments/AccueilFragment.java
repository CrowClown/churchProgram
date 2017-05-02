package nfc.itu.mbds.churchprog.fragments;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.activity.MainActivity;
import nfc.itu.mbds.churchprog.outils.Utile;

/**
 * Created by T7 on 17/03/2017.
 */
public class AccueilFragment extends Fragment {

    private TextView textV;

    public AccueilFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        textV = (TextView) view.findViewById(R.id.approchez);
        //Log.e("io"," tafidite");

        return view;
    }

    public TextView getTextV() {
        return textV;
    }

    public void setTextV(TextView textV) {
        this.textV = textV;
    }
}

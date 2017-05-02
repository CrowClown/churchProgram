package nfc.itu.mbds.churchprog.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.fragments.DetailsArchiveFragment;
import nfc.itu.mbds.churchprog.modele.Programme;

public class DisplayArchivesActivity extends AppCompatActivity {

    private Programme programme;
    private DetailsArchiveFragment detailsArchiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_archives);

        Intent intent = getIntent();
        programme = (Programme) intent.getSerializableExtra("Prog");
        //Log.e("PROG : ",programme.getProg_desc());
        if (savedInstanceState == null) {
            // Insert detail fragment based on the item passed
            detailsArchiveFragment = DetailsArchiveFragment.newInstance(programme);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.display_archives_activity, detailsArchiveFragment);
            ft.commit();
        }
    }
}

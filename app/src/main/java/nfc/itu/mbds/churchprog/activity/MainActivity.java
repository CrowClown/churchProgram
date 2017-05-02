package nfc.itu.mbds.churchprog.activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.fragments.AccueilFragment;
import nfc.itu.mbds.churchprog.fragments.ArchivesFragment;
import nfc.itu.mbds.churchprog.fragments.ContenuFragment;
import nfc.itu.mbds.churchprog.modele.Programme;
import nfc.itu.mbds.churchprog.outils.Utile;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    // Progress dialog
    private ProgressDialog pDialog;

    private AccueilFragment accueilFragment;
    private ContenuFragment contenuFragment;
    private ArchivesFragment archivesFragment;

    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Chargement en cours...");
        pDialog.setCancelable(false);


        /** NFC **/
        //final TextView txtTag = (TextView) findViewById(R.id.approchez);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC not available on this device", Toast.LENGTH_LONG).show();
            //accueilFragment.getTextV().setText("NFC incompatible avec le mobile");
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Veuillez activer l’option NFC", Toast.LENGTH_LONG).show();
            //accueilFragment.getTextV().setText("Veuillez activer le NFC");
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        accueilFragment = new AccueilFragment();
        contenuFragment = new ContenuFragment();
        archivesFragment = new ArchivesFragment();
        adapter.addFragment(accueilFragment, "ACCUEIL");
        adapter.addFragment(contenuFragment, "PROGRAMME");
        adapter.addFragment(archivesFragment, "ARCHIVES");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    /**
     * Activer la détection du NFC
     */
    @Override
    public void onResume() {
        super.onResume();
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    /**
     * Désactiver la détection du NFC
     */
    @Override
    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * Récupération de l’intent du TAG NFC
     *
     * @param intent
     */
    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //Méthode qui va traiter le tag NFC
            processNfcIntent(intent);
        }
    }

    public void processNfcIntent(Intent intent) {

        try {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] id = tag.getId();
            String[] technologies = tag.getTechList();
            int content = tag.describeContents();
            Ndef ndef = Ndef.get(tag);
            boolean isWritable = ndef.isWritable();
            boolean canMakeReadOnly = ndef.canMakeReadOnly();
            //Récupération des messages
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            //Boucle sur les enregistrements
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    NdefRecord record = msgs[i].getRecords()[i];
                    byte[] idRec = record.getId();
                    short tnf = record.getTnf();
                    byte[] type = record.getType();
                    String message = record.getPayload().toString();
                    //Utiliser ?
                    //Laisser Android choisir l’appli par défaut si type URI ?
                    if (Arrays.equals(type, NdefRecord.RTD_URI)) {
                        Uri uri = record.toUri();
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(uri);
                        startActivity(intent1);
                    }
                }
            } else {
                //Tag de type inconnu, tester une récupération du contenu hexadécimal ?
                byte[] empty = new byte[]{};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
                        empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
            }

            Parcelable[] ndefMessageArray = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage ndefMessage = (NdefMessage) ndefMessageArray[0];
            String msg = new String(ndefMessage.getRecords()[0].getPayload());
            traiterMsg(msg.substring(3).trim());
        }catch (Exception ex){
            Toast.makeText(this, "ZXCEPTION " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void traiterMsg(String url) {

        showpDialog();
        //Log.e("URL: ", " >>> " + url);
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("onResponse: ", " >>> " + response);

                        //Traiter les informations...
                        List<Programme> progList = Utile.JsonToListObj(response);
                        if (progList.size() > 0) {
                            Programme leProgramme = progList.get(0);
                            //contenuFragment.setProg(leProgramme);
                            /*contenuFragment.getListAdapter().setContenuList(leProgramme.getContenu());
                            contenuFragment.getSelv().setAdapter(contenuFragment.getListAdapter());
                            contenuFragment.setContenu_l(leProgramme.getContenu());*/
                            contenuFragment.chargement(leProgramme);
                            archivesFragment.chargement();
                            TabLayout.Tab tab = tabLayout.getTabAt(1);
                            tab.select();
                        }else{
                            Toast.makeText(getApplicationContext(), "Aucun programme disponible", Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("onErrorResponse: ", " >>> " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Mauvaise connexion Internet", Toast.LENGTH_LONG).show();
                        hidepDialog();
                    }
                });
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
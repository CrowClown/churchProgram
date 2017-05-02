package nfc.itu.mbds.churchprog.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.activity.DisplayArchivesActivity;
import nfc.itu.mbds.churchprog.activity.MainActivity;
import nfc.itu.mbds.churchprog.adapter.ListArchivesAdapter;
import nfc.itu.mbds.churchprog.helper.DatabaseHelper;
import nfc.itu.mbds.churchprog.modele.Programme;
import nfc.itu.mbds.churchprog.outils.Utile;

/**
 * Created by T7 on 27/03/2017.
 */

public class ArchivesFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListArchivesAdapter adapter;
    private List<Programme> donnee;

    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseHelper dh;

    public ArchivesFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chargement();
    }

    public void chargement(){
        if(donnee==null || donnee.size()==0) {
            //donnee = Utile.loadData(getActivity());
            dh = new DatabaseHelper(getActivity());
            donnee = dh.getAll();
            //Log.e(">>>>>>>>>> ",String.valueOf(donnee.size()));
            if (donnee!=null) {
                adapter = new ListArchivesAdapter(getActivity(), donnee);
                setListAdapter(adapter);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_archives, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DisplayArchivesActivity.class);
        //Log.e("CLICK >>>>>>>>>>> ",adapter.getItem(position).getProg_desc());
        intent.putExtra("Prog",adapter.getItem(position));
        startActivity(intent);
        // listener.onItemSelected(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        try {
            swipeRefreshLayout.setRefreshing(true);
            donnee = dh.getAll();
            adapter = new ListArchivesAdapter(getActivity(), donnee);
            setListAdapter(adapter);
        }catch(Exception e){

        }finally {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public List<Programme> getDonnee() {
        return donnee;
    }

    public void setDonnee(List<Programme> donnee) {
        this.donnee = donnee;
    }
}

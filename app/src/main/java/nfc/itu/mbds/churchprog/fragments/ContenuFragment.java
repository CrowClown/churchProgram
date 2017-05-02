package nfc.itu.mbds.churchprog.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.adapter.ExpandableListAdapter;
import nfc.itu.mbds.churchprog.helper.DatabaseHelper;
import nfc.itu.mbds.churchprog.modele.Contenu;
import nfc.itu.mbds.churchprog.modele.Programme;

/**
 * Created by T7 on 17/03/2017.
 */

public class ContenuFragment extends Fragment implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, View.OnClickListener {
    private Programme prog;
    private List<Contenu> contenu_l;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView selv;
    private FloatingActionButton fab;
    private DatabaseHelper dh;
    private boolean exist = false;

    private static final String TAG = ContenuFragment.class.getSimpleName();

    public ContenuFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dh = new DatabaseHelper(getActivity());
        //List<Programme> progList = Utile.loadData(getActivity());
        //chargement();
    }

    public void chargement(Programme programme){

        setProg(programme);

        if(prog !=null) {
            contenu_l = prog.getContenu();

            dh.open();
            Programme temp = dh.getProgrammeById(prog.getProg_id());
            //Log.i("TEMP ",temp.toString());
            if(temp!=null)
                fab.setVisibility(View.INVISIBLE);
            else
                fab.setVisibility(View.VISIBLE);

            dh.closeDB();

            listAdapter = new ExpandableListAdapter(getActivity(), contenu_l);
            selv.setAdapter(listAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view =  inflater.inflate(R.layout.fragment_prog, container, false);

        selv = (ExpandableListView) view.findViewById(R.id.simpleExpandableListView);
        //expandAll();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        //if(exist)
        //    fab.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(this);
        selv.setOnGroupClickListener(this);
        selv.setOnChildClickListener(this);

        return view;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
        Contenu headerInfo = contenu_l.get(groupPos);
        String detailInfo = (String) expandableListView.getExpandableListAdapter().getChild(groupPos, childPos);
        /*display it or do something with it
        Toast.makeText(getContext(), " Clicked on :: " + headerInfo.getC_titre()
                + "/" + detailInfo, Toast.LENGTH_LONG).show();
                */

        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPos, long l) {
        //Contenu headerInfo = contenu_l.get(groupPos);
        /*display it or do something with it
        Toast.makeText(getContext(), " Header is :: " + headerInfo.getC_titre(),
                Toast.LENGTH_LONG).show();
        */

        return false;
    }

    @Override
    public void onClick(View v) {

        try{
            dh.open();
            dh.createProgramme(prog);
            for(int i=0;i < contenu_l.size();i++)
                dh.createContenu(contenu_l.get(i));
            fab.setVisibility(View.INVISIBLE);
            dh.closeDB();
            Toast.makeText(getContext(),"Programme archivé",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Log.i(TAG," Un problème durant l'opération");
        }
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            selv.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            selv.collapseGroup(i);
        }
    }

    public Programme getProg() {
        return prog;
    }

    public void setProg(Programme prog) {
        this.prog = prog;
    }

    public ExpandableListAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(ExpandableListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }

    public ExpandableListView getSelv() {
        return selv;
    }

    public void setSelv(ExpandableListView selv) {
        this.selv = selv;
    }

    public List<Contenu> getContenu_l() {
        return contenu_l;
    }

    public void setContenu_l(List<Contenu> contenu_l) {
        this.contenu_l = contenu_l;
    }
}
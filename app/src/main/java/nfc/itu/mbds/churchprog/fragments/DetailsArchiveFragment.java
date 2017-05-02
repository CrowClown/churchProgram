package nfc.itu.mbds.churchprog.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.activity.MainActivity;
import nfc.itu.mbds.churchprog.adapter.ExpandableListAdapter;
import nfc.itu.mbds.churchprog.modele.Contenu;
import nfc.itu.mbds.churchprog.modele.Programme;
import nfc.itu.mbds.churchprog.outils.Utile;

/**
 * Created by T7 on 17/03/2017.
 */

public class DetailsArchiveFragment extends Fragment implements ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener{
    private Programme prog_l;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView selv;

    public DetailsArchiveFragment() {
        super();
    }

    public static DetailsArchiveFragment newInstance(Programme programme) {
        DetailsArchiveFragment fragmentDemo = new DetailsArchiveFragment();
        Bundle args = new Bundle();
        args.putSerializable("Prog", programme);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prog_l = (Programme) getArguments().getSerializable("Prog");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view =  inflater.inflate(R.layout.fragment_prog, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        selv = (ExpandableListView) view.findViewById(R.id.simpleExpandableListView);
        listAdapter = new ExpandableListAdapter(getActivity(), prog_l.getContenu());
        selv.setAdapter(listAdapter);
        //expandAll();

        selv.setOnGroupClickListener(this);
        selv.setOnChildClickListener(this);

        return view;
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
        /*
        Contenu headerInfo = contenu_l.get(groupPos);
        String detailInfo = (String) expandableListView.getExpandableListAdapter().getChild(groupPos, childPos);
        //Toast.makeText(getContext(), " Clicked on :: " + headerInfo.getC_titre() + "/" + detailInfo, Toast.LENGTH_LONG).show();
        */
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPos, long l) {
        //Contenu headerInfo = contenu_l.get(groupPos);
        //Toast.makeText(getContext(), " Header is :: " + headerInfo.getC_titre(),Toast.LENGTH_LONG).show();

        return false;
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
}
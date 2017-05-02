package nfc.itu.mbds.churchprog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.modele.Contenu;

/**
 * Created by T7 on 22/03/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Contenu> contenuList;

    public ExpandableListAdapter(Context context, List<Contenu> contenuList) {
        this.context = context;
        if(contenuList==null)
            this.contenuList = new ArrayList<>();
        else
            this.contenuList = contenuList;
    }

    @Override
    public int getGroupCount() {
        return contenuList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        Contenu contenu = contenuList.get(i);
        String[] childs = contenu.getC_contenu().split("/");
        return childs.length;
    }

    @Override
    public Object getGroup(int i) {
        return contenuList.get(i);
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        Contenu contenu = contenuList.get(groupPos);
        String[] childs = contenu.getC_contenu().split("/");
        return childs[childPos];
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPos, boolean isLastChild, View view, ViewGroup parent) {

        Contenu group = contenuList.get(groupPos);
        if(view==null) {
            LayoutInflater inf = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_items, null);
            //view = LayoutInflater.from(context).inflate(R.layout.group_items, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(group.getC_titre());

        return view;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View view, ViewGroup parent) {

        String detailInfo = (String) getChild(groupPos, childPos);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_items, null);
            //view = LayoutInflater.from(context).inflate(R.layout.child_items, null);
        }

        TextView sequence = (TextView) view.findViewById(R.id.items);
        sequence.setText(detailInfo);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public List<Contenu> getContenuList() {
        return contenuList;
    }

    public void setContenuList(List<Contenu> contenuList) {
        this.contenuList = contenuList;
    }
}

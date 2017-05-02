package nfc.itu.mbds.churchprog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import nfc.itu.mbds.churchprog.R;
import nfc.itu.mbds.churchprog.modele.Programme;

/**
 * Created by T7 on 27/03/2017.
 */

public class ListArchivesAdapter extends ArrayAdapter<Programme> {

    private final List<Programme> values;

    private static class ViewHolder {
        TextView label;
        ImageView image;
    }

    public ListArchivesAdapter(Context context, List<Programme> values) {
        super(context, R.layout.items_archives, values);
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Programme getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Programme p = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_archives, parent, false);
        }
        TextView t = (TextView) convertView.findViewById(R.id.label);
        ImageView img = (ImageView) convertView.findViewById(R.id.icon);
        t.setText(p.getProg_desc());

        return convertView;
        /*
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = LayoutInflater.from(context).inflate(R.layout.items_archives, parent);
            convertView = inflater.inflate(R.layout.items_archives, parent);

            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Programme p = getItem(position);
        viewHolder.label.setText(p.getProg_desc());

        return convertView;
        */
    }

}
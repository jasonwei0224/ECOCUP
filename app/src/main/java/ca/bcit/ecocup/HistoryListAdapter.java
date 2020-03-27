package ca.bcit.ecocup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import ca.bcit.ecocup.History;
import ca.bcit.ecocup.R;

public class HistoryListAdapter extends ArrayAdapter<History> {
    private Activity context;
    private List<History> historyList;

    public HistoryListAdapter(Activity context, List<History> historyList){
        super(context,R.layout.list_layout ,historyList);
        this.context = context;
        this.historyList = historyList;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        @SuppressLint({"ViewHolder", "InflateParams"})
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView type = listViewItem.findViewById(R.id.type);
        TextView points = listViewItem.findViewById(R.id.points);
        TextView time = listViewItem.findViewById(R.id.time);

        History history = historyList.get(position);

        time.setText(history.getDate().toString());
        points.setText(String.format(Locale.ENGLISH, "%d", history.getPoints()));
        type.setText((history.getType()));

        return listViewItem;
    }

}

package ca.bcit.ecocup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryListAdapter extends ArrayAdapter<History> {
    private Context context;
    private List<History> historyList;

    public HistoryListAdapter(Context context, List<History> historyList){
        super(context,R.layout.list_layout ,historyList);

        this.context = context;
        this.historyList = historyList;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint({"ViewHolder", "InflateParams"})
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView type = listViewItem.findViewById(R.id.type);
        TextView pointsRedeem = listViewItem.findViewById(R.id.pointsRedeem);
        TextView time = listViewItem.findViewById(R.id.time);
        History history = historyList.get(position);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setText(formatter.format(history.getDate()));
        pointsRedeem.setText(String.format(Locale.ENGLISH, "%d", history.getPointsRedeem()));
        type.setText((history.getType()));

        return listViewItem;
    }

}

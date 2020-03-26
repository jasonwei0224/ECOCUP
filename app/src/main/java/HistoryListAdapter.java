import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

import ca.bcit.ecocup.History;
import ca.bcit.ecocup.R;

public class HistoryListAdapter extends ArrayAdapter<History> {
    private Activity context;
    private List<History> historyList;

    public HistoryListAdapter(Activity context, List<History> histories){
        super(context,R.layout.list_layout ,histories);
        this.context = context;
        this.historyList = histories;
    }
}

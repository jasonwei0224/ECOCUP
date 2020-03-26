package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Rewards extends Fragment {

    private View view;
    ArrayList<Exhibition> exhibitions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_rewards, container, false);

        //this is to receive arraylist of vendors from MainActivity.
        Bundle bundle= getArguments();
        exhibitions=bundle.getParcelableArrayList("exhibitions");
        System.out.println(exhibitions);



//        String[] menuItems={"do something", "do something else", "Do another thing"};

        ListView lv_rewards_listview=(ListView)view.findViewById(R.id.lv_rewards_listview);

//        ArrayAdapter<String>  listViewAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menuItems);
//        lv_rewards_listview.setAdapter(listViewAdapter);

        ExhibitionList exAdapter=new ExhibitionList();
        lv_rewards_listview.setAdapter(exAdapter);
//        exAdapter.addExhibition(ContextCompat.getDrawable(this, R.drawable.pic1), "a", "b");
        exAdapter.addExhibition("aegeawgawegaewgawegae", "betqwteewqgasdgasdgasdgwqaetqwetagasdgsdbetgawesgasgd");


        return view;
    }

    public class ExhibitionList extends BaseAdapter {

        //how many data
        @Override
        public int getCount() {
            return exhibitions.size()-1;
        }

        //sending data
        @Override
        public Object getItem(int i) {
            return exhibitions.get(i);
        }

        //where is the data
        @Override
        public long getItemId(int i) {
            return i;
        }

        //view
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Context c=viewGroup.getContext();
            if(view==null) {
                LayoutInflater li=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=li.inflate(R.layout.j21, viewGroup, false);
            }
            //need to check
            ImageView iv = view.findViewById(R.id.test1lv);
            TextView tv = view.findViewById(R.id.test2);
            TextView tv2 = view.findViewById(R.id.test3);

            Exhibition exhibition=exhibitions.get(i);

            int[] images=new int[6];
            images[0]=R.drawable.pic1;
            images[1]=R.drawable.pic2;
            images[2]=R.drawable.pic3;
            images[3]=R.drawable.pic4;
            images[4]=R.drawable.pic5;
            images[5]=R.drawable.pic6;

            iv.setImageResource(images[i]);
            tv.setText(exhibition.getTitle());
            System.out.println(exhibition.getTitle());
            tv2.setText(exhibition.getDate());

            return view;
        }
        public void addExhibition(String title, String date) {
            Exhibition ex=new Exhibition();
            ex.setTitle(title);
            ex.setDate(date);

            exhibitions.add(ex);
        }
    }
}

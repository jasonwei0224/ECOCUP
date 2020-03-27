package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rewards extends Fragment {

    private View view;
    ArrayList<Exhibition> exhibitions;
    Dialog epicDialog;
    TextView tv_popup_title;
    ImageView iv_popup_pic;
    TextView tv_popup_desc;
    TextView tv_popup_point;
    ImageView popup_close_popup;
    Button confirmBtn;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    long points;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_rewards, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //this is to receive arraylist of vendors from MainActivity.
        Bundle bundle= getArguments();
        exhibitions=bundle.getParcelableArrayList("exhibitions");
        System.out.println(exhibitions);


        ListView lv_rewards_listview=(ListView)view.findViewById(R.id.lv_rewards_listview);



        ExhibitionList exAdapter=new ExhibitionList();
        lv_rewards_listview.setAdapter(exAdapter);

//        exAdapter.addExhibition("aegeawgawegaewgawegae", "betqwteewqgasdgasdgasdgwqaetqwetagasdgsdbetgawesgasgd");


        epicDialog=new Dialog(getContext());

        lv_rewards_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                view=LayoutInflater.from(getActivity()).inflate(R.layout.custom_popup, null);
                tv_popup_title=view.findViewById(R.id.tv_popup_title);
                tv_popup_title.setText(exhibitions.get(i).getTitle());
                confirmBtn = view.findViewById(R.id.confirm);
                int[] images=new int[6];
                images[0]=R.drawable.pic1;
                images[1]=R.drawable.pic2;
                images[2]=R.drawable.pic3;
                images[3]=R.drawable.pic4;
                images[4]=R.drawable.pic5;
                images[5]=R.drawable.pic6;


                iv_popup_pic=view.findViewById(R.id.iv_popup_pic);
                try{
                    iv_popup_pic.setImageResource(images[i]);
                }catch(Exception e) {
                    System.out.println("Exception needed");
                }

                tv_popup_desc=view.findViewById(R.id.tv_popup_desc);
                tv_popup_desc.setText(exhibitions.get(i).getDescription());

                tv_popup_point=view.findViewById(R.id.tv_popup_point);
                tv_popup_point.setText(Integer.toString(exhibitions.get(i).getPoint())+"POINT");
//                System.out.println(exhibitions.get(i).getPoint());

                popup_close_popup=view.findViewById(R.id.popup_close_popup);
                popup_close_popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        epicDialog.dismiss();
                    }
                });

                if (points < exhibitions.get(i).getPoint()){
                    confirmBtn.setClickable(false);
                }
                ValueEventListener pointsListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u = (User) dataSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);

                        assert u != null;
                        points = u.getPoints();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        points = points - exhibitions.get(i).getPoint();
                        Map<String, Object> update = new HashMap<>();
                        update.put("/points", points);
                        String id = mDatabase.push().getKey();
                        History h = new History();
                        h.setType("Redeem");
                        h.setPoints(points);
                        h.setDate(System.currentTimeMillis());
                        update.put("/historys/"+id, h);

                        mDatabase.child("users").child(mAuth.getUid()).updateChildren(update);
                    }
                });

                epicDialog.setContentView(view);
                epicDialog.show();

            }
        });


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
            ImageView iv = view.findViewById(R.id.iv_rewards_pic);
            TextView tv = view.findViewById(R.id.tv_rewards_title);
            TextView tv2 = view.findViewById(R.id.tv_rewards_date);

            Exhibition exhibition=exhibitions.get(i);

            int[] images=new int[6];
            images[0]=R.drawable.pic1;
            images[1]=R.drawable.pic2;
            images[2]=R.drawable.pic3;
            images[3]=R.drawable.pic4;
            images[4]=R.drawable.pic5;
            images[5]=R.drawable.pic6;


            try{
                    iv.setImageResource(images[i]);
            }catch(Exception e) {
                System.out.println("Exception needed");
            }

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

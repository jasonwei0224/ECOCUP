package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.media.tv.TvContract;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;

public class Points extends Fragment {

    private View view;

    private FirebaseAuth mAuth;
    private TextView userPoints;
    long points;
    ImageView imageView5;
    DatabaseReference mDatabase;
    List<History> historyList;
    ImageView btn_main_logout;
    Button btn_points_qr;
    private ListView listView;
    Context mContext;

    TextView tv_popup_title;
    ImageView popup_close_popup;
    Button confirmBtn;
    Dialog epicDialog;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_points, container, false);
        mAuth = FirebaseAuth.getInstance();
        userPoints = view.findViewById(R.id.userPoints);
        listView = view.findViewById(R.id.history);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        historyList = new ArrayList<>();

        btn_main_logout=view.findViewById(R.id.btn_main_logout);
        btn_main_logout.setOnClickListener(onClickListener);

        btn_points_qr=view.findViewById(R.id.btn_points_qr);
        btn_points_qr.setOnClickListener(onClickListener);

        imageView5=view.findViewById(R.id.iv_points_history);

        epicDialog=new Dialog(getContext());

        return view;

    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_main_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent i=new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    break;
                case R.id.btn_points_qr:
                    Intent j=new Intent(getActivity(), QRcode.class);

//                    System.out.println("What is in btn_points_qr"+points);

                    j.putExtra("points", points);
                    startActivity(j);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ValueEventListener pointsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyList.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.child("users").child(mAuth.getUid()).getChildren()) {
                    if(userSnapshot.hasChildren()) {
                        History h = userSnapshot.getValue(History.class);
                        historyList.add(h);
                    }

                }

                points = (long) dataSnapshot.child("users").child(mAuth.getUid()).child("points").getValue();
                userPoints.setText(Long.toString(points));

                if(historyList.size()==0) {
                    showNoHistory(getView());

                }else {
                    hideNoHistory(getView());
                    HistoryListAdapter adapter = new HistoryListAdapter(mContext, historyList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            view=LayoutInflater.from(getActivity()).inflate(R.layout.custom_popup_point, null);



                            tv_popup_title=view.findViewById(R.id.tv_popup_title);
                            tv_popup_title.setText(historyList.get(i).getType());
                            confirmBtn = view.findViewById(R.id.confirm);

                            confirmBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    epicDialog.dismiss();
                                }
                            });

                            epicDialog.setContentView(view);
                            epicDialog.show();

                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(pointsListener);
    }

    public void showNoHistory(View view) {
        imageView5.setVisibility(View.VISIBLE);
    }
    public void hideNoHistory(View view) {
        imageView5.setVisibility(View.GONE);
    }
}

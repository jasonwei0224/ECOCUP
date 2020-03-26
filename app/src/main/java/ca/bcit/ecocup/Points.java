package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.tv.TvContract;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    ProgressBar progressBar;
    ValueEventListener mPointsListener;
    DatabaseReference mDatabase;
    List<History> historyList;

    Button btn_main_logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_points, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);
        userPoints = view.findViewById(R.id.userPoints);
        progressBar.setProgress(50);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        historyList = new ArrayList<>();
        ValueEventListener pointsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyList.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.child("users'").child(mAuth.getUid()).child("historys").getChildren()) {
                    User user = userSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);
                    if(user!=null){
                        historyList.add(user.getHistorys());
                    }
                }
                User u = (User) dataSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);
//                History h = dataSnapshot.child("users").child(mAuth.getUid()).child("historys").getValue(History.class);
                assert u != null;
                userPoints.setText(u.getPoints().toString());

//
//                System.out.println("success");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(pointsListener);
        btn_main_logout=view.findViewById(R.id.btn_main_logout);
        btn_main_logout.setOnClickListener(onClickListener);
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
            }
        }
    };

}

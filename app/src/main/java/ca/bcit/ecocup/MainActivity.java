package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Points points;
    private Rewards rewards;
    private Maps maps;
    private ArrayList<Vendor> vendors =new ArrayList<>();
    private ArrayList<Exhibition> exhibitions =new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Long valueOfCurrentPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readVendorData();
        readMuseumData();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        System.out.println("There is mAuth"+mAuth.getCurrentUser());
        if(mAuth.getCurrentUser()==null) {
            myStartActivity(LoginActivity.class);
        }

        bottomNavigationView=findViewById(R.id.bn_general);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_user:
                        setFrag(0);
                        break;
                    case R.id.action_quest:
                        setFrag(1);
                        break;
                    case R.id.action_map:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        points=new Points();
        rewards=new Rewards();
        maps=new Maps();

        //this is to send data from mainactivity to other fragment.
        Bundle bundle;
        bundle=new Bundle();
        bundle.putParcelableArrayList("arraylist", vendors);
        bundle.putParcelableArrayList("exhibitions", exhibitions);
        maps.setArguments(bundle);
        rewards.setArguments(bundle);
        if(mAuth.getCurrentUser()!= null){
            setFrag(0);
        }

        Intent i =getIntent();
        try{
            if(i.getExtras().getString("answer")!=null) {
                String answer=i.getExtras().getString("answer");
                valueOfCurrentPoint=i.getExtras().getLong("points");

                databaseWrite(answer, valueOfCurrentPoint);
            }
        }catch(NullPointerException e) {

//            System.out.println("In main working null?");

        }
    }

    private void databaseWrite(String answer, Long valueOfCurrentPoint) {
        if(answer.equals("startbucks")) {
            valueOfCurrentPoint+=5;

        }
        mDatabase.child("users").child(mAuth.getUid()).child("points").setValue(valueOfCurrentPoint);

    }

    private void setFrag(int n){
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.fl_main, points);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.fl_main, rewards);
                ft.commit();
                break;
            case 2:

                ft.replace(R.id.fl_main, maps);
                ft.commit();
                break;
        }
    }


    private void myStartActivity(Class c) {
        Intent intent=new Intent(this, c);
        // this is removing previous activity.. need to study.. this part..
        // this is needed when we press back button, it turns off.. and log in maintains.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    //this method is to read data from raw data2.csv. (from tutorial)
    private void readVendorData() {
        InputStream is=getResources().openRawResource(R.raw.vendorlist);
        BufferedReader reader=new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            //stop over headers
            reader.readLine();
            while((line=reader.readLine())!=null) {
                //split by ","
                String[] token=line.split(",");

                //read the data
                Vendor sample=new Vendor();
                sample.setName(token[27]);
                if(token[1].length()>0) {
                    sample.setY(Double.parseDouble(token[1]));
                }else {
                    sample.setY(0);
                }

                if(token[2].length()>0) {
                    sample.setX(Double.parseDouble(token[2]));
                }else {
                    sample.setX(0);
                }
                sample.setServiceProvided(token[28]);
                vendors.add(sample);
            }
        }catch(IOException e) {

            e.printStackTrace();
        }

    }


    //this method is to read data from raw data2.csv. (from tutorial)
    private void readMuseumData() {
        InputStream is=getResources().openRawResource(R.raw.exhibitionlist);
        BufferedReader reader=new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            //stop over headers
            reader.readLine();
            while((line=reader.readLine())!=null) {
                //split by ","
                String[] token=line.split(",");

                //read the data
                Exhibition sample=new Exhibition();
                sample.setNo(Integer.parseInt(token[0]));
                sample.setTitle(token[1]);
                sample.setDate(token[2]);
                sample.setDescription(token[3]);
                sample.setPoint(Integer.parseInt(token[4]));

                exhibitions.add(sample);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

}

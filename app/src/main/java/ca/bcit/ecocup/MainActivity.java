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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_main_logout;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Points points;
    private Rewards rewards;
    private Maps maps;
    private ArrayList<Vendor> vendors =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readVendorData();

        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            myStartActivity(LoginActivity.class);
        }
        btn_main_logout=findViewById(R.id.btn_main_logout);
        btn_main_logout.setOnClickListener(onClickListener);

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

        setFrag(0);

        //this is to send data from mainactivity to other fragment.
        Bundle bundle;
        bundle=new Bundle();
        bundle.putParcelableArrayList("arraylist", vendors);
        maps.setArguments(bundle);




    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_main_logout:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(LoginActivity.class);
                    break;
            }
        }
    };


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
        InputStream is=getResources().openRawResource(R.raw.data2);
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

                vendors.add(sample);
                System.out.println(sample);
            }
        }catch(IOException e) {

            e.printStackTrace();
        }

    }

}

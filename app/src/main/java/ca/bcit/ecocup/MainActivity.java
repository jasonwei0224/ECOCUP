package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btn_main_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            startSignupActivity();
        }
        btn_main_logout=findViewById(R.id.btn_main_logout);
        btn_main_logout.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_main_logout:
                    FirebaseAuth.getInstance().signOut();
                    startSignupActivity();
                    break;
            }
        }
    };

    private void startSignupActivity() {
        Intent intent=new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}

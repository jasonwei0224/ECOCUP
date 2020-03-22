package ca.bcit.ecocup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG="SignupActivity";
    private FirebaseAuth mAuth;


    Button btn_signup_signup;
    Button btn_signup_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_signup_signup=findViewById(R.id.btn_signup_signup);
        btn_signup_signup.setOnClickListener(onClickListener);

        btn_signup_login=findViewById(R.id.btn_signup_login);
        btn_signup_login.setOnClickListener(onClickListener);

        mAuth=FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_signup_signup:
                    signup();
                    System.out.println("Signup pressed");
                    break;
                case R.id.btn_signup_login:
                    myStartActivity(LoginActivity.class);
            }
        }
    };

    private void signup() {
        String email=((EditText)findViewById(R.id.et_signup_email)).getText().toString();
        String password=((EditText)findViewById(R.id.et_signup_password)).getText().toString();
        String passwordCheck=((EditText)findViewById(R.id.et_signup_passwordCheck)).getText().toString();

        if(email.length()>0&& password.length()>0 && passwordCheck.length()>0) {
            if(password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startToast("Success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    myStartActivity(MainActivity.class);


                                } else {
                                    if(task.getException()!=null) {
                                        startToast(task.getException().toString());
                                    }

                                }

                            }
                        });
            }else {
                Toast.makeText(this, "Password is not correct", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Pleaes input all", Toast.LENGTH_SHORT).show();
        }
    }

    private void startToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c) {
        Intent intent=new Intent(this, c);
        // this is removing previous activity.. need to study.. this part..
        // this is needed when we press back button, it turns off.. and log in maintains.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG="SignupActivity";
    private FirebaseAuth mAuth;
    Button btn_login_login;
    Button btn_login_passwordReset;
    Button btn_login_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login_login=findViewById(R.id.btn_login_login);
        btn_login_login.setOnClickListener(onClickListner);
        btn_login_passwordReset=findViewById(R.id.btn_login_passwordReset);
        btn_login_passwordReset.setOnClickListener(onClickListner);
        btn_login_signup=findViewById(R.id.btn_login_signup);
        btn_login_signup.setOnClickListener(onClickListner);

        mAuth=FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListner=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_login_login:
//                    Log.e('cd', 'clickww');
                    login();
                    break;
                case R.id.btn_login_passwordReset:
//                    Log.e('cd', 'clickww');
//                    myStartActivity(PasswordResetActivity.class);
                    System.out.println("Should make passwordResetActivity");
                    break;
                case R.id.btn_login_signup:
//                    Log.e('cd', 'clickww');
                    myStartActivity(SignUpActivity.class);
                    break;
            }
        }
    };

    private void login() {
        String email=((EditText)findViewById(R.id.et_login_email)).getText().toString();
        String password=((EditText)findViewById(R.id.et_login_password)).getText().toString();

        if(email.length()>0&& password.length()>0) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            System.out.println("I came to inside");
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("Authentication success");
                                myStartActivity(MainActivity.class);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                startToast("Authentication failed");

                            }

                        }
                    });

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

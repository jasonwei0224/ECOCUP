package ca.bcit.ecocup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    private TextView txtResult;
    private String answer;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Long valueOfCurrentPoint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        scannerView=(ZXingScannerView) findViewById(R.id.sv_qr_qr);
        txtResult=(TextView) findViewById(R.id.tv_qr_bottom);

        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(QRcode.this);
                        scannerView.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(QRcode.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }

                })
                .check();


    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        System.out.println("Called");



        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        txtResult.setText(rawResult.getText());

        processRawResult(rawResult.getText());
        databaseRead();
        Intent i =new Intent(QRcode.this, MainActivity.class);
        System.out.println("Please"+valueOfCurrentPoint);
        i.putExtra("answer", answer);
        i.putExtra("point", valueOfCurrentPoint);

        startActivity(i);
        scannerView.startCamera();


    }

    private void processRawResult(String text) {
        txtResult.setText(text);
        answer=text;
        System.out.println(answer);
    }
    private void databaseRead() {
        System.out.println("database REad");
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                valueOfCurrentPoint = (long) dataSnapshot.child("users").child(mAuth.getUid()).child("points").getValue();
                System.out.println("value of data"+valueOfCurrentPoint);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


}

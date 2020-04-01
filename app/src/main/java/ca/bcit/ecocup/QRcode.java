package ca.bcit.ecocup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private Long currentPoint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Intent i=getIntent();
        currentPoint=i.getExtras().getLong("points");

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
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        txtResult.setText(rawResult.getText());

        processRawResult(rawResult.getText());
        Intent i =new Intent(QRcode.this, MainActivity.class);
        i.putExtra("points", currentPoint);
        i.putExtra("answer", answer);
        startActivity(i);
        scannerView.startCamera();


    }

    private void processRawResult(String text) {
        txtResult.setText(text);
        answer=text;
    }



}

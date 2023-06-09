package com.example.phoneverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOtp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        final EditText inputMobile = findViewById(R.id.mobileNumberId);
        Button getOTP = findViewById(R.id.getOTPId);

        final ProgressBar progressBar = findViewById(R.id.progressOTPId);

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(inputMobile.getText().toString().trim().isEmpty()){
                        Toast.makeText(SendOtp.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    getOTP.setVisibility(View.INVISIBLE);


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+88"+inputMobile.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SendOtp.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(),VerifyOtp.class);
                                    intent.putExtra("mobile",inputMobile.getText().toString());
                                    intent.putExtra("verificationId",s);
                                    startActivity(intent);
                                }
                            }
                    );

            }
        });
    }
}
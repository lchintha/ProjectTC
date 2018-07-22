package com.tcltd.corp.tradecrop;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class LaunchActivity extends AppCompatActivity {

    CardView mobileNumberScreen, pinNumberScreen;
    Button toPinnumberScreen;
    RelativeLayout mainLayout;
    TextView toMobileNumberScreen;
    EditText mobileNumber, pinNumber;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth mAuth;

    AlertDialog dialog;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        setUpWindowAnimations();

        //Phone Number Authentication Callbacks
        mAuth = FirebaseAuth.getInstance();
        mobileNumber = findViewById(R.id.mobileNumberField);

        //Show loading dialog
        dialog = new SpotsDialog(this);

        //Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Animating to pinNumber Screen
        mainLayout = findViewById(R.id.mainLayout);
        mobileNumberScreen = findViewById(R.id.mobileNumber);
        pinNumberScreen = findViewById(R.id.pinNumber);
        toPinnumberScreen = findViewById(R.id.toPinNumberScreen);
        toPinnumberScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumberScreen.animate().translationX(-mainLayout.getWidth());
                mobileNumberScreen.setVisibility(View.GONE);
                pinNumberScreen.animate().translationX(0);
                String mobileNo = mobileNumber.getText().toString().trim();
                if (TextUtils.isEmpty(mobileNo)) {
                    mobileNumber.setError("Cannot be empty!");
                    return;
                }
                if(mobileNo.length() == 10) {
                    dialog.setMessage("Sending code...");
                    dialog.show();
                    startPhoneNumberVerification(mobileNo);
                }else{
                    Toast.makeText(LaunchActivity.this, "Please Check Your Number", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //Animating back to mobileNumber Screen
        toMobileNumberScreen = findViewById(R.id.toMobileNumberScreen);
        toMobileNumberScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumberScreen.animate().translationX(0);
                mobileNumberScreen.setVisibility(View.VISIBLE);
                pinNumberScreen.animate().translationX(mainLayout.getWidth());
            }
        });
        mainLayout.setBackground(getResources().getDrawable(R.drawable.launch_activity_background));
    }

    private void setUpWindowAnimations() {
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            slide.setDuration(500);
            getWindow().setExitTransition(slide);
        }
    }

    private void startPhoneNumberVerification(String phoneNumber){
        setUpVerificationCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);
    }

    private void setUpVerificationCallbacks(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d("tag", "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                dialog.dismiss();
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Snackbar.make(mobileNumber, "Invalid Phone Number", Snackbar.LENGTH_LONG).show();
                }else if (e instanceof FirebaseTooManyRequestsException){
                    Snackbar.make(mobileNumber, "Please try after some time", Snackbar.LENGTH_LONG).show();
                }else
                    Snackbar.make(mobileNumber, "Verfication Failed", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                dialog.dismiss();
                /*mobileNumberScreen.animate().translationX(-mainLayout.getWidth());
                mobileNumberScreen.setVisibility(View.GONE);
                pinNumberScreen.animate().translationX(0);*/
                Snackbar.make(mobileNumber, "Code has been Sent to your mobile", Snackbar.LENGTH_LONG).show();
                mVerificationId = s;
            }
        };
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LaunchActivity.this, "Success", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LaunchActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void verifyPhoneNumber(View view) {
        /*pinNumber = findViewById(R.id.pinNumberField);
        String code = pinNumber.getText().toString();
        if (TextUtils.isEmpty(code)) {
            pinNumber.setError("Cannot be empty!");
            return;
        }
        verifyPhoneNumberWithCode(mVerificationId, code);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        else
            startActivity(new Intent(this, MainActivity.class));
    }
}

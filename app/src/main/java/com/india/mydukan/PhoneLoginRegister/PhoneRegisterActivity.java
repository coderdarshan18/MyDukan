package com.india.mydukan.PhoneLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.india.mydukan.Sessions.SessionManager;
import com.india.mydukan.HomeActivity;
import com.india.mydukan.R;
import com.india.mydukan.RetrofitApi.ApiClient;
import com.india.mydukan.RetrofitApi.ApiInterface;
import com.india.mydukan.RetrofitApi.Users;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneRegisterActivity extends AppCompatActivity {

    private EditText phone,otp;
    private Button btnPhone,btnOtp;
    public static ApiInterface apiInterface;
    String user_id;
    SessionManager sessionManager;

    //////phone otp/////
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    ImageView dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        /////////hide status////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///////hide status end//////
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        mAuth=FirebaseAuth.getInstance();

        sessionManager = new SessionManager(this);




        init();
    }
    private void init(){
        phone = (EditText) findViewById(R.id.phone);
        otp = (EditText) findViewById(R.id.otp);
        btnPhone=(Button) findViewById(R.id.phoneBtn);
        btnOtp=(Button) findViewById(R.id.btnOtpSubmit);


        ////////////progressdialog/////////

        dialog = (ImageView) findViewById(R.id.loader);
        Glide.with(this).load(R.drawable.loader).into(dialog);

        ////////////////phone otp callback start/////////
        callbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
//                loaderImg.setVisibility(View.VISIBLE);
//                phone.setVisibility(View.VISIBLE);
                dialog.setVisibility(View.GONE);
               btnOtp.setVisibility(View.GONE);
                otp.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                btnPhone.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneRegisterActivity.this, "Invalid Phone Number"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token)
            {
                mVerificationId =verificationId;
                mResendToken=token;
                Toast.makeText(PhoneRegisterActivity.this, "Code has been sent,please check and verified", Toast.LENGTH_SHORT).show();
                btnOtp.setVisibility(View.VISIBLE);
                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                btnPhone.setVisibility(View.GONE);
                dialog.setVisibility(View.GONE);

            }

        };


        ///////////////phone otp callback end///////////

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_phone=phone.getText().toString().trim();
                if(TextUtils.isEmpty(user_phone)){
                    phone.setError("phone is reuired");
                }
                if(user_phone.length() !=10){
                    phone.setError("phone should be of 10 digit");
                }
                else {

                    dialog.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + user_phone,////phone number to verify
                            60,
                            TimeUnit.SECONDS,
                            PhoneRegisterActivity.this,
                            callbacks);
                }

            }
        });
        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().equals("")){
                    Toast.makeText(PhoneRegisterActivity.this, "please enter your 6 digit otp", Toast.LENGTH_SHORT).show();
                }
                if(otp.getText().toString().length() !=6){
                    Toast.makeText(PhoneRegisterActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential =PhoneAuthProvider.getCredential(mVerificationId,otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Register();
                        }
                        else{
                            Toast.makeText(PhoneRegisterActivity.this, "Something went wrong please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void Register() {

            Call<Users> call = apiInterface.performPhoneRegistration(phone.getText().toString());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String user_id = response.body().getUserId();
                    Toast.makeText(PhoneRegisterActivity.this, user_id, Toast.LENGTH_SHORT).show();
                    if(response.body().getResponse().equals("ok")){
                        user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);
                        Intent intent = new Intent(PhoneRegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Animatoo.animateSwipeLeft(PhoneRegisterActivity.this);

                        dialog.setVisibility(View.GONE);

                    }
                    else if(response.body().getResponse().equals("failed")){
                        Toast.makeText(PhoneRegisterActivity.this, "No account found", Toast.LENGTH_SHORT).show();
                        dialog.setVisibility(View.GONE);
                    }
                    else if(response.body().getResponse().equals("already")){
                        Toast.makeText(PhoneRegisterActivity.this, "This phone is already exists,please try another", Toast.LENGTH_SHORT).show();
                        dialog.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    dialog.setVisibility(View.GONE);

                }
            });
        }


    public void goToLogin(View view) {
        Intent intent = new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }
}
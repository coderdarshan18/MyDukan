package com.india.mydukan.EmailLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.india.mydukan.Sessions.SessionManager;
import com.india.mydukan.HomeActivity;
import com.india.mydukan.MainActivity;
import com.india.mydukan.R;
import com.india.mydukan.RetrofitApi.ApiClient;
import com.india.mydukan.RetrofitApi.ApiInterface;
import com.india.mydukan.RetrofitApi.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegisterActivity extends AppCompatActivity {

    private EditText name,email,password;
    private Button regBtn;

    public static ApiInterface apiInterface;
    private static final String TAG = "EmailRegisterActivity";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);
        /////////hide status////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///////hide status end//////

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        sessionManager = new SessionManager(this);



        init();
    }
    private void init(){
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        regBtn= (Button) findViewById(R.id.emailRegBtn);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Registration();

            }
        });
    }

    private void Registration() {

        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        if(TextUtils.isEmpty(user_name)){
            name.setError("Name is required");
        }
        else if(TextUtils.isEmpty(user_email)){
            email.setError("email is required");
        }
        else if(TextUtils.isEmpty(user_password)){
            password.setError("password is required");
        }
        else{
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Registering...");
            dialog.setMessage("Please wait while we are register your crdenitial");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            Call<Users> call =apiInterface.performEmailRegistration(user_name,user_email,user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Log.e(TAG, "onNext: "+response);
                    Toast.makeText(EmailRegisterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    if(response.body().getResponse().equals("ok")){
                        String user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);
                        Intent intent = new Intent(EmailRegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Animatoo.animateSwipeLeft(EmailRegisterActivity.this);
                        dialog.dismiss();
                        dialog.dismiss();
                    }
                   else if(response.body().getResponse().equals("failed")){
                        Toast.makeText(EmailRegisterActivity.this, "Something Went Wrong,Please Try Again", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if(response.body().getResponse().equals("already")){
                        Toast.makeText(EmailRegisterActivity.this, "This email is already exists,please try another.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(EmailRegisterActivity.this, "failed  ll", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }
}
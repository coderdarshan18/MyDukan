package com.india.mydukan.EmailLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button btnLogin;
    public static ApiInterface apiInterface;
    SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        /////////hide status////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///////hide status end//////
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        sessionManager =new SessionManager(this);


        init();
    }
    private void init(){
        email =(EditText) findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);
        btnLogin=(Button) findViewById(R.id.emailLogBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });



    }

    private void Login() {
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        if(TextUtils.isEmpty(user_email)){
            email.setError("email is required");
        }
        else if(TextUtils.isEmpty(user_password)){
            password.setError("password is required");
        }
        else{
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Logging...");
            dialog.setMessage("Please wait while we are checking your credential");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            Call<Users> call= apiInterface.performEmailLogin(user_email,user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.body().getResponse().equals("ok")){

                        String user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);
                        Intent intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Animatoo.animateSwipeLeft(EmailLoginActivity.this);
                        dialog.dismiss();
                    }
                    else if(response.body().getResponse().equals("no_account")){
                        Toast.makeText(EmailLoginActivity.this, "No account found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }
}
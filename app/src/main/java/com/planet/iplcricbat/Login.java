package com.planet.iplcricbat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.planet.iplcricbat.Module.Login_Module;
import com.planet.iplcricbat.Module.Match_OTP;
import com.planet.iplcricbat.Module.forgot_password;
import com.planet.iplcricbat.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    LinearLayout SignUp_txt;
    Button login_btn, new_password, get_otp;
    EditText username, password, email_forgot, otp, new_ps;
    SharedPreferences preferences;
    TextView forgot;
    BottomSheetDialog Bottom_dialog;
    String email_data = "";
    TextInputLayout email_txt, otp_txt, new_ps_txt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignUp_txt = findViewById(R.id.SignUp_txt);
        login_btn = findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgot = findViewById(R.id.forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickBottom();
            }
        });
        preferences = getSharedPreferences("details", MODE_PRIVATE);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    login();
                }
               // startActivity(new Intent(Login.this, Open_Dashboard.class));
            }
        });

        SignUp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));

            }
        });
    }

    private void login() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<Login_Module> call = RetrofitClient
                .getInstance()
                .getAllApiService()
                .login(username.getText().toString(),
                        password.getText().toString());
        call.enqueue(new Callback<Login_Module>() {
            @Override
            public void onResponse(Call<Login_Module> call, Response<Login_Module> response) {
                dialog.dismiss();
                Login_Module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", module.getName());
                        editor.putString("user_id", module.getUser_id());
                        editor.putString("email", module.getEmail());
                        editor.putString("mobile_no", module.getMobile_no());
                        editor.putString("refer_code",module.getRefer_code());
                        editor.commit();

                        startActivity(new Intent(Login.this, Open_Dashboard.class));


                    } else {
                        Toast.makeText(Login.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Login_Module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation() {
        if (username.getText().toString().equalsIgnoreCase("")) {
            username.setError("Please enter number");
            username.requestFocus();
            return false;
        } else if (password.getText().toString().equalsIgnoreCase("")) {
            password.setError("Please enter password");
            password.requestFocus();
            return false;
        }
        return true;
    }

    private void PickBottom() {
        Bottom_dialog = new BottomSheetDialog(Login.this);
        Bottom_dialog.setContentView(R.layout.forgot_custom);
        Bottom_dialog.show();

        email_forgot = Bottom_dialog.findViewById(R.id.email_forgot);
        otp = Bottom_dialog.findViewById(R.id.otp);
        new_ps = Bottom_dialog.findViewById(R.id.new_ps);
        new_password = Bottom_dialog.findViewById(R.id.new_password);
        get_otp = Bottom_dialog.findViewById(R.id.get_otp);
        email_txt = Bottom_dialog.findViewById(R.id.email_txt);
        otp_txt = Bottom_dialog.findViewById(R.id.otp_txt);
        new_ps_txt = Bottom_dialog.findViewById(R.id.new_ps_txt);


        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation_bottom()) {
                    if (email_forgot.getText().toString().matches(emailPattern)) {
                        forgot();
                    } else {
                        Toast.makeText(Login.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_validation()) {
                    new_password();
                }
            }
        });


    }

    private void new_password() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        Call<Match_OTP> match_otpCall = RetrofitClient
                .getInstance()
                .getAllApiService().match_otp(otp.getText().toString(), new_ps.getText().toString(),
                        email_data);

        match_otpCall.enqueue(new Callback<Match_OTP>() {
            @Override
            public void onResponse(Call<Match_OTP> call, Response<Match_OTP> response) {
                dialog.dismiss();
                Match_OTP match_otp = response.body();
                if (response.isSuccessful()) {
                    if (match_otp.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Login.this, match_otp.getMsg(), Toast.LENGTH_SHORT).show();
                        Bottom_dialog.dismiss();
                    } else {
                        Toast.makeText(Login.this, match_otp.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Match_OTP> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean new_validation() {
        if (otp.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            otp.requestFocus();
            return false;
        } else if (new_ps.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please enter new password ", Toast.LENGTH_SHORT).show();
            new_ps.requestFocus();
            return false;
        }
        return true;
    }

    private void forgot() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        Call<forgot_password> passwordCall = RetrofitClient
                .getInstance()
                .getAllApiService().forgot_password(email_forgot.getText().toString());

        passwordCall.enqueue(new Callback<forgot_password>() {
            @Override
            public void onResponse(Call<forgot_password> call, Response<forgot_password> response) {
                dialog.dismiss();
                forgot_password forgot_password = response.body();
                if (response.isSuccessful()) {
                    if (forgot_password.getStatus().equalsIgnoreCase("success")) {
                        email_txt.setVisibility(View.GONE);
                        get_otp.setVisibility(View.GONE);
                        otp_txt.setVisibility(View.VISIBLE);
                        new_ps_txt.setVisibility(View.VISIBLE);
                        new_password.setVisibility(View.VISIBLE);
                        email_data = forgot_password.getEmail();
                    } else {
                        Toast.makeText(Login.this, forgot_password.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<forgot_password> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation_bottom() {
        if (email_forgot.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please enter email", Toast.LENGTH_SHORT).show();
            email_forgot.requestFocus();
            return false;
        }
        return true;
    }

}
package com.planet.iplcricbat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.planet.iplcricbat.Module.Sign_Module;
import com.planet.iplcricbat.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    LinearLayout SignUp_txt;
    Button signUp;
    EditText name, no, email, refer_code, sp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp = findViewById(R.id.signUp);
        name = findViewById(R.id.name);
        no = findViewById(R.id.no);
        email = findViewById(R.id.email);
        refer_code = findViewById(R.id.refer_code);
        sp = findViewById(R.id.sp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    if (email.getText().toString().matches(emailPattern)) {
                        sign_up();
                    } else {
                        Toast.makeText(SignUp.this, "enter valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        SignUp_txt = findViewById(R.id.SignUp_txt);
        SignUp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));

            }
        });
    }

    private void sign_up() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<Sign_Module> moduleCall = RetrofitClient
                .getInstance()
                .getAllApiService().sign_up(name.getText().toString(),
                        no.getText().toString(),
                        email.getText().toString(),
                        refer_code.getText().toString(),
                        sp.getText().toString());
        moduleCall.enqueue(new Callback<Sign_Module>() {
            @Override
            public void onResponse(Call<Sign_Module> call, Response<Sign_Module> response) {
                dialog.dismiss();
                Sign_Module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(SignUp.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class));
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Sign_Module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validation() {
        if (name.getText().toString().equalsIgnoreCase("")) {
            name.setError("Please enter name");
            name.requestFocus();
            return false;

        } else if (no.getText().toString().equalsIgnoreCase("")) {
            no.setError("Please enter number");
            no.requestFocus();
            return false;
        } else if (email.getText().toString().equalsIgnoreCase("")) {
            email.setError("Please enter email");
            email.requestFocus();
            return false;
        } else if (sp.getText().toString().equalsIgnoreCase("")) {
            sp.setError("Please enter password");
            sp.requestFocus();
            return false;
        } else if (!sp.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{6,15}$")) {
            //not having special characters error message
            Toast.makeText(SignUp.this, "Password is too weak", Toast.LENGTH_SHORT).show();
            sp.requestFocus();
            return false;
        }
        return true;
    }
}
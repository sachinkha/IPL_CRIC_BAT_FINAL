package com.planet.iplcricbat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.planet.iplcricbat.Module.Bank_Module;
import com.planet.iplcricbat.Module.Paytm_Module;
import com.planet.iplcricbat.Module.UPI_Module;
import com.planet.iplcricbat.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Details extends AppCompatActivity {

    Toolbar toolbar;
    RadioButton upi, paytm, bank;
    EditText upi_id, upi_amount, paytm_no, paytm_amount, account_no, ifsc_code, account_holder, bank_amount;
    Button upi_submit, Paytm_submit, bank_submit;
    String user_id = "", wallet_balance = "";
    LinearLayout upi_layout, paytm_layout, bank_layout;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        preferences = getSharedPreferences("details", MODE_PRIVATE);
        user_id = preferences.getString("user_id", null);
        Intent intent = getIntent();
        wallet_balance = intent.getStringExtra("winning_total");

        upi_layout = findViewById(R.id.upi_layout);
        paytm_layout = findViewById(R.id.paytm_layout);
        bank_layout = findViewById(R.id.bank_layout);
        upi = findViewById(R.id.upi);
        paytm = findViewById(R.id.paytm);
        bank = findViewById(R.id.bank);

        upi_id = findViewById(R.id.upi_id);
        upi_amount = findViewById(R.id.upi_amount);
        upi_submit = findViewById(R.id.upi_submit);

        paytm_no = findViewById(R.id.paytm_no);
        paytm_amount = findViewById(R.id.paytm_amount);
        Paytm_submit = findViewById(R.id.Paytm_submit);

        account_no = findViewById(R.id.account_no);
        ifsc_code = findViewById(R.id.ifsc_code);
        account_holder = findViewById(R.id.account_holder);
        bank_amount = findViewById(R.id.bank_amount);
        bank_submit = findViewById(R.id.bank_submit);

        bank_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bank_validation()) {
                    if (bank_amount.length() < 3) {
                        Toast.makeText(Payment_Details.this, "withdrawal amount greater than " + "\u20A8." + "100", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(wallet_balance)<Integer.parseInt(bank_amount.getText().toString())){
                        Toast.makeText(Payment_Details.this, "Please enter sufficient amount", Toast.LENGTH_SHORT).show();
                    } else {
                        bank_submit();
                    }
                }
            }
        });
        upi_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Upi_validation()) {
                    if (upi_amount.length() < 3) {
                        Toast.makeText(Payment_Details.this, "withdrawal amount greater than " + "\u20A8." + "100", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(wallet_balance)<Integer.parseInt(upi_amount.getText().toString())){
                        Toast.makeText(Payment_Details.this, "Please enter sufficient amount", Toast.LENGTH_SHORT).show();
                    } else {
                        upi_submit();
                    }
                }
            }
        });

        Paytm_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paytm_validation()) {
                    if (paytm_amount.length() < 3) {
                        Toast.makeText(Payment_Details.this, "withdrawal amount greater than " + "\u20A8." + "100", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(wallet_balance)<Integer.parseInt(paytm_amount.getText().toString())){
                        Toast.makeText(Payment_Details.this, "Please enter sufficient amount", Toast.LENGTH_SHORT).show();
                    } else {
                        Paytm_submit();
                    }
                }
            }
        });
        upi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (upi.isChecked()) {
                    upi_layout.setVisibility(View.VISIBLE);
                    paytm_layout.setVisibility(View.GONE);
                    bank_layout.setVisibility(View.GONE);
                }
            }
        });
        paytm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (paytm.isChecked()) {
                    paytm_layout.setVisibility(View.VISIBLE);
                    bank_layout.setVisibility(View.GONE);
                    upi_layout.setVisibility(View.GONE);
                }
            }
        });

        bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bank.isChecked()) {
                    bank_layout.setVisibility(View.VISIBLE);
                    upi_layout.setVisibility(View.GONE);
                    paytm_layout.setVisibility(View.GONE);

                }
            }
        });
    }

    private void bank_submit() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait....");
        dialog.show();
        Call<Bank_Module> moduleCall = RetrofitClient.getInstance()
                .getAllApiService().bank(user_id, account_no.getText().toString(),
                        ifsc_code.getText().toString(), account_holder.getText().toString(),
                        bank_amount.getText().toString());

        moduleCall.enqueue(new Callback<Bank_Module>() {
            @Override
            public void onResponse(Call<Bank_Module> call, Response<Bank_Module> response) {
                dialog.dismiss();
                Bank_Module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Payment_Details.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Payment_Details.this, Wallet.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Payment_Details.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bank_Module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Payment_Details.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Paytm_submit() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait....");
        dialog.show();
        Call<Paytm_Module> moduleCall = RetrofitClient
                .getInstance()
                .getAllApiService().paytm(user_id, paytm_no.getText().toString(),
                        paytm_amount.getText().toString());

        moduleCall.enqueue(new Callback<Paytm_Module>() {
            @Override
            public void onResponse(Call<Paytm_Module> call, Response<Paytm_Module> response) {
                dialog.dismiss();
                Paytm_Module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Payment_Details.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Payment_Details.this, Wallet.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Payment_Details.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Paytm_Module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Payment_Details.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void upi_submit() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait....");
        dialog.show();
        Call<UPI_Module> moduleCall = RetrofitClient
                .getInstance()
                .getAllApiService().upi(user_id, upi_id.getText().toString(),
                        upi_amount.getText().toString());

        moduleCall.enqueue(new Callback<UPI_Module>() {
            @Override
            public void onResponse(Call<UPI_Module> call, Response<UPI_Module> response) {
                dialog.dismiss();
                UPI_Module module = response.body();

                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Payment_Details.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Payment_Details.this, Wallet.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Payment_Details.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UPI_Module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Payment_Details.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Upi_validation() {
        if (upi_id.getText().toString().equalsIgnoreCase("")) {
            upi_id.setError("Please enter your upi id");
            upi_id.requestFocus();
            return false;
        } else if (upi_amount.getText().toString().equalsIgnoreCase("")) {
            upi_amount.setError("Please enter withdrawal amount");
            upi_amount.requestFocus();
            return false;
        }
        return true;
    }

    private boolean Paytm_validation() {
        if (paytm_no.getText().toString().equalsIgnoreCase("")) {
            paytm_no.setError("Please enter your paytm no.");
            paytm_no.requestFocus();
            return false;
        } else if (paytm_amount.getText().toString().equalsIgnoreCase("")) {
            paytm_amount.setError("Please enter withdrawal amount");
            paytm_amount.requestFocus();
            return false;
        }
        return true;
    }

    private boolean bank_validation() {
        if (account_no.getText().toString().equalsIgnoreCase("")) {
            account_no.setError("please enter account number");
            account_no.requestFocus();
            return false;
        } else if (ifsc_code.getText().toString().equalsIgnoreCase("")) {
            ifsc_code.setError("Please enter IFSC code");
            ifsc_code.requestFocus();
            return false;
        } else if (account_holder.getText().toString().equalsIgnoreCase("")) {
            account_holder.setError("Please enter Holder Name");
            account_holder.requestFocus();
            return false;
        } else if (bank_amount.getText().toString().equalsIgnoreCase("")) {
            bank_amount.setError("Please enter withdrawal amount");
            bank_amount.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
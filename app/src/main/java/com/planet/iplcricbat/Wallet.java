package com.planet.iplcricbat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Wallet extends AppCompatActivity {

    SharedPreferences preferences;
    TextView name_profile, balance, add_balance, discount, winning_balance, grandTotal;
    Toolbar toolbar;
    String user_id= "",winning_total = "";
    Button add_amount, withdrawal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        preferences = getSharedPreferences("details", MODE_PRIVATE);
        user_id = preferences.getString("user_id", null);

        name_profile = findViewById(R.id.name_profile);
        name_profile.setText(preferences.getString("name", null));

        balance = findViewById(R.id.balance);
        add_balance = findViewById(R.id.add_balance);
        discount = findViewById(R.id.discount);
        winning_balance = findViewById(R.id.winning_balance);
        grandTotal = findViewById(R.id.grandTotal);

      amount();

       /* String amount = preferences.getString("amount", "");
        String discount = preferences.getString("discountAmount", "");
        String winning_amount = preferences.getString("winning_amount", "");       */
        //  int value = Integer.parseInt(amount)+Integer.parseInt(discount)+Integer.parseInt(winning_amount);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        add_amount = findViewById(R.id.add_amount);
        withdrawal = findViewById(R.id.withdrawal);
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Payment_Details.class);
                intent.putExtra("winning_total",winning_total);
                startActivity(intent);
            }
        });

        add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Wallet.this, Payment.class));
            }
        });

    }

    private void amount() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait....");
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://iplcricbet.com/Api/add_amount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                dialog.dismiss();
                //  Toast.makeText(Open_Dashboard.this, response, Toast.LENGTH_SHORT).show();
                if (response != null){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("success")){

                            winning_total = jsonObject.getString("winning_total");
                            balance.setText("\u20A8." +winning_total);
                            add_balance.setText("\u20A8." +jsonObject.getString("sum_amount"));
                            discount.setText("\u20A8." + jsonObject.getString("discount_sum"));
                            winning_balance.setText("\u20A8." +jsonObject.getString("winning_amount"));
                            grandTotal.setText("\u20A8." + jsonObject.getString("grand_total"));

/*
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("amount",);
                            editor.putString("discountAmount",);
                            editor.putString("winning_amount",);
                            editor.putString("winning_total",);
                            editor.putString("grand_total",);
                            editor.commit();*/
                        }else {
                            // Toast.makeText(Open_Dashboard.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Wallet.this, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> getParams = new HashMap<>();
                getParams.put("user_id",user_id);
                return getParams;
            }
        };
        queue.add(request);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Wallet.this,MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(Wallet.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
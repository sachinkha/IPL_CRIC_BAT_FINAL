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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.planet.iplcricbat.Module.over_bet_module;
import com.planet.iplcricbat.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Per_over extends AppCompatActivity {

    Toolbar toolbar;
    ImageView per_over_first, per_over_second,per_over_fail;
    TextView per_over_first_team, per_over_second_team;
    String match_id = "", append = "", wallet_balance = "";
    TextView over, ball, run, play_team;
    EditText Ball, amount_guess;
    Button guess_btn;
    String over_string = "", run_check = "";
    LinearLayout layout_ball, over_layout;
    RadioButton bat2, bat3, bat4, bat6, bat_w, bat_b;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_over);


        per_over_first = findViewById(R.id.per_over_first);
        per_over_second = findViewById(R.id.per_over_second);
        per_over_first_team = findViewById(R.id.per_over_first_team);
        per_over_second_team = findViewById(R.id.per_over_second_team);
        over_layout = findViewById(R.id.over_layout);
        ball = findViewById(R.id.ball);
        over = findViewById(R.id.over);
        run = findViewById(R.id.run);
        play_team = findViewById(R.id.play_team);
        layout_ball = findViewById(R.id.layout_ball);
        Ball = findViewById(R.id.Ball);
        amount_guess = findViewById(R.id.amount_guess);
        guess_btn = findViewById(R.id.guess_btn);
        per_over_fail = findViewById(R.id.per_over_fail);

        bat2 = findViewById(R.id.bat2);
        bat3 = findViewById(R.id.bat3);
        bat4 = findViewById(R.id.bat4);
        bat6 = findViewById(R.id.bat6);
        bat_w = findViewById(R.id.bat_w);
        bat_b = findViewById(R.id.bat_b);


        bat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat2.isChecked()) {
                    run_check = bat2.getText().toString();
                }
            }
        });
        bat3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat3.isChecked()) {
                    run_check = bat3.getText().toString();
                }
            }
        });
        bat4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat4.isChecked()) {
                    run_check = bat4.getText().toString();
                }
            }
        });
        bat6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat6.isChecked()) {
                    run_check = bat6.getText().toString();
                }
            }
        });

        bat_w.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat_w.isChecked()) {
                    run_check = bat_w.getText().toString();
                }
            }
        });

        bat_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bat_b.isChecked()) {
                    run_check = bat_b.getText().toString();
                }
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        preferences = getSharedPreferences("details", MODE_PRIVATE);


        Picasso.get().load(intent.getStringExtra("first_image")).into(per_over_first);
        Picasso.get().load(intent.getStringExtra("second_image")).into(per_over_second);
        per_over_first_team.setText(intent.getStringExtra("first_team"));
        per_over_second_team.setText(intent.getStringExtra("second_team"));
        match_id = intent.getStringExtra("match_id");
        wallet_balance = intent.getStringExtra("wallet_balance");
        //  Toast.makeText(Per_over.this, match_id, Toast.LENGTH_SHORT).show();

                per_over_details();


        guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (Integer.parseInt(amount_guess.getText().toString()) > Integer.parseInt(wallet_balance)) {
                        Toast.makeText(Per_over.this, "Please add money first ", Toast.LENGTH_SHORT).show();
                    } else {
                        guess();
                    }
                }
            }
        });
    }

    private void guess() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        Call<over_bet_module> moduleCall = RetrofitClient
                .getInstance()
                .getAllApiService().over_bet(preferences.getString("user_id", null),
                        match_id, over_string, Ball.getText().toString(), run_check,
                        amount_guess.getText().toString());

        moduleCall.enqueue(new Callback<over_bet_module>() {
            @Override
            public void onResponse(Call<over_bet_module> call, retrofit2.Response<over_bet_module> response) {
                dialog.dismiss();
                over_bet_module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(Per_over.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                        per_over_details();
                        bat2.setChecked(false);
                        bat3.setChecked(false);
                        bat4.setChecked(false);
                        bat6.setChecked(false);
                        bat_w.setChecked(false);
                        bat_b.setChecked(false);
                        amount_guess.setText("");
                        Ball.setText("");
                    }
                } else {
                    Toast.makeText(Per_over.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<over_bet_module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Per_over.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Validation() {
        if (Ball.getText().toString().equalsIgnoreCase("")) {
            Ball.setError("Please enter next ball");
            Ball.requestFocus();
            return false;
        } else if (run_check.equalsIgnoreCase("")) {
            Toast.makeText(Per_over.this, "Please select run", Toast.LENGTH_SHORT).show();
            return false;
        } else if (amount_guess.getText().toString().equalsIgnoreCase("")) {
            amount_guess.setError("Please enter amount");
            amount_guess.requestFocus();
            return false;
        } else if (amount_guess.length() < 3) {
            amount_guess.setError("Minimum 100");
            amount_guess.requestFocus();
            return false;
        }
        return true;
    }

    private void per_over_details() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://iplcricbet.com/Api/ball_wise_score", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            layout_ball.setVisibility(View.VISIBLE);
                            over_layout.setVisibility(View.VISIBLE);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                play_team.setText(object.getString("team_name"));
                                over_string = object.getString("over");
                                over.setText("" + object.getString("over"));
                                ball.setText("." + object.getString("over_ball"));
                                append = append + "-" + object.getString("ball_run");
                                run.setText("Run : " + append);
                                // run.setText("Run :- "+object.getString("ball_run"));

                            }

                        } else {
                            per_over_fail.setVisibility(View.VISIBLE);
                            //Toast.makeText(Per_over.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(Per_over.this, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getParams = new HashMap<>();
                getParams.put("match_id", match_id);
                return getParams;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Per_over.this,MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(Per_over.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
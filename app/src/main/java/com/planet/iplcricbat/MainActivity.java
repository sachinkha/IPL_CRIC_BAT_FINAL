package com.planet.iplcricbat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.planet.iplcricbat.Adapter.All_Match_Adapter;
import com.planet.iplcricbat.Adapter.Custom_View_Pager_Adapter;
import com.planet.iplcricbat.Adapter.Match_RCY_Adapter;
import com.planet.iplcricbat.Adapter.Over_Adapter;
import com.planet.iplcricbat.Adapter.Toss_RCY_Adapter;
import com.planet.iplcricbat.Module.Match_Bet_Module;
import com.planet.iplcricbat.Module.Match_Module;
import com.planet.iplcricbat.Module.Over_Module;
import com.planet.iplcricbat.Module.Team_Module;
import com.planet.iplcricbat.Module.Toss_Module;
import com.planet.iplcricbat.Module.ViewPagerData;
import com.planet.iplcricbat.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Match_RCY_Adapter.Match_bet
        , Toss_RCY_Adapter.Toss_bet, All_Match_Adapter.All_Team, Over_Adapter.Over_Bat {

    SharedPreferences preferences;
    RecyclerView toss, match, team;
    ArrayList<ViewPagerData> listImage = new ArrayList<>();
    NavigationView navigationView;
    RecyclerView over_rcy;
    TextView show_fund;
    String user_code = "";
    String wallet_balance = "0",user_id = "";
    String toss_api = "https://pay2pal.in/tata_ipl/Api/get_toss";
    String[] value = {"one", "two", "three"};
    CircleIndicator circleIndicator;
    public ViewPager viewPager1;
    int cunt = 0;
    ArrayList<Team_Module> Team = new ArrayList<>();
    EditText amount;
    String id = "", first_team = "", second_team = "", team_name = "", type = "";
    ArrayList<Toss_Module> arrayList = new ArrayList<>();
    ArrayList<Match_Module> moduleArrayList = new ArrayList<>();
    ArrayList<Over_Module> over_array = new ArrayList<Over_Module>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView name = view.findViewById(R.id.head_name);
        TextView head_email = view.findViewById(R.id.head_email);
        preferences = getSharedPreferences("details", MODE_PRIVATE);
        user_id = preferences.getString("user_id",null);
        show_fund = toolbar.findViewById(R.id.show_fund);
        TextView add_fund = toolbar.findViewById(R.id.add_fund);

        value_add();
        amount();

        add_fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Payment.class));
            }
        });
        name.setText(preferences.getString("name", null));
        head_email.setText(preferences.getString("email", null));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


      /*  spinner_toss = findViewById(R.id.spinner_toss);
        preferences = getSharedPreferences("details", MODE_PRIVATE);
        spinner_amount = findViewById(R.id.spinner_amount);
*/
      /*  Toss_Adapter adapter = new Toss_Adapter(MainActivity.this, Toss_value);
        spinner_toss.setAdapter(adapter);

        Amount_Adapter amount_adapter = new Amount_Adapter(MainActivity.this,amount);
        spinner_amount.setAdapter(amount_adapter);
*/

/*
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, Toss_value);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_toss.setAdapter(spinnerArrayAdapter);

        spinner_toss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = String.valueOf(id);
                value2 = (String) parent.getItemAtPosition(position);
               //  Toast.makeText(MainActivity.this, value + "  "+ value2, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        viewPager1 = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleindia);
        slider();
        toss = findViewById(R.id.toss);
        toss.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        toss();
        match = findViewById(R.id.match);
        match.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        match();
        team = findViewById(R.id.team);
        team.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        team();

        over_rcy = findViewById(R.id.over_rcy);
        over_rcy.setLayoutManager(new LinearLayoutManager(this));
        over_rcy();
    }

    private void value_add() {

        user_code = preferences.getString("refer_code", null);
    }

    private void over_rcy() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_match", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Over_Module match_module = new Over_Module();
                                JSONObject object = jsonArray.getJSONObject(i);
                                match_module.setId(object.getString("id"));
                                match_module.setFirst_team(object.getString("first_team"));
                                match_module.setSecond_team(object.getString("second_team"));
                                match_module.setFirst_image(object.getString("first_image"));
                                match_module.setSecond_image(object.getString("second_image"));

                                over_array.add(match_module);
                            }

                            Over_Adapter adapter = new Over_Adapter(MainActivity.this, over_array, MainActivity.this);
                            over_rcy.setAdapter(adapter);

                        } else {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Log.e("error", String.valueOf(error));

            }
        });
        queue.add(request);
    }


    private void slider() {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_banner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response.toString());
                if (response != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("ImageResponse", ">>>>" + jsonObject);

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ViewPagerData imageShow = new ViewPagerData();
                                JSONObject c = jsonArray.getJSONObject(i);

                                imageShow.setImage(c.getString("image"));
                                imageShow.setId(c.getString("id"));

                                Log.e("ImageoneSet", ">>>>" + imageShow);

                                listImage.add(imageShow);
                                Log.e("ListImage", ">>>" + listImage);
                            }
                            Custom_View_Pager_Adapter myCustomPagerAdapter = new Custom_View_Pager_Adapter(MainActivity.this, listImage);
                            viewPager1.setAdapter(myCustomPagerAdapter);
                            circleIndicator.setViewPager(viewPager1);

                            final Handler handler = new Handler();
                            final Runnable update = new Runnable() {
                                @Override
                                public void run() {
                                    if (cunt == listImage.size()) {
                                        cunt = 0;
                                    }
                                    viewPager1.setCurrentItem(cunt++, true);
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(update);
                                }
                            }, 2000, 2000);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        });
        queue.add(request);
    }


    private void team() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_match", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Team_Module match_module = new Team_Module();
                                JSONObject object = jsonArray.getJSONObject(i);
                                match_module.setId(object.getString("id"));
                                match_module.setFirst_team(object.getString("first_team"));
                                match_module.setSecond_team(object.getString("second_team"));
                                match_module.setFirst_image(object.getString("first_image"));
                                match_module.setSecond_image(object.getString("second_image"));

                                Team.add(match_module);
                            }

                            All_Match_Adapter adapter = new All_Match_Adapter(MainActivity.this, Team, MainActivity.this);
                            team.setAdapter(adapter);
                        } else {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Log.e("error", String.valueOf(error));

            }
        });
        queue.add(request);
    }

    private void match() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_match", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Match_Module match_module = new Match_Module();
                                JSONObject object = jsonArray.getJSONObject(i);
                                match_module.setId(object.getString("id"));
                                match_module.setFirst_team(object.getString("first_team"));
                                match_module.setSecond_team(object.getString("second_team"));
                                match_module.setFirst_image(object.getString("first_image"));
                                match_module.setSecond_image(object.getString("second_image"));
                                match_module.setPercentage1(object.getString("percentage1") + "X");
                                match_module.setPercentage2(object.getString("percentage2") + "X");
                                match_module.setWinner_match(object.getString("winner_match"));

                                moduleArrayList.add(match_module);
                            }

                            Match_RCY_Adapter adapter = new Match_RCY_Adapter(MainActivity.this, moduleArrayList, MainActivity.this);
                            match.setAdapter(adapter);
                        } else {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Log.e("error", String.valueOf(error));

            }
        });
        queue.add(request);
    }

    private void toss() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_toss", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Toss_Module module = new Toss_Module();
                                JSONObject object = jsonArray.getJSONObject(i);
                                module.setId(object.getString("id"));
                                module.setFirst_team(object.getString("first_team"));
                                module.setSecond_team(object.getString("second_team"));
                                module.setFirst_image(object.getString("first_image"));
                                module.setSecond_image(object.getString("second_image"));
                                module.setPercentage1(object.getString("percentage1") + "X");
                                module.setPercentage2(object.getString("percentage2") + "X");
                                module.setWinner_toss(object.getString("winner_toss"));

                                arrayList.add(module);
                            }

                            Toss_RCY_Adapter adapter = new Toss_RCY_Adapter(MainActivity.this, arrayList, MainActivity.this);
                            toss.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", String.valueOf(error));
                dialog.dismiss();
            }
        });
        queue.add(request);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, Profile.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, Wallet.class));

        } else if (id == R.id.refer) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "IPL CRIC BAT");
                String shareMessage = "\nInvite code: " + user_code + ".\n\n";
                shareMessage = shareMessage + "Download the app using my link:" + "https://iplcricbet.com/" + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        } else if (id == R.id.action_settings) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void match_bet(String id, String first_team, String second_team) {
        this.id = id;
        this.first_team = first_team;
        this.second_team = second_team;
        type = "match";
        PickBottom();
    }

    private void PickBottom() {
        final BottomSheetDialog Bottom_dialog = new BottomSheetDialog(MainActivity.this);
        Bottom_dialog.setContentView(R.layout.drop_order_custom);
        Bottom_dialog.show();
        RadioButton first_radio, second_radio;

        first_radio = Bottom_dialog.findViewById(R.id.first_radio);
        second_radio = Bottom_dialog.findViewById(R.id.second_radio);
        amount = Bottom_dialog.findViewById(R.id.amount);

        first_radio.setText(first_team);
        second_radio.setText(second_team);
        Button submit;

        first_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (first_radio.isChecked()) {
                    team_name = first_radio.getText().toString();
                } else {

                }
            }
        });
        second_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (second_radio.isChecked()) {
                    team_name = second_radio.getText().toString();
                }
            }
        });

        submit = Bottom_dialog.findViewById(R.id.submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = Integer.parseInt(amount.getText().toString());
                int value1 = Integer.parseInt(wallet_balance);
                if (validation()) {
                    if (amount.length() < 3) {
                        amount.setError("Minimum 100");
                        amount.requestFocus();
                    } else if (value > value1) {
                        Toast.makeText(MainActivity.this, "Please add money first ", Toast.LENGTH_SHORT).show();
                    } else {
                        Call<Match_Bet_Module> moduleCall = RetrofitClient
                                .getInstance()
                                .getAllApiService().match_bet(preferences.getString("user_id", null), id,
                                        team_name, amount.getText().toString(), type);

                        moduleCall.enqueue(new Callback<Match_Bet_Module>() {
                            @Override
                            public void onResponse(Call<Match_Bet_Module> call, retrofit2.Response<Match_Bet_Module> response) {
                                Log.e("response", String.valueOf(response));
                                Bottom_dialog.dismiss();
                                Match_Bet_Module module = response.body();
                                if (response.isSuccessful()) {
                                    if (module.getStatus().equalsIgnoreCase("success")) {
                                        amount();

                                    } else {
                                        Toast.makeText(MainActivity.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Match_Bet_Module> call, Throwable t) {
                                Bottom_dialog.dismiss();
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        });
    }

    private boolean validation() {
        if (team_name.equalsIgnoreCase("")) {
            Toast.makeText(MainActivity.this, "Please select your team", Toast.LENGTH_SHORT).show();
            return false;
        } else if (amount.getText().toString().equalsIgnoreCase("")) {
            amount.setError("Please enter amount");
            amount.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void toss_bet(String id, String first_team, String second_team) {
        this.id = id;
        this.first_team = first_team;
        this.second_team = second_team;
        type = "toss";
        PickBottom();
    }


    @Override
    public void all_team(String id) {

        Intent intent = new Intent(MainActivity.this, TEAM.class);
        intent.putExtra("match_id", id);
        intent.putExtra("wallet_balance", wallet_balance);
        startActivity(intent);
    }

    @Override
    public void over_bat(String id, String first_image, String second_image, String first_team, String second_team) {
        Intent intent = new Intent(MainActivity.this, Per_over.class);
        intent.putExtra("match_id", id);
        intent.putExtra("first_image", first_image);
        intent.putExtra("second_image", second_image);
        intent.putExtra("first_team", first_team);
        intent.putExtra("second_team", second_team);
        intent.putExtra("wallet_balance", wallet_balance);
        startActivity(intent);

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

                            show_fund.setText("\u20A8." + jsonObject.getString("winning_total"));
                            wallet_balance = jsonObject.getString("winning_total");
                           /* balance.setText("\u20A8." + jsonObject.getString("winning_total"));
                            add_balance.setText("\u20A8." +jsonObject.getString("sum_amount"));
                            discount.setText("\u20A8." + jsonObject.getString("discount_sum"));
                            winning_balance.setText("\u20A8." +jsonObject.getString("winning_amount"));
                            grandTotal.setText("\u20A8." + jsonObject.getString("grand_total"));
*/
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
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                MainActivity.this);
        myAlertDialog.setTitle("IPL CRIC BAT");
        myAlertDialog.setMessage("You want to close app?");
        myAlertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });

        myAlertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        myAlertDialog.show();
    }

}

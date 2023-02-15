package com.planet.iplcricbat.UI;


import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.planet.iplcricbat.Adapter.Batting_Adapter;
import com.planet.iplcricbat.Module.Player_Module;
import com.planet.iplcricbat.Module.Player_bet;
import com.planet.iplcricbat.R;
import com.planet.iplcricbat.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class Batting extends Fragment implements Batting_Adapter.Batting_set {
    RecyclerView batting_rcy;
    Batting_Adapter adapter;
    String id;
    String wallet_balance = "";
    EditText player_run, player_amount;
    BottomSheetDialog Bottom_dialog;
    ImageView batting_fail;
    String player_id = "", match_id = "";
    SharedPreferences preferences;
    ArrayList<Player_Module> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_batting, container, false);
        batting_rcy = view.findViewById(R.id.batting_rcy);
        batting_fail = view.findViewById(R.id.batting_fail);
        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra("match_id");
        // Toast.makeText(getActivity(),id, Toast.LENGTH_SHORT).show();
        batting_rcy.setLayoutManager(new LinearLayoutManager(view.getContext()));

        preferences = getActivity().getSharedPreferences("details", MODE_PRIVATE);
        wallet_balance = intent.getStringExtra("wallet_balance");

        batting();

        return view;


    }

    private void batting() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, "https://iplcricbet.com/Api/get_batsman", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                if (response != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            JSONArray array = jsonObject.getJSONArray("bat");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Player_Module module = new Player_Module();
                                module.setId(object.getString("id"));
                                module.setPlayers_name(object.getString("player"));
                                module.setMatch_id(object.getString("match_id"));

                                arrayList.add(module);
                            }
                            adapter = new Batting_Adapter(getActivity(), arrayList, Batting.this);
                            batting_rcy.setAdapter(adapter);

                        }else {
                            batting_fail.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", String.valueOf(error));
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getParams = new HashMap<>();
                getParams.put("match_id", id);
                // getParams.put("type","bat");
                return getParams;
            }
        };
        queue.add(request);
    }

    @Override
    public void batting_set(String id, String match_id) {
        this.player_id = id;
        this.match_id = match_id;
        PickBottom();
    }

    private void PickBottom() {
        Bottom_dialog = new BottomSheetDialog(getActivity());
        Bottom_dialog.setContentView(R.layout.player_custom);
        Bottom_dialog.show();

        Button player_bet;

        player_run = Bottom_dialog.findViewById(R.id.player_run);
        player_amount = Bottom_dialog.findViewById(R.id.player_amount);
        player_bet = Bottom_dialog.findViewById(R.id.player_bet);

        player_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    int value = Integer.parseInt(player_amount.getText().toString());
                    int value1 = Integer.parseInt(wallet_balance);
                    if (value > value1) {
                        Toast.makeText(getActivity(), "Please add money first ", Toast.LENGTH_SHORT).show();
                    } else {
                        player_bet();
                    }
                }
            }
        });


    }


    private boolean validation() {
        if (player_run.getText().toString().equalsIgnoreCase("")) {
            player_run.setError("Please enter run");
            player_run.requestFocus();
            return false;
        } else if (player_amount.getText().toString().equalsIgnoreCase("")) {
            player_amount.setError("Please enter amount");
            player_amount.requestFocus();
            return false;
        } else if (player_amount.length() < 3) {
            player_amount.setError("Minimum 100");
            player_amount.requestFocus();
            return false;
        }
        return true;
    }

    private void player_bet() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait....");
        dialog.dismiss();
        Call<Player_bet> player_betCall = RetrofitClient
                .getInstance()
                .getAllApiService().player_bet(preferences.getString("user_id", null), match_id, player_id
                        , player_run.getText().toString(), player_amount.getText().toString());

        player_betCall.enqueue(new Callback<Player_bet>() {
            @Override
            public void onResponse(Call<Player_bet> call, retrofit2.Response<Player_bet> response) {
                dialog.dismiss();
                Player_bet player_module = response.body();
                if (response.isSuccessful()) {
                    if (player_module.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(getActivity(), player_module.getMsg(), Toast.LENGTH_SHORT).show();
                        Bottom_dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                    Bottom_dialog.dismiss();
                }

                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Player_bet> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


}
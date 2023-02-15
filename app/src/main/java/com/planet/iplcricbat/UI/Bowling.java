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
import com.planet.iplcricbat.Adapter.Bowling_Adapter;
import com.planet.iplcricbat.Module.Bowling_Module;
import com.planet.iplcricbat.Module.Bowling_bet;
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


public class Bowling extends Fragment implements Bowling_Adapter.Bowling_Set {
    RecyclerView bowling_rcy;
    String id;
    String wallet_balance = "";
    BottomSheetDialog Bottom_dialog;
    EditText wicket, bowling_amount;
    SharedPreferences preferences;
    ImageView bowling_fail;
    String Baller_id = "",match_id = "";
    ArrayList<Bowling_Module> arrayList = new ArrayList<>();
//    String[] player = {"one","one","one","one","one","one","one","one","one","one","one","one"};
    Bowling_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bowling, container, false);
        bowling_rcy = view.findViewById(R.id.bowling_rcy);
        bowling_fail = view.findViewById(R.id.bowling_fail);
        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra("match_id");

        preferences = getActivity().getSharedPreferences("details", MODE_PRIVATE);
        wallet_balance = intent.getStringExtra("wallet_balance");


        bowling_rcy.setLayoutManager(new LinearLayoutManager(view.getContext()));


        Bowling();
        return view;
    }



    private void Bowling() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, "https://iplcricbet.com/Api/get_blower", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            JSONArray array = jsonObject.getJSONArray("ball");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Bowling_Module module = new Bowling_Module();
                                module.setId(object.getString("id"));
                                module.setPlayers_name(object.getString("player"));
                                module.setMatch_id(object.getString("match_id"));


                                arrayList.add(module);
                            }
                            adapter = new Bowling_Adapter(getActivity(),arrayList,Bowling.this);
                            bowling_rcy.setAdapter(adapter);

                        }else {
                            bowling_fail.setVisibility(View.VISIBLE);
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
                //getParams.put("type","ball");
                return getParams;
            }
        };
        queue.add(request);
    }

    @Override
    public void bowling_set(String id, String match_id) {
        this.Baller_id = id;
        this.match_id = match_id;
        PickBottom();
    }
    private void PickBottom() {
        Bottom_dialog = new BottomSheetDialog(getActivity());
        Bottom_dialog.setContentView(R.layout.bowling_custom);
        Bottom_dialog.show();

        Button player_bet;

        wicket = Bottom_dialog.findViewById(R.id.wicket);
        bowling_amount = Bottom_dialog.findViewById(R.id.bowling_amount);
        player_bet = Bottom_dialog.findViewById(R.id.Bowling_bet);

        player_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    int value = Integer.parseInt(bowling_amount.getText().toString());
                    int value1 = Integer.parseInt(wallet_balance);
                    if (value > value1) {
                        Toast.makeText(getActivity(), "Please add money first ", Toast.LENGTH_SHORT).show();
                    }else {
                        bowling_bet();
                        Bottom_dialog.dismiss();

                    }
                }
            }
        });


    }

    private boolean validation() {
        if (wicket.getText().toString().equalsIgnoreCase("")){
            wicket.setError("Please enter run");
            wicket.requestFocus();
            return false;
        }else if (bowling_amount.getText().toString().equalsIgnoreCase("")){
            bowling_amount.setError("Please enter amount");
            bowling_amount.requestFocus();
            return false;
        }else if (bowling_amount.length()<3){
            bowling_amount.setError("Minimum 100");
            bowling_amount.requestFocus();
            return false;
        }
        return true;
    }

    private void bowling_bet() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait....");
        dialog.show();
        Call<Bowling_bet> bowling_bat = RetrofitClient
                .getInstance()
                .getAllApiService().bowling_bat(preferences.getString("user_id",null),match_id,Baller_id
                        ,wicket.getText().toString(),bowling_amount.getText().toString());

        bowling_bat.enqueue(new Callback<Bowling_bet>() {
            @Override
            public void onResponse(Call<Bowling_bet> call, retrofit2.Response<Bowling_bet> response) {
                dialog.dismiss();
                Bowling_bet module = response.body();
                if (response.isSuccessful()){
                    if (module.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(getActivity(), module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Bowling_bet> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
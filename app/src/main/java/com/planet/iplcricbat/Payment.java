package com.planet.iplcricbat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.planet.iplcricbat.Adapter.Amount_Adapter_Design;
import com.planet.iplcricbat.Adapter.Offer_Adapter;
import com.planet.iplcricbat.Module.Offer_module;
import com.planet.iplcricbat.Module.payment_module;
import com.planet.iplcricbat.Retrofit.RetrofitClient;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Payment extends AppCompatActivity {

    private static final String TAG = "Payment";
    String total, Discount, GrandTotal, propertyType, booking_date, pro_Id,
            pro_assress, cityName, selectType, cabinSelId,
            stringtimefrom, stringtimeto, timing, person, timefrom, timetoStr;

    private static final int PERMISSION_REQUEST_CODE = 200;

    String encodeScreen_shot = "";
    File file;
    String txnId = "";
    String totalPrice;
    String bet = "";
    SharedPreferences preferences;
    Toolbar toolbar;
    EditText amountEdt, txn_Id, Amount;
    ImageView screen_shot, camera;
    String url = "https://iplcricbet.com/Api/get_bank_details";
    TextView account_name, account_number, ifsc, phonePe_no, google_Pay_no, paytm_details_no;
    Button payBtn, submit_ss;
    CardView account_card, phonePe_card, google_Pay_card, paytm_card;
    RecyclerView offer_rcy, amount_rcy;
    ArrayList<Offer_module> arrayList = new ArrayList<Offer_module>();
    String[] value = {"one", "two", "three", "four"};
    RadioButton amount1, amount2, amount3, amount4, amount5, amount6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        screen_shot = findViewById(R.id.screen_shot);
        camera = findViewById(R.id.camera);
        account_card = findViewById(R.id.account_card);
        phonePe_card = findViewById(R.id.phonePe_card);
        google_Pay_card = findViewById(R.id.google_Pay_card);
        paytm_card = findViewById(R.id.paytm_card);

        account_name = findViewById(R.id.account_name);
        account_number = findViewById(R.id.account_number);
        ifsc = findViewById(R.id.ifsc);
        phonePe_no = findViewById(R.id.phonePe_no);
        google_Pay_no = findViewById(R.id.google_Pay_no);
        paytm_details_no = findViewById(R.id.paytm_details_no);

        txn_Id = findViewById(R.id.txn_Id);
        Amount = findViewById(R.id.Amount);

        submit_ss = findViewById(R.id.submit_ss);
        submit_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    payment();
                }
            }
        });


        amountEdt = findViewById(R.id.idEdtAmount);
        payBtn = findViewById(R.id.idBtnPay);
        amount1 = findViewById(R.id.amount1);
        amount2 = findViewById(R.id.amount2);
        amount3 = findViewById(R.id.amount3);
        amount4 = findViewById(R.id.amount4);
        amount5 = findViewById(R.id.amount5);
        amount6 = findViewById(R.id.amount6);
        offer_rcy = findViewById(R.id.offer_rcy);
        amount_rcy = findViewById(R.id.amount_rcy);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {

                    startDialog();
                }
                if (!checkPermission()) {

                    requestPermission();

                }
            }
        });

        offer_rcy.setLayoutManager(new LinearLayoutManager(this));

        amount_rcy.setLayoutManager(new GridLayoutManager(this, 2));

        get_details();

        Amount_Adapter_Design adapter_design = new Amount_Adapter_Design(Payment.this, value);
        amount_rcy.setAdapter(adapter_design);
        offer_rcy();

        amount1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount1.isChecked()) {
                    bet = amount1.getText().toString();
                    amountEdt.setText(bet);
                }
            }
        });
        amount2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount2.isChecked()) {
                    bet = amount2.getText().toString();
                    amountEdt.setText(bet);

                }
            }
        });
        amount3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount3.isChecked()) {
                    bet = amount3.getText().toString();
                    amountEdt.setText(bet);

                }
            }
        });
        amount4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount4.isChecked()) {
                    bet = amount4.getText().toString();
                    amountEdt.setText("1000");

                }
            }
        });
        amount5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount5.isChecked()) {
                    bet = amount5.getText().toString();
                    amountEdt.setText("5000");

                }
            }
        });
        amount6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (amount6.isChecked()) {
                    bet = amount6.getText().toString();
                    amountEdt.setText("10000");

                }
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountEdt.length() < 3) {
                    amountEdt.setError("Minimum 100");
                    amountEdt.requestFocus();
                } else {
                    startPayment();
                }
                //startActivity(new Intent(Payment.this,Payment_Details.class));

            }
        });
        preferences = getSharedPreferences("details", MODE_PRIVATE);

    }

    private void payment() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        Call<payment_module> moduleCall = RetrofitClient
                .getInstance()
                .getAllApiService().payment_(preferences.getString("user_id", null)
                        , txn_Id.getText().toString(), Amount.getText().toString(), encodeScreen_shot);

        moduleCall.enqueue(new Callback<payment_module>() {
            @Override
            public void onResponse(Call<payment_module> call, retrofit2.Response<payment_module> response) {
                dialog.dismiss();
                payment_module module = response.body();
                if (response.isSuccessful()) {
                    if (module.getStatus().equalsIgnoreCase("success")) {
                        txn_Id.setText("");
                        Amount.setText("");
                        screen_shot.setImageResource(R.drawable.demo);
                        Toast.makeText(Payment.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Payment.this, module.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<payment_module> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Payment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation() {
        if (txn_Id.getText().toString().equalsIgnoreCase("")) {
            txn_Id.setError("Please Enter Transaction I'd");
            txn_Id.requestFocus();
            return false;
        } else if (Amount.getText().toString().equalsIgnoreCase("")) {
            Amount.setError("Please Enter Amount");
            Amount.requestFocus();
            return false;
        }

        return true;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDialog();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Payment.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {

                    Uri selectedImage = data.getData();
                    screen_shot.setImageURI(selectedImage);
                    file = new File(getRealPathFromURI(selectedImage));

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                        byte[] imagebyte = byteArrayOutputStream.toByteArray();
                        encodeScreen_shot = Base64.encodeToString(imagebyte, Base64.DEFAULT);
                        Log.e("gallery", "gallery -" + encodeScreen_shot);
                        //  Toast.makeText(Payment.this, "gallery \n "+encodeScreen_shot, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                break;
            case 1:

                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    screen_shot.setImageBitmap(photo);

                    Uri tempUri = getImageUri(Payment.this, photo);
                    file = new File(getRealPathFromURI(tempUri));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                    byte[] imagebyte = byteArrayOutputStream.toByteArray();
                    encodeScreen_shot = Base64.encodeToString(imagebyte, Base64.DEFAULT);

                }
                break;

        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (Payment.this.getContentResolver() != null) {
            Cursor cursor = Payment.this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                Payment.this);
        myAlertDialog.setTitle("Upload Pictures Option");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 0);


                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
                    }
                });
        myAlertDialog.show();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void get_details() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                if (!object.getString("bank_name").equalsIgnoreCase("")) {
                                    account_card.setVisibility(View.VISIBLE);
                                    account_name.setText(object.getString("account_holder_name"));
                                    account_number.setText(object.getString("account_no"));
                                    ifsc.setText(object.getString("ifsc_code"));
                                } else if (!object.getString("phone_pay").equalsIgnoreCase("")) {
                                    phonePe_card.setVisibility(View.VISIBLE);
                                    phonePe_no.setText(object.getString("phone_pay"));
                                } else if (!object.getString("google_pay").equalsIgnoreCase("")) {
                                    google_Pay_card.setVisibility(View.VISIBLE);
                                    google_Pay_no.setText(object.getString("google_pay"));
                                } else if (!object.getString("paytm_no").equalsIgnoreCase("")) {
                                    paytm_card.setVisibility(View.VISIBLE);
                                    paytm_details_no.setText(object.getString("paytm_no"));
                                }
                            }
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

            }
        });
        queue.add(request);
    }

    private void offer_rcy() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://iplcricbet.com/Api/get_discount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("message");
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Offer_module module = new Offer_module();
                                module.setId(object.getString("id"));
                                module.setOffer_name(object.getString("discount"));

                                arrayList.add(module);
                            }
                            Offer_Adapter adapter = new Offer_Adapter(Payment.this, arrayList);
                            offer_rcy.setAdapter(adapter);
                        } else {
                            Toast.makeText(Payment.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Payment.this, "Something want wrong", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(Payment.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


    public void startPayment() {
/*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "IPL CRIC BAT");
            options.put("description", "Get the best deal");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://cdn.razorpay.com/logos/JJEE3KuvTho3Zu_original.png");
            options.put("currency", "INR");
            options.put("amount", Double.valueOf(amountEdt.getText().toString()) * 100);//Integer.valueOf(amount.getText().toString().trim()) * 100);
            JSONObject preFill = new JSONObject();
            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")

    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            txnId = razorpayPaymentID;
//            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            // sendBalanceRequest();
            registerSuccess();

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    public void onPaymentError(int code, String response) {
        try {
            faildDialog();
            //  Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
            Log.e("reeere", ">>" + response);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void registerSuccess() {
        RequestQueue queue = Volley.newRequestQueue(Payment.this);
        StringRequest request = new StringRequest(Request.Method.POST, "https://iplcricbet.com/Api/checkout",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getString("status").equalsIgnoreCase("success")) {
                                    sucessDialog();
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(Payment.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> getParams = new HashMap<>();
                getParams.put("user_id", preferences.getString("user_id", null));
                getParams.put("transaction_id", txnId);
                getParams.put("amount", amountEdt.getText().toString());

                return getParams;
            }
        };
        queue.add(request);
    }


    public void sucessDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);
        TextView sucees = (TextView) dialog.findViewById(R.id.sucees);

        sucees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Payment.this, "Payment successfully !", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Payment.this, Open_Dashboard.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void faildDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.failed_dialog);
        TextView failed = (TextView) dialog.findViewById(R.id.failed);

        failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Payment.this, MainActivity.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Payment.this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(Payment.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
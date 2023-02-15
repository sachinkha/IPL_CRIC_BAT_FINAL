package com.planet.iplcricbat.Retrofit;

import com.planet.iplcricbat.Module.Bank_Module;
import com.planet.iplcricbat.Module.Bowling_bet;
import com.planet.iplcricbat.Module.Login_Module;
import com.planet.iplcricbat.Module.Match_Bet_Module;
import com.planet.iplcricbat.Module.Match_OTP;
import com.planet.iplcricbat.Module.Paytm_Module;
import com.planet.iplcricbat.Module.Player_bet;
import com.planet.iplcricbat.Module.Sign_Module;
import com.planet.iplcricbat.Module.UPI_Module;
import com.planet.iplcricbat.Module.forgot_password;
import com.planet.iplcricbat.Module.over_bet_module;
import com.planet.iplcricbat.Module.payment_module;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {


    @FormUrlEncoded
    @POST("user_signup")
    Call<Sign_Module> sign_up(@Field("name") String name,
                              @Field("mobile_no") String mobile_no,
                              @Field("email") String email,
                              @Field("refer_code")String refer_code,
                              @Field("password") String password);


    @FormUrlEncoded
    @POST("user_login")
    Call<Login_Module> login(@Field("mobile_no") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("toss_match_bet")
    Call<Match_Bet_Module> match_bet(@Field("user_id")String user_id,
                                     @Field("match_id") String match_id,
                                     @Field("team_name")String team_name,
                                     @Field("amount")String Amount,
                                     @Field("type")String type);

    @FormUrlEncoded
    @POST("player_bet")
    Call<Player_bet> player_bet(@Field("user_id")String user_id,
                                @Field("match_id")String match_id,
                                @Field("player_id")String Player_id,
                                @Field("run")String run,
                                @Field("amount")String amount);

    @FormUrlEncoded
    @POST("bowling_bet")
    Call<Bowling_bet> bowling_bat(@Field("user_id")String user_id,
                                 @Field("match_id")String match_id,
                                 @Field("player_id")String Player_id,
                                 @Field("wicket")String wicket,
                                 @Field("amount")String amount);

    @FormUrlEncoded
    @POST("withdrawal_upi")
    Call<UPI_Module> upi(@Field("user_id")String user_id,
                         @Field("upi_id")String upi_id,
                         @Field("amount")String amount);

    @FormUrlEncoded
    @POST("withdrawal_paytm")
    Call<Paytm_Module> paytm(@Field("user_id")String user_id,
                             @Field("paytm_no")String paytm_no,
                             @Field("amount")String amount);


    @FormUrlEncoded
    @POST("withdrawal_bank")
    Call<Bank_Module> bank(@Field("user_id")String user_id,
                           @Field("bank_account")String bank_account,
                           @Field("ifsc_code")String ifsc_code,
                           @Field("account_holder_name")String account_holder_name,
                           @Field("amount")String amount);


    @FormUrlEncoded
    @POST("over_bet")
    Call<over_bet_module> over_bet(@Field("user_id")String user_id,
                                   @Field("match_id")String match_id,
                                   @Field("over")String over,
                                   @Field("ball")String ball,
                                   @Field("run")String run,
                                   @Field("amount_bet")String amount_bet);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<forgot_password> forgot_password(@Field("email")String email);

    @FormUrlEncoded
    @POST("match_otp")
    Call<Match_OTP> match_otp(@Field("otp")String otp,
                              @Field("password")String password,
                              @Field("email")String email);@FormUrlEncoded
    @POST("payment")
    Call<payment_module> payment_(@Field("user_id")String user_id,
                                        @Field("transaction_id")String transaction_id,
                                        @Field("amount")String amount,
                                        @Field("image")String image);
}


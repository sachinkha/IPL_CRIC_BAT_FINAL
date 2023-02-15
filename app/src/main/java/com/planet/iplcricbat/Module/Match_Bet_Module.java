package com.planet.iplcricbat.Module;

public class Match_Bet_Module {

    String status;
    String id;
    String user_id;
    String team_name;
    String amount;
    String msg;
    String winning_total;
    String add_amount;
    String winning_amount;
    String grand_total;

    public Match_Bet_Module(String status, String id, String user_id, String team_name, String amount, String msg, String winning_total, String add_amount, String winning_amount,String grand_total) {
        this.status = status;
        this.id = id;
        this.user_id = user_id;
        this.team_name = team_name;
        this.amount = amount;
        this.msg = msg;
        this.winning_total = winning_total;
        this.add_amount = add_amount;
        this.winning_amount = winning_amount;
        this.grand_total = grand_total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWinning_total() {
        return winning_total;
    }

    public void setWinning_total(String winning_total) {
        this.winning_total = winning_total;
    }

    public String getAdd_amount() {
        return add_amount;
    }

    public void setAdd_amount(String add_amount) {
        this.add_amount = add_amount;
    }

    public String getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(String winning_amount) {
        this.winning_amount = winning_amount;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }
}

package com.planet.iplcricbat.Module;

public class Login_Module {
    String status;
    String msg;
    String name;
    String user_id;
    String email;
    String mobile_no;
    String refer_code;

    public Login_Module(String status, String msg, String name, String user_id, String email, String mobile_no, String refer_code) {
        this.status = status;
        this.msg = msg;
        this.name = name;
        this.user_id = user_id;
        this.email = email;
        this.mobile_no = mobile_no;
        this.refer_code = refer_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }
}

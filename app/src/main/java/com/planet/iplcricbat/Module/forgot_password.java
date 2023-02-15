package com.planet.iplcricbat.Module;

public class forgot_password {

    String status;
    String message;
    String otp;
    String email;

    public forgot_password(String status, String message, String otp, String email) {
        this.status = status;
        this.message = message;
        this.otp = otp;
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

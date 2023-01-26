package com.main;

import com.service.PasswordMeter;
import com.service.PasswordStrength;

public class Main {
    public static void main(String[] args) {

        PasswordMeter passwordMeter = new PasswordMeter();
        PasswordStrength passwordStrenth = passwordMeter.meter("qwe123!@#");
        System.out.println(passwordStrenth);

    }
}
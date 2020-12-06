package com.company;

import susteam.sdk.SusteamSdk;

import javax.swing.*;

public class AddAchievement {
    public static void main(String[] args) {
        String token = "invalid token";

        if (args.length > 0) {
            token = args[0];
        }

        SusteamSdk.init(token, 10);
    }
}

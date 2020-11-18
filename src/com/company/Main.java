package com.company;

import susteam.sdk.SusteamSdk;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String token = "invalid token";

        if (args.length > 0) {
            token = args[0];
            System.out.println(token);
        }

        SusteamSdk.init(token);
        SusteamSdk.user().onFailure(it -> {
            it.printStackTrace();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HomePage("unauthorized user");
                }
            });
        }).onSuccess(it -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HomePage(it.getUsername());
                }
            });
        });

    }
}

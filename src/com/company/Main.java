package com.company;

import susteam.sdk.SusteamSdk;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String token = "invalid token";

        if (args.length > 0) {
            token = args[0];
        }

        SusteamSdk.init(token, 10);
        SusteamSdk.user().onFailure(Throwable::printStackTrace).onSuccess(it -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HomePage(it.getUsername());
                }
            });
        });
    }
}

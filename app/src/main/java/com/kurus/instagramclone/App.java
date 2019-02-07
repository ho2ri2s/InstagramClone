package com.kurus.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7NAv9tmQVFb92H9psq8SikSVCduwzo9ctbg0aivv")
                // if defined
                .clientKey("dPMuCHtj3Ktxu2sMM92o6xEpPCKvauxLi75dZKk0")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}

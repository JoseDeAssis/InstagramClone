package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("LoiIs82gpnafGDMQaHafv5wfgSAjUlii2iQMR0ro")
                // if defined
                .clientKey("iA87K1Utr9Ax6miCCipwYNkRE079NJqdHLKdeqZT")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

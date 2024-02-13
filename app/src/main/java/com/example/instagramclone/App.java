package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("A9ZGgmrLhsFs2zsfCZ2NmTEmtZE25dprGdAx97Wi")
                // if defined
                .clientKey("uDhnLl6Xoyv2ekRkd139TaDXHvK7QRrbeswhQTAj")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

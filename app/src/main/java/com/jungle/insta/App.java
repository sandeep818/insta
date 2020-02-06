package com.jungle.insta;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Sf4eu7a1r8ILcoqGd3B0P35QNbFEJHiGejFZskah")
                .clientKey("EYfd1B65RwPzJYpsdefNb96qCHJa3NWfax54kcuR")
                .server("https://parseapi.back4app.com/")
                .build()
        );


    }
}

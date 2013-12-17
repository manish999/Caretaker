package com.rampgreen.caretakermobile;

import android.app.Application;
import android.content.Intent;

import com.rampgreen.caretakermobile.socket.RouterService;

/**
 * Application class for the demo. Used to ensure that MyVolley is initialized. {@see MyVolley}
 * @author Manish Pathak
 *
 */
public class CareTakerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        init();
        
    }


    private void init() {
        MyVolley.init(this);
    }
}

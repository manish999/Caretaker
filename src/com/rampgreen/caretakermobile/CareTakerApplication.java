package com.rampgreen.caretakermobile;

import android.app.Application;

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
        
//        Intent	routerSetupIntent= new Intent(this, RouterService.class);
//		this.startService(routerSetupIntent);
    }


    private void init() {
        MyVolley.init(this);
    }
}

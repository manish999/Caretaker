package com.rampgreen.caretakermobile.ui;

import android.app.Activity;

public class Alerts extends Activity {

	/*@Override
	
	    protected void onCreate(Bundle savedInstanceState) {
	
	 
	
	        super.onCreate(savedInstanceState);
	
	        setContentView(R.layout.activity_main);
	
	 
	
	        // listener handler
	
	        View.OnClickListener handler = new View.OnClickListener(){
	
	            public void onClick(View v) {
	
	 
	
	                switch (v.getId()) {
	
	 
	
	                    case R.id.btnShowNotification:
	
	                        showNotification();
	
	                        break;
	
	 
	
	                    case R.id.btnCancelNotification:
	
	                        cancelNotification(0);
	
	                        break;
	
	                }
	
	            }
	
	        };
	
	 
	
	        // we will set the listeners
	
	        findViewById(R.id.btnShowNotification).setOnClickListener(handler);
	
	        findViewById(R.id.btnCancelNotification).setOnClickListener(handler);
	
	 
	
	    }
	
	 
	
	    public void showNotification(){
	
	 
	
	        // define sound URI, the sound to be played when there's a notification
	
	        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	
	 
	
	        // intent triggered, you can add other intent for other actions
	
	        Intent intent = new Intent(Alerts.this, NotificationReceiver.class);
	
	        PendingIntent pIntent = PendingIntent.getActivity(Alerts.this, 0, intent, 0);
	
	 
	
	        // this is it, we'll build the notification!
	
	        // in the addAction method, if you don't want any icon, just set the first param to 0
	
	        Notification mNotification = new Notification.Builder(this)
	
	 
	
	            .setContentTitle("New Post!")
	
	            .setContentText("Here's an awesome update for you!")
	
	            .setSmallIcon(R.drawable.ninja)
	
	            .setContentIntent(pIntent)
	
	            .setSound(soundUri)
	
	 
	
	            .addAction(R.drawable.ninja, "View", pIntent)
	
	            .addAction(0, "Remind", pIntent)
	
	 
	
	            .build();
	
	 
	
	        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	
	 
	
	        // If you want to hide the notification after it was selected, do the code below
	
	        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
	
	 
	
	        notificationManager.notify(0, mNotification);
	
	    }
	
	 
	
	    public void cancelNotification(int notificationId){
	
	 
	
	        if (Context.NOTIFICATION_SERVICE!=null) {
	
	            String ns = Context.NOTIFICATION_SERVICE;
	
	            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
	
	            nMgr.cancel(notificationId);
	
	        }
	    }*/
	}

